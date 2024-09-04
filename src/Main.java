public class Main {

    //base de dados da porta E
    //a primeira dimencao eh as amostras, sendo ao todo 4
    //a segunda dimencao sao as entradas e a saida {x,x} entrada {y} saida
    //a terceira dimencao sao os valores dentro dos vetores x e y{}
    static double [][][] portaE = new double[][][]{
            {{0,0}, {0}},
            {{0,1}, {0}},
            {{1,0}, {0}},
            {{1,1}, {1}}
    };

    public static void main(String[] args) {
        //inicialização do Perceptron
        Perceptron rna = new Perceptron(3,2);

        System.out.println("----------------------------------------");
        System.out.println(" Época    | Erro aproximado da época        " );
        System.out.println("----------------------------------------");
        //de epocas (1000 epocas)
        for (int e = 0; e < 1000; e++){
            double erro_epoca_aprox = 0;

            //pegas as amostras
            for (int a=0; a < portaE.length; a++) {
                double[][] amostra = portaE[a];
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