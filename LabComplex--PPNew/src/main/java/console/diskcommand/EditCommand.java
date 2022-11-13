package console.diskcommand;

import console.ConsoleCommand;
import info.Disk;

public class EditCommand extends ConsoleCommand {
    /*User can not change name, genre*/
    public EditCommand(Disk receiver) {
        super(receiver);
    }

    @Override
    public boolean execute(String[] params) {
        log.debug("Edit command was executed");
        if(!connectionCheck())
            return false;
        if(params.length < 3 || !params[1].contains("name=")) {
            System.out.println(wrongCommandMessage);
            return false;
        }
        if (!receiver.changeTrackParamsOnDisk(params)){
            System.out.println(wrongCommandMessage);
            return false;
        } else return true;
    }

    public void undo(String[] params) {
        receiver.returnParamsForLastModified();
        log.debug("Edit command was canceled");
    }

    @Override
    protected String getInfo() {
        return "edit name=... author=.../duration=.../...  - change parameters of " +
                "the music composition, which is recorded on disk";
    }
}
