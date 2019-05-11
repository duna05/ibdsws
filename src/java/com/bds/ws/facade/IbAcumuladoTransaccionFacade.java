/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.facade;

import com.bds.ws.dto.IBAcumuladoTransaccionDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.model.IbAcumuladoTransaccion;
import java.math.BigDecimal;
import java.util.ArrayList;
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
@Named("ibAcumuladoTransaccionFacade")
@Stateless(name = "wsIbAcumuladoTransaccionFacade")
public class IbAcumuladoTransaccionFacade extends AbstractFacade<IbAcumuladoTransaccion> {

    /**
     * Log de sistema
     */
    private static final Logger logger = Logger.getLogger(IbAcumuladoTransaccionFacade.class.getName());

    @PersistenceContext(unitName = "ibdsPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IbAcumuladoTransaccionFacade() {
        super(IbAcumuladoTransaccion.class);
    }

    /**
     * Metodo que Obtiene los acumulado de transacciones que tiene un cliente
     * por día
     *
     * @param usuario String Referencia foranea al usuario dueno de la
     * afiliacion.
     * @param idTransaccion String ID de el tipo de transaccion.
     * @param canal String nombreCanal por el cual se ejecuta la transaccion.
     * @return IBAfiliacionesDTO
     */
    public IBAcumuladoTransaccionDTO obtenerAcumuladosTransaccionUsuario(String usuario, String canal) {
        IBAcumuladoTransaccionDTO acumuladoTransaccionDTO = new IBAcumuladoTransaccionDTO();
        RespuestaDTO respuestaDTO = new RespuestaDTO();
        List<IbAcumuladoTransaccion> acum = new ArrayList<>();
        List<IbAcumuladoTransaccion> acumuladoUsuario = new ArrayList<>();
        String fecha = this.formatearFecha(new Date(), "yyyyMMdd");
        String fechaAux;

        try {
            acum = (List<IbAcumuladoTransaccion>) em.createQuery("select c from IbAcumuladoTransaccion c "
                    + "where c.idUsuario.id =:usuario order by c.fecha desc")
                    .setParameter("usuario", new BigDecimal(usuario))
                    .getResultList();
            //Pregunto si hay registros
            if (acum.size() > 0) {
                //Se recorre la lista devuelta
                for (IbAcumuladoTransaccion i : acum) {
                    fechaAux = this.formatearFecha(i.getFecha(), "yyyyMMdd");
                    //Se comparan las fechas y si son iguales se guarda el registro
                    if (Integer.parseInt(fecha) == Integer.parseInt(fechaAux)) {
                        acumuladoUsuario.add(i);
                    }
                }
            }

        } catch (NoResultException e) {
            respuestaDTO.setCodigo(CODIGO_SIN_RESULTADOS_JPA);//revisar el log   
            respuestaDTO.setDescripcion(TEXTO_SIN_RESULTADOS_JPA);
            logger.error( new StringBuilder("ERROR JPA EN obtenerAcumuladosTransaccionUsuario: ")
                    .append("USR-").append(usuario)
                    .append("-CH-").append(canal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (IllegalArgumentException e) {
            respuestaDTO.setCodigo("JPA003");//revisar el log     
            logger.error( new StringBuilder("ERROR JPA EN obtenerAcumuladosTransaccionUsuario: ")
                    .append("USR-").append(usuario)
                    .append("-CH-").append(canal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (Exception ex) {
            respuestaDTO.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log            
        } finally {
            acumuladoTransaccionDTO.setRespuesta(respuestaDTO);
        }

        // acumuladoTransaccionDTO.setIbAcumuladoTransacciones(acum);
        acumuladoTransaccionDTO.setIbAcumuladoTransacciones(acumuladoUsuario);

        return acumuladoTransaccionDTO;
    }

}
