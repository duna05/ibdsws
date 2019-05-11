/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.daoimpl;

import com.bds.ws.dao.DelSurDAO;
import com.bds.ws.dao.NotificationServiceType;
import com.bds.ws.dto.AgenciaDTO;
import com.bds.ws.dto.BancoDTO;
import com.bds.ws.dto.DelSurDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.dto.UtilDTO;
import com.bds.ws.exception.DAOException;
import com.bds.ws.facade.IbBancosExcepcionesFacade;
import static com.bds.ws.util.BDSUtil.CODIGO_EXCEPCION_GENERICA;
import static com.bds.ws.util.BDSUtil.CODIGO_RESPUESTA_EXITOSO;
import static com.bds.ws.util.BDSUtil.CODIGO_RESPUESTA_EXITOSO_SP;
import static com.bds.ws.util.BDSUtil.CODIGO_SIN_RESULTADOS_JPA;
import static com.bds.ws.util.BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA;
import static com.bds.ws.util.BDSUtil.JNDI_ORACLE_9;
import com.bds.ws.util.DAOUtil;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.NoResultException;
import oracle.jdbc.OracleTypes;
import org.apache.log4j.Logger;

/**
 * Clase que implementa la interfaz DelSurDAO y contiene los metodos para
 * obtener listado de datos especificos
 *
 * @author rony.rodriguez
 */
@Named("DelSurDAO")
@Stateless
@NotificationServiceType(NotificationServiceType.ServiceType.SERVICES)
public class DelSurDAOImpl extends DAOUtil implements DelSurDAO {

    /**
     * Log de sistema
     */
    private static final Logger logger = Logger.getLogger(TddDAOImpl.class.getName());

    @EJB
    private IbBancosExcepcionesFacade bancosExcepcionesFacade;

