package neuralnetwork;

import java.io.IOException;

public class NeuralNetwork {
    private static final double offRange = 0.4;
    private static final double onRange = 0.6;

    private static final int INPUT = 81;
    private static final int HIDDEN = 10;
    private static final int OUTPUT = 4;
    private static final double TRAININGRATE = 0.005;    
    
    private static final String fileWeightsItoH = "weightsItoH.txt";
    private static final String fileWeightsHtoO = "weightsHtoO.txt";
    
    private static final double[][] result = new double[80000][OUTPUT];
    private static final double[][] training = new double[80000][INPUT];
    
    public static void main(String[] args) {
        
        boolean train = false;
        
        long nowTime = System.currentTimeMillis();
        try
        {
            //Network.resetDatas(INPUT, HIDDEN, OUTPUT, fileWeightsItoH, fileWeightsHtoO);
            Network n = new Network(INPUT, HIDDEN, OUTPUT, TRAININGRATE, fileWeightsItoH, fileWeightsHtoO);
            
            if(train)
            {
                for(int i = 0; i < training.length; i++)
                {
                    Letter l = new Letter(INPUT, offRange, onRange, i%4, OUTPUT);
                    training[i] = l.getArray();
                    result[i] = l.getResults();
                }
                n.train(training, result);
            }
            else
            {
                int nbsErreur = 0;
                int nbsData = 40000;

                for(int i = 0; i < nbsData; i++)
                    if(n.getAnwser(new Letter(INPUT, offRange, onRange, i%4, OUTPUT).getArray()) != i%4)
                        nbsErreur++;
                
                System.out.println("Data : " + nbsData);
                System.out.println("Réussite : " + (nbsData - nbsErreur));
            }
        }
        catch(IOException ex)
        {
            System.err.println(ex.toString());
        }
        System.out.println("Time (ms) : " + (System.currentTimeMillis() - nowTime));
    }
}