/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.daoimpl;

import com.bds.ws.dao.FichaClienteDAO;
import com.bds.ws.dao.NotificationServiceType;
import com.bds.ws.dao.NotificationServiceType.ServiceType;
import com.bds.ws.dto.FichaClienteDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.exception.DAOException;
import static com.bds.ws.util.BDSUtil.CODIGO_EXCEPCION_GENERICA;
import static com.bds.ws.util.BDSUtil.CODIGO_RESPUESTA_EXITOSO;
import static com.bds.ws.util.BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA;
import static com.bds.ws.util.BDSUtil.JNDI_ORACLE_9;
import com.bds.ws.util.DAOUtil;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.Date;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import oracle.jdbc.OracleTypes;
import org.apache.log4j.Logger;

/**
 *
 * @author humberto.rojas
 */
@Named("FichaClienteDAO")
@Stateless
@NotificationServiceType(ServiceType.SERVICES)
public class FichaClienteDAOImpl extends DAOUtil implements FichaClienteDAO {

    private static final Logger logger = Logger.getLogger(FichaClienteDAOImpl.class.getName());

    @Override
    public FichaClienteDTO consultarDatosCliente(String iCodigoCliente) {
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
            //String codSalida = this.statement.getBigDecimal(4).toString();

            /*if (codSalida != null || !codSalida.isEmpty() || !codSalida.equalsIgnoreCase("")) {
                respuesta = actuaciones.obtenerDescripcionSalidaSP(codSalida, codigoCanal);
                if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO) || !respuesta.getCodigoSP().equals(CODIGO_RESPUESTA_EXITOSO)) {
                    throw new DAOException();
                } else {*/
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
