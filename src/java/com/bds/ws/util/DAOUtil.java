/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.util;

import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.exception.DAOException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.LogManager;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.apache.log4j.Logger;

/**
 * clase utilitaria para el manejo de las conexiones
 *
 * @author cesar.mujica
 */
public class DAOUtil extends BDSUtil {

    public CallableStatement statement = null;
    public Connection conn = null;
    public ResultSet objResultSet = null;
    public boolean ejecuto = false;
    
    
    //solo se genero para P2P
    public Connection p2pConn() {
        return conn;
    }

    /**
     * Log de sistema
     */
    private static Logger logger ;
    
    static {
        InputStream inputStream = DAOUtil.class.getResourceAsStream("/logging.properties");
        if (null != inputStream) {
            try {
                LogManager.getLogManager().readConfiguration(inputStream);
            } catch (IOException e) {
                logger.error( new StringBuilder("init logging system "),e);
            }
            logger = Logger.getLogger(DAOUtil.class.getCanonicalName());
        }
    }

    /**
     * metodo que se encarga de crear una conexion en base a un String JNDI
     * correspondiente a un DataSource ubicado en el servidor
     *
     * @param jndiConexion String jndiConexion nombre de JNDI definido
     * @param canal int canal canla por el que se invoca al SP
     * @return RespuestaDTO clase que contiene el estatus de la operacion
     */
    public RespuestaDTO conectarJNDI(String jndiConexion, String canal) {
        RespuestaDTO respuesta = new RespuestaDTO();
        try {
            InitialContext initContext = new InitialContext();
            DataSource ds = (DataSource) initContext.lookup(jndiConexion);
            this.conn = ds.getConnection();
        } catch (NamingException e) {
            logger.error( new StringBuilder("ERROR DAO EN conectarJNDI. Nombre JNDI no encontrado: ").append("JNDI-").append(jndiConexion)
                    .append("-CH-").append(canal).append("-DT-").append(new Date()).append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA).append("-EXCP-").append(e.toString()).toString(),e);
            respuesta.setCodigo("DAO001");//nombre de jndi no encontrado
        } catch (SQLException e) {
            logger.error( new StringBuilder("ERROR DAO EN conectarJNDI: ").append("JNDI-").append(jndiConexion)
                    .append("-CH-").append(canal).append("-DT-").append(new Date()).append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA).append("-EXCP-").append(e.toString()).toString(),e);
            respuesta.setCodigo("DAO002");//problema de conexion a BD
        }
        return respuesta;
    }

    /**
     * metodo que se encarga de constuir y preparar el query para invocar un
     * store procedure de Oracle
     *
     * @param paquete String paquete nombre del paquete de BD donde se ubica el
     * query
     * @param nombreProcedimiento String nombre del procedimiento a llamar
     * @param cantParametros int cantidad de parametros que recibe el
     * procedimiento (de entrada y salida)
     * @param canal int canla por el que se invoca al SP
     * @return RespuestaDTO clase que contiene el estatus de la operacion
     */
    public RespuestaDTO generarQuery(String paquete, String nombreProcedimiento, int cantParametros, String canal) {
        RespuestaDTO respuesta = new RespuestaDTO();
        //StringBuilder query = new StringBuilder("BEGIN ").append(paquete.trim()).append(".").append(nombreProcedimiento.trim());
        StringBuilder query = new StringBuilder("CALL ").append(paquete.trim()).append(".").append(nombreProcedimiento.trim());
        try {
            if (cantParametros == 0) {
                //query.append(" (); END; ");
                query.append(" () ");
            } else {
                query.append(" (");
                if (cantParametros < 0) {
                    throw new DAOException();
                } else {
                    int coma = 0;
                    for (int i = 0; i < cantParametros; i++) {
                        coma = i + 1;
                        query.append(" ?");
                        if (coma < cantParametros) {
                            query.append(",");
                        }
                    }
                    //query.append("); END;");
                    query.append(")");
                }
            }
            this.statement = conn.prepareCall(query.toString());
        } catch (DAOException e) {
            logger.error( new StringBuilder("ERROR DAO EN generarQuery: ").append("CANTPARAM-").append(cantParametros)
                    .append("-CH-").append(canal).append("-DT-").append(new Date()).append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA).toString(), e);
            respuesta.setCodigo("DAO003");//cantidad de parametros no puede ser negativa
        } catch (SQLException e) {
            logger.error( new StringBuilder("ERROR DAO EN generarQuery: ").append("SP-").append(paquete).append(".").append(nombreProcedimiento)
                    .append("-CH-").append(canal).append("-DT-").append(new Date()).append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA).append("-EXCP-").append(e.toString()).toString(), e);
            respuesta.setCodigo("DAO004");//problema al generar el llamado a BD
        }
        return respuesta;
    }
    
    public RespuestaDTO generarQueryFichaCliente(String script) {
        RespuestaDTO respuesta = new RespuestaDTO();

        //StringBuilder query = new StringBuilder("BEGIN ").append(paquete.trim()).append(".").append(nombreProcedimiento.trim());
        //StringBuilder query = new StringBuilder("CALL ").append(paquete.trim()).append(".").append(nombreProcedimiento.trim());
        //StringBuilder query = new StringBuilder("CALL ").append(script.trim());
        String query = script.trim();
        try {            
            this.statement = conn.prepareCall(query.toString());
        }catch (SQLException e) {
           // logger.error( new StringBuilder("ERROR DAO EN generarQuery: ").append("SP-").append(paquete).append(".").append(nombreProcedimiento)
                   // .append("-CH-").append(canal).append("-DT-").append(new Date()).append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA).append("-EXCP-").append(e.toString()).toString(), e);
            respuesta.setCodigo("DAO004");//problema al generar el llamado a BD
        }
        //cantidad de parametros no puede ser negativa
        return respuesta;
    }

    
    /**
     * metodo que se encarga de constuir y preparar el query para invocar un
     * store procedure de Oracle
     *
     * @param paquete String paquete nombre del paquete de BD donde se ubica el
     * query
     * @param nombreProcedimiento String nombre del procedimiento a llamar
     * @param cantParametros int cantidad de parametros que recibe el
     * procedimiento (de entrada y salida)
     * @param canal int canla por el que se invoca al SP
     * @return RespuestaDTO clase que contiene el estatus de la operacion
     */
    public RespuestaDTO generarQueryParaFunciones(String paquete, String nombreFuncion, int cantParametros, String canal) {
        RespuestaDTO respuesta = new RespuestaDTO();
        StringBuilder query = new StringBuilder("BEGIN ? := ").append(paquete.trim()).append(".").append(nombreFuncion.trim());
        try {
            if (cantParametros == 0) {
                query.append(" () ");
            } else {
                query.append(" (");
                if (cantParametros < 0) {
                    throw new DAOException();
                } else {
                    int coma = 0;
                    for (int i = 0; i < cantParametros; i++) {
                        coma = i + 1;
                        query.append(" ?");
                        if (coma < cantParametros) {
                            query.append(",");
                        }
                    }
                    query.append("); END;");
                }
            }
            this.statement = conn.prepareCall(query.toString());
        } catch (DAOException e) {
            logger.error( new StringBuilder("ERROR DAO EN generarQueryParaFunciones: ").append("CANTPARAM-").append(cantParametros)
                    .append("-CH-").append(canal).append("-DT-").append(new Date()).append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA).toString(), e);
            respuesta.setCodigo("DAO003");//cantidad de parametros no puede ser negativa
        } catch (SQLException e) {
            logger.error( new StringBuilder("ERROR DAO EN generarQueryParaFunciones: ").append("SP-").append(paquete).append(".").append(nombreFuncion)
                    .append("-CH-").append(canal).append("-DT-").append(new Date()).append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA).append("-EXCP-").append(e.toString()).toString(), e);
            respuesta.setCodigo("DAO004");//problema al generar el llamado a BD
        }
        return respuesta;
    }
    
    /**
     * metodo que se encarga de cerrar todos los objetos de Oracle
     *
     * @return RespuestaDTO clase que contiene el estatus de la operacion
     * @param canal int canla por el que se invoca al S
     */
    public RespuestaDTO cerrarConexion(String canal) {
        RespuestaDTO respuesta = new RespuestaDTO();
        try {
            if (this.objResultSet != null) {
                this.objResultSet.close();
            }
            if (this.statement != null) {
                this.statement.close();
            }
            if (this.conn != null) {
                this.conn.close();
            }
        } catch (SQLException e) {
            logger.error( new StringBuilder("ERROR DAO EN cerrarConexion: ").append("-CH-").append(canal)
                    .append("-DT-").append(new Date()).append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA).append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo("DAO005");//problema al intentar cerrar las conexiones
        }
        return respuesta;
    }

    /**
     * metodo que se encarga de hacer el encoding en Oracle9 para UTF-8
     *
     * @return String valor String
     * @param rs ResultSet Set de datos
     * @param nombreCampo String nombre del campo
     * @throws java.lang.Exception
     */
    public String getCharSetOracle9(ResultSet rs, String nombreCampo) throws Exception {
        if (rs.getBytes(nombreCampo) == null) {
            return "";
        } else {
            return new String(rs.getBytes(nombreCampo), CHARSET_ORACLE_9);
        }

    }

    /**
     * metodo que se encarga de hacer el casting de sqlDate a utilDate
     *
     * @return Date fecha convertida
     * @param rs ResultSet Set de datos
     * @param nombreCampo String nombre del campo
     * @throws java.lang.Exception
     */
    public Date getUtilDate(ResultSet rs, String nombreCampo) throws Exception {
        if ((this.objResultSet.getString(nombreCampo) == null) || (this.objResultSet.getString(nombreCampo).isEmpty())) {
            return null;
        } else {
            return new Date((this.objResultSet.getDate(nombreCampo)).getTime());
        }
    }

    /**
     * metodo que se encarga de hacer el casting de String a sqlDate
     *
     * @return java.sql.Date fecha convertida
     * @param fecha String fecha en formato String eje 31/12/2014
     * @param formato String formato en que se recibe la fecha eje dd/MM/yyyy
     * @throws java.lang.Exception
     */
    public java.sql.Date getSQLDate(String fecha, String formato) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat(formato);
        Date parsed = format.parse(fecha);
        return new java.sql.Date(parsed.getTime());
    }

}
