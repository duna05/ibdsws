/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dao;

import com.bds.ws.dto.IbServiEmpreUsuariosPjDTO;

/**
 *
 * @author luis.perez
 */
public interface IbServiEmpreUsuariosPjDAO {
    /**
     * *
     * Metodo que retorna una lista de IbServiEmpreUsuariosPj
     * 
     * @param idUsuario   id del usuario en IB
     * @param idEmpresa   id de la empresa en IB
     * @param idCanal     id de canal interno en IB
     * @param codigoCanal codigo de canal en el CORE Bancario
     * @return List IbServiEmpreUsuariosPjDTO 
     * 
     */
    public IbServiEmpreUsuariosPjDTO consultarIbServiEmpreUsuariosPj(String idUsuario, String idEmpresa, String idCanal, String codigoCanal);
}
