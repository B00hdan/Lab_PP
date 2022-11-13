package console.diskcommand;

import console.ConsoleCommand;
import info.Disk;

public class FindByCommand extends ConsoleCommand {

    public FindByCommand(Disk receiver) {
        super(receiver);
    }

    @Override
    public boolean execute(String[] params) {
        log.debug("FindBy command was executed");
        if(!connectionCheck()) {
            return false;
        }
        if(!receiver.printFromDiskAllBy(params))
            System.out.println(wrongCommandMessage);
        return false;
    }

    @Override
    public void undo(String[] params) {}

    @Override
    protected String getInfo() {
        return "find all/name=.../genre=.../.../duration >time1 <time2 - show specific tracks from disk";
    }
}
