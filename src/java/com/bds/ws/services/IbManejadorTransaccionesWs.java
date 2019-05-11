/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.services;

import com.bds.ws.dao.IbManejadorTransaccionesDAO;
import com.bds.ws.dto.IbEmpresasUsuariosPjDTO;
import com.bds.ws.dto.PreguntaRespuestaUsuarioDTO;
import com.bds.ws.dto.UtilDTO;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author luis.perez
 */
@WebService(serviceName = "IbManejadorTransaccionesWs")
public class IbManejadorTransaccionesWs {
    @EJB
    private IbManejadorTransaccionesDAO ibManejadorTransaccionesDAO;

    /**
     * La creacion del usuario y la empresa con todos sus datos asociados
     *
     * Parametros para el usuario
     *
     *
     * Parametros para empresa
     *
     * @param ibEmpresasUsuariosPjDTO contiene la lista de usuarios con la empresa a registrar
     * @param idCanal
     * @param codigoCanal
     *
     * @return UtilDTO
     */
    @WebMethod(operationName = "insertarDatosUsuarioEmpresa")
    public UtilDTO insertarDatosUsuarioEmpresa(
            @WebParam(name = "ibEmpresasUsuariosPjDTO")         IbEmpresasUsuariosPjDTO ibEmpresasUsuariosPjDTO,
            @WebParam(name = "idCanal") String idCanal,
            @WebParam(name = "codigoCanal") String codigoCanal
    ) {
        return ibManejadorTransaccionesDAO.insertarDatosUsuarioEmpresa(ibEmpresasUsuariosPjDTO,idCanal, codigoCanal);        
    }
    
    /**
     * Metodo utilizado para completar el perfil del usuario
     * en el proceso de registro
     * 
     * Actualiza el login, la clave y las PDD
     *
     * Parametros para el usuario
     *
     * @param idUsuario id del usuario en IB
     * @param idEmpresa codigo interno de la empresa en IB
     * @param clave     clave del usuario
     * @param login     login del usuario
     * @param periodoVigencia indica el periodo de vigencia de la clave en dias
     * @param preguntaRespuestaUsuarioDTO objeto que contiene una lista de
     * preguntas son sus respuestas
     * @param idCanal   id de canal en el IB
     * @param codigoCanal codigo de canal en el CORE del banco
     *
     * @return UtilDTO
     */
    @WebMethod(operationName = "completarAfiliacionUsuarioPj")
    public UtilDTO completarAfiliacionUsuarioPj(
            @WebParam(name = "idUsuario") String idUsuario,
            @WebParam(name = "idEmpresa") String idEmpresa,
            @WebParam(name = "clave") String clave,
            @WebParam(name = "login") String login,
            @WebParam(name = "periodoVigencia") String periodoVigencia,
            @WebParam(name = "preguntaRespuestaUsuarioDTO") PreguntaRespuestaUsuarioDTO preguntaRespuestaUsuarioDTO,
            @WebParam(name = "idCanal") String idCanal,
            @WebParam(name = "codigoCanal") String codigoCanal
    ) {
        return ibManejadorTransaccionesDAO.completarAfiliacionUsuarioPj(idUsuario, idEmpresa,clave,login,periodoVigencia,preguntaRespuestaUsuarioDTO, idCanal, codigoCanal);
    }
    
    
    
    /**
     * Metodo utilizado para modificar los datos de la empresa, usuario, 
     * y monto del usuario
     * 
     * Actualiza el login, la clave, las PDD, y el monto del usuario
     *
     * Parametros para el usuario
     *
     * @param ibEmpresasUsuariosPjDTO objeto completo con los datos de modificacion
     * @param idCanal id de canal en el IB
     * @param codigoCanal codigo de canal en el CORE del banco
     *
     * @return UtilDTO
     */
    @WebMethod(operationName = "modificarEmpresaUsuarioMontoPj")
    public UtilDTO modificarEmpresaUsuarioMontoPj(
            @WebParam(name = "ibEmpresasUsuariosPjDTO") IbEmpresasUsuariosPjDTO ibEmpresasUsuariosPjDTO,
            @WebParam(name = "idCanal") String idCanal,
            @WebParam(name = "codigoCanal") String codigoCanal
    ) {
        return ibManejadorTransaccionesDAO.modificarEmpresaUsuarioMontoPj(ibEmpresasUsuariosPjDTO, idCanal, codigoCanal);
    }
}
