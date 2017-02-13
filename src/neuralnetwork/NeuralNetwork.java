package neuralnetwork;

public class NeuralNetwork {
      
    private static double[][][] weight = 
    {
        {
            {1, 1, 1},
            {1, 1, 1},
            {1, 1, 1},
            {1, 1, 1}
        },
        {
            {1, 1},
            {1, 1},
            {1, 1}
        }
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