package track;

import java.util.Comparator;

public class SortByDuration implements Comparator<MusicTrack> {
    @Override
    public int compare(MusicTrack o1, MusicTrack o2) {
        return o1.getDuration().compareTo(o2.getDuration());
    }
}
