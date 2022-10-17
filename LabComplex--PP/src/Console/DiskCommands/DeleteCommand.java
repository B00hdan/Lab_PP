package Console.DiskCommands;

import Console.ConsoleCommand;
import Info.Disk;

public class DeleteCommand extends ConsoleCommand {

    public DeleteCommand(Disk receiver) {
        super(receiver);
    }

    @Override
    public boolean execute(String[] params) {
        return true;
    }

    @Override
    public void undo(String[] params) {
    }

    @Override
    protected void getInfo() {
        System.out.println("delete - deletes the music composition/album from the disc");
    }
}
