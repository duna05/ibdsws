/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dao;

import com.bds.ws.dto.UtilDTO;

/**
 *
 * @author luis.perez
 */
public interface EmpresasDAO {
    /**
     * Metodo que se encarga de buscar el nombre y el codigo de la empresa por un RIF en el
     * CORE del banco
     *
     * @param nroCuenta numero de cuenta correspondiente a la empresa
     * @param tipoRif tipo de rif J, G, etc.
     * @param rif rif de la empresa
     * @param idCanal id de canal interno en IB
     * @param codigoCanal codigo de canal en el CORE bancario
     * @return UtilDTO
     */
    public UtilDTO buscarNombreCodEmpresaByRif(String nroCuenta,Character tipoRif, String rif, String idCanal, String codigoCanal);
}
