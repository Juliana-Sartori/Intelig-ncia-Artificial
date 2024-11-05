//import java.util.Random;
//
//public class MLP {
//
//    private int qtd_in;
//    private int qtd_out;
//    private int qtd_H;
//
//   //*** private double[][] pesos;
//
//    private double ni;
//    private double[][] vetWH, vetWo;
//
//    public MLP(int qtd_in, int qtd_out, int qtd_H, double ni){
//        this.qtd_in = qtd_in;
//        this.qtd_out = qtd_out;
//        this.qtd_H = qtd_H;
//        this.ni = ni;
//
//        this.vetWH = new double[qtd_in+1][qtd_H];
//        this.vetWo = new double[qtd_H+1][qtd_out];
//     //*   this.pesos = new double[qtd_in+1][qtd_out];
//
//        //--*ACHO QUE ESSE AQUI TEM Q TER, SO QUE COM WH E WO
//     //--*   Random rand = new Random();
//     //--*   for (int i = 0; i < pesos.length; i++) {
//        //--*       for (int j = 0; j < pesos[i].length; j++) {
//        //--*    pesos[i][j] = -0.3 + (0.3 - (-0.3)) * rand.nextDouble();
//        //--*}
//        //--*}
//    }
//
//    public double[] treinar (double [] x_in, double [] y){
//        double [] xi = new double[x_in.length + 1];
//
//        xi[0] = 1;
//
//        for (int i = 0; i < x_in.length; i++){
//            xi[i+1] = x_in[i];
//        }
//
//        //--*   double [] u = new double[y.length];
//        //--* double [] o = new double[y.length];
//
//        double [] H = new double[qtd_H+1];
//        H[qtd_H] =1;
//
//        //--*
//        /*
//        for (int j = 0; j< y.length; j++){
//
//            for (int i = 0; i < xi.length; i++){
//                u[j] = u[j] + ( xi[i] * this.pesos[i][j]);
//            }
//
//            o[j] = 1/(1+Math.exp(-u[j]));
//        }*/
//
//        for (int j = 0; j< qtd_H; j++){
//            double soma =0;
//
//            for (int i = 0; i < xi.length; i++){
//                soma += xi[i] * vetWH[i][j];
//            }
//
//           H[j] = 1/(1+Math.exp(-soma));
//        }
//
//        double [] out = new double[qtd_out];
//
//        for(int j = 0; j< out.length; j++){
//            double soma = 0;
//
//            for(int i = 0; i< xi.length; i++){
//                soma += H[i] * vetWo[i][j];
//            }
//            out[j] = 1/(1+Math.exp(-soma));
//        }
//
//        double [] deltaO = new double[qtd_out];
//
//        for(int j = 0; j< qtd_out; j++){
//            deltaO[j] = out[j] * (1 - out[j]) * (y[j] - out[j]);
//        }
//
//        double [] deltaH = new double[qtd_H];
//
//        for(int i = 0; i< qtd_H; i++){
//            double soma = 0;
//
//            for(int j = 0; j< qtd_out; j++){
//                soma += deltaO[j] * vetWo[i][j];
//            }
//            deltaH[i] = H[i] * (1 - H[i]) * soma;
//        }
//
//        for (int i = 0; i< xi.length; i++){
//            for (int j = 0; j< qtd_H; j++){
//               vetWH[i][j] = vetWH[i][j] + ni * deltaH[j] * xi[i];
//            }
//        }
//
//        for (int i = 0; i< qtd_H+1; i++){
//            for (int j = 0; j< qtd_out; j++){
//                vetWo[i][j] += ni * deltaO[j] * H[i];
//            }
//        }
//        return out;
//    }
//}

import java.util.Random;

public class MLP {

    private int qtd_in;
    private int qtd_hidden;
    private int qtd_out;

    private double[][] pesosEntradaOculta;
    private double[][] pesosOcultaSaida;

