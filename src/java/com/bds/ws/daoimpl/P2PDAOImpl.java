/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.daoimpl;

import com.bds.ws.dao.NotificationServiceType;
import com.bds.ws.dao.P2PDAO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.dto.UtilDTO;
import com.bds.ws.exception.DAOException;
import com.bds.ws.facade.IbBeneficiariosP2pFacade;
import com.bds.ws.facade.IbParametrosFacade;
import com.bds.ws.model.IbBeneficiariosP2p;
import com.bds.ws.model.IbUsuarios;
import com.bds.ws.util.DAOUtil;
import com.roas.ws.p2p.CanalDTO;
import com.roas.ws.p2p.ParametroDAO;
import com.roas.ws.P2PBankWS;
import com.roas.ws.P2PBankWSPortType;
import java.math.BigDecimal;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Named;
import org.apache.log4j.Logger;

/**
 *
 * @author cesar.mujica
 */
@Named
@Stateless(name = "P2PDAO")
@NotificationServiceType(NotificationServiceType.ServiceType.SERVICES)
public class P2PDAOImpl extends DAOUtil implements P2PDAO {

    /**
     * Log de sistema
     */
    private static final Logger logger = Logger.getLogger(P2PDAOImpl.class.getName());

    @EJB
    IbParametrosFacade parametrosFacade;

    @EJB
    IbBeneficiariosP2pFacade ibBeneficiariosP2pFacade;

    //private final String urlWsdlP2P = "roas.wsdl.p2p"; //servicios de Nelson Roas 
    private String formatoCodigoBanco = "%07d";

    /**
     * invoca al WS de pagos P2P en DelSur.
     *
     * @param codCanalP2P
     * @param nroEmisor
     * @param idCanal String
     * @param nroBeneficiario
     * @param monto
     * @param conceptoPago
     * @param identificacionPagador
     * @param url
     * @param identificacionBeneficiario
     * @param nombreCanal String
     * @return RespuestaDTO
     */
    @Override
    public UtilDTO realizarPagoP2PTercerosDelsur(String codCanalP2P, String nroEmisor, String nroBeneficiario, String identificacionBeneficiario, String monto, String conceptoPago, String identificacionPagador, String url, String idCanal, String nombreCanal) {
        /*
        logger.error( new StringBuilder("TRAZA P2P realizarPagoP2PTercerosDelsur: ")
                .append("codCanalP2P: ")
                .append(codCanalP2P)
                .append("  nroEmisor: ")
                .append(nroEmisor)
                .append("  nroBeneficiario: ")
                .append(nroBeneficiario)
                .append("  identificacionBeneficiario: ")
                .append(identificacionBeneficiario)
                .append("  monto: ")
                .append(monto)
                .append("  conceptoPago: ")
                .append(conceptoPago)
                .append("  identificacionPagador: ")
                .append(identificacionPagador)
                .append("  url: ")
                .append(url)
                .append("  FECHA: ").append(new Date()).toString());
        */
        UtilDTO util = new UtilDTO();
        RespuestaDTO respuesta = new RespuestaDTO();
        try {
            //se obtiene el DS de P2P
            respuesta = this.conectarJNDI(JNDI_P2P, nombreCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            //invocacion del WS
            P2PBankWS service = new P2PBankWS(new URL(url));
            P2PBankWSPortType port = service.getP2PBankWSHttpSoap12Endpoint();

            ParametroDAO params = new ParametroDAO();
            CanalDTO canal = params.getDatosCanal(codCanalP2P, this.p2pConn());

            com.roas.ws.RespuestaDTO respuestaP2P;
            
            respuestaP2P = port.realizarPagoP2PTercerosDelsur(canal.getUsuarioCanal(), canal.getClvCanal(), codCanalP2P, nroEmisor, nroBeneficiario, identificacionBeneficiario, monto, conceptoPago, identificacionPagador);

            //validacion de codigo de respuesta, en el caso de getCodigoSP se valida "00" ya que RoasSystem retorna el valor standar de swiche7B
            if (!respuestaP2P.getCodigo().getValue().equalsIgnoreCase(CODIGO_RESPUESTA_EXITOSO) || !respuestaP2P.getCodigoSP().getValue().equalsIgnoreCase("00")) {
                respuesta.setCodigo(respuestaP2P.getCodigo().getValue());
                respuesta.setDescripcion(respuestaP2P.getDescripcion().getValue());
                respuesta.setCodigoSP(respuestaP2P.getCodigoSP().getValue());
                respuesta.setDescripcionSP(respuestaP2P.getDescripcionSP().getValue());
            } else {
                if (respuestaP2P.getCodigo().getValue().equalsIgnoreCase(CODIGO_RESPUESTA_EXITOSO) && respuestaP2P.getCodigoSP().getValue().equalsIgnoreCase("00")) {
                    Map resultado = new HashMap<>();
                    resultado.put("referencia", respuestaP2P.getReferenciaTransaccion().getValue());
                    util.setResulados(resultado);
                }
            }
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO GENERICO EN realizarPagoP2PTercerosDelsur: ")
                    .append("P2P-").append(formatoAsteriscosWeb(nroEmisor))
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString(), e);
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log            
        } finally {
            this.cerrarConexion(nombreCanal);
        }
        util.setRespuesta(respuesta);
        return util;
    }

