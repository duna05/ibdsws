/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.services;

import com.bds.ws.dao.IbUsuariosPjDAO;
import com.bds.ws.dto.IbConexionUsuarioPjDTO;
import com.bds.ws.dto.IbEmpresasUsuariosPjDTO;
import com.bds.ws.dto.IbPerfilesPjDTO;
import com.bds.ws.dto.IbUsuariosPjDTO;
import com.bds.ws.dto.UtilDTO;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;


/**
 *
 * @author ledwin.belen
 */
@WebService(serviceName = "IbUsuariosPjWs")
public class IbUsuariosPjWs {
    
    @EJB
    private IbUsuariosPjDAO usuariosPjDAO;


    /**
     * This is a sample web service operation
     */
    /**
     * Realiza la busqueda de un login en la base de datos
     *
     * @param login login del usuario
     * @param idCanal id de canal interno
     * @param codigoCanal codigo de canal en el CORE
     * @return UtilDTO
     */
    @WebMethod(operationName = "validarUsuarioLoginPJ")
    public UtilDTO validarUsuarioLoginPJ(@WebParam(name = "login") String login, @WebParam(name = "idCanal") String idCanal, @WebParam(name = "codigoCanal") String codigoCanal) {
        return usuariosPjDAO.validarUsuarioLogin(login, idCanal, codigoCanal);
    }

    /**
     * Realiza la validacion de un usuario en la base de datos
     *
     * @param password clave del usuario
     * @param login login del usuario
     * @param idSession codigo de la session del usuario que esta realizando la
     * peticion
     * @param idCanal id de canal interno
     * @param codigoCanal codigo de canal en el CORE
     * @return UtilDTO
     */
    @WebMethod(operationName = "validarLoginPJ")
    public UtilDTO validarLoginPJ(@WebParam(name = "password") String password, @WebParam(name = "login") String login, @WebParam(name = "idCanal") String idCanal, @WebParam(name = "codigoCanal") String codigoCanal, @WebParam(name = "idSession") String idSession) {
        return usuariosPjDAO.validarLogin(password, login, idCanal, codigoCanal, idSession);
    }

    /**
     * Metodo que se +encarga de registrar los datos de un usuario pj en la BD
     * Oracle 11++
     *
     *
     * @param nombreCompleto nombre completo del usuario
     * @param nroDoc numero de documento de indentificacion
     * @param tipoDoc tipo de documento del usuario
     * @param clave clave del usuario
     * @param login login del suaurio
     * @param estatusAcceso estatus de acceso del usuario
     * 1:Activo,2:Bloqueado,3:Inactivo(Vacaciones),4:Eliminado
     * @param estatusRegistro 1:Activo,3:PorCompletarAfiliacion
     * @param idCanal codigo interno del canal
     * @param codigoCanal codigo de canal en el CORE del banco
     * @return Objeto de Respuesta con el resultado de la transaccion.
     */
    @WebMethod(operationName = "insertarDatosIbUsuarioPj")
    public UtilDTO insertarDatosIbUsuarioPj(@WebParam(name = "nombreCompleto") String nombreCompleto,
            @WebParam(name = "nroDoc") String nroDoc,
            @WebParam(name = "tipoDoc") Character tipoDoc,
            @WebParam(name = "clave") String clave,
            @WebParam(name = "login") String login,
            @WebParam(name = "estatusAcceso") Character estatusAcceso,
            @WebParam(name = "estatusRegistro") Character estatusRegistro,
            @WebParam(name = "idCanal") String idCanal,
            @WebParam(name = "codigoCanal") String codigoCanal) {

        return usuariosPjDAO.insertarDatosIbUsuarioPj(nombreCompleto, nroDoc, tipoDoc, clave, login, estatusAcceso, estatusRegistro, idCanal, codigoCanal);
    }

