/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dao;

import com.bds.ws.dto.FichaClienteDTO;
import com.bds.ws.dto.FichaClienteUpdateDTO;
import com.bds.ws.dto.UtilDTO;

/**
 *
 * @author Accusys Technology
 */
public interface FichaClienteUpdateDAO {
    
    public UtilDTO insertUpdateFichaCliente(String iCodigoCliente);
    
    
    
}

