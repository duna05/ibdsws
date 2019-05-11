/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dao;

import com.bds.ws.dto.SicadIDTO;
import com.bds.ws.dto.SicadIIDTO;

/**
 * Interfaz de divisas
 * @author juan.faneite
 */
public interface DivisasDAO {

    /**
     * Obtiene los datos para consultar el listado de las subastas historicas en
     * las que ha participado el cliente.
     *
     * @param iIdentificacion String Numero de cedula del cliente.
     * @param canal String canal por el cual se realiza la consulta.
     * @return SicadIDTO 
     */
    public SicadIDTO consultaAdjSicadI(String iIdentificacion, String canal);

    /**
     * Obtiene los datos para consultar el listado de las subastas historicas en
     * las que ha participado el cliente.
     *
     * @param iIdentificacion String Numero de cedula del cliente.
     * @param canal String canal por el cual se realiza la consulta.
     * @return SicadIIDTO
     */
    public SicadIIDTO consultaAdjSicadII(String iIdentificacion, String canal);
}
