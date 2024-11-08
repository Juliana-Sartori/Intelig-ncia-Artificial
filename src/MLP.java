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

        this.pesosEntradaOculta = new double[qtd_in + 1][qtd_hidden];
        this.pesosOcultaSaida = new double[qtd_hidden + 1][qtd_out];

        Random rand = new Random();

        for (int i = 0; i < pesosEntradaOculta.length; i++) {
            for (int j = 0; j < pesosEntradaOculta[i].length; j++) {
                pesosEntradaOculta[i][j] = -0.3 + (0.3 - (-0.3)) * rand.nextDouble();
            }
        }

        for (int i = 0; i < pesosOcultaSaida.length; i++) {
            for (int j = 0; j < pesosOcultaSaida[i].length; j++) {
                pesosOcultaSaida[i][j] = -0.3 + (0.3 - (-0.3)) * rand.nextDouble();
            }
        }
    }

    public double[] treinar(double[] x_in, double[] y) {

        double[] xi = new double[x_in.length + 1];
        xi[0] = 1;
        for (int i = 0; i < x_in.length; i++) {
            xi[i + 1] = x_in[i];
        }

        double[] u_hidden = new double[qtd_hidden];
        double[] o_hidden = new double[qtd_hidden + 1];
        o_hidden[0] = 1;

        for (int j = 0; j < qtd_hidden; j++) {
            for (int i = 0; i < xi.length; i++) {
                u_hidden[j] += xi[i] * pesosEntradaOculta[i][j];
            }
            o_hidden[j + 1] = 1 / (1 + Math.exp(-u_hidden[j]));
        }

        double[] u_out = new double[qtd_out];
        double[] o_out = new double[qtd_out];

        for (int k = 0; k < qtd_out; k++) {
            for (int j = 0; j < o_hidden.length; j++) {
                u_out[k] += o_hidden[j] * pesosOcultaSaida[j][k];
            }
            o_out[k] = 1 / (1 + Math.exp(-u_out[k]));
        }

        double[][] dW_oculta_saida = new double[qtd_hidden + 1][qtd_out];
        double[][] dW_entrada_oculta = new double[qtd_in + 1][qtd_hidden];
        double n = 0.03; // Taxa de aprendizado

        double[] erro_saida = new double[qtd_out];
        for (int k = 0; k < qtd_out; k++) {
            erro_saida[k] = (y[k] - o_out[k]) * o_out[k] * (1 - o_out[k]);
        }

        for (int k = 0; k < qtd_out; k++) {
            for (int j = 0; j < o_hidden.length; j++) {
                dW_oculta_saida[j][k] = n * erro_saida[k] * o_hidden[j];
            }
        }

        double[] erro_oculta = new double[qtd_hidden];
        for (int j = 0; j < qtd_hidden; j++) {
            double sum = 0;
            for (int k = 0; k < qtd_out; k++) {
                sum += erro_saida[k] * pesosOcultaSaida[j + 1][k];
            }
            erro_oculta[j] = sum * o_hidden[j + 1] * (1 - o_hidden[j + 1]);
        }

        for (int j = 0; j < qtd_hidden; j++) {
            for (int i = 0; i < xi.length; i++) {
                dW_entrada_oculta[i][j] = n * erro_oculta[j] * xi[i];
            }
        }

        for (int k = 0; k < qtd_out; k++) {
            for (int j = 0; j < o_hidden.length; j++) {
                pesosOcultaSaida[j][k] += dW_oculta_saida[j][k];
            }
        }

        for (int j = 0; j < qtd_hidden; j++) {
            for (int i = 0; i < xi.length; i++) {
                pesosEntradaOculta[i][j] += dW_entrada_oculta[i][j];
            }
        }

        return o_out;
    }
}
