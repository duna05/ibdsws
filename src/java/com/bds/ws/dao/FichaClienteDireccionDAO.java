/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dao;

import com.bds.ws.dto.FichaClienteDireccionDTO;

/**
 *
 * @author humberto.rojas
 */
public interface FichaClienteDireccionDAO {
    
    public FichaClienteDireccionDTO consultarDatosDireccionCliente(String iCodigoCliente);
    
}
