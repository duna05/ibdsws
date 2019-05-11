/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.facade;

import com.bds.ws.model.IbEventosNotificacion;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author juan.faneite
 */
@Named("ibEventosNotificacionFacade")
@Stateless(name = "wsIbEventosNotificacionFacade")
public class IbEventosNotificacionFacade extends AbstractFacade<IbEventosNotificacion> {
    @PersistenceContext(unitName = "ibdsPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IbEventosNotificacionFacade() {
        super(IbEventosNotificacion.class);
    }
    
}
