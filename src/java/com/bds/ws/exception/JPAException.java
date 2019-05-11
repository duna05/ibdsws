/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.exception;

/**
 * Clase JPAException
 * @author renseld.lugo
 */
public class JPAException extends Exception {

    public JPAException() {
    }

    public JPAException(String message) {
        super(message);
    }

    public JPAException(String message, Throwable cause) {
        super(message, cause);
    }

    public JPAException(Throwable cause) {
        super(cause);
    }

    public JPAException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
