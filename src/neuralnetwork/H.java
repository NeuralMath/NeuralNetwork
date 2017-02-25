/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neuralnetwork;

/**
 *
 * @author Marc4492
 */
public class H {
    private double[] array = new double[9];
    
    public H()
    {
        array[0] = ONPix();
        array[1] = OFFPix();
        array[2] = ONPix();
        
        array[3] = ONPix();
        array[4] = ONPix();
        array[5] = ONPix();
        
        array[6] = ONPix();
        array[7] = OFFPix();
        array[8] = ONPix();
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
