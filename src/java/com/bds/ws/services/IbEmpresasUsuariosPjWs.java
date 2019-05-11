/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.services;

import com.bds.ws.dao.IbEmpresasUsuariosDAO;
import com.bds.ws.dto.IbEmpresasUsuariosPjDTO;
import com.bds.ws.dto.OtpDTO;
import com.bds.ws.dto.UtilDTO;
import com.bds.ws.model.IbEmpresasUsuariosPj;
import java.util.List;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author luis.perez
 */
@WebService(serviceName = "IbEmpresasUsuariosPjWs")
public class IbEmpresasUsuariosPjWs {

    @EJB
    private IbEmpresasUsuariosDAO ibEmpresasUsuariosDAO;

    /**
     * Metodo que se +encarga de registrar los datos de un cliente pj en la BD
     * Oracle 11++
     *
     *
     * @param idUsuario id del usuario en IB
     * @param representanteLegal indica si es representante legal
     * @param idEmpresa identificador de la empresa a la cual pertenece el
     * @param estatusAcceso estatus de acceso
     * @param email email del usuario
     * @param perfilAcceso perfil de acceso del usuario
     * @param estatusRegistro estatus del registro del usuario
     * @param telfCelularCompleto numero de telefono celular completo
     * @param telfOficinaCompleto telefono completo del usuario
     * @param idCanal codigo interno del canal
     * @param codigoCanal codigo de canal en el CORE del banco
     * @return Objeto de Respuesta con el resultado de la transaccion.
     */
    @WebMethod(operationName = "insertarDatosEmpresaUsuario")
    public UtilDTO insertarDatosEmpresaUsuario(@WebParam(name = "idEmpresa") String idEmpresa,
            @WebParam(name = "idUsuario") String idUsuario,
            @WebParam(name = "email") String email,
            @WebParam(name = "representanteLegal") Character representanteLegal,
            @WebParam(name = "estatusAcceso") Character estatusAcceso,
            @WebParam(name = "estatusRegistro") Character estatusRegistro,
            @WebParam(name = "perfilAcceso") String perfilAcceso,
            @WebParam(name = "telfCelularCompleto") String telfCelularCompleto,
            @WebParam(name = "telfOficinaCompleto") String telfOficinaCompleto,
            @WebParam(name = "idCanal") String idCanal,
            @WebParam(name = "codigoCanal") String codigoCanal) {
        return ibEmpresasUsuariosDAO.insertarDatosEmpresaUsuario(idEmpresa, idUsuario, email, telfCelularCompleto, telfOficinaCompleto, representanteLegal, perfilAcceso, estatusAcceso, estatusRegistro, idCanal, codigoCanal);
    }

    /**
     * Metodo que consultar los usuarios juridicos Oracle 11++
     *
     *
     * @param estatusAcceso
     * 1:Activo,2:Bloqueado,3:Inactivo(Vacaciones),4:Eliminado
     * @param idEmpresa codigo interno de la empresa en IB
     * @param idCanal codigo interno del canal
     * @param codigoCanal codigo de canal en el CORE del banco
     * @return UtilDTO
     */
    @WebMethod(operationName = "consultarEmpresasUsuariosPj")
    public UtilDTO consultarEmpresasUsuariosPj(@WebParam(name = "estatusAcceso") Character estatusAcceso,
            @WebParam(name = "idEmpresa") String idEmpresa,
            @WebParam(name = "idCanal") String idCanal,
            @WebParam(name = "codigoCanal") String codigoCanal) {

        return ibEmpresasUsuariosDAO.consultarEmpresasUsuarios(idEmpresa, idCanal, codigoCanal, estatusAcceso);
    }

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
    @WebMethod(operationName = "consultarEmpresasUsuariosPjPerfil")
    public UtilDTO consultarEmpresasUsuariosPjPerfil(@WebParam(name = "perfilAcceso") String perfilAcceso,
            @WebParam(name = "idEmpresa") String idEmpresa,
            @WebParam(name = "idCanal") String idCanal,
            @WebParam(name = "codigoCanal") String codigoCanal) {

        return ibEmpresasUsuariosDAO.consultarEmpresasUsuariosPjPerfil(idEmpresa, idCanal, codigoCanal, perfilAcceso);
    }

