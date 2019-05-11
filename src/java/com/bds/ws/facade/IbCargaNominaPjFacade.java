/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.facade;

import com.bds.ws.dto.IbCargaNominaPjDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.model.IbCargaNominaPj;
import com.bds.ws.model.IbEstatusPagosPj;
import com.bds.ws.model.IbTextos;
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
 * @author juan.vasquez
 */
@Named("ibCargaNominaPjFacade")
@Stateless(name = "wsIbCargaNominaPjFacade")
public class IbCargaNominaPjFacade extends AbstractFacade<IbCargaNominaPj> {

    @PersistenceContext(unitName = "ibdsPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IbCargaNominaPjFacade() {
        super(IbCargaNominaPj.class);
    }

    public IbCargaNominaPj buscarIbCargaNominaPj(BigDecimal idPago) {
        IbCargaNominaPj nomina = null;
        try {
            nomina = (IbCargaNominaPj) getEntityManager().find(IbCargaNominaPj.class, idPago);
        } catch (NoResultException e) {
            // Se debe validar en la clase que llama el metodo
        } catch (Exception e) {
            //TODO: definir respuesta incorrecta            
            throw e;
        }
        return nomina;
    }

    public IbEstatusPagosPj buscarIbEstatusPagosPj(String idEstatus) {
        IbEstatusPagosPj estatus = null;
        BigDecimal idEstatuss = (new BigDecimal((String) idEstatus));

        try {
            estatus = (IbEstatusPagosPj) getEntityManager().find(IbEstatusPagosPj.class, idEstatuss);
        } catch (NoResultException e) {
            // Se debe validar en la clase que llama el metodo
        } catch (Exception e) {
            //TODO: definir respuesta incorrecta            
            throw e;
        }
        return estatus;
    }

    /**
     * PJW-CASH330 – Consulta – Nomina, Consulta Carga Masiva - Cabeceras
     *
     * @param cdClienteAbanks
     * @param estatusCarga
     * @return IbCargaNominaPjDTO
     * @throws java.lang.Exception
     */
    public IbCargaNominaPjDTO listarCargaNomina(BigDecimal cdClienteAbanks, Short estatusCarga) throws Exception {

        IbCargaNominaPjDTO ibCargaNominaPjDTO = new IbCargaNominaPjDTO();
        ibCargaNominaPjDTO.setIbCargaNominaPjsList(new ArrayList<IbCargaNominaPj>());
        ibCargaNominaPjDTO.setRespuestaDTO(new RespuestaDTO());
        try {

            /**
             * La union de la tabla del dia y la del historico el order by es
             * deacuerdo al requerimiento "ordenados de forma descendente a
             * partir del campo Fecha de registro del lote"
             *
             */
            StringBuilder consulta = new StringBuilder();
            //orden usado el el model IbCargaNominaPj
            consulta.append("SELECT  ");
            consulta.append(" ID_PAGO,");
            consulta.append(" CODIGO_CLIENTE_ABANKS,");
            consulta.append(" CODIGO_USUARIO_CARGA,");
            consulta.append(" NRO_CONVENIO,");
            consulta.append(" NOMBRE_ARCHIVO,");
            consulta.append(" MOTIVO_DE_PAGO,");
            consulta.append(" MONTO_TOTAL_APLICAR,");
            consulta.append(" CANTIDAD_CREDITOS_APLICAR,");
            consulta.append(" NRO_CUENTA_DEBITO,");
            consulta.append(" ESTATUS,");
            consulta.append(" MONTO_TOTAL_APLICADO,");
            consulta.append(" CANTIDAD_CREDITOS_APLICADOS,");
            consulta.append(" CANTIDAD_CREDITOS_RECHAZADOS,");
            consulta.append(" CANTIDAD_APROBADORES_SERVICIO,");
            consulta.append(" SECUENCIA_CUMPLIDA,");
            consulta.append(" FECHA_HORA_CARGA,");
            consulta.append(" FECHA_HORA_PAGO,");
            consulta.append(" FECHA_HORA_MODIFICACION,");
            consulta.append(" TIPO_CARGA_NOMINA,");
            consulta.append(" NOMBRE ");
            consulta.append("FROM ( ");
            consulta.append("    SELECT cn.*, e.nombre FROM ib_carga_nomina_pj cn join ib_estatus_pagos_pj e on (cn.estatus=e.id)");
            consulta.append("     WHERE cn.CODIGO_CLIENTE_ABANKS = ?1");
            consulta.append("  UNION ALL ");
            consulta.append("    SELECT ch.*, e.nombre FROM ib_carga_nomina_pj_his ch join ib_estatus_pagos_pj e on (ch.estatus=e.id)");
            consulta.append("     WHERE ch.CODIGO_CLIENTE_ABANKS = ?1");
            consulta.append("  ) ");

            if (estatusCarga != null) {
                consulta.append(" WHERE ESTATUS = ?2 ");
            } else {
                consulta.append(" WHERE ESTATUS >= 1 "); //MAYORES A PRECARGADO
            }

            consulta.append(" ORDER BY FECHA_HORA_CARGA DESC, ID_PAGO DESC ");

            Collection listaCargaNomina;
            if (estatusCarga != null) {
                listaCargaNomina = em.createNativeQuery(consulta.toString())
                        .setParameter(1, cdClienteAbanks)
                        .setParameter(2, estatusCarga)
                        .getResultList();
            } else {
                listaCargaNomina = em.createNativeQuery(consulta.toString())
                        .setParameter(1, cdClienteAbanks)
                        .getResultList();
            }

            Iterator interador = listaCargaNomina.iterator();
            Object[] registro;
            while (interador.hasNext()) {
                registro = (Object[]) interador.next();
                ibCargaNominaPjDTO.getIbCargaNominaPjsList().add(new IbCargaNominaPj(registro));
            }

        } catch (NoResultException e) {

        } catch (Exception e) {
            //TODO: definir respuesta incorrecta
            //ibCargaNominaPjDTO.getRespuestaDTO().setCodigo();            
            throw e;
        }
        return ibCargaNominaPjDTO;
    }

    
   
 
    
