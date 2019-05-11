/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dao;

import com.bds.ws.dto.ConvenioPjDTO;
import java.util.List;



public interface ConvenioPjDAO {
    /**
     * retorna la informacion del convenio consultado
     *
     * @param sCliente String idCliente
     * @param iCodExtCanal canal por el cual se realiza la consulta
     * @return List<ConvenioPjDTO> informacion del nro y nombre del convenio por cliente consultado
     * 
     */

    
    public List<ConvenioPjDTO> obtenerDetalleConvenio(String sCliente, String iCodExtCanal);
   
}
