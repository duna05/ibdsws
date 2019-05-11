/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.services;

import com.bds.ws.dao.IBBitacoraDAO;
import com.bds.ws.dto.RespuestaDTO;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * Clase de servicio web para bitacora
 * @author juan.faneite
 */
@WebService(serviceName = "IbBitacoraWS")
public class IbBitacoraWS {

    @EJB
    private IBBitacoraDAO bitacoraDAO;// Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Web Service Operation")

    /**
     * Registro de bitacora Todos los campos son obligatorios.
     *
     * @param cuentaOrigen String Numero de cuenta origen
     * @param cuentaDestino String Numero de cuenta/TDC/Servicio destino
     * @param monto String Monto de la transaccion
     * @param referencia String Numero de referencia de la transaccion
     * @param descripcion String Descripcion dada por el usuario a la
     * transaccion
     * @param ip String Direccion ip del usuario
     * @param userAgent String Identificacion del navegador utilizado por el
     * usuario
     * @param errorOperacion String Canal de ejecucion de la transaccion
     * @param nombreBeneficiario String Nombre del beneficiario
     * @param tipoRif String Tipo de RIF de una empresa
     * @param rifCedula String Numero de RIF de una empresa
     * @param fechaHoraTx String Fecha y hora para ejecutar la transaccion
     * @param fechaHoraJob String Fecha y hora de ejecucion del job
     * @param idUsuario String Referencia foranea al usuario creador de la
     * transaccion
     * @param idTransaccion String Identificador de la transaccion realizada
     * @param idAfiliacion String Referencia foranea al beneficiario
     * @param idCanal String idCanal por el cual se ejecuta la transaccion.
     * @param nombreCanal String nombreCanal por el cual se ejecuta la
     * transaccion.
     * @return RespuestaDTO indica si la operacion se realizo de manera exitosa o no
     */
    @WebMethod(operationName = "registroDeBitacora")
    public RespuestaDTO registroDeBitacora(@WebParam(name = "cuentaOrigen") String cuentaOrigen, @WebParam(name = "cuentaDestino") String cuentaDestino, @WebParam(name = "monto") String monto, @WebParam(name = "referencia") String referencia, @WebParam(name = "descripcion") String descripcion, @WebParam(name = "ip") String ip, @WebParam(name = "userAgent") String userAgent, @WebParam(name = "errorOperacion") String errorOperacion, @WebParam(name = "nombreBeneficiario") String nombreBeneficiario, @WebParam(name = "tipoRif") String tipoRif, @WebParam(name = "rifCedula") String rifCedula, @WebParam(name = "fechaHoraTx") String fechaHoraTx, @WebParam(name = "fechaHoraJob") String fechaHoraJob, @WebParam(name = "idCanal") String idCanal, @WebParam(name = "nombreCanal") String nombreCanal, @WebParam(name = "idUsuario") String idUsuario, @WebParam(name = "idTransaccion") String idTransaccion, @WebParam(name = "idAfiliacion") String idAfiliacion) {
        return bitacoraDAO.registroDeBitacora(cuentaOrigen, cuentaDestino, monto, referencia, descripcion, ip, userAgent, errorOperacion, nombreBeneficiario, tipoRif, rifCedula, fechaHoraTx, fechaHoraJob, idCanal, nombreCanal, idUsuario, idTransaccion, idAfiliacion);
    }

    /**
     * Registro de bitacora Todos los campos son obligatorios.
     *
     * @param cuentaOrigen String Numero de cuenta origen
     * @param cuentaDestino String Numero de cuenta/TDC/Servicio destino
     * @param monto String Monto de la transaccion
     * @param referencia String Numero de referencia de la transaccion
     * @param descripcion String Descripcion dada por el usuario a la
     * transaccion
     * @param ip String Direccion ip del usuario
     * @param userAgent String Identificacion del navegador utilizado por el
     * usuario
     * @param errorOperacion String Canal de ejecucion de la transaccion
     * @param nombreBeneficiario String Nombre del beneficiario
     * @param tipoRif String Tipo de RIF de una empresa
     * @param rifCedula String Numero de RIF de una empresa
     * @param fechaHoraTx String Fecha y hora para ejecutar la transaccion
     * @param fechaHoraJob String Fecha y hora de ejecucion del job
     * @param idUsuario String Referencia foranea al usuario creador de la
     * transaccion
     * @param idAfiliacion String Referencia foranea al beneficiario
     * @param idEmpresa codigo de la empresa en IB
     * @param nombreServicio nombre del servicio en IB
     * @param idCanal String idCanal por el cual se ejecuta la transaccion.
     * @param nombreCanal String nombreCanal por el cual se ejecuta la
     * transaccion.
     * @return RespuestaDTO indica si la operacion se realizo de manera exitosa
     * o no
     */
    @WebMethod(operationName = "registroDeBitacoraPj")
    public RespuestaDTO registroDeBitacoraPj(
            @WebParam(name = "cuentaOrigen") String cuentaOrigen, 
            @WebParam(name = "cuentaDestino") String cuentaDestino, 
            @WebParam(name = "monto") String monto, 
            @WebParam(name = "referencia") String referencia, 
            @WebParam(name = "descripcion") String descripcion, 
            @WebParam(name = "ip") String ip, 
            @WebParam(name = "userAgent") String userAgent, 
            @WebParam(name = "errorOperacion") String errorOperacion, 
            @WebParam(name = "nombreBeneficiario") String nombreBeneficiario, 
            @WebParam(name = "tipoRif") String tipoRif, 
            @WebParam(name = "rifCedula") String rifCedula, 
            @WebParam(name = "fechaHoraTx") String fechaHoraTx, 
            @WebParam(name = "fechaHoraJob") String fechaHoraJob, 
            @WebParam(name = "idCanal") String idCanal, 
            @WebParam(name = "nombreCanal") String nombreCanal, 
            @WebParam(name = "idUsuario") String idUsuario, 
            @WebParam(name = "idEmpresa") String idEmpresa, 
            @WebParam(name = "nombreServicio") String nombreServicio, 
            @WebParam(name = "idAfiliacion") String idAfiliacion) {
        return bitacoraDAO.registroDeBitacoraPj(cuentaOrigen, cuentaDestino, monto, referencia, descripcion, ip, userAgent, errorOperacion, nombreBeneficiario, tipoRif, rifCedula, fechaHoraTx, fechaHoraJob, idCanal, nombreCanal, idUsuario, idEmpresa, nombreServicio, idAfiliacion);
    }
}