    /**
     * PJW-CASH330 – Consulta – Nomina, Consulta Carga Masiva - Cabeceras
     *
     * @param cdClienteAbanks
     * @param idUsuarioAurorizado
     * @param estatusCarga
     * @return IbCargaNominaPjDTO
     * @throws java.lang.Exception
     */
     public BigDecimal convertidor(String Numero) {
        Numero = Numero.replace(",", ".");
        BigDecimal montoBigdecimal = (new BigDecimal((String) Numero));
        return montoBigdecimal;
    }
     //IbCargaNominaDetallePj;
    public IbCargaNominaPjDTO listarCargaNominaAutorizacion(BigDecimal cdClienteAbanks, Long idUsuarioAurorizado,Long idServicio, String StatusAConsultar) throws Exception {

        IbCargaNominaPjDTO ibCargaNominaPjDTO = new IbCargaNominaPjDTO();
        ibCargaNominaPjDTO.setIbCargaNominaPjsList(new ArrayList<IbCargaNominaPj>());
        ibCargaNominaPjDTO.setRespuestaDTO(new RespuestaDTO());
        
        List<IbCargaNominaPj> ibCargaNominaPjList = null;   
        
        try {
            StringBuilder consulta = new StringBuilder();
            consulta.append(" SELECT distinct b FROM IbCargaNominaPj  b");
            consulta.append(" INNER JOIN FETCH b.ibCargaNominaDetallePjCollection bar");
            consulta.append(" WHERE bar.codigoClienteAbanks =:id and  (bar.estatus.id=1 or bar.estatus.id=2 or bar.estatus.id=3)" );
            consulta.append(" and NOT EXISTS (SELECT p FROM IbSecuenciaAprobacionPj p" );
            consulta.append("  WHERE p.idTransaccion = bar.idPagoDetalle and" );  
            consulta.append("  p.idServicioPj.id =:idServicioPj and p.idUsuarioPj.id =:idUsuarioAurorizado)" );  
         
            
            ibCargaNominaPjList = (List<IbCargaNominaPj>) em.createQuery(consulta.toString())
                   .setParameter("id", cdClienteAbanks)
                   .setParameter("idUsuarioAurorizado", new BigDecimal(idUsuarioAurorizado))
                    .setParameter("idServicioPj", new BigDecimal(idServicio))
                    .getResultList();


        } catch (Exception e) {
            throw e;
        }
        ibCargaNominaPjDTO.setIbCargaNominaPjsList(ibCargaNominaPjList);

        return ibCargaNominaPjDTO;
    }

