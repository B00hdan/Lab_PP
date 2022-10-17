package Console.DiskCommands;

import Console.ConsoleCommand;
import Info.Disk;

public class FindByCommand extends ConsoleCommand {

    public FindByCommand(Disk receiver) {
        super(receiver);
    }

    @Override
    public boolean execute(String[] params) {
        return false;
    }

    @Override
    public void undo(String[] params) {
    }

    @Override
    protected void getInfo() {
        System.out.println("findBy");
    }
}
