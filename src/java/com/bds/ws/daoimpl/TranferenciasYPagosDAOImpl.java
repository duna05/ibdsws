/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.daoimpl;

import com.bds.ws.dao.ActuacionesDAO;
import com.bds.ws.dao.NotificationServiceType;
import com.bds.ws.dao.TranferenciasYPagosDAO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.dto.TransaccionDTO;
import com.bds.ws.exception.DAOException;
import static com.bds.ws.util.BDSUtil.CODIGO_EXCEPCION_GENERICA;
import static com.bds.ws.util.BDSUtil.CODIGO_RESPUESTA_EXITOSO;
import static com.bds.ws.util.BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA;
import static com.bds.ws.util.BDSUtil.JNDI_ORACLE_9;
import com.bds.ws.util.DAOUtil;
import java.math.BigDecimal;
import java.util.Date;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Named;
import oracle.jdbc.OracleTypes;
import org.apache.log4j.Logger;

/**
 * Clase que implementa TranferenciasYPagosDAO
 * @author cesar.mujica
 */
@Named("TranferenciasYPagosDAO")
@Stateless
@NotificationServiceType(NotificationServiceType.ServiceType.SERVICES)
public class TranferenciasYPagosDAOImpl extends DAOUtil implements TranferenciasYPagosDAO {

    private static final Logger logger = Logger.getLogger(TranferenciasYPagosDAOImpl.class.getName());

    @EJB
    private ActuacionesDAO actuacionesDAO;

