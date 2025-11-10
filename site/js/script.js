// Espera todo o conteúdo da página carregar antes de executar o código
document.addEventListener('DOMContentLoaded', () => {

    // --- DADOS DOS PRODUTOS ---
    // Uma lista (array) com os nossos hambúrgueres.
    // Cada hambúrguer é um objeto com nome e preço.
    const produtos = [
        { id: 1, nome: 'X-Salada', preco: 25.50 },
        { id: 2, nome: 'X-Bacon', preco: 28.00 },
        { id: 3, nome: 'X-Egg', preco: 26.50 }
    ];

    // --- VARIÁVEIS GLOBAIS ---
    // Aqui guardamos os itens que o cliente adicionou ao carrinho.
    let carrinho = [];

    // --- REFERÊNCIAS AOS ELEMENTOS HTML ---
    // Pegamos os elementos do HTML para poder manipulá-los.
    const menuContainer = document.getElementById('menu-container');
    const carrinhoItensUl = document.getElementById('carrinho-itens');
    const carrinhoTotalSpan = document.getElementById('carrinho-total');
    const btnCheckout = document.getElementById('btn-checkout');
    const checkoutContainer = document.getElementById('checkout-container');
    const opcaoEntregaSelect = document.getElementById('opcao-entrega');
    const enderecoGroup = document.getElementById('endereco-group');
    const btnConfirmarPedido = document.getElementById('btn-confirmar-pedido');

    // --- FUNÇÕES ---

    /**
     * Função para criar e exibir os produtos do cardápio na tela.
     */
    function renderizarMenu() {
        // Para cada produto na nossa lista...
        produtos.forEach(produto => {
            // ...cria um elemento HTML para ele.
            const produtoDiv = document.createElement('div');
            produtoDiv.className = 'produto';
            produtoDiv.innerHTML = `
                <div class="produto-info">
                    <h3>${produto.nome}</h3>
                    <p class="preco">R$ ${produto.preco.toFixed(2).replace('.', ',')}</p>
                </div>
                <button class="btn-adicionar" data-id="${produto.id}">Adicionar</button>
            `;
            // Adiciona o elemento do produto ao container do menu.
            menuContainer.appendChild(produtoDiv);
        });
    }

    /**
     * Função para adicionar um produto ao carrinho.
     * @param {number} produtoId - O ID do produto a ser adicionado.
     */
    function adicionarAoCarrinho(produtoId) {
        // Encontra o produto na nossa lista de produtos pelo ID.
        const produto = produtos.find(p => p.id === produtoId);
        if (produto) {
            // Adiciona o produto encontrado à nossa lista do carrinho.
            carrinho.push(produto);
            // Atualiza a exibição do carrinho na tela.
            renderizarCarrinho();
        }
    }

    /**
     * Função para atualizar a exibição do carrinho na tela.
     */
    function renderizarCarrinho() {
        // Limpa a lista de itens do carrinho para não duplicar.
        carrinhoItensUl.innerHTML = '';
        let total = 0;

        // Para cada item no carrinho...
        carrinho.forEach(item => {
            // ...cria um elemento <li> para ele.
            const li = document.createElement('li');
            li.textContent = `${item.nome} - R$ ${item.preco.toFixed(2).replace('.', ',')}`;
            carrinhoItensUl.appendChild(li);
            // ...soma o preço dele ao total.
            total += item.preco;
        });

        // Atualiza o texto do total.
        carrinhoTotalSpan.textContent = `R$ ${total.toFixed(2).replace('.', ',')}`;

        // Habilita ou desabilita o botão de finalizar pedido.
        btnCheckout.disabled = carrinho.length === 0;
    }

    // --- LÓGICA DE EVENTOS ---

    // Adiciona um "ouvinte" no container do menu.
    // Se o clique for em um botão "Adicionar", chama a função de adicionar.
    menuContainer.addEventListener('click', (event) => {
        if (event.target.classList.contains('btn-adicionar')) {
            const produtoId = parseInt(event.target.getAttribute('data-id'));
            adicionarAoCarrinho(produtoId);
        }
    });

    // Quando o cliente clica em "Finalizar Pedido".
    btnCheckout.addEventListener('click', () => {
        // Mostra a seção de checkout e esconde o botão antigo.
        checkoutContainer.classList.remove('hidden');
        btnCheckout.classList.add('hidden');
    });

    // Quando a opção de entrega muda (Retirada ou Entrega).
    opcaoEntregaSelect.addEventListener('change', () => {
        if (opcaoEntregaSelect.value === 'entrega') {
            enderecoGroup.classList.remove('hidden');
        } else {
            enderecoGroup.classList.add('hidden');
        }
    });

    // Quando o cliente confirma o pedido.
    // Encontre este trecho no seu script.js (está no final)
// E substitua a função inteira pelo código abaixo

btnConfirmarPedido.addEventListener('click', () => {
    const nome = document.getElementById('nome').value;
    const opcaoEntrega = opcaoEntregaSelect.value;
    const endereco = document.getElementById('endereco').value;

    if (!nome) {
        alert('Por favor, digite seu nome.');
        return;
    }

    if (opcaoEntrega === 'entrega' && !endereco) {
        alert('Por favor, digite seu endereço para entrega.');
        return;
    }

    // --- NOVA LÓGICA DE SALVAMENTO ---

    // 1. Pega os pedidos que já existem no localStorage (se houver)
    // JSON.parse transforma o texto de volta em uma lista
    const pedidosAtuais = JSON.parse(localStorage.getItem('pedidos')) || [];

    // 2. Cria um novo objeto de pedido com todos os detalhes
    const novoPedido = {
        id: Date.now(), // Cria um ID único baseado na data e hora atual
        cliente: nome,
        itens: carrinho,
        total: carrinho.reduce((acc, item) => acc + item.preco, 0), // Calcula o total
        tipoEntrega: opcaoEntrega,
        endereco: opcaoEntrega === 'entrega' ? endereco : 'Retirada no balcão',
        status: 'Pendente' // Adicionamos um status inicial
    };

    // 3. Adiciona o novo pedido à lista de pedidos
    pedidosAtuais.push(novoPedido);

    // 4. Salva a lista atualizada de volta no localStorage
    // JSON.stringify transforma nossa lista em texto, que é como o localStorage armazena
    localStorage.setItem('pedidos', JSON.stringify(pedidosAtuais));
    
    // --- FIM DA NOVA LÓGICA ---

    // Mensagem de sucesso para o cliente
    alert(`Pedido para ${nome} confirmado com sucesso! Obrigado!`);
    
    // Limpa tudo para um novo pedido do cliente
    carrinho = [];
    renderizarCarrinho();
    checkoutContainer.classList.add('hidden');
    btnCheckout.classList.remove('hidden');
    document.getElementById('nome').value = '';
    document.getElementById('endereco').value = '';
});


    // --- INICIALIZAÇÃO ---
    // Chama as funções iniciais para montar a página.
    renderizarMenu();
    renderizarCarrinho();

});