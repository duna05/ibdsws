/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dao;

import com.bds.ws.dto.IbErroresPjDTO;
import com.bds.ws.dto.UtilDTO;
import java.math.BigDecimal;

/**
 *
 * @author juan.vasquez
 */
public interface IbErroresPjDAO {
    
     /**
     * Metodo para insertar un nuevo error relacionado a la carga de nomina
     * Oracle 11
     *
     * @param ibErroresCargaPjDTO objeto completo de IbErroresPjDTO
     * @return UtilDTO
     */
    public UtilDTO insertarIbErroresCargaPj(IbErroresPjDTO ibErroresCargaPjDTO);
    
     /**
     * Metodo para consultar un error relacionado a la carga de nomina
     * Oracle 11
     *
     * @param idError BigDecimal, identificador del error a buscar
     * @return UtilDTO
     */
    public UtilDTO consultarIbErroresCargaPjById(BigDecimal idError);
}
