package console.diskcommand;

import info.Disk;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EditCommandTest {
    @Mock
    Disk mockDisk;

    @InjectMocks
    EditCommand editCommand;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testExecuteConnectionFalse() {
        when(mockDisk.connectionStatus()).thenReturn(false);
        assertFalse(editCommand.execute(new String[]{"edit", "name=testName", "author=newName"}));
    }

    @Test
    void testExecuteNotCorrectParams() {
        when(mockDisk.connectionStatus()).thenReturn(true);
        assertFalse(editCommand.execute(new String[]{"edit", "author=newName"}));
        assertFalse(editCommand.execute(new String[]{"edit", "author=newName", "duration=00:00:02"}));
    }

    @Test
    void testExecuteNotCorrectEdit() {
        String[] tempParams = new String[]{"edit", "name=testName", "name=newName"};
        when(mockDisk.connectionStatus()).thenReturn(true);
        when(mockDisk.changeTrackParamsOnDisk(tempParams)).thenReturn(false);
        assertFalse(editCommand.execute(tempParams));
    }

    @Test
    void testExecuteCorrectEdit() {
        String[] tempParams = new String[]{"edit", "name=testName", "author=newName"};
        when(mockDisk.connectionStatus()).thenReturn(true);
        when(mockDisk.changeTrackParamsOnDisk(tempParams)).thenReturn(true);
        assertTrue(editCommand.execute(tempParams));
    }

    @Test
    void testUndo() {
        editCommand.undo(new String[]{});
        verify(mockDisk).returnParamsForLastModified();
    }

    @Test
    void testGetInfo(){
        assertNotEquals("", editCommand.getInfo());
    }
}