/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.daoimpl;

import com.bds.ws.dao.IbErroresNominaDetallePjDAO;
import com.bds.ws.dto.IbErroresNominaDetallePjDTO;
import com.bds.ws.dto.UtilDTO;
import com.bds.ws.facade.IbErroresNominaDetallePjFacade;
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
 * @author juan.vasquez
 */
@Named("IbErroresNominaDetallePjDAO")
@Stateless
public class IbErroresNominaDetallePjDaoImpl extends BDSUtil implements IbErroresNominaDetallePjDAO{
    
     /**
     * Log de sistema.
     */
    private static final Logger logger = Logger.getLogger(IbErroresNominaDetallePjDaoImpl.class.getName());
    
    @EJB
    private IbErroresNominaDetallePjFacade ibErroresNominaDetallePjFacade;

    @Override
    public UtilDTO insertarIbErroresNominaDetallePj(IbErroresNominaDetallePjDTO ibErroresNominaDetallePjDTO) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public UtilDTO consultarIbErroresNominaDetallePjById(BigDecimal idError) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public UtilDTO consultarIbErroresNominaDetallePjByPago(BigDecimal idPago) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IbErroresNominaDetallePjDTO listarCargaNominaErroresByPago(BigDecimal idpago, String codigoCanal) {
         IbErroresNominaDetallePjDTO ibErroresNominaDetallePjDTO = new IbErroresNominaDetallePjDTO();
        try {
            ibErroresNominaDetallePjDTO = this.ibErroresNominaDetallePjFacade.listarCargaNominaErrores(idpago);            
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO en listarIbCargaNominaDetallePj: ")
                    .append("-CH- ").append(codigoCanal)
                    .append("-DT- ").append(new Date())
                    .append("-STS- ").append(DESCRIPCION_RESPUESTA_FALLIDA)
                        .append("-EXCP- ").append(e.getCause()).toString(),e);
            ibErroresNominaDetallePjDTO.getRespuestaDTO().setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }        
        return ibErroresNominaDetallePjDTO;
   }
    
}
