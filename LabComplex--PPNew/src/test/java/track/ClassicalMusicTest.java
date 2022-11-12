package track;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static org.junit.jupiter.api.Assertions.*;

class ClassicalMusicTest {
    @InjectMocks
    ClassicalMusic classicalMusic;

    @Test
    void testGetGenre() {
        classicalMusic = new ClassicalMusic();
        assertEquals("Classical", classicalMusic.getGenre());
    }
}