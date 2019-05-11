/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.facade;

import com.bds.ws.dto.IbCargaPagosCorpPjDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.model.IbCargaPagosCorpPj;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TemporalType;

/**
 *
 * @author Christian.gutierrez
 */
@Named("ibCargaPagosCorpPjFacade")
@Stateless(name = "wsIbCargaPagosCorpPjFacade")
public class IbCargaPagosCorpPjFacade extends AbstractFacade<IbCargaPagosCorpPj> {

    @PersistenceContext(unitName = "ibdsPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IbCargaPagosCorpPjFacade() {
        super(IbCargaPagosCorpPj.class);
    }
    

    public IbCargaPagosCorpPj buscarIbCargaPagosCorpPj(Long idPago) {
        IbCargaPagosCorpPj pagos_especiales = null;
        try {
            pagos_especiales = (IbCargaPagosCorpPj) getEntityManager().find(IbCargaPagosCorpPj.class, idPago);
        } catch (NoResultException e) {
            // Se debe validar en la clase que llama el metodo
        } catch (Exception e) {
            //TODO: definir respuesta incorrecta            
            throw e;
        }
        return pagos_especiales;
    }
    
    

    /**
     * CASH410 – Consulta - Lista de pagos para Pagos especiales
     *
     * @param cdClienteAbanks Código de cliente de identificación en el core
     * bancario
     * @param estatusCarga estatus del archivo cargado
     * @return ibCargaPagosEspPjDTO resulset de la consulta
     * @throws java.lang.Exception Error lanzado por el facade
     */
    public List<IbCargaPagosCorpPj> listarIbCargaPagosCorpPj(BigDecimal cdClienteAbanks, Short estatusCarga) throws Exception {

        List<IbCargaPagosCorpPj> ibCargaPagosCorpPjList = new ArrayList<IbCargaPagosCorpPj>();
        try {
            StringBuilder consulta = new StringBuilder();
            consulta.append("SELECT e FROM IbCargaPagosCorpPj e ");
            consulta.append(" WHERE   e.codigoClienteAbanks = :codigoClienteAbanks ");
            if (estatusCarga == null) {
                estatusCarga = 0;
                consulta.append(" AND     e.estatusAutorizacion.id > :idEstatus ");
            } else {
                consulta.append(" AND     e.estatusAutorizacion.id = :idEstatus ");
            }
            consulta.append(" ORDER BY e.fechaHoraCarga DESC, e.idPago DESC  ");

            ibCargaPagosCorpPjList = (List<IbCargaPagosCorpPj>) em.createQuery(consulta.toString())
                    .setParameter("codigoClienteAbanks", cdClienteAbanks)
                    .setParameter("idEstatus", estatusCarga)
                    .getResultList();
        } catch (Exception e) {
            throw e;
        }
        return ibCargaPagosCorpPjList;
    }

    public Map<String, String> listarErroresPagosCorporativasCargaMasiva() throws Exception {

        Map<String, String> mapErroresPagosEspecialesCargaMasiva = new HashMap();

        try {

            StringBuilder consulta = new StringBuilder();

            consulta.append("SELECT  CODIGO, MENSAJE_USUARIO ");
            consulta.append("  FROM ib_textos ");
            consulta.append(" WHERE CODIGO LIKE 'pjw.cargaMasPagCorp.error.archivo.%'");

            Collection listaCargaPagosEspeciales = em.createNativeQuery(consulta.toString())
                    .getResultList();

            Iterator interador = listaCargaPagosEspeciales.iterator();
            Object[] registro;
            while (interador.hasNext()) {
                registro = (Object[]) interador.next();
                mapErroresPagosEspecialesCargaMasiva.put((String) registro[0], (String) registro[1]);
            }

        } catch (NoResultException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }
        return mapErroresPagosEspecialesCargaMasiva;
    }

    public Boolean archivoPagosCorporativasCargaMasivaExiste(Long codeClient, String filename) throws Exception {
        try {

            StringBuilder consulta = new StringBuilder();
            consulta.append("SELECT count(i) ");
            consulta.append("  FROM IbCargaPagosCorpPj i ");
            consulta.append(" WHERE i.codigoClienteAbanks = :codigoClienteAbanksP ");
            consulta.append("   AND i.nombreArchivo = :nombreArchivoP ");
            consulta.append("   AND i.estatusAutorizacion.id > 0 ");//ya esta procesado

            long count = (long) em.createQuery(consulta.toString())
                    .setParameter("nombreArchivoP", filename)
                    .setParameter("codigoClienteAbanksP", codeClient)
                    .getSingleResult();

            return (count > 0);

        } catch (Exception ex) {
            //respuestaDTO.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log            
            throw ex;
        }

    }
    
