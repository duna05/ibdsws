/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.daoimpl;

import com.bds.ws.dao.IBAgendaTransaccionesDAO;
import com.bds.ws.dao.NotificationServiceType;
import com.bds.ws.dto.IBAgendaPNDTO;
import com.bds.ws.dto.IBAgendaTransaccionDTO;
import com.bds.ws.dto.RespuestaDTO;
import com.bds.ws.dto.UtilDTO;
import com.bds.ws.exception.DAOException;
import com.bds.ws.facade.IbAgendaTransaccionesPnFacade;
import com.bds.ws.facade.IbDetalleAgendaTransPnFacade;
import static com.bds.ws.util.BDSUtil.CODIGO_EXCEPCION_GENERICA;
import static com.bds.ws.util.BDSUtil.CODIGO_RESPUESTA_EXITOSO;
import static com.bds.ws.util.BDSUtil.DESCRIPCION_RESPUESTA_FALLIDA;
import com.bds.ws.util.DAOUtil;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Named;
import org.apache.log4j.Logger;

/**
 *
 * @author cesar.mujica
 */
@Named("iBAgendaTransaccionesDAO")
@Stateless
@NotificationServiceType(NotificationServiceType.ServiceType.SERVICES)
public class IBAgendaTransaccionesDAOImpl extends DAOUtil implements IBAgendaTransaccionesDAO {

    private static final Logger logger = Logger.getLogger(IBAgendaTransaccionesDAOImpl.class.getName());

    @EJB
    IbAgendaTransaccionesPnFacade agendaTransaccionesPnFacade;

    @EJB
    IbDetalleAgendaTransPnFacade detalleAgendaTransPnFacade;

    /**
     * Metodo se encarga de almacenar la cabecera de una transaccion agendada
     *
     * @param agenda Objeto con los datos de la cabecera de transaccion a
     * agendar
     * @param nombreCanal nombre del canal
     * @return RespuestaDTO
     */
    @Override
    public RespuestaDTO agregarCabeceraAgenda(IBAgendaTransaccionDTO agenda, String nombreCanal) {
        RespuestaDTO respuestaDTO = new RespuestaDTO();

        try {

            if (agenda != null && nombreCanal != null && agenda.getCuentaDestino() != null && agenda.getCuentaOrigen() != null && agenda.getMonto() != null
                    && agenda.getIdUsuario() != null && String.valueOf(agenda.getFrecuencia()) != null
                    && agenda.getDiaFrecuencia() != 0 && agenda.getFechalimite() != null && String.valueOf(agenda.getTipo()) != null) {
                respuestaDTO = agendaTransaccionesPnFacade.agregarCabeceraAgenda(agenda, nombreCanal);
            } else {
                throw new DAOException("DAO008");
            }
            if (!respuestaDTO.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new Exception();
            }

        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN agregarCabeceraAgenda: ")
                    .append("USR-").append(agenda.getIdUsuario())
                    .append("CTAORG-").append(this.numeroCuentaFormato(agenda.getCuentaOrigen()))
                    .append("CTADST-").append(this.numeroCuentaFormato(agenda.getCuentaDestino()))
                    .append("MTO-").append(agenda.getMonto())
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuestaDTO.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        } 
//        if (respuestaDTO.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN agregarCabeceraAgenda: ")
//                    .append("USR-").append(agenda.getIdUsuario())
//                    .append("CTAORG-").append(this.numeroCuentaFormato(agenda.getCuentaOrigen()))
//                    .append("CTADST-").append(this.numeroCuentaFormato(agenda.getCuentaDestino()))
//                    .append("MTO-").append(agenda.getMonto())
//                    .append("-CH-").append(nombreCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA).toString());
//        }
        return respuestaDTO;
    }

