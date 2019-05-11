/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.facade;

import com.bds.ws.enumerated.EstatusAccesoEnum;
import com.bds.ws.enumerated.EstatusRegistroUsuarioEnum;
import com.bds.ws.enumerated.PerfilAccesoEnum;
import com.bds.ws.model.IbEmpresasUsuariosPj;
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
@Named("ibEmpresasUsuariosPjFacade")
@Stateless(name = "wsIbEmpresasUsuariosPjFacade")
public class IbEmpresasUsuariosPjFacade extends AbstractFacade<IbEmpresasUsuariosPj> {

    @PersistenceContext(unitName = "ibdsPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IbEmpresasUsuariosPjFacade() {
        super(IbEmpresasUsuariosPj.class);
    }

    public List<IbEmpresasUsuariosPj> consultarDatosEmpresaUsuario(BigDecimal idUsuario) {
        List<IbEmpresasUsuariosPj> empresas = null;
        try {
            StringBuilder consulta = new StringBuilder();
            consulta.append(" SELECT u FROM IbEmpresasUsuariosPj u       ");
            consulta.append(" WHERE u.idUsuarioPj.id = :idUsuario ");

            empresas = (List<IbEmpresasUsuariosPj>) em.createQuery(consulta.toString())
                    .setParameter("idUsuario", idUsuario)
                    .getResultList();
        } catch (Exception e) {
            throw e;
        }
        return empresas;
    }

    public List<IbEmpresasUsuariosPj> consultarEmpresasUsuarios(BigDecimal idEmpresa, Character estatusAcceso) {
        List<IbEmpresasUsuariosPj> ibEmpresasUsuariosPj = null;
        try {
            StringBuilder consulta = new StringBuilder();
            consulta.append(" SELECT e FROM IbEmpresasUsuariosPj e");
            consulta.append("     WHERE e.idEmpresa.id   = :idEmpresa");

            if (!estatusAcceso.toString().trim().equals("")) {
                consulta.append("     AND e.estatusAcceso  = :estatusAcceso  ");
                ibEmpresasUsuariosPj = (List<IbEmpresasUsuariosPj>) em.createQuery(consulta.toString())
                        .setParameter("idEmpresa", idEmpresa)
                        .setParameter("estatusAcceso", estatusAcceso)
                        .getResultList();
            } else {
                ibEmpresasUsuariosPj = (List<IbEmpresasUsuariosPj>) em.createQuery(consulta.toString())
                        .setParameter("idEmpresa", idEmpresa)
                        .getResultList();
            }
        } catch (Exception e) {
            throw e;
        }
        return ibEmpresasUsuariosPj;
    }

    public List<IbEmpresasUsuariosPj> consultarEmpresasUsuariosAprobadores(BigDecimal idEmpresa) {
        List<IbEmpresasUsuariosPj> ibEmpresasUsuariosPj = null;
        try {
            StringBuilder consulta = new StringBuilder();
            consulta.append(" SELECT e FROM IbEmpresasUsuariosPj e");
            consulta.append("     WHERE e.idEmpresa.id   = :idEmpresa")
                    .append("     AND e.estatusAcceso  = :estatusAcceso  ")
                    .append("     AND e.perfilAcceso.id = :perfilAcceso")
                    .append("     AND e.estatusRegistro = :estatusRegistro");

            Integer in = EstatusAccesoEnum.ACTIVO.getId();
            char[] charAcceso = in.toString().toCharArray();
            in = EstatusRegistroUsuarioEnum.ACTIVO.getId();
            char[] charRegistro = in.toString().toCharArray();

            ibEmpresasUsuariosPj = (List<IbEmpresasUsuariosPj>) em.createQuery(consulta.toString())
                    .setParameter("idEmpresa", idEmpresa)
                    .setParameter("estatusAcceso", new Character(charAcceso[0]))
                    .setParameter("perfilAcceso", new BigDecimal(PerfilAccesoEnum.AUTORIZADOR.getId()))
                    .setParameter("estatusRegistro", new Character(charRegistro[0]))
                    .getResultList();
        } catch (Exception e) {
            throw e;
        }
        return ibEmpresasUsuariosPj;
    }

    /**
     *
     * @param idUsuario
     * @param idEmpresa
     * @return IbEmpresasUsuariosPj
     * @throws NoResultException
     */
    public IbEmpresasUsuariosPj consultarEmpresaUsuario(BigDecimal idUsuario, BigDecimal idEmpresa) {
        IbEmpresasUsuariosPj ibEmpresaUsuarioPj = null;
        try {
            StringBuilder consulta = new StringBuilder();
            consulta.append(" SELECT e FROM IbEmpresasUsuariosPj e");
            consulta.append("     WHERE e.idEmpresa.id     = :idEmpresa");
            consulta.append("     AND   e.idUsuarioPj.id   = :idUsuario");

            ibEmpresaUsuarioPj = (IbEmpresasUsuariosPj) em.createQuery(consulta.toString())
                    .setParameter("idEmpresa", idEmpresa)
                    .setParameter("idUsuario", idUsuario)
                    .getSingleResult();
        } catch (NoResultException e) {
            //SE HACE EL MANEJO DE LA EXCEPCION DESDE EL METODO DONDE SE LLAMA 
            //VERIFICANDO SI LO QUE SE DEVULEVE ES NULL
        } catch (Exception e) {
            throw e;
        }
        return ibEmpresaUsuarioPj;
    }

