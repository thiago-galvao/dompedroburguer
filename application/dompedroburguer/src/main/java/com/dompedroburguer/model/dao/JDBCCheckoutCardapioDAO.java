package com.dompedroburguer.model.dao;

import java.util.List;

import com.dompedroburguer.model.CheckoutCardapio;
import com.dompedroburguer.model.FabricaConexoes;
import com.dompedroburguer.model.Produto;

public class JDBCCheckoutCardapioDAO implements CheckoutCardapioDAO {
    private FabricaConexoes fabrica;
    private Produto produto;
    private ProdutoDAO daoProd;

    // Busca pelo ID do checkout.
    @Override
    public List<CheckoutCardapio> buscarPorId(int id) {
        // String sql = "SELECT CKC.observacao, CKC.quantidade, CKC.valor, CKC.id_cardapio FROM pi_checkout_cardapio CKC INNER JOIN pi_checkout ON CKC.id_checkout = pi_checkout.id AND CKC.id_checkout = ?";

        // List<CheckoutCardapio> lista = new ArrayList<>();
        // List<Produto> listaProd = new ArrayList<>();

        // lista.clear();
        // listaProd.clear();

        // try(Connection con = fabrica.getConnection();
        //     PreparedStatement pstm = con.prepareStatement(sql)) {

        //         pstm.setInt(1, id);
        //         ResultSet rs = pstm.executeQuery();

        //         while(rs.next()) {
        //             String obs = rs.getString("observacao");
        //             int qtd = rs.getInt("quantidade");
        //             double valor = rs.getFloat("valor");
        //             Long idProduto = rs.getLong("id_cardapio");
        //             produto = daoProd.buscar(idProduto);
        //             listaProd.add(produto);

        //             CheckoutCardapio checkoutCardapio = new CheckoutCardapio(null, listaProd, obs, qtd, valor)
        //         }

        //     } catch (SQLException e) {
        //         e.getMessage();
        //     }
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
