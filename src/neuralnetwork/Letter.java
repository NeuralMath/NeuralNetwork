package neuralnetwork;

/**
 *
 * @author Marc4492
 */
public class Letter {
    private int[] array;
    private int[] results;
    private final int squareNumber;
    
    public Letter(int pix, int letter, int output)
    {
        array = new int[pix];
        squareNumber = (int) Math.sqrt((double) pix);
        
        for(int i = 0; i < array.length; i++)
            array[i] = 0;
        
        results = new int[output];
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
            array[i] = 1;
        
        if(array.length%2 == 0)
        {
            for(int i = squareNumber/2 - 1; i < array.length; i += squareNumber)
            {
                array[i] = 1;
                array[i + 1] = 1;
            }
        }
        else
            for(int i = (int) Math.floor((squareNumber)/2); i < array.length; i += squareNumber)
                array[i] = 1;
            
                
    }
    
    private void createH()
    {
        for(int i = 0; i < array.length; i += squareNumber)
            array[i] = 1;
        
        for(int i = squareNumber - 1; i < array.length; i += squareNumber)
            array[i] = 1;
        
        if(array.length%2 == 0)
        {
            int debutMiddle = squareNumber - 1;
            for(int i = debutMiddle; i < debutMiddle + squareNumber; i++)
            {
                array[i] = 1;
                array[i + squareNumber] = 1;
            }
        }
        else
        {
            int debutMiddle = (int) (squareNumber * Math.floor((squareNumber)/2));
            for(int i = debutMiddle; i < debutMiddle + squareNumber; i++)
                array[i] = 1;
        }
    }
    
    private void createC()
    {
        for(int i = 0; i < squareNumber; i++)
            array[i] = 1;
        
        for(int i = array.length - squareNumber; i < array.length; i++)
            array[i] = 1;
        
        for(int i = 0; i < array.length; i += squareNumber)
            array[i] = 1;
    }
    
    private void createO()
    {
        for(int i = 0; i < squareNumber; i++)
            array[i] = 1;
        
        for(int i = array.length - squareNumber; i < array.length; i++)
            array[i] = 1;
        
        for(int i = 0; i < array.length; i += squareNumber)
            array[i] = 1;
        
        for(int i = squareNumber-1; i < array.length; i += squareNumber)
            array[i] = 1;
    }
    public int[] getArray() {
        return array;
    }
    
    public int[] getResults()
    {
        return results;
    }
}