    /**
    * Lista los pagos corporativos que el usuario puede autorizar
    * @param cdClienteAbanks
    * @param idUsuarioAurorizado
    * @param idServicio
    * @param StatusAConsultar
    * @return
    * @throws Exception 
    */
   public IbCargaPagosCorpPjDTO listarIbCargaPagosCorpAutorizarPj(BigDecimal cdClienteAbanks, Long idUsuarioAurorizado,Long idServicio, String StatusAConsultar) throws Exception {

        IbCargaPagosCorpPjDTO ibCargaPagosCorpPjDTO = new IbCargaPagosCorpPjDTO();
        ibCargaPagosCorpPjDTO.setIbCargaPagosCorpPjList(new ArrayList<IbCargaPagosCorpPj>());
        ibCargaPagosCorpPjDTO.setRespuestaDTO(new RespuestaDTO());
        
        List<IbCargaPagosCorpPj> ibCargaPagosCorpPjList = null;
        
        try {
            StringBuilder consulta = new StringBuilder();
            consulta.append(" SELECT distinct b FROM IbCargaPagosCorpPj  b");
            consulta.append(" INNER JOIN FETCH b.ibCargaPagosCorpDetPjCollection bar");
            consulta.append(" WHERE bar.codigoClienteAbanks =:cdClienteAbanks and  (bar.estatusAutorizacion.id=1 or bar.estatusAutorizacion.id=2 or bar.estatusAutorizacion.id=3)" );
            consulta.append(" and NOT EXISTS (SELECT p FROM IbSecuenciaAprobacionPj p" );
            consulta.append("  WHERE p.idTransaccion = bar.idPagoDetalle and" );  
            consulta.append("  p.idServicioPj.id =:idServicioPj and p.idUsuarioPj.id =:idUsuarioAurorizado)" );  
         
            
            ibCargaPagosCorpPjList = (List<IbCargaPagosCorpPj>) em.createQuery(consulta.toString())
                   .setParameter("cdClienteAbanks", cdClienteAbanks)
                   .setParameter("idUsuarioAurorizado", new BigDecimal(idUsuarioAurorizado))
                    .setParameter("idServicioPj", new BigDecimal(idServicio))
                    .getResultList();


        } catch (Exception e) {
            throw e;
        }
        ibCargaPagosCorpPjDTO.setIbCargaPagosCorpPjList(ibCargaPagosCorpPjList);

        return ibCargaPagosCorpPjDTO;
    }
   
   
   
    public IbCargaPagosCorpPjDTO listarIbCargaPagosCorpAdminPj(BigDecimal clienteAbanks,Long idPago,Date fechaInicio, Date fechaFin) throws Exception {

        IbCargaPagosCorpPjDTO ibCargaPagosCorpPjDTO = new IbCargaPagosCorpPjDTO();
        ibCargaPagosCorpPjDTO.setIbCargaPagosCorpPjList(new ArrayList<IbCargaPagosCorpPj>());
        ibCargaPagosCorpPjDTO.setRespuestaDTO(new RespuestaDTO());
        Boolean flagFecha=Boolean.FALSE;
        
        List<IbCargaPagosCorpPj> ibCargaPagosCorpPjList = null;
        
        try {
            StringBuilder consulta = new StringBuilder(); 
             
            consulta.append(" SELECT  b FROM IbCargaPagosCorpPj  b ");
            consulta.append("  WHERE b.codigoClienteAbanks = :cdClienteAbanks" ); 
              if(!(fechaInicio==null || fechaFin==null)){
               flagFecha=Boolean.TRUE;
              }
            
                if( flagFecha == Boolean.TRUE && (idPago==0 )){
                  
                    consulta.append(" AND b.fechaHoraCarga BETWEEN :startDate AND :endDate"); 
                    ibCargaPagosCorpPjList = (List<IbCargaPagosCorpPj>) em.createQuery(consulta.toString())
                   .setParameter("cdClienteAbanks", clienteAbanks)
                   .setParameter("startDate", fechaInicio, TemporalType.DATE)  
                   .setParameter("endDate", fechaFin, TemporalType.DATE)  
                   .getResultList();  
                }else if(idPago>0 && flagFecha == Boolean.FALSE){
                    consulta.append(" AND b.idPago ="+idPago+" ");
                    ibCargaPagosCorpPjList = (List<IbCargaPagosCorpPj>) em.createQuery(consulta.toString())
                    .setParameter("cdClienteAbanks", clienteAbanks)   
                    .getResultList(); 
                
                }else if(flagFecha == Boolean.TRUE && (idPago>0 )){
                    consulta.append(" AND b.fechaHoraCarga BETWEEN :startDate AND :endDate"); 
                    consulta.append(" AND b.idPago ="+idPago+" ");
                    ibCargaPagosCorpPjList = (List<IbCargaPagosCorpPj>) em.createQuery(consulta.toString())
                   .setParameter("cdClienteAbanks", clienteAbanks)
                   .setParameter("startDate", fechaInicio, TemporalType.DATE)  
                   .setParameter("endDate", fechaFin, TemporalType.DATE)  
                   .getResultList();
        
            }
                
                
            


        } catch (Exception e) {
            throw e;
        }
        ibCargaPagosCorpPjDTO.setIbCargaPagosCorpPjList(ibCargaPagosCorpPjList);

        return ibCargaPagosCorpPjDTO;
    }
   
   

}
