package console;

import console.diskcommand.*;
import info.Disk;

import java.util.Arrays;
import java.util.LinkedHashMap;

import java.util.Map;

public class CollectionOfCommands {
    private final Map<String, ConsoleCommand> newList;
    private final Disk mainDisk = new Disk();

    public CollectionOfCommands(){
        newList = new LinkedHashMap<>();
        newList.put("connect", new ConnectDiskCommand(mainDisk));
        newList.put("disconnect", new DisconnectDiskCommand(mainDisk));
        newList.put("exit", new ExitCommand(mainDisk));
        newList.put("add", new AddCommand(mainDisk));
        newList.put("delete", new DeleteCommand(mainDisk));
        newList.put("edit", new EditCommand(mainDisk));
        newList.put("sort", new SortByCommand(mainDisk));
        newList.put("find", new FindByCommand(mainDisk));
        newList.put("calculateDuration", new CalculateDurationCommand(mainDisk));
    }
    public void helpCommand(String[] params) {
        if (params.length > 1) {
            if (params.length < 3) {
                if (newList.get(params[1]) != null) {
                    newList.get(params[1]).getInfo();
                    return;
                }
            }
            System.out.println("This command doesn't exist");
        } else {
            for (Map.Entry<String, ConsoleCommand> entry : newList.entrySet())
                entry.getValue().getInfo();
        }
    }
    void undoCommand(String[] params, String[] lastCommand) {
        if (params.length > 1) {
            System.out.println("This command doesn't exist");
        } else if (lastCommand != null && lastCommand[0] != null) {
            newList.get(lastCommand[0]).undo(lastCommand);
            Arrays.fill(lastCommand, null);
        }
    }

    public Disk getMainDisk(){
        return mainDisk;
    }
    public Map<String, ConsoleCommand> getList() {
        return newList;
    }

}