    /**
     * Metodo que se +encarga de modificar los datos de un usuario pj en la BD
     * Oracle 11++
     *
     *
     * @param idUsuario id del usuario en IB
     * @param idEmpresa codigo de la empresa en IB
     * @param nombreCompleto nombre completo del usuario
     * @param nroDoc numero de documento de indentificacion
     * @param tipoDoc tipo de documento del usuario
     * @param clave clave del usuario
     * @param login login del suaurio
     * @param email email del usuario
     * @param telefonoOficina telefono de oficina del usuario
     * @param telefonoCelular telefono celular del usuario
     * @param representanteLegal 1:Si, 0:No
     * @param perfilAcceso
     * 1:Master,2:Admin,3:Autorizador,4:Ingresador,5:Consultor
     * @param estatusAcceso estatus de acceso del usuario *
     * 1:Activo,2:Bloqueado,3:Inactivo(Vacaciones),4:Eliminado
     * @param idCanal codigo interno del canal
     * @param codigoCanal codigo de canal en el CORE del banco
     * @return Objeto de Respuesta con el resultado de la transaccion.
     */
    @WebMethod(operationName = "modificarDatosIbUsuarioPj")
    public UtilDTO modificarDatosUsuarioPj(@WebParam(name = "idUsuario") String idUsuario,
            @WebParam(name = "idEmpresa") String idEmpresa,
            @WebParam(name = "nombreCompleto") String nombreCompleto,
            @WebParam(name = "nroDoc") String nroDoc,
            @WebParam(name = "tipoDoc") Character tipoDoc,
            @WebParam(name = "clave") String clave,
            @WebParam(name = "login") String login,
            @WebParam(name = "email") String email,
            @WebParam(name = "telefonoOficina") String telefonoOficina,
            @WebParam(name = "telefonoCelular") String telefonoCelular,
            @WebParam(name = "representanteLegal") Character representanteLegal,
            @WebParam(name = "perfilAcceso") String perfilAcceso,
            @WebParam(name = "estatusAcceso") Character estatusAcceso,
            @WebParam(name = "idCanal") String idCanal,
            @WebParam(name = "codigoCanal") String codigoCanal) {

        return usuariosPjDAO.modificarDatosIbUsuarioPj(idEmpresa, idUsuario, nombreCompleto, nroDoc, tipoDoc, clave, login, email, telefonoOficina, telefonoCelular, representanteLegal, perfilAcceso, estatusAcceso, idCanal, codigoCanal);
    }

    /**
     * Metodo que se +encarga de modificar los datos de un usuario pj en la BD
     * Oracle 11++
     *
     *
     * @param idUsuario id del usuario en IB
     * @param clave clave del usuario
     * @param periodoVigencia periodo de vigencia de la clave
     * @param login login del suaurio
     * @param estatusAcceso estatus de acceso del usuario *
     * 1:Activo,2:Bloqueado,3:Inactivo(Vacaciones),4:Eliminado
     * @param estatusRegistro 1: Activo, 2: En proceso Afiliacion, 3: Por
     * completar Afiliacion
     * @param idCanal codigo interno del canal
     * @param codigoCanal codigo de canal en el CORE del banco
     * @return Objeto de Respuesta con el resultado de la transaccion.
     */
    @WebMethod(operationName = "modificarLoginClaveUsuarioPj")
    public UtilDTO modificarLoginClaveUsuarioPj(@WebParam(name = "idUsuario") String idUsuario,
            @WebParam(name = "clave") String clave,
            @WebParam(name = "periodoVigencia") String periodoVigencia,
            @WebParam(name = "login") String login,
            @WebParam(name = "estatusAcceso") Character estatusAcceso,
            @WebParam(name = "estatusRegistro") Character estatusRegistro,
            @WebParam(name = "idCanal") String idCanal,
            @WebParam(name = "codigoCanal") String codigoCanal) {
        return usuariosPjDAO.modificarLoginClaveUsuarioPj(idUsuario, clave, periodoVigencia, login, estatusRegistro, estatusAcceso, idCanal, codigoCanal);
    }

    /**
     * Metodo que se +encarga de modificar los datos de un usuario pj en la BD
     * Oracle 11++
     *
     *
     * @param idUsuario id del usuario en IB
     * @param idCanal codigo interno del canal
     * @param codigoCanal codigo de canal en el CORE del banco
     * @return IbUsuariosPjDTO
     */
    @WebMethod(operationName = "actualizarIntentosPDD")
    public IbUsuariosPjDTO actualizarIntentosPDD(
            @WebParam(name = "idUsuario") String idUsuario,
            @WebParam(name = "idCanal") String idCanal,
            @WebParam(name = "codigoCanal") String codigoCanal) {
        return usuariosPjDAO.actualizarIntentosPDD(idUsuario, idCanal, codigoCanal);
    }

