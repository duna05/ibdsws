/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dao;

import com.bds.ws.dto.RespuestaDTO;

/**
 * Interfaz de actualizaciones
 * @author juan.faneite 28/05/2015
 */
public interface ActuacionesDAO {

    /**
     * Obtiene las siglas y la descripcion de un codigo de salida, dado su ID.
     *
     * @param id String id ID del codigo de salida devuelto por algun
     * procedimiento.
     * @param canal String canal Codigo (extendido) del canal desde el cual es
     * llamado el procedimiento.
     * @return RespuestaDTO indica si el metodo se realizo de manera exitosa o no
     */
    public RespuestaDTO obtenerDescripcionSalidaSP(String id, String canal);

}
