<script setup>
import { reactive, ref } from 'vue'
import { PlaylistApi } from '../api.js'

const emit = defineEmits(['criada'])

function musicaVazia() {
  return { titulo: '', artista: '', album: '', ano: '', genero: '' }
}

const form = reactive({
  nome: '',
  descricao: '',
  musicas: [musicaVazia()]
})

const enviando = ref(false)
const feedback = reactive({ tipo: '', texto: '' })

function adicionarMusica() {
  form.musicas.push(musicaVazia())
}

function removerMusica(index) {
  form.musicas.splice(index, 1)
}

function limparForm() {
  form.nome = ''
  form.descricao = ''
  form.musicas = [musicaVazia()]
}

async function criar() {
  feedback.tipo = ''
  feedback.texto = ''
  enviando.value = true
  try {
    const payload = {
      nome: form.nome,
      descricao: form.descricao,
      musicas: form.musicas
        .filter(m => m.titulo.trim() !== '')
        .map(m => ({ ...m, ano: m.ano ? Number(m.ano) : null }))
    }
    const criada = await PlaylistApi.criar(payload)
    feedback.tipo = 'ok'
    feedback.texto = `Lista "${criada.nome}" criada com sucesso (201 Created).`
    limparForm()
    emit('criada')
  } catch (e) {
    feedback.tipo = 'err'
    feedback.texto = `Falha ao criar (status ${e.status ?? '?'}): ${e.message}`
  } finally {
    enviando.value = false
  }
}
</script>

<template>
  <div class="card">
    <h2>Nova playlist</h2>

    <label for="nome">Nome *</label>
    <input id="nome" v-model="form.nome" placeholder="Ex: Lista 1" />

    <label for="descricao">Descrição</label>
    <input id="descricao" v-model="form.descricao" placeholder="Ex: Lista de músicas do Spotify" />

    <label>Músicas</label>
    <div v-for="(musica, index) in form.musicas" :key="index" class="musica-row">
      <input v-model="musica.titulo" placeholder="Título" />
      <input v-model="musica.artista" placeholder="Artista" />
      <input v-model="musica.album" placeholder="Álbum" />
      <input v-model="musica.ano" placeholder="Ano" />
      <input v-model="musica.genero" placeholder="Gênero" />
      <button type="button" class="secondary small" @click="removerMusica(index)">Remover</button>
    </div>
    <button type="button" class="secondary small" @click="adicionarMusica">+ Adicionar música</button>

    <div>
      <button :disabled="enviando || !form.nome" @click="criar">
        {{ enviando ? 'Enviando...' : 'Criar playlist (POST /lists)' }}
      </button>
    </div>

    <div v-if="feedback.texto" :class="['feedback', feedback.tipo]">{{ feedback.texto }}</div>
  </div>
</template>