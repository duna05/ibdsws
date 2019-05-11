/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Clase MovimientoCuentaDTO
 * @author cesar.mujica
 */
public class MovimientoCuentaDTO implements Serializable {
    private String nombreAgencia;           //Nombre de la agencia en la cual se realizo el movimiento (si aplica).
    private String secuenciaMovimiento;             //Numero secuencial identificador del movimiento.
    private String secuenciaExtMovimiento;          //Identificador extendido (completo) del movimiento.                                       
    private String numeroReferencia;                //Identificador unico del movimiento (numero_documento). 
    private String codigoAplicacion;
    private String tipoTransaccion;                                   
    private BigDecimal monto;                       //monto del movimiento.    
    private Date fechaTransaccion;                  //Fecha y hora en los cuales se realizo el movimiento.    
    private String fechaTransaccionString;          //Fecha y hora en los cuales se realizo el movimiento.    
    private String descripcion;                     //Descripcion del movimiento.    
    private String debitoCredito;
    private String numeroCuentaOrigen;  
    private String numeroCuentaDestino;
    private String codigoAgencia;                   //codigo de agencia donde se realizo el movimiento  
    private String identificadorEmisor;
    private String nombreEmisor;
    private String identificadorBeneficiario;       //id del Beneficiario de el movimiento        
    private String nombreBancoOrigen;
    private String codigoBancoDestino;              //codigo de Banco de Destino del movimiento
    private String nombreBancoDestino;
    private Date hora;
    private String horaString;
    private String fechaBloqueo;                      //Define la fecha donde se activa el embargo.
    private String fechaReserva;                       //Fecha en que se realizó el depósito.
    private String canal;                           //id del canal donde se realizo el movimiento    
    
    /**
     * Usado solo para listar los movimientos
     */

    private BigDecimal egreso;              //Monto egresado de la cuenta.
    private BigDecimal ingreso;             //Monto ingresado a la cuenta.
    
    private String tipoDeposito;            //Tipo de deposito (si aplica).
    private String nombreBeneficiario;      //Nombre del beneficiario en caso de que aplique (CI, RIF, etc).
    private String codigoBancoOrigen;       //Codigo del banco desde el cual se recibieron los fondos (si aplica).
    private String siglasTipoOp;            //Siglas que identifican el tipo de operacion (tipo de transaccion).
    private String descripcionTipoOp;       //Descripcion del tipo de operacion (tipo de transaccion).
    private String codigoUsuario;           //Codigo del usuario de la transaccion.
    
    /**
     * ****************CAMPOS EXCLUSIVOS PAAR CUENTAS MONEDA
     * EXTRAJERA******************
     */
    private BigDecimal debitosDolar;       //Monto (en dolares) egresado de la cuenta.
    private BigDecimal creditosDolar;      //Monto (en dolares) ingresado a la cuenta.
    private BigDecimal debitosBs;          //Monto (en bolivares) egresado de la cuenta.
    private BigDecimal creditosBs;         //Monto (en bolivares) ingresado a la cuenta.
    private BigDecimal valorCambioTasa;    //Tasa de cambio aplicada al movimiento.
    private RespuestaDTO respuesta;         //manejo de respuesta

    /**
     * @return codigo de la aplicacion
     */
    public String getCodigoAplicacion() {
        return codigoAplicacion;
    }

    /**
     * @param codigoAplicacion inserta codigo de la aplicacion
     */
    public void setCodigoAplicacion(String codigoAplicacion) {
        this.codigoAplicacion = codigoAplicacion;
    }

    /**
     * @return tipo de transaccion
     */
    public String getTipoTransaccion() {
        return tipoTransaccion;
    }

    /**
     * @param tipoTransaccion inserta tipo de transaccion
     */
    public void setTipoTransaccion(String tipoTransaccion) {
        this.tipoTransaccion = tipoTransaccion;
    }

    /**
     * 
     * @return debito o credito
     */
    public String getDebitoCredito() {
        return debitoCredito;
    }

    /**
     * 
     * @param debitoCredito debito o credito
     */
    public void setDebitoCredito(String debitoCredito) {
        this.debitoCredito = debitoCredito;
    }

    /**
     * 
     * @return numero de cuenta origen
     */
    public String getNumeroCuentaOrigen() {
        return numeroCuentaOrigen;
    }

    /**
     * 
     * @param numeroCuentaOrigen numero de cuenta origen
     */
    public void setNumeroCuentaOrigen(String numeroCuentaOrigen) {
        this.numeroCuentaOrigen = numeroCuentaOrigen;
    }