    /**
     * Metodo que se +encarga de consultar los UsuariosPj Oracle 11++
     *
     * @param tipoDocumento V,E,P
     * @param nroDocumento numero de cedula
     * @param codigoCanal codigo de canal en el CORE del banco
     * @param idCanal codigo interno del canal
     * @return the com.bds.ws.dto.UtilDTO
     */
    @WebMethod(operationName = "consultarUsuarioPjPorCedula")
    public UtilDTO consultarUsuarioPjPorCedula(
            @WebParam(name = "tipoDocumento") Character tipoDocumento,
            @WebParam(name = "nroDocumento") String nroDocumento,
            @WebParam(name = "codigoCanal") String codigoCanal,
            @WebParam(name = "idCanal") String idCanal
    ) {

        return usuariosPjDAO.consultarUsuarioPjPorCedula(tipoDocumento, nroDocumento, codigoCanal, idCanal);
    }

    /**
     * Metodo que consultar los usuarios juridicos Oracle 11++
     *
     *
     * @param perfilAcceso
     * 1:Master,2:Admin,3:Autorizador,4:Ingresador,5:Consultor
     * @param estatusRegistro
     * 1:Activo,2:EnProcesoAfiliacion,3:PorCompletarAfiliacion //SE PUEDEN
     * ENVIAR VARIOS SEPARADOS POR COMAS (,)
     * @param idEmpresa codigo interno de la empresa en IB
     * @param idCanal codigo interno del canal
     * @param codigoCanal codigo de canal en el CORE del banco
     * @return UtilDTO
     */
    @WebMethod(operationName = "consultarUsuariosSinReglasAproPj")
    public UtilDTO consultarUsuariosSinReglasAproPj(@WebParam(name = "perfilAcceso") String perfilAcceso,
            @WebParam(name = "estatusRegistro") int[] estatusRegistro,
            @WebParam(name = "idEmpresa") String idEmpresa,
            @WebParam(name = "idCanal") String idCanal,
            @WebParam(name = "codigoCanal") String codigoCanal) {

        return usuariosPjDAO.consultarUsuariosSinReglasAproPj(perfilAcceso, estatusRegistro, idEmpresa, idCanal, codigoCanal);
    }

    /**
     * Metodo que valida el Historico de Claves del usuario
     *
     *
     * @param idUsuario codigo del usuario interno en IB
     * @param clave cantidad de claves anteriores a validar
     * @param idCanal codigo interno del canal en IB
     * @param codigoCanal codigo de canal en el CORE del banco
     * @return UtilDTO true cuando es valida, false en caso contrario
     */
    @WebMethod(operationName = "validarHistoricoClavesUsuarioPj")
    public UtilDTO validarHistoricoClavesUsuarioPj(
            @WebParam(name = "idUsuario") String idUsuario,
            @WebParam(name = "clave") String clave,
            @WebParam(name = "idCanal") String idCanal,
            @WebParam(name = "codigoCanal") String codigoCanal) {

        return usuariosPjDAO.validarHistoricoClavesUsuarioPj(idUsuario, clave, idCanal, codigoCanal);
    }

    /**
     * Metodo que consulta las conexiones de un usuario y actualiza la fecha de
     * interaccion
     *
     *
     * @param idUsuario codigo del usuario interno en IB
     * @param idSession codigo de la session del usuario
     * @param idCanal codigo interno del canal en IB
     * @param codigoCanal codigo de canal en el CORE del banco
     * @return UtilDTO true cuando es valida, false en caso contrario
     */
    @WebMethod(operationName = "consultarConexionUsuarioPj")
    public IbConexionUsuarioPjDTO consultarConexionUsuarioPj(
            @WebParam(name = "idUsuario")   String idUsuario,
            @WebParam(name = "idCanal")     String idCanal,
            @WebParam(name = "codigoCanal") String codigoCanal,
            @WebParam(name = "idSession")   String idSession) {

        return usuariosPjDAO.consultarConexionUsuarioPj(idUsuario, idCanal, codigoCanal, idSession);
    }

