/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.facade;

import com.bds.ws.dto.IbCtasXBeneficiariosPjDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.enumerated.EstatusCuentaEnum;
import com.bds.ws.model.IbBeneficiariosPj;
import com.bds.ws.model.IbCtasXBeneficiariosPj;
import com.bds.ws.model.IbEmpresas;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;

/**
 *
 * @author christian.gutierrez
 */
@Named("ibCtasXBeneficiariosPjFacade")
@Stateless(name = "wsIbCtasXBeneficiariosPjFacade")
public class IbCtasXBeneficiariosPjFacade extends AbstractFacade<IbCtasXBeneficiariosPj> {

    @PersistenceContext(unitName = "ibdsPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IbCtasXBeneficiariosPjFacade() {
        super(IbCtasXBeneficiariosPj.class);
    }

    public List<IbCtasXBeneficiariosPj> listarIbCtasXBeneficiariosPj(Long idBeneficiario, Short estatusCuenta) {
        List<IbCtasXBeneficiariosPj> ibCtasXBeneficiariosPjDTO = null;
        try {
            StringBuilder consulta = new StringBuilder();
            consulta.append(" SELECT cb FROM IbCtasXBeneficiariosPj cb ");
            consulta.append(" WHERE cb.idBeneficiario =:id");
            consulta.append(" AND cb.estatusCuenta =:estatus");

            ibCtasXBeneficiariosPjDTO = (List<IbCtasXBeneficiariosPj>) em.createQuery(consulta.toString())
                    .setParameter("id", idBeneficiario)
                    .setParameter("estatus", estatusCuenta)
                    .getResultList();
        } catch (Exception e) {
            throw e;
        }
        return ibCtasXBeneficiariosPjDTO;
    }

    public int modificarEstatusCuentas(Long idCargaBeneficiario, String estatus) {

        int cantActualizados = 0;

        StringBuilder queryUpdate = new StringBuilder();
        queryUpdate.append("UPDATE IB_CTAS_X_BENEFICIARIOS_PJ CB ")
                .append(" SET CB.ESTATUS_AUTORIZACION = ? ")
                .append(" WHERE CB.ID_BENEFICIARIO IN(SELECT B.ID_BENEFICIARIO ")
                .append(" FROM IB_BENEFICIARIOS_PJ B")
                .append(" WHERE B.ID_CARGA_BENEFICIARIO = ?) ")
                .append(" AND CB.ESTATUS_AUTORIZACION = 0");
        try {
            cantActualizados = getEntityManager().createNativeQuery(queryUpdate.toString())
                    .setParameter(1, estatus)
                    .setParameter(2, idCargaBeneficiario)
                    .executeUpdate();
        } catch (Exception e) {
            throw e;
        }

        return cantActualizados;
    }

    public IbCtasXBeneficiariosPj existeCuenta(IbEmpresas idEmpresa, String nroCuentaBeneficiario, String nroBeneficiario) {
        IbCtasXBeneficiariosPj ibCtasXBeneficiariosPj = null;
        try {
            StringBuilder consulta = new StringBuilder();
            consulta.append(" SELECT cb FROM IbCtasXBeneficiariosPj cb ");
            consulta.append(" WHERE  cb.idBeneficiario.nroIdentificacionCliente = :rif");
            consulta.append(" AND    cb.nroCuentaBeneficiario                   = :nroCuenta");
            consulta.append(" AND    cb.idBeneficiario.idEmpresa             = :idEmpresa");

            ibCtasXBeneficiariosPj = (IbCtasXBeneficiariosPj) em.createQuery(consulta.toString())
                    .setParameter("nroCuenta", nroCuentaBeneficiario)
                    .setParameter("rif", nroBeneficiario)
                    .setParameter("idEmpresa", idEmpresa)
                    .getSingleResult();
        } catch (NoResultException e) {
            //SE HACE EL MANEJO DE LA EXCEPCION DESDE EL METODO DONDE SE LLAMA 
            //VERIFICANDO SI LO QUE SE DEVULEVE ES NULL
        } catch (ConstraintViolationException e) {
            /*logger.log(Level.SEVERE, "Exception: ");
            e.getConstraintViolations().forEach(err -> logger.log(Level.SEVERE, err.toString()));*/
        } catch (Exception e) {
            throw e;
        }

        return ibCtasXBeneficiariosPj;
    }

