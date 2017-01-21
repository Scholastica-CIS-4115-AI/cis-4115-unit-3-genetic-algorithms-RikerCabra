
import java.util.*;

/**
 * Original @author Kunuk Nykjaer on http://kunuk.wordpress.com/2010/09/27/genetic-algorithm-example-with-java/ 
 * Updated for CIS 4115 AI by Tom Gibbons. 
 * Updated for assignment by: STUDENT NAME HERE
 * @version Spring 2017 version
 */
public class GA {

    LinkedList<Candidate> population = new LinkedList<Candidate>();
    final Random rand;

    // try to modify the following parameters to improve the GA's performance
    static final int GENESIZE = 100;				// length of the gene
    final int populationSize = 10;				// number of individuals in population
    final int maxGenerations = 50;                              // number of generations to run evolution
    final double mutatePercent = 0.1;				// chance of a mutation
    final int parentUsePercent = 10;				// percent of parents to keep each generation

    /**
     * 
     * Constructor: Initialize the population to random candidates
     */
    public GA() {
        rand = new Random();
        for (int i = 0; i < populationSize; i++) {
            Candidate c = new Candidate();
            population.add(c);
        }
        Collections.sort(population); // sort method

    }
    /**
     * print() displays each candidate on its own line along with its fitness score.
     */
    void print() {
        for (Candidate c : population) {
            System.out.println(c);
        }
    }

    /**
     * Selection strategy: Tournament method Replacement strategy: elitism 10% and steady state find 4 random in population not same let 2 fight, and 2 fight
     * the winners makes 2 children
     */
    void produceNextGen() {
        LinkedList<Candidate> newpopulation = new LinkedList<Candidate>();

        while (newpopulation.size() < populationSize * (1.0 - (parentUsePercent / 100.0))) {
            int size = population.size();
            int i = rand.nextInt(size);
            int j, k, l;
            j = k = l = i;
            while (j == i) {
                j = rand.nextInt(size);
            }
            while (k == i || k == j) {
                k = rand.nextInt(size);
            }
            while (l == i || l == j || k == l) {
                l = rand.nextInt(size);
            }

            Candidate cand1 = population.get(i);
            Candidate cand2 = population.get(j);
            Candidate cand3 = population.get(k);
            Candidate cand4 = population.get(l);

            int fit1 = cand1.fitness();
            int fit2 = cand2.fitness();
            int fit3 = cand3.fitness();
            int fit4 = cand4.fitness();

            Candidate winner1, winner2;

            if (fit1 > fit2) {
                winner1 = cand1;
            } else {
                winner1 = cand2;
            }
            if (fit3 > fit4) {
                winner2 = cand3;
            } else {
                winner2 = cand4;
            }

            Candidate child1, child2;

            // Method one-point crossover random pivot
            int pivot = GENESIZE/2;      // select the midpoint of the gene for crossover		
            child1 = newChildFixedPivot(winner1, winner2, pivot);
            child2 = newChildFixedPivot(winner2, winner1, pivot);
            // Method uniform crossover
            //Candidate[] children = GA.this.newChildRandomPivot(winner1, winner2);
            //child1 = children[0];
            //child2 = children[1];

            boolean m1 = rand.nextFloat() <= mutatePercent;
            boolean m2 = rand.nextFloat() <= mutatePercent;

            if (m1) {
                mutate(child1);
            }
            if (m2) {
                mutate(child2);
            }

            boolean isChild1Good = child1.fitness() >= winner1.fitness();
            boolean isChild2Good = child2.fitness() >= winner2.fitness();

            newpopulation.add(isChild1Good ? child1 : winner1);
            newpopulation.add(isChild2Good ? child2 : winner2);
        }

        // add top percent parent		
        int j = (int) (populationSize * parentUsePercent / 100.0);
        for (int i = 0; i < j; i++) {
            newpopulation.add(population.get(i));
        }

        population = newpopulation;
        Collections.sort(population);
    }
    /*
     * one-point crossover with fixed uniform crossover provided as pivot point
     */
    Candidate newChildFixedPivot(Candidate c1, Candidate c2, int pivot) {
        Candidate child = new Candidate();

        for (int i = 0; i < pivot; i++) {
            child.genotype[i] = c1.genotype[i];
        }
        for (int j = pivot; j < GENESIZE; j++) {
            child.genotype[j] = c2.genotype[j];
        }

        return child;
    }
    /*
     * Randomly swap genes between the two candidates.
     */ 
    Candidate[] newChildRandomPivot(Candidate c1, Candidate c2) {
        Candidate child1 = new Candidate();
        Candidate child2 = new Candidate();

        for (int i = 0; i < GENESIZE; i++) {
            boolean b = rand.nextFloat() >= 0.5;
            if (b) {
                child1.genotype[i] = c1.genotype[i];
                child2.genotype[i] = c2.genotype[i];
            } else {
                child1.genotype[i] = c2.genotype[i];
                child2.genotype[i] = c1.genotype[i];
            }
        }
        return new Candidate[]{child1, child2};
    }
    /*
     * Mutate a random bit in the candidate's gene, flipping it from 1 to 0 or 0 to 1.
     */ 
    void mutate(Candidate c) {
        int i = rand.nextInt(GENESIZE);
        c.genotype[i] = !c.genotype[i]; // flip
    }
    /*
     * run the simulation for the given number of generations
     */ 
    void run() {
        System.out.println("Init population sorted best to worst");
        print();

        for (int count=0; count < maxGenerations; count++) {
            produceNextGen();
        }

        System.out.println("Final population after " + maxGenerations +  " generations");
        print();
    }
    /*
     * main routine which is started when executed
     */ 
    public static void main(String[] args) {

        GA ga = new GA();
        ga.run();

    }
}
