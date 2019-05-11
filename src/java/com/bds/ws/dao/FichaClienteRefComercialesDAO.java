/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dao;

import com.bds.ws.dto.FichaClienteRefComercialesDTO;

/**
 *
 * @author humberto.rojas
 */
public interface FichaClienteRefComercialesDAO {
    
    public FichaClienteRefComercialesDTO consultarRefComerciales(String iCodigoCliente);
    
}
