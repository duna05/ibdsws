/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.facade;

import com.bds.ws.dto.IbErroresCargaPagosCorpDetPjDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.model.IbErroresPaCorpDetPj;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author christian.gutierrez
 */
@Named("ibErroresCargaPagosCorpDetPjFacade")
@Stateless(name = "ibErroresCargaPagosCorpDetPjFacade")
public class IbErroresCargaPagosCorpDetPjFacade extends AbstractFacade<IbErroresPaCorpDetPj> {

    @PersistenceContext(unitName = "ibdsPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IbErroresCargaPagosCorpDetPjFacade() {
        super(IbErroresPaCorpDetPj.class);
    }

    public IbErroresPaCorpDetPj buscarIbErrCargaPagosEspDetPj(BigDecimal idPago) {
        IbErroresPaCorpDetPj err_pagos_corporativo = null;
        try {
            err_pagos_corporativo = (IbErroresPaCorpDetPj) getEntityManager().find(IbErroresPaCorpDetPj.class, idPago);
        } catch (NoResultException e) {
            // Se debe validar en la clase que llama el metodo
        } catch (Exception e) {
            //TODO: definir respuesta incorrecta            
            throw e;
        }
        return err_pagos_corporativo;
    }

    /**
     * CASH410 – Consulta - Lista de pagos para Pagos especiales - error
     *
     * @param idpago id del pago a consultar
     * @return ibCargaPagosEspPjDTO resulset de la consulta
     * @throws java.lang.Exception Error lanzado por el facade
     */
    public IbErroresCargaPagosCorpDetPjDTO listarIbErrCargaPagosCorpDetPj(BigDecimal idpago) throws Exception {

        IbErroresCargaPagosCorpDetPjDTO ibErroresCargaPagosCorpDetPjDTO = new IbErroresCargaPagosCorpDetPjDTO();
        ibErroresCargaPagosCorpDetPjDTO.setIbErrCargaPagosEspDetPjsList(new ArrayList<IbErroresPaCorpDetPj>());
        ibErroresCargaPagosCorpDetPjDTO.setRespuestaDTO(new RespuestaDTO());
        
         List<IbErroresPaCorpDetPj> ibErroresPaCorpDetPjList;
        
        
        try {
            StringBuilder consulta = new StringBuilder();
            consulta.append("SELECT e FROM IbErroresPaCorpDetPj e ");
            consulta.append(" WHERE   e.idPagoDetalle.idPago.idPago = :idPago ");
            consulta.append(" AND     e.idPagoDetalle.estatusAutorizacion.id > 0 ");
            consulta.append(" ORDER BY e.id, e.id DESC  ");

            ibErroresPaCorpDetPjList = (List<IbErroresPaCorpDetPj>) em.createQuery(consulta.toString())
                    .setParameter("idPago", idpago)
                    .getResultList();
            
            ibErroresCargaPagosCorpDetPjDTO.setIbErrCargaPagosEspDetPjsList(ibErroresPaCorpDetPjList);
            
            
           
        } catch (Exception e) {
            throw e;
        }
        
        return ibErroresCargaPagosCorpDetPjDTO;
    }

}
