/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import org.apache.log4j.Logger;

/**
 *
 * @author cesar.mujica
 */
public class BDSUtil {

   
    private List<String> feriadosList;

    public static final String FORMATO_ARCHIVO_CARGA_TXT = "text/plain";
    public static final String FORMATO_ARCHIVO_CARGA_XLS = "application/vnd.ms-excel";
    public static final String FORMATO_ARCHIVO_CARGA_XLSX = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    /**
     * Constantes para los MAPS
     */
    /**
     * Indica si un registro u objeto existe o no
     */
    public static final String EXISTE = "existe";
    /**
     * Indica un usuario
     */
    public static final String USUARIO_CONEXION = "usuarioConexion";
    /**
     * Indica un usuario
     */
    public static final String USUARIO = "usuario";
    /**
     * Indica una lista de empresausuario
     */
    public static final String LISTA_EMPRESA_USUARIO = "listEmpresaUsuario";
    /**
     * Indica la lista de usuarios
     */
    public static final String USUARIOS = "usuarios";
    /**
     * Indica la lista de empresas
     */
    public static final String EMPRESAS = "empresas";
    /**
     * Indica si un registro es o no valido
     */
    public static final String VALIDO = "valido";
    /**
     * Indica el id del usuario
     */
    public static final String ID_USUARIO = "idUsuario";
    /**
     * Indica el id de la empresa
     */
    public static final String ID_EMPRESA = "idEmpresa";
    /**
     * Indica el nombre de la empresa
     */
    public static final String NOMBRE_EMPRESA = "nombreEmpresa";
    /**
     * Indica el codigo de la empresa
     */
    public static final String CODIGO_EMPRESA = "codigoEmpresa";
    /**
     * Lista de modulos
     */
    public static final String MODULOS = "modulos";
    /**
     * Permite indicar si un usuario se encuentra bloqueado
     */
    public static final String USUARIO_BLOQUEADO = "usuario_bloqueado";
    /**
     * Permite indicar si la clave esta vencida
     */
    public static final String CLAVE_VENCIDA = "clave_vencida";
    /**
     *
     */
    public static final String OTP = "otp";

    /**
     * Permite indicar si existe una conexion simultanea de algun usuario
     */
    public static final String CONEXION_SIMULTANEA = "conexionSimultanea";

    /**
     * Indica la ultima fecha de conexion
     */
    public static final String FECHA_ULTIMA_CONEXION = "fechaUltimaConexion";

    /**
     * Constante que almacena codigo de respuesta exitoso para procedimientos
     * almacenados
     */
    public static final String CODIGO_RESPUESTA_EXITOSO_SP = "1";

    /**
     * Constante que almacena codigo de respuesta exitoso para procedimientos
     * almacenados
     */
    public static final String CODIGO_RESPUESTA_FALLIDO_SP = "0";

    /**
     * Constante que almacena codigo del usario conectado
     */
    public static final Character CODIGO_USUARIO_CONECTADO = '1';

    /**
     * Constante que almacena codigo del usario desconectado
     */
    public static final Character CODIGO_USUARIO_DESCONECTADO = '0';

    /**
     * Constante que almacena codigo del canal mobile
     */
    public static final String CODIGO_CANAL_MOBILE = "1";

    /**
     * Constante que almacena codigo del canal web
     */
    public static final String CODIGO_CANAL_WEB = "2";

    /**
     * Constante que almacena codigo del canal PJ
     */
    public static final String CODIGO_CANAL_PJ = "3";

    /**
     * Constante que almacena codigo de respuesta exitoso para los dao
     */
    public static final String CODIGO_RESPUESTA_EXITOSO = "000";

    /**
     * Constante que almacena codigo de error para las validaciones de
     * parametros
     */
    public static final String CODIGO_ERROR_VALIDACIONES = "888";

    /**
     * Constante que almacena la descripcion de respuesta exitoso para los dao
     */
    public static final String DESCRIPCION_RESPUESTA_EXITOSO = "OK";

