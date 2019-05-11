/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dao;

import com.bds.ws.dto.IbAprobacionesServiciosPjDTO;
import com.bds.ws.dto.RespuestaDTO;
import java.math.BigDecimal;

/**
 *
 * @author luis.perez
 */
public interface IbAprobacionesServiciosPjDAO {
    /**
     * Lista las aprobaciones por servicio para una empresa
     *
     * Parametros
     *
     * @param idEmpresa codigo interno de la empresa en IB
     * @param idCanal codigo del canal interno en IB
     * @param codigoCanal nombre del canal en el CORE
     *
     * @return the com.bds.ws.dto.IbAprobacionesServiciosPjDTO
     */
    
    public IbAprobacionesServiciosPjDTO consultarAprobacionesEmpresaServiciosPj(String idEmpresa, String idCanal, String codigoCanal);
    
    /**
     * Inserta la cantidad de aprobadores por servicio para una empresa
     *
     * Parametros
     *
     * @param ibAprobacionesServiciosPjDTO lista de servicios con su cantidad de
     * aprobadores
     * @param idCanal codigo del canal interno en IB
     * @param codigoCanal nombre del canal en el CORE
     *
     * @return the com.bds.ws.dto.RespuestaDTO
     */
    public RespuestaDTO insertarAprobacionesEmpresaServiciosPj(IbAprobacionesServiciosPjDTO ibAprobacionesServiciosPjDTO, String idCanal, String codigoCanal);
    
    /**
     * Busca AprobacionServicio por servicio "Nomina", para una empresa dada
     * @param cdClienteAbanks
     * @param idCanal
     * @return IbAprobacionesServiciosPjDTO de servicio Nomina para la empresa dada
     */
    public IbAprobacionesServiciosPjDTO consultarByEmpresa(BigDecimal cdClienteAbanks, String servicio, String idCanal);
    
}
