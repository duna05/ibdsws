/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.facade;

import com.bds.ws.dto.IbErroresCargaPagosEspDetPjDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.model.IbCargaPagosEspDetPj;
import com.bds.ws.model.IbErroresPaEspDetPj;
import com.bds.ws.model.IbErroresPj;
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
 * @author robinson.rodriguez
 */
@Named("ibErroresCargaPagosEspDetPjFacade")
@Stateless(name = "wsIbErroresCargaPagosEspDetPjFacade")
public class IbErroresCargaPagosEspDetPjFacade extends AbstractFacade<IbErroresPaEspDetPj> {

    @PersistenceContext(unitName = "ibdsPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IbErroresCargaPagosEspDetPjFacade() {
        super(IbErroresPaEspDetPj.class);
    }

    public IbErroresPaEspDetPj buscarIbErrCargaPagosEspDetPj(BigDecimal idPago) {
        IbErroresPaEspDetPj err_pagos_especiales = null;
        try {
            err_pagos_especiales = (IbErroresPaEspDetPj) getEntityManager().find(IbErroresPaEspDetPj.class, idPago);
        } catch (NoResultException e) {
            // Se debe validar en la clase que llama el metodo
        } catch (Exception e) {
            //TODO: definir respuesta incorrecta            
            throw e;
        }
        return err_pagos_especiales;
    }

    /**
     * CASH410 – Consulta - Lista de pagos para Pagos especiales - error
     *
     * @param idpago id del pago a consultar
     * @return ibCargaPagosEspPjDTO resulset de la consulta
     * @throws java.lang.Exception Error lanzado por el facade
     */
    public IbErroresCargaPagosEspDetPjDTO listarIbErrCargaPagosEspDetPj(BigDecimal idpago) throws Exception {

        IbErroresCargaPagosEspDetPjDTO ibErrCargaPagosEspDetPjDTO = new IbErroresCargaPagosEspDetPjDTO();
        ibErrCargaPagosEspDetPjDTO.setIbErrCargaPagosEspDetPjsList(new ArrayList<IbErroresPaEspDetPj>());
        ibErrCargaPagosEspDetPjDTO.setRespuestaDTO(new RespuestaDTO());
        try {

            /**
             * La union de la tabla del dia y la del historico el order by es
             * deacuerdo al requerimiento "ordenados de forma descendente a
             * partir del campo Fecha de registro del lote"
             *
             */
            StringBuilder consulta = new StringBuilder();
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
            consulta.append("    SELECT cn.*, e.nombre, ec.descripcion  FROM ib_carga_pagos_esp_det_pj cn join ib_estatus_pagos_pj e on (cn.estatus=e.id) join ib_errores_pa_esp_det_pj en on (en.id_pago_detalle=cn.id_pago_detalle) join ib_errores_pj ec on (ec.id=en.id_error)");
            consulta.append("     WHERE cn.id_pago= ?1");
            consulta.append("  UNION ALL ");
            consulta.append("    SELECT cn.*, e.nombre, ec.descripcion  FROM ib_carga_pagos_esp_det_pj_his cn join ib_estatus_pagos_pj e on (cn.estatus=e.id) join ib_errores_pa_esp_det_pj en on (en.id_pago_detalle=cn.id_pago_detalle) join ib_errores_pj ec on (ec.id=en.id_error)");
            consulta.append("     WHERE cn.id_pago= ?1");
            consulta.append("  ) ");
            consulta.append("  ORDER BY id_pago_detalle ");

            Collection listaIbErrCargaPagosEspDetPj = em.createNativeQuery(consulta.toString())
                    .setParameter(1, idpago)
                    .getResultList();
            
            Iterator interador = listaIbErrCargaPagosEspDetPj.iterator();
            Object[] registro;
//            while (interador.hasNext()) {
//                registro = (Object[]) interador.next();
//                ibErrCargaPagosEspDetPjDTO.getIbErrCargaPagosEspDetPjsList().add(new IbErroresPaEspDetPj(registro));
//            }
            
            while (interador.hasNext()) {
                registro = (Object[]) interador.next();
                IbCargaPagosEspDetPj IbCargaPagosEspDetPj = new IbCargaPagosEspDetPj(registro);
                IbErroresPaEspDetPj  ibErroresPaEspDetPj = new IbErroresPaEspDetPj();
                ibErroresPaEspDetPj.setIdPagoDetalle(IbCargaPagosEspDetPj);
                IbErroresPj ibErroresPj = new IbErroresPj();
                ibErroresPj.setDescripcion((String)registro[13]);
                ibErroresPaEspDetPj.setIdError(ibErroresPj);
                ibErrCargaPagosEspDetPjDTO.getIbErrCargaPagosEspDetPjsList().add(ibErroresPaEspDetPj);                
            }
            
            

        } catch (NoResultException e) {

        } catch (Exception e) {
            //TODO: definir respuesta incorrecta
            //ibCargaNominaPjDTO.getRespuestaDTO().setCodigo();            
            throw e;
        }
        return ibErrCargaPagosEspDetPjDTO;
    }

}
