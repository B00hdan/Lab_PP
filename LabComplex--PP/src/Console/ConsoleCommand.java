package console;

import info.Disk;

public abstract class ConsoleCommand {
    protected Disk receiver;
    protected final String wrongCommandMessage = "This command cannot be executed";

    public ConsoleCommand(Disk receiver){
        this.receiver = receiver;
    }
    protected boolean connectionCheck(){
        if(!receiver.connectionStatus()) {
            System.out.println("Disk not connected");
            return false;
        } else return true;
    }
    public abstract boolean execute(String[] params);
    public abstract void undo(String[] params);
    protected abstract void getInfo();

}
