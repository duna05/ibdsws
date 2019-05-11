/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.daoimpl;

import com.bds.ws.dao.FichaClienteUpdateDAO;
import com.bds.ws.dao.NotificationServiceType;
import com.bds.ws.dto.FichaClienteDTO;
import com.bds.ws.dto.FichaClienteUpdateDTO;
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
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.ejb.Stateless;
import javax.inject.Named;
import oracle.jdbc.OracleTypes;
import org.apache.log4j.Logger;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;



/**
 *
 * @author humberto.rojas
 */
@Named("FichaClienteUpdateDAO")
@Stateless
@NotificationServiceType(NotificationServiceType.ServiceType.SERVICES)
public class FichaClienteUpdateDAOImpl extends DAOUtil implements FichaClienteUpdateDAO {
    private static final Logger logger = Logger.getLogger(FichaClienteUpdateDAOImpl.class.getName());
    
    
    @Override
    public UtilDTO insertUpdateFichaCliente(String iCodigoCliente) {
        RespuestaDTO respuesta = new RespuestaDTO();
        //Map resultados = new HashMap();
        //FichaClienteDTO fichacliente = new FichaClienteDTO();
        UtilDTO utilDTO = new UtilDTO();
        //Statement stmt = null;
        try {
            respuesta = this.conectarJNDI(JNDI_ORACLE_9, null);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
          
            /*respuesta = this.generarQueryParaFunciones("FICHA_CLIENTE", "OBT_DATOS_FICHA_CLI_IN", 1, null);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }*/
            //statement.setInt(1, Integer.parseInt(iCodigoCliente));
            //statement.registerOutParameter(2, OracleTypes.INTEGER);
            //statement.executeUpdate();
            /*----------------------------------------------------------------------*/
            
            CallableStatement cst = conn.prepareCall("{call FICHA_CLIENTE.OBT_DATOS_FICHA_CLI_IN (?)}");
            cst.setInt(1, Integer.parseInt(iCodigoCliente));
            /*Se ejecuta la sentencia SQL*/
            cst.execute();
            
            /*------------------------------------------------------------------------*/
            
            
            //resultados.put("esExcluido", statement.getString(1));
            //utilDTO.setResulados(resultados);
            //utilDTO.setRespuesta(respuesta);
//            logger.error( new StringBuilder("EXITO DAO EN validarClienteExcluido: ")
//                    .append("CTA-").append(codCliente)
//                    .append("-CH-").append(iCodExtCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
            //respuesta.setCodigo(CODIGO_RESPUESTA_EXITOSO);
            //respuesta.setCodigoSP(CODIGO_RESPUESTA_EXITOSO_SP);
            utilDTO.setRespuesta(respuesta);
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN validarClienteExcluido: ")
                    //.append("CTA-").append(codCliente)
                    //.append("-CH-").append(iCodExtCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString(), e);
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);
            utilDTO.setRespuesta(respuesta);
        } finally {
            //this.cerrarConexion(iCodExtCanal);
             this.cerrarConexion(null);
        }

