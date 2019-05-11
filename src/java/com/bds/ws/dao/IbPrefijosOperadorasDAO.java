/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dao;

import com.bds.ws.dto.IbPrefijosOperadorasDTO;

/**
 * Interfaz DAO para prefijos de operadoras
 * @author juan.faneite
 */
public interface IbPrefijosOperadorasDAO {
    
    /**
     * Metodo para obtener el listado de prefijos de operadoras
     * @param nombreCanal nombre del canal
     * @return IbPrefijosOperadorasDTO listado de prefijos de operadoras
     */
    public IbPrefijosOperadorasDTO listaPrefijosOperadoras (String nombreCanal);
}
