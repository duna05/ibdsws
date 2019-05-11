/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dao;

import com.bds.ws.dto.OtpDTO;

/**
 * Interfaz DAO para OTP
 * @author juan.faneite
 */
public interface OtpDAO { 

    /* Genera un nuevo OTP el cual es asignado al cliente asociado a una tarjeta de debito.
     * Adicionalmente, el OTP es enviado por correo electronico al cliente, solo en caso de que, 
     * tanto el canal, como el cliente esten configurados para dicho envio.
     * @param inCodigoCliente String codigo de cliente al cual se le va a generar el OTP.
     * @param canal String canal Codigo (extendido) del canal desde el cual es llamado el procedimiento.
     * @return OtpDTO con el codigo otp generado y/o respuestaDTO si fue exitoso o no
     */
    public OtpDTO generarOTP(String inCodigoCliente, String canal);
    /* Genera un nuevo OTP el cual es asignado al cliente asociado a una tarjeta de debito.
     * Adicionalmente, el OTP es enviado por correo electronico al cliente, solo en caso de que, 
     * tanto el canal, como el cliente esten configurados para dicho envio.
     * @param inCodigoCliente String codigo de cliente al cual se le va a generar el OTP.
     * @param canal String canal Codigo (extendido) del canal desde el cual es llamado el procedimiento.
     * @return OtpDTO con el codigo otp generado y/o respuestaDTO si fue exitoso o no
     */
    public OtpDTO generarOTPSinEmail(String inCodigoCliente, String canal);

    /**
     * Valida un OTP. El OTP es valido solo si coincide con el OTP generado
     * previamente. Nota: Se tomara siempre el ultimo OTP generado.
     *
     * @param inCodigoCliente String codigo de cliente al cual se le va a
     * generar el OTP.
     * @param ivOTP String OTP que va a ser validado.
     * @param ivCodExtCanal String Codigo (extendido) del canal desde el cual es
     * llamado el procedimiento.
     * @return OtpDTO indica si el otp es valido o no 
     */
    public OtpDTO validarOTP(String inCodigoCliente, String ivOTP, String ivCodExtCanal);
    
    /* Genera un nuevo OTP el cual es asignado al usuario
     * 
     *
     * @param idUsuario   usuario al cual se le va a generar el OTP
     * @param codigoCanal codigo de canal en el CORE bancario
     * @return OtpDTO con el codigo otp generado y/o respuestaDTO si fue exitoso o no
     */
    public OtpDTO generarOTPEnviarCorreoPj(String idUsuario, Integer codigoPlantillaCorreo, String destinatario, String parametrosCorreo, String codigoCanal);
    
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
    public OtpDTO validarOTPPj(String idUsuario, String codOTP, String codigoCanal, String registroUsuario);
}
