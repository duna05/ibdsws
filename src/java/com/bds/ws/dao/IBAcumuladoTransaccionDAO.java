
package com.bds.ws.dao;

import com.bds.ws.dto.IBAcumuladoTransaccionDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.model.IbUsuarios;
import java.util.Date;

/**
 * Interfaz para Acumulado de Transacciones
 * @author mdiaz
 */
public interface IBAcumuladoTransaccionDAO {
 
     /**
     * Metodo para insertar una afiliacion
     * @param idUsuario    
     * @param montoTransaccion  
     * @param idTransaccion  
     * @param nombreCanal    
     
     * @return RespuestaDTO indica si la operacion se realizo de manera exitosa o no
     */
    public RespuestaDTO insertarAcumuladoTransaccion(String idUsuario, String montoTransaccion, String idTransaccion, String nombreCanal);

    
    /**
     * Metodo para obtener el afiliado por codigo de usuario y Id de Usuario
     * @param idUsuario
     * @param fecha
     * @param nombreCanal
     * @return IBAfiliacionesDTO -> afiliacion Seleccionada
     */
    public IBAcumuladoTransaccionDTO consultarAcumuladoUsuario(String idUsuario, String nombreCanal);   
    
}
