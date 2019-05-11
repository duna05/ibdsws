/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.daoimpl;

import com.bds.ws.dao.FideicomisoPjDAO;
import com.bds.ws.dao.NotificationServiceType;
import com.bds.ws.dto.FideicomisoPjDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.enumerated.FideicomisoEnum;
import com.bds.ws.exception.DAOException;
import com.bds.ws.facade.IbEstatusPagosPjFacade;
import com.bds.ws.model.IbEstatusPagosPj;
import static com.bds.ws.util.BDSUtil.CHARSET_ORACLE_9;
import static com.bds.ws.util.BDSUtil.CODIGO_EXCEPCION_GENERICA;
import static com.bds.ws.util.BDSUtil.CODIGO_RESPUESTA_EXITOSO;
import static com.bds.ws.util.BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA;
import static com.bds.ws.util.BDSUtil.JNDI_ORACLE_9;
import com.bds.ws.util.DAOUtil;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Named;
import oracle.jdbc.OracleTypes;
import org.apache.log4j.Logger;

/**
 *
 * @author robinson.rodriguez
 */
@Named("FideicomisoPjDAO")
@Stateless
@NotificationServiceType(NotificationServiceType.ServiceType.SERVICES)
public class FideicomisoPjDAOImpl extends DAOUtil implements FideicomisoPjDAO {

    private static final Logger logger = Logger.getLogger(FideicomisoPjDAOImpl.class.getName());

    @EJB
    private IbEstatusPagosPjFacade ibEstatusPagosPjFacade;

    @Override
    public FideicomisoPjDTO listarFideicomisoPj(String cdClienteAbanks, String idFideicomiso, String idIndicadorTxt, String idCanal, String codigoCanal) {

        FideicomisoPjDTO fideicomisoCabeceraSalida = new FideicomisoPjDTO();
        List<FideicomisoPjDTO> fideicomisoCabeceraAporteList = new ArrayList<FideicomisoPjDTO>();
        List<FideicomisoPjDTO> fideicomisoCabeceraMovimientoList = new ArrayList<FideicomisoPjDTO>();

        RespuestaDTO respuesta = new RespuestaDTO();

        try {
            respuesta = this.conectarJNDI(JNDI_ORACLE_9, codigoCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }

            respuesta = this.generarQuery("FW_K_PROCESOS", "FW_P_CONSULTA_CAB", 5, codigoCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }

            //Consulta para los Aportes
            fideicomisoCabeceraAporteList = fideicomisoCabeceraList(cdClienteAbanks, idFideicomiso, FideicomisoEnum.APORTE.getDescripcion().trim());
            
            //Consultas para los Movimientos
            fideicomisoCabeceraMovimientoList = fideicomisoCabeceraList(cdClienteAbanks, idFideicomiso, FideicomisoEnum.MOVIMIENTO.getDescripcion().trim());

            //Lista temporal creada para la unión de los aportes y movimientos en una sola
            fideicomisoCabeceraAporteList.addAll(fideicomisoCabeceraMovimientoList);

            fideicomisoCabeceraSalida.setFideicomisoPjDTOList(fideicomisoCabeceraAporteList);
            fideicomisoCabeceraSalida.setRespuesta(respuesta);

        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN listarFideicomisoPj: ")
                    .append("ID-CH- ").append(idCanal)
                    .append("-CH-").append(codigoCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);
            fideicomisoCabeceraSalida.setRespuesta(respuesta);
        } finally {
            fideicomisoCabeceraSalida.setRespuesta(respuesta);
            this.cerrarConexion(codigoCanal);
        }

        return fideicomisoCabeceraSalida;

    }

