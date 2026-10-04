<script setup>
import { config, salvarConfig } from '../api.js'

function salvar() {
  salvarConfig()
}
</script>

<template>
  <div class="card">
    <h2>Conexão com a API</h2>
    <p class="muted">
      Informe a URL da sua API (ex: a URL pública do Codespaces) e as credenciais de login.
      Use <strong>user / user123</strong> para operações normais, ou <strong>admin / admin123</strong> para poder apagar listas.
    </p>

    <label for="baseUrl">URL base da API</label>
    <input id="baseUrl" v-model="config.baseUrl" placeholder="http://localhost:8080" />

    <div class="row">
      <div>
        <label for="username">Usuário</label>
        <input id="username" v-model="config.username" />
      </div>
      <div>
        <label for="password">Senha</label>
        <input id="password" v-model="config.password" type="password" />
      </div>
    </div>

    <button @click="salvar">Salvar conexão</button>
  </div>
</template>