/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.daoimpl;

import com.bds.ws.dao.ActuacionesDAO;
import com.bds.ws.dao.NotificationServiceType;
import com.bds.ws.dao.TddDAO;
import com.bds.ws.dto.ClienteDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.dto.TarjetaDebitoDTO;
import com.bds.ws.dto.UtilDTO;
import com.bds.ws.exception.DAOException;
import static com.bds.ws.util.BDSUtil.CODIGO_EXCEPCION_GENERICA;
import static com.bds.ws.util.BDSUtil.CODIGO_RESPUESTA_EXITOSO;
import static com.bds.ws.util.BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA;
import static com.bds.ws.util.BDSUtil.JNDI_ORACLE_9;
import com.bds.ws.util.DAOUtil;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.NoResultException;
import oracle.jdbc.OracleTypes;
import org.apache.log4j.Logger;

/**
 * Clase que implementa TddDAO
 *
 * @author rony.rodriguez
 */
@Named("TddDAO")
@Stateless
@NotificationServiceType(NotificationServiceType.ServiceType.SERVICES)
public class TddDAOImpl extends DAOUtil implements TddDAO {

    
    @EJB
    public ActuacionesDAO actuaciones;
    /**
     * Log de sistema
     */
    private static final Logger logger = Logger.getLogger(TddDAOImpl.class.getName());

