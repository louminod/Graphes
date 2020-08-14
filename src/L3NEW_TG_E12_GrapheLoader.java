import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

public class L3NEW_TG_E12_GrapheLoader {

    // Permet de lire les entrées clavier
    private Scanner scanner;
    // Création des fichiers et dossiers
    private File graphfolder, graphToload;

    /**
     * Contructeur de la classe.
     * @param scanner Le scanner utilisé dans l'application.
     */
    public L3NEW_TG_E12_GrapheLoader(Scanner scanner) {
        this.scanner = scanner;
        // Défini manuellement pour rendu du projet
        this.graphfolder = new File("src/");
    }

    /**
     * Retourne le nom du fichier du graphe en cours.
     * @return Le nom.
     */
    public String getGraphName() {
        return graphToload.getName();
    }

    /**
     * Affiche une fenetre permettant la sélection du dossier contenant les fichiers graphes à tester
     */
    public void selectFolder() {
        JFileChooser fileChooser = new JFileChooser(System.getProperty("user.home"));
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setDialogTitle("Où sont situés les graphes à tester ?");
        int faisable = fileChooser.showOpenDialog(null);
        if (faisable == JFileChooser.APPROVE_OPTION) {
            this.graphfolder = fileChooser.getSelectedFile();
        }
    }

    /**
     * Sélection du fichier contenant le graphe à charger et tester
     * @return le numéro du graphe
     */
    public void selectFile() {
        System.out.println("Choix du fichier à charger :");
        File[] listOfFiles = this.graphfolder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile() && !listOfFiles[i].getName().substring(0, 1).equals(".") && listOfFiles[i].getName().split("\\.")[1].equals("txt")) {
                System.out.printf("  %d. %s\n", i, listOfFiles[i].getName());
            }
        }
        this.graphToload = listOfFiles[this.scanner.nextInt()];
        System.out.println("\n-> Fichier chargé !");
    }

    /**
     * Chargement du graphe par la lecture des informations contenues dans le fichier.
     * Des vérifications sont effectuées pour garantir l'intégrité du graphe.
     *
     * @return Le graphe chargé (Graphe)
     */
    public L3NEW_TG_E12_Graphe loadGraph() {
        System.out.println("-> Importation du graphe...");
        L3NEW_TG_E12_Graphe graphe = new L3NEW_TG_E12_Graphe();
        int nbArcsFichier = 0;
        try {
            // Création d'un flux de lecture en mémoire
            BufferedReader br = new BufferedReader(new FileReader(this.graphToload));
            String line = br.readLine();

            int indexLine = 1;
            // Action en fonction du numéro de la ligne lue
            while (line != null) {
                if (indexLine == 1) {
                    graphe.setNbSommet(Integer.valueOf(line));
                    System.out.printf("|-> Nombre de sommets : %d\n", graphe.getNbSommet());
                }
                ;
                if (indexLine == 2) {
                    graphe.setNbArc(Integer.valueOf(line));
                    System.out.printf("|-> Nombre d'arcs : %d\n", graphe.getNbArc());
                    System.out.println("|-> Génération des arcs...");
                }
                ;
                if (indexLine >= 3) {
                    try {
                        String data[] = line.split(" ");
                        L3NEW_TG_E12_Arc arc = new L3NEW_TG_E12_Arc(Integer.valueOf(data[0]), Integer.valueOf(data[1]), Integer.valueOf(data[2]));
                        graphe.addArc(arc);
                        System.out.printf("|--> Arc créé : %d -> %d = %d\n", arc.getStartPoint(), arc.getEndPoint(), arc.getWeight());
                        nbArcsFichier++;
                    } catch (Exception exception) {
                        if(exception.getClass() != NumberFormatException.class) {
                            exception.printStackTrace();
                        }
                    }
                }
                line = br.readLine();
                indexLine++;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        System.out.println("|-> Arcs générés !");

        if (graphe.getNbArc() != nbArcsFichier) { // Vérification de la cohérence du nombre d'arcs
            System.err.println("==> Graphe incorrect, nombre d'arcs annoncés != nb d'arcs lus\n");
            System.out.println("==> Import annulé !");
            graphe = null;
        } else if (graphe.getNbSommet() != graphe.getDistinctSommetList().size()) { // Vérification du nombre de sommets
            System.err.println("==> Graphe incorrect, nombre de sommets annoncés != nb de sommets lus\n");
            System.out.println("==> Import annulé !");
            graphe = null;
        } else {
            System.out.println("==> Graphe correct et importé !\n");
            graphe.makeMatriceAdjacence();
            graphe.makeMatriceValeurs();
            System.out.println("-> Matrices générées !\n");
        }

        return graphe;
    }
}
