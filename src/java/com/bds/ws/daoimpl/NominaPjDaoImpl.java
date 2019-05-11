/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.daoimpl;

import com.bds.ws.dao.DelSurDAO;
import com.bds.ws.dao.NominaPjDAO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.dto.UtilDTO;
import com.bds.ws.exception.DAOException;
import static com.bds.ws.util.BDSUtil.CODIGO_EXCEPCION_GENERICA;
import static com.bds.ws.util.BDSUtil.CODIGO_RESPUESTA_EXITOSO;
import static com.bds.ws.util.BDSUtil.CODIGO_RESPUESTA_EXITOSO_SP;
import static com.bds.ws.util.BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA;
import static com.bds.ws.util.BDSUtil.JNDI_ORACLE_9;
import com.bds.ws.util.DAOUtil;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Named;
import oracle.jdbc.OracleTypes;
import org.apache.log4j.Logger;

/**
 *
 * @author juan.vasquez
 */
@Named("IbNominaPjDAO")
@Stateless
public class NominaPjDaoImpl extends DAOUtil implements NominaPjDAO{

    
    /**
     * Log de sistema.
     */
    private static final Logger logger = Logger.getLogger(NominaPjDaoImpl.class.getName());

    @EJB
    private DelSurDAO delsurDAO;

    
    /**
     * Metodo para validar si un cliente es excluido o no
     *
     * @param codCliente String codigo del cliente a consultar
     * @param secuenciaConvenio Integer secuencia de convenio a consultar
     * @param iCodExtCanal String condigo del canal para realizar la consulta
     * @return UtilDTO indica si la operacion se realizo de manera exitosa o no y contiene el dato de respuesta
     * en el map con key esExcluido
     */
    @Override
    public UtilDTO validarClienteExcluido(String codCliente, Integer secuenciaConvenio, String iCodExtCanal) {
        RespuestaDTO respuesta = new RespuestaDTO();
        Map resultados = new HashMap();
        UtilDTO utilDTO = new UtilDTO();

        try {
            respuesta = this.conectarJNDI(JNDI_ORACLE_9, iCodExtCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }

            respuesta = this.generarQueryParaFunciones("IB_K_PAGOS_GENERALES_PJ", "IB_F_VALIDA_EXO_24H", 2, iCodExtCanal);

            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            //seteo de parametros de entrada y/o salida
            statement.registerOutParameter(1, OracleTypes.VARCHAR);
            statement.setString(2, codCliente);
            statement.setInt(3, secuenciaConvenio);

            statement.executeUpdate();
            
            resultados.put("esExcluido", statement.getString(1));
            utilDTO.setResulados(resultados);
            utilDTO.setRespuesta(respuesta);
//            logger.error( new StringBuilder("EXITO DAO EN validarClienteExcluido: ")
//                    .append("CTA-").append(codCliente)
//                    .append("-CH-").append(iCodExtCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
            respuesta.setCodigo(CODIGO_RESPUESTA_EXITOSO);
            respuesta.setCodigoSP(CODIGO_RESPUESTA_EXITOSO_SP);
            utilDTO.setRespuesta(respuesta);
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN validarClienteExcluido: ")
                    .append("CTA-").append(codCliente)
                    .append("-CH-").append(iCodExtCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString(), e);
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);
            utilDTO.setRespuesta(respuesta);
        } finally {
            this.cerrarConexion(iCodExtCanal);
        }

