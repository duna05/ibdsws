/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.facade;

import com.bds.ws.dto.IbCargaNominaDetallePjDTO;
import com.bds.ws.dto.IbErroresNominaDetallePjDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.model.IbCargaNominaDetallePj;
import com.bds.ws.model.IbErroresPj;
import com.bds.ws.model.IbErroresNominaDetallePj;
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
@Named("ibErroresNominaDetallePjFacade")
@Stateless(name = "wsIbErroresNominaDetallePjFacade")
public class IbErroresNominaDetallePjFacade extends AbstractFacade<IbErroresNominaDetallePj> {
    @PersistenceContext(unitName = "ibdsPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public IbErroresNominaDetallePjFacade(){
        super(IbErroresNominaDetallePj.class);
    }
    
    /**
     * PJW-CASH330 – Consulta – Nomina, Consulta Carga Masiva - Errores
     */
    public IbErroresNominaDetallePjDTO listarCargaNominaErrores(BigDecimal idpago) {

        IbErroresNominaDetallePjDTO  dto = new IbErroresNominaDetallePjDTO();
        dto.setIbErroresNominaDetallePjList(new ArrayList<IbErroresNominaDetallePj>());        
        dto.setRespuestaDTO(new RespuestaDTO());
        try {
            
            /** La union de la tabla del dia y la del historico
             *  el order by es deacuerdo al requerimiento 
             *   "ordenados de forma descendente a partir 
             *    del campo Fecha de registro del lote"
             **/    
            StringBuilder consulta = new StringBuilder();
            //ORDEN CREADO PARA 
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
            consulta.append("  NOMBRE, ");            
            consulta.append("  DESCRIPCION ");            
            consulta.append("FROM ( ");
            consulta.append("    SELECT cn.*, e.nombre, ec.descripcion  FROM ib_carga_nomina_detalle_pj cn join ib_estatus_pagos_pj e on (cn.estatus=e.id) join ib_errores_nomina_det_pj en on (en.id_pago_detalle=cn.id_pago_detalle) join ib_errores_pj ec on (ec.id=en.id_error)");
            consulta.append("     WHERE cn.id_pago= ?1");
            consulta.append("  UNION ALL ");
            consulta.append("    SELECT cn.*, e.nombre, ec.descripcion  FROM ib_carga_nomina_det_pj_his cn join ib_estatus_pagos_pj e on (cn.estatus=e.id) join ib_errores_nomina_det_pj en on (en.id_pago_detalle=cn.id_pago_detalle) join ib_errores_pj ec on (ec.id=en.id_error)");
            consulta.append("     WHERE cn.id_pago= ?2");
            consulta.append("  ) ");
            consulta.append("  ORDER BY id_pago_detalle ");
            
            Collection listaCargaNominaDetalle = em.createNativeQuery(consulta.toString())
                    .setParameter(1, idpago)
                    .setParameter(2, idpago)
                    .getResultList();   
            
            Iterator interador = listaCargaNominaDetalle.iterator();
            Object[] registro;
            while (interador.hasNext()) {
                registro = (Object[]) interador.next();
                IbCargaNominaDetallePj ibCargaNominaDetallePj = new IbCargaNominaDetallePj(registro);
                IbErroresNominaDetallePj  ibErroresNominaDetallePj = new IbErroresNominaDetallePj();
                ibErroresNominaDetallePj.setIdPagoDetalle(ibCargaNominaDetallePj);
                IbErroresPj ibErroresPj = new IbErroresPj();
                ibErroresPj.setDescripcion((String)registro[13]);
                ibErroresNominaDetallePj.setIdError(ibErroresPj);
                dto.getIbErroresNominaDetallePjList().add(ibErroresNominaDetallePj);                
            }
            
        } catch (NoResultException e) {

        } catch (Exception e) {
            //TODO: definir respuesta incorrecta
            //ibCargaNominaPjDTO.getRespuestaDTO().setCodigo();            
            throw e;
        }
        return dto;
    }    
    
}
