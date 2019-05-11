/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dao;

import com.bds.ws.dto.IbErroresCargaPagosEspDetPjDTO;
import java.math.BigDecimal;

/**
 *
 * @author robinson rodriguez
 */
public interface IbErroresCargaPagosEspDetPjDAO {
    
     /**
     * Metodo buscar el detalle de pagos especiales
     * Oracle 11
     *
     * @param idPago id del pago a consultar
     * @param idCanal id de canal interno en IB
     * @param codigoCanal codigo de canal asignado por el CORE bancario
     * @return 
     */ 
    public IbErroresCargaPagosEspDetPjDTO listarErrCargaPagosEspDetPj(BigDecimal idPago, String idCanal, String codigoCanal);
}
