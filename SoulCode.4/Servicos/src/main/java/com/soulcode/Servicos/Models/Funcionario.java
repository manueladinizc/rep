package com.soulcode.Servicos.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

//Entidade: representa tabela que será criado no MySQL. Indica que a classe é uma entidade, que é uma tabela e cada
// instancia dessa entidade é uma linha do banco de dados
@Entity
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idFuncionario;
//generated value, cria o id automaticamente, e strategy a forma que será gerado
    @Column(nullable = false, length = 100)
    private String nome;
// Collumn nullable diz que o funcionario não pode ser cadastrado sem essa coluna nome
    @Column(nullable= false, length = 100, unique = true)
    private String email;

    @Column(nullable = true)
    private String foto;

    @JsonIgnore //para n entrar em um loop  infinito
    @OneToMany(mappedBy = "funcionario")
    private List<Chamado>chamado = new ArrayList<Chamado>();


    @ManyToOne
    @JoinColumn(name = "idCargo")
    private Cargo cargo;


    public Integer getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(Integer idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public List<Chamado> getChamado() {
        return chamado;
    }

    public void setChamado(List<Chamado> chamado) {
        this.chamado = chamado;
    }


    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public Cargo getCargo() {
        return cargo;
    }


}


/*
ordem para dropar tabelas chamados, funcionario, cargos, cliente*/
