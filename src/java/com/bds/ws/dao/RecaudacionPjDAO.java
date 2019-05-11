/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dao;

import com.bds.ws.dto.RecaudacionPjDTO;
import java.util.List;



public interface RecaudacionPjDAO {
    /**
     * retorna la informacion de la recaudacion consultada
     *
     * @param sNumeroConvenio String identificador
     * @param dFechaDesde String fechaValida
     * @param dFechaHasta String lvOperacion 
     * @param iCodExtCanal String agenciaOrigen
     * @return RecaudacionPjDTO informacion de la recaudacion consultada
     * 
     */
    public List<RecaudacionPjDTO>  obtenerDetalleRecaudacion(String sNumeroConvenio, String dFechaDesde, String dFechaHasta, String iCodExtCanal);
   
}
