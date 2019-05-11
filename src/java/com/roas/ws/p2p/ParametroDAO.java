package com.roas.ws.p2p;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ParametroDAO {

    private Statement stmt;
    private ResultSet rs;
    private PreparedStatement pstmt;

    public ParametroDAO() {
        this.rs = null;
        this.stmt = null;
        this.pstmt = null;
    }

    public CanalDTO getDatosCanal(String codCanal, Connection conexion) throws SQLException {
        CanalDTO dto = null;
        try {
            String sql = "SELECT COD_CANAL,USUARIO_WS,CLV_WS FROM P2P_CANALES_BANCO WHERE COD_CANAL=?";
            pstmt = conexion.prepareStatement(sql);
            pstmt.setString(1, codCanal);;
            rs = pstmt.executeQuery();
            if (rs.next()) {
                //LOGGER.info("check value de canal encontrado");
                dto = new CanalDTO();
                dto.setCodCanal(rs.getString("COD_CANAL"));
                dto.setUsuarioCanal(rs.getString("USUARIO_WS"));
                dto.setClvCanal(rs.getString("CLV_WS"));
            } else {
                dto = null;
            }
        } catch (Exception e) {
            //LOGGER.info("Error al consultar si hay o no checkvalue del canal: " + e);
            throw new SQLException("Error al consultar datos de uso del canal " + e);
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e2) {
                //LOGGER.info("error al cerrar stm. pstm en parametro dao"+e2);
            }
        }
        return dto;
    }
}
