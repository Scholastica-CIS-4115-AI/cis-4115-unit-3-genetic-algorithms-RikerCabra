
import java.util.Random;

/**
 * Original @author Kunuk Nykjaer on http://kunuk.wordpress.com/2010/09/27/genetic-algorithm-example-with-java/ Updated for CIS 4115 AI by Tom Gibbons. Updated
 * for assignment by: STUDENT NAME HERE
 *
 * @version Spring 2017 version
 */
public class Candidate implements Comparable<Candidate> {

    public boolean[] genotype;
    Random rand;

    /*
     * constructor creates a candidate and initializes the gene to random boolean values
     */
    public Candidate() {
        rand = new Random();
        genotype = new boolean[GA.GENESIZE];
        for (int i = 0; i < genotype.length; i++) {
            genotype[i] = ( 0.5 > rand.nextFloat() );
        }
    }
    /*
     * returns the gene as a string
     */ 
    private String geneAsString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < genotype.length; i++) {
            sb.append(genotype[i] == true ? 1 : 0);
        }
        return sb.toString();
    }
    /*
     * calculates the fitness by summing up all the 1's in the gene.
     */
    int fitness() {
        int sum = 0;
        for (int i = 0; i < genotype.length; i++) {
            if (genotype[i]) {
                sum++;
            }
        }
        return sum;
    }
    /*
     * compares to candidates by comparing their fitness values. Used by sort method and needed for interface
     */
    public int compareTo(Candidate o) {
        int f1 = this.fitness();
        int f2 = o.fitness();

        if (f1 < f2) {
            return 1;
        } else if (f1 > f2) {
            return -1;
        } else {
            return 0;
        }
    }
    /*
     * Displays the candidate as a string
     */
    @Override
    public String toString() {
        return "gene=" + geneAsString() + " fit=" + fitness();
    }
}
