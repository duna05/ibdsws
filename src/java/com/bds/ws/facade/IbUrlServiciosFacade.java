/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.facade;

import com.bds.ws.model.IbUrl;
import com.bds.ws.model.IbUrlServicios;
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
@Named("ibUrlFacade")
@Stateless(name = "wsIbUrlFacade")
public class IbUrlServiciosFacade extends AbstractFacade<IbUrl> {

    @PersistenceContext(unitName = "ibdsPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IbUrlServiciosFacade() {
        super(IbUrl.class);
    }
    
    public List<IbUrlServicios> consultarUrlServicio(BigDecimal idEmpresa, BigDecimal idUsuario ) {
        List<IbUrlServicios> url = null;
        try {
            StringBuilder consulta = new StringBuilder();
            consulta.append(" SELECT url FROM IbUrlServicios url,       ");
            consulta.append("               IbServiEmpreUsuariosPj sp   ");
            consulta.append(" WHERE  url.idServicioPj.id       = sp.idServiciosPj.id ");
            consulta.append(" AND    sp.idUsuarioPj.id         = :idUsuario ");
            consulta.append(" AND    sp.idEmpresas.id          = :idEmpresa ");
            
            url = (List<IbUrlServicios>) em.createQuery(consulta.toString())
                    .setParameter("idEmpresa", idEmpresa)
                    .setParameter("idUsuario",  idUsuario)
                    .getResultList();
        } catch (Exception e) {
            throw e;
        }
        return url;
    }
}