    /**
     * Metodo se encarga de almacenar el detalle de una transaccion agendada
     *
     * @param agenda Objeto con los datos de la cabecera de transaccion a
     * agendar
     * @param nombreCanal nombre del canal
     * @return RespuestaDTO
     */
    @Override
    public RespuestaDTO agregarDetalleAgenda(IBAgendaTransaccionDTO agenda, String nombreCanal) {
        RespuestaDTO respuestaDTO = new RespuestaDTO();
        try {

            if (agenda != null && nombreCanal != null && agenda.getCuentaDestino() != null && agenda.getCuentaOrigen() != null && agenda.getMonto() != null
                    && agenda.getIdUsuario() != null && agenda.getIdTransaccion() != 0 && String.valueOf(agenda.getFrecuencia()) != null
                    && agenda.getDiaFrecuencia() != 0 && agenda.getFechalimite() != null) {
                int diaSemanaActual = 1;       //dia actual en notacion calendar 1 = Domingo, 2 = Lunes, 3 = Martes...
                int diaSeleccionadoCalendar;   //dia transaccion seleccionado en notacion calendar 1 = Domingo, 2 = Lunes, 3 = Martes...
                Calendar fechaProximaTrans;    //fecha de la agenda a registrar
                boolean registroExitoso = true;//bandera para indicar si la transccion se ejecuto completa o se necesita hacer un rollback
                //obtenemos la fecha actual con el formato de horas min y seg en 12:00:00 AM para incluir las fechas iguales en los inserts
                Date fechaActual = fechaActual = this.formatearFechaStringADate(this.formatearFecha(new Date(), this.FORMATO_FECHA_SIMPLE)+" 12:00:00 AM", this.FORMATO_FECHA_COMPLETA);                
                GregorianCalendar cal = new GregorianCalendar(); 
                cal.setTime(fechaActual);
                fechaProximaTrans = new GregorianCalendar();
                fechaProximaTrans.setTime(fechaActual);

                switch (agenda.getFrecuencia()) {
                    //semanal
                    case ('1'): {
                        //obtenemos el dia de la semana en base a la notacion calendar
                        if (agenda.getDiaFrecuencia() == 7) {
                            diaSeleccionadoCalendar = 1;
                        } else {
                            diaSeleccionadoCalendar = agenda.getDiaFrecuencia() + 1; //en calendar el dia de la semana empieza en 1 = domingo
                        }
                        cal.setTime(fechaActual);
                        diaSemanaActual = cal.get(Calendar.DAY_OF_WEEK);
                        //identificamos el primer registro a agendar
                        if (diaSemanaActual == diaSeleccionadoCalendar) {
                            //registro normalmente
                            agenda.setFechaAgendada(this.formatearFecha(fechaProximaTrans.getTime(), this.FORMATO_FECHA_SIMPLE));
                        } else {
                            if (diaSemanaActual < diaSeleccionadoCalendar) {
                                //se incrementa en la diferencia
                                fechaProximaTrans.add(Calendar.DAY_OF_MONTH, (diaSeleccionadoCalendar - diaSemanaActual));
                                agenda.setFechaAgendada(this.formatearFecha(fechaProximaTrans.getTime(), this.FORMATO_FECHA_SIMPLE));
                            } else {
                                //se calcula la diferencia en base a los 7 dias de la semana
                                fechaProximaTrans.add(Calendar.DAY_OF_MONTH, ((7 - diaSemanaActual) + diaSeleccionadoCalendar));
                                agenda.setFechaAgendada(this.formatearFecha(fechaProximaTrans.getTime(), this.FORMATO_FECHA_SIMPLE));
                            }
                        }
                        cal.setTime(this.formatearFechaStringADate(agenda.getFechalimite(), this.FORMATO_FECHA_SIMPLE));
                        //iteramos mientras la nueva fecha a agendar sea menor o igual a la fecha limite
                        while ((fechaProximaTrans.before(cal) || fechaProximaTrans.equals(cal)) && registroExitoso) {
                            respuestaDTO = detalleAgendaTransPnFacade.agregarDetalleAgenda(agenda, nombreCanal);
                            if (!respuestaDTO.getCodigo().equalsIgnoreCase(this.CODIGO_RESPUESTA_EXITOSO)) {
                                registroExitoso = false;
                            } else {
                                //procedemos a asignar el proximo registro en una semana
                                fechaProximaTrans.add(Calendar.DAY_OF_MONTH, 7);
                                agenda.setFechaAgendada(this.formatearFecha(fechaProximaTrans.getTime(), this.FORMATO_FECHA_SIMPLE));
                            }
                        }

                        if (!registroExitoso) {
                            //borramos cualquier cantidad de registros que se hayan realizado
                            respuestaDTO = detalleAgendaTransPnFacade.eliminarDetalleAgenda(agenda.getIdAgenda(), nombreCanal);
                            if (respuestaDTO == null || respuestaDTO.getCodigo() == null || !respuestaDTO.getCodigo().equalsIgnoreCase(this.CODIGO_RESPUESTA_EXITOSO)) {
                                throw new DAOException("DAO008");
                            }
                            //borramos la cabecera de la agenda
                            respuestaDTO = agendaTransaccionesPnFacade.eliminar(agendaTransaccionesPnFacade.find(agenda.getIdAgenda()));
                            if (respuestaDTO == null || respuestaDTO.getCodigo() == null || !respuestaDTO.getCodigo().equalsIgnoreCase(this.CODIGO_RESPUESTA_EXITOSO)) {
                                throw new DAOException("DAO008");
                            }
                            //arrojamos el error para notificar al cliente
                            throw new DAOException("DAO008");
                        }

                        break;
                    }
                    //quincenal
                    case ('2'): {
                        //asignamos el dia del mes seleccionado
                        if (fechaProximaTrans.get(Calendar.DAY_OF_MONTH) > agenda.getDiaFrecuencia()) {
                            fechaProximaTrans.add(Calendar.MONTH, 1);
                        }
                        fechaProximaTrans.set(Calendar.DAY_OF_MONTH, agenda.getDiaFrecuencia());

                        //almacenamos el primer dia de la quincena
                        Calendar fechaPrimeraQuincena = new GregorianCalendar();
                        fechaPrimeraQuincena.setTime(fechaProximaTrans.getTime());
                        //almacenamos el segun dia de la quincena de esta manera ambos dias se mantendran a lo largo de los meses
                        Calendar fechaSegundaQuincena = new GregorianCalendar();
                        fechaSegundaQuincena.setTime(fechaPrimeraQuincena.getTime());
                        fechaSegundaQuincena.add(Calendar.DAY_OF_MONTH, 15);

                        fechaProximaTrans = fechaPrimeraQuincena;

                        agenda.setFechaAgendada(this.formatearFecha(fechaProximaTrans.getTime(), this.FORMATO_FECHA_SIMPLE));
                        cal.setTime(this.formatearFechaStringADate(agenda.getFechalimite(), this.FORMATO_FECHA_SIMPLE));
                        //iteramos mientras la nueva fecha a agendar sea menor o igual a la fecha limite
                        while ((fechaProximaTrans.before(cal) || fechaProximaTrans.equals(cal)) && registroExitoso) {
                            respuestaDTO = detalleAgendaTransPnFacade.agregarDetalleAgenda(agenda, nombreCanal);
                            if (!respuestaDTO.getCodigo().equalsIgnoreCase(this.CODIGO_RESPUESTA_EXITOSO)) {
                                registroExitoso = false;
                            } else {
                                fechaProximaTrans = fechaSegundaQuincena;
                                if ((fechaProximaTrans.before(cal) || fechaProximaTrans.equals(cal)) && registroExitoso) {
                                    agenda.setFechaAgendada(this.formatearFecha(fechaProximaTrans.getTime(), this.FORMATO_FECHA_SIMPLE));
                                    respuestaDTO = detalleAgendaTransPnFacade.agregarDetalleAgenda(agenda, nombreCanal);
                                    if (!respuestaDTO.getCodigo().equalsIgnoreCase(this.CODIGO_RESPUESTA_EXITOSO)) {
                                        registroExitoso = false;
                                    }
                                }
                                fechaPrimeraQuincena.add(Calendar.MONTH, 1);
                                fechaSegundaQuincena.add(Calendar.MONTH, 1);
                                //procedemos a asignar el proximo registro en una quincena
                                fechaProximaTrans = fechaPrimeraQuincena;
                                agenda.setFechaAgendada(this.formatearFecha(fechaProximaTrans.getTime(), this.FORMATO_FECHA_SIMPLE));
                            }
                        }

                        if (!registroExitoso) {
                            //borramos cualquier cantidad de registros que se hayan realizado
                            respuestaDTO = detalleAgendaTransPnFacade.eliminarDetalleAgenda(agenda.getId(), nombreCanal);
                            if (respuestaDTO == null || respuestaDTO.getCodigo() == null || !respuestaDTO.getCodigo().equalsIgnoreCase(this.CODIGO_RESPUESTA_EXITOSO)) {
                                throw new DAOException("DAO008");
                            }
                            //borramos la cabecera de la agenda
                            respuestaDTO = agendaTransaccionesPnFacade.eliminar(agendaTransaccionesPnFacade.find(agenda.getId()));
                            if (respuestaDTO == null || respuestaDTO.getCodigo() == null || !respuestaDTO.getCodigo().equalsIgnoreCase(this.CODIGO_RESPUESTA_EXITOSO)) {
                                throw new DAOException("DAO008");
                            }
                            //arrojamos el error para notificar al cliente
                            throw new DAOException("DAO008");
                        }

                        break;
                    }
                    //mensual
                    case ('3'): {
                        //asignamos el dia del mes seleccionado
                        if (fechaProximaTrans.get(Calendar.DAY_OF_MONTH) > agenda.getDiaFrecuencia()) {
                            fechaProximaTrans.add(Calendar.MONTH, 1); 
                        }
                        fechaProximaTrans.set(Calendar.DAY_OF_MONTH, agenda.getDiaFrecuencia());
                        agenda.setFechaAgendada(this.formatearFecha(fechaProximaTrans.getTime(), this.FORMATO_FECHA_SIMPLE));
                        cal.setTime(this.formatearFechaStringADate(agenda.getFechalimite(), this.FORMATO_FECHA_SIMPLE));
                        //iteramos mientras la nueva fecha a agendar sea menor o igual a la fecha limite
                        while ((fechaProximaTrans.before(cal) || fechaProximaTrans.equals(cal)) && registroExitoso) {
                            respuestaDTO = detalleAgendaTransPnFacade.agregarDetalleAgenda(agenda, nombreCanal);
                            if (!respuestaDTO.getCodigo().equalsIgnoreCase(this.CODIGO_RESPUESTA_EXITOSO)) {
                                registroExitoso = false;
                            } else {
                                //procedemos a asignar el proximo registro en un mes
                                fechaProximaTrans.add(Calendar.MONTH, 1);
                                agenda.setFechaAgendada(this.formatearFecha(fechaProximaTrans.getTime(), this.FORMATO_FECHA_SIMPLE));
                            }
                        }

                        if (!registroExitoso) {
                            //borramos cualquier cantidad de registros que se hayan realizado
                            respuestaDTO = detalleAgendaTransPnFacade.eliminarDetalleAgenda(agenda.getId(), nombreCanal);
                            if (respuestaDTO == null || respuestaDTO.getCodigo() == null || !respuestaDTO.getCodigo().equalsIgnoreCase(this.CODIGO_RESPUESTA_EXITOSO)) {
                                throw new DAOException("DAO008");
                            }
                            //borramos la cabecera de la agenda
                            respuestaDTO = agendaTransaccionesPnFacade.eliminar(agendaTransaccionesPnFacade.find(agenda.getId()));
                            if (respuestaDTO == null || respuestaDTO.getCodigo() == null || !respuestaDTO.getCodigo().equalsIgnoreCase(this.CODIGO_RESPUESTA_EXITOSO)) {
                                throw new DAOException("DAO008");
                            }
                            //arrojamos el error para notificar al cliente
                            throw new DAOException("DAO008");
                        }
                        break;
                    }
                }
            } else {
                throw new DAOException("DAO008");
            }
            if (!respuestaDTO.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new Exception();
            }

        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN agregarCabeceraAgenda: ")
                    .append("USR-").append(agenda.getIdUsuario())
                    .append("CTAORG-").append(this.numeroCuentaFormato(agenda.getCuentaOrigen()))
                    .append("CTADST-").append(this.numeroCuentaFormato(agenda.getCuentaDestino()))
                    .append("MTO-").append(agenda.getMonto())
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuestaDTO.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }
//        if (respuestaDTO.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN agregarCabeceraAgenda: ")
//                    .append("USR-").append(agenda.getIdUsuario())
//                    .append("CTAORG-").append(this.numeroCuentaFormato(agenda.getCuentaOrigen()))
//                    .append("CTADST-").append(this.numeroCuentaFormato(agenda.getCuentaDestino()))
//                    .append("MTO-").append(agenda.getMonto())
//                    .append("-CH-").append(nombreCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA).toString());
//        }
        return respuestaDTO;
    }

