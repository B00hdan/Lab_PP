package console;

import info.Disk;
import org.apache.log4j.Logger;

import static org.apache.log4j.LogManager.getLogger;

public abstract class ConsoleCommand {
    protected Disk receiver;
    protected final String wrongCommandMessage = "This command cannot be executed";
    protected final Logger log = getLogger(ConsoleCommand.class);

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
    protected abstract String getInfo();

}
