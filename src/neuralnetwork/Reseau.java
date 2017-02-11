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
        new Neurone[4],
        new Neurone[3],
        new Neurone[2]
    };

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
            for(int i = 1; i < reseau.length; i++)
            {
                if(weightsTemp[i-1].length != reseau[i].length)
                    throw new IllegalArgumentException("Le tableau de weight a pas la bonne grandeur par rapport au nombre de neurones dans le layer " + i);
                
                for(int j = 0; j < reseau[i].length; j++)
                    if(weightsTemp[i-1][j].length != reseau[i-1].length)
                        throw new IllegalArgumentException("Le tableau de weight a pas la bonne grandeur par rapport au nombre de liaisons de la neurone " + j + " dans le entre le layer " + (i-1) + " et " + i);
            }
        }
                    
        weights = weightsTemp;
        
        //Création des neuronnes
        for(int i = 0; i < reseau.length; i++)
        {
            for(int j = 0; j < reseau[i].length; j++)
            {
                //Creation des neurones d'entrée donc sans weight
                if(i == 0)
                    reseau[i][j] = new Neurone(j);
                else
                    reseau[i][j] = new Neurone(j, weights[i-1][j]);
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
            reseau[0][i].addInputs(input[i], 0);
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
        {
            reseau[0][i].computes();
            for(int j = 0; j < reseau[1].length; j++)
            {
                reseau[1][j].addInputs(reseau[0][i].getOutput(), i);
            }
        }
        
        //Exécuter les neurones du deuxième étage et passer les valeurs à l'autre couche
        for(int i = 0; i < reseau[1].length; i++)
        {
            reseau[1][i].computes();
            for(int j = 0; j < reseau[2].length; j++)
            {
                reseau[2][j].addInputs(reseau[1][i].getOutput(), i);
            }
        }
        
        //Exécuter les neurones du dernier étage
        for(int i = 0; i < reseau[2].length; i++)
            reseau[2][i].computes();
        
        //Trouver la position de la plus grande valeur d'output
        for(int i = 0; i < reseau[2].length; i++)
            if(reseau[2][i].getOutput() > reseau[2][posMax].getOutput())
                posMax = i;
        
        return posMax;
    }
}
