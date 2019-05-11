/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.daoimpl;

import com.bds.ws.dao.ActuacionesDAO;
import com.bds.ws.dao.ClienteDAO;
import com.bds.ws.dao.NotificationServiceType;
import com.bds.ws.dao.NotificationServiceType.ServiceType;
import com.bds.ws.dto.ClienteDTO;
import com.bds.ws.dto.CuentaDTO;
import com.bds.ws.dto.DepositoPlazoDTO;
import com.bds.ws.dto.FideicomisoDTO;
import com.bds.ws.dto.PosicionConsolidadaDTO;
import com.bds.ws.dto.PrestamoDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.dto.TarjetasCreditoDTO;
import com.bds.ws.dto.UtilDTO;
import com.bds.ws.exception.DAOException;
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
 * Clase que implemente ClienteDAO
 *
 * @author cesar.mujica
 */
@Named("ClienteDAO")
@Stateless
@NotificationServiceType(ServiceType.SERVICES)
public class ClienteDAOImpl extends DAOUtil implements ClienteDAO {

    @EJB
    public ActuacionesDAO actuaciones;

    /**
     * Log de sistema.
     */
    private static final Logger logger = Logger.getLogger(ClienteDAOImpl.class.getName());

    /**
     * retorna los datos basicos del cliente mediante el codigo y el canal
     *
     * @param iCodigoCliente codigo del cliente
     * @param canal canal por el cual se realiza la consulta
     * @param codigoCanal Codigo de canal por el cual se realiza la consulta
     * @return ClienteDTO datos del cliente en core bancario
     */
    @Override
    public ClienteDTO consultarDatosCliente(String iCodigoCliente, String canal, String codigoCanal) {
        ClienteDTO cliente = new ClienteDTO();
        RespuestaDTO respuesta = new RespuestaDTO();

        try {
            respuesta = this.conectarJNDI(JNDI_ORACLE_9, canal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            respuesta = this.generarQuery("IB_K_CLIENTES", "IB_P_OBT_DATOS_CLIENTE_IB", 4, canal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            //seteo de parametros de entrada y/o salida
            statement.setInt(1, Integer.parseInt(iCodigoCliente));
            statement.setString(2, canal);
            statement.registerOutParameter(3, OracleTypes.CURSOR);
            statement.registerOutParameter(4, OracleTypes.NUMBER);
            this.ejecuto = statement.execute();

            String codSalida = this.statement.getBigDecimal(4).toString();

            if (codSalida != null || !codSalida.isEmpty() || !codSalida.equalsIgnoreCase("")) {
                respuesta = actuaciones.obtenerDescripcionSalidaSP(codSalida, codigoCanal);
                if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO) || !respuesta.getCodigoSP().equals(CODIGO_RESPUESTA_EXITOSO)) {
                    throw new DAOException();
                } else {

                    objResultSet = (ResultSet) statement.getObject(3);
                    while (objResultSet.next()) {
                        cliente.setCodigoCliente(iCodigoCliente);
                        cliente.setIdentificacion(this.getCharSetOracle9(objResultSet, "identificacion"));
                        cliente.setNombres(this.getCharSetOracle9(objResultSet, "nombres"));
                        cliente.setApellidos(this.getCharSetOracle9(objResultSet, "apellidos"));
                        cliente.setEmailCorreo(this.getCharSetOracle9(objResultSet, "email"));
                        cliente.setCodigoTlfCell(this.getCharSetOracle9(objResultSet, "codigocell"));
                        cliente.setTelefonoCell(this.getCharSetOracle9(objResultSet, "telefonocell"));
                        cliente.setEnviarSms(this.getCharSetOracle9(objResultSet, "enviarsms"));
                        cliente.setEnviarCorreo(this.getCharSetOracle9(objResultSet, "enviarcorreo"));
                        cliente.setLimiteInternas(objResultSet.getBigDecimal("limiteInternas"));
                        cliente.setLimiteExternas(objResultSet.getBigDecimal("limiteExternas"));
                        cliente.setLimiteInternasTerc(objResultSet.getBigDecimal("limiteInternasTerceros"));
                        cliente.setLimiteExternasTerc(objResultSet.getBigDecimal("limiteExternasTerceros"));
                    }
                }
            }
            cliente.setRespuesta(respuesta);

        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN consultarDatosCliente: ").append("USR-").append(iCodigoCliente)
                    .append("-CH-").append(canal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
            cliente.setRespuesta(respuesta);
        } finally {
            cliente.setRespuesta(respuesta);
            this.cerrarConexion(canal);
        }
//        if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN consultarDatosCliente: ").append("USR-").append(iCodigoCliente)
//                    .append("-CH-").append(canal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return cliente;
    }

    /**
     * retorna los datos de los productos del cliente
     *
     * @param codigoCliente codigo del cliente
     * @param canal canal por el cual se realiza la consulta
     * @return PosicionConsolidadaDTO datos de productos de cliente en core
     * bancario
     */
    @Override
    public PosicionConsolidadaDTO consultarPosicionConsolidadaCliente(String codigoCliente, String canal) {
        PosicionConsolidadaDTO consolidada = new PosicionConsolidadaDTO();
        RespuestaDTO respuesta = new RespuestaDTO();
        BigDecimal totalDisp = BigDecimal.ZERO;
        BigDecimal saldoTotalTDC = BigDecimal.ZERO;
        CuentaDTO cuenta = null;
        PrestamoDTO prestamo = null;
        DepositoPlazoDTO depositoPlazo = null;
        TarjetasCreditoDTO tarjeta = null;
        FideicomisoDTO fidecomiso = null;
        List<CuentaDTO> cuentas = new ArrayList<>();
        List<PrestamoDTO> prestamos = new ArrayList<>();
        List<DepositoPlazoDTO> depositosPlazo = new ArrayList<>();
        List<TarjetasCreditoDTO> tarjetasCredito = new ArrayList<>();
        List<FideicomisoDTO> fidecomisos = new ArrayList<>();
        try {
            respuesta = this.conectarJNDI(JNDI_ORACLE_9, canal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            respuesta = this.generarQuery("IB_K_CLIENTES", "IB_P_OBTENER_LISTADO_PRODUCTOS", 9, canal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            //seteo de parametros de entrada y/o salida
            statement.setInt(1, Integer.parseInt(codigoCliente));
            statement.setString(2, canal);
            statement.registerOutParameter(3, OracleTypes.CURSOR);
            statement.registerOutParameter(4, OracleTypes.CURSOR);
            statement.registerOutParameter(5, OracleTypes.CURSOR);
            statement.registerOutParameter(6, OracleTypes.CURSOR);
            statement.registerOutParameter(7, OracleTypes.CURSOR);
            statement.registerOutParameter(8, OracleTypes.CURSOR);
            statement.registerOutParameter(9, OracleTypes.CURSOR);
            this.ejecuto = statement.execute();

            //se obtiene la informacion de las cuentas de ahorro
            objResultSet = (ResultSet) statement.getObject(3);
            while (objResultSet.next()) {
                cuenta = new CuentaDTO();
                cuenta.setNombreProducto(this.getCharSetOracle9(objResultSet, "nombreProducto"));
                cuenta.setNumeroCuenta(this.getCharSetOracle9(objResultSet, "numeroCuenta"));
                cuenta.setCodigoTipoProducto(this.getCharSetOracle9(objResultSet, "codigoTipoProducto"));
                cuenta.setSiglasTipoMoneda(this.getCharSetOracle9(objResultSet, "siglasTipoMoneda"));
                cuenta.setSaldoDisponible(this.objResultSet.getBigDecimal("saldoDisponible"));
                //totalizacion de saldos
                totalDisp = totalDisp.add(cuenta.getSaldoDisponible());
                cuentas.add(cuenta);
            }
            consolidada.setCuentasAhorro(cuentas);
            consolidada.setTotalDispCuentasAhorro(totalDisp);
            totalDisp = BigDecimal.ZERO;
            cuentas = new ArrayList<>();

            //se obtiene la informacion de las cuentas corrientes
            objResultSet.close();
            objResultSet = (ResultSet) statement.getObject(4);
            while (objResultSet.next()) {
                cuenta = new CuentaDTO();
                cuenta.setNombreProducto(this.getCharSetOracle9(objResultSet, "nombreProducto"));
                cuenta.setNumeroCuenta(this.getCharSetOracle9(objResultSet, "numeroCuenta"));
                cuenta.setCodigoTipoProducto(this.getCharSetOracle9(objResultSet, "codigoTipoProducto"));
                cuenta.setSiglasTipoMoneda(this.getCharSetOracle9(objResultSet, "siglasTipoMoneda"));
                cuenta.setSaldoDisponible(this.objResultSet.getBigDecimal("saldoDisponible"));
                //totalizacion de saldos
                totalDisp = totalDisp.add(cuenta.getSaldoDisponible());
                cuentas.add(cuenta);
            }
            consolidada.setCuentasCorriente(cuentas);
            consolidada.setTotalDispCuentasCorriente(totalDisp);
            totalDisp = BigDecimal.ZERO;
            cuentas = new ArrayList<>();

            //se obtiene la informacion de las cuentas en moneda extrajera
            objResultSet.close();
            objResultSet = (ResultSet) statement.getObject(5);
            while (objResultSet.next()) {
                cuenta = new CuentaDTO();
                cuenta.setNombreProducto(this.getCharSetOracle9(objResultSet, "nombreProducto"));
                cuenta.setNumeroCuenta(this.getCharSetOracle9(objResultSet, "numeroCuenta"));
                cuenta.setCodigoTipoProducto(this.getCharSetOracle9(objResultSet, "codigoTipoProducto"));
                cuenta.setSiglasTipoMoneda(this.getCharSetOracle9(objResultSet, "moneda"));
                cuenta.setSaldoDisponible(this.objResultSet.getBigDecimal("saldoDisponible"));
                //totalizacion de saldos
                totalDisp = totalDisp.add(cuenta.getSaldoDisponible());
                cuentas.add(cuenta);
            }
            consolidada.setCuentasME(cuentas);
            consolidada.setTotalDispCuentasME(totalDisp);
            totalDisp = BigDecimal.ZERO;

            //se obtiene la informacion de los prestamos
            objResultSet.close();
            objResultSet = (ResultSet) statement.getObject(6);
            while (objResultSet.next()) {
                prestamo = new PrestamoDTO();
                prestamo.setNombreProducto(this.getCharSetOracle9(objResultSet, "nombreProducto"));
                prestamo.setNumeroPrestamo(this.getCharSetOracle9(objResultSet, "numeroPrestamo"));
                prestamo.setCodigoTipoProducto(this.getCharSetOracle9(objResultSet, "codigoTipoProducto"));
                prestamo.setSiglasTipoMoneda(this.getCharSetOracle9(objResultSet, "siglasTipoMoneda"));
                prestamo.setSaldoPrestamo(this.objResultSet.getBigDecimal("saldoPrestamo"));
                //totalizacion de saldos
                totalDisp = totalDisp.add(prestamo.getSaldoPrestamo());
                prestamos.add(prestamo);
            }
            consolidada.setPrestamos(prestamos);
            consolidada.setTotalDispPrestamos(totalDisp);
            totalDisp = BigDecimal.ZERO;

            //se obtiene la informacion de los depositos a plazo
            objResultSet.close();
            objResultSet = (ResultSet) statement.getObject(7);
            while (objResultSet.next()) {
                depositoPlazo = new DepositoPlazoDTO();
                depositoPlazo.setNombreProducto(this.getCharSetOracle9(objResultSet, "nombreProducto"));
                depositoPlazo.setNumeroDeposito(this.getCharSetOracle9(objResultSet, "numeroCuenta"));
                depositoPlazo.setCodigoTipoProducto(this.getCharSetOracle9(objResultSet, "codigoTipoProducto"));
                depositoPlazo.setSiglasTipoMoneda(this.getCharSetOracle9(objResultSet, "siglasTipoMoneda"));
                depositoPlazo.setSaldoDisponible(this.objResultSet.getBigDecimal("saldoDisponible"));
                //totalizacion de saldos
                totalDisp = totalDisp.add(depositoPlazo.getSaldoDisponible());
                depositosPlazo.add(depositoPlazo);
            }
            consolidada.setDepositosPlazo(depositosPlazo);
            consolidada.setTotalDispDepositosPlazo(totalDisp);
            totalDisp = BigDecimal.ZERO;

            //se obtiene la informacion de las TDC
            objResultSet.close();
            objResultSet = (ResultSet) statement.getObject(8);
            while (objResultSet.next()) {
                tarjeta = new TarjetasCreditoDTO();
                tarjeta.setNombreProducto(this.getCharSetOracle9(objResultSet, "nombreProducto"));
                tarjeta.setNumeroTarjeta(this.getCharSetOracle9(objResultSet, "numeroTarjeta"));
                tarjeta.setCodigoTipoProducto(this.getCharSetOracle9(objResultSet, "codigoTipoProducto"));
                tarjeta.setSiglasTipoMoneda(this.getCharSetOracle9(objResultSet, "siglasTipoMoneda"));
                tarjeta.setSaldoDisponible(this.objResultSet.getBigDecimal("saldoDisponible"));
                tarjeta.setSaldoTotal(this.objResultSet.getBigDecimal("saldoTotal"));
                //totalizacion de saldos
                totalDisp = totalDisp.add(tarjeta.getSaldoDisponible());
                saldoTotalTDC = saldoTotalTDC.add(tarjeta.getSaldoTotal());
                tarjetasCredito.add(tarjeta);
            }
            consolidada.setTarjetasCredito(tarjetasCredito);
            consolidada.setTotalDispTDC(totalDisp);
            consolidada.setSaldoTotalTDC(saldoTotalTDC);

            //se obtiene la informacion de los Fideicomisos
            objResultSet.close();
            objResultSet = (ResultSet) statement.getObject(9);
            while (objResultSet.next()) {
                fidecomiso = new FideicomisoDTO();
                fidecomiso.setCodigoPlan(this.getCharSetOracle9(objResultSet, "codigoPlan"));
                fidecomiso.setNombrePlan(this.getCharSetOracle9(objResultSet, "nombrePlan"));
                fidecomiso.setTipoPlan(this.getCharSetOracle9(objResultSet, "tipoPlan"));
                fidecomiso.setSaldoDisponible(this.objResultSet.getBigDecimal("saldoDisponible"));
                fidecomiso.setTotalAporte(this.objResultSet.getBigDecimal("totalAporte"));
                fidecomiso.setTotalRetiro(this.objResultSet.getBigDecimal("totalRetiro"));
                //totalizacion de saldos
                fidecomisos.add(fidecomiso);
            }
            consolidada.setFideicomisos(fidecomisos);
            
            consolidada.setRespuesta(respuesta);

        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN consultarPosicionConsolidada: ")
                    .append("codigoCliente-").append(codigoCliente)
                    .append("-CH-").append(canal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
            consolidada.setRespuesta(respuesta);
        } finally {
            consolidada.setRespuesta(respuesta);
            this.cerrarConexion(canal);
        }
//        if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.log(Level.INFO, new StringBuilder("EXITO DAO EN consultarPosicionConsolidada: ")
//                    .append("codigoCliente-").append(codigoCliente)
//                    .append("-CH-").append(canal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }

        return consolidada;
    }

    /**
     * Obtiene los datos de un cliente del Internet Banking. Que valide la
     * existencia del cliente.
     *
     * @param codigoCliente codigo del cliente
     * @param codigoCanal nombre del canal
     * @return UtilDTO existe -> true en caso de existir el cliente, false en
     * caso contrario
     */
    @Override
    public UtilDTO validarCliente(String codigoCliente, String codigoCanal) {
        UtilDTO utilDTO = new UtilDTO();
        RespuestaDTO respuesta = new RespuestaDTO();
        Map resultado = new HashMap();
        String identificacion = null;
        try {
            respuesta = this.conectarJNDI(JNDI_ORACLE_9, codigoCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }

            respuesta = this.generarQuery("IB_K_CLIENTES", "IB_P_OBT_DATOS_CLIENTE_IB", 3, codigoCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            //seteo de parametros de entrada y/o salida
            statement.setInt(1, Integer.parseInt(codigoCliente));
            statement.setString(2, codigoCanal);
            statement.registerOutParameter(3, OracleTypes.CURSOR);
            this.ejecuto = statement.execute();

            objResultSet = (ResultSet) statement.getObject(3);
            while (objResultSet.next()) {
                identificacion = this.getCharSetOracle9(objResultSet, "IDENTIFICACION");
            }

            if (identificacion == null || identificacion.isEmpty()) {
                throw new DAOException();
            } else {
                resultado.put("existe", true);
            }

            utilDTO.setResulados(resultado);
            utilDTO.setRespuesta(respuesta);

        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN validarCliente: ")
                    .append("COD-").append(codigoCliente)
                    .append("-CH-").append(codigoCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
            utilDTO.setRespuesta(respuesta);
        } finally {
            utilDTO.setRespuesta(respuesta);
            this.cerrarConexion(codigoCanal);
        }
//        if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN validarCliente: ")
//                    .append("COD-").append(codigoCliente)
//                    .append("-CH-").append(codigoCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return utilDTO;
    }

    /**
     * Realiza la validacion de la clave con la ficha del cliente.
     *
     * @param codigoCliente
     * @param claveCifrada
     * @param codigoCanal
     * @return UtilDTO Datos de validacion de la clave
     */
    @Override
    public UtilDTO validarClaveFondo(String codigoCliente, String claveCifrada, String codigoCanal) {
        UtilDTO utilDTO = new UtilDTO();
        RespuestaDTO respuesta = new RespuestaDTO();
        Map resultados = new HashMap();

        try {

            if (codigoCliente == null || codigoCliente.isEmpty() || codigoCliente.equalsIgnoreCase("")
                    || claveCifrada == null || claveCifrada.isEmpty() || claveCifrada.equalsIgnoreCase("")
                    || codigoCanal == null || codigoCanal.isEmpty() || codigoCanal.equalsIgnoreCase("")) {
                respuesta.setCodigo("DAO008");
                throw new DAOException("DAO008");
            }

            respuesta = this.conectarJNDI(JNDI_ORACLE_9, codigoCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            respuesta = this.generarQuery("IB_K_CLIENTES", "IB_P_VALIDAR_CLAVE_FONDO", 4, codigoCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            //seteo de parametros de entrada y/o salida
            statement.setInt(1, Integer.parseInt(codigoCliente));
            statement.setString(2, claveCifrada);
            statement.setString(3, codigoCanal);
            statement.registerOutParameter(4, OracleTypes.NUMBER);
            this.ejecuto = statement.execute();

            String codSalida = this.statement.getBigDecimal(4).toString();

            if (codSalida != null || !codSalida.isEmpty() || !codSalida.equalsIgnoreCase("")) {
                respuesta = actuaciones.obtenerDescripcionSalidaSP(codSalida, codigoCanal);
                if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                    throw new DAOException();
                } else if (!respuesta.getCodigoSP().equals(CODIGO_RESPUESTA_EXITOSO)) {
                    resultados.put("respuesta", false);
                } else {
                    resultados.put("respuesta", true);
                }
            } else {
                resultados.put("respuesta", false);

                throw new NoResultException();
            }

        } catch (NoResultException e) {
            respuesta.setCodigoSP(CODIGO_SIN_RESULTADOS_JPA);//revisar el log
            respuesta.setTextoSP(TEXTO_SIN_RESULTADOS_JPA);
            logger.error( new StringBuilder("ERROR DAO EN validarClaveFondo: ").append("CODUSR-").append(codigoCliente)
                    .append("-CH-").append(codigoCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());

        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN validarClaveFondo: ").append("CODUSR-").append(codigoCliente)
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
//            logger.error( new StringBuilder("EXITO DAO EN validarClaveFondo: ").append("CODUSR-").append(codigoCliente)
//                    .append("-CH-").append(codigoCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        utilDTO.setResulados(resultados);
        return utilDTO;
    }

    /**
     * Actualiza los el telefono movil y el email del cliente
     *
     * @param codigoCliente codigo del cliente
     * @param celular numero del telefono celular a actualizar
     * @param ivCodExtCanal codigo del canal
     * @param email email a actualizar
     * @return UtilDTO
     */
    @Override
    public UtilDTO actualizarDatosContacto(String codigoCliente, String celular, String email, String ivCodExtCanal) {
        UtilDTO utilDTO = new UtilDTO();
        RespuestaDTO respuesta = new RespuestaDTO();
        RespuestaDTO respuestaActuaciones = new RespuestaDTO();
        Map resultados = new HashMap();

        try {

            if (codigoCliente == null || codigoCliente.isEmpty() || codigoCliente.equalsIgnoreCase("")
                    || ivCodExtCanal == null || ivCodExtCanal.isEmpty() || ivCodExtCanal.equalsIgnoreCase("")) {
                respuesta.setCodigo("DAO008");
                throw new DAOException("DAO008");
            }

            respuesta = this.conectarJNDI(JNDI_ORACLE_9, ivCodExtCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            respuesta = this.generarQuery("IB_K_CLIENTES", "IB_P_ACTUALIZAR_DATOS_CONTACTO", 5, ivCodExtCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            //seteo de parametros de entrada y/o salida
            statement.setInt(1, Integer.parseInt(codigoCliente));
            statement.setString(2, celular);
            statement.setString(3, email);
            statement.setString(4, ivCodExtCanal);
            statement.registerOutParameter(5, OracleTypes.INTEGER);
            this.ejecuto = statement.execute();

            if (!this.ejecuto) {
                resultados.put("update", true);
            }

            respuesta = actuaciones.obtenerDescripcionSalidaSP(String.valueOf(this.statement.getInt(8)), ivCodExtCanal);

        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN actualizarDatosContacto: ")
                    .append("COD-").append(codigoCliente)
                    .append("-CH-").append(ivCodExtCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
            utilDTO.setRespuesta(respuesta);
        } finally {
            utilDTO.setRespuesta(respuesta);
            this.cerrarConexion(ivCodExtCanal);
        }
//        if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN actualizarDatosContacto: ")
//                    .append("COD-").append(codigoCliente)
//                    .append("-CH-").append(ivCodExtCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return utilDTO;
    }

    /**
     * Metodo para validar el rif de un cliente
     *
     * @param rif rif 'v1234567890'
     * @param codigoCanal nombre del canal
     * @return retorna 'S' para casos validos, 'N' en caso contrario.
     */
    @Override
    public UtilDTO validarRif(String rif, String codigoCanal) {
        UtilDTO utilDTO = new UtilDTO();
        RespuestaDTO respuesta = new RespuestaDTO();
        Map resultados = new HashMap();

        try {

            if (rif == null || rif.isEmpty() || rif.equalsIgnoreCase("")
                    || codigoCanal == null || codigoCanal.isEmpty() || codigoCanal.equalsIgnoreCase("")) {
                respuesta.setCodigo("DAO008");
                throw new DAOException("DAO008");
            }

            respuesta = this.conectarJNDI(JNDI_ORACLE_9, codigoCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            respuesta = this.generarQuery("IB_K_RIF", "IB_P_VALIDAR_RIF", 3, codigoCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            //seteo de parametros de entrada y/o salida            
            statement.setString(1, rif);
            statement.setString(2, codigoCanal);
            statement.registerOutParameter(3, OracleTypes.VARCHAR);
            this.ejecuto = statement.execute();

            resultados.put("resultado", new String(this.statement.getBytes(3), CHARSET_ORACLE_9));

            utilDTO.setResulados(resultados);
            utilDTO.setRespuesta(respuesta);

        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN validarRif: ")
                    .append("RIF-").append(rif)
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
//            logger.error( new StringBuilder("EXITO DAO EN validarRif: ")
//                    .append("RIF-").append(rif)
//                    .append("-CH-").append(codigoCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return utilDTO;
    }

    /**
     * Metodo para Valida cliente con Identificacion cliente y No. De cuenta
     *
     * @param identificacion identificacion del cliente se debe incluir letra
     * ej: v123456
     * @param nroCuenta numero de cuenta del cliente
     * @param codigoCanal nombre del canal
     * @return retorna 'V' para casos validos, 'F' en caso contrario.
     */
    @Override
    public UtilDTO validarIdentificacionCuenta(String identificacion, String nroCuenta, String codigoCanal) {
        UtilDTO utilDTO = new UtilDTO();
        RespuestaDTO respuesta = new RespuestaDTO();
        Map resultados = new HashMap();

        try {

            if (identificacion == null || identificacion.isEmpty() || identificacion.equalsIgnoreCase("")
                    || nroCuenta == null || nroCuenta.isEmpty() || nroCuenta.equalsIgnoreCase("")
                    || codigoCanal == null || codigoCanal.isEmpty() || codigoCanal.equalsIgnoreCase("")) {
                respuesta.setCodigo("DAO008");
                throw new DAOException("DAO008");
            }

            respuesta = this.conectarJNDI(JNDI_ORACLE_9, codigoCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            respuesta = this.generarQuery("IB_K_CLIENTES", "IB_P_VALIDAR_IDENTF_CUENTA", 4, codigoCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            //seteo de parametros de entrada y/o salida            
            statement.setString(1, nroCuenta);
            statement.setString(2, identificacion);
            statement.setString(3, codigoCanal);
            statement.registerOutParameter(4, OracleTypes.VARCHAR);
            this.ejecuto = statement.execute();

            resultados.put("resultado", new String(this.statement.getBytes(4), CHARSET_ORACLE_9));

            utilDTO.setResulados(resultados);

        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN validarIdentificacionCuenta: ")
                    .append("CI-").append(identificacion)
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
//            logger.error( new StringBuilder("EXITO DAO EN validarIdentificacionCuenta: ")
//                    .append("CI-").append(identificacion)
//                    .append("-CH-").append(codigoCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return utilDTO;
    }

    /**
     * Metodo para validar y obtener un codigo de usuario existente por numero
     * de cedula
     *
     * @param identificacion identificacion del cliente se debe incluir letra
     * ej: v123456
     * @param codigoCanal nombre del canal
     * @return en caso de existir retorna el parametro codUsuario en el mapa con
     * el valor correspondiente
     */
    @Override
    public UtilDTO obtenerCodCliente(String identificacion, String codigoCanal) {
        UtilDTO utilDTO = new UtilDTO();
        RespuestaDTO respuesta = new RespuestaDTO();
        Map resultados = new HashMap();

        try {

            if (identificacion == null || codigoCanal.equalsIgnoreCase("")) {
                respuesta.setCodigo("DAO008");
                throw new DAOException("DAO008");
            }

            respuesta = this.conectarJNDI(JNDI_ORACLE_9, codigoCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            respuesta = this.generarQuery("IB_K_CLIENTES", "IB_P_OBT_CODIGO_CLIENTE", 4, codigoCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            //seteo de parametros de entrada y/o salida            
            statement.setString(1, identificacion);
            statement.setString(2, codigoCanal);
            statement.registerOutParameter(3, OracleTypes.NUMBER);
            statement.registerOutParameter(4, OracleTypes.VARCHAR);
            this.ejecuto = statement.execute();

            String codSalida = new String(this.statement.getBytes(4), CHARSET_ORACLE_9);

            if (codSalida != null || !codSalida.isEmpty() || !codSalida.equalsIgnoreCase("")) {
                respuesta = actuaciones.obtenerDescripcionSalidaSP(codSalida, codigoCanal);
                if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO) || !respuesta.getCodigoSP().equals(CODIGO_RESPUESTA_EXITOSO)) {
                    throw new DAOException();
                } else {
                    resultados.put("codUsuario", this.statement.getBigDecimal(3).toString());
                }
            }

            utilDTO.setResulados(resultados);

        } catch (Exception e) {
            String descripcionSP = "";
            if(respuesta.getDescripcionSP() != null){
                descripcionSP = respuesta.getDescripcionSP();
            }
            logger.error( new StringBuilder("ERROR DAO EN obtenerCodCliente: ")
                    .append("CI-").append(identificacion)
                    .append("-CH-").append(codigoCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e)
                    .append(descripcionSP).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        } finally {
            utilDTO.setRespuesta(respuesta);
            this.cerrarConexion(codigoCanal);
        }
//        if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN obtenerCodCliente: ")
//                    .append("CI-").append(identificacion)
//                    .append("-CH-").append(codigoCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return utilDTO;
    }

    /**
     * valida que un producto pertenezca a un cliente
     *
     * @param codigoCliente codigo del cliente
     * @param nroProducto
     * @param codigoCanal nombre del canal
     * @return UtilDTO valido -> true en caso de existir el producto, false en
     * caso contrario
     */
    @Override
    public UtilDTO validarProductoCliente(String codigoCliente, String nroProducto, String codigoCanal) {
        UtilDTO utilDTO = new UtilDTO();
        RespuestaDTO respuesta = new RespuestaDTO();
        Map resultado = new HashMap();
        try {
            respuesta = this.conectarJNDI(JNDI_ORACLE_9, codigoCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            respuesta = this.generarQuery("IB_K_CLIENTES", "IB_P_VALIDA_PRODUCTO_CLI", 5, codigoCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            //seteo de parametros de entrada y/o salida
            statement.setInt(1, Integer.parseInt(codigoCliente));
            statement.setString(2, nroProducto);
            statement.setString(3, codigoCanal);
            statement.registerOutParameter(4, OracleTypes.VARCHAR);
            statement.registerOutParameter(5, OracleTypes.VARCHAR);
            this.ejecuto = statement.execute();

            String codSalida = new String(this.statement.getBytes(5), CHARSET_ORACLE_9);

            if ( !codSalida.isEmpty() || !codSalida.equalsIgnoreCase("")) {
                respuesta = actuaciones.obtenerDescripcionSalidaSP(codSalida, codigoCanal);
                if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO) || !respuesta.getCodigoSP().equals(CODIGO_RESPUESTA_EXITOSO)) {
                    throw new DAOException();
                } else {
                    if (this.statement.getString(4) == null || this.statement.getString(4).isEmpty()) {
                        throw new DAOException();
                    } else {
                        if (this.statement.getString(4).equalsIgnoreCase("V")) {
                            resultado.put("valido", true);
                        } else {
                            resultado.put("valido", false);
                        }
                    }
                }
            }

            utilDTO.setResulados(resultado);
            utilDTO.setRespuesta(respuesta);

        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN validarProductoCliente: ")
                    .append("COD-").append(codigoCliente)
                    .append("-CH-").append(codigoCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
            utilDTO.setRespuesta(respuesta);
        } finally {
            utilDTO.setRespuesta(respuesta);
            this.cerrarConexion(codigoCanal);
        }
//        if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN validarProductoCliente: ")
//                    .append("COD-").append(codigoCliente)
//                    .append("-CH-").append(codigoCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return utilDTO;
    }

}
