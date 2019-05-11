/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.facade;

import com.bds.ws.model.IbEmpresas;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author luis.perez
 */
@Named("ibEmpresasFacade")
@Stateless(name = "wsIbEmpresasFacade")
public class IbEmpresasFacade extends AbstractFacade<IbEmpresas> {    
    @PersistenceContext(unitName = "ibdsPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IbEmpresasFacade() {
        super(IbEmpresas.class);
    }
    
    public IbEmpresas findByRif(Character tipoRif, String rif) {
        IbEmpresas empresa = null;
        try {    
            StringBuilder consulta = new StringBuilder();
            consulta.append(" SELECT e FROM IbEmpresas e");
            consulta.append("     WHERE e.nroRif  = :nroRif");
            consulta.append("     AND   e.tipoRif = :tipoRif");
            
            empresa = (IbEmpresas) em.createQuery(consulta.toString())
                    .setParameter("nroRif", rif)
                    .setParameter("tipoRif", tipoRif)
                    .getSingleResult();
        } catch (NoResultException e) {
            //SE HACE EL MANEJO DE LA EXCEPCION DESDE EL METODO DONDE SE LLAMA 
            //VERIFICANDO SI LO QUE SE DEVULEVE ES NULL
        }catch (Exception e) {
            throw e;
        }
        return empresa;
    }

    public List<IbEmpresas> consultarDatosEmpresaUsuario(BigDecimal idUsuario, Character estatusEmpresa) {
        List<IbEmpresas> empresas = null;
        try {    
            StringBuilder consulta = new StringBuilder();
            consulta.append(" SELECT e FROM IbEmpresas e,       ");
            consulta.append("               IbEmpresasUsuariosPj u");
            consulta.append("     WHERE e.id           = u.idEmpresa.id ");
            consulta.append("     AND   u.idUsuarioPj.id = :idUsuario");
            consulta.append("     AND   e.estatus        = :estatusEmpresa");
            
            empresas = (List<IbEmpresas>) em.createQuery(consulta.toString())
                    .setParameter("idUsuario", idUsuario)
                    .setParameter("estatusEmpresa", estatusEmpresa)
                    .getResultList();
        } catch (Exception e) {
            throw e;
        }
        return empresas;
    }
    
    public List<IbEmpresas> consultarDatosEmpresaUsuario(BigDecimal idUsuario, Character estatusRegistro, Character estatusEmpresa) {
        List<IbEmpresas> empresas = null;
        try {
            StringBuilder consulta = new StringBuilder();
            consulta.append(" SELECT e FROM IbEmpresas e,       ");
            consulta.append("               IbEmpresasUsuariosPj u");
            consulta.append("     WHERE e.id           = u.idEmpresa.id ");
            consulta.append("     AND   u.idUsuarioPj.id = :idUsuario");
            consulta.append("     AND   e.estatus         = :estatusEmpresa");
            consulta.append("     AND   u.estatusRegistro = :estatusRegistro");

            empresas = (List<IbEmpresas>) em.createQuery(consulta.toString())
                    .setParameter("idUsuario", idUsuario)
                    .setParameter("estatusEmpresa", estatusEmpresa)
                    .setParameter("estatusRegistro", estatusRegistro)
                    .getResultList();
        } catch (Exception e) {
            throw e;
        }
        return empresas;
    }
}
