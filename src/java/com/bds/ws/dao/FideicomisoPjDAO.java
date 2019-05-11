/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dao;

import com.bds.ws.dto.FideicomisoPjDTO;

/**
 *
 * @author robinson.rodriguez
 */
public interface FideicomisoPjDAO {

    /**
     * Metodo para listar las cabeceras de la consulta fideicomiso
     * Oracle 11
     *
     * @param cdClienteAbanks codigo del cliente en el core bancario
     * @param idFideicomiso id del fideicomiso
     * @param idIndicadorTxt indicador del texto
     * @param idCanal id del canal a utilizar
     * @param codigoCanal id canal utilizado
     * @return IbFideicomisoPjDTO
     */
    public FideicomisoPjDTO listarFideicomisoPj(String cdClienteAbanks, String idFideicomiso, String idIndicadorTxt, String idCanal, String codigoCanal);
    
    /**
     * Metodo para validar si un codigo de plan pertenece a una empresa
     *
     * @param rifEmpresa
     * @param codigoPlan
     * @param idCanal id del canal a utilizar
     * @param codigoCanal id canal utilizado
     * @return IbFideicomisoPjDTO
     */
    public boolean validarCodigoPlanEmpresa(String rifEmpresa, String codigoPlan, String idCanal, String codigoCanal);
    
    /**
     * Metodo para validar si un codigo de plan esta activo
     *
     * @param codigoPlan
     * @param idCanal id del canal a utilizar
     * @param codigoCanal id canal utilizado
     * @return IbFideicomisoPjDTO
     */
    public boolean validarEstatusPlan(String codigoPlan, String idCanal, String codigoCanal);
}
