package neuralnetwork;

public class NeuralNetwork {
      
    private static double[][][] weight = 
    {
        {
            {1, 1, 1},
            {1, 1, 1},
            {1, 1, 1},
            {1, 1, 1},
            {1, 1, 1},
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
    
    /*private static double[] input = 
    {
        1,
        1,
        1, 
        1
    };*/
    
    static double[][] training = 
    {
        {
            1,
            1,
            1,
            0,
            1,
            0,
            0,
            1,
            0
        },
        {
            1,
            0,
            1,
            1,
            1,
            1,
            1,
            0,
            1
        },
        {
            1,
            0,
            1,
            0,
            1,
            0,
            0,
            1,
            0
        }
    };
    
    static double[] results = 
    {
       1,
       0,
       1
    };
    
    static double[] testInput = 
    {
        1,
        1,
        0,
        0,
        1,
        0,
        0,
        1,
        0
    };
    
    public static void main(String[] args) {
        try
        {
            Reseau r = new Reseau(weight);            
            r.train(training, results);
            r.setInputs(testInput);
            System.out.println(r.computes());
        }
        catch(IllegalArgumentException ex)
        {
            System.out.println(ex.toString());
        }
    }
    
}