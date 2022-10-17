package Console.DiskCommands;

import Console.ConsoleCommand;
import Info.Disk;

public class AddCommand extends ConsoleCommand {
    public AddCommand(Disk receiver) {
        super(receiver);
    }

    @Override
    public boolean execute(String[] params){
        System.out.println("AddCommand");
        return true;
    }

    @Override
    public void undo(String[] params) {
        System.out.println("UndoAddCommand");
    }

    @Override
    public void getInfo() {
        System.out.println("add - add new music composition/album on the disk");
    }
}
