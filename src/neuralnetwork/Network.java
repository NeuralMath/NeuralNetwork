package neuralnetwork;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

/**
 * Class qui contient le réseau de neurones et qui peut obtenir la valeur de sortie du réseau
 * 
 * @author Marc4492
 * 10 février 2017
 */
public class Network {
    
    private final int INPUT;
    private final int HIDDEN;
    private final int OUTPUT;
    
    private final String fileWeightsItoH;
    private final String fileWeightsHtoO;
    private final String fileBias;
    
    private double[][] weightsItoH;
    private double[][] weightsHtoO;
    private double[][] bias;
    
    private double[] inputValues;
    private final Neuron[][] reseau;
    
    private final double trainingRate;

    /**
     * Constructeur du réseau, création des neurones
     * 
     * @param inputLayer                        Nombre de neurons dans la première couche
     * @param hiddenLayer                       Nombre de neurons dans la deuxième couche
     * @param outputLayer                       Nombre de neurons dans la troisième couche    
     * @param training                          Vitesse de l'apprentisage
     * @param fileWIH                           Path du fichier pour les poids entre l'input et le hidden layer
     * @param fileWHO                           Path du fichier pour les poids entre le hidden et le output layer
     * @param fileB                             Path du fichier pour les bias des neurones
     * 
     * @throws IOException                      S'il ya des problème de lecture d fichier
     */
    public Network(int inputLayer, int hiddenLayer, int outputLayer, double training, String fileWIH, String fileWHO, String fileB) throws IOException{
        INPUT = inputLayer;
        HIDDEN = hiddenLayer;
        OUTPUT = outputLayer;
        trainingRate = training;
        
        fileWeightsItoH = fileWIH;
        fileWeightsHtoO = fileWHO;
        fileBias = fileB;
        
        initializationVariablesNetwork();
        
        inputValues = new double[INPUT];
        
        //Création de la première dimmension
        reseau = new Neuron[][] 
                {
                    new Neuron[HIDDEN],
                    new Neuron[OUTPUT]
                };
        
        //Création des neuronnes avec leurs bias
        for (int i = 0; i < reseau.length; i++)
            for (int j = 0; j < reseau[i].length; j++)
                reseau[i][j] = new Neuron(bias[i][j]);
    }
    
    /**
     * Pour entrer les entrées des neurones du premier layer
     * 
     * @param input                         Le tableau de double pour les entrées
     * @throws IllegalArgumentException     S'il n'y a pas le bon nombre de données
     */
    /*public void setInputs(double[] input) {
        removeAllInputs();
        inputValues = input;
    }*/
    
    /**
     * Obtenir la valeur du réseau
     * 
     * @param input                         Le tableau de double pour les entrées
     * @return La position de la neurone avec la plus haute valeur
     */
    public int getAnwser(double[] input)
    {
        int posMax = 0;
        removeAllInputs();
        inputValues = input;
        
        computes();
        
        //Trouver la position de la plus grande valeur d'output
        for(int i = 1; i < reseau[1].length; i++)
            if(reseau[1][i].getOutput() > reseau[1][posMax].getOutput())
                posMax = i;
        
        return posMax;
    }
    
    /**
     * Efface les inputs de toute les étages
     */
    private void removeAllInputs() {
        inputValues = new double[inputValues.length];
        
        for (Neuron[] etage : reseau)
            for (Neuron neurone : etage)
                neurone.removeInputs();
    }
    
    /**
     * Exectuer le réseau de neurones
     */
    private void computes() {
        //Exécuter les neurones du premier étage et passer les valeurs à l'autre couche
        for(int i = 0; i < inputValues.length; i++)
            for(int j = 0; j < reseau[0].length; j++)
                reseau[0][j].addInputs(inputValues[i]*weightsItoH[i][j]);
        
        for(int i = 0; i < reseau[0].length; i++)
            reseau[0][i].computes();
        
        //Exécuter les neurones du deuxième étage et passer les valeurs à l'autre couche
        for(int i = 0; i < reseau[0].length; i++)
            for(int j = 0; j < reseau[1].length; j++)
                reseau[1][j].addInputs(reseau[0][i].getOutput()*weightsHtoO[i][j]);
        
        //Exécuter les neurones du dernier étage
        for(int i = 0; i < reseau[1].length; i++)
            reseau[1][i].computes();
    }
    
    
    /**
     * Initialisation des paramètres du réseau
     * 
     * @throws IOException      Si les fichiers ne sont pas de la bonne taille
     */
    private void initializationVariablesNetwork() throws IOException {        
        //Creation du tableau de bias avec les bonnes longeurs
        bias = new double[2][];
        bias[0] = new double[HIDDEN];
        bias[1] = new double[OUTPUT];
        
        //Creation des tableau de weights
        weightsItoH = new double[INPUT][HIDDEN];
        weightsHtoO = new double[HIDDEN][OUTPUT];
        
        //Read les valeurs dans les fichiers
        readFile(fileBias, bias);
        readFile(fileWeightsItoH, weightsItoH);
        readFile(fileWeightsHtoO, weightsHtoO);
    }
    
    
    
    
    
