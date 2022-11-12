package console;

import console.diskcommand.AddCommand;

import info.Disk;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ConsoleMenuTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final String[] lastCommand = new String[]{};

    @Mock
    CollectionOfCommands mockCollectionOfCommands;

    @Mock
    AddCommand mockAddCommand;

    @Mock
    Disk mockDisk;

    @InjectMocks
    ConsoleMenu menu;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
        System.setOut(new PrintStream(outContent));
        ReflectionTestUtils.setField(menu, "data", mockCollectionOfCommands);
        ReflectionTestUtils.setField(menu, "lastCommand", lastCommand);
    }

    @AfterEach
    public void restore() {
        System.setOut(originalOut);
    }

    @Test
    void testExecuteHelpCommand() {
        menu.execute("help something");
        verify(mockCollectionOfCommands).helpCommand(new String[] {"help", "something"});
    }

    @Test
    void testExecuteUndoCommand() {
        menu.execute("undo something");
        verify(mockCollectionOfCommands).undoCommand(new String[] {"undo", "something"}, lastCommand);
    }

    @Test
    void testExecuteNotCorrectCommand() {
        menu.execute("test command");
        assertEquals("This command doesn't exist\r\n", outContent.toString());
    }

    @Test
    void testExecuteCorrectCommandWithSave() {
        String[] testCommand = new String[]{"add", "test"};
        when(mockCollectionOfCommands.getList()).thenReturn(new HashMap<>() {{
            put("add", mockAddCommand);
        }});
        when(mockAddCommand.execute(testCommand)).thenReturn(true);
        menu.execute("add test");
        assertEquals("", outContent.toString());
    }

    @Test
    void testExecuteCorrectCommandWithoutSave() {
        String[] testCommand = new String[]{"add", "test"};
        when(mockCollectionOfCommands.getList()).thenReturn(new HashMap<>() {{
            put("add", mockAddCommand);
        }});
        when(mockAddCommand.execute(testCommand)).thenReturn(false);
        menu.execute("add test");
        assertEquals("", outContent.toString());
    }

    @Test
    void testDiskLocationExist() {
        when(mockCollectionOfCommands.getMainDisk()).thenReturn(mockDisk);
        when(mockDisk.connectionStatus()).thenReturn(true);
        assertThat(menu.diskLocation()).contains("\\");
    }

    @Test
    void testDiskLocationNotExist() {
        when(mockCollectionOfCommands.getMainDisk()).thenReturn(mockDisk);
        when(mockDisk.connectionStatus()).thenReturn(false);
        assertEquals(" ", menu.diskLocation());
    }
}