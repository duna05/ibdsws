/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.services;

import com.bds.ws.dao.TranferenciasYPagosDAO;
import com.bds.ws.dto.TransaccionDTO;
import java.math.BigDecimal;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * Clase donde se encuentran los servicios para transferencias y pagos
 * @author cesar.mujica
 */
@WebService(serviceName = "TransferenciasYPagosWS")
public class TransferenciasYPagosWS {

    @EJB
    private TranferenciasYPagosDAO ejbRef;// Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Web Service Operation")

    /**
     * Metodo que se encarga de realizar la transferencia entre dos cuentas del mismo banco
     * @param cuentaOrigen Numero (de 20 digitos) de la cuenta origen de los fondos.
     * @param cuentaDestino Numero de cuenta, de 20 digitos, destino de los fondos.
     * @param monto Monto en Bs. de la transferencia.
     * @param descripcion Descripcion, de la transferencia, suministrada por el cliente que la realiza.
     * @param canal Codigo (extendido) del canal desde el cual es llamado el procedimiento.
     * @return TransaccionDTO objeto de respuesta con el codigo del resultado de la transaccion
     */
    @WebMethod(operationName = "transferenciaMismoBanco")
    public TransaccionDTO transferenciaMismoBanco(@WebParam(name = "cuentaOrigen") String cuentaOrigen, @WebParam(name = "cuentaDestino") String cuentaDestino, @WebParam(name = "monto") BigDecimal monto, @WebParam(name = "descripcion") String descripcion, @WebParam(name = "canal") String canal) {
        return ejbRef.transferenciaMismoBanco(cuentaOrigen, cuentaDestino, monto, descripcion, canal);
    }

    /**
     * Metodo que se encarga de realizar la transferencia entre dos cuentas propias del mismo banco
     * @param cuentaOrigen Numero (de 20 digitos) de la cuenta origen de los fondos.
     * @param cuentaDestino Numero de cuenta, de 20 digitos, destino de los fondos.
     * @param monto Monto en Bs. de la transferencia.
     * @param descripcion Descripcion, de la transferencia, suministrada por el cliente que la realiza.
     * @param canal Codigo (extendido) del canal desde el cual es llamado el procedimiento.
     * @return TransaccionDTO objeto de respuesta con el codigo del resultado de la transaccion
     */
    @WebMethod(operationName = "transferenciaPropiasMismoBanco")
    public TransaccionDTO transferenciaPropiasMismoBanco(@WebParam(name = "cuentaOrigen") String cuentaOrigen, @WebParam(name = "cuentaDestino") String cuentaDestino, @WebParam(name = "monto") BigDecimal monto, @WebParam(name = "descripcion") String descripcion, @WebParam(name = "canal") String canal) {
        return ejbRef.transferenciaPropiasMismoBanco(cuentaOrigen, cuentaDestino, monto, descripcion, canal);
    }

    /**
     * Metodo que se encarga de realizar la transferencia entre dos cuentas hacia otro banco
     * @param cuentaOrigen Numero (de 20 digitos) de la cuenta origen de los fondos.
     * @param cuentaBeneficiario Numero de cuenta, de 20 digitos, destino de los fondos.
     * @param idBeneficiario Identificacion (numero de cedula, RIF, etc.) del beneficiario del pago.
     * @param nombreBeneficiario Nombre y apellido del beneficiario de la transferencia.
     * @param monto Monto en Bs. de la transferencia.
     * @param descripcion Descripcion, de la transferencia, suministrada por el cliente que la realiza.
     * @param canal Codigo (extendido) del canal desde el cual es llamado el procedimiento.
     * @return TransaccionDTO objeto de respuesta con el codigo del resultado de la transaccion
     */
    @WebMethod(operationName = "transferenciaOtroBanco")
    public TransaccionDTO transferenciaOtroBanco(@WebParam(name = "cuentaOrigen") String cuentaOrigen, @WebParam(name = "cuentaBeneficiario") String cuentaBeneficiario, @WebParam(name = "idBeneficiario") String idBeneficiario, @WebParam(name = "nombreBeneficiario") String nombreBeneficiario, @WebParam(name = "monto") BigDecimal monto, @WebParam(name = "descripcion") String descripcion, @WebParam(name = "canal") String canal) {
        return ejbRef.transferenciaOtroBanco(cuentaOrigen, cuentaBeneficiario, idBeneficiario, nombreBeneficiario, monto, descripcion, canal);
    }