    /**
     * Constante que almacena la descripcion de respuesta exitoso para los dao
     */
    public static final String DESCRIPCION_RESPUESTA_FALLIDA = "FAIL";

    /**
     * Constante que almacena la descripcion de respuesta exitoso para los SP
     * que retorna un codigo de salida
     */
    public static final String DESCRIPCION_RESPUESTA_EXITOSO_SP = "OK";
    
    /**
     * Constante que almacena la descripcion de respuesta fallida para los SP
     * que retorna un codigo de salida
     */
    public static final String DESCRIPCION_RESPUESTA_FALLIDA_SP = "FAIL-SP";
    
    /**
     * Constante que almacena la descripcion de respuesta fallida para los SP
     * que retorna un codigo de salida
     */
    public static final String CODIGO_RESPUESTA_FALLIDA_SP = "999";

    /**
     * Constante que almacena codigo de respuesta JPA noResultException
     */
    public static final String CODIGO_SIN_RESULTADOS_JPA = "JPA005";

    /**
     * Constante que almacena codigo de respuesta JPA noResultException
     */
    public static final String DESCRIPCION_RESPUESTA_FALLIDA_JPA = "JPA004";

    /**
     * Constante que almacena texto de respuesta JPA noResultException
     */
    public static final String TEXTO_SIN_RESULTADOS_JPA = "No se encontraron registros para esta consulta";

    /**
     * Constante que almacena el jndi de conexion a Oracle 9
     */
    public static final String JNDI_ORACLE_9 = "jdbc/CORE";
    
     /**
     * Constante que almacena el jndi de conexion a Oracle 9
     */
   public static final String JNDI_ORACLE_TEMPORAL = "jdbc/temp";

    /**
     * Constante que almacena el jndi de conexion a Oracle 11
     */
    public static final String JNDI_ORACLE_11 = "jdbc/IB";

    /**
     * Constante que almacena el jndi de conexion a Oracle P2P
     */
    public static final String JNDI_P2P = "p2p";

    /**
     * Constante que almacena el codigo de error para una excepcion generica que
     * indica validar el log
     */
    public static final String CODIGO_EXCEPCION_GENERICA = "EXC999";

    /**
     * constante que indica que no se actualizó, eliminó o insertó un registro
     * en la bd para un SP
     */
    public static final String CODIGO_EXCEPCION_SIN_ACTUALIZAR = "DAO007";

    /**
     * constante que indica el telf ingresado ya esta afiliado al servicio P2P
     */
    public static final String CODIGO_EXCEPCION_P2P_TELF_DUP = "P2P001";

    /**
     * constante que indica la cuenta ingresada ya esta afiliada al servicio P2P
     */
    public static final String CODIGO_EXCEPCION_P2P_CTA_DUP = "P2P002";

    /**
     * Constante que almacena el set de caracteres de Oracle 9
     */
    public static final String CHARSET_ORACLE_9 = "Cp1252";

    /**
     * Constante que almacena el codigo del producto cuenta corriente
     */
    public static final String CODIGO_PRODUCTO_CC = "BCC";

    /**
     * Constante que almacena el codigo del producto cuenta ahorro
     */
    public static final String CODIGO_PRODUCTO_CA = "BCA";

    /**
     * Constante que almacena el codigo del producto cuenta en moneda extranjera
     */
    public static final String CODIGO_PRODUCTO_ME = "BDC";

    /**
     * Constante que almacena el codigo del producto tarjetas de credito
     */
    public static final String CODIGO_PRODUCTO_TDC = "BTC";

    /**
     * Constante que almacena el codigo del producto prestamos
     */
    public static final String CODIGO_PRODUCTO_PR = "BPR";

    /**
     * Constante que almacena el codigo del producto depositos a plazo
     */
    public static final String CODIGO_PRODUCTO_DP = "BDP";

    /**
     * Constante que almacena el formato de fecha simple
     */
    public static final String FORMATO_FECHA_SIMPLE = "dd/MM/yyyy";

