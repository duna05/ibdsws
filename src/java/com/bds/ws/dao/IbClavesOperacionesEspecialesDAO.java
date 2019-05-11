/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dao;

import com.bds.ws.dto.UtilDTO;

/**
 *
 * @author ledwin.belen
 */
public interface IbClavesOperacionesEspecialesDAO {
    
    /**
     * Métod para validar la clave de operaciones especiales porporcionada por el usuario
     * @param idUsuario
     * @param claveOP
     * @param idCanal
     * @param codigoCanal
     * @return 
     */
    public UtilDTO validarClaveOP(String idUsuario, String claveOP, String idCanal, String codigoCanal);
    
    /**
     * Método para crear o modificar la clave de operaciones especiales
     * @param idUsuario
     * @param claveOP
     * @param idUsuarioCarga
     * @param sw
     * @param idCanal
     * @param codigoCanal
     * @return 
     */
    public UtilDTO insertarActualizarClaveOP(String idUsuario, String claveOP, String idUsuarioCarga, boolean sw, String idCanal, String codigoCanal);
    
    /**
     * Valida que el usuario tenga un registro de las claves de operaciones especiales
     * @param idUsuario
     * @param idCanal
     * @param codigoCanal
     * @return the boolean 
     */
    public boolean validarExisteClaveOperacionesEspeciales(String idUsuario, String idCanal, String codigoCanal);
    
}
