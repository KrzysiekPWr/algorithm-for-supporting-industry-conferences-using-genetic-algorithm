import java.util.*;

import static java.util.Arrays.asList;

public class GeneticAlgorithm {
    private final int SIZE_OF_PROPOSITIONS_LIST;
    private final int NUMBER_OF_PARTICIPANTS;
    private final int NUMBER_OF_DESCENDANTS;
    static List<Guest> GUEST_LIST;
     List<Map<Integer, Set<Integer>>> population;
     List<List<Integer>> populationIdAndFitnessList;
     List<Map<Integer, Set<Integer>>> newPopulation;

    public GeneticAlgorithm(List<Guest> guestList, int sizeOfPropositionsList, int numberOfParticipants, int numberOfDescendants) {
        SIZE_OF_PROPOSITIONS_LIST = sizeOfPropositionsList;
        NUMBER_OF_PARTICIPANTS = numberOfParticipants;
        NUMBER_OF_DESCENDANTS = numberOfDescendants;
        population = new ArrayList<>();
        populationIdAndFitnessList = new ArrayList<>();
        newPopulation = new ArrayList<>();
        GUEST_LIST = guestList;
    }


    public List<Map<Integer, Set<Integer>>> initializePopulation(){
        Random random = new Random();
        for (int i = 0; i < NUMBER_OF_PARTICIPANTS; i++)
        {
            //create a new participant
            Map<Integer, Set<Integer>> participant = new LinkedHashMap<>();

            for (Guest guest : GUEST_LIST)
            {
                List<Integer> availableGuestIds = new ArrayList<>();

                for (int j = 1; j < GUEST_LIST.size()+1; j++) {
                    availableGuestIds.add(j);
                }

                availableGuestIds.remove(guest.guestId-1);
                Set<Integer> guestPropositionsList = new HashSet<>();

                //!!!BE SURE THAT THERE ARE ENOUGH GUESTS TO MAKE THESE PROPOSITIONS!!!
                // OR ELSE THIS LOOP MAY BE INFINITE!!!
                //try with copying the whole list and poping each element after it was chosen
                while (guestPropositionsList.size() != SIZE_OF_PROPOSITIONS_LIST)
                {
                    //pick random number from 1 to maxGuestId
                    int randInt = random.nextInt(availableGuestIds.size());
                    guestPropositionsList.add(availableGuestIds.remove(randInt));
                }
                participant.put(guest.guestId, guestPropositionsList);
            }
            population.add(participant);
        }
        return population;
    }
    // REMEMBER TO CHANGE PUBLIC STATIC TO PRIVATE!!!!!!!!!!!

    public void createIdAndFitnessList(){
        List<List<Integer>> populationIdAndFitnessListTemp = new ArrayList<>();

        int participantId = 0;
        for (Map<Integer, Set<Integer>> participant: population)
        {
            int participantFitnessScore = 0;
            // for each guest in the whole participant List guestId=key
            participantFitnessScore = getParticipantFitnessScore(participant);
            populationIdAndFitnessListTemp.add(asList(participantId, participantFitnessScore));
            participantId++;
        }
        populationIdAndFitnessList.clear();
        populationIdAndFitnessList.addAll(populationIdAndFitnessListTemp);
    }

    private int getParticipantFitnessScore(Map<Integer, Set<Integer>> participant) {
        int participantFitnessScore = 0;
        for (Integer guestId : participant.keySet())
        {
            int singleGuestScore = 0;
            Guest guest = GUEST_LIST.get(guestId-1);
            Set<Integer> guestPropositonList = participant.get(guestId);

            for (Integer propositionGuestId: guestPropositonList)
            {
                //the guestList[0] would be guest n.o 1 therefore guestId-1
                Guest propositonGuest = GUEST_LIST.get(propositionGuestId-1);
                singleGuestScore += checkCompatibilityOfGuests(guest, propositonGuest);
            }
            participantFitnessScore += singleGuestScore;
        }
        return participantFitnessScore;
    }

    public Map<Integer, Set<Integer>> selectFunction(){
        //Create a new population
        List<Map<Integer, Set<Integer>>> newPopulationTemp = new ArrayList<>();
        Collections.sort(populationIdAndFitnessList, new Comparator<List<Integer>>() {
            @Override
            public int compare(List<Integer> o1, List<Integer> o2) {
                return o1.get(1).compareTo(o2.get(1));
            }
        }.reversed());

        Map<Integer, Set<Integer>> bestSolution = population.get(populationIdAndFitnessList.get(0).get(0));

        for (int i = 0; i < NUMBER_OF_DESCENDANTS; i++) {
            Map<Integer, Set<Integer>> participantToAdd = population.get(populationIdAndFitnessList.get(i).get(0));
            newPopulationTemp.add(participantToAdd);
        }

        while (newPopulationTemp.size() < NUMBER_OF_PARTICIPANTS)
        {
            List<Integer> parentsIdList = selectPair();

            List<Map<Integer, Set<Integer>>> listOfTwoChildren = crossoverFunction(parentsIdList, population);
            newPopulationTemp.add(listOfTwoChildren.get(0));
            //to avoid adding two participants when there is only one spot
            if (newPopulationTemp.size() != NUMBER_OF_PARTICIPANTS) newPopulationTemp.add(listOfTwoChildren.get(1));
        }
        if(calculateFitnessOfPopulation(newPopulationTemp) > calculateFitnessOfPopulation(population))
        {
        population.clear();
        population.addAll(newPopulationTemp);
        }

        return bestSolution;
    }

