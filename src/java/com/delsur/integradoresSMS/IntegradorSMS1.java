/*
 * ultimas modificacione:
 * 01 de octubre de 2014: Occibiz Tecnologia - Nestor Marcano
 */
package com.delsur.integradoresSMS;

import java.rmi.RemoteException;

/**
 * Integrador Base. Todos los integradores deben heredar de IntegradorSMS1.

 Esta es la clase devuelta por el metodo obtenerIntegrador de la
 FabricaDeIntegradores.
 */
public abstract class IntegradorSMS1 {

    private int id;
    private String nombre;
    protected int numSolicitud;
    protected String numeroTelefonico;
    protected String mensaje;

    /**
     * Contructor de un IntegradorSMS.
     *
     * @param id
     * @param nombre
     * @param numeroTelefonico
     * @param mensaje
     * @param numSolicitud
     */
    public IntegradorSMS1(int id, String nombre, String numeroTelefonico, String mensaje, int numSolicitud) {
        this.id = id;
        this.nombre = nombre;
        this.numeroTelefonico = numeroTelefonico;
        this.mensaje = mensaje;
        this.numSolicitud = numSolicitud;
    }

    /**
     * Inicializa el IntegradorSMS1.
     *
     * @throws java.rmi.RemoteException
     */
    public abstract void conectarConServidor() throws RemoteException;

    /**
     * Envia el mensaje SMS al numero indicado.
     *
     * @return Entero con el estatus del envio. 0: Envio satisfactorio.
     * Diferente de cero, errores varios.
     * @throws RemoteException
     */
    public abstract Integer sendSMS() throws RemoteException;

    public String getNombre() {
        return nombre;
    }

    /**
     * Devuelve el numero de la solicitud.
     *
     * @return int
     */
    public int getNumSolicitud() {
        return numSolicitud;
    }

	public void setNumSolicitud(int numSolicitud)
	{
        this.numSolicitud = numSolicitud;
    }

	public void setNumeroTelefonico(String numeroTelefonico)
	{
        this.numeroTelefonico = numeroTelefonico;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public int getId() {
        return id;
    }

    public String getNumeroTelefonico() {
        return numeroTelefonico;
    }
}
