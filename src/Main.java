import java.util.Scanner;
import java.util.Random;

public class Main {

    static Scanner scn = new Scanner(System.in);

    static double[][][] portaE = new double[][][]{
            {{0, 0}, {0}},
            {{0, 1}, {0}},
            {{1, 0}, {0}},
            {{1, 1}, {1}}
    };

    static double[][][] portaOU = new double[][][]{
            {{0, 0}, {0}},
            {{0, 1}, {1}},
            {{1, 0}, {1}},
            {{1, 1}, {1}}
    };

    static double[][][] portaXOR = new double[][][]{
            {{0, 0}, {0}},
            {{0, 1}, {1}},
            {{1, 0}, {1}},
            {{1, 1}, {0}}
    };

    static double[][][] portaROBO = new double[][][]{
            {{0, 0, 0}, {1, 0}},
            {{0, 0, 1}, {0, 1}},
            {{0, 1, 0}, {0, 1}},
            {{0, 1, 1}, {0, 1}},
            {{1, 0, 0}, {1, 0}},
            {{1, 0, 1}, {1, 0}},
            {{1, 1, 0}, {1, 0}},
            {{1, 1, 1}, {0, 0}}
    };

    static double[][][] balance = ReadData.todaBaseManual();

    public static double[][][] readUser() {
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

        double[][][] qualBase = readUser();

        MLP rna;
        if (qualBase == portaROBO) {
            rna = new MLP(3, 4, 2);
        } else if (qualBase == balance) {
            rna = new MLP(4, 5, 3);
        } else {
            rna = new MLP(2, 3, 1);
        }

        // Dividir os dados em treino (80%) e teste (20%)
        double[][][] treino = new double[qualBase.length * 80 / 100][][];
        double[][][] teste = new double[qualBase.length - treino.length][][];
        Random rand = new Random();

        // Embaralhar os dados
        for (int i = 0; i < qualBase.length; i++) {
            int randomIndex = rand.nextInt(qualBase.length);
            double[][] temp = qualBase[i];
            qualBase[i] = qualBase[randomIndex];
            qualBase[randomIndex] = temp;
        }

        // Separar os dados em treino e teste
        System.arraycopy(qualBase, 0, treino, 0, treino.length);
        System.arraycopy(qualBase, treino.length, teste, 0, teste.length);

        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println(" Época  |      Erro treino aprox        |     Erro class. treino     |          Erro teste aprox          |   Erro class. teste ");
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------");

        for (int e = 0; e < 1000; e++) {
            double erroTreinoAprox = 0;
            int erroClassTreino = 0;

            // Treinamento
            for (double[][] amostra : treino) {
                double[] x_in = amostra[0];
                double[] y = amostra[1];
                double[] o = rna.treinar(x_in, y);

                double erroAprox = 0;
                for (int j = 0; j < o.length; j++) {
                    erroAprox += Math.abs(y[j] - o[j]);
                    if (Math.round(o[j]) != y[j]) {
                        erroClassTreino++;
                    }
                }
                erroTreinoAprox += erroAprox;
            }

            // Teste
            double erroTesteAprox = 0;
            int erroClassTeste = 0;
            for (double[][] amostra : teste) {
                double[] x_in = amostra[0];
                double[] y = amostra[1];
                double[] o = rna.treinar(x_in, y); // Aqui você deve usar a função de inferência, não treinar

                double erroAprox = 0;
                for (int j = 0; j < o.length; j++) {
                    erroAprox += Math.abs(y[j] - o[j]);
                    if (Math.round(o[j]) != y[j]) {
                        erroClassTeste++;
                    }
                }
                erroTesteAprox += erroAprox;
            }

            System.out.println("  " + e + "    |        " + erroTreinoAprox + "       |                   " + erroClassTreino + "       |       " + erroTesteAprox + "       |       " + erroClassTeste);
        }
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------------------------");
    }
}

