/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.daoimpl;

import com.bds.ws.dao.ConvenioPjDAO;
import com.bds.ws.dao.NotificationServiceType;
import com.bds.ws.dto.ConvenioPjDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.dto.UtilDTO;
import com.bds.ws.exception.DAOException;
import static com.bds.ws.util.BDSUtil.CODIGO_EXCEPCION_GENERICA;
import static com.bds.ws.util.BDSUtil.CODIGO_RESPUESTA_EXITOSO;
import static com.bds.ws.util.BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA;
import static com.bds.ws.util.BDSUtil.JNDI_ORACLE_9;
import com.bds.ws.util.DAOUtil;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.inject.Named;
import oracle.jdbc.OracleTypes;
import org.apache.log4j.Logger;

@Named("ConvenioDAO")
@Stateless
@NotificationServiceType(NotificationServiceType.ServiceType.SERVICES)
public class ConvenioDAOImpl extends DAOUtil implements ConvenioPjDAO {

    /**
     * Log de sistema
     */
    private static final Logger logger = Logger.getLogger(ConvenioDAOImpl.class.getName());

    @Override
    public List<ConvenioPjDTO> obtenerDetalleConvenio(String sCliente, String iCodExtCanal) {
        RespuestaDTO respuesta = new RespuestaDTO();
        Map resultados = new HashMap();
        UtilDTO utilDTO = new UtilDTO();
        List<ConvenioPjDTO> listaconvenio = new ArrayList<>();

        try {
            respuesta = this.conectarJNDI(JNDI_ORACLE_9, iCodExtCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }

            respuesta = this.generarQuery("IB_K_GENERALES_PJ", "IB_P_BUSCA_CONV_X_CLIEN", 5, iCodExtCanal);

            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            //seteo de parametros de entrada y/o salida
            
            statement.setString(1, sCliente);
            statement.setString(2, iCodExtCanal);
            statement.registerOutParameter(3, OracleTypes.CURSOR);
            statement.registerOutParameter(4, OracleTypes.VARCHAR);
            statement.registerOutParameter(5, OracleTypes.INTEGER);

            this.ejecuto = statement.execute();
            
            objResultSet = (ResultSet) statement.getObject(3);

            while (objResultSet.next()) {
                ConvenioPjDTO detalle = new ConvenioPjDTO();
                detalle.setSecuenciaConvenio(objResultSet.getBigDecimal("SECUENCIA_CONVENIO").toString());
                detalle.setNombreConvenio(this.getCharSetOracle9(objResultSet, "NOMBRE_CONVENIO"));
                detalle.setRespuesta(new RespuestaDTO());
                detalle.getRespuesta().setCodigoSP(CODIGO_RESPUESTA_EXITOSO_SP);
                listaconvenio.add(detalle);
            }
//            logger.error( new StringBuilder("EXITO DAO EN obtenerDetalleConvenio: ")
//                    .append("CTA-").append(sCliente)
//                    .append("-CH-").append(iCodExtCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN obtenerDetalleConvenio: ")
                    .append("CTA-").append(sCliente)
                    .append("-CH-").append(iCodExtCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString(), e);
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);
            ConvenioPjDTO convenio = new ConvenioPjDTO();
            convenio.setRespuesta(respuesta);
            listaconvenio.add(convenio);
        } finally {
            this.cerrarConexion(iCodExtCanal);
        }

        return listaconvenio;
    }

}
