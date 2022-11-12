package track;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static org.junit.jupiter.api.Assertions.*;

class CountryMusicTest {
    @InjectMocks
    CountryMusic countryMusic;

    @Test
    void testGetGenre() {
        countryMusic = new CountryMusic();
        assertEquals("Country", countryMusic.getGenre());
    }
}