import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

class InputParser {
    static final List<Guest> guestsList = new ArrayList<>();
    public static List<Guest> getInputFromFile(String path, String delimiter){
        try{
            File myFile = new File(path);
            Scanner myScanner = new Scanner(myFile);
            myScanner.useDelimiter(delimiter);

            while(myScanner.hasNext())
            {
                String[] data = myScanner.nextLine().split(delimiter);
                int guestId = Integer.parseInt(data[0]);
                Set<String> characteristicsPossessed = new HashSet<>(Arrays.asList(data[1].split(",")));
                Set<String> preferences = new HashSet<>(Arrays.asList(data[2].split(",")));
                guestsList.add(new Guest(guestId, characteristicsPossessed, preferences));
            }
            myScanner.close();
        } catch (FileNotFoundException fnfe){
            System.out.println("File not found!");
            System.exit(1);
        }
        return guestsList;
    }
}
