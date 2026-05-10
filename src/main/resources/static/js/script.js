const URL_BASE = window.location.origin;

function verificarAutenticacao() {
    const token = localStorage.getItem('token');

    if (!token && !window.location.pathname.endsWith('login.html')) {
        window.location.href = 'login.html';
    }
    return token;
}

function login(event) {
    event.preventDefault();

    const email = document.getElementById('email').value.trim();
    const senha = document.getElementById('senha').value.trim();

    fetch(`${URL_BASE}/login`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ email, password: senha })
    })
    .then(resposta => {
        if (resposta.ok) return resposta.json();

        console.log("A API recusou o acesso. Status do Erro: " + resposta.status);
        throw new Error('Credenciais inválidas. Verifique letras maiúsculas!');
    })
    .then(dados => {
        localStorage.setItem('token', dados.token);
        window.location.href = '/';
    })
    .catch(erro => {
        document.getElementById('mensagemErro').innerHTML = erro.message;
    });
}

function logout() {
    localStorage.removeItem('token');
    window.location.href = 'login.html';
}

function carregarJogos() {
    fetch(`${URL_BASE}/jogos`)
        .then(resposta => resposta.json())
        .then(jogos => {
            const container = document.getElementById('containerJogos');
            container.innerHTML = jogos.map(jogo => `
                <div class="card-jogo">
                    <h3>${jogo.nome}</h3>
                    <span class="tipo">${jogo.tipo}</span>
                    <div class="nota">⭐ ${jogo.nota}/10</div>
                    <p class="review-texto">${jogo.review || 'Sem review'}</p>
                    <div class="botoes-acao">
                        <button class="botao-editar" onclick="editarJogo(${jogo.id})">Editar</button>
                        <button class="botao-deletar" onclick="deletarJogo(${jogo.id})">Deletar</button>
                    </div>
                </div>
            `).join('');
        });
}

function salvarJogo(event) {
    event.preventDefault();

    const id = document.getElementById('jogoId').value;
    const jogo = {
        nome: document.getElementById('nomeJogo').value,
        tipo: document.getElementById('tipoJogo').value,
        nota: parseInt(document.getElementById('notaJogo').value),
        review: document.getElementById('reviewJogo').value
    };

    const url = id ? `${URL_BASE}/jogos/${id}` : `${URL_BASE}/jogos`;
    const metodo = id ? 'PUT' : 'POST';

    fetch(url, {
        method: metodo,
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(jogo)
    })
    .then(resposta => resposta.json())
    .then(() => {
        limparFormulario();
        carregarJogos();
    });
}

function editarJogo(id) {
    fetch(`${URL_BASE}/jogos/${id}`)
        .then(resposta => resposta.json())
        .then(jogo => {
            document.getElementById('jogoId').value = jogo.id;
            document.getElementById('nomeJogo').value = jogo.nome;
            document.getElementById('tipoJogo').value = jogo.tipo;
            document.getElementById('notaJogo').value = jogo.nota;
            document.getElementById('reviewJogo').value = jogo.review;
            document.getElementById('botaoSalvar').textContent = 'Atualizar';
        });
}

function deletarJogo(id) {
    if (confirm('Tem certeza que deseja deletar esta review?')) {
        fetch(`${URL_BASE}/jogos/${id}`, { method: 'DELETE' })
            .then(() => carregarJogos());
    }
}

function limparFormulario() {
    document.getElementById('jogoId').value = '';
    document.getElementById('formularioJogo').reset();
    document.getElementById('botaoSalvar').textContent = 'Salvar';
}

document.addEventListener('DOMContentLoaded', () => {
    if (window.location.pathname.endsWith('login.html')) {
        document.getElementById('formularioLogin').addEventListener('submit', login);
    }
    else {
        verificarAutenticacao();

        if (localStorage.getItem('token')) {
            const conteudo = document.getElementById('conteudoPrincipal');
            if(conteudo) {
                conteudo.style.display = 'block';
            }

            carregarJogos();
            document.getElementById('formularioJogo').addEventListener('submit', salvarJogo);
        }
    }
});