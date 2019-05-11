/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.daoimpl;

import com.bds.ws.dao.IbFideicomisoDetPjDAO;
import com.bds.ws.dto.IbFideicomisoDetPjDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.dto.UtilDTO;
import com.bds.ws.facade.IbFideicomisoDetPjFacade;
import com.bds.ws.model.IbFideicomisoDetPj;
import com.bds.ws.util.BDSUtil;
import static com.bds.ws.util.BDSUtil.CODIGO_EXCEPCION_GENERICA;
import static com.bds.ws.util.BDSUtil.CODIGO_RESPUESTA_EXITOSO;
import static com.bds.ws.util.BDSUtil.CODIGO_SIN_RESULTADOS_JPA;
import static com.bds.ws.util.BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA;
import static com.bds.ws.util.BDSUtil.EMPRESAS;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.NoResultException;
import org.apache.log4j.Logger;

/**
 *
 * @author robinson.rodriguez
 */
@Named("IbFideicomisoDetPjDAO")
@Stateless
public class IbFideicomisoDetPjDAOImpl extends BDSUtil implements IbFideicomisoDetPjDAO{

    @EJB
    private IbFideicomisoDetPjFacade ibFideicomisoDetPjFacade;

    private static final Logger logger = Logger.getLogger(IbFideicomisoDetPjDAOImpl.class.getName());
    
    @Override
    public UtilDTO insertarIbFideicomisoDetPj(IbFideicomisoDetPj ibFideicomisoDetPj, String codigoCanal) {
         RespuestaDTO respuesta = new RespuestaDTO();
        UtilDTO utilDTO = new UtilDTO();
        Map resultado = new HashMap();
        try {
            if(ibFideicomisoDetPj != null){
                respuesta = this.ibFideicomisoDetPjFacade.crearPJ(ibFideicomisoDetPj);
                if(!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)){
                    throw new Exception("ERROR AL INSERTAR insertarIbFideicomisoDetPj");
                }
                resultado.put("ibFideicomisoDetPj", ibFideicomisoDetPj);
                utilDTO.setRespuesta(respuesta);
                utilDTO.setResulados(resultado);
            }
        } 
        catch (NoResultException e) {
            logger.error( new StringBuilder("ERROR JPA EN insertarIbFideicomisoDetPj: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            respuesta.setCodigo(CODIGO_SIN_RESULTADOS_JPA);
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN insertarIbFideicomisoDetPj: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(),e);
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }
        return utilDTO;
    }

    @Override
    public IbFideicomisoDetPjDTO listarIbFideicomisoDetPjDTO(BigDecimal idfideicomiso, Short estatusCarga, String idCanal, String codigoCanal) {
        RespuestaDTO respuestaDTO = new RespuestaDTO();
        IbFideicomisoDetPjDTO ibFideicomisoDetPjDTO = new IbFideicomisoDetPjDTO();
        Map resultado = new HashMap();

        try {
            List<IbFideicomisoDetPj> detalleFideicomiso = ibFideicomisoDetPjFacade
                    .listarFideicomisoDetalle(idfideicomiso);
            if (!detalleFideicomiso.isEmpty()) {
                ibFideicomisoDetPjDTO.setIbFideicomisoDetPjsList(detalleFideicomiso);
                resultado.put(EMPRESAS, ibFideicomisoDetPjDTO);
                respuestaDTO.setCodigo(CODIGO_RESPUESTA_EXITOSO); // agrego DN
                ibFideicomisoDetPjDTO.setRespuestaDTO(respuestaDTO);// agrego DN
            } else {
                ibFideicomisoDetPjDTO.setIbFideicomisoDetPjsList(new ArrayList<IbFideicomisoDetPj>());
                resultado.put(EMPRESAS, ibFideicomisoDetPjDTO);
            }
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR AL CONSULTAR listarIbFideicomisoDetPj: ")
                    .append("ID-CH- ").append(idCanal)
                    .append("-CH- ").append(codigoCanal)
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString());
            respuestaDTO.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }
        return ibFideicomisoDetPjDTO;
    }
}
