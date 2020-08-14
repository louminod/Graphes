import java.io.BufferedWriter;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class L3NEW_TG_E12_Tools {
    private L3NEW_TG_E12_Tools() {

    }

    /**
     * Utilisé dans le débug pour afficher le contenu d'une matrice dans la console.
     *
     * @param matrice La matrice à afficher.
     * @return Un String contenant la matrice formaté pour l'affichage.
     */
    public static String convertMatriceToString(Object[][] matrice) {
        StringBuilder sbResult = new StringBuilder();

        for (int i = 0; i < matrice.length; i++) {
            for (int j = 0; j < matrice[0].length; j++) {
                sbResult.append(String.format("%s ", matrice[i][j]));
            }
            sbResult.append("\n");
        }

        return sbResult.toString();
    }

    /**
     * Format une matrice donnée en paramètre en un String adapté à l'affichage en console.
     *
     * @param graphe      Le graphe à formater.
     * @param matriceType Le type de matrice donné en paramètre.
     * @return Un String.
     */
    public static String formatMatrice(L3NEW_TG_E12_Graphe graphe, L3NEW_TG_E12_MatriceType matriceType) {
        StringBuilder sbResult = new StringBuilder();

        int size = String.valueOf(graphe.getMaxWeight()).length() + 2;
        if (size < graphe.getMaxSommetSize()) {
            size = graphe.getMaxSommetSize();
        }

        switch (matriceType) {
            case ADJACENCE:
                sbResult.append("Matrice d'adjacence : \n");
                break;
            case VALEUR:
                sbResult.append("Matrice de valeurs : \n");
                break;
        }

        for (int i = 0; i < size; i++) {
            sbResult.append(" ");
        }
        for (Integer sommet : graphe.getDistinctSommetList()) {
            sbResult.append(String.format("%s", sommet));
            for (int i = 0; i < size - String.valueOf(sommet).length(); i++) {
                sbResult.append(" ");
            }
        }
        sbResult.append("\n");

        switch (matriceType) {
            case ADJACENCE:
                for (int i = 0; i < graphe.getMatriceAdjacence().length; i++) {
                    sbResult.append(String.format("%s", graphe.getDistinctSommetList().get(i)));
                    for (int k = 0; k < size - String.valueOf(graphe.getDistinctSommetList().get(i)).length(); k++) {
                        sbResult.append(" ");
                    }
                    for (int j = 0; j < graphe.getMatriceAdjacence()[i].length; j++) {
                        sbResult.append(String.format("%s", graphe.getMatriceAdjacence()[i][j]));
                        for (int k = 0; k < size - String.valueOf(graphe.getMatriceAdjacence()[i][j]).length(); k++) {
                            sbResult.append(" ");
                        }
                    }
                    sbResult.append("\n");
                }
                break;
            case VALEUR:
                for (int i = 0; i < graphe.getMatriceAdjacence().length; i++) {
                    sbResult.append(String.format("%s", graphe.getDistinctSommetList().get(i)));
                    for (int k = 0; k < size - String.valueOf(graphe.getDistinctSommetList().get(i)).length(); k++) {
                        sbResult.append(" ");
                    }
                    for (int j = 0; j < graphe.getMatriceAdjacence()[i].length; j++) {
                        if ((graphe.getMatriceValeur()[i][j] == 0 && graphe.verifArcExist(i, j)) || graphe.getMatriceValeur()[i][j] != 0) {
                            sbResult.append(String.format("%d", graphe.getMatriceValeur()[i][j]));
                        } else {
                            sbResult.append("*");
                        }
                        for (int k = 0; k < size - String.valueOf(graphe.getMatriceValeur()[i][j]).length(); k++) {
                            sbResult.append(" ");
                        }
                    }
                    sbResult.append("\n");
                }
                break;
        }

        return sbResult.toString();
    }

    /**
     * Enregistre les traces d'execution
     * @param stack La trace à enregistrer
     */
    public static void saveStack(String stack, String grapheName) {
        try {
            String grapheNumber = grapheName.substring(grapheName.length()-5, grapheName.length()-4);
            BufferedWriter writer = new BufferedWriter(new FileWriter(String.format("%s/L3NEW-TG-E12-g%s.txt", System.getProperty("user.home"), grapheNumber)));
            writer.write(stack);
            writer.close();
            System.out.println("--> Trace d'execution enregistrée dans " + System.getProperty("user.home"));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
