package neuralnetwork;

/**
 * Class qui contient l'exécution d'une neurone selon les input(avec les weights associé) 
 * 
 * @author Marc4492
 * 10 février 2017
 */
public class Neurone {
    
    private double inputValue = 0;
    private double output;
    private final int position;
    
    /**
     * Constructeur pour les neurones sans weight (input layer)
     * 
     * @param pos Position de la neurone dans le layer quel appartient
     */
    public Neurone(int pos) {
        position = pos;
    }

    /**
     * Obtenir l'output de la neurone
     * 
     * @return  L'output
     */
    public double getOutput() {
        return output;
    }

    /**
     * Ajouté un input à la neurone
     * 
     * @param input     La valeur de l'input
     */
    public void addInputs(double input)
    {
        inputValue += input;
    }
    
    /**
     * Calculer la valeur de l'output
     */
    public void computes() {
        output = 1 / (1 + Math.exp(-inputValue));
    }
}
