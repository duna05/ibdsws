/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dao;

import com.bds.ws.dto.IbFideicomisoDetPjDTO;
import com.bds.ws.dto.UtilDTO;
import com.bds.ws.model.IbFideicomisoDetPj;
import java.math.BigDecimal;

/**
 *
 * @author robinson.rodriguez
 */
public interface IbFideicomisoDetPjDAO {
    
        
    public UtilDTO insertarIbFideicomisoDetPj(IbFideicomisoDetPj ibFideicomisoDetPj, String codigoCanal);
    
    /**
     * Metodo buscar el detalle de pagos especiales Oracle 11
     *
     * @param idfideicomiso id del fideicomiso realizado
     * @param estatusCarga estatus de la carga
     * @param idCanal id de canal interno en IB
     * @param codigoCanal codigo de canal asignado por el CORE bancario
     * @return
     */
    public IbFideicomisoDetPjDTO listarIbFideicomisoDetPjDTO(BigDecimal idfideicomiso, Short estatusCarga, String idCanal, String codigoCanal);
    
}
