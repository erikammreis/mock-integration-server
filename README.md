# 🔐 Mock Integration Server

Simulador extensível de autenticações via OAuth2/OpenID Connect para testes de integração sem depender de serviços reais como Azure AD, Google OAuth, Auth0, etc.

---

## 📌 Visão geral

Este projeto nasceu com o objetivo de facilitar o desenvolvimento e testes de integrações com provedores de autenticação — **sem necessidade de pagar por serviços reais** ou se preocupar com limites de uso, tokens reais e configurações complexas.

Com uma estrutura modular, ele permite simular múltiplos provedores de autenticação e pode ser facilmente estendido para outras integrações de login.

---

## 🎯 O que este mock resolve

- Testes locais sem internet ou credenciais reais
- Simulação de tokens válidos e fluxos de autenticação
- Integração fácil com sistemas que esperam um provedor real
- Criação de ambientes de desenvolvimento mais rápidos e independentes

---

## ✅ Funcionalidades atuais

- Simulação do endpoint `/oauth2/v2.0/token` do Azure AD
- Retorno de token JWT falso com estrutura válida
- Configuração simples com Spring Boot + PostgreSQL
- Código pronto para deploy local ou em nuvem

---

##🌍 Para quem é este projeto?
Desenvolvedores que querem testar autenticação OAuth2 sem criar conta em cada provedor

Pessoas montando portfólio e desejando simular integrações realistas

Times que precisam automatizar testes de login em ambiente local ou homologação

--

##🤝 Contribuições
Este projeto é aberto à comunidade! Algumas formas de contribuir:

Adicionar novos provedores simulados (Google, Facebook, GitHub, etc.)

Melhorar a geração e validação de tokens fake

Criar testes automatizados

Sugerir melhorias via Issues ou Pull Requests

--

##👩‍💻 Autoria
Feito com ❤️ por Erika Macedo
LinkedIn <!-- troque pela sua URL real -->

--

## 🛣️ Roadmap (futuro)

- [ ] Simulação do fluxo completo OAuth com `/authorize` + `/token`
- [ ] Suporte a Google OAuth 2.0
- [ ] Simulação de outros provedores (Facebook, Auth0, GitHub)
- [ ] Interface web para visualizar e testar os fluxos
- [ ] Geração de tokens customizados com claims personalizadas
- [ ] Configuração de múltiplos "ambientes fake"

---

⚠️ Aviso Legal
Este projeto é apenas para fins de teste e aprendizado. Não é afiliado, associado ou mantido por nenhum provedor oficial de autenticação como Microsoft, Google ou outros.

--

## 🧪 Como usar localmente

1. Clone o repositório:
   ```bash
   git clone https://github.com/seuusuario/mock-integration-server.git
   cd mock-integration-server

