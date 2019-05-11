/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.services;

import com.bds.ws.dao.IBAfiliacionDAO;
import com.bds.ws.dao.IbMenuDAO;
import com.bds.ws.dto.IBAfiliacionesDTO;
import com.bds.ws.dto.IbModulosDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.dto.UtilDTO;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * Clase de servicio web para afiliaciones
 * @author juan.faneite
 */
@WebService(serviceName = "IbAfiliacionWS")
public class IbAfiliacionWS {

    @EJB
    private IBAfiliacionDAO afiliacionDAO;// Add business logic below. (Right-click in editor and choose
    @EJB
    private IbMenuDAO menuDAO;
    // "Insert Code > Add Web Service Operation")

    /**
     * Metodo que Obtiene el listado de afiliados de un cliente por operacion.
     *
     * @param usuario String Referencia foranea al usuario dueno de la
     * afiliacion.
     * @param idTransaccion String ID de el tipo de transaccion.
     * @param idCanal String idCanal por el cual se ejecuta la transaccion.
     * @param nombreCanal String nombreCanal por el cual se ejecuta la
     * transaccion.
     * @return IBAfiliacionesDTO listado de afiliados
     */
    @WebMethod(operationName = "obtenerListadoAfiliadosPorOperacion")
    public IBAfiliacionesDTO obtenerListadoAfiliadosPorOperacion(@WebParam(name = "usuario") String usuario, @WebParam(name = "idTransaccion") String idTransaccion, @WebParam(name = "idCanal") String idCanal, @WebParam(name = "nombreCanal") String nombreCanal) {
        return afiliacionDAO.obtenerListadoAfiliadosPorOperacion(usuario, idTransaccion, idCanal, nombreCanal);
    }

    /**
     * Metodo que Obtiene el listado de afiliados de un cliente por operacion.
     *
     * @param nroIdentidad String Numero de CI del usuario dueno. (Ordenante)
     * @param codUsuario String Codigo del
     * @param idTransaccion String ID de el tipo de transaccion.
     * @param tipoTransf Indica el tipo de transferencia
     * @param nombreCanal String nombreCanal por el cual se ejecuta la
     * transaccion.
     * @return IBAfiliacionesDTO listado de afiliados
     */
    @WebMethod(operationName = "afiliacionesOperacionCodUsuario")
    public IBAfiliacionesDTO afiliacionesOperacionCodUsuario(@WebParam(name = "nroIdentidad") String nroIdentidad, @WebParam(name = "codUsuario") String codUsuario, @WebParam(name = "idTransaccion") String idTransaccion, @WebParam(name = "tipoTransf") String tipoTransf, @WebParam(name = "nombreCanal") String nombreCanal) {
        return afiliacionDAO.afiliacionesOperacionCodUsuario(nroIdentidad, codUsuario, idTransaccion, tipoTransf, nombreCanal);
    }
    
    /**
     * Metodo para obtener el afiliado por codigo de usuario y Id de Usuario
     * @param idAfiliacion String id de la afiliacion
     * @param nombreCanal String ID del canal
     * @return IBAfiliacionesDTO -> afiliacion Seleccionada
     */
    @WebMethod(operationName = "obtenerAfiliacionPorId")
    public IBAfiliacionesDTO obtenerAfiliacionPorId(@WebParam(name = "idAfiliacion")String idAfiliacion, @WebParam(name = "nombreCanal")String nombreCanal){
        return afiliacionDAO.obtenerAfiliacionPorId( idAfiliacion, nombreCanal);
    }  
          
    /**
     * Metodo para deshabilitar el afiliado por codigo de usuario y Id de
     * Usuario
     *
     * @param listIdAfiliaciones
     * @param nombreCanal String ID del canal
     * @return RespuestaDTO
     */
    @WebMethod(operationName = "deshabilitarAfiliacion")
    public RespuestaDTO deshabilitarAfiliacion(@WebParam(name = "rafaga")String rafaga, @WebParam(name = "separador")String separador, @WebParam(name = "nombreCanal")String nombreCanal){
        return afiliacionDAO.deshabilitarAfiliacion(rafaga, separador, nombreCanal);
    } 
    
