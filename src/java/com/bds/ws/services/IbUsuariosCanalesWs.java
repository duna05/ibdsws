/*
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.services;

import com.bds.ws.dao.IbCanalesDAO;
import com.bds.ws.dao.IbClaveDAO;
import com.bds.ws.dao.IbUsuarioDAO;
import com.bds.ws.dto.IbCanalDTO;
import com.bds.ws.dto.IbHistoricoClavesDTO;
import com.bds.ws.dto.IbUsuarioDTO;
import com.bds.ws.dto.IbUsuariosCanalesDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.dto.UtilDTO;
import java.math.BigDecimal;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * Clase de servicios relacionados a usuariosCanales
 * @author rony.rodriguez
 */
@WebService(serviceName = "IbUsuariosCanalesWs")
public class IbUsuariosCanalesWs {

    @EJB
    private IbCanalesDAO canalesDAO;

    @EJB
    private IbUsuarioDAO usuarioDAO;
    
    @EJB
    private IbClaveDAO claveDAO;

    /**
     * Obtiene la fecha de la ultima conexion realizada por el cliente al canal.
     *
     * @param idUsuario String id del cliente.
     * @param idCanal String idCanal por el cual se ejecuta la transaccion.
     * @param nombreCanal String nombreCanal por el cual se ejecuta la
     * transaccion.
     * @return UtilDTO con fecha de la ultima conexion realizada
     */
    @WebMethod(operationName = "consultarUltimaConexionCanal")
    public UtilDTO consultarUltimaConexionCanal(@WebParam(name = "idUsuario") String idUsuario, @WebParam(name = "idCanal") String idCanal, @WebParam(name = "nombreCanal") String nombreCanal) {
        return canalesDAO.consultarUltimaConexionCanal(idUsuario, idCanal, nombreCanal);
    }

    /**
     * Realiza el bloqueo de acceso al canal .
     *
     * @param idUsuario String id del cliente.
     * @param idCanal String idCanal por el cual se ejecuta la transaccion.
     * @param nombreCanal String nombreCanal por el cual se ejecuta la
     * transaccion.
     * @return RespuestaDTO indicando si el metodo se realizo de manera exitosa o no
     */
    @WebMethod(operationName = "bloquearAccesoCanal")
    public RespuestaDTO bloquearAccesoCanal(@WebParam(name = "idUsuario") String idUsuario, @WebParam(name = "idCanal") String idCanal, @WebParam(name = "nombreCanal") String nombreCanal) {
        return canalesDAO.bloquearAccesoCanal(idUsuario, idCanal, nombreCanal);
    }

    /**
     * Valida que no exista una conexion antes de iniciar session.
     * @param idUsuario String id del cliente.
     * @param idCanal String idCanal por el cual se ejecuta la transaccion.
     * @param nombreCanal String nombreCanal por el cual se ejecuta la
     * transaccion.
     * @return UtilDTO retorna true si existe una conexion false en caso contrario
     */
    @WebMethod(operationName = "validarConexionSimultanea")
    public UtilDTO validarConexionSimultanea(@WebParam(name = "idUsuario") String idUsuario, @WebParam(name = "idCanal") String idCanal, @WebParam(name = "nombreCanal") String nombreCanal) {
        return canalesDAO.validarConexionSimultanea(idUsuario, idCanal, nombreCanal);
    }

    /**
     * Metodo que obtiene un Usuario por canal
     * @param idUsuario String Codigo del usuario.
     * @param idCanal String idCanal por el cual se ejecuta la transaccion.
     * @param nombreCanal String nombreCanal por el cual se ejecuta la transaccion.
     * @return IbUsuariosCanalesDTO modelo de ib_usuario_Canal
     */
    @WebMethod(operationName = "obtenerIbUsuarioPorCanal")
    public IbUsuariosCanalesDTO obtenerIbUsuarioPorCanal(@WebParam(name = "idUsuario") String idUsuario, @WebParam(name = "idCanal") String idCanal, @WebParam(name = "nombreCanal") String nombreCanal) {
        return usuarioDAO.obtenerIbUsuarioPorCanal(idUsuario, idCanal, nombreCanal);
    }

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
    @WebMethod(operationName = "loginIB")
    public UtilDTO loginIB(@WebParam(name = "numeroTarjeta") String numeroTarjeta, @WebParam(name = "password") String password, @WebParam(name = "idCanal") String idCanal, @WebParam(name = "nombreCanal") String nombreCanal) {
        return canalesDAO.loginIB(numeroTarjeta, password, idCanal, nombreCanal);
    }

