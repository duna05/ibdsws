/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dao;

import com.bds.ws.dto.UtilDTO;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author juan.vasquez
 */
public interface NominaPjDAO {
    
    
    /**
     * Metodo para registrar las transacciones pendientes
     *
     * @param codCliente String codigo del cliente a consultar
     * @param secuenciaConvenio Integer secuencia de convenio a consultar
     * @param iCodExtCanal String condigo del canal para realizar la consulta
     * @return UtilDTO indica si la operacion se realizo de manera exitosa o no y contiene el dato de respuesta
     * en el map con key esExcluido
     */
    public UtilDTO validarClienteExcluido(String codCliente, Integer secuenciaConvenio, String iCodExtCanal);
    
    /**
     * Metodo para obtenar las cuentas asociadas a un convenio
     *
     * @param secuenciaConvenio Integer secuencia de convenio a consultar
     * @param iCodExtCanal String condigo del canal para realizar la consulta
     * @return UtilDTO indica si la operacion se realizo de manera exitosa o no y contiene el dato de respuesta
     * en el map con key cuentaList, es una lista de tipo String
     */
    public List<String> buscarCuentasPorConvenio(Integer secuenciaConvenio, String iCodExtCanal);
    
    
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
     * Metodo para validar que el numero de cuenta introducido exista y pertenesca 
     * al benefeciario asignado
     *
     * @param nroCuenta String snumero de cuenta a validar
     * @param identBeneficiario String identificacion del benefeciario
     * @param iCodExtCanal String condigo del canal para realizar la consulta
     * @return UtilDTO indica si la operacion se realizo de manera exitosa o no y contiene el dato de respuesta
     * en el map con key perteneseCta, es una tipo String
     */
    public UtilDTO validarCtaBeneficiario(String nroCuenta, String identBeneficiario, String iCodExtCanal);
    
    /*  validarConvenio
     * @param codigoClienteAbanks
     * @param codigoConvenio
     * @param codCuenta10
     * @param iCodExtCanal
     * @return resultado booleano que significa:
          "la cuenta pertenezca al convenio " Y 
          "el número de convenio corresponda al cliente".
     */
    public Boolean validarConvenio(String codigoClienteAbanks,
                                        BigDecimal codigoConvenio,
                                        String codCuenta10,
                                        String iCodExtCanal) ;    
}