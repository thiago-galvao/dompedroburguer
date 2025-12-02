package com.dompedroburguer.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dompedroburguer.model.FabricaConexoes;
import com.dompedroburguer.model.FormasPagamento;

public class JDBCFormasPagamentoDAO implements FormasPagamentoDAO{

    private FabricaConexoes fabrica;

    
    public JDBCFormasPagamentoDAO(FabricaConexoes fabrica){
        this.fabrica = fabrica;
    }

    @Override
    public List<FormasPagamento> listar() {
        String sql = "SELECT * FROM pi_tipo_pagamento";

        ArrayList<FormasPagamento> lista = new ArrayList<>();
        lista.clear();

        try(Connection con = fabrica.getConnection();
            PreparedStatement pstm = con.prepareCall(sql)){
                ResultSet result = pstm.executeQuery();
                while(result.next()){
                    int id = result.getInt("id");
                    String descricao = result.getString("descricao");
                    FormasPagamento pagamento = new FormasPagamento(id, descricao);
                    lista.add(pagamento);
                }
            return lista;
            } catch (SQLException e){
                e.getMessage();
            }
            return null;
    }
}
