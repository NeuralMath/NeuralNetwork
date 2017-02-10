package neuralnetwork;

public class NeuralNetwork {
      
    private static double[][] weight = 
    {
        {-0.25, 0.5, -0.75, 0.0, -0.25, 0.5, -0.75, 1.0, -0.6, 1.03, -1.0, 1.65},
        {1.75, 1.5, 1.0, 1.5, 1.25, 1.2}
    };
    
    private static double[] input = 
    {
        1,
        1,
        1, 
        1
    };
    
    public static void main(String[] args) {
        try
        {
            Reseau r = new Reseau(weight);
            r.setInputs(input);
            System.out.println(r.computes());
        }
        catch(IllegalArgumentException ex)
        {
            System.out.println(ex.toString());
        }
    }
    
}