package console.diskcommand;

import console.ConsoleCommand;
import info.Disk;

public class ExitCommand extends ConsoleCommand {
    public ExitCommand(Disk receiver) {
        super(receiver);
    }

    @Override
    public boolean execute(String[] params) {
        if(connectionCheck()){
            new DisconnectDiskCommand(receiver).execute(params);
        }
        System.out.println("Logging out...");
        exit(0);
        return false;
    }

    protected void exit(int status){
        System.exit(status);
    }

    @Override
    public void undo(String[] params) {}

    @Override
    protected String getInfo() {
        return "exit - terminates the program";
    }
}
