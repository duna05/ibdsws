/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dao;

import com.bds.ws.dto.ActividadEconomicaDTO;
import com.bds.ws.dto.MunicipioDTO;
import com.bds.ws.dto.SectorDTO;
import com.bds.ws.dto.TipoIdentificacionDTO;

/**
 *
 * @author humberto.rojas
 */
public interface MunicipioDAO {
    
    public MunicipioDTO consultarMunicipio();
    
}
