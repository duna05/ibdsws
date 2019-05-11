
package com.bds.ws.services;

import com.bds.ws.dao.IBAcumuladoTransaccionDAO;
import com.bds.ws.dto.IBAcumuladoTransaccionDTO;
import com.bds.ws.dto.RespuestaDTO;
import java.util.Date;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author juan.faneite
 */
@WebService(serviceName = "IbAcumuladoTransaccionWS")
public class IbAcumuladoTransaccionWS {
 
    @EJB
    private IBAcumuladoTransaccionDAO acumuladoTransaccionDAO;

   /**
     * Metodo para insertar una afiliacion
     * @param idUsuario
     * @param montoTransaccion
     * @param idTransaccion
     * @param nombreCanal nombre del canal
     * @return RespuestaDTO indica si la operacion se realizo de manera exitosa o no
     */
    @WebMethod(operationName = "insertarAcumuladoTransaccion")
    public RespuestaDTO insertarAcumuladoTransaccion(@WebParam(name = "idusuario") String idUsuario, @WebParam(name = "montoTransaccion") String montoTransaccion, @WebParam(name = "idTransaccion") String idTransaccion, @WebParam(name = "nombreCanal") String nombreCanal) {        
        return acumuladoTransaccionDAO.insertarAcumuladoTransaccion(idUsuario, montoTransaccion, idTransaccion, nombreCanal);
    }
    
   /**
     * Metodo para insertar una afiliacion
     * @param idUsuario
     * @param montoTransaccion
     * @param idTransaccion
     * @param nombreCanal nombre del canal
     * @return RespuestaDTO indica si la operacion se realizo de manera exitosa o no
     */
    @WebMethod(operationName = "consultarAcumuladoTransaccion")
    public IBAcumuladoTransaccionDTO consultarAcumuladoTransaccion(@WebParam(name = "idusuario") String idUsuario, @WebParam(name = "nombreCanal") String nombreCanal) {        
        return acumuladoTransaccionDAO.consultarAcumuladoUsuario(idUsuario, nombreCanal);
    }
    
    
}