    /**
     * Metodo que se encarga de registrar los datos de un cliente en la BD
     * Oracle 11
     * @param codigoCliente codigo de cliente en DelSur
     * @param nroTDD        numero de TDD afiliada
     * @param tipoDoc       tipo de documento V,E,J
     * @param nroDocumento        numero de documento de indentidad
     * @param email         email del cliente
     * @param nombreCompleto nombres y apellidos
     * @param telfCelularCompleto codigo y numero de telefono
     * @param idPerfil       identificador del perfil
     * @param clave          clave de acceso a la aplicacion
     * @param idCanal String idCanal por el cual se ejecuta la transaccion.
     * @param nombreCanal String nombreCanal por el cual se ejecuta la transaccion.
     * @return Objeto de Respuesta con el resultado de la transaccion.
     */
    @WebMethod(operationName = "insertarDatosIbUsuario")
    public RespuestaDTO insertarDatosIbUsuario(@WebParam(name = "codigoCliente") String codigoCliente, @WebParam(name = "nroTDD") String nroTDD, @WebParam(name = "tipoDoc") char tipoDoc,
            @WebParam(name = "nroDocumento") String nroDocumento, @WebParam(name = "email") String email, @WebParam(name = "nombreCompleto") String nombreCompleto,
            @WebParam(name = "telfCelularCompleto") String telfCelularCompleto, @WebParam(name = "idPerfil") BigDecimal idPerfil,@WebParam(name = "clave") String clave, @WebParam(name = "idCanal") String idCanal, @WebParam(name = "nombreCanal") String nombreCanal) {
        return usuarioDAO.insertarDatosIbUsuario(codigoCliente, nroTDD, tipoDoc, nroDocumento, email, nombreCompleto, telfCelularCompleto, idPerfil,clave, idCanal, nombreCanal);
    }

    /**
     * Metodo que obtiene un Usuario por codigo de cliente
     * @param codigoCliente String Codigo del usuario.
     * @param idCanal String idCanal por el cual se ejecuta la transaccion.
     * @param nombreCanal String nombreCanal por el cual se ejecuta la transaccion.
     * @return IbUsuarioDTO un Usuario del modelo de ib_usuarios
     */
    @WebMethod(operationName = "obtenerIbUsuarioPorCodUsuario")
    public IbUsuarioDTO obtenerIbUsuarioPorCodUsuario(@WebParam(name = "codigoCliente") String codigoCliente, @WebParam(name = "idCanal") String idCanal, @WebParam(name = "nombreCanal") String nombreCanal) {
        return usuarioDAO.obtenerIbUsuarioPorCodusuario(codigoCliente, idCanal, nombreCanal);
    }

    /**
     * Metodo que obtiene un Usuario por tdd
     * @param tdd numero de TDD
     * @param idCanal String idCanal por el cual se ejecuta la transaccion.
     * @param nombreCanal String nombreCanal por el cual se ejecuta la transaccion.
     * @return IbUsuarioDTO un Usuario del modelo de ib_usuarios
     */
    @WebMethod(operationName = "obtenerIbUsuarioPorTDD")
    public IbUsuarioDTO obtenerIbUsuarioPorTDD(@WebParam(name = "tdd") String tdd, @WebParam(name = "idCanal") String idCanal, @WebParam(name = "nombreCanal") String nombreCanal) {
        return usuarioDAO.obtenerIbUsuarioPorTDD(tdd, idCanal, nombreCanal);
    }
 
