package com.dompedroburguer.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.dompedroburguer.model.Checkout;
import com.dompedroburguer.model.FabricaConexoes;
import com.dompedroburguer.model.Produto;
import com.dompedroburguer.utils.DBUtils;
import com.github.hugoperlin.results.Resultado;

public class JDBCCheckoutDAO implements CheckoutDAO{

    private FabricaConexoes fabrica;

    public JDBCCheckoutDAO(FabricaConexoes fabrica){
        this.fabrica = fabrica;
    }

    @Override
    public boolean inserir(Checkout checkout) {
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

                    for(Produto produto:checkout.getProdutos()){
                        pstmt2.setInt(1, id);
                        pstmt2.setInt(2, produto.getId());
                        pstmt2.setString(3, checkout.getObs());
                        pstmt2.executeUpdate();
                    }
                    pstmt2.close();

                
                    return true;
                }else{
                    return false;
                }
            }catch(SQLException e){
                e.printStackTrace();
            }
            return false;
        }
        /*id pagamento.
id cliente.
valor total
valor final
entrega ou balcao*/ 

// - dataHora (LocalDateTime)
        // - tipoServico (boolean)
        // - cliente (Cliente)
        // - produtos (List<Produto>)
        // - obs (String)
        // Forma de pagamento.
        // Você pode salvar no repositório
}