        return utilDTO;
    }

  /**
     * Metodo para obtenar las cuentas asociadas a un convenio
     *
     * @param secuenciaConvenio Integer secuencia de convenio a consultar
     * @param iCodExtCanal String condigo del canal para realizar la consulta
     * @return UtilDTO indica si la operacion se realizo de manera exitosa o no y contiene el dato de respuesta
     * en el map con key cuentaList, es una lista de tipo String
     */
    @Override
    public List<String> buscarCuentasPorConvenio(Integer secuenciaConvenio, String iCodExtCanal) {
        RespuestaDTO respuesta = new RespuestaDTO();
        Map resultados = new HashMap();
        UtilDTO utilDTO = new UtilDTO();
        List<String> cuentaList = new ArrayList<String>();

        try {
            respuesta = this.conectarJNDI(JNDI_ORACLE_9, iCodExtCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }

            respuesta = this.generarQuery("IB_K_GENERALES_PJ", "IB_P_BUSCA_CUENTA_CONVENIO", 5, iCodExtCanal);

            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            //seteo de parametros de entrada y/o salida
            statement.setInt(1, secuenciaConvenio);
            statement.setString(2, iCodExtCanal);
            statement.registerOutParameter(3, OracleTypes.CURSOR);
            statement.registerOutParameter(4, OracleTypes.VARCHAR);
            statement.registerOutParameter(5, OracleTypes.NUMBER);

            this.ejecuto = statement.execute();
            
            objResultSet = (ResultSet) statement.getObject(3);

            while (objResultSet.next()) {
                String cuenta = objResultSet.getString("NUMERO_CUENTA");
                cuentaList.add(cuenta);
            }
            resultados.put("cuentaList", cuentaList);
            utilDTO.setResulados(resultados);
            utilDTO.setRespuesta(respuesta);
//            logger.error( new StringBuilder("EXITO DAO EN buscarCuentasPorConvenio: ")
//                    .append("CTA-").append("")
//                    .append("-CH-").append(iCodExtCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
            respuesta.setCodigo(CODIGO_RESPUESTA_EXITOSO);
            respuesta.setCodigoSP(CODIGO_RESPUESTA_EXITOSO_SP);
            utilDTO.setRespuesta(respuesta);
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN buscarCuentasPorConvenio: ")
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

        return cuentaList;
    }

    /**
     * Metodo para obtenar las fechas de los dias feriados dado el numero de meses
     * requerido
     *
     * @param cantMeses Integer secuencia de convenio a consultar
     * @param iCodExtCanal String condigo del canal para realizar la consulta
     * @return List<String> lista con las fechas de feriados
     * formato dd/MM/yyyy
     */
    @Override
    public List<String> buscarDiasFeriados(Integer cantMeses, String iCodExtCanal) {
        return delsurDAO.buscarDiasFeriados(cantMeses, iCodExtCanal);
    }

    /**
     * Metodo para validar que el numero de cuenta introducido exista y pertenesca 
     * al benefeciario asignado
     *
     * @param nroCuenta String snumero de cuenta a validar
     * @param identBeneficiario String identificacion del benefeciario
     * @param iCodExtCanal String condigo del canal para realizar la consulta
     * @return UtilDTO indica si la operacion se realizo de manera exitosa o no y contiene el dato de respuesta
     * en el map con key perteneseCta, es una tipo String
     */
    @Override
    public UtilDTO validarCtaBeneficiario(String nroCuenta, String identBeneficiario, String iCodExtCanal) {
        RespuestaDTO respuesta = new RespuestaDTO();
        Map resultados = new HashMap();
        UtilDTO utilDTO = new UtilDTO();

        try {
            respuesta = this.conectarJNDI(JNDI_ORACLE_9, iCodExtCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }

            respuesta = this.generarQuery("IB_K_PAGOS_GENERALES_PJ", "IB_P_VALIDAR_IDENTF_CUENTA", 5, iCodExtCanal);

            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            //seteo de parametros de entrada y/o salida
            statement.setString(1, nroCuenta);
            statement.setString(2, identBeneficiario);
            statement.setString(3, iCodExtCanal);
            statement.setInt(4, OracleTypes.INTEGER);
            statement.registerOutParameter(5, OracleTypes.VARCHAR);

            this.ejecuto = statement.execute();
            
            resultados.put("perteneseCta", statement.getString(5));
            utilDTO.setResulados(resultados);
            utilDTO.setRespuesta(respuesta);
//            logger.error( new StringBuilder("EXITO DAO EN validarCtaBeneficiario: ")
//                    .append("CTA-").append("")
//                    .append("-CH-").append(iCodExtCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
            respuesta.setCodigo(CODIGO_RESPUESTA_EXITOSO);
            respuesta.setCodigoSP(CODIGO_RESPUESTA_EXITOSO_SP);
            utilDTO.setRespuesta(respuesta);
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN validarCtaBeneficiario: ")
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

        return utilDTO;
    }

    /*  validarConvenio
     * @param codigoClienteAbanks
     * @param codigoConvenio
     * @param codCuenta10
     * @param iCodExtCanal
     * @return resultado booleano que significa:
          "la cuenta pertenezca al convenio " Y 
          "el número de convenio corresponda al cliente".
     */    
    @Override
    public Boolean validarConvenio( String codigoClienteAbanks,
                                    BigDecimal codigoConvenio,
                                    String codCuenta10,
                                    String iCodExtCanal) {
        RespuestaDTO respuesta = new RespuestaDTO();
        boolean resultado = false;
        
        try {
            respuesta = this.conectarJNDI(JNDI_ORACLE_9, iCodExtCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }

            respuesta = this.generarQuery("IB_K_GENERALES_PJ", "IB_P_VALIDA_CONV_CTA_CLI", 6, iCodExtCanal);

            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            //seteo de parametros de entrada y/o salida
            statement.setString(1, codigoClienteAbanks);
            statement.setBigDecimal(2, codigoConvenio);
            statement.setBigDecimal(3, new BigDecimal(codCuenta10));
            statement.setString(4, iCodExtCanal);
            statement.registerOutParameter(5, OracleTypes.VARCHAR);
            statement.registerOutParameter(6, OracleTypes.INTEGER);

            this.ejecuto = statement.execute();
            resultado =  statement.getInt(6)  == 1 ? statement.getString(5).equals("V") ? true  : false : false;
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN validarConvenio: ")
                    .append("CTA-").append("")
                    .append("-CH-").append(iCodExtCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString(), e);
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);
        } finally {
            this.cerrarConexion(iCodExtCanal);
        }
        return resultado;
    }
}
