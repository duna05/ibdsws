/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.facade;

import com.bds.ws.model.IbModulosAdm;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author cesar.mujica
 */
@Named("ibModulosAdmFacade")
@Stateless(name = "wsIbModulosAdmFacade")
public class IbModulosAdmFacade extends AbstractFacade<IbModulosAdm> {

    @PersistenceContext(unitName = "ibdsPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IbModulosAdmFacade() {
        super(IbModulosAdm.class);
    }

}
