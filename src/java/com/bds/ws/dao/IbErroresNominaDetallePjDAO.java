/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dao;

import com.bds.ws.dto.IbErroresNominaDetallePjDTO;
import com.bds.ws.dto.UtilDTO;
import java.math.BigDecimal;

/**
 *
 * @author juan.vasquez
 */
public interface IbErroresNominaDetallePjDAO {
     /**
     * Metodo para insertar un nuevo error relacionado a la carga de nomina
     * Oracle 11
     *
     * @param ibErroresNominaDetallePjDTO objeto completo de ibErroresNominaDetallePjDTO
     * @return UtilDTO
     */
    public UtilDTO insertarIbErroresNominaDetallePj(IbErroresNominaDetallePjDTO ibErroresNominaDetallePjDTO);
    
     /**
     * Metodo para consultar un error relacionado a la carga de nomina
     * Oracle 11
     *
     * @param idError BigDecimal, identificador del error a buscar
     * @return UtilDTO
     */
    public UtilDTO consultarIbErroresNominaDetallePjById(BigDecimal idError);
    
     /**
     * Metodo para consultar un error relacionado a la carga de nomina
     * Oracle 11
     *
     * @param idPago BigDecimal, identificador del error a buscar
     * @return UtilDTO
     */
    public UtilDTO consultarIbErroresNominaDetallePjByPago(BigDecimal idPago);

    /**
     * Metodo para listar los detalles con errorres en una cabecera
     * Oracle 11
     *
     * @return IbErroresNominaDetallePjDTO
     */
    public IbErroresNominaDetallePjDTO listarCargaNominaErroresByPago(BigDecimal idPago, String codigoCanal);     
    
}
