import { reactive } from 'vue'

export const config = reactive({
    baseUrl: localStorage.getItem('playlist_baseUrl') || 'http://localhost:8080',
    username: localStorage.getItem('playlist_username') || 'user',
    password: localStorage.getItem('playlist_password') || 'user123'
})

export function salvarConfig(){
    localStorage.setItem('playlist_baseUrl', config.baseUrl)
    localStorage.setItem('playlis_username', config.username)
    localStorage.setItem('playlist_password', config.password)
}

function authHeader(){
    const token = btoa(`${config.username}:${config.password}`)
    return { Authorization:`Basic ${token}` }
}

async function request(path, options = {}) {
    const url = `${config.baseUrl.replace(/\/$/, '')}${path}`
    const res = await fetch(url,{
        ...options,
        headers: {
            'Content-Type': 'application/json',
            ...authHeader(),
            ...(options.headers || {})
    }
    })
    const texto = await res.text()
    let corpo = null
    if(texto){
        try {corpo = JSON.parse(texto)} catch { corpo = texto}
    }

    if(!res.ok){
        const mensagem = (corpo && corpo.mensagem) ? corpo.mensagem : `Erro ${res.status} ${res.statusText}`
        const erro = new Error(mensagem)
        erro.status = res.status
        throw erro
    }

    return corpo    
}

export const PlaylistApi = {
    listar: () => request('/lists'),
    buscar: (nome) => request(`/lists/${encodeURIComponent(nome)}`),
    criar: (playlist) => request('/lists', { method: 'POST', body: JSON.stringify(playlist) }),
    remover: (nome) => request(`/lists/${encodeURIComponent(nome)}`, { method: 'DELETE' })

}


// Config é um objeto reativo que guarda URL da API e o login.
// PlaylistApi são as quatro funções que batem com o Endpoints da API.