    /**
     * Metodo que obtiene el detalle de los nombres de las agencias y estados.
     *
     * @param codigoCanal codigo del canal
     * @return DelSurDTO Lisdtado de las agencias
     */
    @Override
    public DelSurDTO obtenerDatosEstadoAgencias(String codigoCanal) {
        DelSurDTO delsur = new DelSurDTO();
        RespuestaDTO respuesta = new RespuestaDTO();
        List<AgenciaDTO> agencias = new ArrayList<>();
        AgenciaDTO agencia = null;

        try {

            if (codigoCanal == null || codigoCanal.isEmpty() || codigoCanal.equalsIgnoreCase("")) {
                respuesta.setCodigo("DAO008");
                throw new DAOException("DAO008");
            }

            respuesta = this.conectarJNDI(JNDI_ORACLE_9, codigoCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            respuesta = this.generarQuery("IB_K_DELSUR ", "IB_P_DATOS_ESTADO_AGENCIAS", 2, codigoCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }

            //seteo de parametros de entrada y/o salida            
            statement.setString(1, codigoCanal);
            statement.registerOutParameter(2, OracleTypes.CURSOR);
            this.ejecuto = statement.execute();

            objResultSet = (ResultSet) statement.getObject(2);
            while (objResultSet.next()) {
                agencia = new AgenciaDTO();
                agencia.setCodigoAgencia(this.getCharSetOracle9(objResultSet, "codigoAgencia"));
                agencia.setNombreAgencia(this.getCharSetOracle9(objResultSet, "nombreAgencia"));
                agencia.setNombreEstado(this.getCharSetOracle9(objResultSet, "nombreEstado"));
                agencias.add(agencia);
            }

            delsur.setAgencias(agencias);

            if (agencias.isEmpty()) {
                throw new NoResultException();
            }

        } catch (NoResultException e) {
            respuesta.setCodigoSP(CODIGO_SIN_RESULTADOS_JPA);//revisar el log
            respuesta.setTextoSP(TEXTO_SIN_RESULTADOS_JPA);
            logger.error( new StringBuilder("ERROR DAO EN obtenerDatosEstadoAgencias: ")
                    .append("-CH-").append(codigoCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN obtenerDatosEstadoAgencias: ")
                    .append("-CH-").append(codigoCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        } finally {
            delsur.setRespuesta(respuesta);
            this.cerrarConexion(codigoCanal);
        }
//        if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN obtenerDatosEstadoAgencias: ")
//                    .append("-CH-").append(codigoCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return delsur;
    }

    /**
     * Metodo para obtener el listado de bancos
     *
     * @param codigoCanal codigo del canal
     * @return listado de bancos
     */
    @Override
    public DelSurDTO listadoBancos(String codigoCanal) {
        DelSurDTO delsur = new DelSurDTO();
        RespuestaDTO respuesta = new RespuestaDTO();
        List<BancoDTO> bancos = new ArrayList<>();

        try {

            if (codigoCanal == null || codigoCanal.isEmpty() || codigoCanal.equalsIgnoreCase("")) {
                respuesta.setCodigo("DAO008");
                delsur.setRespuesta(respuesta);
                throw new DAOException("DAO008");
            }

            respuesta = this.conectarJNDI(JNDI_ORACLE_9, codigoCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                delsur.setRespuesta(respuesta);
                throw new DAOException();
            }
            respuesta = this.generarQuery("IB_K_DELSUR ", "IB_P_OBTENER_LISTADO_BANCOS", 2, codigoCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                delsur.setRespuesta(respuesta);
                throw new DAOException();
            }

            //seteo de parametros de entrada y/o salida            
            statement.setString(1, codigoCanal);
            statement.registerOutParameter(2, OracleTypes.CURSOR);
            this.ejecuto = statement.execute();

            objResultSet = (ResultSet) statement.getObject(2);
            while (objResultSet.next()) {
                BancoDTO banco = new BancoDTO();
                banco.setCodigoBanco(this.getCharSetOracle9(objResultSet, "codbanco"));
                banco.setNombreBanco(this.getCharSetOracle9(objResultSet, "descripcion"));
                bancos.add(banco);
            }
            if (bancos.isEmpty()) {
                throw new NoResultException();
            }
            delsur.setBancos(bancos);

            delsur.setRespuesta(respuesta);
        } catch (SQLException e) {
            respuesta.setCodigoSP(CODIGO_SIN_RESULTADOS_JPA);//revisar el log
            respuesta.setTextoSP(TEXTO_SIN_RESULTADOS_JPA);
            logger.error( new StringBuilder("ERROR DAO EN listadoBancos: ")
                    .append("-CH-").append(codigoCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN listadoBancos: ")
                    .append("-CH-").append(codigoCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        } finally {
            delsur.setRespuesta(respuesta);
            this.cerrarConexion(codigoCanal);
        }
//        if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN listadoBancos: ")
//                    .append("-CH-").append(codigoCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return delsur;
    }

    /**
     * Metodo para verificar que el banco no posee restricciones internas
     *
     * @param codbanco codigo del banco
     * @param nombreCanal nombre del canal
     * @return RespuestaDTO
     */
    @Override
    public UtilDTO verificarBloqueBanco(String codbanco, String nombreCanal) {
        UtilDTO utilDTO = new UtilDTO();
        RespuestaDTO respuesta = new RespuestaDTO();
        try {

            if (codbanco == null || codbanco.isEmpty() || codbanco.equalsIgnoreCase("")
                    || nombreCanal == null || nombreCanal.isEmpty() || nombreCanal.equalsIgnoreCase("")) {
                respuesta.setCodigo("DAO008");
                throw new DAOException("DAO008");
            }

            utilDTO = bancosExcepcionesFacade.verificarBloqueBanco(codbanco, nombreCanal);

            if (!utilDTO.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new Exception();
            }

        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO verificarBloqueBanco: ")
                    .append("BCO-").append(codbanco)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            utilDTO.getRespuesta().setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }
//        if (utilDTO.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN verificarBloqueBanco: ")
//                    .append("BCO-").append(codbanco)
//                    .append("-CH-").append(nombreCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return utilDTO;
    }

    /**
     * Metodo para obtenar las fechas de los dias feriados dado el numero de
     * meses requerido
     *
     * @param cantMeses Integer secuencia de convenio a consultar
     * @param iCodExtCanal String condigo del canal para realizar la consulta
     * @return List<String> lista con las fechas de feriados formato dd/MM/yyyy
     */
    @Override
    public Boolean isDiaFeriado(String dia, String iCodExtCanal) {
        RespuestaDTO respuesta = new RespuestaDTO();
        Map resultados = new HashMap();
        UtilDTO utilDTO = new UtilDTO();
        Boolean salida = false;

        try {
            respuesta = this.conectarJNDI(JNDI_ORACLE_9, iCodExtCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }

            respuesta = this.generarQuery("IB_K_PAGOS_GENERALES_PJ", "IB_P_VALIDA_DIA_FERIADOS", 4, iCodExtCanal);

            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            //seteo de parametros de entrada y/o salida
            statement.setInt(1, 0);
            statement.setString(2, dia);
            statement.setString(3, iCodExtCanal);
            statement.registerOutParameter(4, OracleTypes.CURSOR);

            this.ejecuto = statement.execute();

            objResultSet = (ResultSet) statement.getObject(4);

            if (objResultSet.next()) {
                String fecha = objResultSet.getString("FECHA");
                //el procedimiento devuelve un null si es feriado
                salida = !(fecha.equals(null));
            }
//            logger.error( new StringBuilder("EXITO DAO EN buscarDiasFeriados: ")
//                    .append("CTA-").append("")
//                    .append("-CH-").append(iCodExtCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
            respuesta.setCodigo(CODIGO_RESPUESTA_EXITOSO);
            respuesta.setCodigoSP(CODIGO_RESPUESTA_EXITOSO_SP);
            utilDTO.setRespuesta(respuesta);
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN buscarDiasFeriados: ")
                    .append("CTA-").append("")
                    .append("-CH-").append(iCodExtCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString(), e);
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);
            utilDTO.setRespuesta(respuesta);
        } finally {
            this.cerrarConexion(iCodExtCanal);
        }

        return salida;
    }

    /**
     * Metodo para obtenar las fechas de los dias feriados dado el numero de
     * meses requerido
     *
     * @param cantMeses Integer secuencia de convenio a consultar
     * @param iCodExtCanal String condigo del canal para realizar la consulta
     * @return List<String> lista con las fechas de feriados formato dd/MM/yyyy
     */
    @Override
    public List<String> buscarDiasFeriados(Integer cantMeses, String iCodExtCanal) {
        RespuestaDTO respuesta = new RespuestaDTO();
        Map resultados = new HashMap();
        UtilDTO utilDTO = new UtilDTO();
        List<String> listaFeriados = new ArrayList<String>();

        try {
            respuesta = this.conectarJNDI(JNDI_ORACLE_9, iCodExtCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }

            respuesta = this.generarQuery("IB_K_PAGOS_GENERALES_PJ", "IB_P_VALIDA_DIA_FERIADOS", 4, iCodExtCanal);

            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            //seteo de parametros de entrada y/o salida
            statement.setInt(1, cantMeses);
            statement.setString(2, null);
            statement.setString(3, iCodExtCanal);
            statement.registerOutParameter(4, OracleTypes.CURSOR);

            this.ejecuto = statement.execute();

            objResultSet = (ResultSet) statement.getObject(4);

            while (objResultSet.next()) {
                String cuenta = objResultSet.getString("FECHA");
                listaFeriados.add(cuenta);
            }
//            logger.error( new StringBuilder("EXITO DAO EN buscarDiasFeriados: ")
//                    .append("CTA-").append("")
//                    .append("-CH-").append(iCodExtCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
            respuesta.setCodigo(CODIGO_RESPUESTA_EXITOSO);
            respuesta.setCodigoSP(CODIGO_RESPUESTA_EXITOSO_SP);
            utilDTO.setRespuesta(respuesta);
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN buscarDiasFeriados: ")
                    .append("CTA-").append("")
                    .append("-CH-").append(iCodExtCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString(), e);
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);
            utilDTO.setRespuesta(respuesta);
        } finally {
            this.cerrarConexion(iCodExtCanal);
        }

        return listaFeriados;
    }

    /**
     * Metodo para obtener el listado de bancos afiliados a Switch7B
     *
     * @param codigoCanal codigo del canal
     * @return listado de bancos
     */
    @Override
    public DelSurDTO listadoBancosSwitch7B(String codigoCanal) {
        DelSurDTO delsur = new DelSurDTO();
        RespuestaDTO respuesta = new RespuestaDTO();
        List<BancoDTO> bancos = new ArrayList<>();

        try {

            if (codigoCanal == null || codigoCanal.isEmpty() || codigoCanal.equalsIgnoreCase("")) {
                respuesta.setCodigo("DAO008");
                delsur.setRespuesta(respuesta);
                throw new DAOException("DAO008");
            }

            respuesta = this.conectarJNDI(JNDI_ORACLE_9, codigoCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                delsur.setRespuesta(respuesta);
                throw new DAOException();
            }
            respuesta = this.generarQuery("IB_K_P2P", "IB_P_BANCOS_P2P", 1, codigoCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                delsur.setRespuesta(respuesta);
                throw new DAOException();
            }

            //seteo de parametros de entrada y/o salida            
//            statement.setString(1, codigoCanal);
            statement.registerOutParameter(1, OracleTypes.CURSOR);
            this.ejecuto = statement.execute();

            objResultSet = (ResultSet) statement.getObject(1);
            while (objResultSet.next()) {
                BancoDTO banco = new BancoDTO();
                banco.setCodigoBanco(String.valueOf(objResultSet.getString("codigo_financiera")));
                banco.setNombreBanco(this.getCharSetOracle9(objResultSet, "nombre"));
                bancos.add(banco);
            }
            if (bancos.isEmpty()) {
                throw new NoResultException();
            }
            delsur.setBancos(bancos);

            delsur.setRespuesta(respuesta);
        } catch (SQLException e) {
            respuesta.setCodigoSP(CODIGO_SIN_RESULTADOS_JPA);//revisar el log
            respuesta.setTextoSP(TEXTO_SIN_RESULTADOS_JPA);
            logger.error( new StringBuilder("ERROR DAO EN listadoBancosSwitch7B: ")
                    .append("-CH-").append(codigoCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN listadoBancosSwitch7B: ")
                    .append("-CH-").append(codigoCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        } finally {
            delsur.setRespuesta(respuesta);
            this.cerrarConexion(codigoCanal);
        }
//        if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN listadoBancosSwitch7B: ")
//                    .append("-CH-").append(codigoCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return delsur;
    }

    @Override
    public UtilDTO validarUsuarioPilotoP2P(String cedula, String idCanal, String codigoCanal) {
        UtilDTO util = new UtilDTO();
        RespuestaDTO respuesta = new RespuestaDTO();
        Map<String,Boolean> resultado = new HashMap<String, Boolean>();
        try {

            if (codigoCanal == null || codigoCanal.isEmpty() || codigoCanal.equalsIgnoreCase("")) {
                respuesta.setCodigo("DAO008");
                util.setRespuesta(respuesta);
                throw new DAOException("DAO008");
            }

            respuesta = this.conectarJNDI(JNDI_ORACLE_9, codigoCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                util.setRespuesta(respuesta);
                throw new DAOException();
            }
            respuesta = this.generarQuery("IB_K_P2P", "IB_P_VALIDA_USUARIO_P2P", 3, codigoCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                util.setRespuesta(respuesta);
                throw new DAOException();
            }

            //seteo de parametros de entrada y/o salida            
            statement.setString(1, cedula);
            statement.registerOutParameter(2, OracleTypes.VARCHAR);
            statement.registerOutParameter(3, OracleTypes.INTEGER);
            this.ejecuto = statement.execute();

            if(statement.getInt(3) != 1){
                respuesta.setCodigoSP(CODIGO_RESPUESTA_FALLIDA_SP);
                respuesta.setTextoSP(DESCRIPCION_RESPUESTA_FALLIDA_SP);
                resultado.put(VALIDO, false); 
            }else{
                if(statement.getString(2).equals("V")){
                    resultado.put(VALIDO, true);
                }else{
                    resultado.put(VALIDO, false);                    
                }                
            }
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN validarUsuarioP2P: ")
                    .append("-CH-").append(codigoCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
            resultado.put(VALIDO, false); 
        } finally {
            util.setRespuesta(respuesta);
            util.setResulados(resultado);
            this.cerrarConexion(codigoCanal);
        }
//        if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN listadoBancosSwitch7B: ")
//                    .append("-CH-").append(codigoCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return util;
    }
}