    private int calculateFitnessOfPopulation(List<Map<Integer, Set<Integer>>> population){
        int populationFitnessScore = 0;
        for (Map<Integer, Set<Integer>> participant: population)
        {
            populationFitnessScore += getParticipantFitnessScore(participant);
        }
        return populationFitnessScore;
    }

    //pick a random guestId and switch their solution's places ex. 13:[1,2,3] & 11:[4,5,6] -> 13:[4,5,6] & 11:[1,2,3]
    private List<Map<Integer, Set<Integer>>> crossoverFunction(List<Integer> parentsIdList,
                                                                      List<Map<Integer, Set<Integer>>> population ){
        List<Map<Integer, Set<Integer>>> childrenList = new ArrayList<>();

        Random rand = new Random();
        int randomGuestId = rand.nextInt(1, GUEST_LIST.size()+1);

        Map<Integer, Set<Integer>> parent1 = population.get(parentsIdList.get(0));
        Map<Integer, Set<Integer>> parent2 = population.get(parentsIdList.get(1));

        Map<Integer, Set<Integer>> child1 = new HashMap<>();
        Map<Integer, Set<Integer>> child2 = new HashMap<>();
        for (int key: parent1.keySet()) child1.put(key, parent1.get(key));
        for (int key: parent2.keySet()) child2.put(key, parent1.get(key));

        Set<Integer> guestPropositionSetBuffer = child1.get(randomGuestId);
        child1.put(randomGuestId, parent2.get(randomGuestId));
        child2.put(randomGuestId, guestPropositionSetBuffer);

        childrenList.add(child1);
        childrenList.add(child2);

        return childrenList;
    }

//mutation function
//    public List<Map<Integer, Set<Integer>>> mutationFunction(double mutationProbability){
//        Random rand = new Random();
//        for (int participantId = 0; participantId < population.size(); participantId++)
//        {
//            for (int guestId = 1; guestId <= GUEST_LIST.size(); guestId++) {
//                Map<Integer, Set<Integer>> participant = population.get(participantId);
//                double randomNumber = rand.nextDouble();
//                if (randomNumber <= mutationProbability) {
//                    Set<Integer> propositionsSetBuffer = participant.get(guestId);
//
//                    int randomGuestId = rand.nextInt(1, GUEST_LIST.size() + 1);
//                    //put a Set of propositions from a random Guest to Guest who has id of guestId
//                    participant.put(guestId, participant.get(randomGuestId));
//                    //put the Set of propositions from the Guest who has id of guestId to random Guest
//                    participant.put(randomGuestId, propositionsSetBuffer);
//                }
//            }
//        }
//        return  population;
//    }

    private int checkCompatibilityOfGuests(Guest guest, Guest propositionGuest){
        int score = 0;
        for (String preference: guest.characteristicsWanted)
        {
            for (String attribute: propositionGuest.characteristicsPosessed)
            {
                if(preference.equals(attribute))
                {
                    score++;
                }
            }
        }
        return score;
    }

    private List<Integer> selectPair(){
        List<Integer> parentsIdList = new ArrayList<>();

        int totalSumOfFitnesses = 0;
        for (List<Integer> entry: populationIdAndFitnessList)
        {
            totalSumOfFitnesses += entry.get(1);
        }

        Random rand = new Random();

        while (parentsIdList.size() != 2)
        {
            int randomNumber = rand.nextInt(totalSumOfFitnesses);
            int partialSumOfFitnesses = 0;
            List<Integer> usedParentIds = new ArrayList<>();

            for (int i=populationIdAndFitnessList.size()-1; i>=0; i--)
            {
                partialSumOfFitnesses += populationIdAndFitnessList.get(i).get(1);
                int idOfParent = populationIdAndFitnessList.get(i).get(0);
                if (partialSumOfFitnesses >= randomNumber && !parentsIdList.contains(idOfParent))
                {
                    parentsIdList.add(idOfParent);
                    usedParentIds.add(idOfParent);
                    break;
                }
            }
        }
        return parentsIdList;
    }
}