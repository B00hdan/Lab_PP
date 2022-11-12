package console.diskcommand;

import info.Disk;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CalculateDurationCommandTest {
    @Mock
    Disk mockDisk;

    @InjectMocks
    CalculateDurationCommand calculateDurationCommand;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testExecuteConnectionFalse() {
        when(mockDisk.connectionStatus()).thenReturn(false);
        assertFalse(calculateDurationCommand.execute(new String[]{"calculateDuration"}));
        verify(mockDisk, times(0)).calculateDurationOfAllTracks();
    }

    @Test
    void testExecuteConnectionNotCorrectParams() {
        when(mockDisk.connectionStatus()).thenReturn(true);
        assertFalse(calculateDurationCommand.execute(new String[]{"calculateDuration", "add"}));
        verify(mockDisk, times(0)).calculateDurationOfAllTracks();
    }

    @Test
    void testExecuteConnectionCorrect() {
        when(mockDisk.connectionStatus()).thenReturn(true);
        assertFalse(calculateDurationCommand.execute(new String[]{"calculateDuration"}));
        assertFalse(calculateDurationCommand.execute(new String[]{"calculateDuration", "add"}));
        verify(mockDisk).calculateDurationOfAllTracks();
    }

    @Test
    void testGetInfo(){
        assertNotEquals("", calculateDurationCommand.getInfo());
    }
}