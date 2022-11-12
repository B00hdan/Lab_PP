package track;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MusicTrackTest {
    @InjectMocks
    MusicTrack track;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetSetDateOfWriting() {
        track.setDateOfWriting("temp");
        assertEquals("temp", track.dateOfWriting);
        assertEquals("temp", track.getDateOfWriting());
    }

    @Test
    void testGetSetDateOfPublication() {
        track.setDateOfPublication("temp");
        assertEquals("temp", track.dateOfPublication);
        assertEquals("temp", track.getDateOfPublication());
    }

    @Test
    void testGetSetAuthor() {
        track.setAuthor("temp");
        assertEquals("temp", track.author);
        assertEquals("temp", track.getAuthor());
    }

    @Test
    void testGetSetAlbumName() {
        track.setAlbumName("temp");
        assertEquals(track.albumName, "temp");
        assertEquals(track.getAlbumName(), "temp");
    }

    @Test
    void testGetGenre() {
        assertEquals("", track.getGenre());
    }

    @Test
    void testBuilderOnlyMainParams() {
        Map<String,String> params = new HashMap<>(){{
            put("duration", "00:00:00");
            put("name", "temp");
        }};
        assertTrue(track.builder(params));
        assertEquals(params.get("name"), track.name);
        assertEquals(params.get("duration"), track.duration);
    }

    @Test
    void testBuilderIncorrectDurationFormat() {
        Map<String,String> params = new HashMap<>(){{
            put("duration", "0000");
            put("name", "temp");
        }};
        assertFalse(track.builder(params));
    }

    @Test
    void testBuilderWithoutName() {
        Map<String,String> params = new HashMap<>(){{
            put("author", "temp");
            put("duration", "00:00:00");
        }};
        assertFalse(track.builder(params));
    }

    @Test
    void testBuilderWithoutDuration() {
        Map<String,String> params = new HashMap<>(){{
            put("author", "temp");
            put("name", "temp");
        }};
        assertFalse(track.builder(params));
    }

    @Test
    void testBuilderEmptyMap() {
        Map<String,String> params = new HashMap<>();
        assertFalse(track.builder(params));
    }

    @Test
    void testBuilderIncorrectDateWritingFormat() {
        Map<String,String> params = new HashMap<>(){{
            put("duration", "00:00:00");
            put("name", "temp");
            put("dateOfWriting", "0000");
        }};
        assertFalse(track.builder(params));
    }

    @Test
    void testBuilderIncorrectDatePublicationFormat() {
        Map<String,String> params = new HashMap<>(){{
            put("duration", "00:00:00");
            put("name", "temp");
            put("dateOfPublication", "0000");
        }};
        assertFalse(track.builder(params));
    }

    @Test
    void testBuilderAllCorrectParams() {
        Map<String,String> params = new HashMap<>(){{
            put("album", "temp");
            put("duration", "00:00:00");
            put("name", "temp");
            put("dateOfWriting", "0000-00-00");
            put("author", "temp");
            put("dateOfPublication", "0000-00-00");
        }};
        assertTrue(track.builder(params));
        assertEquals(params.get("name"), track.name);
        assertEquals(params.get("author"), track.author);
        assertEquals(params.get("duration"), track.duration);
        assertEquals(params.get("album"), track.albumName);
        assertEquals(params.get("dateOfWriting"), track.dateOfWriting);
        assertEquals(params.get("dateOfPublication"), track.dateOfPublication);

    }

    @Test
    void testToString() {
        track.name = "testName";
        assertThat(track.toString()).contains("testName");
    }
}