    /**
     *
     * @param idUsuario
     * @return IbEmpresasUsuariosPj
     * @throws NoResultException
     */
    public List<IbEmpresasUsuariosPj> consultarEmpresaUsuario(BigDecimal idUsuario, char estatusAcceso) {
        List<IbEmpresasUsuariosPj> ibEmpresaUsuarioPjList = null;
        try {
            StringBuilder consulta = new StringBuilder();
            consulta.append(" SELECT e FROM IbEmpresasUsuariosPj e");
            consulta.append("     WHERE   e.idUsuarioPj.id   = :idUsuario");
            consulta.append("     AND     e.estatusAcceso    = :estatusAcceso");

            ibEmpresaUsuarioPjList = (List) em.createQuery(consulta.toString())
                    .setParameter("idUsuario", idUsuario)
                    .setParameter("estatusAcceso", estatusAcceso)
                    .getResultList();
        } catch (NoResultException e) {
            //SE HACE EL MANEJO DE LA EXCEPCION DESDE EL METODO DONDE SE LLAMA 
            //VERIFICANDO SI LO QUE SE DEVULEVE ES NULL
        } catch (Exception e) {
            throw e;
        }
        return ibEmpresaUsuarioPjList;
    }

    public List<IbEmpresasUsuariosPj> consultarEmpresasUsuariosPjPerfil(BigDecimal idEmpresa, String perfilAcceso) {
        List<IbEmpresasUsuariosPj> ibEmpresasUsuariosPj = null;
        try {
            StringBuilder consulta = new StringBuilder();
            consulta.append(" SELECT e FROM IbEmpresasUsuariosPj e");
            consulta.append("     WHERE e.perfilAcceso.id   = :perfilAcceso");
            consulta.append("     AND   e.idEmpresa.id      = :idEmpresa");
            consulta.append(" ORDER BY  e.idUsuarioPj.nombre ");

            ibEmpresasUsuariosPj = (List<IbEmpresasUsuariosPj>) em.createQuery(consulta.toString())
                    .setParameter("perfilAcceso", new BigDecimal(perfilAcceso))
                    .setParameter("idEmpresa", idEmpresa)
                    .getResultList();
        } catch (Exception e) {
            throw e;
        }
        return ibEmpresasUsuariosPj;
    }

    public List<IbEmpresasUsuariosPj> consultarEmpresasMontosUsuariosPjPerfil(BigDecimal idEmpresa, String perfilAcceso) {
        List<IbEmpresasUsuariosPj> ibEmpresasUsuariosPj = null;
        try {
            StringBuilder consulta = new StringBuilder();
            consulta.append(" SELECT e FROM IbEmpresasUsuariosPj e, IbMontosUsuariosPj mou ");
            consulta.append("     WHERE e.idUsuarioPj.id    = mou.idUsuarioPj.id");
            consulta.append("     AND   e.idEmpresa.id      = mou.idEmpresa.id");
            consulta.append("     AND   e.perfilAcceso.id   = :perfilAcceso");
            consulta.append("     AND   e.idEmpresa.id      = :idEmpresa");
            consulta.append(" ORDER BY  e.idUsuarioPj.nombre ");

            ibEmpresasUsuariosPj = (List<IbEmpresasUsuariosPj>) em.createQuery(consulta.toString())
                    .setParameter("perfilAcceso", new BigDecimal(perfilAcceso))
                    .setParameter("idEmpresa", idEmpresa)
                    .getResultList();
        } catch (Exception e) {
            throw e;
        }
        return ibEmpresasUsuariosPj;
    }

    public List<IbEmpresasUsuariosPj> consultarEmpresasUsuariosPjRepresentanteLegal(BigDecimal idEmpresa) {
        List<IbEmpresasUsuariosPj> ibEmpresasUsuariosPj = null;
        try {
            StringBuilder consulta = new StringBuilder();
            consulta.append(" SELECT e FROM IbEmpresasUsuariosPj e");
            consulta.append("     WHERE e.idEmpresa.id         = :idEmpresa");
            consulta.append("     AND   e.esRepresentanteLegal = :esRepresentanteLegal ");

            ibEmpresasUsuariosPj = (List<IbEmpresasUsuariosPj>) em.createQuery(consulta.toString())
                    .setParameter("idEmpresa", idEmpresa)
                    .setParameter("esRepresentanteLegal", '1')
                    .getResultList();
        } catch (Exception e) {
            throw e;
        }
        return ibEmpresasUsuariosPj;
    }

    /**
     *
     * @param idUsuario
     * @return IbEmpresasUsuariosPj
     * @throws NoResultException
     */
    public List<IbEmpresasUsuariosPj> consultarEmpresaxEmpresa(String idEmpresa) {
        List<IbEmpresasUsuariosPj> ibEmpresaUsuarioPjList = null;
        Character aux = new Character('3');

        String auxTipoRif = idEmpresa.substring(0, 1);
        Character tipoRif = new Character(auxTipoRif.charAt(0));
        String rif = idEmpresa;
        rif = rif.substring(1, rif.length());
        Long auxInt = Long.parseLong(rif);
        rif = String.valueOf(auxInt);
        try {
            StringBuilder consulta = new StringBuilder();
            consulta.append(" SELECT e FROM IbEmpresasUsuariosPj e");
            consulta.append("     WHERE   e.idEmpresa.tipoRif   = :tipoRif");
            consulta.append("     AND   e.idEmpresa.nroRif   = :rif");
            consulta.append("     AND   e.estatusRegistro = :aux");

            ibEmpresaUsuarioPjList = (List) em.createQuery(consulta.toString())
                    .setParameter("tipoRif", tipoRif)
                    .setParameter("rif", rif)
                    .setParameter("aux", aux)
                    .getResultList();
        } catch (NoResultException e) {
            //SE HACE EL MANEJO DE LA EXCEPCION DESDE EL METODO DONDE SE LLAMA 
            //VERIFICANDO SI LO QUE SE DEVULEVE ES NULL
        } catch (Exception e) {
            throw e;
        }
        return ibEmpresaUsuarioPjList;
    }
}
