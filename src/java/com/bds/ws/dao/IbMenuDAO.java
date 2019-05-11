/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dao;

import com.bds.ws.dto.IbModulosDTO;

/**
 * Interfaz DAO para menu
 * @author renseld.lugo
 */
public interface IbMenuDAO {
   
    /**
     * Metodo que consulta el menu por usuario.
     * @param idUsuario String id del usuario
     * @param idCanal String idCanal por el cual se ejecuta la transaccion.
     * @param nombreCanal String nombreCanal por el cual se ejecuta la
     * transaccion.
     * @return IbModulosDTO con modulos y transacciones
     */
    public IbModulosDTO consultarModulosPorUsuario(String idUsuario, String idCanal, String nombreCanal);
    
    /**
     * Metodo para obtener el modelo y la transaccion de los que tengan el posee beneficiario activo
     * @param nombreCanal String ID del canal
     * @return IbModulosDTO
     */
    public IbModulosDTO obtenerModeloTransBeneficiario(String nombreCanal);
    
    /**
     * Método para obtener todos los módulos y transacciones asociadas a un canal en específico
     * esten activos o inactivos, visibles o no visibles
     * @author wilmer.rondon
     * @param idCanal canal del cual se desea obtener los modulos y las transacciones
     * @param nombreCanal nombre del canal para efectos de los logs
     * @return IbModulosDTO
     */
    public IbModulosDTO obtenerTodosModulosYTransacciones(String idCanal, String nombreCanal);
    
}
