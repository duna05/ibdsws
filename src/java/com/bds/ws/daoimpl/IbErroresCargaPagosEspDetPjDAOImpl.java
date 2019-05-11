/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.daoimpl;

import com.bds.ws.dao.IbErroresCargaPagosEspDetPjDAO;
import com.bds.ws.dto.IbErroresCargaPagosEspDetPjDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.facade.IbErroresCargaPagosEspDetPjFacade;
import com.bds.ws.util.BDSUtil;
import static com.bds.ws.util.BDSUtil.CODIGO_EXCEPCION_GENERICA;
import static com.bds.ws.util.BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA;
import java.math.BigDecimal;
import java.util.Date;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Named;
import org.apache.log4j.Logger;

/**
 *
 * @author robinson.rodriguez
 */
@Named("IbErroresCargaPagosEspDetPjDAO")
@Stateless
public class IbErroresCargaPagosEspDetPjDAOImpl extends BDSUtil implements IbErroresCargaPagosEspDetPjDAO {

    /**
     * Log de sistema.
     */
    private static final Logger logger = Logger.getLogger(IbErroresCargaPagosEspDetPjDAOImpl.class.getName());

    @EJB
    private IbErroresCargaPagosEspDetPjFacade ibErrCargaPagosEspDetPjFacade;

    @Override
    public IbErroresCargaPagosEspDetPjDTO listarErrCargaPagosEspDetPj(BigDecimal idPago, String idCanal, String codigoCanal) {
        IbErroresCargaPagosEspDetPjDTO ibErrCargaPagosEspDetPjDTO = new IbErroresCargaPagosEspDetPjDTO();
        try {
            ibErrCargaPagosEspDetPjDTO = this.ibErrCargaPagosEspDetPjFacade.listarIbErrCargaPagosEspDetPj(idPago);
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO en listarIbErrCargaPagosEspDetPj: ")
                    .append("ID-CH- ").append(idCanal)
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            if (ibErrCargaPagosEspDetPjDTO == null||ibErrCargaPagosEspDetPjDTO.getRespuestaDTO()==null) {
                ibErrCargaPagosEspDetPjDTO = new IbErroresCargaPagosEspDetPjDTO();
                ibErrCargaPagosEspDetPjDTO.setRespuestaDTO(new RespuestaDTO());
            }
            ibErrCargaPagosEspDetPjDTO.getRespuestaDTO().setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }

        return ibErrCargaPagosEspDetPjDTO;
    }

}
