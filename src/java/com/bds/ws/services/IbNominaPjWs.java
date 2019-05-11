/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.services;

import com.bds.ws.dto.UtilDTO;
import java.util.List;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import com.bds.ws.dao.NominaPjDAO;

/**
 *
 * @author juan.vasquez
 */
@WebService(serviceName = "IbNominaPjWs")
public class IbNominaPjWs {
    
    @EJB
    private NominaPjDAO ibNominaPjDAO;
    
    /**
     * Metodo para registrar las transacciones pendientes
     *
     * @param codCliente String codigo del cliente a consultar
     * @param secuenciaConvenio Integer secuencia de convenio a consultar
     * @param iCodExtCanal String condigo del canal para realizar la consulta
     * @return UtilDTO indica si la operacion se realizo de manera exitosa o no y contiene el dato de respuesta
     * en el map con key esExcluido
     */
    @WebMethod(operationName = "validarClienteExcluido")
    public UtilDTO validarClienteExcluido(@WebParam(name = "codCliente")String codCliente, @WebParam(name = "secuenciaConvenio")Integer secuenciaConvenio, @WebParam(name = "nombreCanal")String nombreCanal) {
        return this.ibNominaPjDAO.validarClienteExcluido(codCliente, secuenciaConvenio, nombreCanal);
    }
    
    
    /**
     * Metodo para obtenar las cuentas asociadas a un convenio
     *
     * @param secuenciaConvenio Integer secuencia de convenio a consultar
     * @param iCodExtCanal String condigo del canal para realizar la consulta
     * @return UtilDTO indica si la operacion se realizo de manera exitosa o no y contiene el dato de respuesta
     * en el map con key cuentaList, es una lista de tipo String
     */
    @WebMethod(operationName = "buscarCuentasPorConvenio")
    public List<String> buscarCuentasPorConvenio(@WebParam(name = "secuenciaConvenio")Integer secuenciaConvenio,@WebParam(name = "iCodExtCanal") String iCodExtCanal){
        return this.ibNominaPjDAO.buscarCuentasPorConvenio(secuenciaConvenio, iCodExtCanal);
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
        return this.ibNominaPjDAO.buscarDiasFeriados(cantMeses, iCodExtCanal);
    } 
    
    /**
     * Metodo para validar que el numero de cuenta introducido exista y pertenesca 
     * al benefeciario asignado
     *
     * @param nroCuenta String snumero de cuenta a validar
     * @param identBeneficiario String identificacion del benefeciario
     * @param iCodExtCanal String condigo del canal para realizar la consulta
     * @return UtilDTO indica si la operacion se realizo de manera exitosa o no y contiene el dato de respuesta
     * en el map con key perteneseCta, es una tipo String
     */
    @WebMethod(operationName = "validarCtaBeneficiario")
    public UtilDTO validarCtaBeneficiario(@WebParam(name = "nroCuenta") String nroCuenta,@WebParam(name = "identBeneficiario") String identBeneficiario,
                                            @WebParam(name = "iCodExtCanal") String iCodExtCanal){
        return this.ibNominaPjDAO.validarCtaBeneficiario(nroCuenta, identBeneficiario, iCodExtCanal);
    }
}
