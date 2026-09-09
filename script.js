const API_BASE_URL = 'http://localhost:8080/api';
let mapaGeneros = {};

document.addEventListener('DOMContentLoaded', async () => {
    await carregarGeneros();
    await carregarLivros();

    const form = document.getElementById('formLivro');
    form.addEventListener('submit', cadastrarLivro);
});

async function carregarGeneros() {
    const selectGenero = document.getElementById('genero');

    try {
        const response = await fetch(`${API_BASE_URL}/generos`);
        
        if (!response.ok) throw new Error('Falha ao buscar gêneros');

        const generos = await response.json();
        selectGenero.innerHTML = '<option value="">Selecione um gênero</option>';

        generos.forEach(genero => {
            // Guarda o nome do gênero com chave sendo o ID
            mapaGeneros[genero.id] = genero.nome;

            const option = document.createElement('option');
            option.value = genero.id;
            option.textContent = genero.nome;
            selectGenero.appendChild(option);
        });

    } catch (erro) {
        console.error('Erro ao carregar gêneros:', erro);
        selectGenero.innerHTML = '<option value="">Erro ao carregar gêneros</option>';
    }
}


async function carregarLivros() {
    const containerLista = document.getElementById('listaLivros');

    try {
        const response = await fetch(`${API_BASE_URL}/livros`);

        if (response.status === 204) {
            containerLista.innerHTML = '<p class="mensagem-vazia">Nenhum livro cadastrado ainda.</p>';
            return;
        }

        if (!response.ok) throw new Error('Erro ao buscar lista de livros');

        const livros = await response.json();

        if (livros.length === 0) {
            containerLista.innerHTML = '<p class="mensagem-vazia">Nenhum livro cadastrado ainda.</p>';
            return;
        }

        containerLista.innerHTML = ''; // Limpa a lista

        livros.forEach(livro => {
            const nomeGenero = mapaGeneros[livro.generoId] || `Gênero #${livro.generoId}`;
            
            const card = document.createElement('div');
            card.className = 'item-livro';
            card.innerHTML = `
                <h3>${escapeHTML(livro.titulo)}</h3>
                <p><strong>Autor:</strong> ${escapeHTML(livro.autor)}</p>
                <p><strong>Editora:</strong> ${escapeHTML(livro.editora)}</p>
                <p><strong>Publicação:</strong> ${formatarData(livro.dataPublicacao)} | <strong>Páginas:</strong> ${livro.quantidadePaginas}</p>
                
                <div class="tags">
                    <span class="badge">${escapeHTML(nomeGenero)}</span>
                    <span class="badge">${livro.formato === 'DIGITAL' ? 'E-book' : 'Físico'}</span>
                    ${livro.edicaoEspecial ? '<span class="badge badge-especial">Colecionador</span>' : ''}
                </div>
            `;
            containerLista.appendChild(card);
        });

    } catch (erro) {
        console.error('Erro ao buscar livros:', erro);
        containerLista.innerHTML = '<p class="mensagem-vazia" style="color:red;">Erro ao carregar lista de livros.</p>';
    }
}

async function cadastrarLivro(event) {
    event.preventDefault();

    const titulo = document.getElementById('titulo').value.trim();
    const autor = document.getElementById('autor').value.trim();
    const editora = document.getElementById('editora').value.trim();
    const dataPublicacao = document.getElementById('dataPublicacao').value;
    const paginas = parseInt(document.getElementById('paginas').value, 10);
    const generoId = document.getElementById('genero').value;
    const formato = document.querySelector('input[name="formato"]:checked').value;
    const edicaoEspecial = document.getElementById('possuiEdicaoEspecial').checked;

    if (!titulo || !autor || !editora || !dataPublicacao || !paginas || !generoId) {
        exibirMensagem('Preencha todos os campos obrigatórios!', 'erro');
        return;
    }

    if (paginas <= 0) {
        exibirMensagem('Quantidade de páginas deve ser maior que zero!', 'erro');
        return;
    }

    const novoLivro = {
        titulo: titulo,
        autor: autor,
        editora: editora,
        dataPublicacao: dataPublicacao,
        quantidadePaginas: paginas,
        generoId: parseInt(generoId, 10),
        formato: formato,
        edicaoEspecial: edicaoEspecial
    };

    try {
        const response = await fetch(`${API_BASE_URL}/livros`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(novoLivro)
        });

        if (response.status === 201 || response.status === 200) {
            exibirMensagem('Livro cadastrado com sucesso!', 'sucesso');
            document.getElementById('formLivro').reset();
            // Atualiza a lista de livros imediatamente após o cadastro
            await carregarLivros();
        } else {
            const erroData = await response.json().catch(() => null);
            exibirMensagem(`Erro (${response.status}): ${erroData?.mensagem || 'Erro ao cadastrar.'}`, 'erro');
        }

    } catch (erro) {
        console.error('Erro ao enviar requisição:', erro);
        exibirMensagem('Não foi possível conectar ao servidor.', 'erro');
    }
}

function exibirMensagem(texto, tipo) {
    const divMensagem = document.getElementById('mensagem');
    divMensagem.textContent = texto;
    divMensagem.className = `mensagem ${tipo}`;
}

function formatarData(dataISO) {
    if (!dataISO) return '';
    const partes = dataISO.split('-');
    if (partes.length === 3) {
        return `${partes[2]}/${partes[1]}/${partes[0]}`;
    }
    return dataISO;
}

function escapeHTML(str) {
    return str.replace(/[&<>'"]/g, 
        tag => ({ '&': '&amp;', '<': '&lt;', '>': '&gt;', "'": '&#39;', '"': '&quot;' }[tag] || tag)
    );
}