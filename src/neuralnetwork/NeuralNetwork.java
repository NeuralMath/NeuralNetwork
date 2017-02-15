package neuralnetwork;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class NeuralNetwork {
    
    private static double[][] weightsItoH = 
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
    };
    
    private static double[][] weightsHtoO = 
    {
        {1, 1},
        {1, 1},
        {1, 1},
    };
    
    
    
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
            Reseau r = new Reseau(9, 3, 2, 0.001, weightsItoH, weightsHtoO);            
            r.train(training, results);
            r.setInputs(testInput);
            System.out.println(r.computes());
        }
        catch(IllegalArgumentException ex)
        {
            System.out.println(ex.toString());
        }
    }
    
    public static void writeWeigths(String filePath, double[][] array) throws IOException
    {
        File file = new File(filePath);
        if(!file.exists())
            file.createNewFile();
        
        FileOutputStream writer = new FileOutputStream(file, false);
               
        
        StringBuilder sb = new StringBuilder();
        for(double[] values : array)
            sb.append(Arrays.toString(values)).append(System.lineSeparator());

        writer.write(sb.toString().getBytes());
        writer.close();
    }
    
    public static void readWeights(String filePath, double[][] array) throws IOException, NumberFormatException
    {
        File file = new File(filePath);
        if(!file.exists())
            throw new IOException("The file isn't valid");
        
        FileInputStream reader = new FileInputStream(file);
        
        StringBuilder sb = new StringBuilder();
        
        int content;
        while((content = reader.read()) != -1)
            sb.append((char) content);
        
        String[] lines = sb.toString().split(System.lineSeparator());
        String[][] linesElements = new String[lines.length][];
        
        if(lines.length != array.length)
            throw new IOException("Pas le bon nombre de lignes dans le ficher.");
        
        for(int i = 0; i < lines.length; i++)
            linesElements[i] = lines[i].trim().replace("[", "").replace("]", "").split(",");
        
        for(int i = 0; i < lines.length; i++)
            for(int j = 0; j < linesElements[i].length; j++)
                array[i][j] = Double.parseDouble(linesElements[i][j]);
    }
}