    //********************* Entrainement du réseau *********************\\
    
    
    /**
     * Entrainer le réseau
     * 
     * @param trainingSet   Les valeurs de test, avec tous les exemples
     * @param resultat      Les résultats de chaque exemple
     * 
     * @throws IOException  S'il y a un problème d'écriture dans un fichier
     */
    public void train(double[][] trainingSet, double[][] resultat) throws IOException {        
        for (int i = 0; i < trainingSet.length; i++) {
            //Set les inputs
            removeAllInputs();
            inputValues = trainingSet[i];
            //Calculer la valeur
            computes();
            //Effectué l'apprentisage
            SGDWeightHiddenToOutput(resultat[i]);
            SGDWeightInputToHidden(resultat[i]);
            SGDBiasOutput(resultat[i]);
            SGDBiasHidden(resultat[i]);
        }
        
        //Enregistrement des données
        writeFile(fileBias, bias);
        writeFile(fileWeightsItoH, weightsItoH);
        writeFile(fileWeightsHtoO, weightsHtoO);
    }
    
    /**
     * Modifier les valeur des poids entre l'hidden et le output layer
     * 
     * @param resultat      Les valeurs 
     */
    private void SGDWeightHiddenToOutput(double[] resultat) {
        double valOut;
        double valHidden;
        
        //Stocastic gradient descent
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
    private void SGDWeightInputToHidden(double[] resultat) {
        double somme = 0;
        double valInput;
        double valHidden;
        double valOut;
        
        //Stocastic gradient descent
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
    private void SGDBiasOutput(double[] resultat) {
        double valOut;
        
        //Stocastic gradient descent
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
    private void SGDBiasHidden(double[] resultat) {
        double somme = 0;
        double valHidden;
        double valOut;
        
        //Stocastic gradient descent
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
    
    
    
    
    
    
    //********************* Fichier pour les variables du réseaux *********************\\
    
    
    
    
    
    /**
     * Remettre toutes les valeurs à 1.0 et les écrire dans le fichier
     * 
     * * @param inputLayer                        Nombre de neurons dans la première couche
     * @param hiddenLayer                       Nombre de neurons dans la deuxième couche
     * @param outputLayer                       Nombre de neurons dans la troisième couche
     * @param fileWIH                           Path du fichier pour les poids entre l'input et le hidden layer
     * @param fileWHO                           Path du fichier pour les poids entre le hidden et le output layer
     * @param fileB                             Path du fichier pour les bias des neurones
     * 
     * @throws IOException                      S'il ya des problème de lecture d fichier
     */
    public static void resetDatas(int inputLayer, int hiddenLayer, int outputLayer, String fileWIH, String fileWHO, String fileB) throws IOException
    {
        double[][] weightIH = new double[inputLayer][hiddenLayer];
        double[][] weightHO = new double[hiddenLayer][outputLayer];
        double[][] biasVal = new double[2][];
        biasVal[0] = new double[hiddenLayer];
        biasVal[1] = new double[outputLayer];
        
        //Affectation des variables
        for(double[] val : weightHO)
            Arrays.fill(val, 1.0);
        
        for(double[] val : weightIH)
            Arrays.fill(val, 1.0);
        
        for(double[] val : biasVal)
            Arrays.fill(val, 1.0);
        
        //Écriture dans les fichiers
        writeFile(fileB, biasVal);
        writeFile(fileWHO, weightHO);
        writeFile(fileWIH, weightIH);
    }
    
    /**
     * Écriture d'un tableau deux dimension dans un fichier texte. (Créer s'il n'existe pas)
     * 
     * @param filePath          La direction du fichier
     * @param array             Le tableau à écrire
     * @throws IOException      S'il y a des problème d'écriture dans le fichier
     */
    private static void writeFile(String filePath, double[][] array) throws IOException
    {
        //Creation du fichier s'il n'existe pas
        File file = new File(filePath);
        if(!file.exists())
            file.createNewFile();
        
        //Écriture sou forme de tableau : [...,...,...] séparé par des \n entre les dimensions
        StringBuilder sb = new StringBuilder();
        for(double[] values : array)
            sb.append(Arrays.toString(values)).append(System.lineSeparator());

        //Écriture dans le fichier
        FileOutputStream writer = new FileOutputStream(file, false);
        writer.write(sb.toString().getBytes());
        writer.close();
    }
    
    /**
     * Lecture d'un tableau deux dimension depuis un fichier texte.
     * 
     * @param filePath                      La direction du fichier
     * @param array                         Le tableau à lire
     * @throws IOException                  S'il y a des problème de lecture dans le fichier ou que le fichier n'a pas les bonnes tailles. (nbs lignes/colonnes)
     * @throws NumberFormatException        Si le texte n'est pas en double
     */
    private static void readFile(String filePath, double[][] array) throws IOException, NumberFormatException
    {
        //Obtention du fichier
        File file = new File(filePath);
        if(!file.exists())
            throw new IOException("The file isn't valid");
        
        FileInputStream reader = new FileInputStream(file);
        
        StringBuilder sb = new StringBuilder();
        
        //Lecture jusqu'à la fin du fichier
        int content;
        while((content = reader.read()) != -1)
            sb.append((char) content);
        
        reader.close();
        
        //Cretation des tableaux selon le fichier
        String[] lines = sb.toString().split(System.lineSeparator());
        String[][] linesElements = new String[lines.length][];
        
        if(lines.length != array.length)
            throw new IOException("Pas le bon nombre de lignes dans le ficher.");
        
        //Lecture de chaque élément du tableau deux dimension en String
        for(int i = 0; i < lines.length; i++)
        {
            linesElements[i] = lines[i].trim().replace("[", "").replace("]", "").split(",");
            if(linesElements[i].length != array[i].length)
                throw new IOException("Pas le bon nombre d'élements dans la ligne " + i + ".");
        }
        
        //Convertion en double[][]
        for(int i = 0; i < lines.length; i++)
            for(int j = 0; j < linesElements[i].length; j++)
                array[i][j] = Double.parseDouble(linesElements[i][j]);
    }
}