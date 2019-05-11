/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dao;


/**
 *
 * @author audra.zapata
 */
public interface FichaClienteDireccionUpdateDAO {
    
    /**
     *
     * @param iCodigoCliente
     * @param tipoVivienda
     * @return
     */
    public void insertUpdateFichaClienteDireccion(String iCodigoCliente, String tipoVivienda);
    
}
