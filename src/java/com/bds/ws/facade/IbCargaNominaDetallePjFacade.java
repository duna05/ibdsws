/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.facade;

import com.bds.ws.dto.IbCargaNominaDetallePjDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.model.IbCargaNominaDetallePj;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author juan.vasquez
 */
@Named("ibCargaNominaDetallePjFacade")
@Stateless(name = "wsIbCargaNominaDetallePjFacade")
public class IbCargaNominaDetallePjFacade extends AbstractFacade<IbCargaNominaDetallePj> {
    @PersistenceContext(unitName = "ibdsPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public IbCargaNominaDetallePjFacade(){
        super(IbCargaNominaDetallePj.class);
    }
    
    public int modificarEstatusDetalle(BigDecimal idPago, BigDecimal estatus){
        int cantActualizados = 0;
        
        StringBuilder queryUpdate = new StringBuilder();
        queryUpdate.append("UPDATE Ib_Carga_Nomina_Detalle_Pj D ")
                .append(" SET   D.estatus = ? ")
                .append(" WHERE D.id_Pago = ? ");
        try{
            cantActualizados = getEntityManager().createNativeQuery(queryUpdate.toString())
                    .setParameter(1, estatus)
                    .setParameter(2, idPago)
                    .executeUpdate();
        }catch(Exception e){
            throw e;
        }
        
        return cantActualizados;
    }
    
