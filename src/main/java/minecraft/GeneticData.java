package minecraft;

import java.util.BitSet;
import static minecraft.GeneticConstants.CHROMESOMES;
import static minecraft.GeneticConstants.DISORD_COEFFICIENT;

public class GeneticData {

  private final BitSet genes = new BitSet(CHROMESOMES);
  private boolean hasDisorder = false;

  /**
   * Generate a random sequence of genes
   * @return
   */
  public final static GeneticData random(boolean male) {
    final GeneticData geneticData = new GeneticData();
    for (int i = 0; i < CHROMESOMES; i++) {
      if (Math.floor(Math.random() * 2) == 1) {
        geneticData.genes.set(i, true);
      }
    }
    geneticData.genes.set(0, male);
    return geneticData;
  }

  /**
   * generate genes that could result from parents doing mating
   * @param mom
   * @param dad
   * @return
   */
  public final static GeneticData fromMitosis(GeneticData mom, GeneticData dad) {
    final GeneticData geneticData = new GeneticData();
    // it's randomly selecting chromesomes from one parent or other
    for (int i = 0; i < CHROMESOMES; i++) {
      final boolean momJeans = Math.floor(Math.random() * 2) == 1;
      if (momJeans) {
        geneticData.genes.set(i, mom.genes.get(i));
      } else {
        geneticData.genes.set(i, dad.genes.get(i));
      }
    }

    // assume disorders are congenital
//    geneticData.hasDisorder = mom.hasDisorder() || dad.hasDisorder() || Math.random() > disorderProbability(mom, dad);
    geneticData.hasDisorder = mom.hasDisorder() || dad.hasDisorder() || Math.random() < disorderProbability(mom, dad);
    return geneticData;
  }


  /**
   * the higher the amount of DNA shared between parents, the higher the probability the child will have a disorder
   */
  private final static double disorderProbability(final GeneticData mom, final GeneticData dad) {
    int sameDNA = 0;
    for (int chromesome = 0; chromesome < CHROMESOMES; chromesome++) {
      if (mom.genes.get(chromesome) == dad.genes.get(chromesome)) {
        sameDNA++;
      }
    }
    double pct =  0.5 * Math.pow(Math.E, (double) sameDNA / (double) CHROMESOMES) - DISORD_COEFFICIENT;
    return pct;
//    return (double) sameDNA / (double) CHROMESOMES;
  }

  public boolean hasDisorder() {
    return this.hasDisorder;
  }

  public String getGender() {
    return this.genes.get(0) ? GeneticConstants.MALE : GeneticConstants.FEMALE;
  }
}