    /**
     * Metodo se encarga de obtener el id de la cabecera de una transaccion
     * agendada
     *
     * @param agenda Objeto con los datos de la cabecera de transaccion a
     * agendar
     * @param nombreCanal nombre del canal
     * @return UtilDTO
     */
    
    
    @Override
    public UtilDTO consultarIdCabeceraAgenda(IBAgendaTransaccionDTO agenda, String nombreCanal) {
        UtilDTO utilDTO = new UtilDTO();
        try {
            
            if (agenda != null && nombreCanal != null && agenda.getCuentaDestino() != null && agenda.getCuentaOrigen() != null && agenda.getMonto() != null
                    && agenda.getIdUsuario() != null && String.valueOf(agenda.getFrecuencia()) != null
                    && agenda.getDiaFrecuencia() != 0 && agenda.getFechalimite() != null && String.valueOf(agenda.getTipo()) != null) {
               utilDTO = agendaTransaccionesPnFacade.consultarIdCabeceraAgenda(agenda, nombreCanal);
            } else {
                throw new DAOException("DAO008");
            }
            if (!utilDTO.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new Exception();
            }

        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN consultarIdCabeceraAgenda: ")
                    .append("USR-").append(agenda.getIdUsuario())
                    .append("CTAORG-").append(this.numeroCuentaFormato(agenda.getCuentaOrigen()))
                    .append("CTADST-").append(this.numeroCuentaFormato(agenda.getCuentaDestino()))
                    .append("MTO-").append(agenda.getMonto())
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            utilDTO.getRespuesta().setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }
//        if (utilDTO.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN consultarIdCabeceraAgenda: ")
//                    .append("USR-").append(agenda.getIdUsuario())
//                    .append("CTAORG-").append(this.numeroCuentaFormato(agenda.getCuentaOrigen()))
//                    .append("CTADST-").append(this.numeroCuentaFormato(agenda.getCuentaDestino()))
//                    .append("MTO-").append(agenda.getMonto())
//                    .append("-CH-").append(nombreCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA).toString());
//        }
        return utilDTO;
    }

