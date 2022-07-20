package com.soulcode.Servicos.Models;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Pagamento {

    @Id
    private Integer idPagamento;

    /*@NumberFormat(pattern = "#.##0,00")*/
    @Column(nullable = false)
    private Double valor;

    @Column(nullable = false)
    private String formaPagamento;

    @Enumerated(EnumType.STRING) //Para ele criar do tipo string
    private StatusPagamento status;

    public Integer getIdPagamento() {
        return idPagamento;
    }

    public void setIdPagamento(Integer idPagamento) {
        this.idPagamento = idPagamento;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public StatusPagamento getStatus() {
        return status;
    }

    public void setStatus(StatusPagamento status) {
        this.status = status;
    }
}
