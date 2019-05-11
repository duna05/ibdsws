/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.facade;

import com.bds.ws.dto.IbTransaccionesDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.model.IbTransacciones;
import static com.bds.ws.util.BDSUtil.CODIGO_EXCEPCION_GENERICA;
import static com.bds.ws.util.BDSUtil.CODIGO_SIN_RESULTADOS_JPA;
import static com.bds.ws.util.BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import org.apache.log4j.Logger;

/**
 *
 * @author cesar.mujica
 */
@Named("ibTransaccionesFacade")
@Stateless(name = "wsIbTransaccionesFacade")
public class IbTransaccionesFacade extends AbstractFacade<IbTransacciones> {

    /*
     *logs del sistema
     */
    private static final Logger logger = Logger.getLogger(IbTransaccionesFacade.class.getName());

    @PersistenceContext(unitName = "ibdsPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IbTransaccionesFacade() {
        super(IbTransacciones.class);
    }

    /**
     * *
     * Metodo que realiza la busqueda del submenu aasociadas a un modulo por
     * status
     *
     * @param nombreCanal nombre ext del canal
     * @param codEstatus caracter del status 'A' activo, 'P' piloto e 'I'
     * inactivo
     * @param idModulo identificador del modulo padre
     * @return IbTransaccionesDTO
     */
    public IbTransaccionesDTO consultaModulosTransacciones(String nombreCanal, Character codEstatus, BigDecimal idModulo) {

        RespuestaDTO respuestaDTO = new RespuestaDTO();
        IbTransaccionesDTO ibTransaccionesDTO = new IbTransaccionesDTO();
        String condicion = "";

        try {
            if (codEstatus.equals('P')) {
                condicion = " <> ";
                //se le asigna Inactivo ya que que la condicion sera diferente a Inacivo (Activas + Piloto)
                codEstatus = 'I';
            } else {
                condicion = " = ";
            }

            ibTransaccionesDTO.setTransaccciones((List<IbTransacciones>) em.createQuery("SELECT distinct c FROM IbTransacciones c "
                    + "WHERE c.estatus " + condicion + ":codEstatus "
                    + "and c.idModulo.id = :idModulo "
                    + "ORDER BY c.posicion ASC"
            )
                    .setParameter("codEstatus", codEstatus)
                    .setParameter("idModulo", idModulo)
                    .getResultList()
            );
            
            if (ibTransaccionesDTO.getTransaccciones().isEmpty()){
                throw new NoResultException();
            }

        } catch (NoResultException e) {
            respuestaDTO.setCodigoSP(CODIGO_SIN_RESULTADOS_JPA);//revisar el log    
            respuestaDTO.setTextoSP(TEXTO_SIN_RESULTADOS_JPA);
            logger.error( new StringBuilder("ERROR JPA EN consultaModulosTransacciones: ")
                    .append("MOD-").append(idModulo)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (IllegalArgumentException e) {
            respuestaDTO.setCodigo("JPA003");//revisar el log     
            logger.error( new StringBuilder("ERROR JPA EN consultaModulosTransacciones: ")
                    .append("MOD-").append(idModulo)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (Exception e) {
            respuestaDTO.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }

        ibTransaccionesDTO.setRespuesta(respuestaDTO);

        return ibTransaccionesDTO;
    }
    
    /**
     * *
     * Metodo que realiza la busqueda del submenu aasociadas a un modulo por
     * status y posee beneficiarios activo
     *
     * @param nombreCanal nombre ext del canal
     * @param idModulo identificador del modulo padre
     * @return IbTransaccionesDTO
     */
    public IbTransaccionesDTO obtenerModeloTransBeneficiario(BigDecimal idModulo, String nombreCanal) {

        RespuestaDTO respuestaDTO = new RespuestaDTO();
        IbTransaccionesDTO ibTransaccionesDTO = new IbTransaccionesDTO();

        try {

            ibTransaccionesDTO.setTransaccciones((List<IbTransacciones>) em.createQuery("SELECT distinct c FROM IbTransacciones c "
                    + "WHERE c.estatus = 'A' "
                    + " and c.idModulo.id = :idModulo "
                    + " and c.poseeBeneficiario = 1 "
                    + "ORDER BY c.posicion ASC"
            )
                    .setParameter("idModulo", idModulo)
                    .getResultList()
            );
            
            if (ibTransaccionesDTO.getTransaccciones().isEmpty()){
                throw new NoResultException();
            }

        } catch (NoResultException e) {
            respuestaDTO.setCodigoSP(CODIGO_SIN_RESULTADOS_JPA);//revisar el log 
            respuestaDTO.setTextoSP(TEXTO_SIN_RESULTADOS_JPA);
            logger.error( new StringBuilder("ERROR JPA EN obtenerModeloTransBeneficiario: ")
                    .append("MOD-").append(idModulo)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (IllegalArgumentException e) {
            respuestaDTO.setCodigo("JPA003");//revisar el log     
            logger.error( new StringBuilder("ERROR JPA EN obtenerModeloTransBeneficiario: ")
                    .append("MOD-").append(idModulo)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (Exception e) {
            respuestaDTO.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }

        ibTransaccionesDTO.setRespuesta(respuestaDTO);

        return ibTransaccionesDTO;
    }
    
    /**
     * *
     * Metodo que obtiene todas las transacciones asociadas a un modulo esten activas o inactivas
     * @author wilmer.rondon
     * @param idModulo identificador del modulo padre
     * @param nombreCanal nombre del canal para efectos de los logs     
     * @return IbTransaccionesDTO
     */
    public IbTransaccionesDTO obtenerTodasTransacciones(BigDecimal idModulo, String nombreCanal) {

        RespuestaDTO respuestaDTO = new RespuestaDTO();
        IbTransaccionesDTO ibTransaccionesDTO = new IbTransaccionesDTO();

        try {

            ibTransaccionesDTO.setTransaccciones((List<IbTransacciones>) em.createQuery("SELECT distinct c "
                    + "FROM IbTransacciones c, "
                    + "IbTextos tt "
                    + "WHERE c.idModulo.id = :idModulo "
                    + "and tt.codigo = c.nombre ORDER BY tt.mensajeUsuario ASC"
            )
                    .setParameter("idModulo", idModulo)
                    .getResultList()
            );
            
            if (ibTransaccionesDTO.getTransaccciones().isEmpty()){
                throw new NoResultException();
            }

        } catch (NoResultException e) {
            respuestaDTO.setCodigoSP(CODIGO_SIN_RESULTADOS_JPA);//revisar el log     
            respuestaDTO.setTextoSP(TEXTO_SIN_RESULTADOS_JPA);
            logger.error( new StringBuilder("ERROR JPA EN obtenerTodasTransacciones: ")
                    .append("MOD-").append(idModulo)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (IllegalArgumentException e) {
            respuestaDTO.setCodigo("JPA003");//revisar el log     
            logger.error( new StringBuilder("ERROR JPA EN obtenerTodasTransacciones: ")
                    .append("MOD-").append(idModulo)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (Exception e) {
            respuestaDTO.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }

        ibTransaccionesDTO.setRespuesta(respuestaDTO);

        return ibTransaccionesDTO;
    }

}
