package track;

public class CountryMusic extends MusicTrack {
    String genre;

    public CountryMusic(){
        this.genre = "Country";
    }

    @Override
    public String getGenre() {
        return genre;
    }
}