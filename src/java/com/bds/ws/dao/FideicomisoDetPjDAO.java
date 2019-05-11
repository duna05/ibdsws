/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dao;

import com.bds.ws.dto.FideicomisoDetPjDTO;

/**
 *
 * @author robinson.rodriguez
 */
public interface FideicomisoDetPjDAO {
    
        /**
     * Metodo para listar los detalles de la consulta fideicomiso
     * Oracle 11
     *
     * @param cdClienteAbanks codigo del cliente en el core bancario
     * @param cdUsuario id del usuario
     * @param idFideicomiso id del fideicomiso
     * @param fechaHora fecha del registro en base de datos
     * @param idCanal id del canal a utilizar
     * @param codigoCanal id canal utilizado
     * @return IbFideicomisoDetPjDTO
     */
    public FideicomisoDetPjDTO listarFideicomisoDetPj(String cdClienteAbanks, String cdUsuario, String idFideicomiso, String fechaHora, String idCanal, String codigoCanal);
    
}
