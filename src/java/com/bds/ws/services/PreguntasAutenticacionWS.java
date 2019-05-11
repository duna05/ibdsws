/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.services;

import com.bds.ws.dao.PreguntasAutenticacionDAO;
import com.bds.ws.dto.PreguntaAutenticacionDTO;
import com.bds.ws.dto.UtilDTO;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * Clase de servicios web de preguntas de autenticacion
 * @author juan.faneite
 */
@WebService(serviceName = "PreguntasAutenticacionWS")
public class PreguntasAutenticacionWS {
    @EJB
    private PreguntasAutenticacionDAO ejbRef;

    /**
     * Metodo para obtener las preguntas de autenticacion de un cliente (core bancario)
     * @param tdd tarjeta de debito del cliente
     * @param nroCta numero de cuenta del cliente
     * @param nroPreguntas numero de preguntas a mostrar
     * @param nombreCanal nombre del canal
     * @return PreguntaAutenticacionDTO -> con listado de preguntas de autenticacion
     */
    @WebMethod(operationName = "listPDAporCliente")
    public PreguntaAutenticacionDTO listPDAporCliente(@WebParam(name = "tdd") String tdd, @WebParam(name = "nroCta") String nroCta, @WebParam(name = "nroPreguntas") int nroPreguntas, @WebParam(name = "nombreCanal") String nombreCanal) {
        return ejbRef.listPDAporCliente(tdd, nroCta, nroPreguntas, nombreCanal);
    }
    
    /**
     * Metodo para verificar las respuestas de autenticacion 
     * @param tdd tarjeta de debito del cliente
     * @param nroCta numero de cuenta del cliente
     * @param rafaga cadena con las preguntas y respuestas a validar ej: < codigoPregunta >< separador > < codigoPregunta >< separador >< codigoPregunta >< separador >
     * @param separador separador que utilizara en la rafaga, si este es null se tomará guion "-" como separador
     * @param nombreCanal nombre del canal
     * @return UtilDTO con respuesta valida o no para las respuestas
     */
    @WebMethod(operationName = "validarPDAporCliente")
    public UtilDTO validarPDAporCliente(@WebParam(name = "tdd") String tdd, @WebParam(name = "nroCta") String nroCta, @WebParam(name = "rafaga") String rafaga, @WebParam(name = "separador") String separador, @WebParam(name = "nombreCanal") String nombreCanal) {
        return ejbRef.validarPDAporCliente(tdd, nroCta, rafaga, separador , nombreCanal);
    }
}
