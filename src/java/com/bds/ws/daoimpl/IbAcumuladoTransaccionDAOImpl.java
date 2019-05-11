/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.daoimpl;

import com.bds.ws.dao.IBAcumuladoTransaccionDAO;
import com.bds.ws.dao.NotificationServiceType;
import com.bds.ws.dto.IBAcumuladoTransaccionDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.exception.DAOException;
import com.bds.ws.facade.IbAcumuladoTransaccionFacade;
import com.bds.ws.facade.IbUsuariosFacade;
import com.bds.ws.model.IbAcumuladoTransaccion;
import com.bds.ws.model.IbUsuarios;
import static com.bds.ws.util.BDSUtil.CODIGO_EXCEPCION_GENERICA;
import static com.bds.ws.util.BDSUtil.CODIGO_RESPUESTA_EXITOSO;
import static com.bds.ws.util.BDSUtil.CODIGO_SIN_RESULTADOS_JPA;
import static com.bds.ws.util.BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA;
import static com.bds.ws.util.BDSUtil.TEXTO_SIN_RESULTADOS_JPA;
import com.bds.ws.util.DAOUtil;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.NoResultException;
import org.apache.log4j.Logger;

/**
 * Clase que implementa la interfaz IBAcumuladoTransaccionDAO
 *
 * @author mdiaz
 */
@Named("IBAcumuladoTransaccionDAO")
@Stateless
@NotificationServiceType(NotificationServiceType.ServiceType.SERVICES)
public class IbAcumuladoTransaccionDAOImpl extends DAOUtil implements IBAcumuladoTransaccionDAO {

    @EJB
    private IbAcumuladoTransaccionFacade ibAcumuladoTransaccionFacade;

    @EJB
    private IbUsuariosFacade ibUsuariosFacade;

    /**
     * Log de sistema.
     */
    private static final Logger logger = Logger.getLogger(IbAcumuladoTransaccionDAOImpl.class.getName());

