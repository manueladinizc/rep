package Atividade1;

// definir quais atributos esse funcionário vai ter

public class Funcionario {
    private String nome;
    private String cargo;
    private int idade;
    private String situacao;
    private String salario;

    public Funcionario (String nome, String cargo, int idade, String situacao, String salario) { // o construtor sempre tem o nome da classe
        this.nome = nome;
        this.cargo = cargo;
        this.idade = idade;
        this.situacao = situacao;
        this.salario = salario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }


    public String getSalario() {
        return salario;
    }

    public void setSalario(String salario) {
        this.salario = salario;
    }





    void  demitirFuncionario() {
        this.situacao = "inativo";
    }


    void trocarCargoFuncionario() {
            if (this.cargo == "analista") {
                this.cargo = "supervisor";
            } else if  (this.cargo == "supervisor"){
                this.cargo = "coordenador";
            } else if (this.cargo == "coordenador"){
                this.cargo = "gerente";
            } else {
                this.cargo = "gerente";
            }
        }

        void fazerAniversário(){

        this.idade+=1;
        }

        void pagarSalario(){

        this.salario = "pago";
        }




}
