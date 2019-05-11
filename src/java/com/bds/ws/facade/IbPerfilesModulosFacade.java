/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.facade;

import com.bds.ws.model.IbPerfilesModulos;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author cesar.mujica
 */
@Named("ibPerfilesModulosFacade")
@Stateless(name = "wsIbPerfilesModulosFacade")
public class IbPerfilesModulosFacade extends AbstractFacade<IbPerfilesModulos> {

    @PersistenceContext(unitName = "ibdsPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IbPerfilesModulosFacade() {
        super(IbPerfilesModulos.class);
    }

}
