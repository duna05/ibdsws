/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.daoimpl; 

import com.bds.ws.dao.IBBitacoraDAO;
import com.bds.ws.dao.NotificationServiceType;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.facade.IbAfiliacionesFacade;
import com.bds.ws.facade.IbCanalFacade;
import com.bds.ws.facade.IbEmpresasFacade;
import com.bds.ws.facade.IbLogsFacade;
import com.bds.ws.facade.IbLogsPjFacade;
import com.bds.ws.facade.IbServiciosPjFacade;
import com.bds.ws.facade.IbTransaccionesFacade;
import com.bds.ws.facade.IbUsuariosFacade;
import com.bds.ws.facade.IbUsuariosPjFacade;
import com.bds.ws.model.IbLogs;
import com.bds.ws.model.IbLogsPj;
import com.bds.ws.model.IbServiciosPj;
import static com.bds.ws.util.BDSUtil.CODIGO_EXCEPCION_GENERICA;
import static com.bds.ws.util.BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA;
import com.bds.ws.util.DAOUtil;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityExistsException;
import javax.persistence.TransactionRequiredException;
import org.apache.log4j.Logger;

/**
 * Clase que implementa la interfaz IBBitacoraDAO
 * @author juan.faneite
 */
@Named("IBBitacoraDAO")
@Stateless
@NotificationServiceType(NotificationServiceType.ServiceType.SERVICES)
public class IBBitacoraDAOImpl extends DAOUtil implements IBBitacoraDAO {

    @EJB
    IbLogsFacade logsFacade;

    @EJB
    IbUsuariosFacade ibUsuariosFacade;
    
    @EJB
    IbUsuariosPjFacade ibUsuariosPjFacade;
    
    @EJB
    IbEmpresasFacade ibEmpresasFacade;

    @EJB
    IbCanalFacade ibCanalFacade;

    @EJB
    IbTransaccionesFacade ibTransaccionesFacade;

    @EJB
    IbAfiliacionesFacade afiliacionesFacade;
    
    @EJB
    IbLogsPjFacade ibLogsPjFacade; 
    
    @EJB
    IbServiciosPjFacade ibServiciosPjFacade;
    
    /**
     * Log de sistema.
     */
    private static final Logger logger = Logger.getLogger(IBBitacoraDAOImpl.class.getName());

