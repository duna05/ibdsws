/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.facade;

import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.dto.UtilDTO;
import com.bds.ws.model.IbUsuariosPerfilPiloto;
import static com.bds.ws.util.BDSUtil.CODIGO_EXCEPCION_GENERICA;
import static com.bds.ws.util.BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA;
import java.math.BigDecimal;
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
@Named("ibUsuariosPerfilPilotoFacade")
@Stateless(name = "wsIbUsuariosPerfilPilotoFacade")
public class IbUsuariosPerfilPilotoFacade extends AbstractFacade<IbUsuariosPerfilPiloto> {

    private static final Logger logger = Logger.getLogger(IbUsuariosCanalesFacade.class.getName());
    @PersistenceContext(unitName = "ibdsPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IbUsuariosPerfilPilotoFacade() {
        super(IbUsuariosPerfilPiloto.class);
    }
    
    /***
     * Metodo que realiza la consulta de si un usuario tiene perfil de piloto o no
     * @param idUsuario id del usuario
     * @param idcanal id del canal
     * @return UtilDTO
     */
    public UtilDTO consultaUsaurioperfilPiloto(String idUsuario, String idcanal) {

        UtilDTO utilDTO = new UtilDTO();
        IbUsuariosPerfilPiloto ibUsuariosPerfilPiloto = new IbUsuariosPerfilPiloto();
        RespuestaDTO respuestaDTO = new RespuestaDTO();
        boolean valor = false;
        Map resultados = new HashMap();

        try {

            ibUsuariosPerfilPiloto = (IbUsuariosPerfilPiloto) em.createQuery("SELECT c FROM IbUsuariosPerfilPiloto c "
                    + "WHERE c.idUsuario.id = :idUsuario "
            )
                    .setParameter("idUsuario", BigDecimal.valueOf(Long.parseLong(idUsuario)))
                    .getSingleResult();

            if (ibUsuariosPerfilPiloto != null && ibUsuariosPerfilPiloto.getIdUsuario().getId() != null) {
                valor = true;
            }

            resultados.put("1", valor);
            utilDTO.setResulados(resultados);
        } catch (IllegalArgumentException e) {
            respuestaDTO.setCodigo("JPA003");//revisar el log     
            logger.error( new StringBuilder("ERROR JPA EN consultaUsaurioperfilPiloto: ")
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

}
