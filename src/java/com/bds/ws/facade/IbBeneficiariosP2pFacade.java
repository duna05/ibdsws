/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.facade;

import com.bds.ws.dto.IbUsuariosP2PDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.model.IbBeneficiariosP2p;
import com.bds.ws.model.IbUsuarios;
import static com.bds.ws.util.BDSUtil.CODIGO_EXCEPCION_GENERICA;
import static com.bds.ws.util.BDSUtil.CODIGO_SIN_RESULTADOS_JPA;
import static com.bds.ws.util.BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA;
import static com.bds.ws.util.BDSUtil.TEXTO_SIN_RESULTADOS_JPA;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author ledwin.belen
 */
@Named("IbBeneficiariosP2pFacade")
@Stateless(name = "wsIbBeneficiariosP2pFacade")
public class IbBeneficiariosP2pFacade extends AbstractFacade<IbBeneficiariosP2p> {

    @PersistenceContext(unitName = "ibdsPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IbBeneficiariosP2pFacade() {
        super(IbBeneficiariosP2p.class);
    }
    
    /**
     * *
     * Metodo que realiza la consulta de beneficiarios Activos para el serv P2P por usuario.
     *
     * @param idUsuario id del usuario
     * @param nombreCanal codigo del canal
     * @return IbUsuariosCanalesDTO
     */
    public IbUsuariosP2PDTO consultarBeneficiariosP2P(IbUsuariosP2PDTO usuariosP2PDTO, String idUsuario, String nombreCanal) {

        RespuestaDTO respuestaDTO = new RespuestaDTO();
        List<IbBeneficiariosP2p> ibBeneficiariosP2p = new ArrayList<>();
        try {

            ibBeneficiariosP2p = (List<IbBeneficiariosP2p>) em.createQuery("SELECT c FROM IbBeneficiariosP2p c "
                    + "WHERE c.idUsuario = :idUsuario "
                    + "AND c.estatusBeneficiario = '1' ")
                    .setParameter("idUsuario", new IbUsuarios(new BigDecimal(idUsuario)))
                    .getResultList();

            if (ibBeneficiariosP2p == null || ibBeneficiariosP2p.isEmpty()) {
                throw new NoResultException();
            }

            //---------------------------------------------------------------------------------------
            usuariosP2PDTO.setIbBeneficiarioP2P(ibBeneficiariosP2p);

        } catch (NoResultException e) {
            respuestaDTO.setCodigoSP(CODIGO_SIN_RESULTADOS_JPA);//revisar el log 
            respuestaDTO.setTextoSP(TEXTO_SIN_RESULTADOS_JPA);
            logger.error( new StringBuilder("ERROR JPA EN consultarBeneficiariosP2P: ")
                    .append("USR-").append(idUsuario)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (IllegalArgumentException e) {
            respuestaDTO.setCodigo("JPA003");//revisar el log     
            logger.error( new StringBuilder("ERROR JPA EN consultarBeneficiariosP2P: ")
                    .append("USR-").append(idUsuario)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (Exception e) {
            respuestaDTO.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
            usuariosP2PDTO.setIbBeneficiarioP2P(ibBeneficiariosP2p);

        }
//        usuariosP2PDTO.setRespuesta(respuestaDTO);
        return usuariosP2PDTO;
    }
    
}
