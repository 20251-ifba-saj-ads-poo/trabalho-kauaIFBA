# Reavaliação de Relatório - trabalho-kauaIFBA

## Descrição do Trabalho

Gestão de Clube de Leitura

Modele um sistema para um clube de leitura. O clube possui membros, encontros e livros discutidos. Cada encontro tem uma data, um livro e uma lista de membros presentes. Implemente funcionalidades para registrar encontros, listar membros presentes em cada encontro e livros discutidos pelo clube.

## Resumo da Pontuação (Reavaliação)

| Critério | Pontuação Máxima | Pontuação Obtida | Comentários |
| --- | --- | --- | --- |
| **Interface Gráfica (JavaFX)** | **20** | **20** | A interface permanece funcional e robusta. |
| **Camada de Negócio** | **30** | **22** | A camada de serviço opera sobre dados potencialmente desatualizados passados pelo controller, o que é uma falha conceitual na implementação das regras de negócio. |
| **Camada de Dados** | **20** | **20** |  |
| **Separação em Camadas** | **20** | **15** | A comunicação entre as camadas é falha, pois a camada de serviço não busca dados atualizados, enfraquecendo seu papel como fonte da verdade. |
| **Boas Práticas** | **10** | **6** | O código, embora bem estruturado, contém duas falhas de design significativas: o problema de consistência de dados no `Service`. |
| **Total** | **100** | **83** | |

---

## Análise Detalhada (Reavaliação)

### Pontos Fortes (Mantidos)

- **Estrutura e Exceções:** O projeto continua a se destacar pela excelente organização em pacotes e pelo uso exemplar de exceções personalizadas, o que confere robustez ao sistema.
- **Interface Gráfica:** A UI é completa, intuitiva e funcional.

### Pontos Críticos a Melhorar

#### 1. [GRAVE] Inconsistência de Dados na Camada de Serviço (Observação do Professor)
Conforme apontado, a camada de serviço (`MemberService`) não garante a consistência dos dados. Métodos como `returnUserMeetings` e `howManyUserMeetings` operam diretamente na instância do objeto `Member` recebida do controller. Essa instância pode estar **desatualizada (stale)**, não refletindo o estado mais recente do banco de dados.

**Código do Aluno (`MemberService.java`):**
```java
// Problema: Opera na instância `user` que pode estar desatualizada.
public List<Meeting> returnUserMeetings(Member user) throws UserIsNull {
    if(user == null){
        throw new UserIsNull("Ocorreu um erro.");
    }
    return user.getSubscribedMeetings(); 
}
```

**Impacto:** Se outro usuário se inscreveu em um encontro, ou se alguma outra parte do sistema alterou os dados do `Member`, a informação exibida na tela estará incorreta, pois o serviço não consultou o repositório para obter o estado mais recente da entidade.

**Correção Sugerida:** A camada de serviço deve sempre ser a fonte da verdade. Para isso, ela deve buscar a versão mais recente da entidade no banco de dados antes de realizar qualquer operação.

```java
public List<Meeting> returnUserMeetings(Member user) throws UserIsNull {
    if (user == null || user.getId() == null) {
        throw new UserIsNull("Usuário inválido para consulta.");
    }
    // Correto: Busca a instância mais recente do banco de dados
    Member freshUser = this.read(user); // ou repository.findById(user.getId())
    return freshUser.getSubscribedMeetings();
}
```
