package neuralnetwork;

/**
 *
 * @author Marc4492
 */
public class Letter {
    private double[] array;
    private double[] results;
    
    private final double offRange;
    private final double onRange;
    private final int squareNumber;
    
    public Letter(int pix, double off, double on, int letter, int output)
    {
        array = new double[pix];
        offRange = off;
        onRange = on;
        squareNumber = (int) Math.sqrt((double) pix);
        
        for(int i = 0; i < array.length; i++)
            array[i] = OFFPix();
        
        results = new double[output];
        results[letter] = 1;
        
        switch (letter) {
        case 0:
            createT();
            break;
        case 1:
            createH();
            break;
        case 2:
            createC();
            break;
        case 3:
            createO();
            break;
        default:
            break;
    }
    }
    
    private void createT()
    {
        for(int i = 0; i < squareNumber; i++)
            array[i] = ONPix();
        
        if(array.length%2 == 0)
        {
            for(int i = squareNumber/2 - 1; i < array.length; i += squareNumber)
            {
                array[i] = ONPix();
                array[i + 1] = ONPix();
            }
        }
        else
            for(int i = (int) Math.floor((squareNumber)/2); i < array.length; i += squareNumber)
                array[i] = ONPix();
            
                
    }
    
    private void createH()
    {
        for(int i = 0; i < array.length; i += squareNumber)
            array[i] = ONPix();
        
        for(int i = squareNumber - 1; i < array.length; i += squareNumber)
            array[i] = ONPix();
        
        if(array.length%2 == 0)
        {
            int debutMiddle = squareNumber - 1;
            for(int i = debutMiddle; i < debutMiddle + squareNumber; i++)
            {
                array[i] = ONPix();
                array[i + squareNumber] = ONPix();
            }
        }
        else
        {
            int debutMiddle = (int) (squareNumber * Math.floor((squareNumber)/2));
            for(int i = debutMiddle; i < debutMiddle + squareNumber; i++)
                array[i] = ONPix();
        }
    }
    
    private void createC()
    {
        for(int i = 0; i < squareNumber; i++)
            array[i] = ONPix();
        
        for(int i = array.length - squareNumber; i < array.length; i++)
            array[i] = ONPix();
        
        for(int i = 0; i < array.length; i += squareNumber)
            array[i] = ONPix();
    }
    
    private void createO()
    {
        for(int i = 0; i < squareNumber; i++)
            array[i] = ONPix();
        
        for(int i = array.length - squareNumber; i < array.length; i++)
            array[i] = ONPix();
        
        for(int i = 0; i < array.length; i += squareNumber)
            array[i] = ONPix();
        
        for(int i = squareNumber-1; i < array.length; i += squareNumber)
            array[i] = ONPix();
    }
    
    private double ONPix()
    {
        return onRange + Math.random() * (1 - onRange);
    }
    
    private double OFFPix()
    {
        return Math.random() * offRange;
    }

    public double[] getArray() {
        return array;
    }
    
    public double[] getResults()
    {
        return results;
    }
}