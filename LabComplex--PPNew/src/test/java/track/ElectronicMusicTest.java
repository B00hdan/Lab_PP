package track;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static org.junit.jupiter.api.Assertions.*;

class ElectronicMusicTest {
    @InjectMocks
    ElectronicMusic electronicMusic;

    @Test
    void testGetGenre() {
        electronicMusic = new ElectronicMusic();
        assertEquals("Electronic", electronicMusic.getGenre());
    }
}