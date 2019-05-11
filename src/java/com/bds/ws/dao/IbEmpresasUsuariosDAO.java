/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dao;

import com.bds.ws.dto.IbEmpresasUsuariosPjDTO;
import com.bds.ws.dto.UtilDTO;
import com.bds.ws.model.IbEmpresasUsuariosPj;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author luis.perez
 */
public interface IbEmpresasUsuariosDAO {

    /**
     * Metodo que se encarga de registrar los de la empresa y el usuario Oracle
     * 11
     *
     *
     * @param representanteLegal indica si es representante legal
     * @param idUsuario id del usuario en ib
     * @param idEmpresa identificador de la empresa a la cual pertenece el
     * @param estatusAcceso estatus de acceso
     * @param email email del usuario
     * @param perfilAcceso perfil de acceso del usuario
     * @param estatusRegistro estatus del registro del usuario
     * @param telfCelularCompleto numero de telefono celular completo
     * @param telfOficinaCompleto telefono completo del usuario
     * @param idCanal codigo interno del canal en IB
     * @param codigoCanal codigo de canal en el CORE del banco
     * @return Objeto de Respuesta con el resultado de la transaccion.
     */
    public UtilDTO insertarDatosEmpresaUsuario(String idEmpresa, String idUsuario, String email, String telfCelularCompleto, String telfOficinaCompleto, Character representanteLegal, java.lang.String perfilAcceso, Character estatusAcceso, Character estatusRegistro, String idCanal, String codigoCanal);

    /**
     * Metodo que se encarga de registrar los de la empresa y el usuario Oracle
     * 11
     *
     *
     * @param ibEmpresasUsuariosPj objeto de empresausuario completo con datos
     * de empresa, usuario, monto, perfilacceso
     * @param idCanal codigo interno del canal en IB
     * @param codigoCanal codigo de canal en el CORE del banco
     * @return Objeto de Respuesta con el resultado de la transaccion.
     */
    public UtilDTO insertarDatosEmpresaUsuario(IbEmpresasUsuariosPj ibEmpresasUsuariosPj, String idCanal, String codigoCanal);

    /**
     * Metodo que consultar los usuarios juridicos Oracle 11++
     *
     *
     * @param idEmpresa codigo interno de la empresa en IB
     * @param idCanal codigo interno del canal
     * @param codigoCanal codigo de canal en el CORE del banco
     * @param estatusAcceso
     * 1:Activo,2:Bloqueado,3:Inactivo(Vacaciones),4:Eliminado
     * @return UtilDTO
     */
    public UtilDTO consultarEmpresasUsuarios(String idEmpresa, String idCanal, String codigoCanal, char estatusAcceso);

    /**
     * Metodo que consultar los usuarios juridicos Oracle 11++
     *
     *
     * @param idUsuario codigo interno del usuario en IB
     * @param idEmpresa codigo interno de la empresa en IB
     * @param idCanal codigo interno del canal
     * @param codigoCanal codigo de canal en el CORE del banco
     * @return UtilDTO
     */
    public UtilDTO consultarEmpresaUsuario(String idUsuario, String idEmpresa, String idCanal, String codigoCanal);

    /**
     * Metodo que consultar los usuarios con perfil autorizador activos y
     * registrados, asociados a un empresa
     *
     * @param idEmpresa codigo interno del canal
     * @return UtilDTO
     */
    public UtilDTO consultarEmpresasUsuariosAprobadores(BigDecimal idEmpresa);

    /**
     * Metodo que consulta un usuario de una empresa
     *
     *
     * @param idUsuario codigo del usuario interno en IB
     * @param idCanal codigo interno del canal
     * @param codigoCanal codigo de canal en el CORE del banco
     * @return UtilDTO
     */
    public UtilDTO consultarEmpresaUsuario(String idUsuario, String idCanal, String codigoCanal);

    /**
     * Metodo que valida el OTP ingresado por un usuario
     *
     *
     * @param idUsuario codigo del usuario
     * @param idEmpresa codigo interno de la empresa en IB
     * @param codigoOTP codigo OTP validar
     * @param idCanal codigo interno del canal
     * @param codigoCanal codigo de canal en el CORE del banco
     * @return UtilDTO
     */
    public UtilDTO validarOTPRegistroUsuario(String idUsuario, String idEmpresa, String codigoOTP, String idCanal, String codigoCanal);

    /**
     * Metodo que consultar los usuarios juridicos Oracle 11++ por perfil
     *
     *
     * @param perfilAcceso
     * 1:Master,2:Admin,3:Autorizador,4:Ingresador,5:Consultor
     * @param idEmpresa codigo interno de la empresa en IB
     * @param idCanal codigo interno del canal
     * @param codigoCanal codigo de canal en el CORE del banco
     * @return UtilDTO
     */
    public UtilDTO consultarEmpresasUsuariosPjPerfil(String idEmpresa, String idCanal, String codigoCanal, String perfilAcceso);

    /**
     * Metodo que consultar los usuarios juridicos Oracle 11++ por perfil que
     * cuenten con montos autorizados
     *
     * @param perfilAcceso
     * 1:Master,2:Admin,3:Autorizador,4:Ingresador,5:Consultor
     * @param idEmpresa codigo interno de la empresa en IB
     * @param idCanal codigo interno del canal
     * @param codigoCanal codigo de canal en el CORE del banco
     * @return UtilDTO
     */
    public UtilDTO consultarEmpresasMontosUsuariosPjPerfil(String idEmpresa, String idCanal, String codigoCanal, String perfilAcceso);

    /**
     * Metodo que consultar los usuarios juridicos que son representantes
     * legales de una empresa
     *
     *
     * @param idEmpresa codigo interno de la empresa en IB
     * @param idCanal codigo interno del canal
     * @param codigoCanal codigo de canal en el CORE del banco
     * @return UtilDTO
     */
    public IbEmpresasUsuariosPjDTO consultarEmpresasUsuariosPjRepresentanteLegal(String idEmpresa, String idCanal, String codigoCanal);

    /**
     * Metodo que se encarga de registrar las modificaciones los de la empresa y
     * el usuario Oracle 11
     *
     *
     * @param ibEmpresasUsuariosPj objeto completo de EmpresaUsuario
     * @param codigoCanal codigo de canal en el CORE del banco
     * @return UtilDTO
     */
    public UtilDTO modificarDatosEmpresaUsuario(IbEmpresasUsuariosPj ibEmpresasUsuariosPj, String codigoCanal);

    /**
     * @param ibEmpresas id de la empresa consultada
     * @return List<IbEmpresasUsuariosPj>
     */
    public List<IbEmpresasUsuariosPj> consultarEmpresaxEmpresa(String ibEmpresa, String codigoCanal);
}
