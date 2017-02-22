package neuralnetwork;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class NeuralNetwork {

    private static final int INPUT = 9;
    private static final int HIDDEN = 3;
    private static final int OUTPUT = 2;
    
    private static final String fileWeightsItoH = "weightsItoH.txt";
    private static final String fileWeightsHtoO = "weightsHtoO.txt";
    private static final String fileBias = "bias.txt";
    
    private static double[][] weightsItoH;
    private static double[][] weightsHtoO;
    private static double[][] bias;
    
    public static void main(String[] args) {
        try
        {
            initializationVariablesNetwork();
            Reseau r = new Reseau(INPUT, HIDDEN, OUTPUT, 0.001, bias, weightsItoH, weightsHtoO);
        }
        catch(IllegalArgumentException ex)
        {
            System.out.println(ex.toString());
        }
        catch(IOException ex)
        {
            System.err.println(ex.toString());
        }
    }

    /**
     * Initialisation des paramètres du réseau
     * 
     * @throws IOException      Si les fichiers ne sont pas de la bonne taille
     */
    private static void initializationVariablesNetwork() throws IOException {        
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
    
    /**
     * Écriture d'un tableau deux dimension dans un fichier texte. (Créer s'il n'existe pas)
     * 
     * @param filePath          La direction du fichier
     * @param array             Le tableau à écrire
     * @throws IOException      S'il y a des problème d'écriture dans le fichier
     */
    public static void writeFile(String filePath, double[][] array) throws IOException
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
    public static void readFile(String filePath, double[][] array) throws IOException, NumberFormatException
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