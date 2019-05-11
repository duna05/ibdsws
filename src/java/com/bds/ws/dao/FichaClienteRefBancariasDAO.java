/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dao;

import com.bds.ws.dto.FichaClienteRefBancariasDTO;


/**
 *
 * @author humberto.rojas
 */
public interface FichaClienteRefBancariasDAO {
    
    public FichaClienteRefBancariasDTO consultarRefBancarias(String iCodigoCliente);
    
}