    /**
     * Constante que almacena el formato de fecha simple año
     */
    public static final String FORMATO_FECHA_SIMPLE_ORDEN = "yyyyMMdd";

    /**
     * Constante que almacena el formato de fecha con solo mes y año
     */
    public static final String FORMATO_FECHA_MESANO = "MM/yyyy";

    /**
     * Constante que almacena el codigo de bloqueo de acceso de usuarios a un
     * canal
     */
    public static final String CODIGO_BLOQUEO_CANAL = "BL";

    /**
     * Constante que almacena codigo que indica que la session esta activa
     */
    public static final String CODIGO_SESSION_ACIVA = "1";

    /**
     * Constante que almacena codigo del parametro TIMEOUT de la tabla
     * IB_PARAMETROS
     */
    public static final String CODIGO_TIME_OUT = "TMS";

    /**
     * Constante que almacena codigo del parametro TIMEOUT de la tabla
     * IB_PARAMETROS para usuarios PJ
     */
    public static final String CODIGO_TIME_OUT_PJ = "pjw.global.timeOut.minutos";

    /**
     *
     * Constante que almacena el formato de fecha simple
     */
    public static final String FORMATO_FECHA_COMPLETA = "dd/MM/yyyy hh:mm:ss";

    /**
     *
     * Constante que almacena el formato de fecha 24 horas
     */
    public static final String FORMATO_FECHA_24 = "dd/MM/yyyy HH:mm:ss";

    /**
     *
     * Constante que almacena el formato de hora hh:mm:ss
     */
    public static final String FORMATO_HORA_HHMMSS = "hh:mm:ss";
    
    /**
     *
     * Constante que almacena el formato de hora hh:mm
     */
    public static final String FORMATO_HORA_HHMM = "hh:mm";

    /**
     * Constante que almacena codigo de busqueda de modulos de usuarios con
     * perfil de piloto
     */
    public static final Character CODIGO_BUSQUEDA_PILOTO = 'P';

    /**
     * Constante que almacena codigo de busqueda de modulos de usuarios que no
     * poseen perfil de piloto
     */
    public static final Character CODIGO_BUSQUEDA_NO_PILOTO = 'A';

    public static final String T_TRANSF_PROPIAS = "P"; //Tipo de transferencias propias
    public static final String T_TRANSF_OTROSBANCOS = "TOB"; //Tipo de transferencias terceros a otros bancos
    public static final String T_TRANSF_PROPOTROSBANCOS = "POB"; //Tipo de transferencias propias otros bancos
    public static final String T_TRANSF_3ROSDELSUR = "TBDS"; //Tipo de transferencia a terceros del banco del sur

    public static final String T_PAG_TDC_PROPIAS = "P"; //Tipo de pago tdc propias
    public static final String T_PAG_TDC_OTROSBANCOS = "TOB"; //Tipo de pago tdc terceros a otros bancos
    public static final String T_PAG_TDC_PROPOTROSBANCOS = "POB"; //Tipo de pago tdc propias otros bancos
    public static final String T_PAG_TDC_3ROSDELSUR = "TBDS"; //Tipo de pago tdc a terceros del banco del sur

    public static final String COD_MOV_TAQUILLA_BCA = "CMTBCA";
    public static final String COD_MOV_TAQUILLA_BCC = "CMTBCC";
    public static final String COD_TRANSF_DELSUR = "CTBDS";
    public static final String COD_TRANSF_RECIBIDA = "CTR";
    public static final String COD_TRANSF_ENVIADA = "CTE";
    public static final String COD_MOV_ME = "CMME";

    public static final String BIN_TDD = "6015"; //Tipo de transferencias propias

    /**
     * Cuatros primeros digitos que identifican la cuentas del banco del sur
     */
    public static final String DIGITOS_INICIALES_BANCO_DEL_SUR = "0157";

