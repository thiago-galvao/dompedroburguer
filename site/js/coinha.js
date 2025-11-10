document.addEventListener('DOMContentLoaded', () => {

    const pedidosContainer = document.getElementById('pedidos-container');

    /**
     * Busca os pedidos no localStorage e os exibe na tela.
     */
    function renderizarPedidos() {
        const pedidos = JSON.parse(localStorage.getItem('pedidos')) || [];
        pedidosContainer.innerHTML = ''; // Limpa a tela para não duplicar

        if (pedidos.length === 0) {
            pedidosContainer.innerHTML = '<p>Nenhum pedido pendente.</p>';
            return;
        }

        pedidos.forEach(pedido => {
            const card = document.createElement('div');
            card.className = 'pedido-card';

            // Cria a lista de itens do pedido
            let itensHtml = '<ul>';
            pedido.itens.forEach(item => {
                itensHtml += `<li>${item.nome}</li>`;
            });
            itensHtml += '</ul>';

            card.innerHTML = `
                <h3>Pedido #${pedido.id}</h3>
                <p><strong>Cliente:</strong> ${pedido.cliente}</p>
                <strong>Itens:</strong>
                ${itensHtml}
                <p class="info-entrega">${pedido.tipoEntrega === 'entrega' ? `ENTREGA: ${pedido.endereco}` : 'RETIRADA NO BALCÃO'}</p>
                <button class="btn-finalizar" data-id="${pedido.id}">Marcar como Pronto</button>
            `;
            
            pedidosContainer.appendChild(card);
        });
    }

    /**
     * Remove um pedido da lista de pendentes.
     * @param {number} pedidoId - O ID do pedido a ser removido.
     */
    function finalizarPedido(pedidoId) {
        let pedidos = JSON.parse(localStorage.getItem('pedidos')) || [];
        // Filtra a lista, mantendo apenas os pedidos que NÃO têm o ID do finalizado
        pedidos = pedidos.filter(p => p.id !== pedidoId);
        // Salva a nova lista (sem o pedido finalizado) de volta no localStorage
        localStorage.setItem('pedidos', JSON.stringify(pedidos));
        // Re-renderiza a tela para que o pedido suma
        renderizarPedidos();
    }

    // Adiciona um "ouvinte" de cliques no container.
    // Se o clique for em um botão de finalizar, chama a função.
    pedidosContainer.addEventListener('click', (event) => {
        if (event.target.classList.contains('btn-finalizar')) {
            const pedidoId = parseInt(event.target.getAttribute('data-id'));
            finalizarPedido(pedidoId);
        }
    });

    // --- A MÁGICA EM TEMPO REAL ---
    // A cada 2 segundos (2000 milissegundos), a função renderizarPedidos é chamada.
    // Isso faz com que a tela da cozinha se atualize automaticamente.
    setInterval(renderizarPedidos, 2000);

    // Renderiza os pedidos uma vez assim que a página carrega
    renderizarPedidos();
});