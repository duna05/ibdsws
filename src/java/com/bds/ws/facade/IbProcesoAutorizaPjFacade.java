/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.facade;

import com.bds.ws.model.IbBeneficiariosPj;
import com.bds.ws.model.IbCargaNominaDetallePj;
import com.bds.ws.model.IbCargaPagosCorpDetPj;
import com.bds.ws.model.IbCargaPagosEspDetPj;
import com.bds.ws.model.IbEstatusPagosPj;
import com.bds.ws.model.IbFideicomisoDetPj;
import java.math.BigDecimal;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import org.apache.log4j.Logger;

/**
 *
 * @author juan.vasquez
 */
@Named("ibProcesoAutorizaPjFacade")
@Stateless(name = "ibProcesoAutorizaPjFacade")
public class IbProcesoAutorizaPjFacade extends AbstractFacade<IbCargaNominaDetallePj> {

    private static final Logger logger = Logger.getLogger(IbProcesoAutorizaPjFacade.class.getName());

    @EJB
    private IbSecuenciaAprobacionPjFacade ibSecuenciaAprobacionPjFacade;
    @EJB
    private IbServiciosPjFacade ibServiciosPjFacade;
    @EJB
    private IbUsuariosPjFacade ibUsuariosPjFacade;
    @EJB
    private IbEstatusPagosPjFacade ibEstatusPagosPjFacade;
    @EJB
    private IbCargaPagosCorpDetPjFacade ibCargaPagosCorpDetPjFacade;
    @EJB
    private IbBeneficiariosPjFacade IbBeneficiariosPjFacade;
    @EJB
    private IbCargaPagosEspDetPjFacade ibCargaPagosEspDetPjFacade;
    @EJB
    private IbFideicomisoPjFacade ibFideicomisoPjFacade;

    @PersistenceContext(unitName = "ibdsPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IbProcesoAutorizaPjFacade() {
        super(IbCargaNominaDetallePj.class);
    }

    public IbCargaNominaDetallePj buscarIbEstatusPagosPj(BigDecimal idDetalle) {
        IbCargaNominaDetallePj estatus = null;

        try {
            estatus = (IbCargaNominaDetallePj) getEntityManager().find(IbCargaNominaDetallePj.class, idDetalle);
        } catch (NoResultException e) {
            // Se debe validar en la clase que llama el metodo
        } catch (Exception e) {
            //TODO: definir respuesta incorrecta            
            throw e;
        }
        return estatus;
    }

    public IbCargaNominaDetallePj actualizarEstatusDetalleNominaPorId(BigDecimal idTransaction, BigDecimal idEstatus) throws Exception {
        IbCargaNominaDetallePj ibCargaNominaDetallePj = null;
        IbEstatusPagosPj estatus = null;

        try {
            ibCargaNominaDetallePj = (IbCargaNominaDetallePj) getEntityManager().find(IbCargaNominaDetallePj.class, idTransaction);
            if (ibCargaNominaDetallePj != null) {
                estatus = this.ibEstatusPagosPjFacade.buscarIbEstatusPj(idEstatus);
                ibCargaNominaDetallePj.setEstatus(estatus);
                ibCargaNominaDetallePj = (IbCargaNominaDetallePj) getEntityManager().merge(ibCargaNominaDetallePj);
            }

        } catch (NoResultException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }
        return ibCargaNominaDetallePj;
    }

    public IbCargaPagosCorpDetPj actualizarEstatusDetallePagosPorId(BigDecimal idTransaction, BigDecimal idEstatus) throws Exception {

        return this.ibCargaPagosCorpDetPjFacade.actualizarEstatusDetallePagosPorId(idTransaction, idEstatus);
    }

    public IbBeneficiariosPj actualizarEstatusDetalleBeneficiarioPorId(BigDecimal idBeneficiario, BigDecimal idEstatus,Integer cantidadAprovadores) throws Exception {

        return this.IbBeneficiariosPjFacade.actualizarEstatusBeneficiarioPorId(idBeneficiario, idEstatus,cantidadAprovadores);
    }
    
    public IbFideicomisoDetPj actualizarFideicomisoId(BigDecimal idBeneficiario, BigDecimal idEstatus) throws Exception {

        return this.ibFideicomisoPjFacade.actualizarFideicomisoId(idBeneficiario, idEstatus);
    }

    public IbCargaPagosEspDetPj actualizarEstatusDetallePagosEspecialesPorId(BigDecimal idpagoespecialDet, BigDecimal idEstatus) throws Exception {

        return this.ibCargaPagosEspDetPjFacade.actualizarEstatusPagosEspecialesDetPorId(idpagoespecialDet, idEstatus);
    }

    public IbCargaPagosEspDetPjFacade getIbCargaPagosEspDetPjFacade() {
        return ibCargaPagosEspDetPjFacade;
    }

    public void setIbCargaPagosEspDetPjFacade(IbCargaPagosEspDetPjFacade ibCargaPagosEspDetPjFacade) {
        this.ibCargaPagosEspDetPjFacade = ibCargaPagosEspDetPjFacade;
    }

    public IbCargaPagosCorpDetPjFacade getIbCargaPagosCorpDetPjFacade() {
        return ibCargaPagosCorpDetPjFacade;
    }

    public void setIbCargaPagosCorpDetPjFacade(IbCargaPagosCorpDetPjFacade ibCargaPagosCorpDetPjFacade) {
        this.ibCargaPagosCorpDetPjFacade = ibCargaPagosCorpDetPjFacade;
    }

    public IbBeneficiariosPjFacade getIbBeneficiariosPjFacade() {
        return IbBeneficiariosPjFacade;
    }

    public void setIbBeneficiariosPjFacade(IbBeneficiariosPjFacade IbBeneficiariosPjFacade) {
        this.IbBeneficiariosPjFacade = IbBeneficiariosPjFacade;
    }

    public IbEstatusPagosPjFacade getIbEstatusPagosPjFacade() {
        return ibEstatusPagosPjFacade;
    }

    public void setIbEstatusPagosPjFacade(IbEstatusPagosPjFacade ibEstatusPagosPjFacade) {
        this.ibEstatusPagosPjFacade = ibEstatusPagosPjFacade;
    }

    public IbSecuenciaAprobacionPjFacade getIbSecuenciaAprobacionPjFacade() {
        return ibSecuenciaAprobacionPjFacade;
    }

    public void setIbSecuenciaAprobacionPjFacade(IbSecuenciaAprobacionPjFacade ibSecuenciaAprobacionPjFacade) {
        this.ibSecuenciaAprobacionPjFacade = ibSecuenciaAprobacionPjFacade;
    }

    public IbServiciosPjFacade getIbServiciosPjFacade() {
        return ibServiciosPjFacade;
    }

    public void setIbServiciosPjFacade(IbServiciosPjFacade ibServiciosPjFacade) {
        this.ibServiciosPjFacade = ibServiciosPjFacade;
    }

    public IbUsuariosPjFacade getIbUsuariosPjFacade() {
        return ibUsuariosPjFacade;
    }

    public void setIbUsuariosPjFacade(IbUsuariosPjFacade ibUsuariosPjFacade) {
        this.ibUsuariosPjFacade = ibUsuariosPjFacade;
    }

}
