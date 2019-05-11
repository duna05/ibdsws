/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.facade;

import com.bds.ws.model.IbPerfilesAdm;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author cesar.mujica
 */
@Named("ibPerfilesAdmFacade")
@Stateless(name = "wsIbPerfilesAdmFacade")
public class IbPerfilesAdmFacade extends AbstractFacade<IbPerfilesAdm> {

    @PersistenceContext(unitName = "ibdsPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IbPerfilesAdmFacade() {
        super(IbPerfilesAdm.class);
    }

}
