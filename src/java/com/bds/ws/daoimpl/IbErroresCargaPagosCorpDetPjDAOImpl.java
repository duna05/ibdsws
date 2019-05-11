/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.daoimpl;

import com.bds.ws.dao.IbErroresCargaPagosCorpDetPjDAO;
import com.bds.ws.dto.IbErroresCargaPagosCorpDetPjDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.facade.IbErroresCargaPagosCorpDetPjFacade;
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
@Named("IbErroresCargaPagosCorpDetPjDAO")
@Stateless
public class IbErroresCargaPagosCorpDetPjDAOImpl extends BDSUtil implements IbErroresCargaPagosCorpDetPjDAO {

    /**
     * Log de sistema.
     */
    private static final Logger logger = Logger.getLogger(IbErroresCargaPagosCorpDetPjDAOImpl.class.getName());

    @EJB
    private IbErroresCargaPagosCorpDetPjFacade ibErrCargaPagosCorpDetPjFacade;

    @Override
    public IbErroresCargaPagosCorpDetPjDTO listarErrCargaPagosCorpDetPj(BigDecimal idPago, String idCanal, String codigoCanal) {
        IbErroresCargaPagosCorpDetPjDTO ibErrCargaPagosCorpDetPjDTO = new IbErroresCargaPagosCorpDetPjDTO();
        try {
            ibErrCargaPagosCorpDetPjDTO = this.ibErrCargaPagosCorpDetPjFacade.listarIbErrCargaPagosCorpDetPj(idPago);
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO en ibErrCargaPagosCorpDetPjDTO: ")
                    .append("ID-CH- ").append(idCanal)
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString(), e);
            if (ibErrCargaPagosCorpDetPjDTO == null||ibErrCargaPagosCorpDetPjDTO.getRespuestaDTO()==null) {
                ibErrCargaPagosCorpDetPjDTO = new IbErroresCargaPagosCorpDetPjDTO();
                ibErrCargaPagosCorpDetPjDTO.setRespuestaDTO(new RespuestaDTO());
            }
            ibErrCargaPagosCorpDetPjDTO.getRespuestaDTO().setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }

        return ibErrCargaPagosCorpDetPjDTO;
    }

}