    /**
     * Metodo que consulta los perfiles activos de un usuario
     *
     *
     * @param estatus A = activo, I = inactivo
     * @param idCanal codigo de canal interno en IB
     * @param codigoCanal codigo de canal en el CORE del banco
     * @return UtilDTO true cuando es valida, false en caso contrario
     */
    @WebMethod(operationName = "consultarPerfilesPj")
    public IbPerfilesPjDTO consultarPerfilesPj(
            @WebParam(name = "estatus") String estatus,
            @WebParam(name = "idCanal") String idCanal,
            @WebParam(name = "codigoCanal") String codigoCanal) {

        return usuariosPjDAO.consultarPerfilesPj(estatus, idCanal, codigoCanal);
    }

    /**
     * Metodo que consulta los perfiles activos de un usuario
     *
     *
     * @param estatus A = activo, I = inactivo
     * @param idEmpresa codigo interno de la empresa en IB
     * @param idCanal codigo de canal interno en IB
     * @param codigoCanal codigo de canal en el CORE del banco
     * @return UtilDTO true cuando es valida, false en caso contrario
     */
    @WebMethod(operationName = "consultarPerfilesEmpresaDisponiblePj")
    public IbPerfilesPjDTO consultarPerfilesEmpresaDisponiblePj(
            @WebParam(name = "estatus") String estatus,
            @WebParam(name = "idEmpresa") String idEmpresa,
            @WebParam(name = "idCanal") String idCanal,
            @WebParam(name = "codigoCanal") String codigoCanal) {

        return usuariosPjDAO.consultarPerfilesPj(estatus, idCanal, codigoCanal);
    }

    /**
     * Metodo que cierra la conexion de un usuario
     *
     *
     * @param idUsuario codigo del usuario interno en IB
     * @param idCanal codigo interno del canal en IB
     * @param codigoCanal codigo de canal en el CORE del banco
     * @return UtilDTO true cuando es valida, false en caso contrario
     */
    @WebMethod(operationName = "cerrarConexionUsuarioPj")
    public UtilDTO cerrarConexionUsuarioPj(
            @WebParam(name = "idUsuario") String idUsuario,
            @WebParam(name = "idCanal") String idCanal,
            @WebParam(name = "codigoCanal") String codigoCanal) {

        return usuariosPjDAO.cerrarConexionUsuarioPj(idUsuario, idCanal, codigoCanal);
    }

    /**
     * Metodo que resetea el login y el password de un IbUsuarioPj
     *
     *
     * @param idUsuario codigo del usuario interno en IB
     * @param idCanal codigo interno del canal en IB
     * @param codigoCanal codigo de canal en el CORE del banco
     * @return UtilDTO true cuando es valida, false en caso contrario
     */
    @WebMethod(operationName = "resetearIbUsuarioPj")
    public UtilDTO resetearIbUsuarioPj(
            @WebParam(name = "idUsuario") String idUsuario,
            @WebParam(name = "idCanal") String idCanal,
            @WebParam(name = "codigoCanal") String codigoCanal) {
        return usuariosPjDAO.resetearIbUsuarioPj(idUsuario, idCanal, codigoCanal);
    }
    
    /**
     * Metodo que desbloquea a un usuario de la base de datos
     *
     *
     * @param idUsuario codigo del usuario interno en IB
     * @param idCanal codigo interno del canal en IB
     * @param codigoCanal codigo de canal en el CORE del banco
     * @return UtilDTO true cuando es valida, false en caso contrario
     */
    @WebMethod(operationName = "desbloquearIbUsuarioPj")
    public UtilDTO desbloquearIbUsuarioPj(
            @WebParam(name = "idUsuario")   String idUsuario,
            @WebParam(name = "idCanal")     String idCanal,
            @WebParam(name = "codigoCanal") String codigoCanal) {
        return usuariosPjDAO.desbloquearIbUsuarioPj(idUsuario, idCanal, codigoCanal);
    }

    @WebMethod(operationName = "IbEmpresasUsuariosPjDTOP")
    public IbEmpresasUsuariosPjDTO IbEmpresasUsuariosPjDTOP(@WebParam(name = "prueba") String prueba) {
        return null;
    }
}
