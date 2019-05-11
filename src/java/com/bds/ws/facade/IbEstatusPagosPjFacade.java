/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.facade;

import com.bds.ws.model.IbEstatusPagosPj;
import java.math.BigDecimal;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author juan.vasquez
 */
@Named("ibEstatusPagosPjFacade")
@Stateless(name = "wsIbEstatusPagosPjFacade")
public class IbEstatusPagosPjFacade extends AbstractFacade<IbEstatusPagosPj> {
    @PersistenceContext(unitName = "ibdsPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public IbEstatusPagosPjFacade(){
        super(IbEstatusPagosPj.class);
    }
    
    public IbEstatusPagosPj buscarIbEstatusPj(BigDecimal idEstatus) {
        IbEstatusPagosPj ibEstatusPagosPj = null;

        try {
            ibEstatusPagosPj = (IbEstatusPagosPj) getEntityManager().find(IbEstatusPagosPj.class, idEstatus);
        } catch (NoResultException e) {
            // Se debe validar en la clase que llama el metodo
        } catch (Exception e) {
            //TODO: definir respuesta incorrecta            
            throw e;
        }
        return ibEstatusPagosPj;
    }
    
}
