/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.facade;

import com.bds.ws.dto.IbtextosDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.model.IbTextos;
import static com.bds.ws.util.BDSUtil.CODIGO_EXCEPCION_GENERICA;
import static com.bds.ws.util.BDSUtil.CODIGO_SIN_RESULTADOS_JPA;
import static com.bds.ws.util.BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
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
@Named("ibTextosFacade")
@Stateless(name = "wsIbTextosFacade")
public class IbTextosFacade extends AbstractFacade<IbTextos> {

    @PersistenceContext(unitName = "ibdsPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    private static final Logger logger = Logger.getLogger(IbTextosFacade.class.getName());

    public IbTextosFacade() {
        super(IbTextos.class);
    }

    /**
     * Metodo que realiza la consulta de un texto en la tabla IB_TEXTOS y
     * retorna su descripcion
     *
     * @param codigoParametro codigo del parametro
     * @param nombreCanal nombre del canal
     * @return String
     */
    public String textoParametro(String codigoParametro, String nombreCanal) {
        IbtextosDTO ibtextosDTO = new IbtextosDTO();
        IbTextos ibTextos = new IbTextos();
        RespuestaDTO respuestaDTO = new RespuestaDTO();

        try {
            ibTextos = (IbTextos) em.createNamedQuery("IbTextos.findByCodigo")
                    .setParameter("codigo", codigoParametro)
                    .getSingleResult();

            ibtextosDTO.setIbTextos(ibTextos);

            if (ibtextosDTO.getIbTextos().getMensajeUsuario().isEmpty() || ibtextosDTO.getIbTextos().getMensajeUsuario() == null
                    || ibtextosDTO.getIbTextos().getMensajeUsuario().equals("")) {
                throw new NoResultException();
            }

        } catch (NoResultException e) {
            respuestaDTO.setCodigoSP(CODIGO_SIN_RESULTADOS_JPA);//revisar el log     
            respuestaDTO.setTextoSP(TEXTO_SIN_RESULTADOS_JPA);
            logger.error( new StringBuilder("ERROR JPA EN textoParametro: ")
                    .append("PARAMETRO-").append(codigoParametro)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            return "";
        } catch (IllegalArgumentException e) {
            respuestaDTO.setCodigo("JPA003");//revisar el log     
            logger.error( new StringBuilder("ERROR JPA EN textoParametro: ")
                    .append("PARAMETRO-").append(codigoParametro)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (Exception e) {
            respuestaDTO.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
            ibtextosDTO.setRespuesta(respuestaDTO);
        }

        return ibtextosDTO.getIbTextos().getMensajeUsuario();
    }

    public Map<String,String> listarLikeCodigo(String key) throws Exception {
        Map<String,String> mapList = new HashMap(); 
        try {
            
            StringBuilder consulta = new StringBuilder();
            //orden usado el el model IbCargaNominaPj
            consulta.append("SELECT  t ");
            consulta.append("  FROM IbTextos t");
            consulta.append(" WHERE t.codigo LIKE :key");

            Collection listaTextos = em.createQuery(consulta.toString())
                    .setParameter("key", key + "%")
                    .getResultList();            
            
            Iterator interador = listaTextos.iterator();
            IbTextos ibTexto;
            while (interador.hasNext()) {
                ibTexto = (IbTextos)interador.next();
                mapList.put(ibTexto.getCodigo(), ibTexto.getMensajeUsuario());
            }

        } catch (NoResultException e) {
                logger.error( new StringBuilder("ERROR JPA EN listarLikeCodigo SIN RESULTADOS: ")
                    .append("PARAMETRO-").append(key)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());            
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR JPA EN listarLikeCodigo: ")
                    .append("PARAMETRO-").append(key)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            throw e;
        }
        return mapList;
    }
}
