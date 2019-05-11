/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delsur.integradoresSMS.ogangi;

import com.delsur.integradoresSMS.IntegradorSMS;
import com.delsur.integradoresSMS.ogangi.ogangi.SendMT;
import com.delsur.integradoresSMS.ogangi.ogangi.SendMTService;
import java.rmi.RemoteException;

/**
 *
 * @author cesar.mujica
 */
public class Ogangi extends IntegradorSMS {

    private SendMTService ogangi;

    /**
     *
     * @param id
     * @param numeroTelefonico
     * @param mensaje
     * @param numSolicitud
     */
    public Ogangi(int id, String numeroTelefonico, String mensaje, int numSolicitud) {
        super(id, "Ogangi", numeroTelefonico, mensaje, numSolicitud);
    }

    /**
     * @throws java.rmi.RemoteException
     */
    @Override
    public void conectarConServidor() throws RemoteException {
        try {
            ogangi = new SendMTService();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RemoteException(e.getMessage(), e.getCause());
        }
    }

    /**
     * @return Integer
     * @throws RemoteException
     */
    @Override
    public Integer sendSMS() throws RemoteException {
        SendMT port = ogangi.getSendMTPort();
        int ret = port.send("1", numeroTelefonico, mensaje);
        if (ret == 1) {
            ret = 0;
        } else {
            ret = 1;
        }
        return ret;
    }
}
