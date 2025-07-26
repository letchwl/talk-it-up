# 🗣️ Talk It Up (TIU)  

API RESTful educacional feita com Java e Spring Boot, que permite a criação, exibição e organização de tópicos escolares por categoria, com autenticação segura e verificação por e-mail.

---

## 🎯 Objetivo

Oferecer uma API RESTful robusta para integração com aplicações educacionais, promovendo a comunicação entre estudantes e professores por meio de tópicos organizados por área do conhecimento.  
Ideal para fóruns escolares, projetos de extensão e ferramentas de apoio pedagógico.

---

## ⚙️ Tecnologias Utilizadas

### Backend
- Java 21  
- Spring Boot 3  
- Spring Web  
- Spring Data JPA  
- Spring Security  
- Spring Mail  

### Banco de Dados
- PostgreSQL  

### Frontend (servidor-side)
- Thymeleaf
- HTML5
- CSS3 

### Validação
- Bean Validation

### Outras
- BCrypt para criptografia
- API RESTful com retornos HTTP semânticos

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

- **Raul Alves (Letch)**  
  Backend Developer • Criador do TIU  
  [GitHub](https://github.com/letchwl) | [Email](raullalvespe1227@gmail.com)

---
