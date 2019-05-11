/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dao;

import com.bds.ws.dto.IbEmpresasUsuariosPjDTO;
import com.bds.ws.dto.PreguntaRespuestaUsuarioDTO;
import com.bds.ws.dto.UtilDTO;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author luis.perez
 */
public interface IbManejadorTransaccionesDAO {
    
    /**
     * La creacion del usuario y la empresa con todos sus datos asociados
     *
     * @param ibEmpresasUsuariosPjDTO
     * @param idCanal
     * @param codigoCanal
     *
     * @return UtilDTO
     */
    public UtilDTO insertarDatosUsuarioEmpresa(            
             IbEmpresasUsuariosPjDTO ibEmpresasUsuariosPjDTO, 
             String idCanal,
             String codigoCanal);
    
    /**
     * Metodo utilizado para completar el perfil del usuario en el proceso de
     * registro
     *
     * Actualiza el login, la clave y las PDD
     *
     * Parametros para el usuario
     *
     * @param idUsuario id del usuario en IB
     * @param idEmpresa codigo interno de la empresa en IB
     * @param clave clave del usuario
     * @param login login del usuario
     * @param periodoVigencia indica el periodo de vigencia en dias
     * @param preguntaRespuestaUsuarioDTO objeto que contiene una lista de
     * preguntas son sus respuestas
     * @param idCanal id de canal en el IB
     * @param codigoCanal codigo de canal en el CORE del banco
     * @param montoHasta monto final permitido para la regla
     *
     * @return UtilDTO
     */
    public UtilDTO completarAfiliacionUsuarioPj(String idUsuario, String idEmpresa, String clave, String login, String periodoVigencia, PreguntaRespuestaUsuarioDTO preguntaRespuestaUsuarioDTO, String idCanal, String codigoCanal);
    
    /**
     * Metodo utilizado para modificar los datos de la empresa, usuario, 
     * y monto del usuario
     * 
     * Actualiza el login, la clave, las PDD, y el monto del usuario
     *
     * Parametros para el usuario
     *
     * @param ibEmpresasUsuariosPjDTO objeto completo con los datos de la modificacion
     * @param idCanal id de canal en el IB
     * @param codigoCanal codigo de canal en el CORE del banco
     *
     * @return UtilDTO
     */
    public UtilDTO modificarEmpresaUsuarioMontoPj(IbEmpresasUsuariosPjDTO ibEmpresasUsuariosPjDTO, String idCanal, String codigoCanal);
}
