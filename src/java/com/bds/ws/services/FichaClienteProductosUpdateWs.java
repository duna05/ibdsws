
package com.bds.ws.services;

import com.bds.ws.dao.FichaClienteProductosUpdateDAO;


import com.bds.ws.dto.UtilDTO;
import javax.jws.WebService;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 *
 * @author audra.zapata
 */

@WebService(serviceName = "FichaClienteProductosUpdateWs")
public class FichaClienteProductosUpdateWs {
    
    @EJB
    private FichaClienteProductosUpdateDAO fichaClienteProductosUpdateDAO;
    //insertUpdateFichaClienteDireccion(String iCodigoCliente, String tipoVivienda);
    @WebMethod(operationName = "actualizarClienteProductos")
    public UtilDTO actualizarClienteProductos(@WebParam(name = "codigoCliente") String codigoCliente,@WebParam(name = "motivoSolicitud") String motivoSolicitud) {
        return fichaClienteProductosUpdateDAO.actualizarClienteProductos(codigoCliente,motivoSolicitud);        
    }
}
