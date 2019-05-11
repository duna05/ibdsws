/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.daoimpl;

import com.bds.ws.dao.FichaClienteComplementarioDAO;
import com.bds.ws.dao.FichaClienteDAO;
import com.bds.ws.dao.NotificationServiceType;
import com.bds.ws.dao.NotificationServiceType.ServiceType;
import com.bds.ws.dto.FichaClienteComplementarioDTO;
import com.bds.ws.dto.FichaClienteDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.exception.DAOException;
import static com.bds.ws.util.BDSUtil.CODIGO_EXCEPCION_GENERICA;
import static com.bds.ws.util.BDSUtil.CODIGO_RESPUESTA_EXITOSO;
import static com.bds.ws.util.BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA;
import static com.bds.ws.util.BDSUtil.JNDI_ORACLE_9;
import com.bds.ws.util.DAOUtil;
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
@Named("FichaClienteComplementarioDAO")
@Stateless
@NotificationServiceType(ServiceType.SERVICES)
public class FichaClienteComplementarioDAOImpl extends DAOUtil implements FichaClienteComplementarioDAO {
    
     private static final Logger logger = Logger.getLogger(FichaClienteComplementarioDAOImpl.class.getName());

    @Override
    public FichaClienteComplementarioDTO consultarDatosComplementariosCliente(String iCodigoCliente) {
     FichaClienteComplementarioDTO fichaclientecomplementario = new FichaClienteComplementarioDTO();
     RespuestaDTO respuesta = new RespuestaDTO();     
     try {
           respuesta = this.conectarJNDI(JNDI_ORACLE_9, null);
           
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            respuesta = this.generarQuery("FICHA_CLIENTE", "OBT_COMPL_FICHA_CLIENTE_2", 2, null);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            
            statement.setInt(1, Integer.parseInt(iCodigoCliente));            
            statement.registerOutParameter(2, OracleTypes.CURSOR);            
            this.ejecuto = statement.execute();
            
                    objResultSet = (ResultSet) statement.getObject(2);
                    while (objResultSet.next()) {
                        
                                                fichaclientecomplementario.setCodigoNacionalidad(objResultSet.getBigDecimal("COD_NACIONALIDAD"));
						fichaclientecomplementario.setCantidadSucursales(objResultSet.getBigDecimal("CANT_SUCURSALES"));
                                                fichaclientecomplementario.setCantidadEmpleados(objResultSet.getBigDecimal("CANT_EMPLEADOS"));
                                                fichaclientecomplementario.setCodigoActividadEconomica(objResultSet.getBigDecimal("COD_ACTIVIDAD_ECONOMICA"));
                                                fichaclientecomplementario.setFacturacionMensual(objResultSet.getString("FACTURACION_MENSUAL"));
                                                fichaclientecomplementario.setFacturacioUltima(objResultSet.getString("FECHA_ULTIMA_DECLARACION"));
                                                fichaclientecomplementario.setPersonaContactar(objResultSet.getString("PERSONA_CONTACTO"));
                                                fichaclientecomplementario.setCargoPersonaContacto(objResultSet.getBigDecimal("CARGO_PERSONA_CONTACTO"));
                                                fichaclientecomplementario.setCodigoSegmento(objResultSet.getBigDecimal("COD_SEGMENTO"));
                                                fichaclientecomplementario.setInicioSociedad(objResultSet.getString("FECHA_CONSTITUCION"));                                              
                                                fichaclientecomplementario.setDuracion(objResultSet.getString("DURACION"));
                                                fichaclientecomplementario.setFinSociedad(objResultSet.getString("FECHA_FIN"));
                                                fichaclientecomplementario.setCodigoPaisLegal(objResultSet.getBigDecimal("PAIS_CONSTITUCION"));
                                                fichaclientecomplementario.setCodigoDepartamento(objResultSet.getBigDecimal("ESTADO_CONSTITUCION"));
                                                fichaclientecomplementario.setCodigoMunicipio(objResultSet.getBigDecimal("MUNICIPIO_CONSTITUCION"));
                                                fichaclientecomplementario.setCapitalSocial(objResultSet.getBigDecimal("PATRIMONIO"));
                                                fichaclientecomplementario.setCapitalPagado(objResultSet.getBigDecimal("CAPITAL_PAGADO"));
                                                fichaclientecomplementario.setCapitalSuscrito(objResultSet.getBigDecimal("CAPITAL_SUSCRITO"));
                                                fichaclientecomplementario.setReservas(objResultSet.getBigDecimal("RESERVAS"));
                                                fichaclientecomplementario.setObjetivoSocial(objResultSet.getString("OBJETIVO_SOCIAL"));
                                                fichaclientecomplementario.setLimitacionEstatutaria(objResultSet.getString("LIMITACION_ESTATUTARIA"));
                                                fichaclientecomplementario.setFechaAumentoCapital(objResultSet.getString("FECHA_AUMENTO_CAPITAL"));
                                                fichaclientecomplementario.setValorAumentoCapital(objResultSet.getBigDecimal("VALOR_AUMENTO_CAPITAL"));
                                                fichaclientecomplementario.setTipoCompania(objResultSet.getString("TIPO_COMPANIA"));
                                                fichaclientecomplementario.setNumeroRegistro(objResultSet.getBigDecimal("NUMERO_REGISTRO"));
                                                fichaclientecomplementario.setLetraTomo(objResultSet.getString("LETRA_TOMO"));
                                                fichaclientecomplementario.setTomo(objResultSet.getBigDecimal("TOMO"));
                                                fichaclientecomplementario.setFechaRegistro(objResultSet.getString("FECHA_REGISTRO"));
                                                fichaclientecomplementario.setCiudad(objResultSet.getString("CIUDAD_REGISTRO"));
                                                fichaclientecomplementario.setMotivo((objResultSet.getString("MOTIVO_SOLICITUD") == null || objResultSet.getString("MOTIVO_SOLICITUD").equals("")? "SELECCIONE":objResultSet.getString("MOTIVO_SOLICITUD")));
                                                fichaclientecomplementario.setMov_dep(objResultSet.getString("ESTIMADOS_DEPOSITOS"));
                                                fichaclientecomplementario.setMov_ret(objResultSet.getString("ESTIMADOS_RETIROS"));
                                                fichaclientecomplementario.setEnv_transf(objResultSet.getString("ENVIAR_TRANSFERENCIA"));
                                                fichaclientecomplementario.setRec_transf(objResultSet.getString("RECIBIR_TRANSFERENCIA"));
                                                fichaclientecomplementario.setDep_efect(objResultSet.getBigDecimal("DEPOSITOS_EFECTIVO"));
                                                fichaclientecomplementario.setDep_cheq_per(objResultSet.getBigDecimal("DEPOSITOS_CHEQUE"));
                                                fichaclientecomplementario.setDep_cheq_ger(objResultSet.getBigDecimal("DEPOSITOS_GERENCIA"));
                                                fichaclientecomplementario.setDep_transf(objResultSet.getBigDecimal("DEPOSITOS_TRANSFERENCIA"));
                                                fichaclientecomplementario.setRet_efec(objResultSet.getBigDecimal("RETIRO_EFECTIVO"));
                                                fichaclientecomplementario.setRet_cheq_per(objResultSet.getBigDecimal("RETIRO_PERSONA"));
                                                fichaclientecomplementario.setRet_cheq_ger(objResultSet.getBigDecimal("RETIRO_GERENCIA"));
                                                fichaclientecomplementario.setRet_transf(objResultSet.getBigDecimal("RETIRO_TRANSFERENCIA"));
                                                fichaclientecomplementario.setUso_cta_ahorro(objResultSet.getString("USO_CTA_AHORRO"));
                                                fichaclientecomplementario.setUso_cta_prestamo(objResultSet.getString("USO_CTA_PRESTAMO"));
                                                fichaclientecomplementario.setUso_divisa(objResultSet.getString("USO_COMPRA_DIVISA"));
                                                fichaclientecomplementario.setUso_transferencia(objResultSet.getString("USO_TRANFERENCIAS"));
                                                fichaclientecomplementario.setUso_otros(objResultSet.getString("USO_OTROS"));
                       				/*----------------------------------------------------------------------------------------*/												
						fichaclientecomplementario.setDescNacionalidad(objResultSet.getString("DESC_NACIONALIDAD"));
						fichaclientecomplementario.setActividadEconomica(objResultSet.getString("ACTIVIDAD_ECONOMICA"));												
						fichaclientecomplementario.setSegmento(objResultSet.getString("SEGMENTO"));
						fichaclientecomplementario.setCalificacionCliente(objResultSet.getString("CALIFICACION_DEL_CLIENTE"));
						fichaclientecomplementario.setTiposCalificacion(objResultSet.getString("TIPOS_CALIFICACION"));												                                                
                                                fichaclientecomplementario.setPais_recibir(objResultSet.getString("PROTOCOLO"));
                                                fichaclientecomplementario.setPais_recibir(objResultSet.getString("PAIS_O_DESTINO_RECIBIR"));
                                                fichaclientecomplementario.setPais_enviar(objResultSet.getString("PAIS_O_DESTINO_ENVIAR"));
                                                fichaclientecomplementario.setComentariosOtros(objResultSet.getString("COMENTARIO_USO_OTROS"));
                                                fichaclientecomplementario.setNombreEstado(objResultSet.getString("NOMBRE_DEPARTAMENTO"));
                                                fichaclientecomplementario.setNombreMunicipio(objResultSet.getString("NOMBRE_MUNICIPIO"));
                                                
                    }
                /*}
            }Comente esto para adelantar y revisar el servicio
     */       fichaclientecomplementario.setRespuesta(respuesta); 
           
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN buscarNombreCodEmpresaByRif: ")
                    //.append("-CH-").append(codigoCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString(), e);
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
            
        } finally {
            
            this.cerrarConexion(null);
        }
       
        return fichaclientecomplementario ;     
    }
}
