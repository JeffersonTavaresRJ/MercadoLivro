package com.mercadolivro.enuns

enum class Errors(val code: String, val message: String) {
    ML0000("ML-0000", "Request Inválido"),
    ML0001("ML-0001", "Livro [%s] não existe"),
    ML0002("ML-0002", "Cliente [%s] não existe"),
    ML0003("ML-0003", "Não é possível alterar um livro com status %s"),
    ML0004("ML-0004", "O livro %s foi %s."),
    ML0005("ML-0005", "Acesso Negado")
}