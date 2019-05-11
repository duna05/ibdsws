/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dao;

import com.bds.ws.dto.IbCanalDTO;
import com.bds.ws.dto.IbUsuariosCanalesDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.dto.UtilDTO;
import java.math.BigDecimal;

/**
 * Interfaz para canales
 * @author renseld.lugo
 */
public interface IbCanalesDAO { 

    /**
     * Obtiene la fecha de la ultima conexion realizada por el cliente al canal.
     *
     * @param idUsuario String id del cliente.
     * @param idCanal String idCanal por el cual se ejecuta la transaccion.
     * @param nombreCanal String nombreCanal por el cual se ejecuta la
     * transaccion.
     * @return UtilDTO con fecha de la ultima conexion realizada
     */
    public UtilDTO consultarUltimaConexionCanal(String idUsuario, String idCanal, String nombreCanal);

    /**
     * Realiza el bloqueo de acceso al canal .
     *
     * @param idUsuario String id del cliente.
     * @param idCanal String idCanal por el cual se ejecuta la transaccion.
     * @param nombreCanal String nombreCanal por el cual se ejecuta la
     * transaccion.
     * @return RespuestaDTO indicando si el metodo se realizo de manera exitosa o no
     */
    public RespuestaDTO bloquearAccesoCanal(String idUsuario, String idCanal, String nombreCanal);

    /**
     * Valida que no exista una conexion antes de iniciar session.
     * @param idUsuario String id del cliente.
     * @param idCanal String idCanal por el cual se ejecuta la transaccion.
     * @param nombreCanal String nombreCanal por el cual se ejecuta la
     * transaccion.
     * @return UtilDTO retorna true si existe una conexion false en caso contrario
     */
    public UtilDTO validarConexionSimultanea(String idUsuario, String idCanal, String nombreCanal);

    /**
     * metodo para validar el acceso a la banca movil via app mobile NOTA
     * estemetodo es temporal
     *
     * @param numeroTarjeta numero de tarjeta de 20 digitos
     * @param password password de acceso al IB
     * @param idCanal String idCanal por el cual se ejecuta la transaccion.
     * @param nombreCanal String nombreCanal por el cual se ejecuta la
     * transaccion.
     * @return IbParametrosDTO con el nombre del usuario y la fecha dela ultima
     * conexion si el acceso es correcto
     */
    public UtilDTO loginIB(String numeroTarjeta, String password, String idCanal, String nombreCanal);

    /**
     * inserta la realcion de usuario con canal
     *
     * @param codUsuario el codigo del usuario a asociar
     * @param idSesion indica el id de la sesion
     * @param idCanal String idCanal por el cual se ejecuta la transaccion.
     * @param nombreCanal String nombreCanal por el cual se ejecuta la
     * transaccion.
     * @return RespuestaDTO indicando si el metodo se realizo de manera exitosa o no
     */
    public RespuestaDTO insertarUsuarioCanal(String codUsuario, String idSesion, String idCanal, String nombreCanal);

    /**
     * Actualiza la realcion de usuario con canal
     *
     * @param idUsuario el id del usuario a asociar
     * @param idCanal String idCanal por el cual se ejecuta la transaccion.
     * @param nombreCanal String nombreCanal por el cual se ejecuta la
     * transaccion.
     * @param estatusConexion Char estatus de la conexion.
     * @param intentosFallidos cant de intentos fallidos de acceso
     * @param idSesion identificador unico de la session
     * @param estatusAcceso estatus de acceso al canal
     * @param estatusRegistro estatus de registro
     * @param limiteInternas limite de monto de operaciones propias en DELSUR
     * @param limiteExternas limite de monto de operaciones propias en otros bancos
     * @param limiteInternasTerc limite de monto de operaciones terceros en DELSUR
     * @param limiteExternasTerc limite de monto de operaciones terceros en otros bancos
     * @return RespuestaDTO indica si el metodo se realizo de manera exitosa o no
     */
    public RespuestaDTO actualizarUsuarioCanal(String idUsuario, String idCanal, String nombreCanal, char estatusConexion, int intentosFallidos,  String idSesion, 
           String estatusAcceso, String estatusRegistro, BigDecimal limiteInternas, BigDecimal limiteExternas, BigDecimal limiteInternasTerc, BigDecimal limiteExternasTerc);

    /**
     * Metodo para consultar IB_USUARIOS_CANALES por el id del usuario
     * @param idUsuario id del usuario
     * @param idcanal id del canal
     * @return IbUsuariosCanalesDTO modelo de IB_USUARIOS_CANALES
     */
    public IbUsuariosCanalesDTO consultarUsuarioCanalporIdUsuario(String idUsuario, String idcanal);

    /**
     * Metodo para validar el estatus de registro y de acceso del usuario en
     * IB_USUARIOS_CANALES
     *
     * @param codUsuario codigo del usuario
     * @param idCanal id del canal 
     * @param nombreCanal nombre del canal
     * @return Si esta bloqueado en alguno de los dos retorna el codigoSP y
     * textoSP
     */
    public RespuestaDTO validarUsuarioAccesoRegistro(String codUsuario, String idCanal, String nombreCanal);
    
    /**
     * Metodo para obtener el listado de canales
     * @param codUsuario codigo del usuario
     * @param nombreCanal nombre del canal
     * @return  IbCanalDTO listado de canales
     */
    public IbCanalDTO listadoCanales (String codUsuario, String nombreCanal);
    
     /**
     * metodo para validar que un TDD se encuentre activa en Oracle9
     * @param numeroTDD numero de la TDD a validar
     * @param clave contrasenna de acceso encriptada
     * @param idCanal identificador unico del canal
     * @param nombreCanal String nombreCanal por el cual se ejecuta la
     * transaccion.
     * @return UtilDTO con el resultardo de la validacion del bin
     *
     */
    public UtilDTO validarLogin(String numeroTDD, String clave,  String idCanal, String nombreCanal); 
    
    /**
     * metodo que retorna la TDD activa para un codigo de cliente en Oracle9
     *
     * @param codCliente codigo de cliente Ora9
     * @param nombreCanal String nombreCanal por el cual se ejecuta la
     * transaccion.
     * @return UtilDTO con el resultardo de la validacion del la TDD en Ora9
     *
     */

    public UtilDTO validarTDDActivaCore(String codCliente, String nombreCanal);
      /**
     * metodo que retorna la TDD activa para un codigo de cliente en Oracle9
     *
     * @param codCliente codigo de cliente Ora9
     * @param nombreCanal String nombreCanal por el cual se ejecuta la
     * @param numeroTDD String TDD
     * transaccion.
     * @return UtilDTO con el resultardo de la validacion del la TDD en Ora9
     *
     */
    public UtilDTO validarTDDActivaCore(String codCliente, String nombreCanal,String numeroTDD);
    
    
    /**
     * Metodo para obtener el listado de canales por usuario
     * @param idUsuario id del usuario
     * @param idcanal id del canal
     * @param nombreCanal nombre del canal
     * @return  IbCanalDTO listado de canales
     */
    public IbCanalDTO consultaCanalesPorUsuario (String idUsuario,String idcanal,String nombreCanal);
    
    
}
