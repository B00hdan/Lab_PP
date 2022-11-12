package console;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Map;

import org.springframework.test.util.ReflectionTestUtils;

import static javax.swing.UIManager.put;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

class CollectionOfCommandsTest {
    @Mock
    Map<String, ConsoleCommand> mockList;

    @Mock
    ConsoleCommand mockCommand;

    @InjectMocks
    CollectionOfCommands collection;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(collection, "newList", mockList);
    }

    @Test
    void testHelpCommandThreeAndMoreParams() {
        collection.helpCommand(new String[]{"help", "test", "command"});
        verify(mockList, times(0)).get(anyString());
    }

    @Test
    void testHelpCommandTwoParams() {
        when(mockList.entrySet()).thenReturn(new HashSet<>(){{
            put("add", mockCommand);
            put("delete", mockCommand);
        }});
        when(mockList.get("add")).thenReturn(mockCommand);
        collection.helpCommand(new String[]{"help", "add"});
        verify(mockCommand, times(1)).getInfo();
    }

    @Test
    void testHelpCommandOneParams() {
        when(mockList.entrySet()).thenReturn(new HashSet<>(){{
            put("add", mockCommand);
            put("delete", mockCommand);
        }});
        collection.helpCommand(new String[]{"help"});
        verify(mockCommand, times(mockList.size())).getInfo();
    }

    @Test
    void testUndoCommandNotCorrectParams() {
        collection.undoCommand(new String[]{"undo", "test"},new String[]{});
        verify(mockList, times(0)).get("undo");
        verify(mockCommand, times(0)).undo(any());
    }

    @Test
    void testUndoCommandCorrectParams() {
        String[] testCommandSaves = new String[]{"add", "test"};
        when(mockList.get(testCommandSaves[0])).thenReturn(mockCommand);
        collection.undoCommand(new String[]{"undo"},testCommandSaves);
        verify(mockCommand, times(1)).undo(testCommandSaves);
    }

}