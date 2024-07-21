public class Main {
    public static void main(String[] args)
    {
        MakeAlgorithm myGeneticAlgorithm = new MakeAlgorithm(args[0], Integer.parseInt(args[1]),
                Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]));
        System.out.println("The best solution in form of {GuestId=[ListOfGuestPropositions] is:");
        System.out.println(myGeneticAlgorithm.startAlgorithm());
    }
}
