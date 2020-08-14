import java.util.Objects;

public class L3NEW_TG_E12_Arc {
    // Sommet de départ et d'arrivée de l'arc
    private int startPoint, endPoint;
    // Poids de l'arc
    private int weight;

    /**
     * Constructeur de la classe Arc.
     * @param startPoint Le sommet de départ de l'arc.
     * @param endPoint Le sommet d'arrivée de l'arc.
     * @param weight La valeur de l'arc.
     */
    public L3NEW_TG_E12_Arc(int startPoint, int endPoint, int weight) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.weight = weight;
    }

    /**
     * Permet d'obtenir le sommet de départ.
     * @return Le sommet.
     */
    public int getStartPoint() {
        return startPoint;
    }

    /**
     * Permet d'obtenir le sommet d'arrivée.
     * @return Le sommet.
     */
    public int getEndPoint() {
        return endPoint;
    }

    /**
     * Permet d'obtenir le poids de l'arc.
     * @return Le poids.
     */
    public int getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return "Arc{" +
                "startPoint='" + startPoint + '\'' +
                ", endPoint='" + endPoint + '\'' +
                ", weight=" + weight +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        L3NEW_TG_E12_Arc arc = (L3NEW_TG_E12_Arc) o;
        return weight == arc.weight &&
                Objects.equals(startPoint, arc.startPoint) &&
                Objects.equals(endPoint, arc.endPoint);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startPoint, endPoint, weight);
    }
}