    /**
     * metodo para crear la relacion entre un usuario y un canal
     * @param codigoCliente String codigo de cliente de Oracle9
     * @param idSesion indica el id de la sesion
     * @param idCanal String identificador extendido del canal
     * @param nombreCanal String nombreCanal por el cual se ejecuta la transaccion.
     * @return RespuestaDTO
     */
    @WebMethod(operationName = "insertarUsuarioCanal")
    public RespuestaDTO insertarUsuarioCanal(@WebParam(name = "codigoCliente") String codigoCliente, @WebParam(name = "idSesion") String idSesion, @WebParam(name = "idCanal") String idCanal, @WebParam(name = "nombreCanal") String nombreCanal) {
        return canalesDAO.insertarUsuarioCanal(codigoCliente, idSesion, idCanal, nombreCanal);
    }

    /**
     * inserta la realcion de usuario con canal
     *
     * @param idUsuario el id del usuario a asociar
     * @param idCanal String idCanal por el cual se ejecuta la transaccion.
     * @param nombreCanal String nombreCanal por el cual se ejecuta la
     * transaccion.
     * @param estatusConexion estatus actual de la conexion del usuario
     * @param intentosFallidos cantidad de intentos fallidos
     * @param idSesion identificador unico de la session
     * @param estatusAcceso estatus de acceso al canal
     * @param estatusRegistro estatus de registro
     * @param limiteInternas limite de monto de operaciones propias en DELSUR
     * @param limiteExternas limite de monto de operaciones propias en otros bancos
     * @param limiteInternasTerc limite de monto de operaciones terceros en DELSUR
     * @param limiteExternasTerc limite de monto de operaciones terceros en otros bancos
     * @return RespuestaDTO indicando si el metodo se realizo de manera exitosa o no
     */
    @WebMethod(operationName = "actualizarUsuarioCanal")

    public RespuestaDTO actualizarUsuarioCanal(@WebParam(name = "idUsuario") String idUsuario, @WebParam(name = "idCanal") String idCanal, @WebParam(name = "nombreCanal") String nombreCanal, 
            @WebParam(name = "estatusConexion") char estatusConexion,  @WebParam(name = "intentosFallidos") int intentosFallidos,  @WebParam(name = "idSesion") String idSesion,
            @WebParam(name = "estatusAcceso") String estatusAcceso, @WebParam(name = "estatusRegistro") String estatusRegistro,@WebParam(name = "limiteInternas") BigDecimal limiteInternas, 
            @WebParam(name = "limiteExternas") BigDecimal limiteExternas, @WebParam(name = "limiteInternasTerc") BigDecimal limiteInternasTerc, @WebParam(name = "limiteExternasTerc") BigDecimal limiteExternasTerc) {
        return canalesDAO.actualizarUsuarioCanal(idUsuario, idCanal, nombreCanal, estatusConexion, intentosFallidos, idSesion, 
                estatusAcceso, estatusRegistro, limiteInternas, limiteExternas, limiteInternasTerc, limiteExternasTerc);
    }

    /**
     * Metodo para consultar IB_USUARIOS_CANALES por el id del usuario
     * @param idUsuario id del usuario
     * @param idCanal id del canal
     * @return IbUsuariosCanalesDTO modelo de IB_USUARIOS_CANALES
     */
    @WebMethod(operationName = "usuarioCanalporIdUsuario")
    public IbUsuariosCanalesDTO usuarioCanalporIdUsuario(@WebParam(name = "idUsuario") String idUsuario, @WebParam(name = "idCanal") String idCanal) {
        return canalesDAO.consultarUsuarioCanalporIdUsuario(idUsuario, idCanal);
    }

    /**
     * Metodo para validar el estatus de registro y de acceso del usuario en IB_USUARIOS_CANALES
     * @param codUsuario codigo del usuario
     * @param idCanal id del canal 
     * @param nombreCanal nombre del canal
     * @return Si esta bloqueado en alguno de los dos retorna el codigoSP y textoSP
     */
    @WebMethod(operationName = "validarUsuarioAccesoRegistro")
    public RespuestaDTO validarUsuarioAccesoRegistro(@WebParam(name = "codUsuario") String codUsuario, @WebParam(name = "idCanal") String idCanal, @WebParam(name = "nombreCanal") String nombreCanal) {
        return canalesDAO.validarUsuarioAccesoRegistro(codUsuario, idCanal, nombreCanal);
    }

