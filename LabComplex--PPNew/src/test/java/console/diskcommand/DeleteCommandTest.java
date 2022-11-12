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

class DeleteCommandTest {
    @Mock
    Disk mockDisk;

    @InjectMocks
    DeleteCommand deleteCommand;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testExecuteConnectionFalse() {
        when(mockDisk.connectionStatus()).thenReturn(false);
        assertFalse(deleteCommand.execute(new String[]{"delete", "track", "testName"}));
    }

    @Test
    void testExecuteNotCorrectParams() {
        when(mockDisk.connectionStatus()).thenReturn(true);
        assertFalse(deleteCommand.execute(new String[]{"delete", "track"}));
    }

    @Test
    void testExecuteDeleteTrackExist() {
        when(mockDisk.connectionStatus()).thenReturn(true);
        when(mockDisk.deleteTrackFromDisk("testName")).thenReturn(true);
        assertTrue(deleteCommand.execute(new String[]{"delete", "track", "testName"}));
        verify(mockDisk).deleteTrackFromDisk("testName");
    }

    @Test
    void testExecuteDeleteTrackNotExist() {
        when(mockDisk.connectionStatus()).thenReturn(true);
        when(mockDisk.deleteTrackFromDisk("testName")).thenReturn(false);
        assertFalse(deleteCommand.execute(new String[]{"delete", "track", "testName"}));
        verify(mockDisk).deleteTrackFromDisk("testName");
    }

    @Test
    void testExecuteDeleteAlbumExist() {
        when(mockDisk.connectionStatus()).thenReturn(true);
        when(mockDisk.deleteAlbumFromDisk("testName")).thenReturn(true);
        assertTrue(deleteCommand.execute(new String[]{"delete", "album", "testName"}));
        verify(mockDisk).deleteAlbumFromDisk("testName");
    }

    @Test
    void testExecuteDeleteAlbumNotExist() {
        when(mockDisk.connectionStatus()).thenReturn(true);
        when(mockDisk.deleteAlbumFromDisk("testName")).thenReturn(false);
        assertFalse(deleteCommand.execute(new String[]{"delete", "album", "testName"}));
        verify(mockDisk).deleteAlbumFromDisk("testName");
    }

    @Test
    void testUndo() {
        deleteCommand.undo(new String[]{});
        verify(mockDisk).restoreTrackLastOnDisk();
    }

    @Test
    void testGetInfo(){
        assertNotEquals("", deleteCommand.getInfo());
    }
}