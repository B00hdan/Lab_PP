package console.diskcommand;

import info.Disk;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FindByCommandTest {
    private final String[] testParams = new String[]{"find", "all"};

    @Mock
    Disk mockDisk;

    @InjectMocks
    FindByCommand findByCommand;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testExecuteConnectionFalse() {
        when(mockDisk.connectionStatus()).thenReturn(false);
        assertFalse(findByCommand.execute(testParams));
        verify(mockDisk, times(0)).printFromDiskAllBy(testParams);
    }

    @Test
    void testExecuteCorrectExecute() {
        when(mockDisk.connectionStatus()).thenReturn(true);
        when(mockDisk.printFromDiskAllBy(testParams)).thenReturn(true);
        assertFalse(findByCommand.execute(testParams));
        verify(mockDisk).printFromDiskAllBy(testParams);
    }

    @Test
    void testExecuteNotCorrectExecute() {
        when(mockDisk.connectionStatus()).thenReturn(true);
        when(mockDisk.printFromDiskAllBy(testParams)).thenReturn(false);
        assertFalse(findByCommand.execute(testParams));
        verify(mockDisk).printFromDiskAllBy(testParams);
    }

    @Test
    void testGetInfo(){
        assertNotEquals("", findByCommand.getInfo());
    }
}