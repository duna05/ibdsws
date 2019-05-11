/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dao;

import com.bds.ws.dto.DelSurDTO;
import com.bds.ws.dto.UtilDTO;
import java.util.List;

/**
 * Interfaz de metodos especificos del banco del sur
 * @author rony.rodriguez
 */
public interface DelSurDAO {

    /**
     * Metodo que obtiene el detalle de los nombres de las agencias y estados.
     *
     * @param codigoCanal codigo canal
     * @return DelSurDTO listado de las agencias
     */
    public DelSurDTO obtenerDatosEstadoAgencias(String codigoCanal);

    /**
     * Metodo para obtener el listado de bancos
     * @param codigoCanal codigo del canal
     * @return listado de bancos
     */
    public DelSurDTO listadoBancos (String codigoCanal);
    
    /**
     * Metodo para verificar que el banco no posee restricciones internas
     * @param codbanco codigo del banco
     * @param nombreCanal nombre del canal
     * @return RespuestaDTO
     */
    public UtilDTO verificarBloqueBanco (String codbanco, String nombreCanal);
    
    
    /**
     * Metodo para saber si una fecha es un feriado
     *
     * @param dia, formato dd/MM/yyyy
     * @param iCodExtCanal
     * @return 
     */
    public Boolean isDiaFeriado(String dia, String iCodExtCanal);

    
    /**
     * Metodo para obtenar las fechas de los dias feriados dado el numero de meses
     * requerido
     *
     * @param cantMeses Integer secuencia de convenio a consultar
     * @param iCodExtCanal String condigo del canal para realizar la consulta
     * @return List<String> lista con las fechas de feriados
     * formato dd/MM/yyyy
     */
    public List<String> buscarDiasFeriados(Integer cantMeses, String iCodExtCanal);
    
    /**
     * Metodo para obtener el listado de bancos afiliados a Switch7B
     * @param codigoCanal codigo del canal
     * @return listado de bancos
     */
    public DelSurDTO listadoBancosSwitch7B (String codigoCanal);
    
    /**
     * Metodo que valida si el usuario está habilitado para hacer afiliaciones P2P
     * @param cedula
     * @param codigoCanal codigo del canal
     * @param idCanal id del canal
     * @return listado de bancos
     */
    public UtilDTO validarUsuarioPilotoP2P(String cedula, String idCanal, String codigoCanal);
}
