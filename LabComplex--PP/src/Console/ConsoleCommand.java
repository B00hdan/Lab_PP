package Console;

import Info.Disk;

public abstract class ConsoleCommand {
    protected Disk receiver;

    public ConsoleCommand(Disk receiver){
        this.receiver = receiver;
    }
    public abstract boolean execute(String[] params);
    public abstract void undo(String[] params);
    protected abstract void getInfo();

}
