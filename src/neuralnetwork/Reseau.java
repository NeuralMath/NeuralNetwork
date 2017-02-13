package neuralnetwork;

/**
 * Class qui contient le réseau de neurones et qui peut obtenir la valeur de sortie du réseau
 * 
 * @author Marc4492
 * 10 février 2017
 */
public class Reseau {
    
    private final double[][][] weights;
    private final Neurone[][] reseau = 
    {
        new Neurone[9],
        new Neurone[3],
        new Neurone[2]
    };
    private double trainingRate = 0.001;

    /**
     * Constructeur du réseau, création des neurones
     * 
     * @param weightsTemp                   Le tableau des weight des liaison entre les neurones
     * @throws IllegalArgumentException     Si le tableau de weight n'a pas les bonne grandeur
     */
    public Reseau(double[][][] weightsTemp) throws IllegalArgumentException{
        
        //Vérification pour la grandeur en toutes les dimensions du tableau de wieght
        if(weightsTemp.length != reseau.length-1)
            throw new IllegalArgumentException("Le tableau de weight a pas la bonne grandeur par rapport au nombre de layer");
        else
        {
            for(int i = 0; i < reseau.length-1; i++)
            {
                if(weightsTemp[i].length != reseau[i].length)
                    throw new IllegalArgumentException("Le tableau de weight a pas la bonne grandeur par rapport au nombre de neurones dans le layer " + i);
                
                for(int j = 1; j < reseau[i].length; j++)
                    if(weightsTemp[i][j].length != reseau[i+1].length)
                        throw new IllegalArgumentException("Le tableau de weight a pas la bonne grandeur par rapport au nombre de liaisons de la neurone " + j + " dans le entre le layer " + (i-1) + " et " + i);
            }
        }
                    
        weights = weightsTemp;
        
        //Création des neuronnes
        for (Neurone[] reseau1 : reseau) {
            for (int j = 0; j < reseau1.length; j++) {
                reseau1[j] = new Neurone(j);
            }
        }
    }
    
    /**
     * Pour entrer les entrées des neurones du premier layer
     * 
     * @param input                         Le tableau de double pour les entrées
     * @throws IllegalArgumentException     S'il n'y a pas le bon nombre de données
     */
    public void setInputs(double[] input) throws IllegalArgumentException
    {
        if(input.length != reseau[0].length)
            throw new IllegalArgumentException("Pas la bonne longueur de data");
        
        for(int i = 0 ; i < input.length; i++)
            reseau[0][i].addInputs(input[i]);
    }
    
    public void removeAllInputs()
    {
        for(int i = 0 ; i < reseau[0].length; i++)
            reseau[0][i].removeInputs();
    }
    
    /**
     * Exectuer le réseau de neurones
     * 
     * @return Retourne la position de la neurone avec le plus grande valeur de sorite
     */
    public int computes()
    {
        int posMax = 0;
        
        //Exécuter les neurones du premier étage et passer les valeurs à l'autre couche
        for(int i = 0; i < reseau[0].length; i++)
            for(int j = 0; j < reseau[1].length; j++)
                reseau[1][j].addInputs(reseau[0][i].computes()*weights[0][i][j]);
        
        //Exécuter les neurones du deuxième étage et passer les valeurs à l'autre couche
        for(int i = 0; i < reseau[1].length; i++)
            for(int j = 0; j < reseau[2].length; j++)
                reseau[2][j].addInputs(reseau[1][i].computes()*weights[1][i][j]);
        
        //Exécuter les neurones du dernier étage
        //for(int i = 0; i < reseau[2].length; i++)
          //  reseau[2][i].computes();
        
        //Trouver la position de la plus grande valeur d'output
        for(int i = 0; i < reseau[2].length; i++)
            if(reseau[2][i].computes() > reseau[2][posMax].computes())
                posMax = i;
        
        return posMax;
    }
    
    public void train(double[][] trainingSet, double[] resultat)
    {
        removeAllInputs();
        
        for(int i = 0; i < trainingSet[0].length; i++)
            setInputs(trainingSet[0]);
        
        computes();
        
        SGDHiddenToOutput(resultat);
        SGDInputToHidden(resultat);
    }
    
    public void SGDHiddenToOutput(double[] resultat)
    {
        for(int j = 0; j < reseau[1].length; j++)
        {
            for(int i = 0; i < reseau[2].length; i++)
            {
                double valeurNeuroneOutput = reseau[2][i].getOutput();
                double valeurNeuroneHidden = reseau[1][j].getOutput();
                weights[1][j][i] -= trainingRate * (valeurNeuroneOutput - resultat[i]) * valeurNeuroneOutput * (1 - valeurNeuroneOutput) * valeurNeuroneHidden;
            }
        }
    }
    
    public void SGDInputToHidden(double[] resultat)
    {
        double somme = 0;
        for(int k = 0; k < reseau[0].length; k++)
        {
            double valInput = reseau[0][k].getOutput();
            
            for(int i = 0; i < reseau[1].length; i++)
            {
                double valHidden = reseau[1][i].getOutput();
                
                for(int j = 0; j < reseau[2].length; j++)
                {
                    double valOut = reseau[2][j].getOutput();

                    somme += ((valOut - resultat[j]) * valOut * (1 - valOut) * weights[1][i][j]) * valHidden * (1 - valHidden) * valInput;
                }
                weights[0][k][i] -= trainingRate * somme;
            }
        }
    }
}
