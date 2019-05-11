/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.facade;

import com.bds.ws.dto.IbCargaPagosEspDetPjDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.model.IbCargaPagosEspDetPj;
import com.bds.ws.model.IbEstatusPagosPj;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
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
@Named("ibCargaPagosEspDetPjFacade")
@Stateless(name = "wsIbCargaPagosEspDetPjFacade")
public class IbCargaPagosEspDetPjFacade extends AbstractFacade<IbCargaPagosEspDetPj> {
    @EJB
    private IbEstatusPagosPjFacade ibEstatusPagosPjFacade;
    
    @PersistenceContext(unitName = "ibdsPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IbCargaPagosEspDetPjFacade() {
        super(IbCargaPagosEspDetPj.class);
    }

    public IbCargaPagosEspDetPj buscarIbCargaPagosEspDetPj(BigDecimal idPago) {
        IbCargaPagosEspDetPj pagos_especiales = null;
        try {
            pagos_especiales = (IbCargaPagosEspDetPj) getEntityManager().find(IbCargaPagosEspDetPj.class, idPago);
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
    public IbCargaPagosEspDetPjDTO listarIbCargaPagosEspDetPj(BigDecimal idpago, Short estatusCarga) throws Exception {

        IbCargaPagosEspDetPjDTO ibCargaPagosEspDetPjDTO = new IbCargaPagosEspDetPjDTO();
        ibCargaPagosEspDetPjDTO.setIbCargaPagosEspDetPjsList(new ArrayList<IbCargaPagosEspDetPj>());
        ibCargaPagosEspDetPjDTO.setRespuestaDTO(new RespuestaDTO());
        try {

            /**
             * La union de la tabla del dia y la del historico el order by es
             * deacuerdo al requerimiento "ordenados de forma descendente a
             * partir del campo Fecha de registro del lote"
             *
             */
            StringBuilder consulta = new StringBuilder();
            consulta.append(" SELECT ID_PAGO_DETALLE, CODIGO_CLIENTE_ABANKS, ID_PAGO, NRO_LINEA_ARCHIVO, NRO_IDENTIFICACION_CLIENTE, NRO_CUENTA_BENEFICIARIO, MONTO_PAGO, ESTATUS, CODIGO_MOTIVO_RECHAZO, NOMBRE_BENEFICIARIO, FECHA_HORA_CARGA, FECHA_HORA_MODIFICACION, NOMBRE FROM ( ");
            consulta.append("    SELECT cn.ID_PAGO_DETALLE, cn.CODIGO_CLIENTE_ABANKS, cn.ID_PAGO, cn.NRO_LINEA_ARCHIVO, cn.NRO_IDENTIFICACION_CLIENTE, cn.NRO_CUENTA_BENEFICIARIO, cn.MONTO_PAGO, cn.ESTATUS, cn.CODIGO_MOTIVO_RECHAZO, cn.NOMBRE_BENEFICIARIO, cn.FECHA_HORA_CARGA, cn.FECHA_HORA_MODIFICACION, e.NOMBRE ");
            consulta.append("    FROM ib_carga_pagos_esp_det_pj cn join ib_estatus_pagos_pj e on (cn.estatus=e.id)");
            consulta.append("     WHERE cn.ID_PAGO = ?1");
            consulta.append("  UNION ALL ");
            consulta.append("    SELECT cn.ID_PAGO_DETALLE, cn.CODIGO_CLIENTE_ABANKS, cn.ID_PAGO, cn.NRO_LINEA_ARCHIVO, cn.NRO_IDENTIFICACION_CLIENTE, cn.NRO_CUENTA_BENEFICIARIO, cn.MONTO_PAGO, cn.ESTATUS, cn.CODIGO_MOTIVO_RECHAZO, cn.NOMBRE_BENEFICIARIO, cn.FECHA_HORA_CARGA, cn.FECHA_HORA_MODIFICACION, e.NOMBRE ");
            consulta.append("    FROM ib_carga_pagos_esp_det_pj_his cn join ib_estatus_pagos_pj e on (cn.estatus=e.id)");
            consulta.append("     WHERE cn.ID_PAGO = ?1");
            consulta.append("  ) ");

            if (estatusCarga != null) {
                consulta.append(" WHERE ESTATUS = ?2 ");
            } else {
                consulta.append(" WHERE ESTATUS >= 1 "); //MAYORES A PRECARGADO
            }

            consulta.append("  ORDER BY FECHA_HORA_CARGA DESC, ID_PAGO_DETALLE DESC ");

            Collection listaIbCargaPagosEspDetPj;
            if (estatusCarga != null) {
                listaIbCargaPagosEspDetPj = em.createNativeQuery(consulta.toString())
                        .setParameter(1, idpago)
                        .setParameter(2, estatusCarga)
                        .getResultList();
            } else {
                listaIbCargaPagosEspDetPj = em.createNativeQuery(consulta.toString())
                        .setParameter(1, idpago)
                        .getResultList();
            }

            Iterator interador = listaIbCargaPagosEspDetPj.iterator();
            Object[] registro;
            while (interador.hasNext()) {
                registro = (Object[]) interador.next();
                ibCargaPagosEspDetPjDTO.getIbCargaPagosEspDetPjsList().add(new IbCargaPagosEspDetPj(registro));
            }

        } catch (NoResultException e) {

        } catch (Exception e) {
            //TODO: definir respuesta incorrecta
            //ibCargaNominaPjDTO.getRespuestaDTO().setCodigo();            
            throw e;
        }
        return ibCargaPagosEspDetPjDTO;
    }

    public int modificarEstatusDetalle(Long idPago, BigDecimal estatus) {
        int cantActualizados = 0;

        StringBuilder queryUpdate = new StringBuilder();
        queryUpdate.append("UPDATE ib_carga_pagos_esp_det_pj D ")
                .append(" SET D.estatus = ? ")
                .append(" WHERE D.id_pago = ? ");
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

    
    public IbCargaPagosEspDetPj actualizarEstatusPagosEspecialesDetPorId(BigDecimal idPagoEspDet, BigDecimal idEstatus) throws Exception {
        IbCargaPagosEspDetPj ibCargaPagosEspDetPj = null;
        IbEstatusPagosPj estatus = null;
        try {
            ibCargaPagosEspDetPj = (IbCargaPagosEspDetPj) getEntityManager().find(IbCargaPagosEspDetPj.class, idPagoEspDet.longValue());
            if(ibCargaPagosEspDetPj!=null){
                estatus = this.ibEstatusPagosPjFacade.buscarIbEstatusPj(idEstatus);
                ibCargaPagosEspDetPj.setEstatus(estatus);
                ibCargaPagosEspDetPj = (IbCargaPagosEspDetPj) getEntityManager().merge(ibCargaPagosEspDetPj);
            }
              
        } catch (NoResultException e) {
            // Se debe validar en la clase que llama el metodo
        } catch (Exception e) {
            //TODO: definir respuesta incorrecta            
            throw e;
        }
        return ibCargaPagosEspDetPj;
    }

}
