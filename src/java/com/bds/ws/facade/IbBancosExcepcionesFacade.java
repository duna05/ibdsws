/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.facade;

import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.dto.UtilDTO;
import com.bds.ws.model.IbBancosExcepciones;
import static com.bds.ws.util.BDSUtil.CODIGO_EXCEPCION_GENERICA;
import static com.bds.ws.util.BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.log4j.Logger;

/**
 *
 * @author cesar.mujica
 */
@Named("ibBancosExcepcionesFacade")
@Stateless(name = "wsIbBancosExcepcionesFacade")
public class IbBancosExcepcionesFacade extends AbstractFacade<IbBancosExcepciones> {
    
    /**
     * Log de sistema
     */
    private static final Logger logger = Logger.getLogger(IbBancosExcepcionesFacade.class.getName());

    @PersistenceContext(unitName = "ibdsPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IbBancosExcepcionesFacade() {
        super(IbBancosExcepciones.class);
    }
    
    /**
     * Metodo para verificar que el banco no posee restricciones internas
     * @param codbanco codigo del banco
     * @param nombreCanal nombre del canal
     * @return RespuestaDTO
     */
    public UtilDTO verificarBloqueBanco (String codbanco, String nombreCanal){
    long countAf = 0;
    UtilDTO utilDTO = new UtilDTO();
    Map resultados = new HashMap();
    RespuestaDTO respuestaDTO = new RespuestaDTO();
    try{
        countAf = (long) em.createQuery("select count (c) from IbBancosExcepciones c where c.idBanco = :codBanco")
                .setParameter("codBanco", codbanco)
                .getSingleResult();
        
        if(countAf > 0){
            resultados.put("bancoBloqueado", true);
        }else{
            resultados.put("bancoBloqueado", false);
        }
        utilDTO.setResulados(resultados);
    }catch(IllegalArgumentException e){            
            respuestaDTO.setCodigo("JPA003");//revisar el log     
            logger.error( new StringBuilder("ERROR JPA EN verificarProducto: ")
                    .append("BCO-").append(codbanco)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        }catch(Exception ex){
            respuestaDTO.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log            
        }
        
        utilDTO.setRespuesta(respuestaDTO);
                
        return utilDTO;
    }
    
}
