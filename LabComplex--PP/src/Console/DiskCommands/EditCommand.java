package Console.DiskCommands;

import Console.ConsoleCommand;
import Info.Disk;

public class EditCommand extends ConsoleCommand {

    public EditCommand(Disk receiver) {
        super(receiver);
    }

    @Override
    public boolean execute(String[] params) {
        return true;
    }
    public void undo(String[] params) {}
    @Override
    protected void getInfo() {
        System.out.println("edit - change parameters of the music composition, which is recorded on disk");
    }
}
