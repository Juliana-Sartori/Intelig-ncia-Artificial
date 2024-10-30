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


    static double[][][] balance = ReadData.todaBaseManual();


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

           case 5:
               try {
                   balance = ReadData.convertToTridimensionalArray("/Users/Juliana/Desktop/facul-8/IC/dataBalance.txt");
                   return balance;
               } catch (Exception e) {
                   System.out.println("Erro ao carregar a base de dados: " + e.getMessage());
                   return new double[][][]{};
               }

           case 6:
               return balance;


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
        System.out.println("| Digite [5] para o BALANCE    |");
        System.out.println("--------------------------------");

        double [][][] qualBase = readUser();
//        ReadData.printMatriz(qualBase);

        MLP mlp;

        if(qualBase == portaROBO) {
                mlp = new MLP(3,2);
        }
        else if(qualBase == balance){
            mlp = new MLP(4,3);
        }
        else{
             mlp = new MLP(2,1);
        }

        System.out.println("----------------------------------------");
        System.out.println(" Época    | Erro aproximado da época        " );
        System.out.println("----------------------------------------");


//        for (int e = 0; e < 1000; e++){
//            double erro_aprox_treino = 0;
//            double erro_classificacao_treino = 0;
//
//            for (int a=0; a < baseTreino.length; a++) {
//                double x_in = baseTreino[a][0];
//                double y = baseTreino[a][1];
//                double[] out = mlp.treinar(x_in, y);
//
//                for (int j = 0; j < out.length; j++) {
//                    erro_aprox_treino += Math.abs((y[j] - out[j]));
//                }
//
//                double outLinha = threshold(out[0]);
//                erro_epoca_aprox += erro_amostra_aprox;
//            }
//
//            System.out.println("  "+e+"       |"+"    "  +erro_epoca_aprox+"             ");
//
//        }

        for (int e = 0; e < 1000; e++) {
            double erro_aprox_treino = 0;
            double erro_classificacao_treino = 0;

            for (int a = 0; a < qualBase.length; a++) {
                double[] x_in = qualBase[a][0];
                double[] y = qualBase[a][1];
                double[] out = mlp.treinar(x_in, y);

                for (int j = 0; j < out.length; j++) {
                    erro_aprox_treino += Math.abs(y[j] - out[j]);
                }

                boolean erroEncontrado = false;
                for (int j = 0; j < out.length; j++) {
                    double outLinha = threshold(out[j]);
                    if (Math.abs(y[j] - outLinha) > 0) {
                        erroEncontrado = true;
                        break;
                    }
                }
                if (erroEncontrado) {
                    erro_classificacao_treino += 1;
                }
            }

            System.out.println("  " + e + "       |" + "    " + erro_aprox_treino + "             "+ erro_classificacao_treinop + "             ");
        }


        System.out.println("----------------------------------------");
    }

    public static double threshold(double value) {
        return value >= 0.5 ? 1 : 0;
    }
}