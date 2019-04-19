package INFO6205_517;

public class BI {
    double fitness;
    boolean[] indv;

    public BI(double f, boolean[] ind) {
        fitness = f; // the fitness value of this individual
        indv = ind; // the color/ RGB information array of this row/individual
    }

    public double getFitness() {
        return fitness;
    }

    public boolean[] getIndv() {
        return indv;
    }
}