package track;

public class ElectronicMusic extends MusicTrack {
    String genre;

    public ElectronicMusic(){
        this.genre = "Electronic";
    }

    @Override
    public String getGenre() {
        return genre;
    }

}
