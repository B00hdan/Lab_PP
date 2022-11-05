package console.diskcommand;

import console.ConsoleCommand;
import info.Disk;

public class CalculateDurationCommand extends ConsoleCommand {
    public CalculateDurationCommand(Disk receiver) {
        super(receiver);
    }
    @Override
    public boolean execute(String[] params) {
        if(!connectionCheck() || params.length > 1)
            return false;
        receiver.calculateDurationOfAllTracks();
        return false;
    }
    @Override
    public void undo(String[] params) {}
    @Override
    protected void getInfo() {
        System.out.println("calculateDuration - calculates the total duration of all tracks on disc");
    }
}
