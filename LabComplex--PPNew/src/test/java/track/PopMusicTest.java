package track;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static org.junit.jupiter.api.Assertions.*;

class PopMusicTest {
    @InjectMocks
    PopMusic popMusic;

    @Test
    void testGetGenre() {
        popMusic = new PopMusic();
        assertEquals("Pop", popMusic.getGenre());
    }
}