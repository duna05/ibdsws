/*
 * Valida los archivos de texto de cargas masivas
 */
package com.bds.ws.validator;

import com.bds.ws.dto.IbCargaArchivoDTO;
import com.bds.ws.dto.IbErroresCargaPjDTO;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;

/**
 *
 * @author roger.muñoz
 */
public abstract class Validator {

    protected IbCargaArchivoDTO ibCargaArchivoDto;
    protected Map<String, String> mapErrorres;
    private IbErroresCargaPjDTO ibErroresCargaPjDTO;

    private BufferedReader br;

    protected String linea = "";
    protected int nroLinea = 0;

    protected int pos = 0;

    private final String EXT_TEXT = "txt";

    private String extension = "";
    private String tipoData = "";

    public String getExtension() {
        return extension;
    }

    public boolean isText() {
        return (this.getExtension().compareTo(EXT_TEXT) == 0);
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    protected String extract(int length) {
        return linea.substring(pos, pos += length);
    }

    public String getTipoData() {
        return tipoData;
    }

    public void setTipoData(String tipoData) {
        this.tipoData = tipoData;
    }

    public IbErroresCargaPjDTO getIbErroresCargaPjDTO() {
        if (this.ibErroresCargaPjDTO == null) {
            this.ibErroresCargaPjDTO = new IbErroresCargaPjDTO();
            this.ibErroresCargaPjDTO.setIbErroresCargaPjDTO(new ArrayList<IbErroresCargaPjDTO>());
        }
        return this.ibErroresCargaPjDTO;
    }

    public Validator(IbCargaArchivoDTO ibCargaArchivoDto) {
        this.ibCargaArchivoDto = ibCargaArchivoDto;
        this.longitudDetalleFONZ03 = 46;
        this.longitudDetalleFONZ04 = 369;
        this.extension = this.obtenerExtensionArchivo();
    }

    protected BufferedReader getBr() {
        return br;
    }

    protected void setBr(BufferedReader br) {
        this.br = br;
    }

    public Map<String, String> getMapErrorres() {
        return mapErrorres;
    }

    public void setMapErrorres(Map<String, String> mapErrorres) {
        this.mapErrorres = mapErrorres;
    }

    public List<IbErroresCargaPjDTO> getIbErroresCargaPjDTOLista() {
        return this.getIbErroresCargaPjDTO().getIbErroresCargaPjDTO();
    }

    public void setIbErroresCargaPjDTOLista(IbErroresCargaPjDTO ibErroresCargaPjDTO) {
        this.ibErroresCargaPjDTO = ibErroresCargaPjDTO;
    }

    public void addError(String line, int nroLinea, String txtError) {
        IbErroresCargaPjDTO ibErroresCargaPjDTO = null;
        boolean found = false;

        for (IbErroresCargaPjDTO ibErrorCarga : this.getIbErroresCargaPjDTOLista()) {
            found = (ibErrorCarga.getNumeroLinea() == nroLinea);
            if (found) {
                ibErroresCargaPjDTO = ibErrorCarga;
                break;
            }
        }

        //LA LINEA NO FUE CONSEGUIDA EN LOS ERRORES
        if (!found) {
            ibErroresCargaPjDTO = new IbErroresCargaPjDTO();
            ibErroresCargaPjDTO.setLinea(line);
            ibErroresCargaPjDTO.setNumeroLinea(nroLinea);
            ibErroresCargaPjDTO.getError().add(txtError);
            getIbErroresCargaPjDTOLista().add(ibErroresCargaPjDTO);
        } else {
            ibErroresCargaPjDTO.getError().add(txtError);
        }
    }

    public void modificarLineaError(String line, int nroLinea) {
        boolean found = false;
        for (IbErroresCargaPjDTO ibErrorCarga : this.getIbErroresCargaPjDTOLista()) {
            found = (ibErrorCarga.getNumeroLinea() == nroLinea);
            if (found) {
                ibErrorCarga.setLinea(line);
                break;
            }
        }
    }

    /**
     * agrega un error, indicando el key del mismo(en table texto) y los valores
     * de reemplazo para las variables de sustitucion "$variable" presentes en
     * el texto del error
     *
     * @param line
     * @param nroLinea
     * @param errkey key del error de tabla ib_textos
     * @param replaces mapa de key/value con los reemplazos, ej: $hora/24
     */
    public void addError(String line, int nroLinea, String errkey, Parametro param) {
        IbErroresCargaPjDTO ibErroresCargaPjDTO = new IbErroresCargaPjDTO();
        ibErroresCargaPjDTO.setLinea(line);
        ibErroresCargaPjDTO.setNumeroLinea(nroLinea);
        ibErroresCargaPjDTO.setError(new ArrayList());
        boolean found = false;

        String txtError = mapErrorres.get(errkey);

        List<String> listKey = new ArrayList<String>(param.getMapa().keySet());
        for (String key : listKey) {
            txtError = txtError.replace(key, (String) param.getMapa().get(key));
        }

        for (IbErroresCargaPjDTO ibErrorCarga : this.getIbErroresCargaPjDTOLista()) {
            found = (ibErrorCarga.getNumeroLinea() == nroLinea);
            if (found) {
                ibErroresCargaPjDTO = ibErrorCarga;
                break;
            }
        }

        //LA LINEA NO FUE CONSEGUIDA EN LOS ERRORES
        if (!found) {
            ibErroresCargaPjDTO = new IbErroresCargaPjDTO();
            ibErroresCargaPjDTO.setLinea(line);
            ibErroresCargaPjDTO.setNumeroLinea(nroLinea);
            ibErroresCargaPjDTO.getError().add(txtError);
            getIbErroresCargaPjDTOLista().add(ibErroresCargaPjDTO);
        } else {
            ibErroresCargaPjDTO.getError().add(txtError);
        }
    }

    public int countErrors() {
        return getIbErroresCargaPjDTOLista().size();
    }

    public void clearErrors() {
        if (ibErroresCargaPjDTO == null) {
            ibErroresCargaPjDTO = new IbErroresCargaPjDTO();
        }
        if (ibErroresCargaPjDTO.getIbErroresCargaPjDTO() == null) {
            ibErroresCargaPjDTO.setIbErroresCargaPjDTO(new ArrayList());
        }
        ibErroresCargaPjDTO.getIbErroresCargaPjDTO().clear();

    }

    protected int longitudCabecera;

    public int getLongitudCabecera() {
        return longitudCabecera;
    }

    public void setLongitudCabecera(int longitudCabecera) {
        this.longitudCabecera = longitudCabecera;
    }

    protected int longitudDetalle;

    public int getLongitudDetalle() {
        return longitudDetalle;
    }

    public void setLongitudDetalle(int longitudDetalle) {
        this.longitudDetalle = longitudDetalle;
    }

    protected int longitudDetalleFONZ03;
    protected int longitudDetalleFONZ04;

    public int getLongitudDetalleFONZ03() {
        return longitudDetalleFONZ03;
    }

    public void setLongitudDetalleFONZ03(int longitudDetalle) {
        this.longitudDetalleFONZ03 = longitudDetalle;
    }

    public int getLongitudDetalleFONZ04() {
        return longitudDetalleFONZ04;
    }

    public void setLongitudDetalleFONZ04(int longitudDetalleFONZ04) {
        this.longitudDetalleFONZ04 = longitudDetalleFONZ04;
    }

    public byte[] readfile(String filename) throws FileNotFoundException, IOException {
        byte[] data;
        data = new byte[1000];
        File file = new File(filename);
        FileInputStream fis = new FileInputStream(file);
        int i = fis.read(data);
        return data;
    }

    public abstract Object leerCabecera(Map<String, Object> param) throws Exception;

    /**
     * *
     * Lee las lineas 2..N correspondientes a los detalles
     */
    public abstract Object leerDetalle(Map<String, Object> param) throws Exception;

    /**
     * *
     * Lee las lineas 2..N correspondientes a los detalles de excel
     */
    public abstract Object leerDetalle(Row row, Integer nroLinea);

    /**
     * valida si un string sea solo numerico
     *
     * @param texto el string a validar
     * @return
     */
    public boolean validaSoloNumeros(String texto) {
        return texto.matches("[0-9]+");
    }
    /**
     * valida si un string sea solo numerico con espacios en blanco
     *
     * @param texto el string a validar
     * @return
     */    
    public boolean validaSoloNumerosYEspaciosEnBlanco(String texto) {
        return texto.matches("[0-9 ]+");
    }

    /**
     * valida si el indice rif es valido
     *
     * @param texto el string a validar
     * @return
     */
    public boolean validaIndiceRif(String texto) {
        return texto.matches("[JVGPEjvgpe]+");
    }

    /**
     * valida si un string posee solo letras
     *
     * @param texto el string a validar
     * @return
     */
    public boolean validaAlfabetico(String texto) {
        return texto.replaceAll(" ", "").matches("[a-zA-ZÁÉÍÓÚáéíóúÑñ]+");
    }
    
    /**
     * valida si un string posee solo letras y los caracteres especiales "," y "."
     *
     * @param texto el string a validar
     * @return
     */
    public boolean validaAlfanumericoPuntoComa(String texto) {
        return texto.replaceAll(" ", "").matches("[a-zA-Z0-9ÁÉÍÓÚáéíóúÑñ.,]+");
    }
    
    public boolean validaAlfanumericoConGuion(String texto) {
        return texto.replaceAll(" ", "").matches("[a-zA-Z0-9ÁÉÍÓÚáéíóúÑñ-]+");
    }

    public boolean validaAlfaNumerico(String texto) {
        return texto.replaceAll(" ", "").matches("[a-zA-Z0-9ÁÉÍÓÚáéíóúÑñ.,;:]+");
    }

    public boolean validaEmail(String texto) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        return texto.replaceAll(" ", "").matches(EMAIL_PATTERN);
    }

