/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dao;

import com.bds.ws.dto.IbConexionUsuarioPjDTO;
import com.bds.ws.dto.IbPerfilesPjDTO;
import com.bds.ws.dto.IbUsuariosPjDTO;
import com.bds.ws.dto.UtilDTO;
import java.math.BigDecimal;

/**
 *
 * @author luis.perez
 */
public interface IbUsuariosPjDAO {
    /**
     * Metodo que se encarga de validar la existencia del login de un usuario
     * Oracle 11
     *
     * @param login login del usuario
     * @param idCanal id de canal interno
     * @param codigoCanal codigo de canal en el CORE
     * @return UtilDTO
     */
    public UtilDTO validarUsuarioLogin(String login, String idCanal, String codigoCanal);
    
    /**
     * Metodo que se encarga de validar la existencia del login de un usuario
     * Oracle 11
     *
     * @param password clave del usuario
     * @param login login del usuario
     * @param idSession codigo de la session del usuario que esta intentando hacer la conexion
     * @param idCanal id de canal interno
     * @param codigoCanal codigo de canal en el CORE
     * @return UtilDTO
     */
    public UtilDTO validarLogin(String password, String login,String idCanal, String codigoCanal, String idSession);
    
    /**
     * Metodo que se encarga de registrar los datos de un cliente pj en la BD
     * Oracle 11
     *
     *
     * @param nombreCompleto nombre completo del usuario
     * @param nroDoc numero de documento de indentificacion
     * @param tipoDoc tipo de documento del usuario
     * @param clave clave del usuario
     * @param login login del suaurio
     * @param estatusAcceso estatus de acceso del usuario 1:Activo,2:Bloqueado,3:Inactivo(Vacaciones),4:Eliminado
     * @param estatusRegistro 1:Activo,3:PorCompletarAfiliacion
     * @param idCanal codigo interno del canal en IB
     * @param codigoCanal codigo de canal en el CORE del banco
     * @return RespuestaDTO de Respuesta con el resultado de la transaccion.
     */
    public UtilDTO insertarDatosIbUsuarioPj(String nombreCompleto, String nroDoc, Character tipoDoc, String clave, String login, Character estatusAcceso, Character estatusRegistro, String idCanal, String codigoCanal);
        
    /**
     * Metodo que se +encarga de modificar los datos de un usuario pj en la BD
     * Oracle 11++
     *
     *
     * @param idUsuario id del usuario en IB
     * @param clave clave del usuario
     * @param periodoVigencia periodo de vigencia que tendra la clave
     * @param login login del suaurio
     * @param estatisRegistro the value of estatisRegistro
     * @param estatusAcceso the value of estatusAcceso
     * @param idCanal codigo interno del canal
     * @param codigoCanal codigo de canal en el CORE del banco
     * @return the com.bds.ws.dto.UtilDTO
     */
    public UtilDTO modificarLoginClaveUsuarioPj(String idUsuario, String clave, String periodoVigencia, String login, Character estatisRegistro,  Character estatusAcceso, String idCanal, String codigoCanal);
    
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
    public IbUsuariosPjDTO actualizarIntentosPDD(String idUsuario, String idCanal, String codigoCanal);
    
