/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.services;

import com.bds.ws.dao.IbUsuariosP2PDAO;
import com.bds.ws.dto.IbUsuariosP2PDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.dto.UtilDTO;
import java.math.BigDecimal;
import javax.ejb.EJB;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author cesar.mujica
 */
@WebService(serviceName = "IbUsuariosP2PWS")
public class IbUsuariosP2PWS {
    @EJB
    private IbUsuariosP2PDAO ejbRef;

    /**
     * inserta la afiliacion de un usuario al servicio P2P
     * @param idUsuario identificador del usuario en Ora11
     * @param nroTelefono numero de telefono asociado al servicio
     * @param tipoDocumento tipo de doc del usuario asociado al servicio
     * @param nroDocumento numero de doc del usuario asociado al servicio
     * @param email correo electronico del usuario asociado al servicio
     * @param nroCuenta numero de cuenta asociada al servicio
     * @param mtoMaxTransaccion monto maximo de pago por operacion
     * @param mtoMaxDiario monto maximo acumlado diarios de pagos
     * @param idCanal String idCanal por el cual se ejecuta la transaccion.
     * @param nombreCanal String nombreCanal por el cual se ejecuta la transaccion.
     * @return RespuestaDTO indica si el metodo se realizo de manera exitosa o no
     */
    @WebMethod(operationName = "insertarUsuarioP2P")
    public RespuestaDTO insertarUsuarioP2P(@WebParam(name = "idUsuario") String idUsuario, @WebParam(name = "nroTelefono") String nroTelefono, @WebParam(name = "tipoDocumento") String tipoDocumento, @WebParam(name = "nroDocumento") String nroDocumento, @WebParam(name = "email") String email, @WebParam(name = "nroCuenta") String nroCuenta, @WebParam(name = "mtoMaxTransaccion") BigDecimal mtoMaxTransaccion, @WebParam(name = "mtoMaxDiario") BigDecimal mtoMaxDiario, @WebParam(name = "idCanal") String idCanal, @WebParam(name = "nombreCanal") String nombreCanal) {
        return ejbRef.insertarUsuarioP2P(idUsuario, nroTelefono, tipoDocumento, nroDocumento, email, nroCuenta, mtoMaxTransaccion, mtoMaxDiario, idCanal, nombreCanal);
    }

    /**  
     * Metodo que realiza la consulta de afiliaciones Activas para el serv P2P por usuario.
     * @param idUsuario identificador del usuario en Ora11
     * @param idCanal String idCanal por el cual se ejecuta la transaccion.
     * @param nombreCanal String nombreCanal por el cual se ejecuta la transaccion.
     * @return RespuestaDTO indica si el metodo se realizo de manera exitosa o no
     */
    @WebMethod(operationName = "consultarUsuarioP2P")
    public IbUsuariosP2PDTO consultarUsuarioP2P(@WebParam(name = "idUsuario") String idUsuario, @WebParam(name = "idCanal") String idCanal, @WebParam(name = "nombreCanal") String nombreCanal) {
        return ejbRef.consultarUsuarioP2P(idUsuario, idCanal, nombreCanal);
    }
    
    /**
     * *
     * Metodo que realiza la validacion de los datos a afiliar al servicio P2P
     *
     * @param nroTelf
     * @param nroCta
     * @param idUsuario id del usuario
     * @param nombreCanal codigo del canal
     * @return UtilDTO
     */
    @WebMethod(operationName = "validarAfiliacionP2P")
    public UtilDTO validarAfiliacionP2P(@WebParam(name = "nroTelf") String nroTelf, @WebParam(name = "nroCta") String nroCta, @WebParam(name = "idUsuario") String idUsuario, @WebParam(name = "nombreCanal") String nombreCanal) {
        return ejbRef.validarAfiliacionP2P(nroTelf, nroCta, idUsuario, nombreCanal);
    }
    
    /**
     * *
     * Metodo que realiza la validacion de los datos a editar una afiliacion al servicio P2P
     *
     * @param idAfiliacion identificador de la afiliacion a modificar
     * @param nroTelf numero de telf a afiliar
     * @param nroCta numero de cta a afiliar
     * @param idUsuario id del usuario
     * @param nombreCanal codigo del canal
     * @return UtilDTO
     */
    @WebMethod(operationName = "validarEdicionAfiliacionP2P")
    public UtilDTO validarEdicionAfiliacionP2P(@WebParam(name = "idAfiliacion") String idAfiliacion, @WebParam(name = "nroTelf") String nroTelf, @WebParam(name = "nroCta") String nroCta, @WebParam(name = "idUsuario") String idUsuario, @WebParam(name = "nombreCanal") String nombreCanal) {
        return ejbRef.validarEdicionAfiliacionP2P(idAfiliacion, nroTelf, nroCta, idUsuario, nombreCanal);
    }
    
    /**
     * edita la afiliacion de un usuario al servicio P2P
     * @param idAfiliacion identificador de la afiliacion a actualizar
     * @param nroTelefono numero de telefono asociado al servicio
     * @param email correo electronico del usuario asociado al servicio
     * @param nroCuenta numero de cuenta asociada al servicio
     * @param mtoMaxTransaccion monto maximo de pago por operacion
     * @param mtoMaxDiario monto maximo acumlado diarios de pagos
     * @param idCanal String idCanal por el cual se ejecuta la transaccion.
     * @param nombreCanal String nombreCanal por el cual se ejecuta la transaccion.
     * @return RespuestaDTO indica si el metodo se realizo de manera exitosa o no
     */   
    @WebMethod(operationName = "editarUsuarioP2P")
    public RespuestaDTO editarUsuarioP2P(@WebParam(name = "idAfiliacion") String idAfiliacion, @WebParam(name = "nroTelefono") String nroTelefono, @WebParam(name = "email") String email, @WebParam(name = "nroCuenta") String nroCuenta, @WebParam(name = "mtoMaxTransaccion") BigDecimal mtoMaxTransaccion, @WebParam(name = "mtoMaxDiario") BigDecimal mtoMaxDiario, @WebParam(name = "idCanal") String idCanal, @WebParam(name = "nombreCanal") String nombreCanal) {
        return ejbRef.editarUsuarioP2P(idAfiliacion, nroTelefono, email, nroCuenta, mtoMaxTransaccion, mtoMaxDiario, idCanal, nombreCanal);
    }
    
    /**
     * desafilia la afiliacion de un usuario al servicio P2P
     * @param idAfiliacion identificador de la afiliacion a actualizar
     * @param idCanal String idCanal por el cual se ejecuta la transaccion.
     * @param nombreCanal String nombreCanal por el cual se ejecuta la transaccion.
     * @return RespuestaDTO indica si el metodo se realizo de manera exitosa o no
     */        
    @WebMethod(operationName = "desafiliarUsuarioP2P")
    public RespuestaDTO desafiliarUsuarioP2P(@WebParam(name = "idAfiliacion") String idAfiliacion, @WebParam(name = "idCanal") String idCanal, @WebParam(name = "nombreCanal") String nombreCanal) {
        return ejbRef.desafiliarUsuarioP2P(idAfiliacion, idCanal, nombreCanal);
    }
    
}
