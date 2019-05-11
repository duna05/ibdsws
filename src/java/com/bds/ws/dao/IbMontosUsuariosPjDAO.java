/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dao;

import com.bds.ws.dto.IbMontosUsuariosPjDTO;
import com.bds.ws.dto.UtilDTO;
import java.math.BigDecimal;

/**
 *
 * @author luis.perez
 */
public interface IbMontosUsuariosPjDAO {
    /**
     * Metodo que inserta las reglas de aprobacion por moto BD Oracle 11++
     *
     *
     * @param idUsuario codigo del usuario en IB
     * @param idEmpresa codigo de la empresa en IB
     * @param montoHasta monto final permitido para la regla
     * @param idCanal codigo interno del canal
     * @param codigoCanal codigo de canal en el CORE del banco
     * @return the com.bds.ws.dto.UtilDTO
     */
    public UtilDTO insertarReglasAprobacionPj(String idUsuario, String idEmpresa, BigDecimal montoHasta, String idCanal, String codigoCanal);
    
    /**
     * Metodo que modifica las reglas de aprobacion por moto BD Oracle 11++
     *
     *
     * @param idUsuario codigo del usuario en IB
     * @param idEmpresa codigo de la empresa en IB
     * @param montoHasta monto final permitido para la regla
     * @param idCanal codigo interno del canal
     * @param codigoCanal codigo de canal en el CORE del banco
     * @return Objeto de Respuesta con el resultado de la transaccion.
     */
    public IbMontosUsuariosPjDTO modificarReglasAprobacionPj(String idUsuario, String idEmpresa, BigDecimal montoHasta, String idCanal, String codigoCanal);
    
}
