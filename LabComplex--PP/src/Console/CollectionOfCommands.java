package Console;

import Console.DiskCommands.*;
import Info.Disk;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CollectionOfCommands {
    private List<Map<String, ConsoleCommand>> levelsList = new ArrayList<>();
    private Map<String, ConsoleCommand> firstLevel;
    private Map<String, ConsoleCommand> secondLevel;
    private Disk mainDisk = new Disk();

    public CollectionOfCommands(){
        firstLevel =  new LinkedHashMap<>();
        firstLevel.put("connect", new ConnectDiskCommand(mainDisk));
        firstLevel.put("disconnect", new DisconnectDiskCommand(mainDisk));
        firstLevel.put("exit", new ExitCommand(mainDisk));
        secondLevel = new LinkedHashMap<>();
        secondLevel.put("add", new AddCommand(mainDisk));
        secondLevel.put("delete", new DeleteCommand(mainDisk));
        secondLevel.put("edit", new EditCommand(mainDisk));
        secondLevel.put("sortBy", new SortByCommand(mainDisk));
        levelsList.add(firstLevel);
        levelsList.add(secondLevel);
    }

    public void helpCommand(String[] params) {
        if (params.length > 1) {
            if (params.length < 3) {
                for (Map<String, ConsoleCommand> level : levelsList){
                    if (level.get(params[1]) != null) {
                        level.get(params[1]).getInfo();
                        return;
                    }
                }
            }
            System.out.println("This command doesn't exist");
        } else {
            for (Map<String, ConsoleCommand> level : levelsList) {
                for (Map.Entry<String, ConsoleCommand> entry : level.entrySet()) {
                    entry.getValue().getInfo();
                }
            }
        }
    }

    void undoCommand(String[] params, String[] lastCommand){
        if (params.length > 1) {
            System.out.println("This command doesn't exist");
        } else  {
            for (Map<String, ConsoleCommand> level : levelsList) {
                if (level.get(lastCommand[0]) != null)
                    level.get(lastCommand[0]).undo(lastCommand);
            }
        }
    }

    public Disk getMainDisk(){
        return mainDisk;
    }

    public List<Map<String, ConsoleCommand>> getLevelsList() {
        return levelsList;
    }

}
