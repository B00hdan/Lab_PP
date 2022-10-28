package console.diskCommands;

import console.ConsoleCommand;
import info.Disk;

public class EditCommand extends ConsoleCommand {

    public EditCommand(Disk receiver) {
        super(receiver);
    }

    @Override
    public boolean execute(String[] params) {
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
    }

    @Override
    protected void getInfo() {
        System.out.println("edit name=... author=.../duration=.../...  - change parameters of " +
                "the music composition, which is recorded on disk");
    }
}
