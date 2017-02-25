package neuralnetwork;

public class T {
    private double[] array = new double[9];
    
    public T()
    {
        array[0] = ONPix();
        array[1] = ONPix();
        array[2] = ONPix();
        
        array[3] = OFFPix();
        array[4] = ONPix();
        array[5] = OFFPix();
        
        array[6] = OFFPix();
        array[7] = ONPix();
        array[8] = OFFPix();
    }
    
    public final double ONPix()
    {
        return 0.75 + Math.random() * 0.25;
    }
    
    public final double OFFPix()
    {
        return Math.random() * 0.25;
    }

    public double[] getArray() {
        return array;
    }
}
