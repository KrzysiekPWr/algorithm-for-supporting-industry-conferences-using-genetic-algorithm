import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MakeAlgorithm {
    private final int NUMBER_OF_GENERATIONS;
    private final String FILE_PATH;
    private final int sizeOfPropositionsList;
    private final int numberOfParticipants;
    private final int numberOfDescendants;
    private Map<Integer, Set<Integer>> currentBestSolution = new HashMap<>();
    private Map<Integer, Set<Integer>> previousBestSolution = new HashMap<>();

    MakeAlgorithm(String filePath, int numberOfGenerations, int sizeOfPropositionsList, int numberOfParticipants, int numberOfDescendants){
        NUMBER_OF_GENERATIONS = numberOfGenerations;
        FILE_PATH = filePath;
        this.sizeOfPropositionsList = sizeOfPropositionsList;
        this.numberOfParticipants = numberOfParticipants;
        this.numberOfDescendants = numberOfDescendants;

    }
    public Map<Integer, Set<Integer>> startAlgorithm(){

        List<Guest> guestList = InputParser.getInputFromFile(FILE_PATH, "\t");

        GeneticAlgorithm myGeneticAlgorithm = new GeneticAlgorithm(guestList, sizeOfPropositionsList, numberOfParticipants, numberOfDescendants);
        myGeneticAlgorithm.initializePopulation();
        myGeneticAlgorithm.createIdAndFitnessList();

        int iterationsWithoutBetterSolution = 0;
        //MAIN LOOP
        for (int i = 0; i < NUMBER_OF_GENERATIONS; i++)
        {
            currentBestSolution = myGeneticAlgorithm.selectFunction();
            myGeneticAlgorithm.createIdAndFitnessList();
            if(currentBestSolution.equals(previousBestSolution))
            {
                iterationsWithoutBetterSolution++;
            }
            else iterationsWithoutBetterSolution = 0;
            if(iterationsWithoutBetterSolution >= NUMBER_OF_GENERATIONS/4) break;
            previousBestSolution = currentBestSolution;
        }
        return currentBestSolution;
    }

    private static int countFitness(List<List<Integer>> idAndFitnessList){
        int populationFitness = 0;
        for (List<Integer> idAndFitness: idAndFitnessList )
        {
            populationFitness += idAndFitness.get(1);
        }
        return  populationFitness;
    }
}
