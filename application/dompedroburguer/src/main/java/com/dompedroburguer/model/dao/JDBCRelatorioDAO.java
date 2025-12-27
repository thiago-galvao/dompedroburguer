package com.dompedroburguer.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.dompedroburguer.model.Cliente;
import com.dompedroburguer.model.FabricaConexoes;

public class JDBCRelatorioDAO implements RelatorioDAO {

    private FabricaConexoes fabrica;

    public JDBCRelatorioDAO(FabricaConexoes fabrica){
        this.fabrica = fabrica;
    }

    @Override
    public List<Cliente> puxarRelatorioCliente(Date inicio, Date fim) {
        String sql = "SELECT PC.nome, PC.telefone, PC.endereco, PCK.valor_final FROM pi_cliente PC, pi_checkout PCK WHERE PCK.id_cliente=PC.id AND PCK.data_hora_compra BETWEEN ? AND ?";
        List<Cliente> clientes = new ArrayList<>();
        clientes.clear();

        Cliente cliente = null;

        try(Connection con = fabrica.getConnection();
            PreparedStatement pstm = con.prepareCall(sql)){
                pstm.setObject(1, inicio);
                pstm.setObject(2, fim);
                try(ResultSet rs = pstm.executeQuery()){
                    while (rs.next()){
                        cliente = new Cliente();
                        cliente.setNome(rs.getString("nome"));
                        cliente.setTelefone(rs.getString("telefone"));
                        cliente.setEndereco(rs.getString("endereco"));
                        cliente.setValor(rs.getDouble("valor_final"));
                        clientes.add(cliente);
                    }
                }
            } catch (SQLException e) {
                e.getMessage();
            }     
        return clientes;
    }
}
