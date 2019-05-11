/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dao;

import com.bds.ws.dto.IbUsuarioDTO;
import com.bds.ws.dto.IbUsuariosCanalesDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.dto.UtilDTO;
import java.math.BigDecimal;

/**
 * Interfaz DAO para Usuarios
 * @author juan.faneite
 */
public interface IbUsuarioDAO {

    /**
     * Metodo que obtiene un Usuario por canal
     *
     * @param codUsuario String Codigo del usuario.
     * @param idCanal String idCanal por el cual se ejecuta la transaccion.
     * @param nombreCanal String nombreCanal por el cual se ejecuta la
     * transaccion.
     * @return IbUsuariosCanalesDTO modelo de ib_usuario_Canal
     */
    public IbUsuariosCanalesDTO obtenerIbUsuarioPorCanal(String codUsuario, String idCanal, String nombreCanal);

    /**
     * Metodo que se encarga de registrar los datos de un cliente en la BD
     * Oracle 11
     *
     * @param codigoCliente codigo de cliente en DelSur
     * @param nroTDD numero de TDD afiliada
     * @param tipoDoc tipo de documento V,E,J
     * @param nroDoc numero de documento de indentidad
     * @param email email del cliente
     * @param nombreCompleto nombres y apellidos
     * @param telfCelularCompleto codigo y numero de telefono
     * @param idPerfil identificador del perfil
     * @param clave          clave de acceso a la aplicacion
     * @param idCanal String idCanal por el cual se ejecuta la transaccion.
     * @param nombreCanal String nombreCanal por el cual se ejecuta la
     * transaccion.
     * @return Objeto de Respuesta con el resultado de la transaccion.
     */
    public RespuestaDTO insertarDatosIbUsuario(String codigoCliente, String nroTDD, char tipoDoc, String nroDoc,
            String email, String nombreCompleto, String telfCelularCompleto, BigDecimal idPerfil, String clave, String idCanal, String nombreCanal);

    /**
     * Metodo que obtiene un Usuario por codigo de cliente
     *
     * @param codUsuario String Codigo del usuario.
     * @param idCanal String idCanal por el cual se ejecuta la transaccion.
     * @param nombreCanal String nombreCanal por el cual se ejecuta la
     * transaccion.
     * @return IbUsuarioDTO un Usuario del modelo de ib_usuarios
     */
    public IbUsuarioDTO obtenerIbUsuarioPorCodusuario(String codUsuario, String idCanal, String nombreCanal);

    /**
     * Metodo que obtiene un Usuario por tdd
     *
     * @param tdd numero de TDD
     * @param idCanal String idCanal por el cual se ejecuta la transaccion.
     * @param nombreCanal String nombreCanal por el cual se ejecuta la
     * transaccion.
     * @return IbUsuarioDTO un Usuario del modelo de ib_usuarios
     */
    public IbUsuarioDTO obtenerIbUsuarioPorTDD(String tdd, String idCanal, String nombreCanal);

    /**
     * Metodo para actualizar los datos del usuario del core a la bd del IB
     * @param codigoCliente codigo del cliente
     * @param nroTDD numero de tarjeta de debito
     * @param tipoDoc tipo de documento 'V' 'J'
     * @param nroDoc numero de cedula
     * @param email email del usuario
     * @param nombreCompleto nombre completo
     * @param telfCelularCompleto telefono celular del usuario
     * @param clave          clave de acceso a la aplicacion
     * @param nombreCanal nombre del canal
     * @return RespuestaDTO indicando si el metodo se realizo de manera exitosa o no
     */
    public RespuestaDTO actualizarDatosUsuario(String codigoCliente, String nroTDD, char tipoDoc, String nroDoc,
            String email, String nombreCompleto, String telfCelularCompleto, String clave, String nombreCanal);
    
    
    /**
     * Metodo para consultar la cantidad de intentos fallidos de preguntas de
     * seguridad
     *
     * @param idUsuario identificador del usuario
     * @param nroTDD nro de TDD del usuario
     * @param nombreCanal nombre del canal
     * @return RespuestaDTO
     */
    public UtilDTO cantidadPregSegFallidas(String idUsuario, String nroTDD, String nombreCanal);

    /**
     * Metodo actualiza la cantidad de intentos fallidos de las preguntas de
     * seguridad
     *
     * @param idUsuario identificador del usuario
     * @param cantIntentos cantidad de intentos fallidos a actualizar
     * @param nombreCanal nombre del canal
     * @return RespuestaDTO
     */
    public RespuestaDTO actualizarPregSegFallidas(String idUsuario, int cantIntentos, String nombreCanal);
    
    /**
     * Metodo para consultar la la TDD de un usuario por su Documento de Identidad
     *
     * @param tipoDoc tipo de documento
     * @param nroDoc nro de TDD del usuario
     * @param nombreCanal nombre del canal
     * @return RespuestaDTO
     */
    public UtilDTO obtenerTDDPorDoc(String tipoDoc, String nroDoc, String nombreCanal);
}
