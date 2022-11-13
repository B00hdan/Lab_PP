package console.diskcommand;

import console.ConsoleCommand;
import info.Disk;

public class DisconnectDiskCommand extends ConsoleCommand {
    public DisconnectDiskCommand(Disk receiver) {
        super(receiver);
    }

    @Override
    public boolean execute(String[] params) {
        log.debug("DisconnectDisk command was executed");
        if(!connectionCheck())
            return false;
        if (params.length > 1)
            return false;
        receiver.disconnectDisk();
        log.info("Disk saved successfully");
        return false;
    }
    @Override
    public void undo(String[] params){}
    @Override
    protected String getInfo() {
        return "disconnect - disconnects disc from system";
    }
}