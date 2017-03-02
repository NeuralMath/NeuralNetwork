package neuralnetwork;

import java.io.IOException;

public class NeuralNetwork {
    private static final double offRange = 0.4;
    private static final double onRange = 0.6;

    private static final int INPUT = 100;
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
                for(int i = 0; i < training.length-1; i+=4)
                {
                    training[i] = new Letter(INPUT, offRange, onRange, "H").getArray();
                    result[i] = new double[] { 1, 0 , 0, 0 };
                    training[i+1] = new Letter(INPUT, offRange, onRange, "T").getArray();
                    result[i+1] = new double[] { 0, 1 , 0, 0 };
                    training[i+2] = new Letter(INPUT, offRange, onRange, "C").getArray();
                    result[i+2] = new double[] { 0, 0 , 1, 0 };
                    training[i+3] = new Letter(INPUT, offRange, onRange, "O").getArray();
                    result[i+3] = new double[] { 0, 0, 0 , 1 };
                }
                n.train(training, result);
            }
            else
            {
                int nbsErreur = 0;
                int nbsData = 10000;

                for(int i = 0; i < nbsData; i++)
                {
                    if(n.getAnwser(new Letter(INPUT, offRange, onRange, "H").getArray()) != 0)
                        nbsErreur++;
                    if(n.getAnwser(new Letter(INPUT, offRange, onRange, "T").getArray()) != 1)
                        nbsErreur++;
                    if(n.getAnwser(new Letter(INPUT, offRange, onRange, "C").getArray()) != 2)
                        nbsErreur++;
                    if(n.getAnwser(new Letter(INPUT, offRange, onRange, "O").getArray()) != 3)
                        nbsErreur++;
                }
                
                System.out.println("Data : " + OUTPUT*nbsData);
                System.out.println("Réussite : " + (OUTPUT*nbsData - nbsErreur));
            }
        }
        catch(IOException ex)
        {
            System.err.println(ex.toString());
        }
        System.out.println("Time (ms) : " + (System.currentTimeMillis() - nowTime));
    }
}