    public IbCtasXBeneficiariosPj existeCuenta(IbEmpresas idEmpresa, String nroCuentaBeneficiario, String nroBeneficiario, String referencia) {
        IbCtasXBeneficiariosPj ibCtasXBeneficiariosPj = null;
        try {
            StringBuilder consulta = new StringBuilder();
            consulta.append(" SELECT cb FROM IbCtasXBeneficiariosPj cb ");
            consulta.append(" WHERE  cb.idBeneficiario.nroIdentificacionCliente = :rif");
            consulta.append(" AND    cb.nroCuentaBeneficiario                   = :nroCuenta");
            consulta.append(" AND    cb.idBeneficiario.referenciaBeneficiario   = :referencia");
            consulta.append(" AND    cb.idBeneficiario.idEmpresa                = :idEmpresa");

            ibCtasXBeneficiariosPj = (IbCtasXBeneficiariosPj) em.createQuery(consulta.toString())
                    .setParameter("nroCuenta", nroCuentaBeneficiario)
                    .setParameter("rif", nroBeneficiario)
                    .setParameter("referencia", referencia)
                    .setParameter("idEmpresa", idEmpresa)
                    .getSingleResult();
        } catch (NoResultException e) {
            //SE HACE EL MANEJO DE LA EXCEPCION DESDE EL METODO DONDE SE LLAMA 
            //VERIFICANDO SI LO QUE SE DEVULEVE ES NULL
        } catch (ConstraintViolationException e) {
            /*logger.log(Level.SEVERE, "Exception: ");
            e.getConstraintViolations().forEach(err -> logger.log(Level.SEVERE, err.toString()));*/
        } catch (Exception e) {
            throw e;
        }

        return ibCtasXBeneficiariosPj;
    }

    public boolean esCuentaRegistrada(String nroBeneficiario, IbEmpresas empresa) {

        try {
            StringBuilder consulta = new StringBuilder();
            consulta.append(" SELECT cb FROM IbCtasXBeneficiariosPj cb ");
            consulta.append(" WHERE  cb.idBeneficiario.nroIdentificacionCliente = :rif");
            consulta.append(" AND    cb.idBeneficiario.idEmpresa  = :empresa");

            int count = (int) em.createQuery(consulta.toString())
                    .setParameter("rif", nroBeneficiario)
                    .setParameter("empresa", empresa)
                    .getResultList().size();

            return (count > 0);

        } catch (Exception ex) {
            //respuestaDTO.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log            
            throw ex;
        }

    }

    public List<IbCtasXBeneficiariosPj> IbBeneficiariosPorCuentaEmpresa(BigDecimal idEmpresa, BigDecimal idCtaDelSur, BigDecimal idCtaPro, Short estatusCta) {
        List<IbCtasXBeneficiariosPj> ibCtasXBeneficiariosPjList = new ArrayList<>();

        try {
            StringBuilder consulta = new StringBuilder();
            consulta.append(" SELECT b FROM IbCtasXBeneficiariosPj b ");
            consulta.append(" WHERE b.idEmpresa.id =:id");
            consulta.append(" AND b.cuentaDelSur =:idCtaDelSur");
            consulta.append(" AND b.cuentaPropia =:idCtaPro");

            if (estatusCta == null) {
                estatusCta = 0;

                if (estatusCta == null) {
                    estatusCta = 0;
                }

                if (estatusCta == null) {
                    estatusCta = 0;

                }
                if (estatusCta == null) {
                    estatusCta = 0;

                    consulta.append(" AND b.estatusCuenta >:estatusCta");
                } else {
                    consulta.append(" AND b.estatusCuenta =:estatusCta");
                }

                ibCtasXBeneficiariosPjList = (List<IbCtasXBeneficiariosPj>) em.createQuery(consulta.toString())
                        .setParameter("id", idEmpresa)
                        .setParameter("estatusCta", estatusCta)
                        .setParameter("idCtaPro", idCtaPro)
                        .setParameter("idCtaDelSur", idCtaDelSur)
                        .getResultList();
            }
        } catch (Exception e) {
            throw e;
        }
        return ibCtasXBeneficiariosPjList;

    }

    public IbCtasXBeneficiariosPj buscarIbCtasXBeneficiariosPj(Long idBeneficiario) {
        IbCtasXBeneficiariosPj ibCtasXBeneficiariosPj = new IbCtasXBeneficiariosPj();
        try {
            ibCtasXBeneficiariosPj = (IbCtasXBeneficiariosPj) em.find(IbCtasXBeneficiariosPj.class, idBeneficiario);
        } catch (NoResultException e) {
            // Se debe validar en la clase que llama el metodo
        } catch (Exception e) {
            //TODO: definir respuesta incorrecta            
            throw e;
        }
        return ibCtasXBeneficiariosPj;
    }

