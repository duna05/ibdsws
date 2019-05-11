/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dao;

import com.bds.ws.dto.IbLogsPjDTO;

/**
 * Interfaz DAO para logs
 * @author luis.perez
 */
public interface IbLogsPjDAO {
    
    /**
     * Metodo para obtener el listado de moviemientos del log del usuario
     *
     * @param idUsuario id del usuario
     * @param idEmpresa id de la empresa en IB
     * @param fechaDesde fecha desde si viene null se tomaran los ultimos registros
     * @param fechaHasta fecha hasta si viene null se tomaran los ultimos registros
     * @param idTransaccion número de la transacción por la que se realizará el filtro
     * @param idCanal id del canal a filtrar
     * @param nombreCanal nombre del canal
     * @return IbLogsDTO -> List < IbLogs > listado de moviemientos del log
     */
    public IbLogsPjDTO listadoHistoricoUsuarioEmpresaPj (String idUsuario,String idEmpresa, String fechaDesde, String fechaHasta, String idTransaccion, String idCanal, String nombreCanal);
}
