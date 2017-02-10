package neuralnetwork;

public class Reseau {
    
    private final InputNeurone[] inputLayer = new InputNeurone[4];
    private final GeneralNeurone[] hiddenLayer = new GeneralNeurone[3];
    private final GeneralNeurone[] outputLayer = new GeneralNeurone[2];
    
    private final Neurone[][] reseau = 
    {
        inputLayer,
        hiddenLayer,
        outputLayer
    };
    
    private final double[][] weights;

    public Reseau(double[][] weightsTemp) throws IllegalArgumentException{
        if(weightsTemp.length != reseau.length-1)
            throw new IllegalArgumentException("Le tableau de weight a pas la bonne grandeur");
        else
            for(int i = 0; i < reseau.length-1; i++)
                if(weightsTemp[i].length != reseau[i].length * reseau[i+1].length)
                    throw new IllegalArgumentException("Le tableau de weight a pas la bonne grandeur" + i);
                    
        weights = weightsTemp;
        
        for(int i = 0; i < reseau.length; i++)
        {
            for(int j = 0; j < reseau[i].length; j++)
            {
                if(i == 0)
                    reseau[i][j] = new InputNeurone(reseau[1].length, j);
                else
                    reseau[i][j] = new GeneralNeurone(j, weights[i-1]);
            }
        }
    }
    
    public void setInputs(double[] input) throws IllegalArgumentException
    {
        if(input.length != inputLayer.length)
            throw new IllegalArgumentException("Pas la bonne longueur de data");
        
        for(int i = 0 ; i < input.length; i++)
            inputLayer[i].setInputs(input[i]);
    }
    
    public int computes()
    {
        int posHighest = -1;
                
        for(int i = 0; i < reseau[0].length; i++)
        {
            reseau[0][i].computes();
            for(int j = 0; j < reseau[1].length; j++)
            {
                reseau[1][j].addInputs(reseau[0][i].getOutput(), i);
                reseau[1][j].computes();
                for(int k = 0; k < reseau[2].length; k++)
                {
                    reseau[2][k].addInputs(reseau[1][j].getOutput(), k);
                    reseau[2][k].computes();
                }
            }
        }
        
        for(int i = 0; i < reseau[2].length; i++)
        {
            if(reseau[2][i].getOutput() > posHighest)
                posHighest = i;
        }
        
        return posHighest;
    }
}
