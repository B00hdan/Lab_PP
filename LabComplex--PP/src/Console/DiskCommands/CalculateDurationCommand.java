package Console.DiskCommands;

import Console.ConsoleCommand;
import Info.Disk;

public class CalculateDurationCommand extends ConsoleCommand {

    public CalculateDurationCommand(Disk receiver) {
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

    }
}
