/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.facade;

import com.bds.ws.model.IbFideicomisoDetPj;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author luis.perez
 */
@Named("ibFideicomisoDetPjFacade")
@Stateless(name = "wsIbFideicomisoDetPjFacade")
public class IbFideicomisoDetPjFacade extends AbstractFacade<IbFideicomisoDetPj> {

    @PersistenceContext(unitName = "ibdsPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IbFideicomisoDetPjFacade() {
        super(IbFideicomisoDetPj.class);
    }
    
    public List<IbFideicomisoDetPj> listarFideicomisoDetalle(BigDecimal idfideicomiso) {
        List<IbFideicomisoDetPj> cabeceraFideicomisoDetalle = null;
        try {
            StringBuilder consulta = new StringBuilder();

            consulta.append(" SELECT e FROM IbFideicomisoDetPj e");
            consulta.append(" WHERE e.idFideMovimientoPj.id = :idFideicomiso ORDER BY e.fechaHoraCarga DESC");

            cabeceraFideicomisoDetalle = (List<IbFideicomisoDetPj>) em.createQuery(consulta.toString())
                    .setParameter("idFideicomiso", idfideicomiso)
                    .getResultList();
        } catch (Exception e) {
            throw e;
        }
        return cabeceraFideicomisoDetalle;
    }
    
    public int modificarEstatusDetalle(Long idFideicomiso, BigDecimal estatus) {
        int cantActualizados = 0;
        StringBuilder queryUpdate = new StringBuilder();
        queryUpdate.append("UPDATE IB_FIDEICOMISO_DET_PJ ")
                .append(" SET   ESTATUS               = ? ")
                .append(" WHERE ID_FIDE_MOVIMIENTO_PJ = ? ");
        try {
            cantActualizados = getEntityManager().createNativeQuery(queryUpdate.toString())
                    .setParameter(1, estatus)
                    .setParameter(2, idFideicomiso)
                    .executeUpdate();
        } catch (Exception e) {
            throw e;
        }
        return cantActualizados;
    }
}
