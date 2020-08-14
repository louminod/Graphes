import java.util.*;

public class L3NEW_TG_E12_Graphe {
    // Nombre de sommets et d'arcs du graphe
    private int nbSommet, nbArc;
    // Liste des arcs du graphe
    private List<L3NEW_TG_E12_Arc> lstArcs;

    // Matrice d'adjacence et de valeur
    private String matriceAdjacence[][];
    private int matriceValeur[][];

    /**
     * Constructeur de la classe.
     * Initialize la liste d'arc, le nombre de sommet et le nombre d'arcs.
     */
    public L3NEW_TG_E12_Graphe() {
        this.lstArcs = new ArrayList<>();
        this.nbSommet = 0;
        this.nbArc = 0;
    }

    /**
     * Permet de définir le nombre de sommet.
     *
     * @param nbSommet Le nombre de sommet.
     */
    public void setNbSommet(int nbSommet) {
        this.nbSommet = nbSommet;
    }

    /**
     * Permet d'obtenir le nombre de sommet.
     *
     * @return Le nombre de sommets.
     */
    public int getNbSommet() {
        return this.nbSommet;
    }

    /**
     * Permet de définir le nombre de d'arcs.
     *
     * @param nbArc Le nombre d'arcs.
     */
    public void setNbArc(int nbArc) {
        this.nbArc = nbArc;
    }

    /**
     * Permet d'obtenir le nombre d'arcs.
     *
     * @return Le nombre d'arcs.
     */
    public int getNbArc() {
        return this.nbArc;
    }

    /**
     * Ajoute un arc à la liste des arcs du graphe.
     *
     * @param arc L'arc à ajouter.
     */
    public void addArc(L3NEW_TG_E12_Arc arc) {
        this.lstArcs.add(arc);
    }

    /**
     * Permet d'obtenir la matrice d'adjacence du graphe.
     *
     * @return La matrice d'adjacence.
     */
    public String[][] getMatriceAdjacence() {
        return this.matriceAdjacence;
    }

    /**
     * Permet d'obtenir la matrice de valeurs du graphe.
     *
     * @return La matrice de valeurs.
     */
    public int[][] getMatriceValeur() {
        return this.matriceValeur;
    }

    /**
     * Fabrique la matrice d'adjacence.
     * La matrice est établie en fonction des successeurs de chaque sommet.
     * La matrice d'adjacence est du type String[][]
     */
    public void makeMatriceAdjacence() {
        this.matriceAdjacence = new String[nbSommet][nbSommet];
        int indexLine = 0;
        for (Integer sommetLigne : this.getDistinctSommetList()) {
            int indexColumn = 0;
            for (Integer sommetColonne : this.getDistinctSommetList()) {
                if (this.getSuccesseursOfSommet(sommetLigne).contains(sommetColonne)) {
                    this.matriceAdjacence[indexLine][indexColumn] = "V";
                } else {
                    this.matriceAdjacence[indexLine][indexColumn] = "F";
                }
                indexColumn++;
            }
            indexLine++;
        }
    }

    /**
     * Fabrique la matrice de valeurs.
     * La matrice est établie en fonction des successeurs de chaque sommet.
     * La matrice de valeurs est du type int[][]
     */
    public void makeMatriceValeurs() {
        this.matriceValeur = new int[nbSommet][nbSommet];
        int indexLine = 0;
        for (Integer sommetLigne : this.getDistinctSommetList()) {
            int indexColumn = 0;
            for (Integer sommetColonne : this.getDistinctSommetList()) {
                if (this.getSuccesseursOfSommet(sommetLigne).contains(sommetColonne)) {
                    this.matriceValeur[indexLine][indexColumn] = this.getWeightOfArc(sommetLigne, sommetColonne);
                } else {
                    this.matriceValeur[indexLine][indexColumn] = 0;
                }
                indexColumn++;
            }
            indexLine++;
        }
    }