    /**
     * Bloquea una tarjeta de debito por intentos fallidos ya sean de acceso o
     * de validacion de OTP.
     *
     * @param tarjetaDebito numero de tarjeta de debito
     * @param nombreCanal nombre del canal
     * @return RespuestaDTO
     */
    @Override
    public RespuestaDTO bloquearTDD(String tarjetaDebito, String nombreCanal) {

        RespuestaDTO respuesta = new RespuestaDTO();

        try {
            respuesta = this.conectarJNDI(JNDI_ORACLE_9, nombreCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            respuesta = this.generarQuery("IB_K_TDD", "IB_P_BLOQUEAR_TDD", 2, nombreCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            //seteo de parametros de entrada y/o salida
            statement.setString(1, tarjetaDebito);
            statement.setString(2, nombreCanal);
            statement.execute();

        } catch (DAOException d) {
            logger.error( new StringBuilder("ERROR DAO  EN bloquearTDD: ")
                    .append("-CH-").append(nombreCanal).append("-DT-").append(new Date()).append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(d.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log            
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO GENERICO EN bloquearTDD: ")
                    .append("-CH-").append(nombreCanal).append("-DT-").append(new Date()).append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log            
        } finally {
            this.cerrarConexion(nombreCanal);
        }
//        if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN bloquearTDD: ")
//                    .append("-CH-").append(nombreCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return respuesta;

    }

    /**
     * Obtiene los datos de una TDD
     *
     * @param codigoCliente codigo del cliente
     * @param codigoCanal nombre del canal
     * @return TarjetaDebitoDTO
     */
    @Override
    public TarjetaDebitoDTO obtenerDatosTDD(String codigoCliente, String codigoCanal) {
        TarjetaDebitoDTO tarjeta = new TarjetaDebitoDTO();
        RespuestaDTO respuesta = new RespuestaDTO();
        int NUMERO_PARAMETROS = 3;

        try {

            if (codigoCliente == null || codigoCliente.isEmpty() || codigoCliente.equalsIgnoreCase("")
                    || codigoCanal == null || codigoCanal.isEmpty() || codigoCanal.equalsIgnoreCase("")) {
                respuesta.setCodigo("DAO008");
                throw new DAOException("DAO008");
            }

            respuesta = this.conectarJNDI(JNDI_ORACLE_9, codigoCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }

            respuesta = this.generarQuery("IB_K_TDD", "IB_P_OBTENER_DATOS_TDD", NUMERO_PARAMETROS, codigoCanal);

            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }

            statement.setBigDecimal(1, BigDecimal.valueOf(Double.parseDouble(codigoCliente)));
            statement.setString(2, codigoCanal);
            statement.registerOutParameter(3, OracleTypes.CURSOR);
            this.ejecuto = statement.execute();

            objResultSet = (ResultSet) statement.getObject(3);
            while (objResultSet.next()) {
                tarjeta = new TarjetaDebitoDTO();
                tarjeta.setCliente(new ClienteDTO());

                tarjeta.setNumeroTarjeta(this.getCharSetOracle9(objResultSet, "numeroTarjeta"));
                tarjeta.getCliente().setEmailCorreo(this.getCharSetOracle9(objResultSet, "correo"));
                tarjeta.getCliente().setTelefonoCell(String.valueOf(this.objResultSet.getBigDecimal("celular")));
                tarjeta.setCodigoAgencia(String.valueOf(this.objResultSet.getInt("codAgencia")));
                tarjeta.setCodigoSubaplicacion(String.valueOf(this.objResultSet.getInt("codSubAplicacion")));
            }

            if (tarjeta.getNumeroTarjeta() == null || tarjeta.getNumeroTarjeta().isEmpty() || tarjeta.getNumeroTarjeta().equals("")) {
                throw new NoResultException();
            }

        } catch (NoResultException e) {
                respuesta.setCodigoSP(CODIGO_SIN_RESULTADOS_JPA);//revisar el log
                respuesta.setTextoSP(TEXTO_SIN_RESULTADOS_JPA);//revisar el log
            logger.error( new StringBuilder("ERROR DAO EN obtenerDatosTDD: ")
                    .append("CC-").append(codigoCliente)
                    .append("-CH-").append(codigoCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN obtenerDatosTDD: ")
                    .append("CC-").append(codigoCliente)
                    .append("-CH-").append(codigoCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);
        } finally {
            tarjeta.setRespuesta(respuesta);
            this.cerrarConexion(codigoCanal);
        }
//        if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN obtenerDatosTDD: ")
//                    .append("CC-").append(codigoCliente)
//                    .append("-CH-").append(codigoCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return tarjeta;
    }
    
    /**
     * Obtiene la lista TDD activas y canceladas del cliente
     *
     * @param codigoCliente codigo del cliente
     * @param codigoCanal nombre del canal
     * @return TarjetaDebitoDTO
     */
    @Override
    public TarjetaDebitoDTO obtenerListadoTDDCliente(String codigoCliente, String codigoCanal) {
        ArrayList<TarjetaDebitoDTO> tarjetas = new ArrayList<>();
        TarjetaDebitoDTO tarjeta = new TarjetaDebitoDTO();
        RespuestaDTO respuesta = new RespuestaDTO();
        int NUMERO_PARAMETROS = 3;

        try {

            if (codigoCliente == null || codigoCliente.isEmpty() || codigoCliente.equalsIgnoreCase("")
                    || codigoCanal == null || codigoCanal.isEmpty() || codigoCanal.equalsIgnoreCase("")) {
                respuesta.setCodigo("DAO008");
                throw new DAOException("DAO008");
            }

            respuesta = this.conectarJNDI(JNDI_ORACLE_9, codigoCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }

            respuesta = this.generarQuery("IB_K_TDD", "IB_P_OBTENER_DATOS_TDD", NUMERO_PARAMETROS, codigoCanal);

            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }

            statement.setBigDecimal(1, BigDecimal.valueOf(Double.parseDouble(codigoCliente)));
            statement.setString(2, codigoCanal);
            statement.registerOutParameter(3, OracleTypes.CURSOR);
            this.ejecuto = statement.execute();

            objResultSet = (ResultSet) statement.getObject(3);
            while (objResultSet.next()) {
                tarjeta = new TarjetaDebitoDTO();
                tarjeta.setCliente(new ClienteDTO());

                tarjeta.setNumeroTarjeta(this.getCharSetOracle9(objResultSet, "numeroTarjeta"));
                tarjeta.setEstado(this.getCharSetOracle9(objResultSet, "estado"));
                tarjeta.getCliente().setEmailCorreo(this.getCharSetOracle9(objResultSet, "correo"));
                tarjeta.getCliente().setTelefonoCell(this.getCharSetOracle9(objResultSet, "celular"));
                tarjeta.setCodigoAgencia(String.valueOf(this.objResultSet.getInt("codAgencia")));
                tarjeta.setCodigoSubaplicacion(String.valueOf(this.objResultSet.getInt("codSubAplicacion")));
                tarjetas.add(tarjeta);
            }
            
            if (tarjetas == null || tarjetas.isEmpty()) {
                throw new NoResultException();
            }
            
            tarjeta = new TarjetaDebitoDTO();
            tarjeta.setTddsDTO(tarjetas);

        } catch (NoResultException e) {
                respuesta.setCodigoSP(CODIGO_SIN_RESULTADOS_JPA);//revisar el log
                respuesta.setTextoSP(TEXTO_SIN_RESULTADOS_JPA);//revisar el log
            logger.error( new StringBuilder("ERROR DAO EN obtenerListadoTDDCliente: ")
                    .append("CC-").append(codigoCliente)
                    .append("-CH-").append(codigoCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN obtenerListadoTDDCliente: ")
                    .append("CC-").append(codigoCliente)
                    .append("-CH-").append(codigoCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);
        } finally {
            tarjeta.setRespuesta(respuesta);
            this.cerrarConexion(codigoCanal);
        }
//        if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN obtenerListadoTDDCliente: ")
//                    .append("CC-").append(codigoCliente)
//                    .append("-CH-").append(codigoCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return tarjeta;
    }

    /**
     * Metodo que valida que el instrumento pertenece al cliente.
     *
     * @param codigoCliente codigo del cliente
     * @param numeroTarjeta numero de la tarjeta
     * @param codigoCanal codigo del canal
     * @return UtilDTO
     */
    @Override
    public UtilDTO obtenerClienteTarjeta(String codigoCliente, String numeroTarjeta, String codigoCanal) {
        UtilDTO utilDTO = new UtilDTO();
        RespuestaDTO respuesta = new RespuestaDTO();
        Map resultados = new HashMap();

        try {

            if (codigoCliente == null || codigoCliente.isEmpty() || codigoCliente.equalsIgnoreCase("")
                    || numeroTarjeta == null || numeroTarjeta.isEmpty() || numeroTarjeta.equalsIgnoreCase("")
                    || codigoCanal == null || codigoCanal.isEmpty() || codigoCanal.equalsIgnoreCase("")) {
                respuesta.setCodigo("DAO008");
                throw new DAOException("DAO008");
            }

            respuesta = this.conectarJNDI(JNDI_ORACLE_9, codigoCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            respuesta = this.generarQuery("IB_K_TDD", "IB_P_CLIENTE_TARJETAS", 4, codigoCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            //seteo de parametros de entrada y/o salida
            statement.setInt(1, Integer.parseInt(codigoCliente));
            statement.setString(2, numeroTarjeta);
            statement.setString(3, codigoCanal);
            statement.registerOutParameter(4, OracleTypes.VARCHAR);
            this.ejecuto = statement.execute();

            if (new String(this.statement.getBytes(4), CHARSET_ORACLE_9).equals("V")) {
                resultados.put("perteneceCliente", true);
            } else {
                resultados.put("perteneceCliente", false); //F
            }

            utilDTO.setResulados(resultados);

        } catch (DAOException e) {
            logger.error( new StringBuilder("ERROR DAO EN obtenerClienteTarjeta: ")
                    .append("USR-").append(codigoCliente)
                    .append("-CH-").append(codigoCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            utilDTO.setRespuesta(respuesta);
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN obtenerClienteTarjeta: ")
                    .append("USER-").append(codigoCliente)
                    .append("-CH-").append(codigoCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        } finally {
            utilDTO.setRespuesta(respuesta);
            this.cerrarConexion(codigoCanal);
        }
//        if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN obtenerClienteTarjeta: ")
//                    .append("USER-").append(codigoCliente)
//                    .append("-CH-").append(codigoCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return utilDTO;
    }

    /**
     * Valida que los instrumentos TDD, Fecha Vcto y PIN pertenece al cliente.
     *
     * @param numeroTarjeta numero de la tarjeta de debito del cliente
     * @param pinCifrado PIN cifrado de la Tarjeta.
     * @param fechaVencimiento Fecha de Vencimiento de la Tarjeta.
     * @param codigoCanal codigo del canal
     * @return UtilDTO
     */
    @Override
    public UtilDTO validarTDD(String numeroTarjeta, String pinCifrado, String fechaVencimiento, String codigoCanal) {
        UtilDTO utilDTO = new UtilDTO();
        RespuestaDTO respuesta = new RespuestaDTO();
        Map resultados = new HashMap();

        try {
            
            if (pinCifrado == null || pinCifrado.isEmpty() || pinCifrado.equalsIgnoreCase("")
                    || fechaVencimiento == null || fechaVencimiento.isEmpty() || fechaVencimiento.equalsIgnoreCase("")
                    || numeroTarjeta == null || numeroTarjeta.isEmpty() || numeroTarjeta.equalsIgnoreCase("")
                    || codigoCanal == null || codigoCanal.isEmpty() || codigoCanal.equalsIgnoreCase("")) {
                respuesta.setCodigo("DAO008");
                throw new DAOException("DAO008");
            }
            
            respuesta = this.conectarJNDI(JNDI_ORACLE_9, codigoCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            
            //ANTERIORMENTE SE LLAMABA A IB_P_VALIDAR_TDD PERO COMO SE ENVIA LA CADENA EN B64 SE LLAMA ESTE METODO QUE DECODIFICA Y LLAMA AL IB_P_VALIDAR_TDD
            respuesta = this.generarQuery("IB_K_TDD", "IB_P_VALIDAR_TDD_PINB64", 6, codigoCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            //seteo de parametros de entrada y/o salida
            statement.setString(1, numeroTarjeta);
            statement.setString(2, pinCifrado);
            statement.setDate(3, this.getSQLDate(fechaVencimiento, FORMATO_FECHA_SIMPLE));
            statement.setString(4, codigoCanal);
            statement.registerOutParameter(5, OracleTypes.NUMBER);
            statement.registerOutParameter(6, OracleTypes.VARCHAR);
            this.ejecuto = statement.execute();

            String codSalida = this.statement.getBigDecimal(5).toString();
            respuesta = actuaciones.obtenerDescripcionSalidaSP(codSalida, codigoCanal);
            
                if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                    throw new DAOException();
                } else if (respuesta.getCodigoSP().equals(CODIGO_RESPUESTA_EXITOSO)) {
                        if (new String(this.statement.getBytes(6), CHARSET_ORACLE_9).trim().equals("S")) {
                            resultados.put("tddValida", true);
                         } else {
                            resultados.put("tddValida", false);
                        }  
                    }
                     else {
                        throw new NoResultException();
                     }
                    
               
            
            utilDTO.setResulados(resultados);

        }  catch (DAOException e) {
            logger.error( new StringBuilder("ERROR DAO EN validarTDD: ")
                    .append("TDD-").append(numeroTarjeta)
                    .append("-CH-").append(codigoCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
           
        } catch (NoResultException e) {
                respuesta.setCodigoSP(CODIGO_SIN_RESULTADOS_JPA);
                respuesta.setTextoSP(TEXTO_SIN_RESULTADOS_JPA);
            logger.error( new StringBuilder("ERROR DAO EN validarTDD: ")
                    .append("TDD-").append(numeroTarjeta)
                    .append("-CH-").append(codigoCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        }       
        catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN validarTDD: ").append("TDD-").append(numeroTarjeta)
                    .append("-CH-").append(codigoCanal).append("-DT-").append(new Date()).append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        } 
        finally {
            utilDTO.setRespuesta(respuesta);
            this.cerrarConexion(codigoCanal);
        }
//        if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN validarTDD: ").append("TDD-").append(numeroTarjeta)
//                    .append("-CH-").append(codigoCanal).append("-DT-").append(new Date()).append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return utilDTO;
    }

}
