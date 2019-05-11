/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.enumerated;

/**
 *
 * @author luis.perez
 */
public enum EstatusPlantillasEmail {
    /**
     *
     */
    CODIGO_EMPRESA_PLANTILLA(1, "1"),
    CLAVE_OPERACIONES_ESPECIALES_TEXT(9,"9"),
    AFILIACION_EMPRESAS_TEXT(15, "15"),
    REGISTRO_USUARIO_TEXT(16, "16"), 
    BLOQUEO_USUARIO_TEXT(17, "17"),
    COMPLETAR_PERFIL_USUARIO_TEXT(21, "21"),
    NIVELES_APROBACION_SERVICIO(24, "24"),
    REGISTRO_EMPRESAUSUARIO_TEXT(27, "27"),
    NOMINA_PENDIENTE_AUTORIZAR(33, "33");
    
    private int id;
    private String descripcion;
    
    private EstatusPlantillasEmail(int id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