    /**
     * valida que el string "letra" sea solo una de las letras contenidas en el
     * string "letras"
     *
     * @param letra
     * @param letras
     * @return
     */
    public boolean validaSolaUnaDeEstasLetras(String letra, String letras) {
        return (letra.length() == 1) && (letras.indexOf(letra) >= 0);
    }

    /**
     * retorna la diferencia en horas entre dos fechas
     *
     * @param d1
     * @param d2
     * @return
     */
    public static long diferenciaDeHorasEntre(Date d1, Date d2) {
        final int MILLI_TO_HOUR = 1000 * 60 * 60;
        long salida = (long) (d1.getTime() - d2.getTime()) / MILLI_TO_HOUR;
        return salida;
    }

    /**
     * retorna la fecha del sistema del instante, similar a la funcion "now" o
     * "sysdate" de otras plataformas
     *
     * @return la fecha y hora actual del sistema
     */
    public static Date ahora() {
        return new Date();
    }

    /**
     * verifica si un elemento entero esta entra a y b
     *
     * @param elememto
     * @param a
     * @param b
     * @return ::= " elemento entre a y b? " ::= " a <= elemento <= b "
     */
    public static boolean between(int elemento, int a, int b) {
        return (a <= elemento) && (elemento <= b);
    }

    public boolean validaSoloLetras(String texto) {
        return texto.replaceAll(" ", "").matches("[a-zA-Z]+");
    }

