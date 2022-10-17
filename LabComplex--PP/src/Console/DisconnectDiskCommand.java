package Console;

import Info.Disk;

public class DisconnectDiskCommand extends ConsoleCommand{
    public DisconnectDiskCommand(Disk receiver) {
        super(receiver);
    }

    @Override
    public boolean execute(String[] params) {
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