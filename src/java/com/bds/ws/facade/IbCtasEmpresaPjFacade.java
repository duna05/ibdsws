/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.facade;

import com.bds.ws.dto.CuentaDTO;
import com.bds.ws.model.IbCtasEmpresaPj;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author luis.perez
 */
@Stateless
public class IbCtasEmpresaPjFacade extends AbstractFacade<IbCtasEmpresaPj> {
    @PersistenceContext(unitName = "ibdsPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IbCtasEmpresaPjFacade() {
        super(IbCtasEmpresaPj.class);
    }
    
    public IbCtasEmpresaPj consultarCuentasPorNumero(String nroCuenta){
        IbCtasEmpresaPj ibCtasEmpresaPj = null;
        try {
            TypedQuery<IbCtasEmpresaPj> consultaCuentas = em.createNamedQuery("IbCtasEmpresaPj.findByNroCuenta", IbCtasEmpresaPj.class);
            consultaCuentas.setParameter("nroCuenta", nroCuenta);
            ibCtasEmpresaPj = consultaCuentas.getSingleResult();
        } catch (NoResultException e) {
            //SE HACE EL MANEJO DE LA EXCEPCION DESDE EL METODO DONDE SE LLAMA 
            //VERIFICANDO SI LO QUE SE DEVULEVE ES NULL
        } catch (Exception e) {
            throw e;
        }
        return ibCtasEmpresaPj;
    }
    
    public List<CuentaDTO> consultarCuentasPorEmpresaUsuario(BigDecimal idUsuario, BigDecimal idEmpresa) {
        List<CuentaDTO> cuentaDTOlist = new ArrayList();
        CuentaDTO cuentaDTO = new CuentaDTO();
        List<IbCtasEmpresaPj> ibCtasEmpresaPjList;
        try {
            StringBuilder consulta = new StringBuilder();
            consulta.append(" SELECT e FROM IbCtasEmpresaPj e,       ");
            consulta.append("               IbCtasEmpresaUsuarioPj u ");
            consulta.append("     WHERE e.id             = u.idCtaEmpresa.id ");
            consulta.append("     AND   u.idUsuarioPj.id = :idUsuario");
            consulta.append("     AND   u.idEmpresaPj.id = :idEmpresa");

            ibCtasEmpresaPjList = (List<IbCtasEmpresaPj>) em.createQuery(consulta.toString())
                    .setParameter("idUsuario", idUsuario)
                    .setParameter("idEmpresa", idEmpresa)
                    .getResultList();
            
            for (IbCtasEmpresaPj ibCtasEmpresaPj : ibCtasEmpresaPjList) {
                cuentaDTO = new CuentaDTO();
                cuentaDTO.setNumeroCuenta(ibCtasEmpresaPj.getNroCuenta());
                cuentaDTOlist.add(cuentaDTO);
            }
        } catch (Exception e) {
            throw e;
        }
        return cuentaDTOlist;
    }
}
