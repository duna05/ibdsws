/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dao;

import com.bds.ws.dto.IbCtaEmpresaPjDTO;

/**
 *
 * @author cesar.mujica
 */
public interface IbCtasEmpresaUsuarioPjDAO {
    
    /**
     * Metodo que se +encarga de consultar las cuentas asociadas a una empresa y un usario
     * Oracle 11++
     *
     *
     * @param idUsuario id del usuario en IB
     * @param idEmpresa
     * @param idCanal codigo interno del canal
     * @param codigoCanal codigo de canal en el CORE del banco
     * @return Objeto de Respuesta con el resultado de la transaccion.
     */
    public IbCtaEmpresaPjDTO obtenerIbCtasEmpresaUsuarioPj(String idUsuario, String idEmpresa, String idCanal, String codigoCanal);
    
}
