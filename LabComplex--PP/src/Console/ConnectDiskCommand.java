package Console;

import Info.Disk;

public class ConnectDiskCommand extends ConsoleCommand {


    public ConnectDiskCommand(Disk receiver) {
        super(receiver);
    }

    @Override
    public boolean execute(String[] params) {
        if(receiver.connectionStatus()){
            System.out.println("Impossible to connect to a new disk, " +
                    "first disconnect from the previous one");
            return false;
        }
        receiver.connectDisk("D:\\temp\\newFile.txt", 1000);
        return false;
    }

    @Override
    public void undo(String[] params) {}

    @Override
    protected void getInfo() {
        System.out.println("connect - connects disc to system");
    }
}
