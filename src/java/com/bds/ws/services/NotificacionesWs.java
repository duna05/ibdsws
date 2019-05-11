/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.services;

import com.bds.ws.dao.EMailDAO;
import com.bds.ws.dao.OtpDAO;
import com.bds.ws.dao.SMSDAO;
import com.bds.ws.dto.EMailDTO;
import com.bds.ws.dto.OtpDTO;
import com.bds.ws.dto.RespuestaDTO;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * Clase de servicios relacionados a las notificaciones, envio de emails y sms
 *
 * @author juan.faneite
 */
@WebService(serviceName = "NotificacionesWs")
public class NotificacionesWs {

    @EJB
    private OtpDAO otp;

    @EJB
    private EMailDAO email;

    @EJB
    private SMSDAO sms;

    /* Genera un nuevo OTP el cual es asignado al cliente asociado a una tarjeta de debito.
     * Adicionalmente, el OTP es enviado por correo electronico al cliente, solo en caso de que, 
     * tanto el canal, como el cliente esten configurados para dicho envio.
     * @param inCodigoCliente String codigo de cliente al cual se le va a generar el OTP.
     * @param canal String canal Codigo (extendido) del canal desde el cual es llamado el procedimiento.
     * @return OtpDTO con el codigo otp generado y/o respuestaDTO si fue exitoso o no
     */
    @WebMethod(operationName = "generarOTP")
    public OtpDTO generarOTP(@WebParam(name = "codigoCliente") String codigoCliente, @WebParam(name = "canal") String canal) {
        return otp.generarOTP(codigoCliente, canal);
    }
    
    /* Genera un nuevo OTP el cual es asignado al cliente asociado a una tarjeta de debito.
     * Adicionalmente, el OTP es enviado por correo electronico al cliente, solo en caso de que, 
     * tanto el canal, como el cliente esten configurados para dicho envio.
     * @param inCodigoCliente String codigo de cliente al cual se le va a generar el OTP.
     * @param canal String canal Codigo (extendido) del canal desde el cual es llamado el procedimiento.
     * @return OtpDTO con el codigo otp generado y/o respuestaDTO si fue exitoso o no
     */
    @WebMethod(operationName = "generarOTPSinMail")
    public OtpDTO generarOTPSinMail(@WebParam(name = "codigoCliente") String codigoCliente, @WebParam(name = "canal") String canal) {
        return otp.generarOTPSinEmail(codigoCliente, canal);
    }

    /**
     * Valida un OTP. El OTP es valido solo si coincide con el OTP generado
     * previamente. Nota: Se tomara siempre el ultimo OTP generado.
     *
     * @param codigoCliente String codigo de cliente al cual se le va a generar
     * el OTP.
     * @param codOTP String OTP que va a ser validado.
     * @param canal String Codigo (extendido) del canal desde el cual es llamado
     * el procedimiento.
     * @return OtpDTO indica si el otp es valido o no
     */
    @WebMethod(operationName = "validarOTP")
    public OtpDTO validarOTP(@WebParam(name = "codigoCliente") String codigoCliente, @WebParam(name = "codOTP") String codOTP, @WebParam(name = "canal") String canal) {
        return otp.validarOTP(codigoCliente, codOTP, canal);
    }

    /* Genera un nuevo OTP el cual es asignado al usuario
     * 
     *
     * @param idUsuario   usuario al cual se le va a generar el OTP
     * @param codigoCanal codigo de canal en el CORE bancario
     * @return OtpDTO con el codigo otp generado y/o respuestaDTO si fue exitoso o no
     */
    @WebMethod(operationName = "generarOTPEnviarCorreoPj")
    public OtpDTO generarOTPEnviarCorreoPj(@WebParam(name = "idUsuario") String idUsuario,
            @WebParam(name = "codPlantillaCorreo") Integer codPlantillaCorreo,
            @WebParam(name = "destinatario") String destinatario,
            @WebParam(name = "parametrosCorreo") String parametrosCorreo,
            @WebParam(name = "codigoCanal") String codigoCanal) {
        return otp.generarOTPEnviarCorreoPj(idUsuario, codPlantillaCorreo, destinatario, parametrosCorreo, codigoCanal);
    }

    /**
     * Valida un OTP. El OTP es valido solo si coincide con el OTP generado
     * previamente. Nota: Se tomara siempre el ultimo OTP generado.
     *
     * @param idUsuario codigo del usuario al cual se le va a validar el OTP
     * @param codOTP String OTP que va a ser validado.
     * @param codigoCanal codigo de canal en el CORE bancario
     * @param registroUsuario the value of registroEmpresa
     * @return the com.bds.ws.dto.OtpDTO
     */
    @WebMethod(operationName = "validarOTPPj")
    public OtpDTO validarOTPPj(@WebParam(name = "idUsuario") String idUsuario,
            @WebParam(name = "codOTP") String codOTP,
            @WebParam(name = "codigoCanal") String codigoCanal,
            @WebParam(name = "registroUsuario") String registroUsuario) {
        return otp.validarOTPPj(idUsuario, codOTP, codigoCanal, registroUsuario);
    }

