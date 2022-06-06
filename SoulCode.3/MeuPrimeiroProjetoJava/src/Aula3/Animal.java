package Aula3;


public class Animal {
    private String nome;
    private String especie;
    private int idade;

    public Animal(String nome, String especie, int idade) { // o construtor sempre tem o nome da classe
        this.nome = nome;
        this.especie = especie;
        this.idade = idade;
    }

    public void vacinar() {
        System.out.println("O animal" + getNome() + "foi vacinado");
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        if (idade < 0) {
            idade *= -1;
            System.out.println("não existe idade negativa");
        } else {
            this.idade = idade;
        }
    }
}
