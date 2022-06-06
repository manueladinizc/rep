package Atividade1;


// Inserir as informações que vai atribuir aos funcionários

public class Principal {
    public static void main(String[] args)  {
        Funcionario func1 = new Funcionario("Ana", "analista", 25,  "ativo", "pendente");
        Funcionario func2 = new Funcionario("Bruno", "analista", 30 , "ativo", "pendente");
        Funcionario func3 = new Funcionario("Caio", "coordenador", 35,  "ativo", "pendente");
        Funcionario func4 = new Funcionario("Daniela", "gerente", 45, "ativo", "pendente");



        System.out.println("O funcionário(a): " + func1.getNome() + " tem o cargo de: " + func1.getCargo() + " ,idade: "+ func1.getIdade() + " ,vínculo: " + func1.getSituacao() + " ,situaão do salário: " + func1.getSalario());
        System.out.println("O funcionário(a): " + func2.getNome() + " tem o cargo de: " + func2.getCargo() + " ,idade: "+ func2.getIdade() + " ,vínculo: " + func2.getSituacao() + " ,situaão do salário: " + func2.getSalario());
        System.out.println("O funcionário(a): " + func3.getNome() + " tem o cargo de: " + func3.getCargo() + " ,idade: "+ func3.getIdade() + " ,vínculo: " + func3.getSituacao() + " ,situaão do salário: " + func3.getSalario());
        System.out.println("O funcionário(a): " + func4.getNome() + " tem o cargo de: " + func4.getCargo() + " ,idade: "+ func4.getIdade() + " ,vínculo: " + func4.getSituacao() + " ,situaão do salário: " + func4.getSalario());
        System.out.println("--------------------------------------------------------------------------------------------------------------------------");

        func1.demitirFuncionario();
        func2.trocarCargoFuncionario();
        func3.fazerAniversário();
        func4.pagarSalario();

        System.out.println("O funcionário(a): " + func1.getNome() + " tem o cargo de: " + func1.getCargo() + " ,idade: "+ func1.getIdade() + " ,vínculo: " + func1.getSituacao() + " ,situaão do salário: " + func1.getSalario());
        System.out.println("O funcionário(a): " + func2.getNome() + " tem o cargo de: " + func2.getCargo() + " ,idade: "+ func2.getIdade() + " ,vínculo: " + func2.getSituacao() + " ,situaão do salário: " + func2.getSalario());
        System.out.println("O funcionário(a): " + func3.getNome() + " tem o cargo de: " + func3.getCargo() + " ,idade: "+ func3.getIdade() + " ,vínculo: " + func3.getSituacao() + " ,situaão do salário: " + func3.getSalario());
        System.out.println("O funcionário(a): " + func4.getNome() + " tem o cargo de: " + func4.getCargo() + " ,idade: "+ func4.getIdade() + " ,vínculo: " + func4.getSituacao() + " ,situaão do salário: " + func4.getSalario());

    }

}