    @Override
    public RespuestaDTO insertarAcumuladoTransaccion(String idUsuario, String montoTransaccion, String idtransaccion, String nombreCanal) {
        RespuestaDTO respuesta = new RespuestaDTO();
        IBAcumuladoTransaccionDTO acumuladosDTO;
        BigDecimal acumuladoInternasTerceros = null;
        BigDecimal acumuladoExternas = null;
        BigDecimal acumuladoExternasTerceros = null;
        BigDecimal acumuladoRecDigitel = null;
        BigDecimal acumuladoP2P = null;
        BigDecimal acumuladoP2C = null;
        try {

            if (idUsuario == null || nombreCanal == null || nombreCanal.isEmpty() || nombreCanal.equalsIgnoreCase("")) {
                throw new DAOException("DAO008");
            }

            acumuladosDTO = ibAcumuladoTransaccionFacade.obtenerAcumuladosTransaccionUsuario(idUsuario, nombreCanal);

            IbUsuarios usuario = ibUsuariosFacade.ibUsuarioPorIdUsuario(idUsuario, nombreCanal).getUsuario();

            if (usuario == null) {
                throw new Exception();
            }

            if (!acumuladosDTO.getIbAcumuladoTransacciones().isEmpty()) {

                for (IbAcumuladoTransaccion acum : acumuladosDTO.getIbAcumuladoTransacciones()) {
                    if (acum.getAcumuladoInternasTerceros() != null) {
                        acumuladoInternasTerceros = acum.getAcumuladoInternasTerceros();
                    } else {
                        acumuladoInternasTerceros = BigDecimal.ZERO;
                    }
                    if (acum.getAcumuladoExternas() != null) {
                        acumuladoExternas = acum.getAcumuladoExternas();
                    } else {
                        acumuladoExternas = BigDecimal.ZERO;
                    }
                    if (acum.getAcumuladoExternasTerceros() != null) {
                        acumuladoExternasTerceros = acum.getAcumuladoExternasTerceros();
                    } else {
                        acumuladoExternasTerceros = BigDecimal.ZERO;
                    }
                    if (acum.getAcumuladoP2p() != null) {
                        acumuladoP2P = acum.getAcumuladoP2p();
                    } else {
                        acumuladoP2P = BigDecimal.ZERO;
                    }
                    if (acum.getAcumuladoP2c() != null) {
                        acumuladoP2C = acum.getAcumuladoP2c();
                    } else {
                        acumuladoP2C = BigDecimal.ZERO;
                    }

                    switch (idtransaccion) {
                        case ID_TRANS_CTAS_TERC_DELSUR: {
                            acum.setAcumuladoInternasTerceros((acumuladoInternasTerceros == null ? new BigDecimal((eliminarformatoSimpleMonto(montoTransaccion))) : acumuladoInternasTerceros.add(new BigDecimal(eliminarformatoSimpleMonto(montoTransaccion)))));
                            break;
                        }
                        case ID_TRANS_CTAS_P_OTROS_BCOS: {
                            acum.setAcumuladoExternas((acumuladoExternas == null ? new BigDecimal((eliminarformatoSimpleMonto(montoTransaccion))) : acumuladoExternas.add(new BigDecimal(eliminarformatoSimpleMonto(montoTransaccion)))));
                            break;
                        }
                        case ID_TRANS_CTAS_T_OTROS_BCOS: {
                            acum.setAcumuladoExternasTerceros((acumuladoExternasTerceros == null ? new BigDecimal((eliminarformatoSimpleMonto(montoTransaccion))) : acumuladoExternasTerceros.add(new BigDecimal(eliminarformatoSimpleMonto(montoTransaccion)))));
                            break;
                        }
                        case ID_TRANS_REC_DIGITEL: {
                            acum.setAcumuladoDigitel((acumuladoRecDigitel == null ? new BigDecimal((eliminarformatoSimpleMonto(montoTransaccion))) : acumuladoRecDigitel.add(new BigDecimal(eliminarformatoSimpleMonto(montoTransaccion)))));
                            break;
                        }
                        case ID_TRANS_PAGARP2P: {
                            acum.setAcumuladoP2p((acumuladoP2P == null ? new BigDecimal((eliminarformatoSimpleMonto(montoTransaccion))) : acumuladoP2P.add(new BigDecimal(eliminarformatoSimpleMonto(montoTransaccion)))));
                            break;
                        }
                        case ID_TRANS_PAGARP2C: {
                            acum.setAcumuladoP2c((acumuladoP2C == null ? new BigDecimal((eliminarformatoSimpleMonto(montoTransaccion))) : acumuladoP2C.add(new BigDecimal(eliminarformatoSimpleMonto(montoTransaccion)))));
                            break;
                        }
                    }
                    respuesta = ibAcumuladoTransaccionFacade.actualizar(acum);
                    if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                        throw new Exception();
                    }
                }

            } else {
                acumuladosDTO = new IBAcumuladoTransaccionDTO();
                acumuladosDTO.setIbAcumuladoTransaccion(new IbAcumuladoTransaccion());
                switch (idtransaccion) {
                    case ID_TRANS_CTAS_TERC_DELSUR: {
                        acumuladosDTO.getIbAcumuladoTransaccion().setAcumuladoInternasTerceros(new BigDecimal(eliminarformatoSimpleMonto(montoTransaccion)));
                        break;
                    }
                    case ID_TRANS_CTAS_P_OTROS_BCOS: {
                        acumuladosDTO.getIbAcumuladoTransaccion().setAcumuladoExternas(new BigDecimal(eliminarformatoSimpleMonto(montoTransaccion)));
                        break;
                    }
                    case ID_TRANS_CTAS_T_OTROS_BCOS: {
                        acumuladosDTO.getIbAcumuladoTransaccion().setAcumuladoExternasTerceros(new BigDecimal(eliminarformatoSimpleMonto(montoTransaccion)));
                        break;
                    }
                    case ID_TRANS_REC_DIGITEL: {
                        acumuladosDTO.getIbAcumuladoTransaccion().setAcumuladoDigitel(new BigDecimal(eliminarformatoSimpleMonto(montoTransaccion)));
                        break;
                    }
                    case ID_TRANS_PAGARP2P: {
                        acumuladosDTO.getIbAcumuladoTransaccion().setAcumuladoP2p(new BigDecimal(eliminarformatoSimpleMonto(montoTransaccion)));
                        break;
                    }
                    case ID_TRANS_PAGARP2C: {
                        acumuladosDTO.getIbAcumuladoTransaccion().setAcumuladoP2p(new BigDecimal(eliminarformatoSimpleMonto(montoTransaccion)));
                        break;
                    }

                }

                acumuladosDTO.getIbAcumuladoTransaccion().setId(BigDecimal.ZERO);
                acumuladosDTO.getIbAcumuladoTransaccion().setIdUsuario(usuario);
                acumuladosDTO.getIbAcumuladoTransaccion().setFecha(new Date());

                respuesta = ibAcumuladoTransaccionFacade.crear(acumuladosDTO.getIbAcumuladoTransaccion());

                if (!respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                    throw new Exception();
                }
            }

        } catch (Exception e) {
            logger.error(new StringBuilder("ERROR DAO insertarAcumuladoTransaccion: ")
                    .append("USR-").append(idUsuario)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log

        }
//        if (respuesta.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN insertarAcumuladoTransaccion: ")
//                    .append("USR-").append(idUsuario)
//                    .append("-CH-").append(nombreCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }

        return respuesta;
    }

