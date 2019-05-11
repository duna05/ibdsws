/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dao;

import com.bds.ws.dto.IbErroresNominaPjDTO;
import com.bds.ws.dto.UtilDTO;
import java.math.BigDecimal;

/**
 *
 * @author juan.vasquez
 */
public interface IbErroresNominaPjDAO {
     /**
     * Metodo para insertar un nuevo error relacionado a la carga de nomina
     * Oracle 11
     *
     * @param ibErroresNominaPjDTO objeto completo de IbErroresNominaPjDTO
     * @return UtilDTO
     */
    public UtilDTO insertarIbErroresNominaPj(IbErroresNominaPjDTO ibErroresNominaPjDTO);
    
     /**
     * Metodo para consultar un error relacionado a la carga de nomina
     * Oracle 11
     *
     * @param idError BigDecimal, identificador del error a buscar
     * @return UtilDTO
     */
    public UtilDTO consultarIbErroresNominaPjById(BigDecimal idError);
    
     /**
     * Metodo para consultar un error relacionado a la carga de nomina
     * Oracle 11
     *
     * @param idPago BigDecimal, identificador del error a buscar
     * @return UtilDTO
     */
    public UtilDTO consultarIbErroresNominaPjByPago(BigDecimal idPago);
    
}
