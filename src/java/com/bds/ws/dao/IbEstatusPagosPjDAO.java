/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dao;

import com.bds.ws.dto.IbEstatusPagosPjDTO;
import com.bds.ws.dto.UtilDTO;
import java.math.BigDecimal;

/**
 *
 * @author juan.vasquez
 */
public interface IbEstatusPagosPjDAO {
    
     /**
     * Metodo para insertar un estatus de pagos nuevo
     * Oracle 11
     *
     * @param ibEstatusPagosPj objeto completo de IbEstatusPagosPj
     * @return UtilDTO
     */
    public UtilDTO insertarIbEstatusPagosPj(IbEstatusPagosPjDTO ibEstatusPagosPj);
    /**
     * Metodo para insertar un estatus de pagos nuevo
     * Oracle 11
     *
     * @param nombre tipo String, nombre con el cual se identificara el estatus
     * @param descripcion tipo String, breve texto explicativo del estatus a crear
     * @return UtilDTO
     */
    public UtilDTO insertarIbEstatusPagosPj(String nombre, String descripcion);
     /**
     * Metodo para modificar un estatus de pagos ya creado
     * Oracle 11
     *
     * @param ibEstatusPagosPj objeto completo de IbEstatusPagosPj a 
     * modificar previamente consultado
     * @return UtilDTO
     */
    public UtilDTO modificarIbEstatusPagosPj(IbEstatusPagosPjDTO ibEstatusPagosPj);
    /**
     * Metodo para consultar un estatus de pagos por numero de identificador
     * Oracle 11
     *
     * @param ibEstatusPagosPj objeto completo de IbEstatusPagosPj a 
     * modificar previamente consultado
     * @return UtilDTO
     */
    public UtilDTO consultarIbEstatusPagosPjById(BigDecimal idEstatus);
}
