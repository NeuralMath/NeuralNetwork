package neuralnetwork;

public class InputNeurone extends Neurone {
        
    public InputNeurone(int nbsOutput, int pos) {
        super(pos);
    }
    
    public void setInputs(double input){
            inputValue = input;
    }

    @Override
    public void computes() {
        output = 1 / (1 + Math.exp(inputValue));
    }
    
    @Override
    public void addInputs(double input, int index) {
        inputValue = input;
    }
}
