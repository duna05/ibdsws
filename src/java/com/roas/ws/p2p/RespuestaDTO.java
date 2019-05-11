package com.roas.ws.p2p;

import java.io.Serializable;

public class RespuestaDTO implements Serializable {

    private static final long serialVersionUID = 8799656478674716635L;
    //Atributos que funcionan para el manejo de errores interno.
    //codigo de respuesta de status, 000 representa estatus exitoso
    private String codigo;
    private String descripcion; //breve descripcion relativa al codigo
    private String referenciaTransaccion; //referencia de la transaccion en switch p2p

    public String getReferenciaTransaccion() {
        return referenciaTransaccion;
    }

    public void setReferenciaTransaccion(String referenciaTransaccion) {
        this.referenciaTransaccion = referenciaTransaccion;
    }

    //Atributos que describen la respuesta de determinados SP    
    private String codigoSP;        //Codigo arrojado por SP invocado.
    private String descripcionSP;   //Siglas del codigo devuelto.

    public RespuestaDTO() {
        this.codigo = "000";
        this.descripcion = "OK";
        this.codigoSP = "000";
        this.descripcionSP = "OK";
        this.referenciaTransaccion = "NOOBTENIDA";
    }

    /**
     * Obtiene Codigo arrojado por SP invocado
     *
     * @return String
     */
    public String getCodigoSP() {
        return codigoSP;
    }

    /**
     * Ingresa Codigo arrojado por SP invocado
     *
     * @param codigoSP String
     */
    public void setCodigoSP(String codigoSP) {
        this.codigoSP = codigoSP;
    }

    /**
     * Obtiene Siglas del codigo devuelto.
     *
     * @return String
     */
    public String getDescripcionSP() {
        return descripcionSP;
    }

    /**
     * Ingresa Siglas del codigo devuelto.
     *
     * @param descripcionSP String
     */
    public void setDescripcionSP(String descripcionSP) {
        this.descripcionSP = descripcionSP;
    }

    /**
     * Retorna el codigo de respuesta de status, 000 representa estatus exitoso
     *
     * @return String
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Asignar codigo de resuesta
     *
     * @param codigo String
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
        if (!this.codigo.equals("000")) {
            this.descripcion = "FAIL";
        }
    }

    /**
     * Retorna una breve descripcion relativa al codigo
     *
     * @return String descripcion de respuesta de status
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Asignar descripcion
     *
     * @param descripcion String
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
