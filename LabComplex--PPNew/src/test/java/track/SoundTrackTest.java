package track;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SoundTrackTest {
    @InjectMocks
    SoundTrack track;

    @BeforeEach
    public void setup(){
        track = Mockito.mock(SoundTrack.class,
                Mockito.CALLS_REAL_METHODS);
    }

    @Test
    void testGetSetName() {
        track.setName("temp");
        assertEquals("temp", track.name);
        assertEquals("temp", track.getName());
    }

    @Test
    void testGetSetDuration() {
        track.setDuration("temp");
        assertEquals("temp", track.duration);
        assertEquals("temp", track.getDuration());
    }

    @Test
    void testSetIsDeleteStatus() {
        track.setDeleteStatus(true);
        assertTrue(track.deleteStatus);
        assertTrue(track.isDeleteStatus());
    }
}