    /**
     * 
     * @param idUsuario
     * @param tipo
     * @param idTransaccion
     * @param nombreCanal
     * @return 
     */
    @Override
    public IBAgendaPNDTO consultarIdCabeceraAgendaPP(BigDecimal idUsuario, char tipo, int idTransaccion, String nombreCanal, String idCanal) {
       IBAgendaPNDTO agendaDTO = new IBAgendaPNDTO();
        
         try {
            
            if (idUsuario != null && idTransaccion != 0 && !nombreCanal.equals("")) {
               agendaDTO = agendaTransaccionesPnFacade.consultarIdCabeceraAgendaPP(idUsuario, tipo, idTransaccion, nombreCanal);
            } else {
                throw new DAOException("DAO008");
            }
            if (!agendaDTO.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
                throw new Exception();
            }

        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN consultarIdCabeceraAgendaPP: ")
                    .append("USR-").append(idUsuario)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            agendaDTO.getRespuesta().setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }
//        if (agendaDTO.getRespuesta().getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN consultarIdCabeceraAgendaPP: ")
//                    .append("USR-").append(idUsuario)
//                    .append("-CH-").append(nombreCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA).toString());
//        }
        return agendaDTO;
    }

    
    
    @Override
    public RespuestaDTO eliminarAgendaProgramada(BigDecimal idAgenda, BigDecimal idUsuario, String nombreCanal, String idCanal) {

        RespuestaDTO respuestaDTO = new RespuestaDTO();
        
         try {
            
            //borramos cualquier cantidad de registros que se hayan realizado
            respuestaDTO = detalleAgendaTransPnFacade.eliminarDetalleAgenda(idAgenda, nombreCanal);
             if (respuestaDTO == null || respuestaDTO.getCodigo() == null || !respuestaDTO.getCodigo().equalsIgnoreCase(this.CODIGO_RESPUESTA_EXITOSO)) {
                 throw new DAOException("DAO008");
             }
             //borramos la cabecera de la agenda
             respuestaDTO = agendaTransaccionesPnFacade.eliminar(agendaTransaccionesPnFacade.find(idAgenda));
             if (respuestaDTO == null || respuestaDTO.getCodigo() == null || !respuestaDTO.getCodigo().equalsIgnoreCase(this.CODIGO_RESPUESTA_EXITOSO)) {
                 throw new DAOException("DAO008");
             }
            

        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR DAO EN eliminarAgendaProgramada: ")
                    .append("USR-").append(idUsuario)
                    .append("-CH-").append(nombreCanal)
                    .append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA)
                    .append("-EXCP-").append(e.toString()).toString());
            respuestaDTO.setCodigo(CODIGO_EXCEPCION_GENERICA);//revisar el log
        }
//        if (respuestaDTO.getCodigo().equals(CODIGO_RESPUESTA_EXITOSO)) {
//            logger.error( new StringBuilder("EXITO DAO EN eliminarAgendaProgramada: ")
//                    .append("USR-").append(idUsuario)
//                    .append("-CH-").append(nombreCanal)
//                    .append("-DT-").append(new Date())
//                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA).toString());
//        }
        return respuestaDTO;
    
    }


    
}
