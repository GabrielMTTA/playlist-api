<script setup> 
import { ref } from 'vue' 
import PlaylistList from './components/PlaylistList.vue'
import PlaylistForm from './components/PlaylistForm.vue'
import BuscarPlaylist from './components/BuscarPlaylist.vue'
import ConfigPanel from './components/ConfigPanel.vue' 
const listaRef = ref(null)

function aoCriar() { 
  listaRef.value?.carregar() 
}

</script> 
<template> 
<div> 
  <h1>
    Playlist Manager
  </h1>
  <ConfigPanel />
  <PlaylistForm @criada="aoCriar" />
  <BuscarPlaylist /> 
  <PlaylistList ref="listaRef" /> 
</div> 
</template>