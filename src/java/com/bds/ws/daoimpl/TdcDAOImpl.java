/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.daoimpl;

import com.bds.ws.dao.ActuacionesDAO;
import com.bds.ws.dao.NotificationServiceType;
import com.bds.ws.dao.TdcDAO;
import com.bds.ws.dto.ClienteDTO;
import com.bds.ws.dto.MovimientoTDCDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.dto.TarjetasCreditoDTO;
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
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.NoResultException;
import oracle.jdbc.OracleTypes;
import org.apache.log4j.Logger;

/**
 * Clase que implementa la interfaz TdcDAOs
 *
 * @author renseld.lugo
 */
@Named("TdcDAO")
@Stateless
@NotificationServiceType(NotificationServiceType.ServiceType.SERVICES)
public class TdcDAOImpl extends DAOUtil implements TdcDAO {

    @EJB
    private ActuacionesDAO actuacionesDAO;

    private static final Logger logger = Logger.getLogger(TdcDAOImpl.class.getName());

    /**
     * *
     * Metodo que retorna los datos de tarjetas de credito asociadas a un
     * cliente.
     *
     * @param iNroCedula numero de ci del cliente
     * @param iCodCanal canal por el cual se realiza la consulta
     * @return Objeto de tipo cliente con la lista de TDC asociadas a el.
     */
    @Override
    public ClienteDTO listadoTdcPorCliente(String iNroCedula, String iCodCanal) {
        ClienteDTO cliente = new ClienteDTO();
        RespuestaDTO respuesta = new RespuestaDTO();
        List<TarjetasCreditoDTO> tarjetas = new ArrayList<>();
        TarjetasCreditoDTO tarjeta;

        try {
            respuesta = this.conectarJNDI(JNDI_ORACLE_9, iCodCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            respuesta = this.generarQuery("IB_K_TARJETAS_DE_CREDITO", "IB_P_OBTENER_LISTADO_TDC", 3, iCodCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }

            //seteo de parametros de entrada y/o salida            
            statement.setInt(1, Integer.parseInt(iNroCedula));
            statement.setString(2, iCodCanal);
            statement.registerOutParameter(3, OracleTypes.CURSOR);
            this.ejecuto = statement.execute();

            objResultSet = (ResultSet) statement.getObject(3);
            while (objResultSet.next()) {
                tarjeta = new TarjetasCreditoDTO();
                tarjeta.setNumeroTarjeta(this.getCharSetOracle9(objResultSet, "nroTarjeta"));
                tarjeta.setEstatus(this.getCharSetOracle9(objResultSet, "estado"));
                tarjetas.add(tarjeta);
            }
            cliente.setTdcAsociadasCliente(tarjetas);

            if (tarjetas.isEmpty()) {
                throw new NoResultException();
            }
        } catch (NoResultException e) {
            respuesta.setCodigoSP(CODIGO_SIN_RESULTADOS_JPA);//revisar el log
            respuesta.setTextoSP(TEXTO_SIN_RESULTADOS_JPA);//revisar el log
            logger.error( new StringBuilder("ERROR DAO EN listadoTdcPorCliente: ")
                    .append("iNroCedula-").append(iNroCedula)
                    .append("-CH-").append(iCodCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN listadoTdcPorCliente: ")
                    .append("iNroCedula-").append(iNroCedula)
                    .append("-CH-").append(iCodCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        } finally {
            cliente.setRespuesta(respuesta);
            this.cerrarConexion(iCodCanal);
        }

//        if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN listadoTdcPorCliente: ")
//                    .append("iNroCedula-").append(iNroCedula)
//                    .append("-CH-").append(iCodCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return cliente;

    }

    /**
     * Retorna los movimientos para la TDC seleccionada por parametros
     *
     * @param numeroTarjeta String Numero de Tarjeta a la cual se le van a
     * obtener los movimientos.
     * @param canal String Codigo del canal desde el cual es llamado el
     * procedimiento.
     * @param nroRegistros Nro maximo de registros que debe contener el listado.
     * @return TarjetasCreditoDTO la TDC con los movimientos encontrados.
     */
    @Override
    public TarjetasCreditoDTO listadoMovimientosTDC(String numeroTarjeta, String canal, String nroRegistros) {
        TarjetasCreditoDTO tarjeta = new TarjetasCreditoDTO();
        RespuestaDTO respuesta = new RespuestaDTO();
        MovimientoTDCDTO movimiento;
        List<MovimientoTDCDTO> movimientos = new ArrayList<>();

        tarjeta.setNumeroTarjeta(numeroTarjeta);

        try {
            respuesta = this.conectarJNDI(JNDI_ORACLE_9, canal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }

            respuesta = this.generarQuery("IB_K_TARJETAS_DE_CREDITO", "IB_P_OBTENER_MOV_X_FACTURAR", 3, canal);

            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }

            statement.setString(1, numeroTarjeta);
            statement.setString(2, canal);
            statement.registerOutParameter(3, OracleTypes.CURSOR);
            this.ejecuto = statement.execute();

            objResultSet = (ResultSet) statement.getObject(3);
            int total = 0;
            int registros = Integer.parseInt(nroRegistros);
            while (objResultSet.next()) {
                movimiento = new MovimientoTDCDTO();
                movimiento.setDescripcion(this.getCharSetOracle9(objResultSet, "descripcion"));
                movimiento.setFechaOperacion(formatearFechaStringADate(this.getCharSetOracle9(objResultSet, "fechaOperacion"), FORMATO_FECHA_SIMPLE));
                movimiento.setFechaRegistro(formatearFechaStringADate(this.getCharSetOracle9(objResultSet, "fechaRegistro"), FORMATO_FECHA_SIMPLE));
                movimiento.setMonto(objResultSet.getBigDecimal("monto"));
                movimientos.add(movimiento);
                total++;
                if (total == registros) {
                    break;
                }
            }
            tarjeta.setMovimientos(movimientos);
            if (movimientos.isEmpty()) {
                throw new NoResultException();
            }

        } catch (NoResultException e) {
            respuesta.setCodigoSP(CODIGO_SIN_RESULTADOS_JPA);//revisar el log
            respuesta.setTextoSP(TEXTO_SIN_RESULTADOS_JPA);//revisar el log
            logger.error( new StringBuilder("ERROR DAO EN listadoMovimientosTDC: ")
                    .append("TDC-").append(this.numeroCuentaFormato(numeroTarjeta))
                    .append("-CH-").append(canal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN listadoMovimientosTDC: ")
                    .append("TDC-").append(this.numeroCuentaFormato(numeroTarjeta))
                    .append("-CH-").append(canal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);
        } finally {
            tarjeta.setRespuesta(respuesta);
            this.cerrarConexion(canal);
        }
//        if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN listadoMovimientosTDC: ")
//                    .append("TDC-").append(this.numeroCuentaFormato(numeroTarjeta))
//                    .append("-CH-").append(canal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return tarjeta;
    }

    /**
     * Retorna el detalle de una TDC especifica.
     *
     * @param numeroTarjeta String con el numero de tarjeta
     * @param canal String Codigo del canal desde el cual es llamado el
     * procedimiento.
     * @return TarjetasCreditoDTO los datos detalles de la TDC
     */
    @Override
    public TarjetasCreditoDTO obtenerDetalleTDC(String numeroTarjeta, String canal) {
        TarjetasCreditoDTO tarjeta = new TarjetasCreditoDTO();
        RespuestaDTO respuesta = new RespuestaDTO();
        RespuestaDTO respuestaAct;
        int parametros = 4;

        tarjeta.setNumeroTarjeta(numeroTarjeta);

        try {
            respuesta = this.conectarJNDI(JNDI_ORACLE_9, canal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }

            respuesta = this.generarQuery("IB_K_TARJETAS_DE_CREDITO", "IB_P_OBTENER_DETALLE", parametros, canal);

            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }

            statement.setString(1, numeroTarjeta);
            statement.setString(2, canal);
            statement.registerOutParameter(3, OracleTypes.NUMBER);
            statement.registerOutParameter(4, OracleTypes.CURSOR);
            this.ejecuto = statement.execute();

            respuestaAct = actuacionesDAO.obtenerDescripcionSalidaSP(this.statement.getBigDecimal(3).toString(), canal);

            if (respuestaAct != null && respuestaAct.getCodigo().equalsIgnoreCase(CODIGO_RESPUESTA_EXITOSO) && respuestaAct.getCodigoSP().equalsIgnoreCase(this.CODIGO_RESPUESTA_EXITOSO)) {
                objResultSet = (ResultSet) statement.getObject(4);
                while (objResultSet.next()) {

                    tarjeta = new TarjetasCreditoDTO();
                    tarjeta.setNumeroTarjeta(this.getCharSetOracle9(objResultSet, "numeroTarjeta"));
                    tarjeta.setCodigoTipoProducto(this.getCharSetOracle9(objResultSet, "codigoTipoProducto"));
                    tarjeta.setNombreProducto(this.getCharSetOracle9(objResultSet, "nombreProducto"));
                    tarjeta.setSiglasTipoMoneda(this.getCharSetOracle9(objResultSet, "siglasTipoMoneda"));
                    tarjeta.setSaldoAlCorte(objResultSet.getBigDecimal("saldoAlCorte"));
                    tarjeta.setSaldoTotal(objResultSet.getBigDecimal("saldoTotal"));
                    tarjeta.setMontoDisponible(objResultSet.getBigDecimal("montoDisponible"));
                    tarjeta.setPagoMinimo(objResultSet.getBigDecimal("pagoMinimo"));
                    tarjeta.setFechaLimite(this.getUtilDate(objResultSet, "fechaLimite"));
                    tarjeta.setFechaCorte(this.getUtilDate(objResultSet, "fechaCorte"));
                    tarjeta.setLimiteCredito(objResultSet.getBigDecimal("limiteCredito"));

                }

                if (tarjeta.getNumeroTarjeta() == null || tarjeta.getNumeroTarjeta().isEmpty() || tarjeta.getNumeroTarjeta().trim().equalsIgnoreCase("")) {
                    respuesta.setCodigoSP(CODIGO_SIN_RESULTADOS_JPA);//revisar el log
                    throw new NoResultException();
                }
            } else {
                respuesta.setCodigoSP(respuestaAct.getCodigoSP());
                respuesta.setDescripcionSP(respuestaAct.getDescripcionSP());

            }

        } catch (NoResultException e) {
            respuesta.setCodigoSP(CODIGO_SIN_RESULTADOS_JPA);
            respuesta.setTextoSP(TEXTO_SIN_RESULTADOS_JPA);
            logger.error( new StringBuilder("ERROR DAO EN obtenerDetalleTDC: ")
                    .append("TDC-").append(this.numeroCuentaFormato(numeroTarjeta))
                    .append("-CH-").append(canal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN obtenerDetalleTDC: ")
                    .append("TDC-").append(this.numeroCuentaFormato(numeroTarjeta))
                    .append("-CH-").append(canal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);
        } finally {
            tarjeta.setRespuesta(respuesta);
            this.cerrarConexion(canal);
        }
//        if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN obtenerDetalleTDC: ")
//                    .append("TDC-").append(this.numeroCuentaFormato(numeroTarjeta))
//                    .append("-CH-").append(canal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return tarjeta;
    }

    /**
     * Metodo que obtiene el listado de TDC propias por cliente
     *
     * @param nroCedula String Numero de CI del cliente
     * @param nombreCanal String Nombre del canal
     * @return ClienteDTO -> con List< TarjetasCreditoDTO >
     */
    @Override
    public ClienteDTO listadoTdcPropias(String nroCedula, String nombreCanal) {
        ClienteDTO clienteDTO = new ClienteDTO();
        RespuestaDTO respuesta = new RespuestaDTO();
        TarjetasCreditoDTO tdcTemp;
        List<TarjetasCreditoDTO> listTdc = new ArrayList<>();
        try {
            clienteDTO = listadoTdcPorCliente(nroCedula, nombreCanal);
            if (!clienteDTO.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            } else if (clienteDTO.getRespuesta().getCodigoSP().equals(CODIGO_SIN_RESULTADOS_JPA)) {
                throw new NoResultException();
            } else {
                for (TarjetasCreditoDTO tdcDTO : clienteDTO.getTdcAsociadasCliente()) {
                    tdcTemp = obtenerDetalleTDC(tdcDTO.getNumeroTarjeta(), nombreCanal);
                    listTdc.add(tdcTemp);
                }
                clienteDTO.setTdcAsociadasCliente(listTdc);
            }

        } catch (NoResultException e) {
            respuesta.setCodigoSP(CODIGO_SIN_RESULTADOS_JPA);//revisar el log
            respuesta.setTextoSP(TEXTO_SIN_RESULTADOS_JPA);//revisar el log
            logger.error( new StringBuilder("ERROR DAO EN listadoTdcPropias: ")
                    .append("CI-").append(nroCedula)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN listadoTdcPropias: ")
                    .append("CI-").append(nroCedula)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);
        } finally {
            clienteDTO.setRespuesta(respuesta);
        }
//        if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN listadoTdcPropias: ")
//                    .append("CI-").append(nroCedula)
//                    .append("-CH-").append(nombreCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return clienteDTO;
    }

    /**
     * Metodo que valida que el instrumento pertenece al cliente.
     *
     * @param codigoCliente codigo del cliente Oracle9
     * @param numeroTarjeta numero de tarjeta
     * @param codigoCanal codigo de canal
     * @return UtilDTO Retorna los datos de la tarjeta del cliente
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
            respuesta = this.generarQuery("IB_K_TARJETAS_DE_CREDITO ", "IB_P_CLIENTE_TARJETAS", 4, codigoCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            //seteo de parametros de entrada y/o salida
            statement.setInt(1, Integer.parseInt(codigoCliente));
            statement.setString(2, numeroTarjeta);
            statement.setString(3, codigoCanal);
            statement.registerOutParameter(4, OracleTypes.VARCHAR);
            this.ejecuto = statement.execute();

            if (new String(this.statement.getBytes(4), CHARSET_ORACLE_9).equalsIgnoreCase("V")) {
                resultados.put(1, true);
            } else {
                resultados.put(1, false); //F
            }
            utilDTO.setResulados(resultados);

        } catch (DAOException e) {
            logger.error( new StringBuilder("ERROR DAO EN obtenerClienteTarjeta: ")
                    .append("USR-").append(codigoCliente)
                    .append("-CH-").append(codigoCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
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
     * Retorna los movimientos para la TDC seleccionada por mes
     *
     * @param numeroTarjeta String Numero de Tarjeta a la cual se le van a
     * obtener los movimientos.
     * @param mes
     * @param anno
     * @param codigoCanal String Codigo del canal desde el cual es llamado el
     * procedimiento.
     * @return TarjetasCreditoDTO la TDC con los movimientos encontrados.
     */
    @Override
    public TarjetasCreditoDTO listadoMovimientosTDCMes(String numeroTarjeta, int mes, int anno, String codigoCanal) {
        TarjetasCreditoDTO tarjeta = new TarjetasCreditoDTO();
        RespuestaDTO respuesta = new RespuestaDTO();
        MovimientoTDCDTO movimiento;
        List<MovimientoTDCDTO> movimientos = new ArrayList<>();

        tarjeta.setNumeroTarjeta(numeroTarjeta);

        try {
            respuesta = this.conectarJNDI(JNDI_ORACLE_9, codigoCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }

            respuesta = this.generarQuery("IB_K_TARJETAS_DE_CREDITO", "IB_P_MOV_DETALLE", 7, codigoCanal);

            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }

            statement.setString(1, numeroTarjeta);
            statement.setInt(2, mes);
            statement.setInt(3, anno);
            statement.setString(4, codigoCanal);
            statement.registerOutParameter(5, OracleTypes.CURSOR);
            statement.registerOutParameter(6, OracleTypes.VARCHAR); //ovCodigoError manejo interno de BD ignorar este parametro
            statement.registerOutParameter(7, OracleTypes.INTEGER); //onCodSalida estatus de ejecucion
            this.ejecuto = statement.execute();

            //Ajuste por error al devolver cuentas sin chequeras activas y/o disponibles
            respuesta = actuacionesDAO.obtenerDescripcionSalidaSP(String.valueOf(this.statement.getInt(7)), codigoCanal);

            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            } else {
                if (respuesta.getCodigoSP().equals(CODIGO_RESPUESTA_EXITOSO)) {
                    objResultSet = (ResultSet) statement.getObject(5);
                    String guion;
                    BigDecimal neg = new BigDecimal(-1);
                    while (objResultSet.next()) {
                        movimiento = new MovimientoTDCDTO();
                        movimiento.setDescripcion(this.getCharSetOracle9(objResultSet, "concepto_transaccion"));
                        movimiento.setFechaString(this.getCharSetOracle9(objResultSet, "fecha"));
                        movimiento.setFechaRegString(this.getCharSetOracle9(objResultSet, "fecha_registro"));
                        movimiento.setFechaTransString(this.getCharSetOracle9(objResultSet, "fecha_transaccion"));
                        guion = this.getCharSetOracle9(objResultSet, "guion");
                        if(guion != null && guion.trim().equalsIgnoreCase("-")){
                            movimiento.setMonto(objResultSet.getBigDecimal("valor_transaccion").multiply(neg));
                        }else{
                            movimiento.setMonto(objResultSet.getBigDecimal("valor_transaccion"));
                        }                        
                        movimiento.setMontoDivisa(objResultSet.getBigDecimal("valor_moneda_extranjera"));
                        movimiento.setIntereses(objResultSet.getBigDecimal("interes_retributivos_trans"));
                        movimiento.setReferencia(this.getCharSetOracle9(objResultSet, "referencia"));
                        movimientos.add(movimiento);
                    }
                }
            }
            tarjeta.setMovimientos(movimientos);
            if (movimientos.isEmpty()) {
                throw new NoResultException();
            }

        } catch (NoResultException e) {
            respuesta.setCodigoSP(CODIGO_SIN_RESULTADOS_JPA);//revisar el log
            respuesta.setTextoSP(TEXTO_SIN_RESULTADOS_JPA);//revisar el log
            logger.error( new StringBuilder("ERROR DAO EN listadoMovimientosTDCMes: ")
                    .append("TDC-").append(this.numeroCuentaFormato(numeroTarjeta))
                    .append("-CH-").append(codigoCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN listadoMovimientosTDCMes: ")
                    .append("TDC-").append(this.numeroCuentaFormato(numeroTarjeta))
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
//            logger.error( new StringBuilder("EXITO DAO EN listadoMovimientosTDCMes: ")
//                    .append("TDC-").append(this.numeroCuentaFormato(numeroTarjeta))
//                    .append("-CH-").append(codigoCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return tarjeta;
    }
}
