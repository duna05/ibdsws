/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dao;

import com.bds.ws.dto.UtilDTO;
import java.util.List;

/**
 *
 * @author luis.perez
 */
public interface IbMenuPjDAO {
    /**
     * *
     * Metodo que retorna el menu (Modulos y Servicios) 
     *
     * @param idUsuario String id del usuario
     * @param idEmpresa id de la empresa en IB
     * @param estatus estatus del modulo A = activo, I = inactivo
     * @param visible visible = 1, no visible = 0
     * @param idCanal String idCanal por el cual se ejecuta la transaccion. 
     * @param codigoCanal codigo de canal en el CORE bancario
     * @return the com.bds.ws.dto.UtilDTO
     */
    public UtilDTO consultarModulosServPorEmpresaUsuarioPj(String idUsuario, String idEmpresa, Character estatus, Character visible, String idCanal, String codigoCanal);
    
    /**
     * Metodo que consulta las urls permitidas para un servicio
     *
     * @param idUsuario String id del usuario
     * @param idEmpresa id de la Empresa en IB
     * @param idCanal String idCanal por el cual se ejecuta la transaccion.
     * @param codigoCanal String nombreCanal por el cual se ejecuta la
     * transaccion.
     * @return List<String> con las urls permitidas
     */
    public List<String> consultarUrlPorServicioUsuario(String idUsuario, String idEmpresa, String idCanal, String codigoCanal);
}
