package com.dompedroburguer.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import com.dompedroburguer.model.Checkout;
import com.dompedroburguer.model.CheckoutCardapio;
import com.dompedroburguer.model.FabricaConexoes;
import com.dompedroburguer.utils.DBUtils;

public class JDBCCheckoutDAO implements CheckoutDAO{

    private FabricaConexoes fabrica;

    public JDBCCheckoutDAO(FabricaConexoes fabrica){
        this.fabrica = fabrica;
    }

    @Override
    public Checkout inserir(Checkout checkout, List<CheckoutCardapio> checkCad) {
        String sql = "INSERT INTO pi_checkout (id_tipo_pagamento, id_cliente, valor_total, valor_final, entrega_balcao, data_hora_compra, observacoes, endereco_entrega, status, cupom) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try(Connection con = fabrica.getConnection())
            { 
                PreparedStatement pstm = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                pstm.setInt(1, checkout.getfPagamento().getId());
                pstm.setInt(2, checkout.getCliente().getId());
                pstm.setDouble(3, checkout.getValorTotal());
                pstm.setDouble(4, checkout.getValorFinal());
                pstm.setBoolean(5, checkout.isEntregaBalcao());
                pstm.setObject(6, checkout.getDataHoraCompra());
                pstm.setString(7, checkout.getObs());
                pstm.setString(8, checkout.getCliente().getEndereco());
                pstm.setString(9, checkout.getStatus() != null ? checkout.getStatus() : "PENDENTE");
                pstm.setString(10, checkout.getCupom());
                
                int rows = pstm.executeUpdate();
                if(rows == 1){
                    int id = DBUtils.getLastId(pstm);
                    checkout.setId(id);

                    String sql2 = "INSERT INTO pi_checkout_cardapio (id_checkout,id_cardapio, observacao, quantidade, valor) VALUES(?,?,?,?,?)";
                    PreparedStatement pstmt2 = con.prepareStatement(sql2);

                    for(CheckoutCardapio checkCardapio : checkCad){
                        pstmt2.setInt(1, id);
                        pstmt2.setInt(2, checkCardapio.getProduto().getId());
                        pstmt2.setString(3, checkCardapio.getObs());
                        pstmt2.setInt(4, checkCardapio.getQtd());
                        pstmt2.setDouble(5, checkCardapio.getValor());
                        pstmt2.executeUpdate();
                    }
                    pstmt2.close();
                    return checkout;
                }else{
                }
            }catch(SQLException e){
                e.printStackTrace();
            }
            return checkout;
        }

        @Override
        public Checkout buscar(int id){
            String sql = "SELECT * FROM pi_checkout WHERE id = ?";
            Checkout check = null;

            try (Connection con = fabrica.getConnection();
                PreparedStatement pstm = con.prepareStatement(sql)) {
                    pstm.setInt(1, id);
                    ResultSet rs = pstm.executeQuery();

                    check = new Checkout();
                    check.setId(rs.getInt("id"));
                    check.setDataHoraCompra(rs.getObject("data_hora_compra", LocalDateTime.class));
                } catch (SQLException e) {
                    e.getMessage();
                }
            return check;
        }
}
