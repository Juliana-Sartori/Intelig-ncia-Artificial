import java.util.Random;

public class Perceptron {

    private int qtd_in;
    private int qtd_out;

    private double[][] pesos;

    public Perceptron(int qtd_in, int qtd_out){
        this.qtd_in = qtd_in;
        this.qtd_out = qtd_out;
        this.pesos = new double[qtd_in+1][qtd_out];

        Random rand = new Random();
        for (int i = 0; i < pesos.length; i++) {
            for (int j = 0; j < pesos[i].length; j++) {
                pesos[i][j] = -0.3 + (0.3 - (-0.3)) * rand.nextDouble();
            }
        }
    }

    public double[] treinar (double [] x_in, double [] y){
        double [] xi = new double[x_in.length + 1];

        xi[0] = 1;

        for (int i = 0; i < x_in.length; i++){
            xi[i+1] = x_in[i];
        }

        double [] u = new double[y.length];
        double [] o = new double[y.length];

        for (int j = 0; j< y.length; j++){

            for (int i = 0; i < xi.length; i++){
                u[j] = u[j] + ( xi[i] * this.pesos[i][j]);
            }

            o[j] = 1/(1+Math.exp(-u[j]));
        }

        double [][] dW = new double[qtd_in+1][qtd_out];
        double n = 0.3;

        for(int j = 0; j< y.length; j++){
            for(int i = 0; i< xi.length; i++){
                dW[i][j] = n * (y[j] - o[j]) * xi[i];
            }
        }

        for(int j = 0; j< y.length; j++){
            for(int i = 0; i< xi.length; i++){
                this.pesos [i][j] = this.pesos[i][j] + dW[i][j];
            }
        }
        return o;
    }
}
