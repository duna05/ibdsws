/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.facade;

import com.bds.ws.model.IbMediosNotificacion;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author juan.faneite
 */
@Named("ibMediosNotificacionFacade")
@Stateless(name = "wsIbMediosNotificacionFacade")
public class IbMediosNotificacionFacade extends AbstractFacade<IbMediosNotificacion> {
    @PersistenceContext(unitName = "ibdsPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IbMediosNotificacionFacade() {
        super(IbMediosNotificacion.class);
    }
    
}
