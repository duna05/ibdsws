/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.facade;

import com.bds.ws.dto.IbCargaPagosCorpDetPjDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.enumerated.EstatusPagosEnum;
import com.bds.ws.model.IbCargaPagosCorpDetPj;
import com.bds.ws.model.IbEstatusPagosPj;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author robinson.rodriguez
 */
@Named("ibCargaPagosCorpDetPjFacade")
@Stateless(name = "wsIbCargaPagosCorpDetPjFacade")
public class IbCargaPagosCorpDetPjFacade extends AbstractFacade<IbCargaPagosCorpDetPj> {
    @EJB
    IbEstatusPagosPjFacade ibEstatusPagosPjFacade;
    
    @PersistenceContext(unitName = "ibdsPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IbCargaPagosCorpDetPjFacade() {
        super(IbCargaPagosCorpDetPj.class);
    }

    public IbCargaPagosCorpDetPj buscarIbCargaPagosEspDetPj(BigDecimal idPago) {
        IbCargaPagosCorpDetPj pagos_especiales = null;
        try {
            pagos_especiales = (IbCargaPagosCorpDetPj) getEntityManager().find(IbCargaPagosCorpDetPj.class, idPago);
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
     * @param idpago id del pago a buscar
     * @param estatusCarga del archivo a registrar
     * @return ibCargaPagosEspPjDTO resulset de la consulta
     * @throws java.lang.Exception Error lanzado por el facade
     */
    public List<IbCargaPagosCorpDetPj> listarIbCargaPagosEspDetPj(BigDecimal idpago, Short estatusCarga) throws Exception {
        
        List<IbCargaPagosCorpDetPj> ibCargaPagosCorpDetPjList = new ArrayList<IbCargaPagosCorpDetPj>();
        try {

            StringBuilder consulta = new StringBuilder();
            consulta.append("SELECT e FROM IbCargaPagosCorpDetPj e ");
            consulta.append(" WHERE   e.idPago.idPago = :idPago ");
            
            if(estatusCarga ==null){
            estatusCarga =0;
            consulta.append(" AND     e.estatusAutorizacion.id > :idEstatus ");
            }else{
            consulta.append(" AND     e.estatusAutorizacion.id = :idEstatus ");
            }
            
            consulta.append(" ORDER BY e.fechaHoraCarga DESC, e.idPagoDetalle DESC  ");

            ibCargaPagosCorpDetPjList = (List<IbCargaPagosCorpDetPj>) em.createQuery(consulta.toString())
                    .setParameter("idPago", idpago)
                    .setParameter("idEstatus", estatusCarga)
                    .getResultList();
            
        } catch (NoResultException e) {

        } catch (Exception e) {   
            throw e;
        }
        return ibCargaPagosCorpDetPjList;
    }

    public int modificarEstatusDetalle(Long idPago, BigDecimal estatus) {
        int cantActualizados = 0;

        StringBuilder queryUpdate = new StringBuilder();
        queryUpdate.append("UPDATE IB_CARGA_PAGOS_CORP_DET_PJ D ")
                .append(" SET D.ESTATUS_AUTORIZACION = ? ")
                .append(" WHERE D.ID_PAGO = ? ");
        try {
            cantActualizados = getEntityManager().createNativeQuery(queryUpdate.toString())
                    .setParameter(1, estatus)
                    .setParameter(2, idPago)
                    .executeUpdate();
        } catch (Exception e) {
            throw e;
        }

        return cantActualizados;
    }
    
    
    public IbCargaPagosCorpDetPj actualizarEstatusDetallePagosPorId(BigDecimal idTransaction, BigDecimal idEstatus) throws Exception {
        IbCargaPagosCorpDetPj ibCargaPagosCorpDetPj = null;
        IbEstatusPagosPj estatus = null;

        try {
            ibCargaPagosCorpDetPj = (IbCargaPagosCorpDetPj) getEntityManager().find(IbCargaPagosCorpDetPj.class, idTransaction);
            if(ibCargaPagosCorpDetPj!=null){
                estatus = this.ibEstatusPagosPjFacade.buscarIbEstatusPj(idEstatus);
                ibCargaPagosCorpDetPj.setEstatusAutorizacion(estatus);
                ibCargaPagosCorpDetPj = (IbCargaPagosCorpDetPj) getEntityManager().merge(ibCargaPagosCorpDetPj);
            }
              
        } catch (NoResultException e) {
            // Se debe validar en la clase que llama el metodo
        } catch (Exception e) {
            //TODO: definir respuesta incorrecta            
            throw e;
        }
        return ibCargaPagosCorpDetPj;
    }
    
    
   public boolean consultarEstatus( String nroIdentificacionCliente, Long codigoClienteAbanks ) throws Exception{
       IbCargaPagosCorpDetPjDTO ibCargaPagosCorpDetPjDTO = new IbCargaPagosCorpDetPjDTO();
       
        ibCargaPagosCorpDetPjDTO.setIbCargaPagosCorpDetPjsList(new ArrayList<IbCargaPagosCorpDetPj>());
        ibCargaPagosCorpDetPjDTO.setRespuestaDTO(new RespuestaDTO());
        
        boolean resultado=true;
        try {
            StringBuilder consulta = new StringBuilder();
            
            consulta.append(" SELECT a.estatusAutorizacion FROM IbBeneficiariosPj a, IbCargaPagosEspDetPj b");
            consulta.append(" WHERE a.nroIdentificacionCliente = :nroIdentificacionCliente");
            consulta.append(" AND b.codigoClienteAbanks = :codigoClienteAbanks");
            
            String estatus =   (String) em.createQuery(consulta.toString())
                    .setParameter("nroIdentificacionCliente", nroIdentificacionCliente)
                    .setParameter("codigoClienteAbanks", codigoClienteAbanks)
                    .getSingleResult();
            System.out.println(estatus);
            if(estatus.equals(EstatusPagosEnum.CARGADO)||estatus.equals(EstatusPagosEnum.PENDIENTE_X_AUTORIZAR)||
             estatus.equals(EstatusPagosEnum.PENDIENTE_FIRMA)||estatus.equals(EstatusPagosEnum.DETENIDO)||
             estatus.equals(EstatusPagosEnum.REACTIVADA)){
            resultado =true;
            
            }else{
            resultado=false;
            } 
        } catch (NoResultException e) {

        } catch (Exception e) {
            throw e;
        }
        return resultado;  
        
    }

}
