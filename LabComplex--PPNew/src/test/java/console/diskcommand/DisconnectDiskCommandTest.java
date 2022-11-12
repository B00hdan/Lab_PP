package console.diskcommand;

import info.Disk;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DisconnectDiskCommandTest {
    @Mock
    Disk mockDisk;

    @InjectMocks
    DisconnectDiskCommand disconnectDiskCommand;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testExecuteConnectionFalse() {
        when(mockDisk.connectionStatus()).thenReturn(false);
        assertFalse(disconnectDiskCommand.execute(new String[]{"disconnect"}));
        verify(mockDisk, times(0)).disconnectDisk();
    }

    @Test
    void testExecuteNotCorrectParams() {
        when(mockDisk.connectionStatus()).thenReturn(true);
        assertFalse(disconnectDiskCommand.execute(new String[]{"disconnect", "test"}));
        verify(mockDisk, times(0)).disconnectDisk();
    }

    @Test
    void testExecuteCorrect() {
        when(mockDisk.connectionStatus()).thenReturn(true);
        assertFalse(disconnectDiskCommand.execute(new String[]{"disconnect"}));
        verify(mockDisk).disconnectDisk();
    }

    @Test
    void testGetInfo(){
        assertNotEquals("", disconnectDiskCommand.getInfo());
    }
}