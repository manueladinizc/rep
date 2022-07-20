package com.soulcode.Servicos.Models;


public enum StatusChamado {

    RECEBIDO("Recebido"),
    ATRIBUIDO("Atribuido"),
    CONCLUIDO("Concluido"),
    ARQUIVADO("Arquivado");

    private String conteudo;

    StatusChamado(String conteudo){
        this.conteudo = conteudo;
    }

    public String getConteudo() {

        return conteudo;
    }


//    O set não deve exister, pois foi definido os atributos no enum
//    public void setConteudo(String conteudo) {
//        this.conteudo = conteudo;
//    }
}
