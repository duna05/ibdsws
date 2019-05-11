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
public interface IbServiciosPerfilesPjDAO {
    /**
     * *
     * Metodo que retorna el menu (Modulos y Servicios) 
     * 
     * @param idPerfil    codigo de perfil en IB
     * @param estatus     estatus del servicio y el modulo
     * @param idCanal     id de canal interno en IB
     * @param codigoCanal codigo de canal en el CORE Bancario
     * @return the com.bds.ws.dto.UtilDTO
     */
    public UtilDTO consultarServiciosPerfilesPj(String idPerfil, Character estatus,String idCanal,String codigoCanal);
}