    /**
     * Détecte la présence d'un circuit ou non dans le graphe.
     *
     * @return un booleen indiquant la présence d'un circuit (true -> circuit)
     */
    public boolean detectCircuit() {
        boolean result = false;

        // Copie de la matrice pour effectuer des modifications sans modifier la source.
        String copieMatrice[][] = this.copieMatriceAdjacence();

        // On effectue nbSommet fois les opérations
        for (int a = 0; a < this.nbSommet; a++) {
            // On itère sur chaque sommet
            for (int i = 0; i < this.nbSommet; i++) {
                boolean supp = true;

                // On vérifie la présence d'un arc
                for (int j = 0; j < this.nbSommet; j++) {
                    // Si un arc est présent, on défini la suppression du sommet à false.
                    if (copieMatrice[j][i].equals("V")) {
                        supp = false;
                    }
                }

                if (supp) {
                    // On défini chaque arc du sommet à F
                    for (int k = 0; k < this.nbSommet; k++) {
                        copieMatrice[i][k] = "F";
                    }
                }
            }
        }

        // On detecte la présence d'un arc restant
        for (int i = 0; i < this.nbSommet; i++) {
            for (int j = 0; j < this.nbSommet; j++) {
                // Si oui, il y a présence d'un circuit
                if (copieMatrice[i][j].equals("V")) {
                    result = true;
                }
            }
        }

        return result;
    }

    /**
     * Calcul du rang de chaque sommet du graphe.
     *
     * @return Un tableau pour lequel l'indice correspond au sommet et la valeur le rang du sommet.
     */
    public List<Integer> getRangsSommets() {
        List<Integer> lstRangs = new ArrayList<>();
        List<Integer> lstProvisoire;
        int rang = 0;

        // Copie de la matrice pour effectuer des modifications.
        String copieMatrice[][] = this.copieMatriceAdjacence();

        // On effectue nbSommet fois les opérations
        for (int k = 0; k < nbSommet; k++) {
            // Création d'une liste provisoire qui va contenir les sommets pour lesquels il n'existe aucun arc.
            lstProvisoire = new ArrayList<>();
            for (int i = 0; i < nbSommet; i++) {
                int compteur = 0;
                // On compte le nombre de F
                for (int j = 0; j < nbSommet; j++) {
                    if (copieMatrice[j][i].equals("F")) {
                        compteur += 1;
                    }
                }
                // Si le compteur est égal au nombre de sommet, on ajoute le sommet à la liste provisoire.
                if (compteur == nbSommet) {
                    lstRangs.add(rang);
                    lstProvisoire.add(i);
                }
            }

            // Si il y a des sommets dans la liste provisoire
            if (lstProvisoire.size() > 0) {
                // Pour chaque sommet de cette liste
                for (Integer b : lstProvisoire) {
                    for (int r = 0; r < nbSommet; r++) {
                        copieMatrice[r][b] = String.valueOf(-1);
                        if (copieMatrice[b][r].equals("V")) {
                            copieMatrice[b][r] = "F";
                        }
                    }
                }
            } else {
                return lstRangs;
            }
            rang += 1;

            System.out.println(L3NEW_TG_E12_Tools.convertMatriceToString(copieMatrice));
        }
        return lstRangs;
    }

