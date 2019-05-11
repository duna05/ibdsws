/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.facade;

import com.bds.ws.model.IbServiciosPj;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author luis.perez
 */
@Stateless
public class IbServiciosPjFacade extends AbstractFacade<IbServiciosPj> {
    @PersistenceContext(unitName = "ibdsPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IbServiciosPjFacade() {
        super(IbServiciosPj.class);
    }
    
    public List<IbServiciosPj> findByNombre(String nombre) {
        List<IbServiciosPj> servicios = null;
        StringBuilder consulta = new StringBuilder();
        consulta.append(" SELECT s FROM IbServiciosPj s");
        consulta.append("     WHERE s.nombre  = :nombre");

        servicios = (List<IbServiciosPj>) em.createQuery(consulta.toString())
                .setParameter("nombre", nombre)
                .getResultList();

        return servicios;
    }
    public IbServiciosPj findById(BigDecimal idServicio) {
        List<IbServiciosPj> servicios = null;
        StringBuilder consulta = new StringBuilder();
        consulta.append(" SELECT s FROM IbServiciosPj s");
        consulta.append("     WHERE s.id  = :idServicio");

        servicios = (List<IbServiciosPj>) em.createQuery(consulta.toString())
                .setParameter("idServicio", idServicio)
                .getResultList();

        return servicios.get(0);
    }
    
     public IbServiciosPj findById2(BigDecimal idServicio) {
        IbServiciosPj servicios = null;
        try {
            servicios = (IbServiciosPj) getEntityManager().find(IbServiciosPj.class, idServicio);
        } catch (NoResultException e) {
            // Se debe validar en la clase que llama el metodo
        } catch (Exception e) {
            //TODO: definir respuesta incorrecta            
            throw e;
        }
        return servicios;
    }
}