    /**
     * Metodo para obtener los acumulados del usuario
     *
     * @param nombreCanal String ID del canal
     * @return IBAfiliacionesDTO -> acumulados
     */
    @Override
    public IBAcumuladoTransaccionDTO consultarAcumuladoUsuario(String idUsuario, String nombreCanal) {
        IBAcumuladoTransaccionDTO acumuladosDTO = new IBAcumuladoTransaccionDTO();
        acumuladosDTO.setRespuesta(new RespuestaDTO());
        IbAcumuladoTransaccion acumulados = null;
        RespuestaDTO respuesta = new RespuestaDTO();
        String fecha = null;
        String fecFiltro = null;
        List<IbAcumuladoTransaccion> tempacumFlt = new ArrayList<>();
        try {

            if (idUsuario == null || idUsuario.isEmpty() || idUsuario.equalsIgnoreCase("")
                    || nombreCanal == null || nombreCanal.isEmpty() || nombreCanal.equalsIgnoreCase("")) {
                acumuladosDTO.getRespuesta().setCodigo("DAO008");
                throw new DAOException("DAO008");
            }

            acumuladosDTO = ibAcumuladoTransaccionFacade.obtenerAcumuladosTransaccionUsuario(idUsuario, nombreCanal);

            if (!acumuladosDTO.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new Exception();
            }

        } catch (NoResultException e) {
            respuesta.setCodigoSP(CODIGO_SIN_RESULTADOS_JPA);//revisar el log
            respuesta.setTextoSP(TEXTO_SIN_RESULTADOS_JPA);
            logger.error(new StringBuilder("ERROR JPA EN consultarAcumuladoUsuario: ")
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());

        } catch (Exception e) {
            logger.error(new StringBuilder("ERROR DAO EN consultarAcumuladoUsuario: ")
                    .append("USR-").append(idUsuario)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuesta.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        } finally {
            acumuladosDTO.setRespuesta(respuesta);
        }
//        if (acumuladosDTO.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN consultarAcumuladoUsuario: ")
//                    .append("USR-").append(idUsuario)
//                    .append("-CH-").append(nombreCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_EXITOSO).toString());
//        }
        return acumuladosDTO;
    }
}
