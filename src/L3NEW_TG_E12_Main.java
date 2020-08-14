import java.util.List;
import java.util.Scanner;

public class L3NEW_TG_E12_Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        L3NEW_TG_E12_GrapheLoader grapheLoader = new L3NEW_TG_E12_GrapheLoader(scanner);

        // Selection du dossier contenant les graphes à tester
        // A utiliser lors de la présentation
        // grapheLoader.selectFolder();

        boolean quitter = false;
        while (!quitter) {
            System.out.println("\nQue faire ?\n  1.Charger un graphe\n  2.Quitter");
            switch (scanner.nextInt()) {
                case 1:
                    StringBuilder stackTrace = new StringBuilder();

                    // Sélection du graphe
                    grapheLoader.selectFile();

                    // Chargement et importation du graphe
                    L3NEW_TG_E12_Graphe graphe = grapheLoader.loadGraph();

                    String grapheName = grapheLoader.getGraphName();

                    if (graphe != null) {
                        // Génération et affichage des matrices
                        String matriceAdjacence = L3NEW_TG_E12_Tools.formatMatrice(graphe, L3NEW_TG_E12_MatriceType.ADJACENCE);
                        String matriceValeur = L3NEW_TG_E12_Tools.formatMatrice(graphe, L3NEW_TG_E12_MatriceType.VALEUR);
                        stackTrace.append(matriceAdjacence + "\n");
                        stackTrace.append(matriceValeur + "\n");
                        System.out.println(matriceAdjacence);
                        System.out.println(matriceValeur);

                        // Détection du circuit
                        System.out.println("Lancement de la détection de circuits...");
                        stackTrace.append("\nLancement de la détection de circuits...");
                        boolean circuit = graphe.detectCircuit();

                        if (!circuit) {
                            System.out.println("Pas de circuit détecté !\n");
                            stackTrace.append("\nPas de circuit détecté !\n");
                            System.out.println("-> Démarrage calcul des rangs");
                            stackTrace.append("-> Démarrage calcul des rangs\n");
                            List<Integer> tabRangs = graphe.getRangsSommets();
                            System.out.println("--> Rangs calculés !");
                            stackTrace.append("--> Rangs calculés !\n");
                            for (int i = 0; i < graphe.getDistinctSommetList().size(); i++) {
                                System.out.printf("|-> Rang de %s : %d\n", graphe.getDistinctSommetList().get(i), tabRangs.get(i));
                                stackTrace.append(String.format("|-> Rang de %s : %d\n", graphe.getDistinctSommetList().get(i), tabRangs.get(i)));
                            }
                        } else {
                            System.out.println("/!\\ Présence d'un circuit, arrêt de l'algorithme /!\\\n");
                            stackTrace.append("/!\\ Présence d'un circuit, arrêt de l'algorithme /!\\\n\n");
                        }

                        System.out.println("\nDétection de l'ordonnancement du graphe");
                        stackTrace.append("\nDétection de l'ordonnancement du graphe");

                        System.out.println("Graphe ordonnancé : " + (graphe.ordonnancement() ? "oui" : "non"));
                        stackTrace.append("\nGraphe ordonnancé : " + (graphe.ordonnancement() ? "oui" : "non"));
                        stackTrace.append("\n");

                        // Ne marche pas
                        // graphe.datePlusTotV2();
                        /*for(Integer date : graphe.datePlusTotV3()) {
                            System.out.println(date);
                        }*/

                        // Sauvegarde de la trace d'éxecution.
                        L3NEW_TG_E12_Tools.saveStack(stackTrace.toString(), grapheName);
                    }
                    break;
                case 2:
                    quitter = true;
                    break;
            }
        }
    }
}
