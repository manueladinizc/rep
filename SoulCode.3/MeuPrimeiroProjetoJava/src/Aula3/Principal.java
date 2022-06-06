package Aula3;

public class Principal {
    public static void main(String[] args)  {
        Animal a1 = new Animal("Totô", "cachorro", 4);
        Animal a2 = new Animal("Otto", "gato", 1);

        System.out.println("o nome do animal a1 é:" + a1.getNome());
        System.out.println("o nome do animal a1 é:" + a2.getNome());

        a1.setIdade(-3);
        System.out.println("o nome do animal a1 é:" + a1.getNome());
        a1.vacinar();
        a2.vacinar();
    }

}
