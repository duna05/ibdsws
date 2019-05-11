/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.daoimpl;

import com.bds.ws.dao.IbCtasEmpresaUsuarioPjDAO;
import com.bds.ws.dto.IbCtaEmpresaPjDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.facade.IbCtasEmpresaUsuarioPjFacade;
import com.bds.ws.model.IbCtasEmpresaUsuarioPj;
import com.bds.ws.util.BDSUtil;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Named;
import org.apache.log4j.Logger;

/**
 *
 * @author cesar.mujica
 */
@Named("IbCtasEmpresaUsuarioPjDAO")
@Stateless
public class IbCtasEmpresaUsuarioPjDAOImpl extends BDSUtil implements IbCtasEmpresaUsuarioPjDAO {

    @EJB
    private IbCtasEmpresaUsuarioPjFacade ctasEmpresaUsuarioPjFacade;

    /**
     * Log de sistema.
     */
    private static final Logger logger = Logger.getLogger(IbCtasEmpresaUsuarioPjDAOImpl.class.getName());

    /**
     * Metodo que se +encarga de consultar las cuentas asociadas a una empresa y un usario
     * Oracle 11++
     *
     *
     * @param idUsuario id del usuario en IB
     * @param idEmpresa
     * @param idCanal codigo interno del canal
     * @param codigoCanal codigo de canal en el CORE del banco
     * @return Objeto de Respuesta con el resultado de la transaccion.
     */
    @Override
    public IbCtaEmpresaPjDTO obtenerIbCtasEmpresaUsuarioPj(String idUsuario, String idEmpresa, String idCanal, String codigoCanal) {
        RespuestaDTO respuestaDTO = new RespuestaDTO();
        IbCtaEmpresaPjDTO cuentasEmp = new IbCtaEmpresaPjDTO();
        try {
            cuentasEmp.setCuentasEmpresaPj(new ArrayList<>());
            List<IbCtasEmpresaUsuarioPj> ctasEmpUsr = ctasEmpresaUsuarioPjFacade.obtenerIbCtasEmpresaUsuarioPj(new BigDecimal(idUsuario), new BigDecimal(idEmpresa));
            if (!ctasEmpUsr.isEmpty()) {
                for (IbCtasEmpresaUsuarioPj ctaTemp : ctasEmpUsr) {
                    cuentasEmp.getCuentasEmpresaPj().add(ctaTemp.getIdCtaEmpresa());
                }
            }
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR JPA EN obtenerIbCtasEmpresaUsuarioPj: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP- ").append(e.getCause()).toString());
            respuestaDTO.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        } finally {
            cuentasEmp.setRespuestaDTO(respuestaDTO);
        }
        return cuentasEmp;
    }

}
