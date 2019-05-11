/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dao;

import com.bds.ws.dto.IBAfiliacionesDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.dto.UtilDTO;
import java.util.List;

/**
 * Interfaz para afiliaciones
 * @author juan.faneite
 */
public interface IBAfiliacionDAO {

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
    public IBAfiliacionesDTO obtenerListadoAfiliadosPorOperacion(String usuario, String idTransaccion, String idCanal, String nombreCanal);

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
    public IBAfiliacionesDTO afiliacionesOperacionCodUsuario(String nroIdentidad, String codUsuario, String idTransaccion, String tipoTransf, String nombreCanal);

    /**
     * Metodo para obtener el afiliado por codigo de usuario y Id de Usuario
     * @param idAfiliacion String id de la afiliacion
     * @param nombreCanal String ID del canal
     * @return IBAfiliacionesDTO -> afiliacion Seleccionada
     */
    public IBAfiliacionesDTO obtenerAfiliacionPorId(String idAfiliacion, String nombreCanal);   
    
    /**
     * Metodo para deshabilitar el afiliado por codigo de usuario y Id de
     * Usuario
     *
     * @param rafaga
     * @param separador
     * @param nombreCanal String ID del canal
     * @return IBAfiliacionesDTO -> afiliacion Seleccionada
     */
    public RespuestaDTO deshabilitarAfiliacion(String rafaga, String separador, String nombreCanal);

    /**
     * Metodo para actualizar una afiliacion
     *
     * @param idAfiliacion id de la afiliacion
     * @param alias alias de la afiliacion
     * @param tipoDoc
     * @param documento
     * @param email email del beneficiario
     * @param montoMax monto maximo
     * @param nombreBenef nombre del beneficiario
     * @param nombreCanal nombre del canal
     * @param nroCtaTDC
     * @param nombreBanco
     * @return RespuestaDTO indica si la operacion se realizo de manera exitosa o no
     */
    public RespuestaDTO actualizarAfiliacion(String idAfiliacion, String alias, char tipoDoc, String documento, String email, String montoMax, String nombreBenef, String nombreBanco, String nroCtaTDC, String nombreCanal);
    
    /**
     * Metodo para verificar que no ingrese un alias existente
     * @param codUsuario codigo del usuario
     * @param alias alias del beneficiario
     * @param nombreCanal nombre del canal
     * @return UtilDTO true si el alias existe false en caso contrario
     */
    public UtilDTO verificarAlias (String codUsuario, String alias, String nombreCanal);
    
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
    public RespuestaDTO insertarAfiliacion(String codUsuario, String alias, char tipoDoc, String documento, String email, String idTran, String montoMax, String nombreBanco, String nombreBenef, String nroCtaTDC, String nombreCanal);

    
       /**
     * Metodo para verificar que no ingrese un producto existente
     * @param codUsuario codigo del usuario
     * @param producto producto del beneficiario
     * @param nombreCanal nombre del canal
     * @return RespuestaDTO
     */
    public UtilDTO verificarProducto (String codUsuario, String producto, String nombreCanal);
}
