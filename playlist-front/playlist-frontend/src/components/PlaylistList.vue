<script setup>
import { ref, onMounted, reactive } from 'vue'
import { PlaylistApi } from '../api.js'

const playlists = ref([])
const carregando = ref(false)
const feedback = reactive({ tipo: '', texto: '' })

async function carregar() {
  carregando.value = true
  feedback.tipo = ''
  feedback.texto = ''
  try {
    playlists.value = await PlaylistApi.listar()
  } catch (e) {
    feedback.tipo = 'err'
    feedback.texto = `Erro ao listar (status ${e.status ?? '?'}): ${e.message}`
  } finally {
    carregando.value = false
  }
}
defineExpose({ carregar }) 
onMounted(carregar)

async function apagar(nome) {
  if (!confirm(`Apagar a lista "${nome}"? Essa ação não pode ser desfeita.`)) return
  feedback.tipo = ''
  feedback.texto = ''
  try {
    await PlaylistApi.remover(nome)
    feedback.tipo = 'ok'
    feedback.texto = `Lista "${nome}" apagada (204 No Content).`
    await carregar()
  } catch (e) {
    if (e.status === 403) {
      feedback.tipo = 'err'
      feedback.texto = `Você não tem permissão para apagar (403 Forbidden). Use o login admin.`
    } else {
      feedback.tipo = 'err'
      feedback.texto = `Erro ao apagar (status ${e.status ?? '?'}): ${e.message}`
    }
  }
}


onMounted(carregar)
</script>

<template>
  <div class="card">
    <h2>Playlists existentes</h2>
    <button class="secondary" :disabled="carregando" @click="carregar">
      {{ carregando ? 'Carregando...' : 'Atualizar lista (GET /lists)' }}
    </button>

    <div v-if="feedback.texto" :class="['feedback', feedback.tipo]">{{ feedback.texto }}</div>

    <p v-if="!carregando && playlists.length === 0" class="muted" style="margin-top:14px;">
      Nenhuma playlist cadastrada ainda.
    </p>

    <div v-for="p in playlists" :key="p.nome" class="playlist-item" style="margin-top:14px;">
      <div class="playlist-item-header">
        <strong>{{ p.nome }}</strong>
        <button class="danger small" @click="apagar(p.nome)">Apagar (DELETE)</button>
      </div>
      <p class="muted">{{ p.descricao }}</p>
      <div>
        <span v-for="(m, i) in p.musicas" :key="i" class="musica-tag">
          {{ m.titulo }} — {{ m.artista }}
        </span>
      </div>
    </div>
  </div>
</template>