/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.facade;

import com.bds.ws.dto.IbCargaPagosEspPjDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.model.IbCargaPagosEspPj;
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
 * @author robinson.rodriguez
 */
@Named("ibCargaPagosEspPjFacade")
@Stateless(name = "wsIbCargaPagosEspPjFacade")
public class IbCargaPagosEspPjFacade extends AbstractFacade<IbCargaPagosEspPj> {

    @PersistenceContext(unitName = "ibdsPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IbCargaPagosEspPjFacade() {
        super(IbCargaPagosEspPj.class);
    }

    public IbCargaPagosEspPj buscarIbCargaPagosEspPj(Long idPago) {
        IbCargaPagosEspPj pagos_especiales = null;
        try {
            pagos_especiales = (IbCargaPagosEspPj) getEntityManager().find(IbCargaPagosEspPj.class, idPago);
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
    public IbCargaPagosEspPjDTO listarIbCargaPagosEspPj(BigDecimal cdClienteAbanks, Short estatusCarga) throws Exception {
        IbCargaPagosEspPjDTO ibCargaPagosEspPjDTO = new IbCargaPagosEspPjDTO();
        ibCargaPagosEspPjDTO.setIbCargaPagosEspPjsList(new ArrayList<IbCargaPagosEspPj>());
        ibCargaPagosEspPjDTO.setRespuestaDTO(new RespuestaDTO());
        try {

            /**
             * La union de la tabla del dia y la del historico el order by es
             * deacuerdo al requerimiento "ordenados de forma descendente a
             * partir del campo Fecha de registro del lote"
             *
             */
            StringBuilder consulta = new StringBuilder();
            consulta.append(" SELECT ID_PAGO, CODIGO_CLIENTE_ABANKS, CODIGO_USUARIO_CARGA,NOMBRE_ARCHIVO,MOTIVO_DE_PAGO,MONTO_TOTAL_APLICAR,CANTIDAD_CREDITOS_APLICAR,NRO_CUENTA_DEBITO, ESTATUS,MONTO_TOTAL_APLICADO,CANTIDAD_CREDITOS_APLICADOS,CANTIDAD_CREDITOS_RECHAZADOS,CANTIDAD_APROBADORES_SERVICIO,SECUENCIA_CUMPLIDA,FECHA_HORA_CARGA,FECHA_HORA_PAGO,FECHA_HORA_MODIFICACION, NOMBRE FROM ( ");
            consulta.append("    SELECT cn.ID_PAGO, cn.CODIGO_CLIENTE_ABANKS, cn.CODIGO_USUARIO_CARGA,cn.NOMBRE_ARCHIVO,cn.MOTIVO_DE_PAGO,cn.MONTO_TOTAL_APLICAR,cn.CANTIDAD_CREDITOS_APLICAR,cn.NRO_CUENTA_DEBITO, ESTATUS,cn.MONTO_TOTAL_APLICADO,cn.CANTIDAD_CREDITOS_APLICADOS,cn.CANTIDAD_CREDITOS_RECHAZADOS,cn.CANTIDAD_APROBADORES_SERVICIO,cn.SECUENCIA_CUMPLIDA,cn.FECHA_HORA_CARGA,cn.FECHA_HORA_PAGO,cn.FECHA_HORA_MODIFICACION, e.NOMBRE ");
            consulta.append("    FROM ib_carga_pagos_esp_pj cn join ib_estatus_pagos_pj e on (cn.estatus=e.id)");
            consulta.append("     WHERE cn.CODIGO_CLIENTE_ABANKS = ?1");
            consulta.append("  UNION ALL ");
            consulta.append("    SELECT cn.ID_PAGO, cn.CODIGO_CLIENTE_ABANKS, cn.CODIGO_USUARIO_CARGA,cn.NOMBRE_ARCHIVO,cn.MOTIVO_DE_PAGO,cn.MONTO_TOTAL_APLICAR,cn.CANTIDAD_CREDITOS_APLICAR,cn.NRO_CUENTA_DEBITO, ESTATUS,cn.MONTO_TOTAL_APLICADO,cn.CANTIDAD_CREDITOS_APLICADOS,cn.CANTIDAD_CREDITOS_RECHAZADOS,cn.CANTIDAD_APROBADORES_SERVICIO,cn.SECUENCIA_CUMPLIDA,cn.FECHA_HORA_CARGA,cn.FECHA_HORA_PAGO,cn.FECHA_HORA_MODIFICACION, e.NOMBRE ");
            consulta.append("    FROM ib_carga_pagos_esp_pj_his cn join ib_estatus_pagos_pj e on (cn.estatus=e.id)");
            consulta.append("     WHERE cn.CODIGO_CLIENTE_ABANKS = ?1");
            consulta.append("  ) ");

            if (estatusCarga != null) {
                consulta.append(" WHERE ESTATUS = ?2 ");
            } else {
                consulta.append(" WHERE ESTATUS >= 1 "); //MAYORES A PRECARGADO
            }

            consulta.append("  ORDER BY FECHA_HORA_CARGA DESC, ID_PAGO DESC ");

            Collection listaIbCargaPagosEspPj;
            if (estatusCarga != null) {
                listaIbCargaPagosEspPj = em.createNativeQuery(consulta.toString())
                        .setParameter(1, cdClienteAbanks)
                        .setParameter(2, estatusCarga)
                        .getResultList();
            } else {
                listaIbCargaPagosEspPj = em.createNativeQuery(consulta.toString())
                        .setParameter(1, cdClienteAbanks)
                        .getResultList();
            }

            Iterator interador = listaIbCargaPagosEspPj.iterator();
            Object[] registro;
            while (interador.hasNext()) {
                registro = (Object[]) interador.next();
                ibCargaPagosEspPjDTO.getIbCargaPagosEspPjsList().add(new IbCargaPagosEspPj(registro));
            }

        } catch (NoResultException e) {

        } catch (Exception e) {
            //TODO: definir respuesta incorrecta
            //ibCargaNominaPjDTO.getRespuestaDTO().setCodigo();            
            throw e;
        }
        return ibCargaPagosEspPjDTO;
    }

    public Map<String, String> listarErroresPagosEspecialesCargaMasiva() throws Exception {

        Map<String, String> mapErroresPagosEspecialesCargaMasiva = new HashMap();

        try {

            StringBuilder consulta = new StringBuilder();
            
            consulta.append("SELECT  CODIGO, MENSAJE_USUARIO ");
            consulta.append("  FROM ib_textos ");
            consulta.append(" WHERE CODIGO LIKE 'pjw.cargaMasPagosEspeciales.error.archivo%'");

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
    
    public Boolean archivoPagosEspecialesCargaMasivaExiste(Long codeClient, String filename) throws Exception {
        try{

            StringBuilder consulta = new StringBuilder();            
            consulta.append("SELECT count(i) ");
            consulta.append("  FROM IbCargaPagosEspPj i ");
            consulta.append(" WHERE i.codigoClienteAbanks = :codigoClienteAbanksP ");
            consulta.append("   AND i.nombreArchivo = :nombreArchivoP ");            
            consulta.append("   AND i.estatus.id > 0 ");//ya esta procesado

            long count = (long) em.createQuery(consulta.toString())
                .setParameter("nombreArchivoP", filename)
                .setParameter("codigoClienteAbanksP", codeClient)
                .getSingleResult();

            return (count > 0);
         
        }catch(Exception ex){
            //respuestaDTO.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log            
            throw ex;
        }

    }
    
    /**
     * Devuelve el listado autorizado para un usuario
     * @param cdClienteAbanks
     * @param idUsuarioAurorizado
     * @param idServicio
     * @param StatusAConsultar
     * @return
     * @throws Exception 
     */
    public IbCargaPagosEspPjDTO listarIbCargaPagosEspAutorizarPj(BigDecimal cdClienteAbanks, Long idUsuarioAurorizado,Long idServicio, String StatusAConsultar) throws Exception {

        IbCargaPagosEspPjDTO ibCargaPagosEspPjDTO = new IbCargaPagosEspPjDTO();
        ibCargaPagosEspPjDTO.setIbCargaPagosEspPjsList(new ArrayList<IbCargaPagosEspPj>());
        ibCargaPagosEspPjDTO.setRespuestaDTO(new RespuestaDTO());
        
        List<IbCargaPagosEspPj> ibCargaPagosEspPjList = null;
        
        try {
            StringBuilder consulta = new StringBuilder();
            consulta.append(" SELECT distinct b FROM IbCargaPagosEspPj  b");
            consulta.append(" INNER JOIN FETCH b.ibCargaPagosEspDetPjCollection bar");
            consulta.append(" WHERE bar.codigoClienteAbanks =:cdClienteAbanks and (bar.estatus.id=1 or  bar.estatus.id=2 or  bar.estatus.id=3)" );
            consulta.append(" and NOT EXISTS (SELECT p FROM IbSecuenciaAprobacionPj p" );
            consulta.append("  WHERE p.idTransaccion = bar.idPagoDetalle and" );  
            consulta.append("  p.idServicioPj.id =:idServicioPj and p.idUsuarioPj.id =:idUsuarioAurorizado)" );  
         
            
            ibCargaPagosEspPjList = (List<IbCargaPagosEspPj>) em.createQuery(consulta.toString())
                   .setParameter("cdClienteAbanks", cdClienteAbanks)
                   .setParameter("idUsuarioAurorizado", new BigDecimal(idUsuarioAurorizado))
                    .setParameter("idServicioPj", new BigDecimal(idServicio))
                    .getResultList();


        } catch (Exception e) {
            throw e;
        }
        ibCargaPagosEspPjDTO.setIbCargaPagosEspPjsList(ibCargaPagosEspPjList);

        return ibCargaPagosEspPjDTO;
    }
    
    /**
     * CASH410 – Consulta - Lista de pagos para Pagos especiales
     *
     * @param cdClienteAbanks Código de cliente de identificación en el core bancario 
     * @param idPago indice asociado al pago realizado por una empresa
     * @param fechaInicio dato que indica desde cuando es el rango de fecha a consultar 
     * @param fechaInicio dato que indica hasta cuando es el rango de fecha a consultar 
     * @return ibCargaPagosEspPjDTO resulset de la consulta
     * 
     * @throws java.lang.Exception Error lanzado por el facade
     */
    public IbCargaPagosEspPjDTO buscarIbCargaPorParametrosPagosEspPj(BigDecimal clienteAbanks,Long idPago,Date  fechaInicio, Date fechaFin ) throws Exception {
        IbCargaPagosEspPjDTO ibCargaPagosEspPjDTO = new IbCargaPagosEspPjDTO();
        ibCargaPagosEspPjDTO.setIbCargaPagosEspPjsList(new ArrayList<IbCargaPagosEspPj>());
        ibCargaPagosEspPjDTO.setRespuestaDTO(new RespuestaDTO());
        Boolean flagFecha=Boolean.FALSE;
        List<IbCargaPagosEspPj> ibCargaPagosEspPjList = null;
        
        try {
            StringBuilder consulta = new StringBuilder();
            consulta.append(" SELECT  b FROM IbCargaPagosEspPj  b ");
            consulta.append("  WHERE b.codigoClienteAbanks = :cdClienteAbanks" ); 
            
           if(!(fechaInicio==null || fechaFin==null)){
               flagFecha=Boolean.TRUE;
              }
            
                if( flagFecha == Boolean.TRUE && (idPago==0 )){
                  
                    consulta.append(" AND b.fechaHoraCarga BETWEEN :startDate AND :endDate"); 
                    ibCargaPagosEspPjList = (List<IbCargaPagosEspPj>) em.createQuery(consulta.toString())
                   .setParameter("cdClienteAbanks", clienteAbanks)
                   .setParameter("startDate", fechaInicio, TemporalType.DATE)  
                   .setParameter("endDate", fechaFin, TemporalType.DATE)  
                   .getResultList();  
                }else if(idPago>0 && flagFecha == Boolean.FALSE){
                    consulta.append(" AND b.idPago ="+idPago+" ");
                    ibCargaPagosEspPjList = (List<IbCargaPagosEspPj>) em.createQuery(consulta.toString())
                    .setParameter("cdClienteAbanks", clienteAbanks)   
                    .getResultList(); 
                
                }else if(flagFecha == Boolean.TRUE && (idPago>0 )){
                    consulta.append(" AND b.fechaHoraCarga BETWEEN :startDate AND :endDate"); 
                    consulta.append(" AND b.idPago ="+idPago+" ");
                    ibCargaPagosEspPjList = (List<IbCargaPagosEspPj>) em.createQuery(consulta.toString())
                   .setParameter("cdClienteAbanks", clienteAbanks)
                   .setParameter("startDate", fechaInicio, TemporalType.DATE)  
                   .setParameter("endDate", fechaFin, TemporalType.DATE)  
                   .getResultList();
        
            }    
            
           

        } catch (Exception e) {
            throw e;
        }
        ibCargaPagosEspPjDTO.setIbCargaPagosEspPjsList(ibCargaPagosEspPjList);

        return ibCargaPagosEspPjDTO;
    }

}
