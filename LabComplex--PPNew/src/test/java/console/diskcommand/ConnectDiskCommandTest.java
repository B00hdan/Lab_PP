package console.diskcommand;

import info.Disk;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ConnectDiskCommandTest {
    @Mock
    Disk mockDisk;

    @InjectMocks
    ConnectDiskCommand connectDiskCommand;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testExecuteNotCorrectParams() {
        when(mockDisk.connectionStatus()).thenReturn(false);
        assertFalse(connectDiskCommand.execute(new String[]{"connect", "test.txt"}));
        assertFalse(connectDiskCommand.execute(new String[]{"connect", "test.txt", "20", "new"}));
        verify(mockDisk, times(0)).connectDisk("test.txt", 50);
        verify(mockDisk, times(0)).connectDisk("newFile.txt", 50);
    }

    @Test
    void testExecuteCorrectParamsDefaultFile() {
        when(mockDisk.connectionStatus()).thenReturn(false);
        assertFalse(connectDiskCommand.execute(new String[]{"connect"}));
        verify(mockDisk).connectDisk("newFile.txt", 50);
    }

    @Test
    void testExecuteCorrectParamsNewFile() {
        when(mockDisk.connectionStatus()).thenReturn(false);
        assertFalse(connectDiskCommand.execute(new String[]{"connect", "test.txt", "20"}));
        verify(mockDisk).connectDisk("test.txt", 20);
    }

    @Test
    void testGetInfo(){
        assertNotEquals("", connectDiskCommand.getInfo());
    }
}