package minecraft;

import java.util.*;

import static minecraft.GeneticConstants.*;

public class App {
  public static void main(String[] args) {
    System.out.println("Hello World!");

    final GeneticData firstMom = GeneticData.random(false);
    final GeneticData firstDad = GeneticData.random(true);

    final int numGenerations = 15;
    final int childrenPerCouple = 5;
    final boolean mateWithDisordereds = MATE_DISORDEREDS;

    final List<GeneticData> generationPopulation = new ArrayList<>();
    generationPopulation.add(firstDad);
    generationPopulation.add(firstMom);

    for (int i_gen = 0; i_gen < numGenerations; i_gen++) {
      final List<GeneticData> nextGenerationPop = new ArrayList<>();

      // find males and females in current population so we can pair them for mating
      final List<GeneticData> males = new ArrayList<>();
      final List<GeneticData> females = new ArrayList<>();
      final List<GeneticData> disordered = new ArrayList<>();

      for (final GeneticData geneticData : generationPopulation) {
        if (geneticData.hasDisorder()) {
          disordered.add(geneticData);
          if (!mateWithDisordereds) {
            continue;
          }
        }

        if (MALE.equals(geneticData.getGender())) {
          males.add(geneticData);
        } else {
          females.add(geneticData);
        }
      }

      // print statistics about the population
      System.out.printf(
          // csv
          // "%s,%s,%s,%s,%s\n",

          // nice
          "generation=%s, population=%s, males=%s, females=%s, hasDisorder=%s, pctDisordered=%.2f\n",
          i_gen,
          generationPopulation.size(),
          males.size(),
          females.size(),
          disordered.size(),
         (double) disordered.size() / (double) generationPopulation.size() * 100

      );

      // mating is random and monogomous (no SINNERS!)
      Collections.shuffle(males);
      Collections.shuffle(females);


      int numCouples = Math.min(males.size(), females.size());
      for (int i_couple = 0; i_couple < numCouples; i_couple++) {
        final GeneticData dad = males.get(i_couple);
        final GeneticData mom = females.get(i_couple);
        // force the couples to produce children:
        for (int i_child = 0; i_child < childrenPerCouple; i_child++) {
          nextGenerationPop.add(GeneticData.fromMitosis(mom, dad));
        }
      }

      generationPopulation.clear();
      generationPopulation.addAll(nextGenerationPop);
    }

  }
}
