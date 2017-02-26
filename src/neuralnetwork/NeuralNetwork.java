package neuralnetwork;

import java.io.IOException;

public class NeuralNetwork {
    private static final double offRange = 0.25;
    private static final double onRange = 0.75;

    private static final int INPUT = 9;
    private static final int HIDDEN = 3;
    private static final int OUTPUT = 2;
    private static final double TRAININGRATE = 0.005;    
    
    private static final String fileWeightsItoH = "weightsItoH.txt";
    private static final String fileWeightsHtoO = "weightsHtoO.txt";
    private static final String fileBias = "bias.txt";
    
    private static final double[][] reslut = new double[500000][OUTPUT];
    private static final double[][] training = new double[500000][INPUT];
    
    public static void main(String[] args) {
        
        boolean train = false;
        
        long nowTime = System.currentTimeMillis();
        try
        {
            //Network.resetDatas(INPUT, HIDDEN, OUTPUT, fileWeightsItoH, fileWeightsHtoO, fileBias);
            Network n = new Network(INPUT, HIDDEN, OUTPUT, TRAININGRATE, fileWeightsItoH, fileWeightsHtoO, fileBias);
                      
            if(train)
            {
                for(int i = 0; i < training.length; i+=2)
                {
                    training[i] = new Letter(INPUT, offRange, onRange, "H").getArray();
                    reslut[i] = new double[] { 1, 0/* , 0 */};
                    training[i+1] = new Letter(INPUT, offRange, onRange, "T").getArray();
                    reslut[i+1] = new double[] { 0, 1/* , 0 */};
                    //training[i+2] = new Letter(INPUT, offRange, onRange, "C").getArray();
                    //reslut[i+2] = new double[] { 0, 0 , 1 };
                }

                n.train(training, reslut);
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
                    //if(n.getAnwser(new Letter(INPUT, offRange, onRange, "C").getArray()) != 2)
                        //nbsErreur++;
                }
                System.out.println("Data : " + 2*nbsData);
                System.out.println("Réussite : " + (2*nbsData - nbsErreur));
            }
        }
        catch(IOException ex)
        {
            System.err.println(ex.toString());
        }
        System.out.println("Time (ms) : " + (System.currentTimeMillis() - nowTime));
    }
}