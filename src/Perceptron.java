import java.util.Random;

public class Perceptron {

    //qtd de neuronios de entrada e saida
    private int qtd_in;
    private int qtd_out;

    //pesos, com o numero de linahs de entradas+1, esse +1 eh o beas e o numero de colunas eh o nuero de neuronios
    private double[][] pesos;

    //construtor
    public Perceptron(int qtd_in, int qtd_out){
        this.qtd_in = qtd_in;
        this.qtd_out = qtd_out;
        this.pesos = new double[qtd_in+1][qtd_out];

//---------------------------        //gerar valores aleatorios para a matriz de pesos : com valores ntre -0.3 - 0.3
        Random rand = new Random();
        for (int i = 0; i < pesos.length; i++) {
            for (int j = 0; j < pesos[i].length; j++) {
                pesos[i][j] = -0.3 + (0.3 - (-0.3)) * rand.nextDouble();
            }
        }

    }

    //entrada da amostra xi (x_in), saida desejada yi
    public double[] treinar (double [] x_in, double [] y){
        double [] xi = new double[x_in.length + 1];
        //beas
        xi[0] = 1;

        for (int i = 0; i < x_in.length; i++){
            xi[i+1] = x_in[i];
        }
//---------------------------          //em duvida se esse u ta certo
        double [] u = new double[y.length];
        double [] o = new double[y.length];
//---------------------------  u.length ??
        //esse for fiz o 1° neuronio
        for (int j = 0; j< u.length; j++){
            //esse for percorre as linhas
            for (int i = 0; i < xi.length; i++){
                u[j] = u[j] + ( xi[i] * this.pesos[i][j]);
            }
            //funcao de ativacao (sigmoidal)
            o[j] = 1/(1+Math.exp(-u[j]));
        }

        //calculando o deltaW
        double [][] dW = new double[qtd_in+1][qtd_out];
        double n = 0.3;

        for(int j = 0; j< y.length; j++){
            for(int i = 0; i< x_in.length; i++){
                dW[i][j] = n * (y[j] - o[j]) * xi[i];

            }
        }

        //atualizacao dos pesos
        for(int j = 0; j< y.length; j++){
            for(int i = 0; i< x_in.length; i++){
                this.pesos [i][j] = this.pesos[i][j] + dW[i][j];

            }
        }

        return o;
    }
}