    /**
     * Constante que almacena el id de transferencias a terceros del sur
     */
    public static final String ID_TRANS_CTAS_TERC_DELSUR = "1";

    /**
     * Constante que almacena el id de pagos tdc a terceros del sur
     */
    public static final String ID_TRANS_TDC_TERC_DELSUR = "5";

    /**
     * Constante que almacena el id de transferencias propias a otros bancos
     */
    public static final String ID_TRANS_CTAS_P_OTROS_BCOS = "7";

    /**
     * Constante que almacena el id de pagos tdc propias a otros bancos
     */
    public static final String ID_TRANS_TDC_P_OTROS_BCOS = "8";

    /**
     * Constante que almacena el id de transferencias a terceros otros bancos
     */
    public static final String ID_TRANS_CTAS_T_OTROS_BCOS = "3";

    /**
     * Constante que almacena el id de pagos tdc terceros a otros bancos
     */
    public static final String ID_TRANS_TDC_T_OTROS_BCOS = "4";

    /**
     * Constante que almacena el id de transferencias a cuentas propias DELSUR
     */
    public static final String ID_TRANS_CTAS_PROPIAS_DELSUR = "6";

    /**
     * Constante que almacena el id de pagos tdc propias DELSUR
     */
    public static final String ID_TRANS_TDC_PROPIAS_DELSUR = "2";

    /**
     * Constante que almacena el id de recargas DIGITEL este ID es un codigo
     * porque a futuro la seccion de pago de servicios puede englobar otros
     * pagos ademas de DIGITEl
     */
    public static final String ID_TRANS_REC_DIGITEL = "rec.digitel";

    /**
     * Constante que almacena el id que indica pago de P2P
     */
    public static final String ID_TRANS_PAGARP2P = "1921";
    
    /**
     * Constante que almacena el id que indica pago de P2C
     */
    public static final String ID_TRANS_PAGARP2C = "1922";

    /**
     * Constante que almacena el idPago NominaCargaMasiva
     */
    public static final String ID_PAGO = "idPago";

    /**
     * Constante que almacena la lista de errores en NominaCargaMasiva
     */
    public static final String LISTA_ERRORES_CARGA_NOMINA_MASIVA = "listaErroresCargaNominaMasiva";

    /**
     * Constante que almacena la lista de errores en CargaPagosEspecialesMasivos
     */
    public static final String LISTA_ERRORES_CARGA_PAGOS_ESPECIALES_MASIVA = "listaErroresCargaPagosEspecialesMasivos";

    /**
     * Variable string declarada para la cabecera de la carga
     *
     */
    public static final String NOMBRE_CABECERA_DETALLE = "IbCargaPagosEspPj";

    /**
     * Variable string declarada para el detalle de la carga
     *
     */
    public static final String NOMBRE_DETALLE = "IbCargaPagosEspDetPjsList";

    /**
     * Variable string declarada para listar aprovadores
     *
     */
    public static final String NOMBRE_APROVADOR = "ListaAprobadores";

    /**
     * Constante que almacena la lista de errores de carga de cualquier archivo
     */
    public static final String LISTA_ERRORES_CARGA_ARCHIVO = "listaErroresCargaArchivo";

    /**
     * Constante que almacena la lista de errores de carga de una carga manualde
     * pagos corporativos
     */
    public static final String LISTA_ERRORES_CARGA_MANUAL = "listaErroresCargaManual";

    /**
     * Constante que almacena la lista de errores de carga de cualquier archivo
     */
    public static final String LISTA_ERRORES_CARGA_ARCHIVO_SP = "listaErroresCargaArchivoSP";

    /**
     * Constante que almacena la lista de exitosos de carga de cualquier archivo
     */
    public static final String LISTA_EXITOSO_CARGA_ARCHIVO = "listaExistosoCargaArchivo";

    /**

     * Log de sistema
     */
    private static final Logger logger = Logger.getLogger(BDSUtil.class.getName());

