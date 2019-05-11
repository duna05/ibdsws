/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.facade;

import com.bds.ws.dto.IbCargaBeneficiariosPjDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.model.IbCargaBeneficiariosPj;
import com.bds.ws.model.IbEmpresas;
import com.bds.ws.model.IbEstatusPagosPj;
import com.bds.ws.model.IbTextos;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author robinson.rodriguez
 */
@Named("ibCargaBeneficiariosPjFacade")
@Stateless(name = "wsIbCargaBeneficiariosPjFacade")
public class IbCargaBeneficiariosPjFacade extends AbstractFacade<IbCargaBeneficiariosPj> {

    @PersistenceContext(unitName = "ibdsPU")
    private EntityManager em;

   @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IbCargaBeneficiariosPjFacade() {
        super(IbCargaBeneficiariosPj.class);
    }
    

    public List<IbCargaBeneficiariosPj> listarIbCargaBeneficiariosPj(BigDecimal idEmpresa, Short estatusCarga) {
        List<IbCargaBeneficiariosPj> ibCargaBeneficiariosPjDTO = null;
        try {
            StringBuilder consulta = new StringBuilder();
            consulta.append(" SELECT cb FROM IbCargaBeneficiariosPj cb ");
            consulta.append(" WHERE cb.idEmpresa.id =:empresa");
            consulta.append(" AND cb.estatusAutorizacion =:estatus");

            ibCargaBeneficiariosPjDTO = (List<IbCargaBeneficiariosPj>) em.createQuery(consulta.toString())
                    .setParameter("empresa", idEmpresa)
                    .setParameter("estatus", new IbEstatusPagosPj(new BigDecimal(estatusCarga)))
                    .getResultList();
        } catch (Exception e) {
            throw e;
        }
        return ibCargaBeneficiariosPjDTO;
    }

    public Boolean archivoCargaBeneficiariosMasivaExiste(IbEmpresas idEmpresa, String filename) throws Exception {
        try{

            StringBuilder consulta = new StringBuilder();            
            consulta.append("SELECT count(i) ");
            consulta.append("  FROM IbCargaBeneficiariosPj i ");
            consulta.append(" WHERE i.idEmpresa.id = :empresa ");
            consulta.append("   AND i.nombreArchivo = :archivo ");            
            consulta.append("   AND i.estatusAutorizacion.id > 0 ");//ya esta procesado

            long count = (long) em.createQuery(consulta.toString())
                .setParameter("archivo", filename)
                .setParameter("empresa", idEmpresa.getId())
                .getSingleResult();

            return (count > 0);
         
        }catch(Exception ex){
            //respuestaDTO.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log            
            throw ex;
        }

    }
    
    public Map<String,String> listarErrorresCargaBeneficiariosMasiva() throws Exception {
        
        Map<String,String> mapErrorresCargaBeneficiariosMasiva = new HashMap(); 
        
        try {
            
            StringBuilder consulta = new StringBuilder();
            consulta.append("SELECT  t ");
            consulta.append("  FROM IbTextos t");
            consulta.append(" WHERE t.codigo LIKE 'pjw.cargaMasBeneficiarios.error%'");
            
            Collection listaCargaBeneficiarios = em.createQuery(consulta.toString())                   
                    .getResultList();
            
            Iterator interador = listaCargaBeneficiarios.iterator();
            IbTextos ibTexto;
            while (interador.hasNext()) {
                ibTexto = (IbTextos)interador.next();
                mapErrorresCargaBeneficiariosMasiva.put(ibTexto.getCodigo(), ibTexto.getMensajeUsuario());
            }

        } catch (NoResultException e) {

        } catch (Exception e) {
            throw e;
        }
        return mapErrorresCargaBeneficiariosMasiva;
    }
    
    
    public IbCargaBeneficiariosPj buscarIbCargaBeneficiariosPj(Long idCargaBeneficiario){
        IbCargaBeneficiariosPj cargaBeneficiario = null;
        try{
            cargaBeneficiario = (IbCargaBeneficiariosPj)getEntityManager().find(IbCargaBeneficiariosPj.class, idCargaBeneficiario);
        }catch (NoResultException e) {
            // Se debe validar en la clase que llama el metodo
        } catch (Exception e) {
            //TODO: definir respuesta incorrecta            
            throw e;
        }
        return cargaBeneficiario;
    }
    
   
    
    /**
     * 
     * @param idEmpresa
     * @param idUsuarioAurorizado
     * @param idServicio
     * @param StatusAConsultar
     * @return
     * @throws Exception 
     */
    public IbCargaBeneficiariosPjDTO listarIbCargaBeneficiariosAutorizarPj(BigDecimal idEmpresa, Long idUsuarioAurorizado,Long idServicio, String StatusAConsultar) throws Exception {

        IbCargaBeneficiariosPjDTO ibCargaBeneficiariosPjDTO = new IbCargaBeneficiariosPjDTO();
        ibCargaBeneficiariosPjDTO.setIbCargaBeneficiariosPjsList(new ArrayList<IbCargaBeneficiariosPj>());
        ibCargaBeneficiariosPjDTO.setRespuestaDTO(new RespuestaDTO());
        
        List<IbCargaBeneficiariosPj> ibCargaBenficiariosPjList = null;
        
        try {
            StringBuilder consulta = new StringBuilder();
            consulta.append(" SELECT distinct b FROM IbCargaBeneficiariosPj  b");
            consulta.append(" INNER JOIN FETCH b.ibBeneficiariosPjCollection bar");
            consulta.append(" WHERE bar.idEmpresa.id =:idEmpresa and  (bar.estatusAutorizacion.id=1 or bar.estatusAutorizacion.id=2 or bar.estatusAutorizacion.id=3)" );
            consulta.append(" and NOT EXISTS (SELECT p FROM IbSecuenciaAprobacionPj p" );
            consulta.append("  WHERE p.idTransaccion = bar.idCargaBeneficiario.idCargaBeneficiario and" );  
            consulta.append("  p.idServicioPj.id =:idServicioPj and p.idUsuarioPj.id =:idUsuarioAurorizado)" );  
         
            
            ibCargaBenficiariosPjList = (List<IbCargaBeneficiariosPj>) em.createQuery(consulta.toString())
                   .setParameter("idEmpresa", idEmpresa)
                   .setParameter("idUsuarioAurorizado", new BigDecimal(idUsuarioAurorizado))
                    .setParameter("idServicioPj", new BigDecimal(idServicio))
                    .getResultList();


        } catch (Exception e) {
            throw e;
        }
        ibCargaBeneficiariosPjDTO.setIbCargaBeneficiariosPjsList(ibCargaBenficiariosPjList);

        return ibCargaBeneficiariosPjDTO;
    }
}