    /**
     * Metodo para actualizar una afiliacion
     *
     * @param idAfiliacion id de la afiliacion
     * @param alias alias de la afiliacion
     * @param email email del beneficiario
     * @param montoMax monto maximo
     * @param nombreBenef nombre del beneficiario
     * @param nombreCanal nombre del canal
     * @return RespuestaDTO indica si la operacion se realizo de manera exitosa o no
     */
    @WebMethod(operationName = "actualizarAfiliacion")
    public RespuestaDTO actualizarAfiliacion(@WebParam(name = "idAfiliacion") String idAfiliacion, @WebParam(name = "alias") String alias, @WebParam(name = "tipoDoc") char tipoDoc, @WebParam(name = "documento") String documento, @WebParam(name = "email") String email, @WebParam(name = "montoMax") String montoMax, @WebParam(name = "nombreBenef") String nombreBenef,@WebParam(name = "nombreBanco") String nombreBanco,@WebParam(name = "nroCtaTDC") String nroCtaTDC, @WebParam(name = "nombreCanal") String nombreCanal) {        
        return afiliacionDAO.actualizarAfiliacion(idAfiliacion,alias,tipoDoc,documento,email,montoMax,nombreBenef,nombreBanco,nroCtaTDC,nombreCanal);
    }
    
    /**
     * Metodo para insertar una afiliacion
     * @param codUsuario codigo del usuario
     * @param alias alias del beneficiario
     * @param tipoDoc tipo de documento V o J
     * @param documento cedula o rif
     * @param email email del beneficiario
     * @param idTran id de la transaccion
     * @param montoMax monto maximo
     * @param nombreBanco nombre del banco
     * @param nombreBenef nombre del beneficiario
     * @param nroCtaTDC numero de cuenta o tdc
     * @param nombreCanal nombre del canal
     * @return RespuestaDTO indica si la operacion se realizo de manera exitosa o no
     */
    @WebMethod(operationName = "insertarAfiliacion")
    public RespuestaDTO insertarAfiliacion(@WebParam(name = "codUsuario") String codUsuario, @WebParam(name = "alias") String alias, @WebParam(name = "tipoDoc") char tipoDoc, @WebParam(name = "documento") String documento, @WebParam(name = "email") String email, @WebParam(name = "idTran") String idTran, @WebParam(name = "montoMax") String montoMax, @WebParam(name = "nombreBanco") String nombreBanco, @WebParam(name = "nombreBenef") String nombreBenef, @WebParam(name = "nroCtaTDC") String nroCtaTDC, @WebParam(name = "nombreCanal") String nombreCanal) {        
        return afiliacionDAO.insertarAfiliacion(codUsuario, alias, tipoDoc, documento, email, idTran, montoMax, nombreBanco, nombreBenef, nroCtaTDC, nombreCanal);
    }
    
    /**
     * Metodo para verificar que no ingrese un alias existente
     * @param codUsuario codigo del usuario
     * @param alias alias del beneficiario 
     * @param nombreCanal nombre del canal
     * @return UtilDTO true si el alias existe false en caso contrario
     */
    @WebMethod(operationName = "verificarAlias")
    public UtilDTO verificarAlias(@WebParam(name = "codUsuario")String codUsuario, @WebParam(name = "alias")String alias, @WebParam(name = "nombreCanal")String nombreCanal){
        return afiliacionDAO.verificarAlias( codUsuario,  alias,  nombreCanal);
    } 
    
    /**
     * Metodo para obtener el modelo y la transaccion de los que tengan el posee beneficiario activo
     * @param nombreCanal String ID del canal
     * @return IbModulosDTO
     */
    @WebMethod(operationName = "modeloTransBeneficiario")
    public IbModulosDTO obtenerModeloTransBeneficiario(@WebParam(name = "nombreCanal")String nombreCanal){
        return menuDAO.obtenerModeloTransBeneficiario(nombreCanal);
    }  
    
       /**
     * Metodo para verificar que no ingrese un producto existente
     * @param codUsuario codigo del usuario
     * @param producto producto del beneficiario
     * @param nombreCanal nombre del canal
     * @return RespuestaDTO
     */
    @WebMethod(operationName = "verificarProducto")
    public UtilDTO verificarProducto(@WebParam(name = "codUsuario")String codUsuario, @WebParam(name = "producto")String producto, @WebParam(name = "nombreCanal")String nombreCanal){
        return afiliacionDAO.verificarProducto( codUsuario,  producto,  nombreCanal);
    } 
    
    
    
}
