/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.services;

import com.bds.ws.dao.DelSurDAO;
import com.bds.ws.dto.DelSurDTO;
import com.bds.ws.dto.UtilDTO;
import java.util.List;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author rony.rodriguez
 */
@WebService(serviceName = "DelSurWS")
public class DelSurWS {

    @EJB
    private DelSurDAO delsurDAO;

    /**
     * Metodo que obtiene el detalle de los nombres de las agencias y estados.
     *
     * @param codigoCanal codigo canal
     * @return DelSurDTO listado de las agencias
     */
    @WebMethod(operationName = "obtenerDatosEstadoAgencias")
    public DelSurDTO obtenerDatosEstadoAgencias(@WebParam(name = "codigoCanal") String codigoCanal) {
        return delsurDAO.obtenerDatosEstadoAgencias(codigoCanal);
    }
    
    /**
     * Metodo para obtener el listado de bancos
     * @param codigoCanal codigo del canal
     * @return listado de bancos
     */
    @WebMethod(operationName = "listadoBancos")
    public DelSurDTO listadoBancos(@WebParam(name = "codigoCanal") String codigoCanal) {
        return delsurDAO.listadoBancos(codigoCanal);
    }
    
    /**
     * Metodo para verificar que el banco no posee restricciones internas
     * @param codbanco codigo del banco
     * @param nombreCanal nombre del canal
     * @return RespuestaDTO
     */
    @WebMethod(operationName = "verificarBloqueBanco")
    public UtilDTO verificarBloqueBanco (@WebParam(name = "codbanco")String codbanco,@WebParam(name = "nombreCanal") String nombreCanal){
        return delsurDAO.verificarBloqueBanco(codbanco, nombreCanal);
    }

     /**
     * Metodo para obtenar las fechas de los dias feriados dado el numero de meses
     * requerido
     *
     * @param cantMeses Integer secuencia de convenio a consultar
     * @param iCodExtCanal String condigo del canal para realizar la consulta
     * @return List<String> lista con las fechas de feriados
     * formato dd/MM/yyyy
     */
    @WebMethod(operationName = "buscarDiasFeriados")
    public List<String> buscarDiasFeriados(@WebParam(name = "cantMeses") Integer cantMeses, @WebParam(name = "iCodExtCanal") String iCodExtCanal){
        return this.delsurDAO.buscarDiasFeriados(cantMeses, iCodExtCanal);
    }

    
    /**
     * Metodo para obtener el listado de bancos afiliados a Switch7B
     * @param codigoCanal codigo del canal
     * @return listado de bancos
     */
    @WebMethod(operationName = "listadoBancosSwitch7B")
    public DelSurDTO listadoBancosSwitch7B(@WebParam(name = "codigoCanal") String codigoCanal) {
        return delsurDAO.listadoBancosSwitch7B(codigoCanal);
    }
    
    /**
     * Metodo que valida si el usuario está habilitado para hacer afiliaciones P2P
     * @param cedula
     * @param codigoCanal codigo del canal
     * @param idCanal id del canal
     * @return listado de bancos
     */
    @WebMethod(operationName = "validarUsuarioPilotoP2P")
    public UtilDTO validarUsuarioPilotoP2P(
            @WebParam(name = "cedula") String cedula,
            @WebParam(name = "idCanal") String idCanal,
            @WebParam(name = "codigoCanal") String codigoCanal) {
        return delsurDAO.validarUsuarioPilotoP2P(cedula, idCanal, codigoCanal);
    }

}
