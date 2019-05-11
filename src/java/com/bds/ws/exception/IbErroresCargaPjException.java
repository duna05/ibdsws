/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.exception;

/**
 *
 * @author roger.muñoz
 */
public class IbErroresCargaPjException extends Exception {
    
    public IbErroresCargaPjException(String message){
        super(message);
    }

    public IbErroresCargaPjException() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
