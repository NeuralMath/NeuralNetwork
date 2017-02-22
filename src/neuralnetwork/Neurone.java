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
    private final double bias;
    
    /**
     * Constructeur pour les neurones
     * 
     * @param pos               Position de la neurone dans le layer quel appartient
     * @param biasTemp          Valeur du bias de la neuronne
     */
    public Neurone(int pos, double biasTemp) {
        position = pos;
        bias = biasTemp;
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
    public void addInputs(double input) {
        inputValue += input;
    }
    
    /**
     * Reset l'input de la neuronne
     */
    public void removeInputs() {
        inputValue = 0;
    }
    
    /**
     * Calculer la valeur de l'output
     * 
     * @return La valeur resultante de la neuronne
     */
    public double computes() {
        output = 1 / (1 + Math.exp(-(inputValue-bias)));
        
        return output;
    }
}
