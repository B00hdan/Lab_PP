package track;

import java.util.Map;
import java.util.Objects;

public class MusicTrack extends SoundTrack {
    protected String dateOfWriting = "";
    protected String dateOfPublication = "";
    protected String author = "";
    protected String albumName = "";

    public String getDateOfWriting() {
        return dateOfWriting;
    }
    public void setDateOfWriting(String dateOfWriting) {
        this.dateOfWriting = dateOfWriting;
    }

    public String getDateOfPublication() {
        return dateOfPublication;
    }
    public void setDateOfPublication(String dateOfPublication) {
        this.dateOfPublication = dateOfPublication;
    }

    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAlbumName() {
        return albumName;
    }
    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getGenre() {
        return "";
    }

    public boolean builder(Map<String, String> params){
        for (Map.Entry<String, String> param : params.entrySet()) {
            switch (param.getKey()) {
                case "name":
                    setName(param.getValue());
                    break;
                case "author":
                    setAuthor(param.getValue());
                    break;
                case "duration":
                    if(param.getValue().matches("\\d{2}:\\d{2}:\\d{2}"))
                        setDuration(param.getValue());
                    else return false;
                    break;
                case "dateOfWriting":
                    if(param.getValue().matches("\\d{4}-\\d{2}-\\d{2}"))
                        setDateOfWriting(param.getValue());
                    else return false;
                    break;
                case "dateOfPublication":
                    if(param.getValue().matches("\\d{4}-\\d{2}-\\d{2}"))
                        setDateOfPublication(param.getValue());
                    else return false;
                    break;
                case "album":
                    setAlbumName(param.getValue());
            }
        }
        return !Objects.equals(name, "") || !Objects.equals(duration, "");
    }
    @Override
    public String toString() {
        return "Name=" + name + " Duration=" + duration
                + " Author=" + author + " DateOfWriting=" + dateOfWriting
                + " DateOfPublication=" + dateOfPublication + " Album=" + albumName + " Genre=" + getGenre();
    }
}