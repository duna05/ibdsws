/*
 * ConexionBDSQLSERVER2005.java
 *
 * Created on 28 de noviembre de 2006, 09:03 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package com.roas.ws.p2p;

/*@author Ing. Nelson Roas www.roassystems.com.ve 0424-2222203 0424-2974812
 * roassystems@gmail.com
 */
import javax.sql.*;



import java.sql.*;
import java.util.ResourceBundle;
import javax.naming.Context;
import javax.naming.InitialContext;
//import org.apache.tomcat.dbcp.dbcp.SQLNestedException;

public class ConexionPOOLBD {

    private static ResourceBundle bundle;
    private static String usuario = "";
    private static String pass = "";
    private static String driver = "";
    private static String urlbd = "";
    private static Connection conexion = null;
    
    /**
     * Creates a new instance of ConexionBDSQLSERVER2000
     */
    public ConexionPOOLBD() {
    }

    public static Connection establecerConexionPool() throws SQLException, Exception {
        try {
            Connection con = null;
            // Obtenemos un contexto inicial
            Context ctx = new InitialContext();
            // Obtenemos el contexto de nuestro entorno. La cadena
            // "java:comp/env" es siempre la misma+ el atributo name de la etqueta
            // resource en el archivo context.xml
            //Context envCtx = (Context) ctx.lookup("java:comp/env"); esto es para tomcat
            // Obtenemos una fuente de datos identificada con la cadena que
            // le hemos asignado en los ficheros de configuración
            DataSource ds = (DataSource) ctx.lookup("p2p");
            con = ds.getConnection();
            setConexion(con);
            // Ya podemos obtener una conexión y operar con ella normalmente
            //System.out.println("Conexion exitosa con pool P2P ORACLE");
            //LOGGER.info("Conexion exitosa con pool P2P ORACLE");
            return con;

        } catch (java.sql.SQLException e2) {
            //System.out.println("ERROR ESTABLECIENDO CONEXION, VERIFIQUE PARAMETROS DE CONEXION ORACLE " + e2);
        	//LOGGER.info("ERROR ESTABLECIENDO CONEXION, VERIFIQUE PARAMETROS DE CONEXION ORACLE " + e2);
            throw e2;
        } catch (Exception e) {
            //System.out.println("ERROR GRAL ESTABLECIENDO CONEXION CON BASE DE DATOS ORACLE " + e);
        	//LOGGER.info("ERROR GRAL ESTABLECIENDO CONEXION CON BASE DE DATOS ORACLE " + e);
            throw new Exception("Excepcion Gral Data Source " + e);
        }


    }
    public static Connection establecerConexionJDBC()  {
        try {
            bundle = ResourceBundle.getBundle("ParametrosBD");
            urlbd = bundle.getString("url_sql");
            driver = bundle.getString("driver_sql");
            usuario = bundle.getString("usuario_sql");
            pass = bundle.getString("password_sql");
            Class.forName(driver);
            conexion = DriverManager.getConnection(urlbd, usuario, pass);
            System.out.println("Conexion exitosa con la base de datos ORACLE P2P");
        } catch (Exception e) {
            System.out.println("Error estableciendo conexion con bd " + e);
            //throw new SQLException("Error al conectar base de Datos P2P "+e);
        }
        return conexion;
    }


        public static void cerrarConexion() {
        try {
            if (conexion != null) {
                conexion.close();
                System.out.println("conexion cerrada");
                //LOGGER.info("CONEXION A BASE DE DATOS CERRADA EXITOSAMENTE");
            }
        } catch (Exception e) {
        	//LOGGER.info("ERROR CERRANDO CONEXION A BD EN CLASE CONEXIONPOOL " + e);
        }
    }

    public java.sql.Connection getConexion() throws Exception {
        return conexion;
    }

    public static void setConexion(java.sql.Connection conexion) {
        ConexionPOOLBD.conexion = conexion;
    }

    public static void main(String ars[]) throws SQLException {
        //ConexionPOOLBD con = new ConexionPOOLBD();
        ConexionPOOLBD.establecerConexionJDBC();
        ConexionPOOLBD.cerrarConexion();
    }
}
