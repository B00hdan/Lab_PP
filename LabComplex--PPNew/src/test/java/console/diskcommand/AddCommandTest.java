package console.diskcommand;

import info.Disk;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AddCommandTest {
    @Mock
    Disk mockDisk;

    @InjectMocks
    AddCommand addCommand;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testExecuteConnectionFalse() {
        when(mockDisk.connectionStatus()).thenReturn(false);
        assertFalse(addCommand.execute(new String[]{"add", "track", "name=testName",
                "duration=00:00:01", "genre=Pop"}));
    }

    @Test
    void testExecuteNotCorrectParams() {
        when(mockDisk.connectionStatus()).thenReturn(true);
        assertFalse(addCommand.execute(new String[]{"add", "track"}));
    }

    @Test
    void testExecuteAddTrackCorrect() {
        String[] newParams = new String[]{"add", "track", "name=testName",
                "duration=00:00:01", "genre=Pop"};
        when(mockDisk.connectionStatus()).thenReturn(true);
        when(mockDisk.addNewTrackOnDisk(newParams)).thenReturn(true);
        assertTrue(addCommand.execute(newParams));
        verify(mockDisk).addNewTrackOnDisk(newParams);
    }

    @Test
    void testExecuteAddTrackNotCorrect() {
        String[] newParams = new String[]{"add", "track", "name=testName", "duration=00:00:01"};
        when(mockDisk.connectionStatus()).thenReturn(true);
        when(mockDisk.addNewTrackOnDisk(newParams)).thenReturn(false);
        assertFalse(addCommand.execute(newParams));
        verify(mockDisk).addNewTrackOnDisk(newParams);
    }

    @Test
    void testExecuteAddAlbumCorrect() {
        String[] newParams = new String[]{"add", "album", "testName", "testWay"};
        when(mockDisk.connectionStatus()).thenReturn(true);
        when(mockDisk.addAlbumOnDisk(newParams)).thenReturn(true);
        assertTrue(addCommand.execute(newParams));
        verify(mockDisk).addAlbumOnDisk(newParams);
    }

    @Test
    void testExecuteAddAlbumNotCorrect() {
        String[] newParams = new String[]{"add", "album", "testName", "testWay"};
        when(mockDisk.connectionStatus()).thenReturn(true);
        when(mockDisk.addAlbumOnDisk(newParams)).thenReturn(false);
        assertFalse(addCommand.execute(newParams));
        verify(mockDisk).addAlbumOnDisk(newParams);
    }

    @Test
    void testGetInfo(){
        assertNotEquals("", addCommand.getInfo());
    }
}