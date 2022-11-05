package console.diskcommand;

import console.ConsoleCommand;
import info.Disk;
import java.util.Arrays;

public class SortByCommand extends ConsoleCommand {

    public SortByCommand(Disk receiver) {
        super(receiver);
    }

    @Override
    public boolean execute(String[] params) {
        if(!connectionCheck()) {
            return false;
        }
        String[] sortOnlyByThis = new String[]{"name", "duration", "genre"};
        if(params.length != 2 || !Arrays.asList(sortOnlyByThis).contains(params[1])) {
            System.out.println(wrongCommandMessage);
            return false;
        }
        receiver.sortOnDiskBy(params[1]);
        return false;
    }

    @Override
    public void undo(String[] params) {}

    @Override
    protected void getInfo() {
        System.out.println("sort name/duration/genre - sorts all music compositions on the disc by parameter");
    }
}