    public List<IbCtasXBeneficiariosPj> IbCuentaEmpresaPorBeneficiarios(IbBeneficiariosPj beneficiario, BigDecimal idCtaDelSur, BigDecimal idCtaPro, Short estatusCta) {
        List<IbCtasXBeneficiariosPj> ibCtasXBeneficiariosPjList = new ArrayList<>();
        StringBuilder consulta = new StringBuilder();
        consulta.append(" SELECT b FROM IbCtasXBeneficiariosPj b ");
        if (estatusCta == null) {
            estatusCta = 0;
            consulta.append(" WHERE b.estatusCuenta >:estatusCta");
        } else {
            consulta.append(" WHERE b.estatusCuenta =:estatusCta");
        }
        if (beneficiario != null) {
            {
                consulta.append(" AND b.estatusCuenta = " + beneficiario);
            }
            consulta.append(" AND b.cuentaDelSur =:idCtaDelSur");
            consulta.append(" AND b.cuentaPropia =:idCtaPro");

            ibCtasXBeneficiariosPjList = (List<IbCtasXBeneficiariosPj>) em.createQuery(consulta.toString())
                    .setParameter("estatusCta", estatusCta)
                    .setParameter("idCtaPro", idCtaPro)
                    .setParameter("idCtaDelSur", idCtaDelSur)
                    .getResultList();
        }
        return ibCtasXBeneficiariosPjList;
    }

    /**
     * Método que lista a los beneficiarios que contiene una empresa dada
     *
     * @param idEmpresa
     * @param idCtaDelSur
     * @param idCtaPro
     * @param estatusCta
     * @return
     */
    public List<IbCtasXBeneficiariosPj> listarCtasBeneficiarioXEmpresa(BigDecimal idEmpresa, Long idBeneficiario) {
        List<IbCtasXBeneficiariosPj> ibCtasXBeneficiariosPjList = new ArrayList<>();
        try {
            ibCtasXBeneficiariosPjList = (List<IbCtasXBeneficiariosPj>) em.createNamedQuery("IbCtasXBeneficiariosPj.findByBeneficiarioXEmpresa")
                    .setParameter("idEmpresa", idEmpresa)
                    .setParameter("idBeneficiario", idBeneficiario).getResultList();
        } catch (Exception e) {
            throw e;
        }
        return ibCtasXBeneficiariosPjList;
    }

    public IbCtasXBeneficiariosPjDTO listarCtasBeneficiarioXEmpresaAutoriza(BigDecimal idEmpresa, Long idUsuarioAurorizado, Long idServicio, Long idBeneficiario) throws Exception {

        IbCtasXBeneficiariosPjDTO ibCtasXBeneficiariosPjDTO = new IbCtasXBeneficiariosPjDTO();
        ibCtasXBeneficiariosPjDTO.setIbCtasXBeneficiariosPjsList(new ArrayList<IbCtasXBeneficiariosPj>());
        ibCtasXBeneficiariosPjDTO.setRespuestaDTO(new RespuestaDTO());

        List<IbCtasXBeneficiariosPj> ibCtasXBeneficiariosPjList = null;

        try {
            StringBuilder consulta = new StringBuilder();
            consulta.append(" SELECT distinct b FROM IbCtasXBeneficiariosPj  b");
            consulta.append(" WHERE  b.estatusCuenta =:estatusCuenta");
            consulta.append(" AND b.idEmpresa.id =:idEmpresa");
            consulta.append(" AND b.idBeneficiario.idBeneficiario =:idBeneficiario");
            consulta.append(" AND NOT EXISTS (SELECT p FROM IbSecuenciaAprobacionPj p");
            consulta.append("  WHERE p.idTransaccion = b.idCuenta ");
            consulta.append("        AND p.idServicioPj.id =:idServicioPj");
            consulta.append("        AND p.idUsuarioPj.id =:idUsuarioAurorizado)");

            ibCtasXBeneficiariosPjList = (List<IbCtasXBeneficiariosPj>) em.createQuery(consulta.toString())
                    .setParameter("idEmpresa", idEmpresa)
                    .setParameter("idUsuarioAurorizado", new BigDecimal(idUsuarioAurorizado))
                    .setParameter("idServicioPj", new BigDecimal(idServicio))
                    .setParameter("estatusCuenta", Integer.parseInt(EstatusCuentaEnum.INACTIVO.getId()))
                    .setParameter("idBeneficiario", idBeneficiario)
                    .getResultList();
        } catch (Exception e) {
            throw e;
        }
        ibCtasXBeneficiariosPjDTO.setIbCtasXBeneficiariosPjsList(ibCtasXBeneficiariosPjList);

        return ibCtasXBeneficiariosPjDTO;
    }

}
