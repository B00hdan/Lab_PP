package Console.DiskCommands;

import Console.ConsoleCommand;
import Info.Disk;

public class SortByCommand extends ConsoleCommand {


    public SortByCommand(Disk receiver) {
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
        System.out.println("sortBy - sorts all music compositions on the disc by parameter");
    }
}