    public Map<String, String> listarErrorresNominaCargaMasiva() throws Exception {

        Map<String, String> mapErrorresNominaCargaMasiva = new HashMap();

        try {

            int tmpCargaMasiva = 0;
            StringBuilder consulta = new StringBuilder();
            //orden usado el el model IbCargaNominaPj
            consulta.append("SELECT  t ");
            consulta.append("  FROM IbTextos t");
            consulta.append(" WHERE t.codigo LIKE 'pjw.nominaCargaMasiva.error%'");

            Collection listaCargaNomina = em.createQuery(consulta.toString())
                    .getResultList();

            Iterator interador = listaCargaNomina.iterator();
            IbTextos ibTexto;
            while (interador.hasNext()) {
                ibTexto = (IbTextos) interador.next();
                mapErrorresNominaCargaMasiva.put(ibTexto.getCodigo(), ibTexto.getMensajeUsuario());
            }

        } catch (NoResultException e) {

        } catch (Exception e) {
            throw e;
        }
        return mapErrorresNominaCargaMasiva;
    }

    public Boolean archivoNominaCargaMasivaExiste(String codigoClienteAbanks, String filename) throws Exception {
        try {

            StringBuilder consulta = new StringBuilder();
            consulta.append("SELECT count(i) ");
            consulta.append("  FROM IbCargaNominaPj i ");
            consulta.append(" WHERE i.codigoClienteAbanks = :codigoClienteAbanks ");
            consulta.append("   AND i.nombreArchivo = :nombreArchivo ");
            consulta.append("   AND i.estatus.id > 0 ");//ya esta procesado

            long count = (long) em.createQuery(consulta.toString())
                    .setParameter("nombreArchivo", filename)
                    .setParameter("codigoClienteAbanks", new BigDecimal(codigoClienteAbanks))
                    .getSingleResult();

            return (count > 0);

        } catch (Exception ex) {
            //respuestaDTO.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log            
            throw ex;
        }
    }
    
    public IbCargaNominaPjDTO listarCargaNominaPjAdmin(BigDecimal clienteAbanks,Long idPago,Date fechaInicio, Date fechaFin) throws Exception {

        IbCargaNominaPjDTO ibCargaNominaPjDTO = new IbCargaNominaPjDTO();
        ibCargaNominaPjDTO.setIbCargaNominaPjsList(new ArrayList<IbCargaNominaPj>());
        ibCargaNominaPjDTO.setRespuestaDTO(new RespuestaDTO());
        
        List<IbCargaNominaPj> ibCargaNominaPjList = null;   
        //
        Boolean flagFecha=Boolean.FALSE;
        
      
        try {
            StringBuilder consulta = new StringBuilder(); 
             
            consulta.append(" SELECT  b FROM IbCargaNominaPj  b ");
            consulta.append("  WHERE b.codigoClienteAbanks = :cdClienteAbanks" ); 
              if(!(fechaInicio==null || fechaFin==null)){
               flagFecha=Boolean.TRUE;
              }
            
                if( flagFecha == Boolean.TRUE && (idPago==0 )){
                  
                    consulta.append(" AND b.fechaHoraCarga BETWEEN :startDate AND :endDate"); 
                    ibCargaNominaPjList = (List<IbCargaNominaPj>) em.createQuery(consulta.toString())
                   .setParameter("cdClienteAbanks", clienteAbanks)
                   .setParameter("startDate", fechaInicio, TemporalType.DATE)  
                   .setParameter("endDate", fechaFin, TemporalType.DATE)  
                   .getResultList();  
                }else if(idPago>0 && flagFecha == Boolean.FALSE){
                    consulta.append(" AND b.idPago ="+idPago+" ");
                    ibCargaNominaPjList = (List<IbCargaNominaPj>) em.createQuery(consulta.toString())
                    .setParameter("cdClienteAbanks", clienteAbanks)   
                    .getResultList(); 
                
                }else if(flagFecha == Boolean.TRUE && (idPago>0 )){
                    consulta.append(" AND b.fechaHoraCarga BETWEEN :startDate AND :endDate"); 
                    consulta.append(" AND b.idPago ="+idPago+" ");
                    ibCargaNominaPjList = (List<IbCargaNominaPj>) em.createQuery(consulta.toString())
                   .setParameter("cdClienteAbanks", clienteAbanks)
                   .setParameter("startDate", fechaInicio, TemporalType.DATE)  
                   .setParameter("endDate", fechaFin, TemporalType.DATE)  
                   .getResultList();
        
            }
        } catch (Exception e) {
            throw e;
        }
        ibCargaNominaPjDTO.setIbCargaNominaPjsList(ibCargaNominaPjList);

        return ibCargaNominaPjDTO;
    }
    