    /**
     * Metodo para actualizar los datos del usuario del core a la bd del IB
     * @param codigoCliente codigo del cliente
     * @param nroTDD numero de tarjeta de debito
     * @param tipoDoc tipo de documento 'V' 'J'
     * @param nroDocumento numero de cedula
     * @param email email del usuario
     * @param nombreCompleto nombre completo
     * @param telfCelularCompleto telefono celular del usuario
     * @param clave          clave de acceso a la aplicacion
     * @param nombreCanal nombre del canal
     * @return RespuestaDTO indicando si el metodo se realizo de manera exitosa o no
     */
    @WebMethod(operationName = "actualizarDatosUsuario")
    public RespuestaDTO actualizarDatosUsuario(@WebParam(name = "codigoCliente") String codigoCliente, @WebParam(name = "nroTDD") String nroTDD, @WebParam(name = "tipoDoc") char tipoDoc,
            @WebParam(name = "nroDocumento") String nroDocumento, @WebParam(name = "email") String email, @WebParam(name = "nombreCompleto") String nombreCompleto,
            @WebParam(name = "telfCelularCompleto") String telfCelularCompleto, @WebParam(name = "clave") String clave, @WebParam(name = "nombreCanal") String nombreCanal) {
        return usuarioDAO.actualizarDatosUsuario(codigoCliente, nroTDD, tipoDoc, nroDocumento, email, nombreCompleto, telfCelularCompleto, clave, nombreCanal);
    }
    
    /**
     * Metodo para obtener el listado de canales
     * @param codUsuario codigo del usuario
     * @param nombreCanal nombre del canal
     * @return  IbCanalDTO listado de canales
     */
    @WebMethod(operationName = "listadoCanales")
    public IbCanalDTO listadoCanales(@WebParam(name = "codUsuario") String codUsuario, @WebParam(name = "nombreCanal") String nombreCanal) {
        return canalesDAO.listadoCanales(codUsuario, nombreCanal);
    }
    
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
    @WebMethod(operationName = "validarLogin")
    public UtilDTO validarLogin(@WebParam(name = "numeroTDD") String numeroTDD, @WebParam(name = "clave") String clave, @WebParam(name = "idCanal") String idCanal, @WebParam(name = "nombreCanal") String nombreCanal) {
        return canalesDAO.validarLogin(numeroTDD ,clave, idCanal, nombreCanal);
    } 
    
    /**
     * metodo que retorna la TDD activa para un codigo de cliente en Oracle9
     *
     * @param codCliente codigo de cliente Ora9
     * @param nombreCanal String nombreCanal por el cual se ejecuta la
     * transaccion.
     * @return UtilDTO con el resultardo de la validacion del la TDD en Ora9
     *
     */
   @WebMethod(operationName = "validarTDDActivaCore")
    public UtilDTO validarTDDActivaCore(@WebParam(name = "codCliente") String codCliente,@WebParam(name = "nombreCanal") String nombreCanal,@WebParam(name = "numeroTDD") String numeroTDD){
        return canalesDAO.validarTDDActivaCore( codCliente, nombreCanal,numeroTDD); 
    } 
    
    /**
     * Método que se encarga de consultar si la nueva clave ya se encuentra entre las
     * últimas N claves que el cliente ha tenido
     * @param idUsuario
     * @param clave
     * @param cantClaves
     * @param nombreCanal
     * @return UtilDTO
     */
    @WebMethod(operationName = "existeEnUltimasNClaves")
    public UtilDTO existeEnUltimasNClaves(@WebParam(name = "idUsuario") String idUsuario, @WebParam(name = "clave") String clave, @WebParam(name = "cantClaves") String cantClaves, @WebParam(name = "nombreCanal") String nombreCanal) {
        return claveDAO.existeEnUltimasNClaves(idUsuario, clave, cantClaves, nombreCanal);
    }
    