    /**
     * Registro de bitacora Todos los campos son obligatorios.
     *
     * @param cuentaOrigen String Numero de cuenta origen
     * @param cuentaDestino String Numero de cuenta/TDC/Servicio destino
     * @param monto BigDecimal Monto de la transaccion
     * @param referencia String Numero de referencia de la transaccion
     * @param descripcion String Descripcion dada por el usuario a la
     * transaccion
     * @param ip String Direccion ip del usuario
     * @param userAgent String Identificacion del navegador utilizado por el
     * usuario
     * @param errorOperacion BigInteger Canal de ejecucion de la transaccion
     * @param nombreBeneficiario String Nombre del beneficiario
     * @param tipoRif Character Tipo de RIF de una empresa
     * @param rifCedula String Numero de RIF de una empresa
     * @param fechaHoraTx Date Fecha y hora para ejecutar la transaccion
     * @param fechaHoraJob Date Fecha y hora de ejecucion del job
     * @param idUsuario String Referencia foranea al usuario creador de la
     * transaccion
     * @param idTransaccion String Identificador de la transaccion realizada
     * @param idAfiliacion String Referencia foranea al beneficiario
     * @param idCanal String idCanal por el cual se ejecuta la transaccion.
     * @param nombreCanal String nombreCanal por el cual se ejecuta la
     * transaccion.
     * @return RespuestaDTO indica si el metodo se realizo de manera exitosa o no
     */
    @Override
    public RespuestaDTO registroDeBitacora(String cuentaOrigen, String cuentaDestino, String monto, String referencia, String descripcion, String ip,
            String userAgent, String errorOperacion, String nombreBeneficiario, String tipoRif, String rifCedula,
            String fechaHoraTx, String fechaHoraJob, String idCanal, String nombreCanal, String idUsuario, String idTransaccion, String idAfiliacion) {

        RespuestaDTO respuesta = new RespuestaDTO();
        IbLogs ibLogs = new IbLogs();
       /* try {
            ibLogs.setId(BigDecimal.ZERO);
            ibLogs.setIdUsuario(ibUsuariosFacade.find(new BigDecimal(idUsuario)));
            ibLogs.setIdTransaccion(new BigDecimal(idTransaccion));
            ibLogs.setFechaHora(new Date());
            if (cuentaDestino != null && !cuentaDestino.equals("") && !cuentaDestino.isEmpty() ) {
                ibLogs.setCuentaDestino(cuentaDestino);
            }
            if (cuentaOrigen != null && !cuentaOrigen.equals("") && !cuentaOrigen.isEmpty()) {
                ibLogs.setCuentaOrigen(cuentaOrigen);
            }
            if (monto != null && !monto.equals("") && !monto.isEmpty()) {
                ibLogs.setMonto(new BigDecimal(eliminarformatoSimpleMonto(monto)));
            }
            if (referencia != null && !referencia.equals("") && !referencia.isEmpty()) {
                ibLogs.setReferencia(referencia);
            }
            if (descripcion != null && !descripcion.equals("") && !descripcion.isEmpty()) {
                ibLogs.setDescripcion(descripcion);
            }
            ibLogs.setIp(ip);
            ibLogs.setUserAgent(userAgent);
            ibLogs.setIdCanal(ibCanalFacade.find(new BigDecimal(idCanal)));
            if (errorOperacion != null && !errorOperacion.equals("") && !errorOperacion.isEmpty()) {
                ibLogs.setErrorOperacion(new BigInteger(errorOperacion));
            }
            if (idAfiliacion != null && !idAfiliacion.equals("") && !idAfiliacion.isEmpty()) {
                ibLogs.setIdAfiliacion(afiliacionesFacade.find(new BigDecimal(idAfiliacion)));
            }
            if (nombreBeneficiario != null && !nombreBeneficiario.equals("") && !nombreBeneficiario.isEmpty()) {
                ibLogs.setNombreBeneficiario(nombreBeneficiario);
            }
            if (tipoRif != null && !tipoRif.equals("") && !tipoRif.isEmpty()) {
                ibLogs.setTipoDoc(tipoRif.charAt(0));
            }
            if (rifCedula != null && !rifCedula.equals("") && !rifCedula.isEmpty()) {
                ibLogs.setDocumento(rifCedula);
            }
            ibLogs.setFechaHoraTx(this.formatearFechaStringADate(fechaHoraTx, FORMATO_FECHA_COMPLETA));
            ibLogs.setFechaHoraJob(this.formatearFechaStringADate(fechaHoraJob, FORMATO_FECHA_COMPLETA));

            //logsFacade.create(ibLogs);
            respuesta = logsFacade.crear(ibLogs);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new TransactionRequiredException();
            }
        } catch (IllegalArgumentException | EntityExistsException | TransactionRequiredException e) {
            logger.error( new StringBuilder("ERROR JPA EN registroDeBitacora: ")
                    .append("TCI-").append(tipoRif)
                    .append("CI-").append(rifCedula)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo("JPA004");
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN registroDeBitacora: ")
                    .append("TCI-").append(tipoRif)
                    .append("CI-").append(rifCedula)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }
//        if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN registroDeBitacora: ")
//                    .append("TCI-").append(tipoRif)
//                    .append("CI-").append(rifCedula)
//                    .append("-CH-").append(nombreCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }*/

        return respuesta;
    }
    