    /**
     * PJW-CASH330 – Consulta – Nomina, Consulta Carga Masiva - Detalles
     * @param idpago 
     * @param estatusCarga
     * @return 
     */
    public IbCargaNominaDetallePjDTO listarCargaNominaDetalle(BigDecimal idpago, Short estatusCarga) {

        IbCargaNominaDetallePjDTO  ibCargaNominaDetallePjDTO  =  new IbCargaNominaDetallePjDTO();
        ibCargaNominaDetallePjDTO.setIbCargaNominaDetallesPjList(new ArrayList<IbCargaNominaDetallePj>());
        ibCargaNominaDetallePjDTO.setRespuestaDTO(new RespuestaDTO());
        try {
            
            /** La union de la tabla del dia y la del historico
             *  el order by es deacuerdo al requerimiento 
             *   "ordenados de forma descendente a partir 
             *    del campo Fecha de registro del lote"
             **/
            StringBuilder consulta = new StringBuilder();
            //ORDEN ESTABLECIDO EN IbCargaNominaDetallePj
            consulta.append("SELECT ");
            consulta.append("  ID_PAGO_DETALLE, ");
            consulta.append("  CODIGO_CLIENTE_ABANKS, ");
            consulta.append("  ID_PAGO , ");
            consulta.append("  NRO_LINEA_ARCHIVO , ");
            consulta.append("  NRO_IDENTIFICACION_CLIENTE, ");
            consulta.append("  NRO_CUENTA_BENEFICIARIO , ");
            consulta.append("  MONTO_PAGO , ");
            consulta.append("  ESTATUS, ");
            consulta.append("  CODIGO_MOTIVO_RECHAZO, ");
            consulta.append("  NOMBRE_BENEFICIARIO, ");
            consulta.append("  FECHA_HORA_CARGA, ");
            consulta.append("  FECHA_HORA_MODIFICACION, ");
            consulta.append("  NOMBRE ");            
            consulta.append("FROM ( ");
            consulta.append("  SELECT cn.*, e.nombre FROM ib_carga_nomina_detalle_pj cn join ib_estatus_pagos_pj e on (cn.estatus=e.id)");
            consulta.append("     WHERE cn.id_pago= ?1");
            consulta.append("  UNION ALL ");
            consulta.append("  SELECT ch.*, e.nombre FROM ib_carga_nomina_det_pj_his ch join ib_estatus_pagos_pj e on (ch.estatus=e.id)");
            consulta.append("     WHERE ch.id_pago= ?1");
            consulta.append("  ) ");
            
            if (estatusCarga != null) {
                consulta.append(" WHERE ESTATUS = ?2 ");
            } else {
                consulta.append(" WHERE ESTATUS >= 1 "); //MAYORES A PRECARGADO
            }
            consulta.append("  ORDER BY id_pago_detalle  ");
            
            Collection listaCargaNominaDetalle;
            if(estatusCarga != null){
                listaCargaNominaDetalle = em.createNativeQuery(consulta.toString())
                        .setParameter(1, idpago)
                        .setParameter(2, estatusCarga)
                        .getResultList();
            }else {
                listaCargaNominaDetalle = em.createNativeQuery(consulta.toString())
                        .setParameter(1, idpago)
                        .getResultList();
            }

            Iterator interador = listaCargaNominaDetalle.iterator();
            Object[] registro;
            while (interador.hasNext()) {
                registro = (Object[]) interador.next();
                ibCargaNominaDetallePjDTO.getIbCargaNominaDetallesPjList().add(new IbCargaNominaDetallePj(registro));
            }
            
        } catch (NoResultException e) {

        } catch (Exception e) {
            //TODO: definir respuesta incorrecta
            //ibCargaNominaPjDTO.getRespuestaDTO().setCodigo();            
            throw e;
        }
        return ibCargaNominaDetallePjDTO;
    }
    
    
    public IbCargaNominaDetallePjDTO listarCargaNominaDetalle2(BigDecimal idpago, Short estatusCarga,Long idServicio,Long idUsuario) {

        IbCargaNominaDetallePjDTO  ibCargaNominaDetallePjDTO  =  new IbCargaNominaDetallePjDTO();
        ibCargaNominaDetallePjDTO.setIbCargaNominaDetallesPjList(new ArrayList<IbCargaNominaDetallePj>());
        ibCargaNominaDetallePjDTO.setRespuestaDTO(new RespuestaDTO());
        try {
            
            /** La union de la tabla del dia y la del historico
             *  el order by es deacuerdo al requerimiento 
             *   "ordenados de forma descendente a partir 
             *    del campo Fecha de registro del lote"
             **/
            StringBuilder consulta = new StringBuilder();
            //ORDEN ESTABLECIDO EN IbCargaNominaDetallePj
            consulta.append("SELECT ");
            consulta.append("  ID_PAGO_DETALLE, ");
            consulta.append("  CODIGO_CLIENTE_ABANKS, ");
            consulta.append("  ID_PAGO, ");
            consulta.append("  NRO_LINEA_ARCHIVO, ");
            consulta.append("  NRO_IDENTIFICACION_CLIENTE, ");
            consulta.append("  NRO_CUENTA_BENEFICIARIO , ");
            consulta.append("  MONTO_PAGO , ");
            consulta.append("  ESTATUS, ");
            consulta.append("  CODIGO_MOTIVO_RECHAZO, ");
            consulta.append("  NOMBRE_BENEFICIARIO, ");
            consulta.append("  FECHA_HORA_CARGA, ");
            consulta.append("  FECHA_HORA_MODIFICACION, ");
            consulta.append("  NOMBRE ");            
            consulta.append("FROM ( ");
            consulta.append(" SELECT s.*,E.NOMBRE FROM IB_CARGA_NOMINA_DETALLE_PJ s LEFT JOIN IB_SECUENCIA_APROBACION_PJ ss ON S.ID_PAGO_DETALLE = SS.ID_TRANSACCION");
            consulta.append(" JOIN  ib_estatus_pagos_pj e on (s.estatus=e.id) WHERE   s.ID_PAGO_DETALLE");
            consulta.append("  NOT IN(SELECT ID_TRANSACCION FROM IB_SECUENCIA_APROBACION_PJ where ID_SERVICIO_PJ =?3 AND ID_USUARIO_PJ =?4) AND S.ID_PAGO=?1 and s.ESTATUS=?2");
            consulta.append("  ) ");
            
         
            
            Collection listaCargaNominaDetalle;
            
                listaCargaNominaDetalle = em.createNativeQuery(consulta.toString())
                        .setParameter(1, idpago)
                        .setParameter(2, estatusCarga)
                        .setParameter(3, idServicio)
                        .setParameter(4, idUsuario)
                        .getResultList();
            

            Iterator interador = listaCargaNominaDetalle.iterator();
            Object[] registro;
            while (interador.hasNext()) {
                registro = (Object[]) interador.next();
                ibCargaNominaDetallePjDTO.getIbCargaNominaDetallesPjList().add(new IbCargaNominaDetallePj(registro));
            }
            
        } catch (NoResultException e) {

        } catch (Exception e) {
            //TODO: definir respuesta incorrecta
            //ibCargaNominaPjDTO.getRespuestaDTO().setCodigo();            
            throw e;
        }
        return ibCargaNominaDetallePjDTO;
    }
    
    public IbCargaNominaDetallePj buscarIbCargaNominaDetallePj(BigDecimal idPagoDetalle) {
        IbCargaNominaDetallePj nominaDetalle = null;
        try {
            nominaDetalle = (IbCargaNominaDetallePj) getEntityManager().find(IbCargaNominaDetallePj.class, idPagoDetalle);
        } catch (NoResultException e) {
            // Se debe validar en la clase que llama el metodo
        } catch (Exception e) {
            //TODO: definir respuesta incorrecta            
            throw e;
        }
        return nominaDetalle;
    }
   
    
    
}