    public List<FideicomisoPjDTO> fideicomisoCabeceraList(String cdClienteAbanks, String idFideicomiso, String idIndicadorTxt) {

        FideicomisoPjDTO fideicomisoCabecera = null;
        IbEstatusPagosPj ibEstatusPagosPj = new IbEstatusPagosPj();
        List<FideicomisoPjDTO> fideicomisoCabeceraList = new ArrayList<FideicomisoPjDTO>();

        try {

            //Setting de los parametros de entrada y salida
            statement.setString(1, cdClienteAbanks);
            statement.setString(2, idFideicomiso);
            statement.setString(3, idIndicadorTxt);
            statement.registerOutParameter(4, OracleTypes.CURSOR);
            statement.registerOutParameter(5, OracleTypes.VARCHAR);
            this.ejecuto = statement.execute();

            objResultSet = (ResultSet) statement.getObject(4);

            while (objResultSet.next()) {
                fideicomisoCabecera = new FideicomisoPjDTO();
                fideicomisoCabecera.setFechaRegistro(objResultSet.getDate("FECHA_HORA"));
                fideicomisoCabecera.setCantidadRegistro(objResultSet.getBigDecimal("REG_TOTAL"));
                fideicomisoCabecera.setCantidadRegistroProcesados(objResultSet.getBigDecimal("REG_ACEP_BASICO"));
                fideicomisoCabecera.setCantidadRegistroConErrores(objResultSet.getBigDecimal("REG_RECH_BASICO"));
                ibEstatusPagosPj = ibEstatusPagosPjFacade.find(objResultSet.getBigDecimal("ESTATUS"));
                fideicomisoCabecera.setEstatus(ibEstatusPagosPj);
                fideicomisoCabecera.setIndicador(idIndicadorTxt);
                fideicomisoCabeceraList.add(fideicomisoCabecera);
            }

        } catch (SQLException ex) {
            Logger.getLogger(FideicomisoPjDAOImpl.class.getName()).log(null, ex);
        }
        return fideicomisoCabeceraList;
    }
    
    /**
     * Metodo para validar si un codigo de plan pertenece a una empresa
     *
     * @param rifEmpresa
     * @param codigoPlan
     * @param idCanal id del canal a utilizar
     * @param codigoCanal id canal utilizado
     * @return IbFideicomisoPjDTO
     */
    @Override
    public boolean validarCodigoPlanEmpresa(String rifEmpresa, String codigoPlan, String idCanal, String codigoCanal){
        RespuestaDTO respuesta = new RespuestaDTO();
        try {
            respuesta = this.conectarJNDI(JNDI_ORACLE_9, codigoCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            respuesta = this.generarQuery("IB_K_FIDEICOMISO", "IB_P_VALIDA_PLAN_EMPRESA", 3, codigoCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            //seteo de parametros de entrada y/o salida            
            statement.setString(1, rifEmpresa);
            statement.setString(2, codigoPlan);
            statement.registerOutParameter(3, OracleTypes.VARCHAR);
            //Ejecutamos la consulta
            ejecuto = statement.execute();
            //Se captura la respuesta y se retorna true o false
            return new String(this.statement.getBytes(3), CHARSET_ORACLE_9).equals("1");
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN validarCodigoPlanEmpresa: ")
                    .append("-RIF-").append(rifEmpresa)
                    .append("-COD_PLAN-").append(codigoPlan)
                    .append("-CH-").append(codigoCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        } finally {
            this.cerrarConexion(codigoCanal);
        }
        return false;
    }
    
    /**
     * Metodo para validar si un codigo de plan esta activo
     *
     * @param codigoPlan
     * @param idCanal id del canal a utilizar
     * @param codigoCanal id canal utilizado
     * @return IbFideicomisoPjDTO
     */
    @Override
    public boolean validarEstatusPlan(String codigoPlan, String idCanal, String codigoCanal){
        RespuestaDTO respuesta = new RespuestaDTO();
        try {
            respuesta = this.conectarJNDI(JNDI_ORACLE_9, codigoCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            respuesta = this.generarQuery("IB_K_FIDEICOMISO", "IB_P_ESTATUS_PLAN", 2, codigoCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            //seteo de parametros de entrada y/o salida            
            statement.setString(1, codigoPlan);
            statement.registerOutParameter(2, OracleTypes.VARCHAR);
            //Ejecutamos la consulta
            ejecuto = statement.execute();
            //Se captura la respuesta y se retorna true o false
            return new String(this.statement.getBytes(2), CHARSET_ORACLE_9).equals("1");
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN validarEstatusPlan: ")
                    .append("-COD_PLAN-").append(codigoPlan)
                    .append("-CH-").append(codigoCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        } finally {
            this.cerrarConexion(codigoCanal);
        }
        return false;
    }
}
