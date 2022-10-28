package console;

public class ConsoleMenu {
    CollectionOfCommands data;
    String[] lastCommand = null;
    public ConsoleMenu() {
        this.data = new CollectionOfCommands();
    }

    public void execute(String fullCommand) {
        String[] commands = fullCommand.split(" ");
        if (commands[0].equals("help")) {
            data.helpCommand(commands);
        }else if (commands[0].equals("undo")){
            data.undoCommand(commands, lastCommand);
        } else if(data.getList().get(commands[0]) != null){
            if (data.getList().get(commands[0]).execute(commands))
                lastCommand = commands;
        } else System.out.println("This command doesn't exist");
    }
    public String diskLocation(){
        if (data.getMainDisk().connectionStatus()){
            return data.getMainDisk().diskLocation() + "\\ ";
        } return " ";
    }
}