    /**
     * Procesa el envio de Email con los parametros especificados
     *
     * @param remitente String Remitente del correo.
     * @param destinatario String Destinatario del correo.
     * @param asunto String Asunto del correo.
     * @param texto String Texto del correo.
     * @param canal Codigo (extendido) del canal desde el cual es llamado el
     * procedimiento.
     * @return EMailDTO la cuenta con los movimientos de la cuenta
     * seleccionada(solo vienen los datos de los movimientos)
     */
    @WebMethod(operationName = "enviarEmail")
    public EMailDTO enviarEmail(@WebParam(name = "remitente") String remitente, @WebParam(name = "destinatario") String destinatario, @WebParam(name = "asunto") String asunto, @WebParam(name = "texto") String texto, @WebParam(name = "canal") String canal) {
        return email.enviarEmail(remitente, destinatario, asunto, texto, canal);
    }
    
    /**
     * Procesa el envio de Email con los parametros especificados
     *
     * @param remitente String Remitente del correo.
     * @param destinatario String Destinatario del correo.
     * @param asunto String Asunto del correo.
     * @param texto String Texto del correo.
     * @param canal Codigo (extendido) del canal desde el cual es llamado el
     * procedimiento.
     * @return EMailDTO la cuenta con los movimientos de la cuenta
     * seleccionada(solo vienen los datos de los movimientos)
     */
    @WebMethod(operationName = "enviarEmailOTP")
    public EMailDTO enviarEmailOTP(@WebParam(name = "codCliente") String codCliente, @WebParam(name = "canal") String canal,  @WebParam(name = "codOtp") String codOtp) {
        return email.enviarEmailOTP(codCliente, canal, codOtp);
    }

    /**
     * Metodo que se encarga de realizar un envio de SMS
     *
     * @param numeroTelefono numero del destinatario
     * @param texto texto del mensaje a enviar
     * @param canal identificador del canal
     * @return RespuestaDTO objeto con el estatus de la operacion
     */
    @WebMethod(operationName = "enviarSMS")
    public RespuestaDTO enviarSMS(@WebParam(name = "numeroTelefono") String numeroTelefono, @WebParam(name = "texto") String texto, @WebParam(name = "canal") String canal) {
        return sms.enviarSMS(numeroTelefono, texto, canal);
    }

    /**
     * Procesa el envio de Email con los parametros especificados
     *
     * @param codigoEmpresa siempre se envia el valor 1
     * @param codigoPlantilla codigo de la plantilla
     * @param destinatario String Destinatario del correo.
     * @param parametrosCorreo parametros requeridos para la plantilla
     * @param idCanal codigo del canal interno en ib
     * @param codigoCanal codigo del canal interno en el CORE
     * @return EMailDTO la cuenta con los movimientos de la cuenta
     * seleccionada(solo vienen los datos de los movimientos)
     */
    @WebMethod(operationName = "enviarEmailPlantilla")
    public EMailDTO enviarEmailPlantilla(@WebParam(name = "codigoEmpresa") String codigoEmpresa,
            @WebParam(name = "codigoPlantilla") String codigoPlantilla,
            @WebParam(name = "destinatario") String destinatario,
            @WebParam(name = "texto") String parametrosCorreo,
            @WebParam(name = "idCanal") String idCanal,
            @WebParam(name = "codigoCanal") String codigoCanal) {
        return email.enviarEmailPlantilla(codigoEmpresa, codigoPlantilla, destinatario, parametrosCorreo, idCanal, codigoCanal);
    }

    /**
     * Procesa el envio de Email con los parametros especificados
     *
     * @param codigoPlantilla codigo de la plantilla
     * @param destinatario String Destinatario del correo.
     * @param parametrosCorreo parametros requeridos para la plantilla
     * @param idCanal codigo del canal interno en ib
     * @param codigoCanal codigo del canal interno en el CORE
     * @return EMailDTO la cuenta con los movimientos de la cuenta
     * seleccionada(solo vienen los datos de los movimientos)
     */
    @WebMethod(operationName = "enviarEmailPN")
    public RespuestaDTO enviarEmailPN(@WebParam(name = "codigoPlantilla") String codigoPlantilla,
            @WebParam(name = "destinatario") String destinatario,
            @WebParam(name = "texto") String parametrosCorreo,
            @WebParam(name = "idCanal") String idCanal,
            @WebParam(name = "codigoCanal") String codigoCanal) {
        return email.enviarEmailPN(codigoPlantilla, destinatario, parametrosCorreo, idCanal, codigoCanal);
    }

}
