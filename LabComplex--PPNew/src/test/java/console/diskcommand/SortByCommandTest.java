package console.diskcommand;

import info.Disk;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class SortByCommandTest {

    @Mock
    Disk mockDisk;

    @InjectMocks
    SortByCommand sortByCommand;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testExecuteConnectionFalse() {
        when(mockDisk.connectionStatus()).thenReturn(false);
        assertFalse(sortByCommand.execute(new String[]{"sort", "name"}));
        verify(mockDisk, times(0)).sortOnDiskBy("name");
    }

    @Test
    void testExecuteNotCorrectParams() {
        when(mockDisk.connectionStatus()).thenReturn(true);
        assertFalse(sortByCommand.execute(new String[]{"sort"}));
        assertFalse(sortByCommand.execute(new String[]{"sort", "author"}));
        verify(mockDisk, times(0)).sortOnDiskBy(anyString());
    }

    @Test
    void testExecuteCorrectParams() {
        when(mockDisk.connectionStatus()).thenReturn(true);
        assertFalse(sortByCommand.execute(new String[]{"sort", "name"}));
        verify(mockDisk).sortOnDiskBy("name");
    }

    @Test
    void testGetInfo(){
        assertNotEquals("", sortByCommand.getInfo());
    }
}