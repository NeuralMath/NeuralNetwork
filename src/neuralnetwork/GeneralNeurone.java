package neuralnetwork;

public class GeneralNeurone extends Neurone{
    
    private double[] weights;
    
    public GeneralNeurone(int pos, double[] weight) {
        super( pos);
        
        weights = weight;
    }
    
    @Override
    public void addInputs(double input, int index) {
        inputValue += weights[index] * input;
    }
    
    @Override
    public void computes()
    {
        output = 1 / (1 + Math.exp(weights[position] * inputValue));
    }
}
