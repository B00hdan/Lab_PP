package track;

public class PopMusic extends MusicTrack {
    String genre;
    public PopMusic(){
        this.genre = "Pop";
    }

    @Override
    public String getGenre() {
        return genre;
    }
}
