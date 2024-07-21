import java.util.Set;

class Guest {
    int guestId;
    Set<String> characteristicsPosessed;
    Set<String>  characteristicsWanted;

    public Guest(int guestId, Set<String> characteristicsPosessed, Set<String> characteristicsWanted){
        this.guestId = guestId;
        this.characteristicsWanted = characteristicsWanted;
        this.characteristicsPosessed = characteristicsPosessed;
    }

    @Override
    public String toString() {
        return guestId + ";" + characteristicsPosessed.toString() + ";" + characteristicsWanted.toString();
    }
}
