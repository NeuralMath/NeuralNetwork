package neuralnetwork;

import java.io.IOException;

public class NeuralNetwork {

    private static final int INPUT = 9;
    private static final int HIDDEN = 3;
    private static final int OUTPUT = 2;
    private static final double TRAININGRATE = 0.001;    
    
    private static final String fileWeightsItoH = "weightsItoH.txt";
    private static final String fileWeightsHtoO = "weightsHtoO.txt";
    private static final String fileBias = "bias.txt";
    
    private static final double[][] reslut = new double[1000000][OUTPUT];
    private static final double[][] training = new double[1000000][INPUT];
    
    public static void main(String[] args) {
        long nowTime = System.currentTimeMillis();
        try
        {
            Network n = new Network(INPUT, HIDDEN, OUTPUT, TRAININGRATE, fileWeightsItoH, fileWeightsHtoO, fileBias);
            
            for(int i = 0; i < training.length; i+=2)
            {
                training[i] = new T().getArray();
                reslut[i] = new double[] { 0, 1 };
                training[i+1] = new H().getArray();
                reslut[i+1] = new double[] { 1, 0 };
            }
            
            n.train(training, reslut);
            
            int nbsErreur = 0;
            int nbsData = 100000;
            
            for(int i = 0; i < nbsData; i++)
            {
                n.setInputs(new H().getArray());
                if(n.getAnwser() != 0)
                    nbsErreur++;
                n.setInputs(new T().getArray());
                if(n.getAnwser() != 1)
                    nbsErreur++;
            }
            System.out.println("Data : " + nbsData);
            System.out.println("Erreurs : " + nbsErreur);
        }
        catch(IOException ex)
        {
            System.err.println(ex.toString());
        }
        
        System.out.println("Time (ms) : " + (System.currentTimeMillis() - nowTime));
    }
}