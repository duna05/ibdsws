/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.delsur.integradoresSMS.tedexis;

import com.delsur.integradoresSMS.IntegradorSMS;
import com.delsur.integradoresSMS.tedexis.tedexis.M4WSIntSR;
import java.rmi.RemoteException;

/**
 *
 * @author cesar.mujica
 */
public class Tedexis extends IntegradorSMS
{
private M4WSIntSR tedexis;

	/**
	 * 
	 * @param id
	 * @param numeroTelefonico
	 * @param mensaje
	 * @param numSolicitud
	 */
	public Tedexis(int id, String numeroTelefonico, String mensaje, int numSolicitud)
	{
		super(id, "Tedexis", numeroTelefonico, mensaje, numSolicitud);
}
	
	@Override
	public void conectarConServidor() throws RemoteException
	{
		try
		{
			tedexis = new M4WSIntSR();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new RemoteException(e.getMessage(), e.getCause());
		}
	}

	/**
	 * @return Integer
	 * @throws RemoteException
	 */
	@Override
	public Integer sendSMS() throws RemoteException
	{       
            if (numeroTelefonico != null && numeroTelefonico.length() > 2){
                numeroTelefonico = "58"+numeroTelefonico.substring(1);
            }            
		return tedexis.getM4WSIntSRHttpSoap11Endpoint().sendSMS("bancodelsur", "$24Bc@D3lsUr", numeroTelefonico, mensaje);
	}
}
