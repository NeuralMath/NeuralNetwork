package neuralnetwork;

import java.io.IOException;
import java.util.Arrays;

public class NeuralNetwork {
   private static final int nbsData = 60000;

    private static final int INPUT = 784;
    private static final int HIDDEN = 100;
    private static final int OUTPUT = 10;
    private static final double TRAININGRATE = 0.005;    
    
    private static final String fileWeightsItoH = "weightsItoH.txt";
    private static final String fileWeightsHtoO = "weightsHtoO.txt";
    
    private static final int[][] result = new int[nbsData][OUTPUT];
    private static final int[][] trainingImage = new int[nbsData][];
    
    public static void main(String[] args) {
        
        boolean train = false;
        
        long nowTime = System.currentTimeMillis();
        try
        {
            //Network.resetDatas(INPUT, HIDDEN, OUTPUT, fileWeightsItoH, fileWeightsHtoO);
            Network n = new Network(INPUT, HIDDEN, OUTPUT, TRAININGRATE, fileWeightsItoH, fileWeightsHtoO);
            if(train)
            {
                MnistManager m = new MnistManager("C:/Users/Marc4492/Downloads/train-images.idx3-ubyte", "C:/Users/Marc4492/Downloads/train-labels.idx1-ubyte");
                for (int[] val : result)
                    Arrays.fill(val, 0);
                
                for(int i = 0; i < nbsData; i++)
                {
                    trainingImage[i] = m.readImage();
                    result[i][m.readLabel()] = 1;
                }
                
                n.train(trainingImage, result);
            }
            else
            {
                MnistManager m = new MnistManager("C:/Users/Marc4492/Downloads/t10k-images.idx3-ubyte", "C:/Users/Marc4492/Downloads/t10k-labels.idx1-ubyte");
                int nbsErreur = 0;
                int nbsDataTry = 1000;
                
                m.setCurrent(1);
                
                for(int i = 0; i < nbsDataTry; i++)
                    if(n.getAnwser(m.readImage()) != m.readLabel())
                        nbsErreur++;
                
                System.out.println(nbsErreur + " / " + nbsDataTry);
            }
        }
        catch(IOException ex)
        {
            System.err.println(ex.toString());
        }
        System.out.println("Time (ms) : " + (System.currentTimeMillis() - nowTime));
    }
}