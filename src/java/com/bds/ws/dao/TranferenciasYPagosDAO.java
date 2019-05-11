/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bds.ws.dao;

import com.bds.ws.dto.TransaccionDTO;
import java.math.BigDecimal;

/**
 * Interfaz DAO
 * @author cesar.mujica
 */
public interface TranferenciasYPagosDAO {

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
    public TransaccionDTO transferenciaMismoBanco(String cuentaOrigen, String cuentaDestino, BigDecimal monto, String descripcion, String canal);

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
    public TransaccionDTO transferenciaPropiasMismoBanco(String cuentaOrigen, String cuentaDestino, BigDecimal monto, String descripcion, String canal);

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
    public TransaccionDTO transferenciaOtroBanco(String cuentaOrigen, String cuentaBeneficiario, String idBeneficiario, String nombreBeneficiario, BigDecimal monto, String descripcion, String canal);

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
    public TransaccionDTO pagoTDCMismoBanco(String cuentaOrigen, String tdcBeneficiario, BigDecimal monto, String descripcion, String canal);

    /**
     * Metodo que se encarga de realizar el pago de un TDC Propia del mismo
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
    public TransaccionDTO pagoTDCPropiaMismoBanco(String cuentaOrigen, String tdcBeneficiario, BigDecimal monto, String descripcion, String canal);

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
    public TransaccionDTO pagoTDCOtroBanco(String cuentaOrigen, String tdcBeneficiario, String idBeneficiario, String nombreBeneficiario, BigDecimal monto, String descripcion, String canal);

}
