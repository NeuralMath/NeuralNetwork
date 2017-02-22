package neuralnetwork;

/**
 * Class qui contient le réseau de neurones et qui peut obtenir la valeur de sortie du réseau
 * 
 * @author Marc4492
 * 10 février 2017
 */
public class Reseau {
    
    private final double[][] weightsItoH;
    private final double[][] weightsHtoO;
    private final double[][] bias;
    
    private double[] inputValues;
    private final Neurone[][] reseau;
    
    private final double trainingRate;

    /**
     * Constructeur du réseau, création des neurones
     * 
     * @param inputLayer                        Nombre de neurons dans la première couche
     * @param hiddenLayer                       Nombre de neurons dans la deuxième couche
     * @param outputLayer                       Nombre de neurons dans la troisième couche    
     * @param training                          Vitesse de l'apprentisage
     * @param bias                              Tableau des bias des neuronnes du 2 et 3 étages
     * @param weightsItoHTemp                   Le tableau des weight des liaison l'input layer et le hidden
     * @param weightsHtoOTemp                   Le tableau des weight des liaison le hidden layer et le output
     * @throws IllegalArgumentException     Si le tableau de weight n'a pas les bonne grandeur
     */
    public Reseau(int inputLayer, int hiddenLayer, int outputLayer, double training, double[][] biasTemp, double[][] weightsItoHTemp, double[][] weightsHtoOTemp) {
        weightsItoH = weightsItoHTemp;
        weightsHtoO = weightsHtoOTemp;
        bias = biasTemp;
        trainingRate = training;
        
        inputValues = new double[inputLayer];
        
        //Création de la première dimmension
        reseau = new Neurone[][] 
                {
                    new Neurone[hiddenLayer],
                    new Neurone[outputLayer]
                };
        
        //Création des neuronnes avec leurs bias
        for (int i = 0; i < reseau.length; i++)
            for (int j = 0; j < reseau[i].length; j++)
                reseau[i][j] = new Neurone(j, bias[i][j]);
    }
    
    /**
     * Pour entrer les entrées des neurones du premier layer
     * 
     * @param input                         Le tableau de double pour les entrées
     * @throws IllegalArgumentException     S'il n'y a pas le bon nombre de données
     */
    public void setInputs(double[] input) throws IllegalArgumentException {
        if(input.length != inputValues.length)
            throw new IllegalArgumentException("Pas la bonne longueur de data");
        
        System.arraycopy(input, 0, inputValues, 0, input.length);
    }
    
    /**
     * Efface les inputs de l'étage d'éentrée
     */
    public void removeAllInputs() {
        inputValues = new double[inputValues.length];
    }
    
    /**
     * Exectuer le réseau de neurones
     * 
     * @return Retourne la position de la neurone avec le plus grande valeur de sorite
     */
    public int computes() {
        int posMax = 0;
        
        //Exécuter les neurones du premier étage et passer les valeurs à l'autre couche
        for(int i = 0; i < inputValues.length; i++)
            for(int j = 0; j < reseau[1].length; j++)
                reseau[0][j].addInputs(inputValues[i]*weightsItoH[i][j]);
        
        //Exécuter les neurones du deuxième étage et passer les valeurs à l'autre couche
        for(int i = 0; i < reseau[0].length; i++)
            for(int j = 0; j < reseau[1].length; j++)
                reseau[1][j].addInputs(reseau[0][i].computes()*weightsHtoO[i][j]);
        
        //Exécuter les neurones du dernier étage
        for(int i = 0; i < reseau[1].length; i++)
            reseau[1][i].computes();
        
        //Trouver la position de la plus grande valeur d'output
        for(int i = 0; i < reseau[1].length; i++)
            if(reseau[1][i].getOutput() > reseau[1][posMax].getOutput())
                posMax = i;
        
        return posMax;
    }
    
    /**
     * Entrainer le réseau
     * 
     * @param trainingSet   Les valeurs de test, avec tous les exemples
     * @param resultat      Les résultats de chaque exemple
     */
    public void train(double[][] trainingSet, double[][] resultat) throws IllegalArgumentException {
        for (int i = 0; i < trainingSet.length; i++) {
            //Effacer les valeur existante
            removeAllInputs();
            //Set les inputs
            setInputs(trainingSet[i]);
            //Calculer la valeur
            computes();
            //Effectué l'apprentisage
            SGDBiasOutput(inputValues);
            SGDWeightHiddenToOutput(resultat[i]);
            SGDBiasHidden(inputValues);
            SGDWeightInputToHidden(resultat[i]);
        }
    }
    
    /**
     * Modifier les valeur des poids entre l'hidden et le output layer
     * 
     * @param resultat      Les valeurs 
     */
    public void SGDWeightHiddenToOutput(double[] resultat) {
        double valOut;
        double valHidden;
        
        for(int i = 0; i < reseau[0].length; i++)
        {
            valHidden = reseau[0][i].getOutput();
            for(int j = 0; j < reseau[1].length; j++)
            {
                valOut = reseau[1][j].getOutput();
                weightsHtoO[i][j] -= trainingRate * (valOut - resultat[j]) * valOut * (1 - valOut) * valHidden;
            }
        }
    }
    
    /**
     * Modifier les valeur des poids entre l'input et le hidden layer
     * 
     * @param resultat      Les valeurs 
     */
    public void SGDWeightInputToHidden(double[] resultat) {
        double somme = 0;
        double valInput;
        double valHidden;
        double valOut;
                
        for(int k = 0; k < inputValues.length; k++)
        {
            valInput = inputValues[k];
            
            for(int i = 0; i < reseau[0].length; i++)
            {
                valHidden = reseau[0][i].getOutput();
                
                for(int j = 0; j < reseau[1].length; j++)
                {
                    valOut = reseau[1][j].getOutput();
                    somme += ((valOut - resultat[j]) * valOut * (1 - valOut) * weightsHtoO[i][j]) * valHidden * (1 - valHidden) * valInput;
                }
                weightsItoH[k][i] -= trainingRate * somme;
            }
        }
    }
    
    
    /**
     * Modifier les valeur des bias de l'output layer
     * 
     * @param resultat      Les valeurs de résultat
     */
    public void SGDBiasOutput(double[] resultat) {
        double valOut;
        
        for(int j = 0; j < reseau[1].length; j++)
        {
            valOut = reseau[1][j].getOutput();
            bias[1][j] -= trainingRate * (valOut - resultat[j]) * valOut * (1 - valOut);
        }
    }
    
    /**
     * Modifier les valeur des bias de le hidden layer
     * 
     * @param resultat      Les valeurs de résultat
     */
    public void SGDBiasHidden(double[] resultat) {
        double somme = 0;
        double valHidden;
        double valOut;
        
        for(int i = 0; i < reseau[0].length; i++)
        {
            valHidden = reseau[0][i].getOutput();

            for(int j = 0; j < reseau[1].length; j++)
            {
                valOut = reseau[1][j].getOutput();
                somme += ((valOut - resultat[j]) * valOut * (1 - valOut) * weightsHtoO[i][j]) * valHidden * (1 - valHidden);
            }
            bias[0][i] -= trainingRate * somme;
        }
    }
}