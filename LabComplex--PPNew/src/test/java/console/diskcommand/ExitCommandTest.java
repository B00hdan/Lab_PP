package console.diskcommand;

import info.Disk;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ExitCommandTest {
    @Mock
    Disk mockDisk;

    @Spy
    @InjectMocks
    ExitCommand spyExitCommand;


    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testExecuteConnectionFalse() {
        when(mockDisk.connectionStatus()).thenReturn(false);
        doNothing().when(spyExitCommand).exit(0);
        assertFalse(spyExitCommand.execute(new String[]{"exit"}));
        verify(spyExitCommand).exit(0);
    }

    @Test
    void testExecuteConnectionTrue() {
        when(mockDisk.connectionStatus()).thenReturn(true);
        doNothing().when(spyExitCommand).exit(0);
        assertFalse(spyExitCommand.execute(new String[]{"exit"}));
        verify(spyExitCommand).exit(0);
    }

    @Test
    void testGetInfo(){
        assertNotEquals("", spyExitCommand.getInfo());
    }
}