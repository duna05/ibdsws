/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dao;

import com.bds.ws.dto.PreguntaAutenticacionDTO;
import com.bds.ws.dto.UtilDTO;

/**
 * Interfaz DAO para preguntas de autenticacion
 * @author juan.faneite
 */
public interface PreguntasAutenticacionDAO {

    /**
     * Metodo para obtener las preguntas de autenticacion de un cliente (core bancario)
     * @param tdd tarjeta de debito del cliente
     * @param nroCta numero de cuenta del cliente
     * @param nroPreguntas numero de preguntas a mostrar
     * @param nombreCanal nombre del canal
     * @return PreguntaAutenticacionDTO -> con listado de preguntas de autenticacion
     */
    public PreguntaAutenticacionDTO listPDAporCliente(String tdd, String nroCta, int nroPreguntas, String nombreCanal);
    
    /**
     * Metodo para verificar las respuestas de autenticacion 
     * @param tdd tarjeta de debito del cliente
     * @param nroCta numero de cuenta del cliente
     * @param rafaga cadena con las preguntas y respuestas a validar ej: < codigoPregunta >< separador > < codigoPregunta >< separador >< codigoPregunta >< separador >
     * @param separador separador que utilizara en la rafaga, si este es null se tomará guion "-" como separador
     * @param nombreCanal nombre del canal
     * @return UtilDTO con respuesta valida o no para las respuestas
     */
    public UtilDTO validarPDAporCliente(String tdd, String nroCta, String rafaga, String separador, String nombreCanal);
}
