/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dao;

import com.bds.ws.dto.UtilDTO;

/**
 *
 * @author luis.perez
 */
public interface IbEmpresasDAO {
    /**
     * Realiza la validacion de la empresa
     *
     * @param tipoRif tipo de persona, natural o juridica
     * @param rif numero de rif
     * @param idCanal id de canal interno
     * @param codigoCanal codigo de canal en el CORE
     * @return UtilDTO
     */
    public UtilDTO validarEmpresa(Character tipoRif, String rif, String idCanal, String codigoCanal);
    
    /**
     * Metodo que se encarga de registrar los datos de una emprea en BD Oracle
     * 11++
     *
     *
     * @param nroDoc numero de documento de indentificacion
     * @param tipoDoc tipo de documento del usuario
     * @param nroCuenta numero de cuenta del cliente
     * @param codigoEmpresa codigo de la empresa
     * @param nombre nombre de la empresa
     * @param tipoAcceso centralizado o centralizado
     * @param estatus activa, en proceso de afiliacion, inactiva
     * @param idCanal id de canal interno en ib
     * @param codigoCanal codigo de canal en el CORE
     * @return UtilDTO Objeto de Respuesta con el resultado de la transaccion.
     */
    public UtilDTO insertarDatosEmpresa(String nroDoc, Character tipoDoc, String nroCuenta, Long codigoEmpresa, String nombre, Character tipoAcceso, Character estatus, String idCanal, String codigoCanal);
    
    /**
     * Metodo que se +encarga de consultar los datos asociados a una empresa
     * Oracle 11++
     *
     *
     * @param idUsuario id del usuario en IB
     * @param idCanal codigo interno del canal
     * @param codigoCanal codigo de canal en el CORE del banco
     * @return Objeto de Respuesta con el resultado de la transaccion.
     */
    public UtilDTO consultarDatosEmpresaUsuario(String idUsuario, String idCanal, String codigoCanal);
    
    /**
     * Metodo que se +encarga de consultar los datos asociados a una empresa
     * Oracle 11++
     *
     *
     * @param idUsuario id del usuario en IB
     * @param estatusRegistro 1:Activo,2:EnProcesoAfiliacion,3:PorCompletarAfiliacion
     * @param idCanal codigo interno del canal
     * @param codigoCanal codigo de canal en el CORE del banco
     * @return Objeto de Respuesta con el resultado de la transaccion.
     */
    public UtilDTO consultarDatosEmpresaUsuario(String idUsuario,String estatusRegistro, String idCanal, String codigoCanal);
}