    /**
     * 
     * @return numero de la cuenta destino
     */
    public String getNumeroCuentaDestino() {
        return numeroCuentaDestino;
    }

    /**
     * 
     * @param numeroCuentaDestino numero de la cuenta destino
     */
    public void setNumeroCuentaDestino(String numeroCuentaDestino) {
        this.numeroCuentaDestino = numeroCuentaDestino;
    }

    /**
     * 
     * @return nombre de la agencia
     */
    public String getNombreAgencia() {
        return nombreAgencia;
    }

    /**
     * 
     * @param nombreAgencia nombre de la agencia
     */
    public void setNombreAgencia(String nombreAgencia) {
        this.nombreAgencia = nombreAgencia;
    }

    /**
     * 
     * @return identificador emisor
     */
    public String getIdentificadorEmisor() {
        return identificadorEmisor;
    }

    /**
     * 
     * @param identificadorEmisor identificador emisor
     */
    public void setIdentificadorEmisor(String identificadorEmisor) {
        this.identificadorEmisor = identificadorEmisor;
    }

    /**
     * 
     * @return Nombre Emisor
     */
    public String getNombreEmisor() {
        return nombreEmisor;
    }

    /**
     * 
     * @param nombreEmisor Nombre Emisor
     */
    public void setNombreEmisor(String nombreEmisor) {
        this.nombreEmisor = nombreEmisor;
    }

    /**
     * 
     * @return Identificador Beneficiario
     */
    public String getIdentificadorBeneficiario() {
        return identificadorBeneficiario;
    }

    /**
     * 
     * @param identificadorBeneficiario Identificador Beneficiario
     */
    public void setIdentificadorBeneficiario(String identificadorBeneficiario) {
        this.identificadorBeneficiario = identificadorBeneficiario;
    }

    /**
     * 
     * @return Nombre Beneficiario
     */
    public String getNombreBeneficiario() {
        return nombreBeneficiario;
    }

    /**
     * 
     * @param nombreBeneficiario Nombre Beneficiario
     */
    public void setNombreBeneficiario(String nombreBeneficiario) {
        this.nombreBeneficiario = nombreBeneficiario;
    }

    /**
     * 
     * @return Codigo Banco Origen
     */
    public String getCodigoBancoOrigen() {
        return codigoBancoOrigen;
    }

    /**
     * 
     * @param codigoBancoOrigen Codigo Banco Origen
     */
    public void setCodigoBancoOrigen(String codigoBancoOrigen) {
        this.codigoBancoOrigen = codigoBancoOrigen;
    }

    /**
     * 
     * @return Nombre de Banco Origen
     */
    public String getNombreBancoOrigen() {
        return nombreBancoOrigen;
    }

    /**
     * 
     * @param nombreBancoOrigen Nombre de Banco Origen
     */
    public void setNombreBancoOrigen(String nombreBancoOrigen) {
        this.nombreBancoOrigen = nombreBancoOrigen;
    }

    /**
     * 
     * @return Nombre del Banco de Destino
     */
    public String getNombreBancoDestino() {
        return nombreBancoDestino;
    }

    /**
     * 
     * @param nombreBancoDestino Nombre del Banco de Destino
     */
    public void setNombreBancoDestino(String nombreBancoDestino) {
        this.nombreBancoDestino = nombreBancoDestino;
    }

    /**
     * 
     * @return Siglas tipo de Operacion
     */
    public String getSiglasTipoOp() {
        return siglasTipoOp;
    }

    /**
     * 
     * @param siglasTipoOp Siglas tipo de Operacion
     */
    public void setSiglasTipoOp(String siglasTipoOp) {
        this.siglasTipoOp = siglasTipoOp;
    }

    /**
     * 
     * @return Descripcion del Tipo de Operacion
     */
    public String getDescripcionTipoOp() {
        return descripcionTipoOp;
    }

    /**
     * 
     * @param descripcionTipoOp Descripcion del Tipo de Operacion
     */
    public void setDescripcionTipoOp(String descripcionTipoOp) {
        this.descripcionTipoOp = descripcionTipoOp;
    }

    /**
     * 
     * @return Codigo del Usuario
     */
    public String getCodigoUsuario() {
        return codigoUsuario;
    }

    /**
     * 
     * @param codigoUsuario Codigo del Usuario
     */
    public void setCodigoUsuario(String codigoUsuario) {
        this.codigoUsuario = codigoUsuario;
    }

    /**
     * 
     * @return Hora
     */
    public Date getHora() {
        return hora;
    }

