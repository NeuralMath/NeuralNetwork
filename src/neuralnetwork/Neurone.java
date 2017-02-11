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
    private final double[] weights;
    
    /**
     * Constructeur pour les neurones sans weight (input layer)
     * 
     * @param pos Position de la neurone dans le layer quel appartient
     */
    public Neurone(int pos) {
        position = pos;
        weights = null;
    }
    
    /**
     * Constructeur pour les neurones avec weight (input layer)
     * 
     * @param pos       Position de la neurone dans le layer quel appartient
     * @param weight    Tableau des weights de toutes les liaisons en amont de la neurone
     */
    public Neurone(int pos, double[] weight) {
        position = pos;
        weights = weight;
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
     * @param index     L'index de la neurone qui envoie ce input pour trouver le weight
     */
    public void addInputs(double input, int index)
    {
        if(weights == null)
            inputValue += input;
        else
            inputValue += weights[index] * input;
    }
    
    /**
     * Calculer la valeur de l'output
     */
    public void computes() {
        output = 1 / (1 + Math.exp(inputValue));
    }
}
