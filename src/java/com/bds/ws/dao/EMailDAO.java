/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dao;

import com.bds.ws.dto.EMailDTO;
import com.bds.ws.dto.RespuestaDTO;

/**
 * Interfaz para email
 * @author cesar.mujica
 */
public interface EMailDAO {

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
    public EMailDTO enviarEmail(String remitente, String destinatario, String asunto, String texto, String canal);
    /**
     * Procesa el envio de Email con los parametros especificados
     *
     * @param inCodigoCliente String codigo del cliente registrado en la BD.
     * @param destinatario String Destinatario del correo.
     * @param asunto String Asunto del correo.
     * @param ivOTP String que contiene el cod a enviar en el correo.
     * @param CodExtCanal Codigo (extendido) del canal desde el cual es llamado el
     * procedimiento.
     * @return EMailDTO la cuenta con los movimientos de la cuenta
     * seleccionada(solo vienen los datos de los movimientos)
     */
    public EMailDTO enviarEmailOTP(String inCodigoCliente, String CodExtCanal, String ivOTP);
    
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
    public EMailDTO enviarEmailPlantilla(String codigoEmpresa, String codigoPlantilla, String destinatario, String parametrosCorreo, String idCanal, String codigoCanal);

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
    public RespuestaDTO enviarEmailPN(String codigoPlantilla, String destinatario, String parametrosCorreo, String idCanal, String codigoCanal);
    
}
