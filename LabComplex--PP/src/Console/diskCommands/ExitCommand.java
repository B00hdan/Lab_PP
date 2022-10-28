package console.diskCommands;

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
        System.exit(0);
        return false;
    }

    @Override
    public void undo(String[] params) {}

    @Override
    protected void getInfo() {
        System.out.println("exit - terminates the program");
    }
}
