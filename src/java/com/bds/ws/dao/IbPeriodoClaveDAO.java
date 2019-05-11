/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dao;

import com.bds.ws.dto.IbPeriodoClaveDTO;

/**
 * Interfaz DAO para periodo de clave
 * @author juan.faneite
 */
public interface IbPeriodoClaveDAO {
    
    /**
     * Metodo que obtiene el listado de periodos de clave
     * @param nombreCanal nombre del canal
     * @return IbPeriodoClaveDTO listado de periodos de clave
     */
    public IbPeriodoClaveDTO listaPeriodoClave (String nombreCanal);
    
}