    /**
     * Metodo que se +encarga de consultar los UsuariosPj Oracle 11++
     *
     * @param tipoDocumento V,E,P
     * @param nroDocumento numero de cedula
     * @param codigoCanal codigo de canal en el CORE del banco
     * @param idCanal codigo interno del canal
     * @return the com.bds.ws.dto.UtilDTO
     */
    public UtilDTO consultarUsuarioPjPorCedula(Character tipoDocumento, String nroDocumento, String codigoCanal, String idCanal);
    
    
     /**
     * Metodo que se +encarga de consultar los UsuariosPj Oracle 11++
     *
     * @param tipoDocumento V,E,P
     * @param nroDocumento numero de cedula
     * @param codigoCanal codigo de canal en el CORE del banco
     * @param idCanal codigo interno del canal
     * @return the com.bds.ws.dto.UtilDTO
     */ 
    public UtilDTO consultarUsuarioPjPorId( BigDecimal idUsuario, String codigoCanal, String idCanal);
    /**
     * Metodo que se +encarga de consultar los UsuariosPj Oracle 11++
     *
     *
     *
     * @param idEmpresa codigo de la empreas en IB
     * @param idUsuario codigo del usuario en IB
     * @param nombreCompleto nombre completo del usuario
     * @param nroDoc         numero de documento del usuario
     * @param tipoDoc        tipo de documento del usuario
     * @param clave          clave del usuario
     * @param login          login del usuario
     * @param email          clave del usario
     * @param telefonoOficina
     * @param telefonoCelular
     * @param representanteLegal
     * @param perfilAcceso
     * @param estatusAcceso 1:Activo,2:Bloqueado,3:Inactivo(Vacaciones),4:Eliminado
     * @param idCanal codigo interno del canal
     * @param codigoCanal codigo de canal en el CORE del banco
     * @return the com.bds.ws.dto.UtilDTO
     */
    public UtilDTO modificarDatosIbUsuarioPj(String idEmpresa, String idUsuario, String nombreCompleto, String nroDoc, Character tipoDoc, String clave, String login, String email, String telefonoOficina, String telefonoCelular, Character representanteLegal, String perfilAcceso, Character estatusAcceso, String idCanal, String codigoCanal);
    
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
     * @return the com.bds.ws.dto.UtilDTO
     */
    public UtilDTO consultarUsuariosSinReglasAproPj(String perfilAcceso, int[] estatusRegistro, String idEmpresa, String idCanal, String codigoCanal);
    
    /**
     * Metodo que valida el Historico de Claves del usuario PJ
     *
     *
     * @param idUsuario   codigo del usuario interno en IB
     * @param clave       clave a validar
     * @param idCanal     codigo interno del canal en IB
     * @param codigoCanal codigo de canal en el CORE del banco
     * @return UtilDTO
     */
    public UtilDTO validarHistoricoClavesUsuarioPj(String idUsuario, String clave, String idCanal, String codigoCanal);
    
    /**
     * Metodo que consulta las conexiones de un usuario
     *
     *
     * @param idUsuario codigo del usuario interno en IB
     * @param idCanal codigo interno del canal en IB
     * @param idSession codigo de la session del usuario que se esta logueando
     * @param codigoCanal codigo de canal en el CORE del banco
     * @return UtilDTO true cuando es valida, false en caso contrario
     */
    public IbConexionUsuarioPjDTO consultarConexionUsuarioPj(String idUsuario, String idCanal, String codigoCanal, String idSession);
    
    /**
     * Metodo que cierra la conexion de un usuario
     *
     *
     * @param idUsuario codigo del usuario interno en IB
     * @param idCanal codigo interno del canal en IB
     * @param codigoCanal codigo de canal en el CORE del banco
     * @return UtilDTO true cuando es valida, false en caso contrario
     */
    public UtilDTO cerrarConexionUsuarioPj(String idUsuario, String idCanal, String codigoCanal);
    
    /**
     * Metodo que consulta los perfiles activos de un usuario
     *
     *
     * @param estatus A = activo, I = inactivo
     * @param idCanal codigo de canal interno en IB
     * @param codigoCanal codigo de canal en el CORE del banco
     * @return UtilDTO true cuando es valida, false en caso contrario
     */
    public IbPerfilesPjDTO consultarPerfilesPj(String estatus, String idCanal, String codigoCanal);

    /**
     * Metodo que resetea el login y el password de un IbUsuarioPj
     *
     *
     * @param idUsuario codigo del usuario interno en IB
     * @param idCanal codigo interno del canal en IB
     * @param codigoCanal codigo de canal en el CORE del banco
     * @return UtilDTO true cuando es valida, false en caso contrario
     */
    public UtilDTO resetearIbUsuarioPj(String idUsuario, String idCanal, String codigoCanal);
    
    /**
     * Metodo que desbloquea a un usuario de la base de datos
     *
     *
     * @param idUsuario codigo del usuario interno en IB
     * @param idCanal codigo interno del canal en IB
     * @param codigoCanal codigo de canal en el CORE del banco
     * @return UtilDTO true cuando es valida, false en caso contrario
     */
    public UtilDTO desbloquearIbUsuarioPj(String idUsuario, String idCanal, String codigoCanal);
}
