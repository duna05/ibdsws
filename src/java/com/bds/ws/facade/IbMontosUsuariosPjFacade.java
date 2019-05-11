/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.facade;

import com.bds.ws.model.IbMontosUsuariosPj;
import java.math.BigDecimal;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author luis.perez
 */
@Stateless
public class IbMontosUsuariosPjFacade extends AbstractFacade<IbMontosUsuariosPj> {
    @PersistenceContext(unitName = "ibdsPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IbMontosUsuariosPjFacade() {
        super(IbMontosUsuariosPj.class);
    }
    
    /**
     * Metodo que consulta los datos de un usuario y sus montos autorizado por
     * empresa Oracle 11++
     *
     *
     * @param idUsuario codigo interno del usuario en IB
     * @param idEmpresa codigo de la empresa en IB
     * @return the com.bds.ws.model.IbMontosUsuariosPj
     */
    public IbMontosUsuariosPj consultarDatosUsuarioMontosAutorizado(BigDecimal idUsuario, BigDecimal idEmpresa) {
        IbMontosUsuariosPj ibMontosUsuarioPj = null;
        try {
            StringBuilder consulta = new StringBuilder();
            consulta.append(" SELECT e FROM IbMontosUsuariosPj e");
            consulta.append("     WHERE e.idEmpresa.id    = :idEmpresa");
            consulta.append("     AND   e.idUsuarioPj.id  = :idUsuario");

            ibMontosUsuarioPj = (IbMontosUsuariosPj) em.createQuery(consulta.toString())
                    .setParameter("idEmpresa", idEmpresa)
                    .setParameter("idUsuario", idUsuario)
                    .getSingleResult();
        } catch (NoResultException e) {
            //SE HACE EL MANEJO DE LA EXEPCION DESDE EL METODO DONDE SE LLAMA 
            //VERIFICANDO SI LO QUE SE DEVULEVE ES NULL
        } catch (Exception e) {
            throw e;
        }
        return ibMontosUsuarioPj;
    }
}