    /**
     * Détermine si le graphe est ordonnancé ou non.
     * - un seul point d’entrée,
     * - un seul point de sortie,
     * - pas de circuit,
     * - valeurs identiques pour tous les arcs incidents vers l’extérieur à un sommet,
     * - arcs incidents vers l’extérieur au point d’entrée de valeur nulle,
     * - pas d’arcs à valeur négative.
     *
     * @return un booleen vrai si il est ordonnancé faux sinon
     */
    public boolean ordonnancement() {
        boolean result = true;
        int tabNbPredecesseurs[] = new int[nbSommet];
        int tabNbSuccesseurs[] = new int[nbSommet];

        // Remplissage des tableaux des prédécesseurs et des successeurs
        for (int i = 0; i < nbSommet; i++) {
            tabNbPredecesseurs[i] = this.getPredecesseursOfSommet(i).size();
            tabNbSuccesseurs[i] = this.getSuccesseursOfSommet(i).size();
        }

        // Vérification de l'entrée unique
        int nbSommetsSansPredecesseur = 0;
        for (int i = 0; i < tabNbPredecesseurs.length; i++) {
            if (tabNbPredecesseurs[i] == 0) {
                nbSommetsSansPredecesseur += 1;
            }
        }

        // Vérification de la sortie unique
        int nbSommetsSansSuccesseur = 0;
        for (int i = 0; i < tabNbSuccesseurs.length; i++) {
            if (tabNbSuccesseurs[i] == 0) {
                nbSommetsSansSuccesseur += 1;
            }
        }

        // Si il y a plus d'un sommet initial ou plus d'un sommet terminal
        if (nbSommetsSansPredecesseur > 1 || nbSommetsSansSuccesseur > 1) {
            result = false;
        }

        // Si pour le moment c'est toujours un graphe ordonnancé
        if (result) {
            // Si il ne contient pas de circuit
            if (!this.detectCircuit()) {
                // Vérification des poids
                for (int i = 0; i < this.nbSommet; i++) {
                    for (int j = 0; j < this.nbSommet; j++)  // Parcour de la Matrice de Valeurs
                    {
                        if (this.getWeightOfArc(i, j) < 0) // Si on a un poids négatif dans la Matrice des valeurs
                        {
                            result = false;
                        }

                        if (this.getWeightOfArc(i, j) > 0) // On regarde si on a un poids différent de 0
                        {
                            for (int k = j; k < this.nbSommet; k++) {
                                // On compare ce poids avec tout les autres poids pour verifier
                                // qu'on a bien un poids unique d'un sommet i vers les sommets 1,2,3,...
                                if (i != j && this.getWeightOfArc(i, j) != this.getWeightOfArc(i, k) && this.getWeightOfArc(i, k) > 0) {
                                    result = false;  // Si on a un poids différent ce n'est pas un graphe D'ORDO
                                }
                            }
                        }
                    }
                }
            }
        }

        // Si pour le moment c'est toujours un graphe ordonnancé
        if (result) {
            // On récupère le sommet initial
            int sommet = 0;
            for (int i = 0; i < tabNbPredecesseurs.length; i++) {
                if (tabNbPredecesseurs[i] == 0) {
                    sommet = i;
                }
            }

            // On vérifie que chaque arc correspondant à ce sommet n'est pas différent de 0
            for (L3NEW_TG_E12_Arc arc : this.lstArcs) {
                if (arc.getStartPoint() == sommet && arc.getWeight() != 0) {
                    result = false;
                }
            }
        }

        return result;
    }

    /**
     * Première tentative de calcul de dates au plus tôt.
     * Une boucle infinie est déclenchée et empèche l'execution correcte.
     */
    public void datePlusTotV1() {
        String chemin = "";
        ArrayList<String> lstChemins = new ArrayList<>();
        int depart = 0;
        int e = 0;
        int nbTest = this.nbSommet * 5;

        for (int i = 0; i < nbTest; i++) {
            while (e != nbSommet - 1) {
                HashMap<Integer, Integer> mapSucc = getSucc(e);
                int n = mapSucc.size();
                int f = (int) (Math.random() * (n - 1));
                chemin = chemin + e + mapSucc.get(f);
                Integer tabProv[] = new Integer[mapSucc.keySet().size()];
                int c = 0;
                for (Integer key : mapSucc.keySet()) {
                    tabProv[c] = key;
                    c++;
                }
                e = tabProv[f];
            }
            lstChemins.add(chemin);
        }

        ArrayList<Integer> lstLongueurChemins = new ArrayList<>();
        int r = lstChemins.size();
        String parcourt = "";
        ArrayList<String> lstParcourts = new ArrayList<>();
        int somme = 0;
        for (int i = 0; i < r; i++) {
            somme = 0;
            parcourt = "";
            int s = lstChemins.get(i).length();
            for (int j = 0; j < s; j += 2) {
                somme += lstChemins.get(j).length();
                parcourt = parcourt + lstChemins.get(j - 1);
            }
            parcourt = parcourt + lstChemins.get(s - 1);
            lstParcourts.add(parcourt);
            lstLongueurChemins.add(somme);
        }

        int dateAuPlusTot = Collections.min(lstLongueurChemins);
        String cheminPlusTot = lstParcourts.get(lstLongueurChemins.indexOf(Collections.min(lstLongueurChemins)));
        int dateAuplusTard = Collections.max(lstLongueurChemins);
        String cheminPlusTard = lstParcourts.get(lstLongueurChemins.indexOf(Collections.max(lstLongueurChemins)));

        System.out.println("Date au plus tôt : " + dateAuPlusTot);
        System.out.println("Chemin au plus tôt : " + cheminPlusTot);
        System.out.println("Date au plus tard : " + dateAuplusTard);
        System.out.println("Chemin au plus tard : " + cheminPlusTard);
    }

