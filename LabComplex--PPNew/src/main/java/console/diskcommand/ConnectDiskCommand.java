package console.diskcommand;

import console.ConsoleCommand;
import info.Disk;

public class ConnectDiskCommand extends ConsoleCommand {
    public ConnectDiskCommand(Disk receiver) {
        super(receiver);
    }
    @Override
    public boolean execute(String[] params) {
        if(receiver.connectionStatus()){
            new DisconnectDiskCommand(receiver).execute(params);
        }
        if (params.length != 3 && params.length != 1){
            System.out.println(wrongCommandMessage);
            return false;
        }
        if(params.length == 1){
            receiver.connectDisk("newFile.txt", 50);
        }else receiver.connectDisk(params[1], Integer.parseInt(params[2]));
        return false;
    }

    @Override
    public void undo(String[] params) {}

    @Override
    protected String getInfo() {
        return "connect [saveFile.txt 40] - connects disc to system, add or create file for it";
    }
}