    public MLP(int qtd_in, int qtd_hidden, int qtd_out) {
        this.qtd_in = qtd_in;
        this.qtd_hidden = qtd_hidden;
        this.qtd_out = qtd_out;

        // Inicialização dos pesos para a camada de entrada-oculta
        this.pesosEntradaOculta = new double[qtd_in + 1][qtd_hidden];
        this.pesosOcultaSaida = new double[qtd_hidden + 1][qtd_out];

        Random rand = new Random();

        // Inicializar pesos da camada de entrada para a camada oculta
        for (int i = 0; i < pesosEntradaOculta.length; i++) {
            for (int j = 0; j < pesosEntradaOculta[i].length; j++) {
                pesosEntradaOculta[i][j] = -0.3 + (0.3 - (-0.3)) * rand.nextDouble();
            }
        }

        // Inicializar pesos da camada oculta para a camada de saída
        for (int i = 0; i < pesosOcultaSaida.length; i++) {
            for (int j = 0; j < pesosOcultaSaida[i].length; j++) {
                pesosOcultaSaida[i][j] = -0.3 + (0.3 - (-0.3)) * rand.nextDouble();
            }
        }
    }

    public double[] treinar(double[] x_in, double[] y) {
        // Adicionar o bias na entrada
        double[] xi = new double[x_in.length + 1];
        xi[0] = 1;
        for (int i = 0; i < x_in.length; i++) {
            xi[i + 1] = x_in[i];
        }

        // Propagação para a camada oculta
        double[] u_hidden = new double[qtd_hidden];
        double[] o_hidden = new double[qtd_hidden + 1];
        o_hidden[0] = 1; // Bias na camada oculta

        for (int j = 0; j < qtd_hidden; j++) {
            for (int i = 0; i < xi.length; i++) {
                u_hidden[j] += xi[i] * pesosEntradaOculta[i][j];
            }
            o_hidden[j + 1] = 1 / (1 + Math.exp(-u_hidden[j])); // Função de ativação Sigmoid
        }

        // Propagação para a camada de saída
        double[] u_out = new double[qtd_out];
        double[] o_out = new double[qtd_out];

        for (int k = 0; k < qtd_out; k++) {
            for (int j = 0; j < o_hidden.length; j++) {
                u_out[k] += o_hidden[j] * pesosOcultaSaida[j][k];
            }
            o_out[k] = 1 / (1 + Math.exp(-u_out[k])); // Função de ativação Sigmoid
        }

        // Retropropagação do erro
        double[][] dW_oculta_saida = new double[qtd_hidden + 1][qtd_out];
        double[][] dW_entrada_oculta = new double[qtd_in + 1][qtd_hidden];
        double n = 0.3; // Taxa de aprendizado

        // Calcular o erro na camada de saída
        double[] erro_saida = new double[qtd_out];
        for (int k = 0; k < qtd_out; k++) {
            erro_saida[k] = (y[k] - o_out[k]) * o_out[k] * (1 - o_out[k]);
        }

        // Calcular o ajuste dos pesos da camada oculta para a saída
        for (int k = 0; k < qtd_out; k++) {
            for (int j = 0; j < o_hidden.length; j++) {
                dW_oculta_saida[j][k] = n * erro_saida[k] * o_hidden[j];
            }
        }

        // Calcular o erro na camada oculta
        double[] erro_oculta = new double[qtd_hidden];
        for (int j = 0; j < qtd_hidden; j++) {
            double sum = 0;
            for (int k = 0; k < qtd_out; k++) {
                sum += erro_saida[k] * pesosOcultaSaida[j + 1][k];
            }
            erro_oculta[j] = sum * o_hidden[j + 1] * (1 - o_hidden[j + 1]);
        }

        // Calcular o ajuste dos pesos da camada de entrada para a camada oculta
        for (int j = 0; j < qtd_hidden; j++) {
            for (int i = 0; i < xi.length; i++) {
                dW_entrada_oculta[i][j] = n * erro_oculta[j] * xi[i];
            }
        }

        // Atualizar os pesos da camada oculta para a saída
        for (int k = 0; k < qtd_out; k++) {
            for (int j = 0; j < o_hidden.length; j++) {
                pesosOcultaSaida[j][k] += dW_oculta_saida[j][k];
            }
        }

        // Atualizar os pesos da camada de entrada para a camada oculta
        for (int j = 0; j < qtd_hidden; j++) {
            for (int i = 0; i < xi.length; i++) {
                pesosEntradaOculta[i][j] += dW_entrada_oculta[i][j];
            }
        }

        return o_out;
    }
}
