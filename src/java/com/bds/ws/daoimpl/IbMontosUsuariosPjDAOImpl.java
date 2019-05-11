/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.daoimpl;

import com.bds.ws.dao.IbMontosUsuariosPjDAO;
import com.bds.ws.dao.NotificationServiceType;
import com.bds.ws.dto.IbMontosUsuariosPjDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.dto.UtilDTO;
import com.bds.ws.facade.IbMontosUsuariosPjFacade;
import com.bds.ws.model.IbEmpresas;
import com.bds.ws.model.IbMontosUsuariosPj;
import com.bds.ws.model.IbUsuariosPj;
import static com.bds.ws.util.BDSUtil.CODIGO_EXCEPCION_GENERICA;
import static com.bds.ws.util.BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA;
import com.bds.ws.util.DAOUtil;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Named;
import org.apache.log4j.Logger;

/**
 *
 * @author luis.perez
 */
@Named("IbMontosUsuariosPjDAOImpl")
@Stateless
@NotificationServiceType(NotificationServiceType.ServiceType.SERVICES)
public class IbMontosUsuariosPjDAOImpl extends DAOUtil implements IbMontosUsuariosPjDAO  {
    /**
     * Log de sistema
     */
    private static final Logger logger = Logger.getLogger(IbMontosUsuariosPjDAOImpl.class.getName());
    
    @EJB
    IbMontosUsuariosPjFacade ibMontosUsuariosFacade;
    /**
     * Metodo que inserta las reglas de aprobacion por moto BD Oracle 11++
     *
     *
     * @param idUsuario codigo del usuario en IB
     * @param idEmpresa codigo de la empresa en IB
     * @param montoHasta monto final permitido para la regla
     * @param idCanal codigo interno del canal
     * @param codigoCanal codigo de canal en el CORE del banco
     * @return the com.bds.ws.dto.UtilDTO
     */
    @Override
    public UtilDTO insertarReglasAprobacionPj(String idUsuario, String idEmpresa, BigDecimal montoHasta, String idCanal, String codigoCanal) {
        RespuestaDTO respuesta = new RespuestaDTO();
        UtilDTO utilDTO = new UtilDTO();
        Map resultado = new HashMap();
        try {
            IbMontosUsuariosPj ibMontosUsuariosPj = new IbMontosUsuariosPj();
            //SE SETEA EL ID PERO EN REALIDAD ES CONTROLADO A TRAVES DE TRIGER EN BD
            ibMontosUsuariosPj.setId(new BigDecimal(BigInteger.ZERO));
            ibMontosUsuariosPj.setIdEmpresa(new IbEmpresas(new BigDecimal(idEmpresa)));
            ibMontosUsuariosPj.setIdUsuarioPj(new IbUsuariosPj(new BigDecimal(idUsuario)));
            ibMontosUsuariosPj.setMontoHasta(montoHasta);
            ibMontosUsuariosFacade.crearPJ(ibMontosUsuariosPj);
            utilDTO.setResulados(resultado);
            utilDTO.setRespuesta(respuesta);
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN insertarReglasAprobacionPj: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
            utilDTO.setResulados(resultado);
            utilDTO.setRespuesta(respuesta);
        }
        return utilDTO;
    }
    
    /**
     * Metodo que modifica las reglas de aprobacion por moto BD Oracle 11++
     *
     *
     * @param idUsuario codigo del usuario en IB
     * @param idEmpresa codigo de la empresa en IB
     * @param montoHasta monto final permitido para la regla
     * @param idCanal codigo interno del canal
     * @param codigoCanal codigo de canal en el CORE del banco
     * @return Objeto de Respuesta con el resultado de la transaccion.
     */
    @Override
    public IbMontosUsuariosPjDTO modificarReglasAprobacionPj(String idUsuario, String idEmpresa, BigDecimal montoHasta, String idCanal, String codigoCanal) {
        RespuestaDTO respuestaDTO = new RespuestaDTO();
        IbMontosUsuariosPj    ibMontosUsuariosPj;
        IbMontosUsuariosPjDTO ibMontosUsuariosPjDTO = new IbMontosUsuariosPjDTO();
        ibMontosUsuariosPjDTO.setRespuesta(respuestaDTO);
        try {
            ibMontosUsuariosPj = ibMontosUsuariosFacade.consultarDatosUsuarioMontosAutorizado(new BigDecimal(idUsuario), new BigDecimal(idEmpresa));
            if(ibMontosUsuariosPj != null){
                ibMontosUsuariosPj.setMontoHasta(montoHasta);
                ibMontosUsuariosFacade.modificarPj(ibMontosUsuariosPj);
                ibMontosUsuariosPjDTO.setIbMontoUsuarioPj(ibMontosUsuariosPj);
            }else {
                logger.error( new StringBuilder("ERROR DAO EN modificarReglasAprobacionPj: ")
                        .append("-CH- ").append(codigoCanal)
                        .append("-DT- ").append(new Date())
                        .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                        .append("-EXCP- ").append(CODIGO_SIN_RESULTADOS_JPA).toString());
                ibMontosUsuariosPjDTO.getRespuesta().setCodigo(CODIGO_SIN_RESULTADOS_JPA);
            }
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN modificarReglasAprobacionPj: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            ibMontosUsuariosPjDTO.getRespuesta().setCodigo(CODIGO_EXCEPCION_GENERICA);
        }
        return ibMontosUsuariosPjDTO;
    }
}