   /**
     * Método que se encarga de actualizar la nueva clave en IB_USUARIOS y de insertar
     * en la tabla IB_HISTORICO_CLAVES la última clave actualizada por el usuario así como el
     * período de validez de la misma.
     * @param idUsuario
     * @param clave
     * @param periodoClave
     * @param nombreCanal
     * @return RespuestaDTO
     */
    @WebMethod(operationName = "crearClave")
    public RespuestaDTO crearClave(@WebParam(name = "idUsuario") String idUsuario, @WebParam(name = "clave") String clave, @WebParam(name = "periodoClave") String periodoClave, @WebParam(name = "nombreCanal") String nombreCanal) {
        return claveDAO.crearClave(idUsuario, clave, periodoClave, nombreCanal);
    }
    
    /**
     * Método que se encarga de retornar la última clave que tiene el cliente
     * activa
     * @param idUsuario
     * @param nombreCanal
     * @return IbHistoricoClavesDTO
     */
    @WebMethod(operationName = "obtenerUltimaClave")
    public IbHistoricoClavesDTO obtenerUltimaClave(@WebParam(name = "idUsuario") String idUsuario, @WebParam(name = "nombreCanal") String nombreCanal) {
        return claveDAO.obtenerUltimaClave(idUsuario, nombreCanal);
    }
    
    /**
     * Metodo para consultar la cantidad de intentos fallidos de preguntas de
     * seguridad
     *
     * @param idUsuario identificador del usuario
     * @param nroTDD nro de TDD del usuario
     * @param nombreCanal nombre del canal
     * @return RespuestaDTO
     */
    @WebMethod(operationName = "cantidadPregSegFallidas")
    public UtilDTO cantidadPregSegFallidas(@WebParam(name = "idUsuario") String idUsuario,@WebParam(name = "nroTDD") String nroTDD, @WebParam(name = "nombreCanal") String nombreCanal){
        return usuarioDAO.cantidadPregSegFallidas(idUsuario, nroTDD, nombreCanal);
    }
    

    /**
     * Metodo actualiza la cantidad de intentos fallidos de las preguntas de
     * seguridad
     *
     * @param idUsuario identificador del usuario
     * @param cantIntentos cantidad de intentos fallidos a actualizar
     * @param nombreCanal nombre del canal
     * @return RespuestaDTO
     */
    @WebMethod(operationName = "actualizarPregSegFallidas")
    public RespuestaDTO actualizarPregSegFallidas(@WebParam(name = "idUsuario") String idUsuario,@WebParam(name = "cantIntentos")  int cantIntentos,@WebParam(name = "nombreCanal")  String nombreCanal){
        return usuarioDAO.actualizarPregSegFallidas(idUsuario, cantIntentos, nombreCanal);
    }
    
        /**
     * Metodo para obtener el listado de canales por usuario
     * @param idUsuario id del usuario
     * @param idCanal id del canal
     * @param nombreCanal nombre del canal
     * @return  IbCanalDTO listado de canales
     */
    
     @WebMethod(operationName = "consultaCanalesPorUsuario")
     public IbCanalDTO   consultaCanalesPorUsuario(@WebParam(name = "idUsuario") String idUsuario, @WebParam(name = "idCanal") String idCanal,@WebParam(name = "nombreCanal")  String nombreCanal)  {
         return canalesDAO.consultaCanalesPorUsuario(idUsuario, idCanal, nombreCanal);
     } 
     
     /**
     * Metodo para consultar la la TDD de un usuario por su Documento de Identidad
     *
     * @param tipoDoc tipo de documento
     * @param nroDoc nro de TDD del usuario
     * @param nombreCanal nombre del canal
     * @return RespuestaDTO
     */
    @WebMethod(operationName = "obtenerTDDPorDoc")
    public UtilDTO obtenerTDDPorDoc(@WebParam(name = "tipoDoc") String tipoDoc,@WebParam(name = "nroDoc") String nroDoc, @WebParam(name = "nombreCanal") String nombreCanal){
        return usuarioDAO.obtenerTDDPorDoc(tipoDoc, nroDoc, nombreCanal);
    }
    
    
    
    
    
}
