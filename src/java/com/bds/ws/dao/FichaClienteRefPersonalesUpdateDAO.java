/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dao;

import com.bds.ws.dto.UtilDTO;

/**
 *
 * @author audra.zapata
 */
public interface FichaClienteRefPersonalesUpdateDAO {
    
    public UtilDTO actualizarRefPersonales(String iCodigoCliente, String codTipoIdentif, String nroIdentif, String nombreIn, String primerApellido, String segundoApellido, String codReferenciaPersonal, String telefonoIn, String telefono2In);
}
