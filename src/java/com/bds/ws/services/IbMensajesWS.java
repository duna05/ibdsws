/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.services;

import com.bds.ws.dao.IbMensajesDAO;
import com.bds.ws.dto.IbMensajesDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.dto.UtilDTO;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author cesar.mujica
 */
@WebService(serviceName = "IbMensajesWS")
public class IbMensajesWS {
    @EJB
    private IbMensajesDAO ejbRef;// Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Web Service Operation")

    @WebMethod(operationName = "consultarMensajesUsuarios")
    public IbMensajesDTO consultarMensajesUsuarios(@WebParam(name = "idUsuario") String idUsuario, @WebParam(name = "nombreCanal") String nombreCanal) {
        return ejbRef.consultarMensajesUsuarios(idUsuario, nombreCanal);
    }
    
    @WebMethod(operationName = "consultarMensajesEmpresa")
    public IbMensajesDTO consultarMensajesEmpresa(
            @WebParam(name = "idEmpresa")   String idEmpresa,
            @WebParam(name = "idCanal") String idCanal,                                      
            @WebParam(name = "nombreCanal") String nombreCanal) {
        return ejbRef.consultarMensajesEmpresa(idEmpresa,idCanal,nombreCanal);
    }

    @WebMethod(operationName = "cantNuevosMsjsUsuarios")
    public UtilDTO cantNuevosMsjsUsuarios(@WebParam(name = "idUsuario") String idUsuario, @WebParam(name = "nombreCanal") String nombreCanal) {
        return ejbRef.cantNuevosMsjsUsuarios(idUsuario, nombreCanal);
    }

    @WebMethod(operationName = "marcarMensajeLeido")
    public RespuestaDTO marcarMensajeLeido(@WebParam(name = "idUsuario") String idUsuario, @WebParam(name = "idMensaje") String idMensaje, @WebParam(name = "nombreCanal") String nombreCanal) {
        return ejbRef.marcarMensajeLeido(idUsuario, idMensaje, nombreCanal);
    }
    
    @WebMethod(operationName = "mensajeUsuarioLeido")
    public UtilDTO mensajeUsuarioLeido(@WebParam(name = "idUsuario") String idUsuario, @WebParam(name = "idMensaje") String idMensaje, @WebParam(name = "nombreCanal") String nombreCanal) {
        return ejbRef.mensajeUsuarioLeido(idUsuario, idMensaje, nombreCanal);
    }
    
}
