/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.facade;

import com.bds.ws.dto.IBAgendaTransaccionDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.model.IbDetalleAgendaTransPn;
import com.bds.ws.util.BDSUtil;
import java.math.BigDecimal;
import java.util.Date;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.PersistenceContext;
import org.apache.log4j.Logger;

/**
 *
 * @author cesar.mujica
 */
@Named("ibDetalleAgendaTransPnFacade")
@Stateless(name = "wsIbDetalleAgendaTransPnFacade")
public class IbDetalleAgendaTransPnFacade extends AbstractFacade<IbDetalleAgendaTransPn> {

    /*
     *logs del sistema
     */
    private static final Logger logger = Logger.getLogger(IbDetalleAgendaTransPn.class.getName());

    @EJB
    IbUsuariosFacade usuariosFacade;

    @EJB
    IbAgendaTransaccionesPnFacade agendaTransaccionesPnFacade;

    @PersistenceContext(unitName = "ibdsPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IbDetalleAgendaTransPnFacade() {
        super(IbDetalleAgendaTransPn.class);
    }

    /**
     * Metodo se encarga de almacenar el detalle de una transaccion agendada
     *
     * @param agenda Objeto con los datos de la cabecera de transaccion a
     * agendar
     * @param nombreCanal nombre del canal
     * @return RespuestaDTO
     */
    public RespuestaDTO agregarDetalleAgenda(IBAgendaTransaccionDTO agenda, String nombreCanal) {
        RespuestaDTO respuestaDTO = new RespuestaDTO();
        IbDetalleAgendaTransPn detalleAgenda = new IbDetalleAgendaTransPn();
        try {

            if (agenda != null && nombreCanal != null && agenda.getIdAgenda() != null && agenda.getFechaAgendada() != null 
                    && String.valueOf(agenda.getTipoDoc()) != null && agenda.getDocumento() != null && agenda.getDescripcion() != null
                    && agenda.getNombre() != null && agenda.getEmail() != null && agenda.getMonto() != null 
                    && agenda.getCuentaDestino() != null && agenda.getCuentaOrigen() != null && agenda.getIdTransaccion() != 0
                    && agenda.getIdUsuario() != null) {

                detalleAgenda.setId(BigDecimal.ZERO);
                detalleAgenda.setFechaCreacion(new Date());
                detalleAgenda.setIdAgenda(agendaTransaccionesPnFacade.find(agenda.getIdAgenda()));
                detalleAgenda.setEstatus('A');
                detalleAgenda.setFechaAgendada(this.formatearFechaStringADate(agenda.getFechaAgendada(), this.FORMATO_FECHA_SIMPLE));
                detalleAgenda.setTipoDoc(agenda.getTipoDoc());
                detalleAgenda.setDocumento(agenda.getDocumento());
                detalleAgenda.setDescripcion(agenda.getDescripcion());
                detalleAgenda.setNombre(agenda.getNombre());
                detalleAgenda.setEmail(agenda.getEmail());
                detalleAgenda.setMonto(agenda.getMonto());
                detalleAgenda.setCuentaDestino(agenda.getCuentaDestino());
                detalleAgenda.setCuentaOrigen(agenda.getCuentaOrigen());
                detalleAgenda.setIdTransaccion(agenda.getIdTransaccion());
                detalleAgenda.setIdUsuario(usuariosFacade.find(agenda.getIdUsuario()));

                respuestaDTO = this.crear(detalleAgenda);
                this.em.setFlushMode(FlushModeType.COMMIT);
                this.em.flush();
            } else {
                throw new IllegalArgumentException();
            }

        } catch (IllegalArgumentException e) {
            respuestaDTO.setCodigo("JPA003");
            respuestaDTO.setDescripcion(BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA);
            logger.error( new StringBuilder("ERROR JPA EN agregarDetalleAgenda: ")
                    .append("USR-").append(detalleAgenda.getIdUsuario().getId())
                    .append("CTAORG-").append(this.numeroCuentaFormato(detalleAgenda.getCuentaOrigen()))
                    .append("CTADST-").append(this.numeroCuentaFormato(detalleAgenda.getCuentaDestino()))
                    .append("MTO-").append(detalleAgenda.getMonto())
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (Exception e) {
            respuestaDTO.setCodigo(BDSUtil.CODIGO_EXCEPCION_GENERICA);
            respuestaDTO.setDescripcion(BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA);
        }

        return respuestaDTO;
    }

    /**
     * Metodo se encarga de eliminar el detalle de una transaccion agendada
     *
     * @param idAgenda identificador de la agenda padre
     * @param nombreCanal nombre del canal
     * @return RespuestaDTO
     */
    public RespuestaDTO eliminarDetalleAgenda(BigDecimal idAgenda, String nombreCanal) {
        RespuestaDTO respuestaDTO = new RespuestaDTO();
        
        try {

            if (idAgenda != null && nombreCanal != null ) {                
                em.createQuery("DELETE FROM IbDetalleAgendaTransPn i WHERE i.idAgenda.id = :idAgenda AND i.estatus = :sts")
                    .setParameter("idAgenda", idAgenda)
                    .setParameter("sts", 'A').executeUpdate();
                
                this.em.setFlushMode(FlushModeType.COMMIT);
                this.em.flush();
                
            } else {
                throw new IllegalArgumentException();
            }

        } catch (IllegalArgumentException e) {
            respuestaDTO.setCodigo("JPA003");
            respuestaDTO.setDescripcion(BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA);
            logger.error( new StringBuilder("ERROR JPA EN eliminarDetalleAgenda: ")
                    .append("IDAGENDA-").append(idAgenda)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (Exception e) {
            respuestaDTO.setCodigo(BDSUtil.CODIGO_EXCEPCION_GENERICA);
            respuestaDTO.setDescripcion(BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA);
        }

        return respuestaDTO;
    }

}