    /**
     * Registro de bitacora Todos los campos son obligatorios.
     *
     * @param cuentaOrigen String Numero de cuenta origen
     * @param cuentaDestino String Numero de cuenta/TDC/Servicio destino
     * @param monto BigDecimal Monto de la transaccion
     * @param referencia String Numero de referencia de la transaccion
     * @param descripcion String Descripcion dada por el usuario a la
     * transaccion
     * @param ip String Direccion ip del usuario
     * @param userAgent String Identificacion del navegador utilizado por el
     * usuario
     * @param errorOperacion BigInteger Canal de ejecucion de la transaccion
     * @param nombreBeneficiario String Nombre del beneficiario
     * @param tipoRif Character Tipo de RIF de una empresa
     * @param rifCedula String Numero de RIF de una empresa
     * @param fechaHoraTx Date Fecha y hora para ejecutar la transaccion
     * @param fechaHoraJob Date Fecha y hora de ejecucion del job
     * @param idUsuario String Referencia foranea al usuario creador de la  transaccion
     * @param idEmpresa codigo de la empresa en IB
     * @param idAfiliacion String Referencia foranea al beneficiario
     * @param nombreServicio nombre del servicio
     * @param idCanal String idCanal por el cual se ejecuta la transaccion.
     * @param nombreCanal String nombreCanal por el cual se ejecuta la
     * transaccion.
     * @return RespuestaDTO indica si el metodo se realizo de manera exitosa o
     * no
     */
    @Override
    public RespuestaDTO registroDeBitacoraPj(String cuentaOrigen, String cuentaDestino, String monto, String referencia, String descripcion, String ip,
            String userAgent, String errorOperacion, String nombreBeneficiario, String tipoRif, String rifCedula,
            String fechaHoraTx, String fechaHoraJob, String idCanal, String nombreCanal, String idUsuario, String idEmpresa, String nombreServicio, String idAfiliacion) {

        RespuestaDTO respuesta = new RespuestaDTO();
        IbLogsPj ibLogspj = new IbLogsPj();
        try {
            if(idEmpresa != null && !idEmpresa.equals("")){
                ibLogspj.setIdEmpresa(ibEmpresasFacade.find(new BigDecimal(idEmpresa)));
            }
            ibLogspj.setId(BigDecimal.ZERO);
            ibLogspj.setIdUsuarioPj(ibUsuariosPjFacade.find(new BigDecimal(idUsuario)));
            List<IbServiciosPj> servicios = ibServiciosPjFacade.findByNombre(nombreServicio);
            if(!servicios.isEmpty()){
                ibLogspj.setIdServicioPj(servicios.get(0));
            }
            ibLogspj.setFechaHora(new Date());
            if (cuentaDestino != null && !cuentaDestino.equals("") && !cuentaDestino.isEmpty()) {
                ibLogspj.setCuentaDestino(cuentaDestino);
            }
            if (cuentaOrigen != null && !cuentaOrigen.equals("") && !cuentaOrigen.isEmpty()) {
                ibLogspj.setCuentaOrigen(cuentaOrigen);
            }
            if (monto != null && !monto.equals("") && !monto.isEmpty()) {
                ibLogspj.setMonto(new BigDecimal(eliminarformatoSimpleMonto(monto)));
            }
            if (referencia != null && !referencia.equals("") && !referencia.isEmpty()) {
                ibLogspj.setReferencia(referencia);
            }
            if (descripcion != null && !descripcion.equals("") && !descripcion.isEmpty()) {
                ibLogspj.setDescripcion(descripcion);
            }
            ibLogspj.setIp(ip);
            ibLogspj.setUserAgent(userAgent);
            ibLogspj.setIdCanal(ibCanalFacade.find(new BigDecimal(idCanal)));
            if (errorOperacion != null && !errorOperacion.equals("") && !errorOperacion.isEmpty()) {
                ibLogspj.setErrorOperacion(new BigInteger(errorOperacion));
            }
            /*
            if (idAfiliacion != null && !idAfiliacion.equals("") && !idAfiliacion.isEmpty()) {
                ibLogspj.setIdAfiliacion(afiliacionesFacade.find(new BigDecimal(idAfiliacion)));
            }
            */        
            if (nombreBeneficiario != null && !nombreBeneficiario.equals("") && !nombreBeneficiario.isEmpty()) {
                ibLogspj.setNombreBeneficiario(nombreBeneficiario);
            }
            if (tipoRif != null && !tipoRif.equals("") && !tipoRif.isEmpty()) {
                ibLogspj.setTipoDoc(tipoRif.charAt(0));
            }
            if (rifCedula != null && !rifCedula.equals("") && !rifCedula.isEmpty()) {
                ibLogspj.setDocumento(rifCedula);
            }
            ibLogspj.setFechaHoraTx(this.formatearFechaStringADate(fechaHoraTx, FORMATO_FECHA_COMPLETA));
            ibLogspj.setFechaHoraJob(this.formatearFechaStringADate(fechaHoraJob, FORMATO_FECHA_COMPLETA));

            respuesta = ibLogsPjFacade.crear(ibLogspj);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new TransactionRequiredException();
            }
        } catch (IllegalArgumentException | EntityExistsException | TransactionRequiredException e) {
            logger.error( new StringBuilder("ERROR JPA EN registroDeBitacoraPj: ")
                    .append("TCI-").append(tipoRif)
                    .append("CI-").append(rifCedula)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString(), e);
            respuesta.setCodigo("JPA004");
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN registroDeBitacoraPj: ")
                    .append("TCI-").append(tipoRif)
                    .append("CI-").append(rifCedula)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString(), e);
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }
        return respuesta;
    }
}