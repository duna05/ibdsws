/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.services;

import com.bds.ws.dao.IbParametrosDAO;
import com.bds.ws.dto.IbParametrosDTO;
import java.math.BigDecimal;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * Clase de servicios web para parametros
 * @author rony.rodriguez
 */
@WebService(serviceName = "IbParametrosWS")
public class IbParametrosWS {

    @EJB
    private IbParametrosDAO ibParametrosDAO;

    /**
     * Metodo para consultar un parametro en IB_PARAMETRO bd oracle 11
     * @param codigoParametro codigo del parametro - hace referencia al atributo codigo de la tabla IB_PARAMETRO
     * @param nombreCanal nombre del canal
     * @return IbParametrosDTO un parametro en IB_PARAMETRO
     */
    @WebMethod(operationName = "consultaParametro")
    public IbParametrosDTO consultaParametro(@WebParam(name = "codigoParametro") String codigoParametro, @WebParam(name = "nombreCanal") String nombreCanal) {
        return ibParametrosDAO.consultaParametro(codigoParametro, nombreCanal);
    }

    /**
     * Metodo para consultar un parametro en IB_PARAMETRO bd oracle 11
     * @param codigoParametro codigo del parametro - hace referencia al atributo codigo de la tabla IB_PARAMETRO
     * @param idCanal id del canal
     * @param nombreCanal nombre del canal
     * @return IbParametrosDTO un parametro en IB_PARAMETRO
     */
    @WebMethod(operationName = "consultaParametroPorIdCanal")
    public IbParametrosDTO consultaParametroPorIdCanal(@WebParam(name = "codigoParametro") String codigoParametro, @WebParam(name = "idCanal") BigDecimal idCanal, @WebParam(name = "nombreCanal") String nombreCanal) {
        return ibParametrosDAO.consultaParametro(codigoParametro, idCanal, nombreCanal);
    }

}