    /**
     * 
     * @param hora Hora
     */
    public void setHora(Date hora) {
        this.hora = hora;
    }
    
    /**
     * retorna Monto egresado de la cuenta.
     *
     * @return BigDecimal egreso Monto egresado de la cuenta.
     */
    public BigDecimal getEgreso() {
        return egreso;
    }

    /**
     * asigna Numero Monto egresado de la cuenta.
     *
     * @param egreso BigDecimal
     */
    public void setEgreso(BigDecimal egreso) {
        this.egreso = egreso;
    }

    /**
     * retorna Monto ingresado a la cuenta.
     *
     * @return BigDecimal ingreso Monto ingresado a la cuenta.
     */
    public BigDecimal getIngreso() {
        return ingreso;
    }

    /**
     * asigna Monto ingresado a la cuenta.
     *
     * @param ingreso BigDecimal
     */
    public void setIngreso(BigDecimal ingreso) {
        this.ingreso = ingreso;
    }
    
    /**
     * retorna Numero secuencial identificador del movimiento.
     *
     * @return String secuenciaMovimiento Numero secuencial identificador del
     * movimiento.
     */
    public String getSecuenciaMovimiento() {
        return secuenciaMovimiento;
    }

    /**
     * asigna Numero secuencial identificador del movimiento.
     *
     * @param secuenciaMovimiento String
     */
    public void setSecuenciaMovimiento(String secuenciaMovimiento) {
        this.secuenciaMovimiento = secuenciaMovimiento;
    }

    /**
     * retorna el Identificador extendido (completo) del movimiento.
     *
     * @return secuenciaExtMovimiento String
     */
    public String getSecuenciaExtMovimiento() {
        return secuenciaExtMovimiento;
    }

    /**
     * asigna el Identificador extendido (completo) del movimiento.
     *
     * @param secuenciaExtMovimiento String
     */
    public void setSecuenciaExtMovimiento(String secuenciaExtMovimiento) {
        this.secuenciaExtMovimiento = secuenciaExtMovimiento;
    }

    /**
     * retorna Fecha y hora en los cuales se realizo el movimiento.
     *
     * @return Date fechaTransaccion Fecha y hora en los cuales se realizo el
     * movimiento.
     */
    public Date getFechaTransaccion() {
        return fechaTransaccion;
    }

    /**
     * asigna Fecha y hora en los cuales se realizo el movimiento.
     *
     * @param fechaTransaccion Date
     */
    public void setFechaTransaccion(Date fechaTransaccion) {
        this.fechaTransaccion = fechaTransaccion;
    }

    /**
     * retorna Descripcion del movimiento.
     *
     * @return String descripcion Descripcion del movimiento.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * asigna Numero Descripcion del movimiento.
     *
     * @param descripcion String
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * retorna Identificador unico del movimiento (numero_documento).
     *
     * @return String secuenciaMovimiento Identificador unico del movimiento
     * (numero_documento).
     */
    public String getNumeroReferencia() {
        return numeroReferencia;
    }

    /**
     * asigna Identificador unico del movimiento (numero_documento).
     *
     * @param numeroReferencia String
     */
    public void setNumeroReferencia(String numeroReferencia) {
        this.numeroReferencia = numeroReferencia;
    }
    
    /**
     * retorna Monto (en dolares) egresado de la cuenta.
     *
     * @return BigDecimal debitosDolar Monto (en dolares) egresado de la cuenta.
     */
    public BigDecimal getDebitosDolar() {
        return debitosDolar;
    }

    /**
     * asigna Saldo Monto (en dolares) egresado de la cuenta.
     *
     * @param debitosDolar BigDecimal
     */
    public void setDebitosDolar(BigDecimal debitosDolar) {
        this.debitosDolar = debitosDolar;
    }

    /**
     * retorna Monto (en dolares) ingresado a la cuenta.
     *
     * @return BigDecimal creditosDolar Monto (en dolares) ingresado a la
     * cuenta.
     */
    public BigDecimal getCreditosDolar() {
        return creditosDolar;
    }

    /**
     * asigna Saldo Monto (en dolares) ingresado a la cuenta.
     *
     * @param creditosDolar BigDecimal
     */
    public void setCreditosDolar(BigDecimal creditosDolar) {
        this.creditosDolar = creditosDolar;
    }

    /**
     * retorna Monto (en bolivares) egresado de la cuenta.
     *
     * @return BigDecimal debitosBs Monto (en bolivares) egresado de la cuenta.
     */
    public BigDecimal getDebitosBs() {
        return debitosBs;
    }

