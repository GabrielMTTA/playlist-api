# Playlist API + Frontend
Projeto feito para o Teste, dividido em duas partes: uma API REST para gerenciar listas de reprodução e um frontend em Vue para interagir com ela.

O que o projeto faz
A API permite criar, listar, buscar e apagar playlists. Cada playlist tem nome, descrição e uma lista de músicas (título, artista, álbum, ano e gênero). O frontend é só uma interface visual para usar essa API sem precisar ficar mandando requisições na mão pelo Postman.

Backend (pasta playlist-api)
Feito em Java com Spring Boot, Maven e JPA. O banco é o H2, que roda em memória (ou seja, toda vez que a aplicação reinicia, os dados somem).

Endpoints
POST /lists — cria uma playlist nova. Retorna 201 se der certo, 400 se o nome vier vazio ou nulo.
GET /lists — lista todas as playlists.
GET /lists/{nome} — busca uma playlist específica pelo nome. 404 se não achar.
DELETE /lists/{nome} — apaga. 204 se der certo, 404 se não existir.

Uso de DTO
A API não devolve as entidades do banco (Playlist, Musica) direto no JSON. Em vez disso, tem duas classes separadas só pra isso: PlaylistJson e MusicaJson (o nome é informal, mas o padrão é conhecido como DTO — Data Transfer Object), estão na pasta oJson.

A ideia é simples: a entidade é o que fica salvo no banco (com @Entity, relacionamentos do JPA etc), e o DTO é o que trafega na API. Se não fizesse essa separação, corria o risco de vazar detalhes internos do banco no JSON de resposta, ou dar erro de serialização por causa de referências do Hibernate. Então o fluxo é: o JSON chega → vira um DTO → o Service converte esse DTO numa entidade pra salvar → quando responde, converte a entidade salva de volta pra DTO.

Autenticação
A API usa Basic Auth. Tem dois usuários já cadastrados quando a aplicação sobe:
user / user123 — pode criar, listar e buscar
admin / admin123 — pode fazer tudo isso e também apagar (o user não consegue apagar, recebe 403)
Como rodar
cd playlist-api
mvn spring-boot:run
A API sobe na porta 8080. Pra rodar os testes: mvn test.

Tem uma coleção do Postman na pasta postman/ com todos os cenários (sucesso e erro) já montados, é só importar e trocar a variável baseUrl pela URL onde a API estiver rodando.

Frontend (pasta playlist-frontend)
Feito em Vue 3 com Vite. Tem um formulário pra cada operação da API.

Como rodar
cd playlist-frontend
npm install
npm run dev
Abre em http://localhost:5173 (ou a porta que o Vite escolher).

Na primeira vez que abrir, precisa preencher o painel "Conexão com a API" com a URL onde o backend está rodando e o usuário/senha. Isso fica salvo no navegador, então não precisa preencher de novo toda vez.

Estrutura
src/api.js — onde ficam as chamadas pra API (fetch com Basic Auth)
src/components/ConfigPanel.vue — tela de login/configuração
src/components/PlaylistForm.vue — formulário de criação
src/components/BuscarPlaylist.vue — busca por nome
src/components/PlaylistList.vue — lista tudo e tem o botão de apagar
Observação sobre CORS
Como o frontend e o backend rodam em portas diferentes, foi preciso liberar CORS no Spring Security (arquivo SecurityConfig.java), senão o navegador bloqueia as requisições do frontend pra API.
