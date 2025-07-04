# 🗣️ Talk It Up (TIU) - API Restfull(WEB) 

**TIU (Talk It Up)** é uma API REST educacional desenvolvida em Java com Spring Boot, permitindo criação e interação em tópicos escolares organizados por categorias.  

---

## 🎯 Objetivo técnico

Oferecer uma API RESTful robusta para integração com aplicações front-end educacionais, permitindo autenticação segura, categorização por áreas do conhecimento e comunicação entre usuários.

---

## ⚙️ Tecnologias

- Java 21  
- Spring Boot 3  
- Spring Web  
- Spring Security  
- Spring Data JPA
- Spring Mail
- PostgreSQL  
- Bean Validation (JSR-380)  
- Thymeleaf

---

## 🔐 Autenticação & Segurança

- Login via **email + senha**
- Registro exige **verificação por email**
- Senhas criptografadas com **BCrypt**
- Acesso a endpoints controlado por autenticação
- Conta só pode logar após confirmação (`enabled = true`)
- Implementação com `UserDetailsService`

---

## 📤 Email

- SMTP configurado com Spring Mail
- Envio de emails HTML com Thymeleaf
- Confirmação por link expira e invalida o token

---

## 🧪 Validações

- Todos os DTOs usam Bean Validation:
  - `@NotNull`, `@NotBlank`, `@Email`, etc.
- Respostas da API retornam HTTP 400 em caso de erro com os detalhes de campo

---

## 👨‍💻 Autor

- **Raul Alves(Letch)** — Backend & Arquitetura

---