    /**
     * Deuxième tentative de calcul de dates au plus tôt.
     * Le résultat obtenue n'est pas celui attendu.
     */
    public List<Integer> datePlusTotV2() {
        List<Integer> lstTaches = new ArrayList<>();
        List<Integer> lstRangsTriesParRangs = new ArrayList<>();

    /*
        for (int i = 0; i < nbSommet; i++) {
            lstTaches.add(0);
            lstRangsTriesParRangs.add(0);
        }

        List<Integer> lstRangsTriesParSommets = this.getRangsSommets();

        int rangMini = nbSommet;
        int a = 0;
        int indiceSommet = 0;

        for (int t = 0; t < nbSommet; t++) {
            rangMini = nbSommet;
            for (int i = 0; i < nbSommet; i++) {
                if (lstRangsTriesParSommets.get(i) != -1 && lstRangsTriesParSommets.get(i) <= rangMini) {
                    indiceSommet = i;
                    rangMini = lstRangsTriesParSommets.get(i);
                }
            }
            lstRangsTriesParSommets.set(indiceSommet, -1);
            lstTaches.set(a, indiceSommet);
            lstRangsTriesParRangs.set(a, rangMini);
            a += 1;
        }

     		lstRangsTriesParSommets = this.getRangsSommets();

    		*/

        List<List<Integer>> lstDatePred = new ArrayList<>(nbSommet);

        // Tri Bulles, Trier Sommets en fonction de leurs rangs
        int temp1 = 0;
        int temp2 = 0;
        List<Integer> rangCroissant = this.getRangsSommets();
        List<Integer> sommetTrieRg = this.getDistinctSommetList();

        for (int i = 0; i < rangCroissant.size(); i++) {
            for (int j = 0; j < (rangCroissant.size()) - i - 1; j++) {
                if (rangCroissant.get(j) > rangCroissant.get(j + 1)) {
                    temp1 = rangCroissant.get(j);
                    rangCroissant.set(j, rangCroissant.get(j + 1));
                    rangCroissant.set(j + 1, temp1);

                    temp2 = sommetTrieRg.get(j);
                    sommetTrieRg.set(j, sommetTrieRg.get(j + 1));
                    sommetTrieRg.set(j + 1, temp2);
                }
            }
        }

        for (int i = 0; i < nbSommet; i++) {
            ArrayList<Integer> lst = new ArrayList<>();
            for (int j = 0; j < nbSommet; j++) {
                lst.add(0);
            }
            lstDatePred.add(lst);
        }

        for (int i = 1; i < nbSommet; i++) {
            for (int j = 0; j < (this.getPredecesseursOfSommet(sommetTrieRg.get(i))).size(); j++)  // On parcourt le nombre de predecesseur du sommet i
            {
                int max = 0;
                int index = 0;    // Index pour le datepred max
                int compteur = 0; // Index pour le datePred max
                for (int k = 0; k < this.getPredecesseursOfSommet(sommetTrieRg.get(i)).size(); k++) {
                    if (lstDatePred.get(this.getPredecesseursOfSommet(sommetTrieRg.get(i)).get(k)).get(k) > max) {
                        max = lstDatePred.get(this.getPredecesseursOfSommet(sommetTrieRg.get(i)).get(k)).get(k);
                        index = compteur;
                    }
                    compteur++;
                }
                int b = this.getPredecesseursOfSommet(sommetTrieRg.get(i)).get(j);

                lstDatePred.get(i).set(j, this.getWeightOfArc(this.getPredecesseursOfSommet(sommetTrieRg.get(i)).get(j), i) + lstDatePred.get(b).get(compteur));
                System.out.println(this.getPredecesseursOfSommet(sommetTrieRg.get(i)).get(j) + " : " + i + " = " + this.getWeightOfArc(this.getPredecesseursOfSommet(sommetTrieRg.get(i)).get(j), i));
                //lstDatePred.get(i).set(j, this.getWeightOfArc(this.getPredecesseursOfSommet(lstTaches.get(i)).get(j), i)); // ou index l'indice du max date pred[tache[i]
                // Somme : Poids du predecesseur du sommet i + max date predecesseur du predecesseur du sommet i dans la colonne
            }
        }
        // Calcul Date au plus tôt = Max des dates predecesseurs pour sommets i
        List<Integer> lstDateTot = new ArrayList<>(nbSommet);
        for (int i = 0; i < nbSommet; i++) {
            lstDateTot.add(0);
        }

        for (int i = 0; i < nbSommet; i++) {
            int maxi = 0;
            for (int j = 0; j < lstDatePred.get(i).size(); j++) {
                if (lstDatePred.get(i).get(j) > maxi) {
                    maxi = lstDatePred.get(i).get(j);
                }
            }
            lstDateTot.add(i, maxi);
        }
        System.out.println(rangCroissant);
        System.out.println(sommetTrieRg);
        return lstDateTot;
    }

