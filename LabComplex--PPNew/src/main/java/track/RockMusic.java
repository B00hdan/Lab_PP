package track;

public class RockMusic extends MusicTrack {
    String genre;
    public RockMusic(){
        this.genre = "Rock";
    }

    @Override
    public String getGenre() {
        return genre;
    }
}
