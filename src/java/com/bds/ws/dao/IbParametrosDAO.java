/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dao;

import com.bds.ws.dto.IbParametrosDTO;
import java.math.BigDecimal;

/**
 * Interfaz DAO para parametros
 * @author rony.rodriguez
 */
public interface IbParametrosDAO {

    /**
     * Metodo para consultar un parametro en IB_PARAMETRO bd oracle 11
     * @param codigoParametro codigo del parametro - hace referencia al atributo codigo de la tabla IB_PARAMETRO
     * @param nombreCanal nombre del canal
     * @return IbParametrosDTO un parametro en IB_PARAMETRO
     */
    public IbParametrosDTO consultaParametro(String codigoParametro, String nombreCanal);

    /**
     * Metodo para consultar un parametro en IB_PARAMETRO bd oracle 11
     * @param codigoParametro codigo del parametro - hace referencia al atributo codigo de la tabla IB_PARAMETRO
     * @param idCanal id del canal
     * @param nombreCanal nombre del canal
     * @return IbParametrosDTO un parametro en IB_PARAMETRO
     */
    public IbParametrosDTO consultaParametro(String codigoParametro, BigDecimal idCanal, String nombreCanal);

}