    /**
     * Indica el nombre del banco de usuarios
     */
    public static final String NOMBRE_BANCO = "nombreBanco";

    /**
     * Indica el nombre del banco de usuarios
     */
    public static final String ID_BENEFICIARIO = "idBeneficiario";

    /**
     * Indica mensaje a enviar
     */
    public static final String MENSAJE = "MENSAJE";

    /**
     * Indica que el estatus está activo
     */
    public static final String STATUS_ACTIVO = "1";
    
    /**
     * Estado de la clave de operaciones especiales
     */
    public static final String ESTADO_CLAVE_OP = "estado";
    
    /*
     * Convierte un objeto java.util.Date a javax.xml.datatype.XMLGregorianCalendar
     */
    public static XMLGregorianCalendar toXMLGregorianCalendar(Date date) {
        GregorianCalendar gCalendar = new GregorianCalendar();
        gCalendar.setTime(date);
        XMLGregorianCalendar xmlCalendar = null;
        try {
            xmlCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(gCalendar);
        } catch (DatatypeConfigurationException e) {
            logger.error( new StringBuilder("ERROR BDSUTIL EN toXMLGregorianCalendar: ").append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA).append("-EXCP-").append(e.toString()).toString());
        }
        return xmlCalendar;
    }

    /*
     * Convierte un XMLGregorianCalendar a java.util.Date in Java
     */
    public static Date toDate(XMLGregorianCalendar calendar) {
        if (calendar == null) {
            return null;
        }
        return calendar.toGregorianCalendar().getTime();
    }

    /**
     * Genera UUID
     *
     * @return String
     */
    public static String generateUUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * elimina la coma de un monto y la reemplaza por punto
     *
     * @param value valor
     * @return String
     */
    public static String eliminarformatoSimpleMonto(String value) {
        if (value == null || value.isEmpty()) {
            return "";
        }
        value = value.replaceAll("[.]", "");
        return value.replace(',', '.');
    }

    /**
     * Formatea un monto
     *
     * @param value valor
     * @return String
     */
    public static String formatearMonto(BigDecimal value) {
        if (value == null) {
            return "";
        }

        DecimalFormatSymbols mySymbols = new DecimalFormatSymbols();
        mySymbols.setDecimalSeparator(',');
        mySymbols.setGroupingSeparator('.');
        DecimalFormat newFormat = new DecimalFormat("#,##0.00", mySymbols);
        return newFormat.format(value.doubleValue());
    }

    /**
     * ParseMonto
     *
     * @param value valor
     * @return Double
     */
    public static Double parseMonto(String value) {
        if (value == null) {
            return null;
        }

        DecimalFormatSymbols mySymbols = new DecimalFormatSymbols();
        mySymbols.setDecimalSeparator(',');
        mySymbols.setGroupingSeparator('.');
        DecimalFormat newFormat = new DecimalFormat("#,##0.00", mySymbols);
        try {
            return newFormat.parse(value).doubleValue();
        } catch (ParseException e) {
            logger.error( new StringBuilder("ERROR BDSUTIL EN formatearMonto: ").append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA).append("-EXCP-").append(e.toString()).toString());
        }

        return null;
    }

    /**
     * Formatea la fecha/hora
     *
     * @param value fecha a formatear
     * @param formato formato para asignar a la fecha
     * @return String
     */
    public static String formatearFecha(Date value, String formato) {
        if (value == null) {
            return "";
        }

        SimpleDateFormat f = new SimpleDateFormat(formato);

        return f.format(value);
    }

    /**
     * Formatea la fecha/hora en String segun el formato recibido
     *
     * @param value fecha a formatear
     * @param formato formato para asignar a la fecha
     * @return fecha en String formateada
     */
    public static String formatearFechaString(String value, String formato) {
        if (value == null) {
            return "";
        }

        SimpleDateFormat f = new SimpleDateFormat(formato);

        return f.format(value);
    }

    /**
     * Formatea la fecha/hora con el formato que se le quiere dar a la fecha
     *
     * @param value Date
     * @param formato String
     * @return Date
     */
    public static Date formatearFechaDateADate(Date value, String formato) {
        Date fechaSalida = new Date();
        if (value == null) {
            return fechaSalida;
        }
        SimpleDateFormat f = new SimpleDateFormat(formato);
        String fechaString = f.format(value);
        try {
            fechaSalida = f.parse(fechaString);
        } catch (ParseException e) {
            logger.error( new StringBuilder("ERROR BDSUTIL EN formatearFechaDateADate: ").append("-DT-").append(value)
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA).append("-EXCP-").append(e.toString()).toString());
        }
        return fechaSalida;
    }

    /**
     * Formatea la fecha/hora en String segun el formato recibido
     *
     * @param value fecha a formatear
     * @param formato formato para asignar a la fecha
     * @return fecha en Date formateada
     */
    public static Date formatearFechaStringADate(String value, String formato) {
        Date fechaSalida = new Date();
        if (value == null) {
            return fechaSalida;
        }
        SimpleDateFormat f = new SimpleDateFormat(formato);
        try {
            fechaSalida = f.parse(value);
        } catch (ParseException e) {
            logger.error( new StringBuilder("ERROR BDSUTIL EN formatearFechaStringADate: ").append("-DT-").append(value)
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA).append("-EXCP-").append(e.toString()).toString());
        }
        return fechaSalida;
    }

    /**
     * Metodo que calcula minutos entre dos fechas
     *
     * @param finicio fecha inicio
     * @param ffin fecha fin
     * @return long
     */
    public static long calculaMinutosEntreFechas(Date finicio, Date ffin) {
        long milis1;
        long milis2;
        long diff;
        long diffMinutos = 0;
        Calendar cinicio = Calendar.getInstance();
        Calendar cfinal = Calendar.getInstance();
        try {
            cinicio.setTime(finicio);
            cfinal.setTime(ffin);

            milis1 = cinicio.getTimeInMillis();

            milis2 = cfinal.getTimeInMillis();

            diff = milis2 - milis1;

            diffMinutos = Math.abs(diff / (60 * 1000));
        } catch (Exception e) {
            logger.error( new StringBuilder("ERROR BDSUTIL EN calculaMinutosEntreFechas: ").append("-DT-").append(new Date())
                    .append("-STS-").append(DESCRIPCION_RESPUESTA_FALLIDA).append("-EXCP-").append(e.toString()).toString());
            diffMinutos = 0;
        }
        return diffMinutos;
    }

    /**
     * Metodo para validar si un BigDecimal es null
     *
     * @param value valor BigDecimal
     * @return boolean
     */
    public static boolean isBDecimalNull(BigDecimal value) {
        return value == null;
    }

    /**
     * Metodo que valida si un BigDecimal es null o zero
     *
     * @param value valor BigDecimal
     * @return boolean
     */
    public static boolean isBDecimalNullOrZero(BigDecimal value) {
        return value == null || value == new BigDecimal(0);

    }

    /**
     * Formatea una cadena, se reemplazara por cuatro asteriscos (****) desde el
     * inicio de la cadena hasta length()-4 de la misma ej: 1234567890 ->
     * ****7890
     *
     * @param value String valor de la cadena a formatear,
     * @return String
     */
    public String numeroCuentaFormato(String value) {
        return ("****" + value.substring(value.length() - 4, value.length()));
    }

    /**
     * Método que convierte un List<String> en un String separando los valores
     * con una coma (,) author wilmer.rondon
     *
     * @param listString
     * @param separador
     * @return String
     */
    public String convierteListStringEnString(List<String> listString, String separador) {
        StringBuilder listcheques = new StringBuilder();
        if (listString != null) {
            for (String i : listString) {
                if (listcheques.length() > 0) {
                    listcheques.append(separador);
                }
                listcheques.append(i);
            }
        }
        return listcheques.toString();
    }

    /**
     * Formatea una cadena, se reemplazara por cuatro asteriscos (****) los
     * caracteres intermedios de un String con longitud mayor a 8 ej: 1234567890
     * -> 1234****7890
     *
     * @param value String valor de la cadena a formatear,
     * @return String
     */
    public static String formatoAsteriscosWeb(String value) {
        if (value != null && value.length() > 8) {
            return (value.substring(0, 4) + "****" + value.substring(value.length() - 4, value.length()));
        } else {
            return (value);
        }
    }

    /*
    Metodo utilizado para validar si la cuenta de 20 digitos pertenece al banco del sur
     */
    public static boolean isCuentaDelSur(String ctaBeneficiario) {
        String auxCuenta = "";
        boolean auxBool = false;
        auxCuenta = ctaBeneficiario.substring(0, 4);
        if (auxCuenta.equalsIgnoreCase(DIGITOS_INICIALES_BANCO_DEL_SUR)) {
            auxBool = true;
        }
        return auxBool;
    }

    /*
    Metodo utilizado para devolver diez digitos de la cuenta perteneciente a banco del sur
     */
    public static String substrCuentaDelSur(String ctaBeneficiario) {
        String cuenta = "";
        cuenta = ctaBeneficiario.substring(2, 11);
        return cuenta;
    }

    public void validarFechaSabadoDomingo() {
        Date fecha = new Date();
        Calendar cal = null;
        String letraD = "";
        int nD = -1;
        cal = Calendar.getInstance();
        cal.setTime(fecha);
        nD = cal.get(Calendar.DAY_OF_WEEK);
        if (nD == 1 || nD == 7) {
            SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
            feriadosList.add(formateador.format(fecha));
        }
    }

    public Date isFechaHabil(String cuenta, String horaMaxima, List<String> feriadosLists, String codCanal) throws ParseException {
        Date fechaOperacion = new Date();
        Date fechaFinaldeOperacion = new Date();
        
        Boolean flag = Boolean.TRUE;
        String  sabaDomin, query = "";
        Integer dia, mes, año, hora, minutos, segundos, cont = 0, horaCompNumber = 0;
        Calendar calendario = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        horaCompNumber = Integer.valueOf(horaMaxima);

        

        if (cuenta.equals("")) {
            año = calendario.get(Calendar.YEAR);
            mes = calendario.get(Calendar.MONTH) + 1;
            dia = calendario.get(Calendar.DAY_OF_MONTH);
            hora = calendario.get(Calendar.HOUR_OF_DAY);
            minutos = calendario.get(Calendar.MINUTE);
            segundos = calendario.get(Calendar.SECOND);
            if (hora > horaCompNumber) {
                do {
                    query = dia + "/" + mes + "/" + año;
                    flag = validarFecha(query, feriadosLists);
                    if (flag) {
                        dia++;
                    }
                } while (flag);
                try {

                    fechaFinaldeOperacion = formatter.parse(query);

                } catch (ParseException e) {
                    e.printStackTrace();
                }

            } else {
                fechaFinaldeOperacion = calendario.getTime();
            }
        } else {
            fechaFinaldeOperacion = calendario.getTime();
        }

        return fechaFinaldeOperacion;
    }

    public Boolean validarFecha(String fecha, List<String> ListFeriados) {
        Boolean flag = Boolean.FALSE;
        this.feriadosList = ListFeriados;
        validarFechaSabadoDomingo();

        Optional<String> queryResult = feriadosList.stream()
                .filter(value -> value.equalsIgnoreCase(fecha))
                .findFirst();
        if (queryResult.isPresent()) {
            System.out.println("Found " + fecha + " in list");
            flag = Boolean.TRUE;
        } else {
            System.out.println("Could not find " + fecha + " in list");
            flag = Boolean.FALSE;
        }

        return flag;

    }

    public List<String> getFeriadosList() {
        return feriadosList;
    }

    public void setFeriadosList(List<String> feriadosList) {
        this.feriadosList = feriadosList;
    }

}