    /**
     * Metodo que se encarga de realizar el pago de un TDC del mismo banco
     * @param cuentaOrigen Numero (de 20 digitos) de la cuenta origen de los fondos.
     * @param tdcBeneficiario Numero de la tarjeta de credito que se desea pagar.
     * @param monto Monto en Bs. de la transferencia.
     * @param descripcion Descripcion, de la transferencia, suministrada por el cliente que la realiza.
     * @param canal Codigo (extendido) del canal desde el cual es llamado el procedimiento.
     * @return TransaccionDTO objeto de respuesta con el codigo del resultado de la transaccion
     */
    @WebMethod(operationName = "pagoTDCMismoBanco")
    public TransaccionDTO pagoTDCMismoBanco(@WebParam(name = "cuentaOrigen") String cuentaOrigen, @WebParam(name = "tdcBeneficiario") String tdcBeneficiario, @WebParam(name = "monto") BigDecimal monto, @WebParam(name = "descripcion") String descripcion, @WebParam(name = "canal") String canal) {
        return ejbRef.pagoTDCMismoBanco(cuentaOrigen, tdcBeneficiario, monto, descripcion, canal);
    }

    /**
     * Metodo que se encarga de realizar el pago de un TDC Propia del mismo banco
     * @param cuentaOrigen Numero (de 20 digitos) de la cuenta origen de los fondos.
     * @param tdcBeneficiario Numero de la tarjeta de credito que se desea pagar.
     * @param monto Monto en Bs. de la transferencia.
     * @param descripcion Descripcion, de la transferencia, suministrada por el cliente que la realiza.
     * @param canal Codigo (extendido) del canal desde el cual es llamado el procedimiento.
     * @return TransaccionDTO objeto de respuesta con el codigo del resultado de la transaccion
     */
    @WebMethod(operationName = "pagoTDCPropiaMismoBanco")
    public TransaccionDTO pagoTDCPropiaMismoBanco(@WebParam(name = "cuentaOrigen") String cuentaOrigen, @WebParam(name = "tdcBeneficiario") String tdcBeneficiario, @WebParam(name = "monto") BigDecimal monto, @WebParam(name = "descripcion") String descripcion, @WebParam(name = "canal") String canal) {
        return ejbRef.pagoTDCPropiaMismoBanco(cuentaOrigen, tdcBeneficiario, monto, descripcion, canal);
    }

    /**
     * Metodo que se encarga de realizar la transferencia entre dos cuentas hacia otro banco
     * @param cuentaOrigen Numero (de 20 digitos) de la cuenta origen de los fondos.
     * @param tdcBeneficiario Numero de cuenta, de 20 digitos, destino de los fondos.
     * @param idBeneficiario Identificacion (numero de cedula, RIF, etc.) del beneficiario del pago.
     * @param nombreBeneficiario Nombre y apellido del beneficiario de la transferencia.
     * @param monto Monto en Bs. de la transferencia.
     * @param descripcion Descripcion, de la transferencia, suministrada por el cliente que la realiza.
     * @param canal Codigo (extendido) del canal desde el cual es llamado el procedimiento.
     * @return TransaccionDTO objeto de respuesta con el codigo del resultado de la transaccion
     */
    @WebMethod(operationName = "pagoTDCOtroBanco")
    public TransaccionDTO pagoTDCOtroBanco(@WebParam(name = "cuentaOrigen") String cuentaOrigen, @WebParam(name = "tdcBeneficiario") String tdcBeneficiario, @WebParam(name = "idBeneficiario") String idBeneficiario, @WebParam(name = "nombreBeneficiario") String nombreBeneficiario, @WebParam(name = "monto") BigDecimal monto, @WebParam(name = "descripcion") String descripcion, @WebParam(name = "canal") String canal) {
        return ejbRef.pagoTDCOtroBanco(cuentaOrigen, tdcBeneficiario, idBeneficiario, nombreBeneficiario, monto, descripcion, canal);
    }

}
