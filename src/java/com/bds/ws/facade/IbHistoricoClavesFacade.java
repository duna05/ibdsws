/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.facade;

import com.bds.ws.dto.IbHistoricoClavesDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.dto.UtilDTO;
import com.bds.ws.model.IbHistoricoClaves;
import static java.lang.Integer.parseInt;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.log4j.Logger;

/**
 *
 * @author wilmer.rondon
 */
@Named
@Stateless(name = "wsIbHistoricoClavesFacade")
public class IbHistoricoClavesFacade extends AbstractFacade<IbHistoricoClaves>{
    
    private static final Logger logger = Logger.getLogger(IbHistoricoClavesFacade.class.getName());
    
    @EJB
    IbParametrosFacade parametrosFacade;
    
    @PersistenceContext(unitName = "ibdsPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IbHistoricoClavesFacade() {
        super(IbHistoricoClaves.class);
    }

/**
 * Método que se encarga de consultar si la nueva clave ya se encuentra entre las
 * últimas N claves que el cliente ha tenido
 * @param idUsuario
 * @param clave
 * @param cantClaves
 * @param idcanal
 * @return UtilDTO
 */    
public UtilDTO existeEnUltimasNClaves(String idUsuario, String clave, String cantClaves, String idcanal){
        UtilDTO utilDTO = new UtilDTO();
        List<IbHistoricoClaves> ibHistoricoClaves = new ArrayList<>();
        RespuestaDTO respuestaDTO = new RespuestaDTO();
        boolean valor = false;
        Map resultados = new HashMap();

        try {
            em.getEntityManagerFactory().getCache().evictAll();
            ibHistoricoClaves = (List<IbHistoricoClaves>) em.createQuery("select i from IbHistoricoClaves i"
                    + " where i.idUsuario.id = :idUsuario"
                    + " order by i.fechaCreacion desc"
            )
                    .setParameter("idUsuario", BigDecimal.valueOf(Long.parseLong(idUsuario)))
                    .setMaxResults(parseInt(cantClaves)).getResultList();                        

            if (!ibHistoricoClaves.isEmpty()) {
                for (IbHistoricoClaves i: ibHistoricoClaves){
                    if(i.getClave().compareTo(clave) == 0){
                        valor = true;
                    }                    
                }
            }

            resultados.put("1", valor);
            utilDTO.setResulados(resultados);
        } catch (IllegalArgumentException e) {
            respuestaDTO.setCodigo("JPA003");//revisar el log     
            logger.error( new StringBuilder("ERROR JPA EN existeEnUltimasNClaves: ")
                    .append("USR-").append(idUsuario)
                    .append("-CH-").append(idcanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (Exception e) {
            respuestaDTO.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }
        utilDTO.setRespuesta(respuestaDTO);
        return utilDTO;    
}

/**
 * Método que se encarga de retornar la última clave que tiene el cliente activa
 * @param idUsuario
 * @param idcanal
 * @return IbHistoricoClavesDTO
 */
public IbHistoricoClavesDTO obtenerUltimaClave(String idUsuario, String idcanal){
        
        IbHistoricoClaves ibHistoricoClaves = new IbHistoricoClaves();
        IbHistoricoClavesDTO ibHistoricoClavesDTO = new IbHistoricoClavesDTO();
        RespuestaDTO respuestaDTO = new RespuestaDTO();

        try {
            em.getEntityManagerFactory().getCache().evictAll();
            ibHistoricoClaves = (IbHistoricoClaves) em.createQuery("select i from IbHistoricoClaves i "
                    + " where i.idUsuario.id = :idUsuario"
                    + " order by i.fechaCreacion desc"
            )
                    .setParameter("idUsuario", BigDecimal.valueOf(Long.parseLong(idUsuario)))
                    .setMaxResults(1).getSingleResult();

            ibHistoricoClavesDTO.setIbHistoricoClave(ibHistoricoClaves);
            
        } catch (IllegalArgumentException e) {
            respuestaDTO.setCodigo("JPA003");//revisar el log     
            logger.error( new StringBuilder("ERROR JPA EN obtenerUltimaClave: ")
                    .append("USR-").append(idUsuario)
                    .append("-CH-").append(idcanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (Exception e) {
            respuestaDTO.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }
        ibHistoricoClavesDTO.setRespuesta(respuestaDTO);
        return ibHistoricoClavesDTO;
}

}