    /**
     * Metodo que consultar los usuarios juridicos Oracle 11++ por perfil que
     * cuenten con montos autorizados
     *
     *
     * @param perfilAcceso
     * 1:Master,2:Admin,3:Autorizador,4:Ingresador,5:Consultor
     * @param idEmpresa codigo interno de la empresa en IB
     * @param idCanal codigo interno del canal
     * @param codigoCanal codigo de canal en el CORE del banco
     * @return UtilDTO
     */
    @WebMethod(operationName = "consultarEmpresasMontosUsuariosPjPerfil")
    public UtilDTO consultarEmpresasMontosUsuariosPjPerfil(@WebParam(name = "perfilAcceso") String perfilAcceso,
            @WebParam(name = "idEmpresa") String idEmpresa,
            @WebParam(name = "idCanal") String idCanal,
            @WebParam(name = "codigoCanal") String codigoCanal) {

        return ibEmpresasUsuariosDAO.consultarEmpresasMontosUsuariosPjPerfil(idEmpresa, idCanal, codigoCanal, perfilAcceso);
    }

    /**
     * Metodo que consulta un usuario juridico
     *
     *
     * @param estatusAcceso
     * 1:Activo,2:Bloqueado,3:Inactivo(Vacaciones),4:Eliminado
     * @param idUsuario
     * @param idEmpresa codigo interno de la empresa en IB
     * @param idCanal codigo interno del canal
     * @param codigoCanal codigo de canal en el CORE del banco
     * @return IbEmpresasUsuariosPjDTO
     */
    @WebMethod(operationName = "consultarEmpresaUsuarioPj")
    public UtilDTO consultaEmpresaUsuarioPj(@WebParam(name = "estatusAcceso") Character estatusAcceso,
            @WebParam(name = "idUsuario") String idUsuario,
            @WebParam(name = "idEmpresa") String idEmpresa,
            @WebParam(name = "idCanal") String idCanal,
            @WebParam(name = "codigoCanal") String codigoCanal) {

        return ibEmpresasUsuariosDAO.consultarEmpresaUsuario(idUsuario, idEmpresa, idCanal, codigoCanal);
    }

    /**
     * Metodo que consulta por el id del usuario
     *
     *
     * @param idUsuario
     * @param idCanal codigo interno del canal
     * @param codigoCanal codigo de canal en el CORE del banco
     * @return List<IbEmpresasUsuariosPjDTO>
     */
    @WebMethod(operationName = "consultarPorEmpresaUsuarioPj")
    public UtilDTO consultarPorEmpresaUsuarioPj(@WebParam(name = "idUsuario") String idUsuario,
            @WebParam(name = "idCanal") String idCanal,
            @WebParam(name = "codigoCanal") String codigoCanal) {

        return ibEmpresasUsuariosDAO.consultarEmpresaUsuario(idUsuario, idCanal, codigoCanal);
    }

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
    @WebMethod(operationName = "validarOTPRegistroUsuario")
    public UtilDTO validarOTPRegistroUsuario(
            @WebParam(name = "idUsuario") String idUsuario,
            @WebParam(name = "idEmpresa") String idEmpresa,
            @WebParam(name = "codigoOTP") String codigoOTP,
            @WebParam(name = "idCanal") String idCanal,
            @WebParam(name = "codigoCanal") String codigoCanal) {

        return ibEmpresasUsuariosDAO.validarOTPRegistroUsuario(idUsuario, idEmpresa, codigoOTP, idCanal, codigoCanal);
    }

    @WebMethod(operationName = "OtpDTOPjP")
    public OtpDTO OtpDTOP(@WebParam(name = "prueba") String prueba) {
        return null;
    }

    @WebMethod(operationName = "IbEmpresasUsuariosPjDTOP2")
    public IbEmpresasUsuariosPjDTO IbEmpresasUsuariosPjDTO(@WebParam(name = "prueba") String prueba) {
        return null;
    }

    /**
     * Metodo que consulta por el id del usuario
     *
     *
     * @param idUsuario
     * @param idCanal codigo interno del canal
     * @param codigoCanal codigo de canal en el CORE del banco
     * @return List<IbEmpresasUsuariosPjDTO>
     */
    @WebMethod(operationName = "consultarEmpresaxEmpresa")
    public List<IbEmpresasUsuariosPj> consultarEmpresaxEmpresa(@WebParam(name = "idEmpresa") String idEmpresa,
            @WebParam(name = "codigoCanal") String codigoCanal
    ) {
        return ibEmpresasUsuariosDAO.consultarEmpresaxEmpresa(idEmpresa, codigoCanal);
    }
}
