/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Clase TarjetaDebitoDTO
 * @author rony.rodriguez
 */
public class TarjetaDebitoDTO implements Serializable {

    private String numeroTarjeta;	//Numero de Tarjeta de 20 digitos.
    private String estado;	//Estatus de la Tarjeta de debito.
    private ClienteDTO cliente;  //cliente asociado a la tdd
    private String codigoAgencia;
    private String codigoSubaplicacion;
    private List<ReclamoDTO> reclamoDTO;
    private List<TarjetaDebitoDTO> tddsDTO;
    private RespuestaDTO respuesta;     //manejo de respuestas

    /**
     * 
     * @return listado de resclamos asociados a las TDD
     */
    public List<ReclamoDTO> getReclamoDTO() {
        return reclamoDTO;
    }

    /**
     * 
     * @param reclamoDTO listado de resclamos asociados a las TDD
     */
    public void setReclamoDTO(List<ReclamoDTO> reclamoDTO) {
        this.reclamoDTO = reclamoDTO;
    }    
    
    /**
     * obtiene codigo de agencia
     * @return codigo de agencia
     */
    public String getCodigoAgencia() {
        return codigoAgencia;
    }

    /**
     * inserta codigo de agencia
     * @param codigoAgencia codigo de agencia
     */
    public void setCodigoAgencia(String codigoAgencia) {
        this.codigoAgencia = codigoAgencia;
    }

    /**
     * obtiene codigo de subaplicacion
     * @return codigo de subaplicacion
     */
    public String getCodigoSubaplicacion() {
        return codigoSubaplicacion;
    }

    /**
     * inserta codigo de subaplicacion
     * @param codigoSubaplicacion codigo de subaplicacion
     */
    public void setCodigoSubaplicacion(String codigoSubaplicacion) {
        this.codigoSubaplicacion = codigoSubaplicacion;
    }
    
    /**
     * obtiene cliente asociado a la tdd
     * @return cliente
     */
    public ClienteDTO getCliente() {
        return cliente;
    }

    /**
     * inserta cliente asociado a la tdd
     * @param cliente cliente
     */
    public void setCliente(ClienteDTO cliente) {
        this.cliente = cliente;
    }
    
    /**
     * @return the numeroTarjeta
     */
    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    /**
     * @param numeroTarjeta the numeroTarjeta to set
     */
    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    /**
     * @return the estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * @return the respuesta
     */
    public RespuestaDTO getRespuesta() {
        return respuesta;
    }

    /**
     * @param respuesta the respuesta to set
     */
    public void setRespuesta(RespuestaDTO respuesta) {
        this.respuesta = respuesta;
    }

    public List<TarjetaDebitoDTO> getTddsDTO() {
        return tddsDTO;
    }

    public void setTddsDTO(List<TarjetaDebitoDTO> tddsDTO) {
        this.tddsDTO = tddsDTO;
    }
    
    
}
