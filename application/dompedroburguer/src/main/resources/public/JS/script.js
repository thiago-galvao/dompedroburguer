
let produtoId = 0;

function alternarCamposEndereco() {
    const tipoServico = document.querySelector('input[name="tipoServico"]:checked').value;
    const camposEndereco = document.getElementById('camposEndereco');
    const enderecoInput = document.getElementById('enderecoEntrega');
    if (tipoServico === 'true') { // Entrega
        camposEndereco.style.display = 'flex'; 
        enderecoInput.setAttribute('required', 'required');
        preencherDadosCliente(); 
        
    } else { // Retirada
        camposEndereco.style.display = 'none';
        enderecoInput.removeAttribute('required');
        document.getElementById('complemento').value = '';
    }
}


function adicionarProduto() {
    adicionarProdutoComQtd(1);
}

function adicionarProdutoComQtd(quantidade = 1) {
    // Pega o template de opções (string contendo <option>...</option>)
    const opcoesHtml = document.getElementById('opcoesProdutosTemplate').value;

    // Cria o container do item (linha) com select, quantidade e valor
    const container = document.createElement('div');
    container.className = 'product-item row align-items-center';
    const id = produtoId;
    container.id = `item-${id}`;

    container.innerHTML = `
        <div class="col-md-6 mb-2">
            <select class="form-select nome-produto" id="produtoId-${id}" name="itens[${id}].produtoId" required onchange="preencherValorProduto(this)">
                ${opcoesHtml}
            </select>
        </div>
        <div class="col-md-2 mb-2">
            <input type="number" class="form-control quantidade-produto" id="quantidade-${id}" name="itens[${id}].quantidade" value="${quantidade}" min="1" onchange="calcularValorTotal()">
        </div>
        <div class="col-md-2 mb-2">
            <div class="input-group">
                <span class="input-group-text">R$</span>
                <input type="text" class="form-control valor-unitario-produto" id="valorUnitario-${id}" name="itens[${id}].valorUnitario" readonly value="0.00">
            </div>
            <input type="hidden" id="nomeProduto-${id}" name="itens[${id}].nome" value="">
        </div>
        <div class="col-md-2 mb-2 d-flex justify-content-end">
            <button type="button" class="btn btn-sm btn-danger" onclick="removerProduto('item-${id}')">Remover</button>
        </div>
    `;

    // Insere antes do resumo financeiro (procura o botão de adicionar)
    const addButton = document.querySelector('button[onclick="adicionarProdutoFromSelect()"]');
    if (addButton) {
        addButton.closest('.mb-3').insertAdjacentElement('afterend', container);
    } else {
        // fallback: adiciona ao final do formulário antes do resumo
        const orderForm = document.getElementById('orderForm');
        orderForm.insertBefore(container, document.getElementById('valorTotal')?.closest('.row') || null);
    }

    // Se houver somente a opção selecionada no template, seleciona-a automaticamente
    const selectEl = document.getElementById(`produtoId-${id}`);
    if (selectEl && selectEl.options.length === 2) { // primeira é placeholder
        selectEl.selectedIndex = 1;
    }

    // Atualiza os valores iniciais para este item
    if (selectEl) preencherValorProduto(selectEl);

    produtoId++;
}

function removerProduto(id) {
    const element = document.getElementById(id);
    if (element) {
        element.remove();
        calcularValorTotal();
    }
}

function calcularValorTotal() {
    let total = 0;
    const quantidades = document.querySelectorAll('.quantidade-produto');
    const valores = document.querySelectorAll('.valor-unitario-produto');

    for (let i = 0; i < quantidades.length; i++) {
        const qtd = parseFloat(quantidades[i].value) || 0;
        const valor = parseFloat(valores[i].value) || 0;
        total += qtd * valor;
    }

    document.getElementById('valorTotal').value = total.toFixed(2);
    calcularValorFinal();
}

function calcularValorFinal() {
    const valorTotal = parseFloat(document.getElementById('valorTotal').value) || 0;
    const cupomDesconto = parseFloat(document.getElementById('cupomDesconto').value) || 0;

    let valorFinal = valorTotal - cupomDesconto;
    if (valorFinal < 0) valorFinal = 0;

    document.getElementById('valorFinal').value = valorFinal.toFixed(2);
    document.getElementById('valorFinalDisplay').textContent = valorFinal.toFixed(2);
}

document.addEventListener('DOMContentLoaded', () => {
    alternarCamposEndereco();
    
    const now = new Date();
    const year = now.getFullYear();
    const month = String(now.getMonth() + 1).padStart(2, '0');
    const day = String(now.getDate()).padStart(2, '0');
    const hours = String(now.getHours()).padStart(2, '0');
    const minutes = String(now.getMinutes()).padStart(2, '0');
    document.getElementById('dataHoraPedido').value = `${year}-${month}-${day}T${hours}:${minutes}`;
});

document.getElementById('orderForm').addEventListener('submit', function(event) {
    event.preventDefault();
    alert('Formulário Submetido! (Verifique o console para os dados)');
    
    const dadosPedido = {
        dataHora: document.getElementById('dataHoraPedido').value,
    };
    
    console.log(dadosPedido);
});

function preencherDadosCliente() {
    const select = document.getElementById('nomeCliente');
    const selectedOption = select.options[select.selectedIndex];
    const clienteId = select.value;
    if (clienteId === "") {
        document.getElementById('telefone').value = '';
        document.getElementById('enderecoEntrega').value = '';
        document.getElementById('complemento').value = '';
        return;
    }
    
    const telefone = selectedOption.getAttribute('data-telefone');
    const endereco = selectedOption.getAttribute('data-endereco');
    const complemento = selectedOption.getAttribute('data-complemento');
    
    document.getElementById('telefone').value = telefone;
    document.getElementById('enderecoEntrega').value = endereco;
    document.getElementById('complemento').value = complemento;
    alternarCamposEndereco(); 
}

function preencherValorProduto(selectElement) {
    // 1. Pega a opção selecionada
    const selectedOption = selectElement.options[selectElement.selectedIndex];
    
    // 2. Resgata o valorUnitario e o nome do produto dos atributos data-*
    const valorUnitario = selectedOption.getAttribute('data-valor');
    const nomeProduto = selectedOption.getAttribute('data-nome');
    
    // 3. Encontra o ID único deste bloco de produto (Ex: 'produtoId-1' -> '1')
    const idSufixo = selectElement.id.split('-')[1];
    
    // 4. Preenche o input do Valor Unitário
    const inputValor = document.getElementById(`valorUnitario-${idSufixo}`);
    inputValor.value = valorUnitario;
    
    // 5. Preenche o campo oculto do nome do produto (Para fins de registro/BD)
    const inputNomeHidden = document.getElementById(`nomeProduto-${idSufixo}`);
    inputNomeHidden.value = nomeProduto;
    
    // 6. Recalcula o total (incluindo o novo valor)
    calcularValorTotal();
}