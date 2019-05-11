package com.bds.ws.dto;

import com.bds.ws.model.IbAcumuladoTransaccion;
import java.io.Serializable;
import java.util.List;

/**
 * Clase IBAcumuladoTransaccionDTO
 *
 * @author mdiaz
 */
public class IBAcumuladoTransaccionDTO implements Serializable {

    private IbAcumuladoTransaccion ibAcumuladoTransaccion;
    private List<IbAcumuladoTransaccion> ibAcumuladoTransacciones;
    private RespuestaDTO respuesta;

    public IBAcumuladoTransaccionDTO() {
    }

    /**
     * Obtiene el modelo de ibAcumuladoTransaccion
     *
     * @return ibAcumuladoTransaccion Obtiene el modelo de
     * ibAcumuladoTransaccion
     */
    public IbAcumuladoTransaccion getIbAcumuladoTransaccion() {
        return ibAcumuladoTransaccion;
    }

    /**
     * Inserta el modelo de ibAcumuladoTransaccion
     *
     * @param ibAcumuladoTransaccion
     */
    public void setIbAcumuladoTransaccion(IbAcumuladoTransaccion ibAcumuladoTransaccion) {
        this.ibAcumuladoTransaccion = ibAcumuladoTransaccion;
    }

    /**
     * Obtiene un listado del modelo ibAcumuladoTransacciones
     *
     * @return ibAcumuladoTransacciones Obtiene un listado del modelo
     * ibAcumuladoTransacciones
     */
    public List<IbAcumuladoTransaccion> getIbAcumuladoTransacciones() {
        return ibAcumuladoTransacciones;
    }

    /**
     * Obtiene un listado del modelo IbAfiliaciones
     *
     * @param ibAcumuladoTransacciones
     */
    public void setIbAcumuladoTransacciones(List<IbAcumuladoTransaccion> ibAcumuladoTransacciones) {
        this.ibAcumuladoTransacciones = ibAcumuladoTransacciones;
    }

    /**
     * retorna la respuesta de la operacion que se realiza.
     *
     * @return RespuestaDTO
     */
    public RespuestaDTO getRespuesta() {
        return respuesta;
    }

    /**
     * asigna objeto para almacenar la respuesta de la transaccion
     *
     * @param respuesta objeto para almacenar la respuesta de la transaccion
     */
    public void setRespuesta(RespuestaDTO respuesta) {
        this.respuesta = respuesta;
    }

}
