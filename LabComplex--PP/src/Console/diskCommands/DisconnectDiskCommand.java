package console.diskCommands;

import console.ConsoleCommand;
import info.Disk;

public class DisconnectDiskCommand extends ConsoleCommand {
    public DisconnectDiskCommand(Disk receiver) {
        super(receiver);
    }

    @Override
    public boolean execute(String[] params) {
        if(!connectionCheck())
            return false;
        if (params.length > 1)
            return false;
        receiver.disconnectDisk();
        return false;
    }
    @Override
    public void undo(String[] params){}
    @Override
    protected void getInfo() {
        System.out.println("disconnect - disconnects disc from system");
    }
}