        return utilDTO;
    }

   
    public FichaClienteDTO obtenerDatosFichaCliente(String iCodigoCliente) {
       FichaClienteDTO fichacliente = new FichaClienteDTO();
        RespuestaDTO respuesta = new RespuestaDTO();
        try {
            respuesta = this.conectarJNDI(JNDI_ORACLE_9, null);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            respuesta = this.generarQuery("FICHA_CLIENTE", "OBT_DATOS_FICHA_CLIENTE_2", 2, null);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            //seteo de parametros de entrada y/o salida
            statement.setInt(1, Integer.parseInt(iCodigoCliente));
            statement.registerOutParameter(2, OracleTypes.CURSOR);
            this.ejecuto = statement.execute();
          
            objResultSet = (ResultSet) statement.getObject(2);
            while (objResultSet.next()) {

                fichacliente.setTipoIdentificacion(objResultSet.getBigDecimal("CODIGO_TIPO_IDENTIFICACION"));
                fichacliente.setDescTipoIdentificacion(objResultSet.getString("DES_TIPO_IDENTIFICACION"));
                fichacliente.setNumeroIdentificacion(objResultSet.getString("NUMERO_IDENTIFICACION"));
                fichacliente.setCodSubTipoCliente(objResultSet.getBigDecimal("CODIGO_SUBTIPO_CLIENTE"));
                fichacliente.setTipoCliente(objResultSet.getString("TIPO_CLIENTE"));
                fichacliente.setCodigoCliente(objResultSet.getBigDecimal("CODIGO_CLIENTE"));
                fichacliente.setCodigoOnt(objResultSet.getString("CODIGO_ONT"));
                fichacliente.setNombreComercial(objResultSet.getString("NOMBRE_COMERCIAL"));
                fichacliente.setRazonSocial(objResultSet.getString("RAZON_SOCIAL"));
                fichacliente.setCodGrupoEconomico(objResultSet.getBigDecimal("CODIGO_GRUPO_ECONOMICO"));
                fichacliente.setDescGrupoEconomico(objResultSet.getString("DES_GRUPO_ECONOMICO"));
                fichacliente.setCodClasificacion(objResultSet.getBigDecimal("CODIGO_CLASIFICACION"));
                fichacliente.setDesClasificacion(objResultSet.getString("CLASIFICACION"));
                fichacliente.setCodSubClasificacion(objResultSet.getBigDecimal("CODIGO_SUBCLASIFICACION"));
                fichacliente.setDesSubClasificacion(objResultSet.getString("SUBCLASIFICACION"));

                fichacliente.setCodSubSubClasificacion(objResultSet.getBigDecimal("CODIGO_SUB_SUB_CLASIFICACION"));
                fichacliente.setDesSubSubClasificacion(objResultSet.getString("SUBSUBCLASIFICACION"));
                fichacliente.setTipoIdentificacionR(objResultSet.getBigDecimal("CODIGO_TIPO_IDENTIFICACION_R"));
                fichacliente.setDescTipoIdentificacionR(objResultSet.getString("DES_TIPO_IDENTIFICACION_R"));
                fichacliente.setNumeroIdentificacionr(objResultSet.getString("NUMERO_IDENTIFICACION_R"));
                fichacliente.setNombreRepresentante(objResultSet.getString("NOMBRE_REPRESENTANTE"));
                fichacliente.setCodCargoRepresentante(objResultSet.getBigDecimal("CODIGO_CARGOR"));
                fichacliente.setDescCargoR(objResultSet.getString("DESCRIPCION_CARGOR"));

                fichacliente.setNombre(objResultSet.getString("NOMBRES"));
                fichacliente.setPrimerApellido(objResultSet.getString("PRIMER_APELLIDO"));
                fichacliente.setApellidoCasada(objResultSet.getString("APELLIDO_CASADA"));
                fichacliente.setFechaNacimiento(objResultSet.getString("FECHA_NACIMIENTO"));
                fichacliente.setEdad(objResultSet.getString("EDAD"));
                fichacliente.setNacionalidad(objResultSet.getString("NACIONALIDAD"));
                fichacliente.setLugarNacimiento(objResultSet.getString("LUGAR_NACIMIENTO"));
                fichacliente.setSexo(objResultSet.getString("SEXO"));
                fichacliente.setEstadoCivil(objResultSet.getString("ESTADO_CIVIL"));
                fichacliente.setActEconomicaPpal(objResultSet.getString("ACTIVIDAD_ECONOMICA_PRINCIPA"));
                fichacliente.setNivelEducativo(objResultSet.getString("NIVEL_EDUCATIVO"));
                fichacliente.setCodActEconomica(objResultSet.getBigDecimal("CODIGO_PROFESION"));
                fichacliente.setDescripcionOcupacion(objResultSet.getString("DESCRIPCION_PROFESION"));

                fichacliente.setCalificacion(objResultSet.getString("CALIFICACION"));
                fichacliente.setDesCalificacion(objResultSet.getString("DESCRIPCION_CALIFICACION"));
                fichacliente.setNumeroDependientes(objResultSet.getString("NUMERO_DEPENDIENTES"));
                fichacliente.setCantidadHijos(objResultSet.getString("CANTIDAD_HIJOS"));
                fichacliente.setIdetConyugue(objResultSet.getString("IDET_CONYUGUE"));
                fichacliente.setNumeroIdentConyugue(objResultSet.getString("NUMERO_IDENT_CONYUGUE"));
                fichacliente.setNombreConyugue(objResultSet.getString("NOMBRE_CONYUGUE"));
                fichacliente.setSegundoApellido(objResultSet.getString("SEGUNDO_APELLIDO"));
                fichacliente.setCodSegmento(objResultSet.getBigDecimal("CODIGO_SEGMENTO"));
                fichacliente.setDesSegmento(objResultSet.getString("DES_SEGMENTO"));
            }
            /*}
            }*/
            fichacliente.setRespuesta(respuesta);

        } catch (Exception e) {
            logger.error(new StringBuilder("ERROR DAO EN buscarNombreCodEmpresaByRif: ")
                    //.append("-CH-").append(codigoCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString(), e);
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log

        } finally {

            this.cerrarConexion(null);
        }
        return fichacliente;
    }
    
}
