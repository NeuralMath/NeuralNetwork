package neuralnetwork;

public abstract class Neurone {
    
    protected double inputValue = 0;
    protected double output;
    protected int position;
        
    public Neurone(int pos) {
        position = pos;
    }

    public double getOutput() {
        return output;
    }

    public abstract void addInputs(double input, int index);
    
    public abstract void computes();
    
    public void sendValue(Neurone[] goalNeuronne)
    {
        for (Neurone n : goalNeuronne)
            n.addInputs(output, position);
    }
}
