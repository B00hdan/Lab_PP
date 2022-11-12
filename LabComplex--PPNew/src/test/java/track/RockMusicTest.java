package track;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static org.junit.jupiter.api.Assertions.*;

class RockMusicTest {
    @InjectMocks
    RockMusic rockMusic;

    @Test
    void testGetGenre() {
        rockMusic = new RockMusic();
        assertEquals("Rock", rockMusic.getGenre());
    }
}