    public IbCargaNominaPjDTO listarCargaNominaAutorizacion(BigDecimal clienteAbanks,Long idPago,Date fechaInicio, Date fechaFin) throws Exception {

        IbCargaNominaPjDTO ibCargaNominaPjDTO = new IbCargaNominaPjDTO();
        ibCargaNominaPjDTO.setIbCargaNominaPjsList(new ArrayList<IbCargaNominaPj>());
        ibCargaNominaPjDTO.setRespuestaDTO(new RespuestaDTO());
        Boolean flagFecha=Boolean.FALSE;
        List<IbCargaNominaPj> ibCargaNominaPjList = null;   
        
        try {
            
            StringBuilder consulta = new StringBuilder(); 
             
            consulta.append(" SELECT  b FROM IbCargaNominaPj  b ");
            consulta.append("  WHERE b.codigoClienteAbanks = :cdClienteAbanks" ); 
              if(!(fechaInicio==null || fechaFin==null)){
               flagFecha=Boolean.TRUE;
              }
            
                if( flagFecha == Boolean.TRUE && (idPago==0 )){
                  
                    consulta.append(" AND b.fechaHoraCarga BETWEEN :startDate AND :endDate"); 
                    ibCargaNominaPjList = (List<IbCargaNominaPj>) em.createQuery(consulta.toString())
                   .setParameter("cdClienteAbanks", clienteAbanks)
                   .setParameter("startDate", fechaInicio, TemporalType.DATE)  
                   .setParameter("endDate", fechaFin, TemporalType.DATE)  
                   .getResultList();  
                }else if(idPago>0 && flagFecha == Boolean.FALSE){
                    consulta.append(" AND b.idPago ="+idPago+" ");
                    ibCargaNominaPjList = (List<IbCargaNominaPj>) em.createQuery(consulta.toString())
                    .setParameter("cdClienteAbanks", clienteAbanks)   
                    .getResultList(); 
                
                }else if(flagFecha == Boolean.TRUE && (idPago>0 )){
                    consulta.append(" AND b.fechaHoraCarga BETWEEN :startDate AND :endDate"); 
                    consulta.append(" AND b.idPago ="+idPago+" ");
                    ibCargaNominaPjList = (List<IbCargaNominaPj>) em.createQuery(consulta.toString())
                   .setParameter("cdClienteAbanks", clienteAbanks)
                   .setParameter("startDate", fechaInicio, TemporalType.DATE)  
                   .setParameter("endDate", fechaFin, TemporalType.DATE)  
                   .getResultList();
        
            }

        } catch (Exception e) {
            throw e;
        }
        ibCargaNominaPjDTO.setIbCargaNominaPjsList(ibCargaNominaPjList);

        return ibCargaNominaPjDTO;
    }

}
