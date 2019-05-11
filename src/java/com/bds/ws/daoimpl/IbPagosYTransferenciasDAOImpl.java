/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.daoimpl;

import com.bds.ws.dao.IbPagosYTransferenciasDAO;
import com.bds.ws.dao.NotificationServiceType;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.facade.IbTransaccionesPendientesFacade;
import com.bds.ws.facade.IbUsuariosFacade;
import com.bds.ws.model.IbTransaccionesPendientes;
import static com.bds.ws.util.BDSUtil.CODIGO_EXCEPCION_GENERICA;
import static com.bds.ws.util.BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA;
import static com.bds.ws.util.BDSUtil.FORMATO_FECHA_COMPLETA;
import com.bds.ws.util.DAOUtil;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityExistsException;
import javax.persistence.TransactionRequiredException;
import org.apache.log4j.Logger;

/**
 * Clase que implementa la interfaz IbPagosYTransferenciasDAO
 * @author juan.faneite
 */
@Named("IbPagosYTransferenciasDAO")
@Stateless
@NotificationServiceType(NotificationServiceType.ServiceType.SERVICES)
public class IbPagosYTransferenciasDAOImpl extends DAOUtil implements IbPagosYTransferenciasDAO {

    @EJB
    IbUsuariosFacade ibUsuariosFacade;

    @EJB
    IbTransaccionesPendientesFacade ibTransaccionesPendientesFacade;
    /**
     * Log de sistema.
     */
    private static final Logger logger = Logger.getLogger(IbPagosYTransferenciasDAOImpl.class.getName());

    /**
     * Metodo para registrar las transacciones pendientes
     *
     * @param idCanal String idCanal por el cual se ejecuta la transaccion.
     * @param nombreCanal String nombreCanal por el cual se ejecuta la
     * transaccion.
     * @param idUsuario String Usuario creador de la transaccion
     * @param tipo String Tipo de transaccion
     * @param estatus String Estatus de la transaccion "1" EJECUTADO "0"
     * PENDIENTE
     * @param lote String Numero de lote
     * @param fechaHoraValor String Fecha y hora en que se ejecutara
     * latransaccion
     * @param monto String Monto de la transaccion
     * @param aprobacionesRequeridas String Cantidad de aprobaciones necesarias
     * para ejecutar la transaccion
     * @param tipoRif String Tipo rif de la empresa
     * @param rif String Numero de rif de la empresa
     * @param cuentaOrigen String Cuenta de origen de los fondos
     * @param cuentaDestino String Cuenta destino de los fondos
     * @param descripcion String Descripcion de la transaccion
     * @param tipoIdBeneficiario String tipo de documento de identidad del
     * beneficiaro C:cedula P:pasaporte y J:juridico
     * @param idBeneficiario String Identificador del beneficiario
     * @param nombreBeneficiario String Nombre del beneficiario
     * @param emailBeneficiario String e-mail del beneficiario
     * @param tipoCarga String Tipo de carga de la transaccion
     * @param fechaEjecucion String FECHA EN LA QUE SE EJECUTO LA TRANSACCION
     * @return IbTransaccionesPendientesDTO Listado de todas las transacciones pendientes
     */
    @Override
    public RespuestaDTO registrarTransaccionesPendientes(String idCanal, String nombreCanal, String idUsuario, String tipo, String estatus, String lote, String fechaHoraValor,
            String monto, String aprobacionesRequeridas, String tipoRif, String rif, String cuentaOrigen, String cuentaDestino, String descripcion,
            String tipoIdBeneficiario, String idBeneficiario, String nombreBeneficiario, String emailBeneficiario, String tipoCarga, String fechaEjecucion) {

        RespuestaDTO respuesta = new RespuestaDTO();
        IbTransaccionesPendientes transaccionesPendientes = new IbTransaccionesPendientes();
        try {
            transaccionesPendientes.setId(BigDecimal.ZERO);
            transaccionesPendientes.setIdUsuario(ibUsuariosFacade.find(new BigDecimal(idUsuario)));
            transaccionesPendientes.setTipo(tipo.charAt(0));
            transaccionesPendientes.setEstatus(estatus.charAt(0));
            if (lote != null && !lote.isEmpty() && !lote.equals("")) {
                transaccionesPendientes.setLote(new BigInteger(lote));
            }
            transaccionesPendientes.setFechaHoraValor(this.formatearFechaStringADate(fechaHoraValor, FORMATO_FECHA_COMPLETA));
            transaccionesPendientes.setMonto(new BigDecimal(monto));
            transaccionesPendientes.setAprobacionesRequeridas(new BigInteger(aprobacionesRequeridas));
            transaccionesPendientes.setTipoDoc(tipoRif.charAt(0));
            transaccionesPendientes.setDocumento(rif);
            transaccionesPendientes.setCuentaOrigen(cuentaOrigen);
            transaccionesPendientes.setCuentaDestino(cuentaDestino);
            if (descripcion != null && !descripcion.isEmpty() && !descripcion.equals("")) {
                transaccionesPendientes.setDescripcion(descripcion);
            }
            if (tipoIdBeneficiario != null && !tipoIdBeneficiario.isEmpty() && !tipoIdBeneficiario.equals("")) {
                transaccionesPendientes.setTipoIdBeneficiario(tipoIdBeneficiario.charAt(0));
            }
            transaccionesPendientes.setIdBeneficiario(new BigInteger(idBeneficiario));
            if (nombreBeneficiario != null && !nombreBeneficiario.isEmpty() && !nombreBeneficiario.equals("")) {
                transaccionesPendientes.setNombreBeneficiario(nombreBeneficiario);
            }
            if (emailBeneficiario != null && !emailBeneficiario.isEmpty() && !emailBeneficiario.equals("")) {
                transaccionesPendientes.setEmailBeneficiario(emailBeneficiario);
            }
            if (tipoCarga != null && !tipoCarga.isEmpty() && !tipoCarga.equals("")) {
                transaccionesPendientes.setTipoCarga(tipoCarga.charAt(0));
            }
            if (fechaEjecucion != null && !fechaEjecucion.isEmpty() && !fechaEjecucion.equals("")) {
                transaccionesPendientes.setFechaEjecucion(this.formatearFechaStringADate(fechaEjecucion, FORMATO_FECHA_COMPLETA));
            }
            transaccionesPendientes.setFechaHoraRegistro(new Date());

            ibTransaccionesPendientesFacade.create(transaccionesPendientes);

        } catch (IllegalArgumentException | EntityExistsException | TransactionRequiredException e) {
            logger.error( new StringBuilder("ERROR JPA EN registroDeBitacora: ")
                    .append("TCI-").append(tipoRif)
                    .append("CI-").append(rif)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo("JPA004");
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN registroDeBitacora: ")
                    .append("TCI-").append(tipoRif)
                    .append("CI-").append(rif)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }
//        if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN registroDeBitacora: ")
//                    .append("TCI-").append(tipoRif)
//                    .append("CI-").append(rif)
//                    .append("-CH-").append(nombreCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return respuesta;
    }
        
}