    /**
     * Permet d'obtenir une HashMap de successeurs d'un sommet
     *
     * @param sommet Le sommet
     * @return La HashMap
     */
    private HashMap<Integer, Integer> getSucc(int sommet) {

        HashMap<Integer, Integer> mapSucc = new HashMap<>();

        for (int j = 0; j < this.getDistinctSommetList().size(); j++) {
            if (this.getWeightOfArc(sommet, j) != 0) {
                mapSucc.put(j, this.getWeightOfArc(sommet, j));
            }
        }

        return mapSucc;
    }

    /**
     * Méthode copiant la matrice d'ajacence.
     * Cette copie permet d'effectuer des modifications sans altérer la matrice source.
     *
     * @return La copie de la matrice.
     */
    private String[][] copieMatriceAdjacence() {
        String copie[][] = new String[nbSommet][nbSommet];
        for (int i = 0; i < nbSommet; i++) {
            for (int j = 0; j < nbSommet; j++) {
                copie[i][j] = this.matriceAdjacence[i][j];
            }
        }
        return copie;
    }

    /**
     * Retourne le poids maximal de l'ensemble des arcs du graphe.
     *
     * @return Le poids maximal.
     */
    public int getMaxWeight() {
        int max = 0;

        for (L3NEW_TG_E12_Arc arc : this.lstArcs) {
            if (arc.getWeight() > max) {
                max = arc.getWeight();
            }
        }

        return max;
    }

    /**
     * Retourne la taille de la chaine de caractère correspondante à l'écriture du sommet le plus grand.
     *
     * @return La taille maximum.
     */
    public int getMaxSommetSize() {
        int max = 0;

        for (Integer sommet : this.getDistinctSommetList()) {
            if (String.valueOf(sommet).length() > max) {
                max = String.valueOf(sommet).length();
            }
        }

        return max;
    }

