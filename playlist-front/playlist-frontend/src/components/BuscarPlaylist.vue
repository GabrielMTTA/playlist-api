<script setup>
import { reactive, ref } from 'vue'
import { PlaylistApi } from '../api.js'

const nomeBusca = ref('')
const resultado = ref(null)
const feedback = reactive({ tipo: '', texto: '' })
const buscando = ref(false)

async function buscar() {
  feedback.tipo = ''
  feedback.texto = ''
  resultado.value = null
  if (!nomeBusca.value) return
  buscando.value = true
  try {
    resultado.value = await PlaylistApi.buscar(nomeBusca.value)
  } catch (e) {
    if (e.status === 404) {
      feedback.tipo = 'err'
      feedback.texto = `Nenhuma lista encontrada com o nome "${nomeBusca.value}" (404 Not Found).`
    } else {
      feedback.tipo = 'err'
      feedback.texto = `Erro ao buscar (status ${e.status ?? '?'}): ${e.message}`
    }
  } finally {
    buscando.value = false
  }
}
</script>

<template>
  <div class="card">
    <h2>Buscar playlist por nome</h2>
    <div class="row">
      <div>
        <label for="nomeBusca">Nome exato da lista</label>
        <input id="nomeBusca" v-model="nomeBusca" placeholder="Ex: Lista 1" @keyup.enter="buscar" />
      </div>
    </div>
    <button :disabled="buscando || !nomeBusca" @click="buscar">
      {{ buscando ? 'Buscando...' : 'Buscar (GET /lists/{nome})' }}
    </button>

    <div v-if="feedback.texto" :class="['feedback', feedback.tipo]">{{ feedback.texto }}</div>

    <div v-if="resultado" class="playlist-item" style="margin-top:16px;">
      <div class="playlist-item-header">
        <strong>{{ resultado.nome }}</strong>
      </div>
      <p class="muted">{{ resultado.descricao }}</p>
      <div>
        <span v-for="(m, i) in resultado.musicas" :key="i" class="musica-tag">
          {{ m.titulo }} — {{ m.artista }}
        </span>
      </div>
    </div>
  </div>
</template>