    /**
     * asigna Monto (en bolivares) egresado de la cuenta.
     *
     * @param debitosBs BigDecimal
     */
    public void setDebitosBs(BigDecimal debitosBs) {
        this.debitosBs = debitosBs;
    }

    /**
     * retorna Monto (en bolivares) ingresado a la cuenta.
     *
     * @return BigDecimal creditosBs Monto (en bolivares) ingresado a la cuenta.
     */
    public BigDecimal getCreditosBs() {
        return creditosBs;
    }

    /**
     * asigna Monto (en bolivares) ingresado a la cuenta.
     *
     * @param creditosBs BigDecimal
     */
    public void setCreditosBs(BigDecimal creditosBs) {
        this.creditosBs = creditosBs;
    }

    /**
     * retorna Tasa de cambio aplicada al movimiento.
     *
     * @return BigDecimal valorCambioTasa Tasa de cambio aplicada al movimiento.
     */
    public BigDecimal getValorCambioTasa() {
        return valorCambioTasa;
    }

    /**
     * asigna Tasa de cambio aplicada al movimiento.
     *
     * @param valorCambioTasa BigDecimal
     */
    public void setValorCambioTasa(BigDecimal valorCambioTasa) {
        this.valorCambioTasa = valorCambioTasa;
    }

    /**
     * monto del movimiento.
     *
     * @return monto del movimiento.
     */
    public BigDecimal getMonto() {
        return monto;
    }

    /**
     * monto del movimiento.
     *
     * @param monto monto del movimiento.
     */
    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }    

    /**
     * codigo de agencia donde se realizo el movimiento
     *
     * @return codigo de agencia donde se realizo el movimiento
     */
    public String getCodigoAgencia() {
        return codigoAgencia;
    }

    /**
     * codigo de agencia donde se realizo el movimiento
     *
     * @param codigoAgencia codigo de agencia donde se realizo el movimiento
     */
    public void setCodigoAgencia(String codigoAgencia) {
        this.codigoAgencia = codigoAgencia;
    }

    

    /**
     * id del canal donde se realizo el movimiento
     *
     * @return id del canal donde se realizo el movimiento
     */
    public String getCanal() {
        return canal;
    }

    /**
     * id del canal donde se realizo el movimiento
     *
     * @param canal id del canal donde se realizo el movimiento
     */
    public void setCanal(String canal) {
        this.canal = canal;
    }

    /**
     * codigo de Banco de Destino del movimiento
     *
     * @return codigo de Banco de Destino del movimiento
     */
    public String getCodigoBancoDestino() {
        return codigoBancoDestino;
    }

    /**
     * codigo de Banco de Destino del movimiento
     *
     * @param codigoBancoDestino codigo de Banco de Destino del movimiento
     */
    public void setCodigoBancoDestino(String codigoBancoDestino) {
        this.codigoBancoDestino = codigoBancoDestino;
    }

    /**
     * retorna la respuesta de la operacion que se realiza.
     * @return RespuestaDTO
     */
    public RespuestaDTO getRespuesta() {
        return respuesta;
    }

    /**
     * asigna objeto para almacenar la respuesta de la transaccion
     * @param respuesta objeto para almacenar la respuesta de la transaccion
     */
    public void setRespuesta(RespuestaDTO respuesta) {
        this.respuesta = respuesta;
    }
    
    /**
     * obtiene Define la fecha donde se activa el embargo.
     * @return fecha de bloqueo
     */
    public String getFechaBloqueo() {
        return fechaBloqueo;
    }

    /**
     * inserta Define la fecha donde se activa el embargo.
     * @param fechaBloqueo fecha de bloqueo
     */
    public void setFechaBloqueo(String fechaBloqueo) {
        this.fechaBloqueo = fechaBloqueo;
    }

    /**
     * obtiene Fecha en que se realizó el depósito.
     * @return fecha reserva
     */
    public String getFechaReserva() {
        return fechaReserva;
    }

    /**
     * inserta Fecha en que se realizó el depósito.
     * @param fechaReserva fecha reserva
     */
    public void setFechaReserva(String fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    public String getFechaTransaccionString() {
        return fechaTransaccionString;
    }

    public void setFechaTransaccionString(String fechaTransaccionString) {
        this.fechaTransaccionString = fechaTransaccionString;
    }

    public String getHoraString() {
        return horaString;
    }

    public void setHoraString(String horaString) {
        this.horaString = horaString;
    }
    
    

}