    /**
     * Permet d'obtenir le poids d'un arc.
     * Si la matrice de valeur n'est pas encore créée, la liste des arcs est utilisée.
     * Sinon c'est la matrice de valeurs qui est utilisée.
     *
     * @param startPoint Le sommet de départ de l'arc.
     * @param endPoint   Le sommet d'arrivée de l'arc.
     * @return Le poids de l'arc.
     */
    public int getWeightOfArc(int startPoint, int endPoint) {
        int result = 0;

        /*if (this.matriceValeur.length > 0) {
            return this.matriceValeur[startPoint][endPoint];
        } else {
            for (Arc arc : this.lstArcs) {
                if (arc.getStartPoint() == startPoint && arc.getEndPoint() == endPoint) {
                    result = arc.getWeight();
                }
            }
        }*/

        for (L3NEW_TG_E12_Arc arc : this.lstArcs) {
            if (arc.getStartPoint() == startPoint && arc.getEndPoint() == endPoint) {
                result = arc.getWeight();
            }
        }

        return result;
    }

    /**
     * Vérifie l'existance d'un Arc
     *
     * @param startPoint Le point de départ
     * @param endPoint   Le point d'arrivé
     * @return Vrai si l'arc existe
     */
    public boolean verifArcExist(int startPoint, int endPoint) {
        boolean result = false;

        for (L3NEW_TG_E12_Arc arc : this.lstArcs) {
            if (arc.getStartPoint() == startPoint && arc.getEndPoint() == endPoint) {
                result = true;
            }
        }

        return result;
    }

    /**
     * Permet d'obtenir la liste des différents sommet du graphe.
     *
     * @return La liste des sommets.
     */
    public List<Integer> getDistinctSommetList() {
        List<Integer> lstResult = new ArrayList<>();

        for (L3NEW_TG_E12_Arc arc : this.lstArcs) {
            if (!lstResult.contains(arc.getStartPoint())) {
                lstResult.add(arc.getStartPoint());
            }
            if (!lstResult.contains(arc.getEndPoint())) {
                lstResult.add(arc.getEndPoint());
            }
        }

        // Trie des éléments du tableau dans l'ordre croissant pour l'affichage et l'itération de manière ordonnée.
        Collections.sort(lstResult);

        // On rends la liste inmodifiable pour éviter l'ajout ou la suppresion (accidentelle ou non) de sommets vu que cette liste va
        // s'agir d'une référence pour les opérations à effectuer.
        return Collections.unmodifiableList(lstResult);
    }

    /**
     * Permet d'obtenir la liste des successeurs d'un sommet.
     *
     * @param sommet Le sommet.
     * @return La liste des sommets successeurs.
     */
    public List<Integer> getSuccesseursOfSommet(int sommet) {
        List<Integer> lstResult = new ArrayList<>();

        for (L3NEW_TG_E12_Arc arc : this.lstArcs) {
            if (arc.getStartPoint() == sommet) {
                lstResult.add(arc.getEndPoint());
            }
        }

        return lstResult;
    }

    /**
     * Permet d'obtenir la liste des prédecesseurs d'un sommet.
     *
     * @param sommet Le sommet.
     * @return La liste des sommets prédécesseurs.
     */
    public List<Integer> getPredecesseursOfSommet(int sommet) {
        List<Integer> lstResult = new ArrayList<>();

        for (L3NEW_TG_E12_Arc arc : this.lstArcs) {
            if (arc.getEndPoint() == sommet) {
                lstResult.add(arc.getStartPoint());
            }
        }

        return lstResult;
    }

    @Override
    public String toString() {
        return "Graphe{" +
                "nbSommet=" + nbSommet +
                ", nbArc=" + nbArc +
                ", lstArcs.size=" + lstArcs.size() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        L3NEW_TG_E12_Graphe graphe = (L3NEW_TG_E12_Graphe) o;
        return nbSommet == graphe.nbSommet &&
                nbArc == graphe.nbArc &&
                Objects.equals(lstArcs, graphe.lstArcs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nbSommet, nbArc, lstArcs);
    }
}