    /**
     * invoca al WS de pagos P2P en Otros Bancos.
     *
     * @param codCanalP2P
     * @param nroEmisor
     * @param idCanal String
     * @param nroBeneficiario
     * @param monto
     * @param conceptoPago
     * @param codBanco
     * @param identificacionPagador
     * @param url
     * @param identificacionBeneficiario
     * @param nombreCanal String
     * @return RespuestaDTO
     */
    @Override
    public UtilDTO realizarPagoP2PTercerosOtrosBancos(String codCanalP2P, String nroEmisor, String nroBeneficiario, String identificacionBeneficiario, String monto, String conceptoPago, String codBanco, String identificacionPagador, String url, String idCanal, String nombreCanal) {
        /*
        logger.error( new StringBuilder("TRAZA P2P realizarPagoP2PTercerosOtrosBancos: ")
                .append("codCanalP2P: ")
                .append(codCanalP2P)
                .append("  nroEmisor: ")
                .append(nroEmisor)
                .append("  nroBeneficiario: ")
                .append(nroBeneficiario)
                .append("  identificacionBeneficiario: ")
                .append(identificacionBeneficiario)
                .append("  monto: ")
                .append(monto)
                .append("  conceptoPago: ")
                .append(conceptoPago)
                .append("  codBanco: ")
                .append(codBanco)
                .append("  identificacionPagador: ")
                .append(identificacionPagador)
                .append("  url: ")
                .append(url)
                .append("  FECHA: ").append(new Date()).toString());
        */
        UtilDTO util = new UtilDTO();
        RespuestaDTO respuesta = new RespuestaDTO();
        try {
            //se obtiene el DS de P2P
            respuesta = this.conectarJNDI(JNDI_P2P, nombreCanal);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }
            //invocacion del WS
//            P2PBankWS service = new P2PBankWS(new URL(url));
//            P2PBankWSPortType port = service.getP2PBankWSHttpSoap12Endpoint();
            P2PBankWS service = new P2PBankWS(new URL(url));
            P2PBankWSPortType port = service.getP2PBankWSHttpSoap12Endpoint();

            ParametroDAO params = new ParametroDAO();
            CanalDTO canal = params.getDatosCanal(codCanalP2P, this.p2pConn());

            com.roas.ws.RespuestaDTO respuestaP2P;

            //SE FORMATEA EL CODIGO DEL BANCO SOLO CUANDO ES DIFERENTE DE 
            //7 DIGITOS
//            if(codBanco.length() != 11){
//                codBanco = String.format(formatoCodigoBanco, codBanco);
//            }

            respuestaP2P = port.realizarPagoP2PTercerosOtrosBancos(canal.getUsuarioCanal(), canal.getClvCanal(), codCanalP2P, nroEmisor, nroBeneficiario, identificacionBeneficiario, monto, conceptoPago, codBanco, identificacionPagador);

            //validacion de codigo de respuesta, en el caso de getCodigoSP se valida "00" ya que RoasSystem retorna el valor standar de swiche7B
            if (!respuestaP2P.getCodigo().getValue().equalsIgnoreCase(CODIGO_RESPUESTA_EXITOSO) || !respuestaP2P.getCodigoSP().getValue().equalsIgnoreCase("00")) {
                respuesta.setCodigo(respuestaP2P.getCodigo().getValue());
                respuesta.setDescripcion(respuestaP2P.getDescripcion().getValue());
                respuesta.setCodigoSP(respuestaP2P.getCodigoSP().getValue());
                respuesta.setDescripcionSP(respuestaP2P.getDescripcionSP().getValue());
            } else {
                if (respuestaP2P.getCodigo().getValue().equalsIgnoreCase(CODIGO_RESPUESTA_EXITOSO) && respuestaP2P.getCodigoSP().getValue().equalsIgnoreCase("00")) {
                    Map resultado = new HashMap<>();
                    resultado.put("referencia", respuestaP2P.getReferenciaTransaccion());
                    util.setResulados(resultado);
                }
            }
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO GENERICO EN realizarPagoP2PTercerosOtrosBancos: ")
                    .append("P2P-").append(formatoAsteriscosWeb(nroEmisor))
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log            
        } finally {
            this.cerrarConexion(nombreCanal);
        }
        util.setRespuesta(respuesta);
        return util;
    }

    /**
     * Método para registrar el beneficiario p2p
     * @param codCanalP2P
     * @param nroEmisor
     * @param nroBeneficiario
     * @param identificacionBeneficiario
     * @param monto
     * @param conceptoPago
     * @param codBanco
     * @param identificacionPagador
     * @param url
     * @param frecuente
     * @param alias
     * @param idUsuarioP2P
     * @param tipoDocumento
     * @param idCanal
     * @param nombreCanal
     * @return 
     */
    @Override
    public UtilDTO registrarBeneficiarioP2P(String codCanalP2P, String nroEmisor, String nroBeneficiario, String identificacionBeneficiario, String monto, String conceptoPago, String codBanco, String identificacionPagador, String url, boolean frecuente, String alias, BigDecimal idUsuarioP2P, String tipoDocumento, String idCanal, String nombreCanal) {
        UtilDTO util = new UtilDTO();
        IbBeneficiariosP2p ibBeneficiarioP2P = new IbBeneficiariosP2p();
        RespuestaDTO respuesta = new RespuestaDTO();
        try {
            ibBeneficiarioP2P.setAliasBeneficiario(alias);
            ibBeneficiarioP2P.setCodigoBanco(Short.parseShort(codBanco));
            ibBeneficiarioP2P.setEstatusBeneficiario(Short.parseShort(STATUS_ACTIVO));
            ibBeneficiarioP2P.setFechaHoraCarga(new Date());
            ibBeneficiarioP2P.setNombreBeneficiario(alias);
            ibBeneficiarioP2P.setNroDocumento(identificacionBeneficiario);
            ibBeneficiarioP2P.setTipoDocumento(tipoDocumento);
            ibBeneficiarioP2P.setNroTelefono(nroBeneficiario);
            ibBeneficiarioP2P.setIdUsuario(new IbUsuarios(idUsuarioP2P));
            ibBeneficiarioP2P.setCodigoUsuarioCarga(new IbUsuarios(idUsuarioP2P));
            respuesta = ibBeneficiariosP2pFacade.crearPJ(ibBeneficiarioP2P);
            if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new DAOException();
            }

        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO GENERICO EN registrarBeneficiarioP2P: ")
                    .append("P2P-").append(formatoAsteriscosWeb(nroEmisor))
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log            
        } finally {
            util.setRespuesta(respuesta);
        }
        return util;
    }
}
