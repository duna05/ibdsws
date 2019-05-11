/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.facade;

import com.bds.ws.dto.IbAprobacionesServiciosPjDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.model.IbAprobacionesServiciosPj;
import com.bds.ws.model.IbEmpresas;
import com.bds.ws.model.IbServiciosPj;
import static com.bds.ws.util.BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author luis.perez
 */
@Stateless
public class IbAprobacionesServiciosPjFacade extends AbstractFacade<IbAprobacionesServiciosPj> {

    @EJB
    IbTextosFacade textosFacade;

    @PersistenceContext(unitName = "ibdsPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IbAprobacionesServiciosPjFacade() {
        super(IbAprobacionesServiciosPj.class);
    }

    /**
     * Lista las aprobaciones por servicio para una empresa
     *
     * Parametros
     *
     * @param idEmpresa codigo interno de la empresa en IB
     * @param idCanal the value of idCanal
     *
     * @return the java.util.List<com.bds.ws.model.IbAprobacionesServiciosPj>
     */
    public List<IbAprobacionesServiciosPj> consultarAprobacionesEmpresaServiciosPj(BigDecimal idEmpresa, String idCanal) {
        List<IbAprobacionesServiciosPj> ibAprobacionesServiciosPj = new ArrayList<>();
        List<IbServiciosPj> ibServiciosPj = null;
        IbAprobacionesServiciosPj ibAprobacionServicioPj = null;
        try {
            StringBuilder consulta = new StringBuilder();
            consulta.append(" SELECT e FROM IbServiciosPj              e       ");
            consulta.append(" WHERE  e.requiereAprobador  = :requiereAprobador ");

            ibServiciosPj = (List<IbServiciosPj>) em.createQuery(consulta.toString())
                    .setParameter("requiereAprobador", new BigDecimal(BigInteger.ONE))
                    .getResultList();

            //POR CADA SERVICIO SE VERIFICA SI EL MISMO ESTA EN LA TABLA 
            //APROBACIONES SERVICIOS PARA ESA EMPRESA, EN ESE CASO SE DEVUELVE 
            //LOS DATOS DE ESA TABLA
            for (IbServiciosPj ibServicioPj : ibServiciosPj) {
                consulta = new StringBuilder();
                consulta.append(" SELECT s FROM IbAprobacionesServiciosPj s ");
                consulta.append(" WHERE  s.idServicioPj.id = :idServicio ");
                consulta.append(" AND    s.idEmpresa.id    = :idEmpresa ");

                try {
                    ibAprobacionServicioPj = (IbAprobacionesServiciosPj) em.createQuery(consulta.toString())
                            .setParameter("idServicio", ibServicioPj.getId())
                            .setParameter("idEmpresa", idEmpresa)
                            .getSingleResult();
                } catch (NoResultException e) {
                    //SI NO EXISTE EL REGISTRO EN LA TABLA
                    ibAprobacionServicioPj = new IbAprobacionesServiciosPj();
                    ibAprobacionServicioPj.setCantAprobadores(BigInteger.ZERO);
                    ibAprobacionServicioPj.setId(BigDecimal.ZERO);
                    ibAprobacionServicioPj.setIdEmpresa(new IbEmpresas(idEmpresa));
                    ibAprobacionServicioPj.setIdServicioPj(ibServicioPj);
                }

                //SE COLOCA EL NOMBRE DEL SERVICIO DE ACUERDO A LO PARAMETRIZADO EN IB TEXTOS
                //ibAprobacionServicioPj.getIdServicioPj().setNombre(textosFacade.textoParametro(ibServicioPj.getNombre(), idCanal));
                ibAprobacionesServiciosPj.add(ibAprobacionServicioPj);
            }
        } catch (Exception e) {
            throw e;
        }
        return ibAprobacionesServiciosPj;
    }

    public IbAprobacionesServiciosPjDTO consultarByServicioEmpresa(BigDecimal cdClienteAbanks, String servicio, String idCanal) {
        IbAprobacionesServiciosPjDTO ibAprobacionesServiciosPjDTO = new IbAprobacionesServiciosPjDTO();
        ibAprobacionesServiciosPjDTO.setRespuesta(new RespuestaDTO());
        IbAprobacionesServiciosPj ibAprobacionServicioPj = null;

        StringBuilder consulta = new StringBuilder();
        consulta.append(" SELECT s FROM IbAprobacionesServiciosPj s  ");
        consulta.append("  WHERE s.idServicioPj.nombre = :servicio");
        consulta.append("  AND   s.idEmpresa.codCliente = :cdClienteAbanks ");

        try {
            Long lcdClienteAbanks = Long.valueOf(cdClienteAbanks.toString());
            ibAprobacionServicioPj = (IbAprobacionesServiciosPj) em.createQuery(consulta.toString())
                    .setParameter("cdClienteAbanks", lcdClienteAbanks)
                    .setParameter("servicio", servicio)
                    .getSingleResult();
            ibAprobacionesServiciosPjDTO.setIbAprobacionServicioPj(ibAprobacionServicioPj);
            return ibAprobacionesServiciosPjDTO;
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR JPA EN consultarByServicioEmpresa")
                    .append("-CH- ").append(idCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);

            ibAprobacionesServiciosPjDTO.setIbAprobacionServicioPj(null);
            ibAprobacionesServiciosPjDTO.getRespuesta().setCodigo(CODIGO_ERROR_VALIDACIONES);
            ibAprobacionesServiciosPjDTO.setIbAprobacionServicioPj(ibAprobacionServicioPj);
            return ibAprobacionesServiciosPjDTO;
        }
    }
}
