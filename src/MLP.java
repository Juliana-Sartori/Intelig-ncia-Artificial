import java.util.Random;

public class MLP {

    private int qtd_in;
    private int qtd_out;
    private int qtd_H;

   //*** private double[][] pesos;

    private double ni;
    private double[][] vetWH, vetWo;

    public MLP(int qtd_in, int qtd_out, int qtd_H, double ni){
        this.qtd_in = qtd_in;
        this.qtd_out = qtd_out;
        this.qtd_H = qtd_H;
        this.ni = ni;

        this.vetWH = new double[qtd_in+1][qtd_H];
        this.vetWo = new double[qtd_H+1][qtd_out];
     //*   this.pesos = new double[qtd_in+1][qtd_out];

        //--*ACHO QUE ESSE AQUI TEM Q TER, SO QUE COM WH E WO
     //--*   Random rand = new Random();
     //--*   for (int i = 0; i < pesos.length; i++) {
        //--*       for (int j = 0; j < pesos[i].length; j++) {
        //--*    pesos[i][j] = -0.3 + (0.3 - (-0.3)) * rand.nextDouble();
        //--*}
        //--*}
    }

    public double[] treinar (double [] x_in, double [] y){
        double [] xi = new double[x_in.length + 1];

        xi[0] = 1;

        for (int i = 0; i < x_in.length; i++){
            xi[i+1] = x_in[i];
        }

        //--*   double [] u = new double[y.length];
        //--* double [] o = new double[y.length];

        double [] H = new double[qtd_H+1];
        H[qtd_H] =1;

        //--*
        /*
        for (int j = 0; j< y.length; j++){

            for (int i = 0; i < xi.length; i++){
                u[j] = u[j] + ( xi[i] * this.pesos[i][j]);
            }

            o[j] = 1/(1+Math.exp(-u[j]));
        }*/

        for (int j = 0; j< qtd_H; j++){
            double soma =0;

            for (int i = 0; i < xi.length; i++){
                soma += xi[i] * vetWH[i][j];
            }

           H[j] = 1/(1+Math.exp(-soma));
        }

        double [] out = new double[qtd_out];

        for(int j = 0; j< out.length; j++){
            double soma = 0;

            for(int i = 0; i< xi.length; i++){
                soma += H[i] * vetWo[i][j];
            }
            out[j] = 1/(1+Math.exp(-soma));
        }

        double [] deltaO = new double[qtd_out];

        for(int j = 0; j< qtd_out; j++){
            deltaO[j] = out[j] * (1 - out[j]) * (y[j] - out[j]);
        }

        double [] deltaH = new double[qtd_H];

        for(int i = 0; i< qtd_H; i++){
            double soma = 0;

            for(int j = 0; j< qtd_out; j++){
                soma += deltaO[j] * vetWo[i][j];
            }
            deltaH[i] = H[i] * (1 - H[i]) * soma;
        }

        for (int i = 0; i< xi.length; i++){
            for (int j = 0; j< qtd_H; j++){
               vetWH[i][j] = vetWH[i][j] + ni * deltaH[j] * xi[i];
            }
        }

        for (int i = 0; i< qtd_H+1; i++){
            for (int j = 0; j< qtd_out; j++){
                vetWo[i][j] += ni * deltaO[j] * H[i];
            }
        }
        return out;
    }
}
