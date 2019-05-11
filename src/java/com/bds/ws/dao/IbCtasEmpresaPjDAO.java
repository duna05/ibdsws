/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dao;

import com.bds.ws.dto.CuentaDTO;
import java.util.List;

/**
 *
 * @author luis.perez
 */
public interface IbCtasEmpresaPjDAO {
    /**
     * Metodo que obtiene el listado de cuentas de ahorro y corrientes una
     * empresa en relacion a un usuario
     *
     * @param idUsuario id del usuario en     IB
     * @param idEmpresa id de la empresa en   IB
     * @param idCanal id del canal interno en IB
     * @param codigoCanal codigo de canal asignado en el core bancario
     * @return ClienteDTO listado de cuentas de ahorro y corrientes que posee un
     * cliente
     */
    public List<CuentaDTO> listarCuentasEmpresaUsuarioPj(String idUsuario, String idEmpresa, String idCanal,  String codigoCanal);
}
