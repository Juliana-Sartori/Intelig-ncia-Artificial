import java.util.Scanner;

public class Main {

   static Scanner scn = new Scanner(System.in);

    static double [][][] portaE = new double[][][]{
            {{0,0}, {0}},
            {{0,1}, {0}},
            {{1,0}, {0}},
            {{1,1}, {1}}
    };

     static double [][][] portaOU = new double[][][]{
            {{0,0}, {0}},
            {{0,1}, {1}},
            {{1,0}, {1}},
            {{1,1}, {1}}
    };

    static double [][][] portaXOR = new double[][][]{
            {{0,0}, {0}},
            {{0,1}, {1}},
            {{1,0}, {1}},
            {{1,1}, {0}}
    };

    static double [][][] portaROBO = new double[][][]{
            {{0,0,0}, {1,0}},
            {{0,0,1}, {0,1}},
            {{0,1,0}, {0,1}},
            {{0,1,1}, {0,1}},
            {{1,0,0}, {1,0}},
            {{1,0,1}, {1,0}},
            {{1,1,0}, {1,0}},
            {{1,1,1}, {0,0}}
    };

   public static double [][][] readUser (){
       int read = scn.nextInt();

       switch (read) {
           case 1:
               return portaE;

           case 2:
               return portaOU;

           case 3:
               return portaXOR;

           case 4:
               return portaROBO;

           default:
               System.out.println("Opção inválida");
               return new double[][][]{};
       }
   }

    public static void main(String[] args) {

        System.out.println("--------------------------------");
        System.out.println("| Escolha a base de dados:     |\n|                              |     ");
        System.out.println("| Digite [1] para a Porta E    |");
        System.out.println("| Digite [2] para a Porta OU   |");
        System.out.println("| Digite [3] para a Porta XOR  |");
        System.out.println("| Digite [4] para a Porta ROBÔ |");
        System.out.println("--------------------------------");

        double [][][] qualBase = readUser();

        Perceptron rna;

        if(qualBase == portaROBO) {
                rna = new Perceptron(3,2); ;
        } else{
             rna = new Perceptron(2,1);
        }

        System.out.println("----------------------------------------");
        System.out.println(" Época    | Erro aproximado da época        " );
        System.out.println("----------------------------------------");

        for (int e = 0; e < 1000; e++){
            double erro_epoca_aprox = 0;

            for (int a=0; a < qualBase.length; a++) {
                double[][] amostra = qualBase[a];
                double[] x_in = amostra[0];
                double[] y = amostra[1];

                double[] o = rna.treinar(x_in, y);

                double erro_amostra_aprox = 0;

                for (int j = 0; j < o.length; j++) {
                    erro_amostra_aprox += Math.abs((y[j] - o[j]));
                }

                erro_epoca_aprox += erro_amostra_aprox;
            }

            System.out.println("  "+e+"       |"+"    "  +erro_epoca_aprox+"             ");

        }
        System.out.println("----------------------------------------");
    }
}