    public boolean validaFecha(String strFecha, String strFormat) {
        try {
            DateFormat df = new SimpleDateFormat(strFormat);
            df.setLenient(false);
            df.parse(strFecha);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    private String obtenerExtensionArchivo() {
        String name = this.ibCargaArchivoDto.getFileName();
        try {
            return name.substring(name.lastIndexOf(".") + 1).toLowerCase();
        } catch (Exception e) {
            return "";
        }
    }

    protected String obtenerValorCelda(Cell celda) {
        if (celda != null) {
            // Dependiendo del formato de la celda el valor se debe mostrar como String, Fecha, boolean, entero...
            switch (celda.getCellType()) {
                case Cell.CELL_TYPE_NUMERIC:
                    if (DateUtil.isCellDateFormatted(celda)) {
                        return celda.getDateCellValue().toString();
                    } else {
                        double number = celda.getNumericCellValue();
                        //se agrega (long) para extraer solo la parte entera
                        long iPart = (long) number;
                        double fPart = number - iPart;
                        //SE HACE ESTA LOGICA YA QUE EN EL ARCHIVO NO SE PUEDEN CARGAR
                        //LOS MONTOS CON PUNTOS, DE ESTA MANERA SE PUEDE REALIZAR LA VALIDACION
                        //EN EL CASO QUE EL CLIENTE LO HAGA Y EVITAR LA CARGA        
                        if (fPart > 0) {
                            return String.valueOf(iPart) + "," + String.valueOf(fPart);
                        } else {
                            return String.valueOf(iPart);
                        }
                    }
                case Cell.CELL_TYPE_STRING:
                    return celda.getStringCellValue();
                case Cell.CELL_TYPE_BLANK:
                    return "";
                default:
                    return "Tipo de Dato de Celda Excel Invalido";
            }
        } else {
            return "";
        }
    }

    //Mensaje para cuando exita un error de linea en el validator
    public void errorLinea(int longitud, String mensaje) throws Exception {
        String err = mapErrorres.get(mensaje);
        String replaceAll = err.replaceAll("<posee>", String.valueOf(linea.trim().length()));
        throw new Exception(replaceAll);
    }
    
}
