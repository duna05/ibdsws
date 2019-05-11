/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.facade;

import com.bds.ws.dto.IBAgendaPNDTO;
import com.bds.ws.dto.IBAgendaTransaccionDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.dto.UtilDTO;
import com.bds.ws.model.IbAgendaTransaccionesPn;
import com.bds.ws.util.BDSUtil;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import org.apache.log4j.Logger;

/**
 *
 * @author cesar.mujica
 */
@Named("ibAgendaTransaccionesPnFacade")
@Stateless(name = "wsIbAgendaTransaccionesPnFacade")
public class IbAgendaTransaccionesPnFacade extends AbstractFacade<IbAgendaTransaccionesPn> {

    /*
     *logs del sistema
     */
    private static final Logger logger = Logger.getLogger(IbAgendaTransaccionesPnFacade.class.getName());

    @EJB
    IbUsuariosFacade usuariosFacade;

    @PersistenceContext(unitName = "ibdsPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IbAgendaTransaccionesPnFacade() {
        super(IbAgendaTransaccionesPn.class);
    }

    /**
     * Metodo se encarga de almacenar la cabecera de una transaccion agendada
     *
     * @param agenda Objeto con los datos de la cabecera de transaccion a
     * agendar
     * @param nombreCanal nombre del canal
     * @return RespuestaDTO
     */
    public RespuestaDTO agregarCabeceraAgenda(IBAgendaTransaccionDTO agenda, String nombreCanal) {
        RespuestaDTO respuestaDTO = new RespuestaDTO();
        IbAgendaTransaccionesPn cabeceraAgenda = new IbAgendaTransaccionesPn();
        try {
            cabeceraAgenda.setId(BigDecimal.ZERO);
            cabeceraAgenda.setIdUsuario(usuariosFacade.find(agenda.getIdUsuario()));
            cabeceraAgenda.setCuentaDestino(agenda.getCuentaDestino());
            cabeceraAgenda.setCuentaOrigen(agenda.getCuentaOrigen());
            cabeceraAgenda.setDiaFrecuencia(agenda.getDiaFrecuencia());
            cabeceraAgenda.setMonto(agenda.getMonto());
            cabeceraAgenda.setFechaLimite(this.formatearFechaStringADate(agenda.getFechalimite(), this.FORMATO_FECHA_SIMPLE));
            cabeceraAgenda.setFrecuencia(agenda.getFrecuencia());
            cabeceraAgenda.setMonto(agenda.getMonto());
            cabeceraAgenda.setTipo(agenda.getTipo());

            respuestaDTO = this.crear(cabeceraAgenda);
            this.em.setFlushMode(FlushModeType.COMMIT);
            this.em.flush();

        } catch (IllegalArgumentException e) {
            respuestaDTO.setCodigo("JPA003");
            respuestaDTO.setDescripcion(BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA);
            logger.error( new StringBuilder("ERROR JPA EN agregarCabeceraAgenda: ")
                    .append("USR-").append(cabeceraAgenda.getIdUsuario().getId())
                    .append("CTAORG-").append(this.numeroCuentaFormato(cabeceraAgenda.getCuentaOrigen()))
                    .append("CTADST-").append(this.numeroCuentaFormato(cabeceraAgenda.getCuentaDestino()))
                    .append("MTO-").append(cabeceraAgenda.getMonto())
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
     * Metodo se encarga de obtener el id de la cabecera de una transaccion agendada
     *
     * @param agenda Objeto con los datos de la cabecera de transaccion a
     * agendar
     * @param nombreCanal nombre del canal
     * @return UtilDTO
     */
    public UtilDTO consultarIdCabeceraAgenda(IBAgendaTransaccionDTO agenda, String nombreCanal) {
        UtilDTO utilDTO = new UtilDTO(); 
        Map resultados = new HashMap();
        RespuestaDTO respuestaDTO = new RespuestaDTO(); 
        try {
            BigDecimal idCabecera = (BigDecimal) em 
                    .createQuery("SELECT i.id FROM IbAgendaTransaccionesPn i WHERE i.idUsuario.id = :idUsuario "
                            + "and i.tipo = :tipo "
                            + "and i.cuentaOrigen = :ctaOrigen "
                            + "and i.cuentaDestino = :ctaDestino "
                            + "and i.monto = :monto "
                            + "and i.frecuencia = :frecuencia "
                            + "and i.diaFrecuencia = :diaFrecuencia "
                            + "and i.fechaLimite = :fechaLimite")
                    .setParameter("idUsuario", agenda.getIdUsuario())
                    .setParameter("tipo", agenda.getTipo())
                    .setParameter("ctaOrigen", agenda.getCuentaOrigen())
                    .setParameter("ctaDestino", agenda.getCuentaDestino())
                    .setParameter("monto", agenda.getMonto())
                    .setParameter("frecuencia", agenda.getFrecuencia())
                    .setParameter("diaFrecuencia", agenda.getDiaFrecuencia())
                    .setParameter("fechaLimite", this.formatearFechaStringADate(agenda.getFechalimite(),this.FORMATO_FECHA_SIMPLE)).getSingleResult();

            if (idCabecera == null ) {
                throw new NoResultException();
            }
            resultados.put("id", idCabecera);
            utilDTO.setResulados(resultados);

        } catch (IllegalArgumentException e) {
            respuestaDTO.setCodigo("JPA003");
            respuestaDTO.setDescripcion(BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA);
            logger.error( new StringBuilder("ERROR JPA EN consultarIdCabeceraAgenda: ")
                    .append("USR-").append(agenda.getIdUsuario())
                    .append("CTAORG-").append(this.numeroCuentaFormato(agenda.getCuentaOrigen()))
                    .append("CTADST-").append(this.numeroCuentaFormato(agenda.getCuentaDestino()))
                    .append("MTO-").append(agenda.getMonto())
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (Exception e) {
            respuestaDTO.setCodigo(BDSUtil.CODIGO_EXCEPCION_GENERICA);
            respuestaDTO.setDescripcion(BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA);
        }
        utilDTO.setRespuesta(respuestaDTO);
        return utilDTO;
    }
    
    
    /**
     * Metodo se encarga de obtener el id de la cabecera de una transaccion agendada
     *
     * @param idUsuario
     * @param tipo
     * @param idTransaccion
     * @param agenda Objeto con los datos de la cabecera de transaccion a
     * agendar
     * @param nombreCanal nombre del canal
     * @return UtilDTO
     */
    public IBAgendaPNDTO consultarIdCabeceraAgendaPP(BigDecimal idUsuario, char tipo, int idTransaccion, String nombreCanal) {
        RespuestaDTO respuestaDTO = new RespuestaDTO(); 
        IBAgendaPNDTO agendaDetalle = new IBAgendaPNDTO();
        try {
             agendaDetalle.setIbAgendas((List<IbAgendaTransaccionesPn>) em 
                    .createQuery("SELECT DISTINCT i FROM IbAgendaTransaccionesPn i, IbDetalleAgendaTransPn d WHERE i.idUsuario.id = :idUsuario "
                            + "and i.tipo = :tipo  and i.id = d.idAgenda.id and d.idTransaccion =:idTransaccion")
                    .setParameter("idUsuario", idUsuario)
                    .setParameter("tipo", tipo)
                    .setParameter("idTransaccion", idTransaccion).getResultList());

            if (agendaDetalle == null  || agendaDetalle.getIbAgendas().isEmpty()) {
                throw new NoResultException();
            }
    
        } catch (IllegalArgumentException e) {
            respuestaDTO.setCodigo("JPA003");
            respuestaDTO.setDescripcion(BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA);
            logger.error( new StringBuilder("ERROR JPA EN consultarIdCabeceraAgendaPP: ")
                    .append("USR-").append(idUsuario)
                    .append("TIPO-").append(tipo)
                    .append("TIPT-").append(idTransaccion)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (Exception e) {
            respuestaDTO.setCodigo(BDSUtil.CODIGO_EXCEPCION_GENERICA);
            respuestaDTO.setDescripcion(BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA);
        }
        agendaDetalle.setRespuesta(respuestaDTO);
        return agendaDetalle;
    }

}