    /**
     * Metodo que se encarga de realizar la transferencia entre dos cuentas del
     * mismo banco
     *
     * @param cuentaOrigen Numero (de 20 digitos) de la cuenta origen de los
     * fondos.
     * @param cuentaDestino Numero de cuenta, de 20 digitos, destino de los
     * fondos.
     * @param monto Monto en Bs. de la transferencia.
     * @param descripcion Descripcion, de la transferencia, suministrada por el
     * cliente que la realiza.
     * @param canal Codigo (extendido) del canal desde el cual es llamado el
     * procedimiento.
     * @return TransaccionDTO objeto de respuesta con el codigo del resultado de
     * la transaccion
     */
    @Override
    public TransaccionDTO transferenciaMismoBanco(String cuentaOrigen, String cuentaDestino, BigDecimal monto, String descripcion, String canal) {
        TransaccionDTO transaccionDTO = new TransaccionDTO();
        RespuestaDTO respuesta = new RespuestaDTO();

        try {
            respuesta = this.conectarJNDI(JNDI_ORACLE_9, canal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            respuesta = this.generarQuery("IB_K_TRANSFERENCIAS", "IB_P_TRANSF_TERCEROS_DELSUR", 7, canal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            //seteo de parametros de entrada y/o salida
            statement.setString(1, cuentaOrigen);
            statement.setString(2, cuentaDestino);
            statement.setBigDecimal(3, monto);
            statement.setString(4, descripcion);
            statement.setString(5, canal);
            statement.registerOutParameter(6, OracleTypes.INTEGER);
            statement.registerOutParameter(7, OracleTypes.INTEGER);
            this.ejecuto = statement.execute();

            respuesta.setCodigoSP(String.valueOf(this.statement.getInt(7)));

            respuesta = actuacionesDAO.obtenerDescripcionSalidaSP(respuesta.getCodigoSP(), canal);

            if (respuesta.getCodigo().equalsIgnoreCase(CODIGO_RESPUESTA_EXITOSO) && respuesta.getCodigoSP().equalsIgnoreCase(CODIGO_RESPUESTA_EXITOSO)) {
                transaccionDTO.setNroReferencia(String.valueOf(this.statement.getInt(6)));
            }
            transaccionDTO.setRespuesta(respuesta);
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN transferenciaMismoBanco: ")
                    .append("ORG-").append(this.numeroCuentaFormato(cuentaOrigen))
                    .append("DST-").append(this.numeroCuentaFormato(cuentaDestino))
                    .append("-CH-").append(canal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log              
        } finally {
            transaccionDTO.setRespuesta(respuesta);
            this.cerrarConexion(canal);
        }
//        if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN transferenciaMismoBanco: ")
//                    .append("ORG-").append(this.numeroCuentaFormato(cuentaOrigen))
//                    .append("DST-").append(this.numeroCuentaFormato(cuentaDestino))
//                    .append("-CH-").append(canal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return transaccionDTO;
    }

    /**
     * Metodo que se encarga de realizar la transferencia entre dos cuentas
     * propias del mismo banco
     *
     * @param cuentaOrigen Numero (de 20 digitos) de la cuenta origen de los
     * fondos.
     * @param cuentaDestino Numero de cuenta, de 20 digitos, destino de los
     * fondos.
     * @param monto Monto en Bs. de la transferencia.
     * @param descripcion Descripcion, de la transferencia, suministrada por el
     * cliente que la realiza.
     * @param canal Codigo (extendido) del canal desde el cual es llamado el
     * procedimiento.
     * @return TransaccionDTO objeto de respuesta con el codigo del resultado de
     * la transaccion
     */
    @Override
    public TransaccionDTO transferenciaPropiasMismoBanco(String cuentaOrigen, String cuentaDestino, BigDecimal monto, String descripcion, String canal) {
        RespuestaDTO respuesta = new RespuestaDTO();
        TransaccionDTO transaccionDTO = new TransaccionDTO();
        try {
            respuesta = this.conectarJNDI(JNDI_ORACLE_9, canal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            respuesta = this.generarQuery("IB_K_TRANSFERENCIAS", "IB_P_TRANSF_PROPIAS_DELSUR", 7, canal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            //seteo de parametros de entrada y/o salida
            statement.setString(1, cuentaOrigen);
            statement.setString(2, cuentaDestino);
            statement.setBigDecimal(3, monto);
            statement.setString(4, descripcion);
            statement.setString(5, canal);
            statement.registerOutParameter(6, OracleTypes.INTEGER);
            statement.registerOutParameter(7, OracleTypes.INTEGER);
            this.ejecuto = statement.execute();

            respuesta.setCodigoSP(String.valueOf(this.statement.getInt(7)));

            respuesta = actuacionesDAO.obtenerDescripcionSalidaSP(respuesta.getCodigoSP(), canal);

            if (respuesta.getCodigo().equalsIgnoreCase(CODIGO_RESPUESTA_EXITOSO) && respuesta.getCodigoSP().equalsIgnoreCase(CODIGO_RESPUESTA_EXITOSO)) {
                transaccionDTO.setNroReferencia(String.valueOf(this.statement.getInt(6)));
            }

        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN transferenciaPropiasMismoBanco: ")
                    .append("ORG-").append(this.numeroCuentaFormato(cuentaOrigen))
                    .append("DST-").append(this.numeroCuentaFormato(cuentaDestino))
                    .append("-CH-").append(canal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString(), e);
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log              
        } finally {
            transaccionDTO.setRespuesta(respuesta);
            this.cerrarConexion(canal);
        }
//        if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN transferenciaPropiasMismoBanco: ")
//                    .append("ORG-").append(this.numeroCuentaFormato(cuentaOrigen))
//                    .append("DST-").append(this.numeroCuentaFormato(cuentaDestino))
//                    .append("-CH-").append(canal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return transaccionDTO;
    }

    /**
     * Metodo que se encarga de realizar la transferencia entre dos cuentas
     * hacia otro banco
     *
     * @param cuentaOrigen Numero (de 20 digitos) de la cuenta origen de los
     * fondos.
     * @param cuentaBeneficiario Numero de cuenta, de 20 digitos, destino de los
     * fondos.
     * @param idBeneficiario Identificacion (numero de cedula, RIF, etc.) del
     * beneficiario del pago.
     * @param nombreBeneficiario Nombre y apellido del beneficiario de la
     * transferencia.
     * @param monto Monto en Bs. de la transferencia.
     * @param descripcion Descripcion, de la transferencia, suministrada por el
     * cliente que la realiza.
     * @param canal Codigo (extendido) del canal desde el cual es llamado el
     * procedimiento.
     * @return TransaccionDTO objeto de respuesta con el codigo del resultado de
     * la transaccion
     */
    @Override
    public TransaccionDTO transferenciaOtroBanco(String cuentaOrigen, String cuentaBeneficiario, String idBeneficiario, String nombreBeneficiario, BigDecimal monto, String descripcion, String canal) {
        RespuestaDTO respuesta = new RespuestaDTO();
        TransaccionDTO transaccionDTO = new TransaccionDTO();
        try {
            respuesta = this.conectarJNDI(JNDI_ORACLE_9, canal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            respuesta = this.generarQuery("IB_K_TRANSFERENCIAS", "IB_P_TRANSF_OTROS_BANCOS", 9, canal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            //seteo de parametros de entrada y/o salida
            statement.setString(1, cuentaOrigen);
            statement.setString(2, cuentaBeneficiario);
            statement.setString(3, idBeneficiario);
            statement.setString(4, nombreBeneficiario);
            statement.setBigDecimal(5, monto);
            statement.setString(6, descripcion);
            statement.setString(7, canal);
            statement.registerOutParameter(8, OracleTypes.INTEGER);
            statement.registerOutParameter(9, OracleTypes.INTEGER);
            this.ejecuto = statement.execute();

            respuesta.setCodigoSP(String.valueOf(this.statement.getInt(9)));

            respuesta = actuacionesDAO.obtenerDescripcionSalidaSP(respuesta.getCodigoSP(), canal);

            if (respuesta.getCodigo().equalsIgnoreCase(CODIGO_RESPUESTA_EXITOSO) && respuesta.getCodigoSP().equalsIgnoreCase(CODIGO_RESPUESTA_EXITOSO)) {
                transaccionDTO.setNroReferencia(String.valueOf(this.statement.getInt(8)));
            }

        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN transferenciaOtroBanco: ")
                    .append("ORG-").append(this.numeroCuentaFormato(cuentaOrigen))
                    .append("DST-").append(this.numeroCuentaFormato(cuentaBeneficiario))
                    .append("-CH-").append(canal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log               
        } finally {
            transaccionDTO.setRespuesta(respuesta);
            this.cerrarConexion(canal);
        }
//        if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN transferenciaOtroBanco: ")
//                    .append("ORG-").append(this.numeroCuentaFormato(cuentaOrigen))
//                    .append("DST-").append(this.numeroCuentaFormato(cuentaBeneficiario))
//                    .append("-CH-").append(canal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return transaccionDTO;
    }

    /**
     * Metodo que se encarga de realizar el pago de un TDC del mismo banco
     *
     * @param cuentaOrigen Numero (de 20 digitos) de la cuenta origen de los
     * fondos.
     * @param tdcBeneficiario Numero de la tarjeta de credito que se desea
     * pagar.
     * @param monto Monto en Bs. de la transferencia.
     * @param descripcion Descripcion, de la transferencia, suministrada por el
     * cliente que la realiza.
     * @param canal Codigo (extendido) del canal desde el cual es llamado el
     * procedimiento.
     * @return TransaccionDTO objeto de respuesta con el codigo del resultado de
     * la transaccion
     */
    @Override
    public TransaccionDTO pagoTDCMismoBanco(String cuentaOrigen, String tdcBeneficiario, BigDecimal monto, String descripcion, String canal) {
        TransaccionDTO transaccionDTO = new TransaccionDTO();
        RespuestaDTO respuesta = new RespuestaDTO();

        try {
            respuesta = this.conectarJNDI(JNDI_ORACLE_9, canal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            respuesta = this.generarQuery("IB_K_TRANSFERENCIAS", "IB_P_PAGAR_TDC_TERCEROS_DELSUR", 7, canal);

            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            //seteo de parametros de entrada y/o salida
            statement.setString(1, cuentaOrigen);
            statement.setString(2, tdcBeneficiario);
            statement.setBigDecimal(3, monto);
            statement.setString(4, descripcion);
            statement.setString(5, canal);
            statement.registerOutParameter(6, OracleTypes.INTEGER);
            statement.registerOutParameter(7, OracleTypes.INTEGER);
            this.ejecuto = statement.execute();

            respuesta.setCodigoSP(String.valueOf(this.statement.getInt(7)));

            respuesta = actuacionesDAO.obtenerDescripcionSalidaSP(respuesta.getCodigoSP(), canal);

            if (respuesta.getCodigo().equalsIgnoreCase(CODIGO_RESPUESTA_EXITOSO) && respuesta.getCodigoSP().equalsIgnoreCase(CODIGO_RESPUESTA_EXITOSO)) {
                transaccionDTO.setNroReferencia(String.valueOf(this.statement.getInt(6)));
            }

        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN pagoTDCMismoBanco: ")
                    .append("ORG-").append(this.numeroCuentaFormato(cuentaOrigen))
                    .append("DST-").append(this.numeroCuentaFormato(tdcBeneficiario))
                    .append("-CH-").append(canal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log 
        } finally {  
            transaccionDTO.setRespuesta(respuesta);
            this.cerrarConexion(canal);
        }
//        if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN pagoTDCMismoBanco: ")
//                    .append("ORG-").append(this.numeroCuentaFormato(cuentaOrigen))
//                    .append("DST-").append(this.numeroCuentaFormato(tdcBeneficiario))
//                    .append("-CH-").append(canal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return transaccionDTO;
    }

    /**
     * Metodo que se encarga de realizar el pago de un TDC propia del mismo
     * banco
     *
     * @param cuentaOrigen Numero (de 20 digitos) de la cuenta origen de los
     * fondos.
     * @param tdcBeneficiario Numero de la tarjeta de credito que se desea
     * pagar.
     * @param monto Monto en Bs. de la transferencia.
     * @param descripcion Descripcion, de la transferencia, suministrada por el
     * cliente que la realiza.
     * @param canal Codigo (extendido) del canal desde el cual es llamado el
     * procedimiento.
     * @return TransaccionDTO objeto de respuesta con el codigo del resultado de
     * la transaccion
     */
    @Override
    public TransaccionDTO pagoTDCPropiaMismoBanco(String cuentaOrigen, String tdcBeneficiario, BigDecimal monto, String descripcion, String canal) {
        TransaccionDTO transaccionDTO = new TransaccionDTO();
        RespuestaDTO respuesta = new RespuestaDTO();

        try {
            respuesta = this.conectarJNDI(JNDI_ORACLE_9, canal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            respuesta = this.generarQuery("IB_K_TRANSFERENCIAS", "IB_P_PAGAR_TDC_PROPIAS_DELSUR", 7, canal);

            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            //seteo de parametros de entrada y/o salida
            statement.setString(1, cuentaOrigen);
            statement.setString(2, tdcBeneficiario);
            statement.setBigDecimal(3, monto);
            statement.setString(4, descripcion);
            statement.setString(5, canal);
            statement.registerOutParameter(6, OracleTypes.INTEGER);
            statement.registerOutParameter(7, OracleTypes.INTEGER);
            this.ejecuto = statement.execute();

            respuesta.setCodigoSP(String.valueOf(this.statement.getInt(7)));

            respuesta = actuacionesDAO.obtenerDescripcionSalidaSP(respuesta.getCodigoSP(), canal);

            if (respuesta.getCodigo().equalsIgnoreCase(CODIGO_RESPUESTA_EXITOSO) && respuesta.getCodigoSP().equalsIgnoreCase(CODIGO_RESPUESTA_EXITOSO)) {
                transaccionDTO.setNroReferencia(String.valueOf(this.statement.getInt(6)));
            }

        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN pagoTDCPropiaMismoBanco: ")
                    .append("ORG-").append(this.numeroCuentaFormato(cuentaOrigen))
                    .append("DST-").append(this.numeroCuentaFormato(tdcBeneficiario))
                    .append("-CH-").append(canal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log  
        } finally {
            transaccionDTO.setRespuesta(respuesta);
            this.cerrarConexion(canal);
        }
//        if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN pagoTDCPropiaMismoBanco: ")
//                    .append("ORG-").append(this.numeroCuentaFormato(cuentaOrigen))
//                    .append("DST-").append(this.numeroCuentaFormato(tdcBeneficiario))
//                    .append("-CH-").append(canal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return transaccionDTO;
    }

    /**
     * Metodo que se encarga de realizar la transferencia entre dos cuentas
     * hacia otro banco
     *
     * @param cuentaOrigen Numero (de 20 digitos) de la cuenta origen de los
     * fondos.
     * @param tdcBeneficiario Numero de cuenta, de 20 digitos, destino de los
     * fondos.
     * @param idBeneficiario Identificacion (numero de cedula, RIF, etc.) del
     * beneficiario del pago.
     * @param nombreBeneficiario Nombre y apellido del beneficiario de la
     * transferencia.
     * @param monto Monto en Bs. de la transferencia.
     * @param descripcion Descripcion, de la transferencia, suministrada por el
     * cliente que la realiza.
     * @param canal Codigo (extendido) del canal desde el cual es llamado el
     * procedimiento.
     * @return TransaccionDTO objeto de respuesta con el codigo del resultado de
     * la transaccion
     */
    @Override
    public TransaccionDTO pagoTDCOtroBanco(String cuentaOrigen, String tdcBeneficiario, String idBeneficiario, String nombreBeneficiario, BigDecimal monto, String descripcion, String canal) {
        RespuestaDTO respuesta = new RespuestaDTO();
        TransaccionDTO transaccionDTO = new TransaccionDTO();
        try {
            respuesta = this.conectarJNDI(JNDI_ORACLE_9, canal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            respuesta = this.generarQuery("IB_K_TRANSFERENCIAS", "IB_P_PAGAR_TDC_OTROS_BANCOS", 9, canal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            //seteo de parametros de entrada y/o salida
            statement.setString(1, cuentaOrigen);
            statement.setString(2, tdcBeneficiario);
            statement.setString(3, idBeneficiario);
            statement.setString(4, nombreBeneficiario);
            statement.setBigDecimal(5, monto);
            statement.setString(6, descripcion);
            statement.setString(7, canal);
            statement.registerOutParameter(8, OracleTypes.INTEGER);
            statement.registerOutParameter(9, OracleTypes.INTEGER);
            this.ejecuto = statement.execute();

            respuesta.setCodigoSP(String.valueOf(this.statement.getInt(9)));

            respuesta = actuacionesDAO.obtenerDescripcionSalidaSP(respuesta.getCodigoSP(), canal);

            if (respuesta.getCodigo().equalsIgnoreCase(CODIGO_RESPUESTA_EXITOSO) && respuesta.getCodigoSP().equalsIgnoreCase(CODIGO_RESPUESTA_EXITOSO)) {
                transaccionDTO.setNroReferencia(String.valueOf(this.statement.getInt(8)));
            }

        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN pagoTDCOtroBanco: ")
                    .append("ORG-").append(this.numeroCuentaFormato(cuentaOrigen))
                    .append("DST-").append(this.numeroCuentaFormato(tdcBeneficiario))
                    .append("-CH-").append(canal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log  
        } finally { 
            transaccionDTO.setRespuesta(respuesta);
            this.cerrarConexion(canal);
        }
//        if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN pagoTDCOtroBanco: ")
//                    .append("ORG-").append(this.numeroCuentaFormato(cuentaOrigen))
//                    .append("DST-").append(this.numeroCuentaFormato(tdcBeneficiario))
//                    .append("-CH-").append(canal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return transaccionDTO;
    }

}
