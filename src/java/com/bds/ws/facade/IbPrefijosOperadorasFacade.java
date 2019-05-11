/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.facade;


import com.bds.ws.model.IbPrefijosOperadoras;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.log4j.Logger;

/**
 *
 * @author cesar.mujica
 */
@Named("ibPrefijosOperadorasFacade")
@Stateless(name = "wsIbPrefijosOperadorasFacade")
public class IbPrefijosOperadorasFacade extends AbstractFacade<IbPrefijosOperadoras> {

    /**
     * Log de sistema
     */
    private static final Logger logger = Logger.getLogger(IbPrefijosOperadorasFacade.class.getName());

    @PersistenceContext(unitName = "ibdsPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IbPrefijosOperadorasFacade() {
        super(IbPrefijosOperadoras.class);
    }
   
}
