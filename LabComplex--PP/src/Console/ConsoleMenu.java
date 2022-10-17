package Console;

public class ConsoleMenu {
    CollectionOfCommands data;
    String[] lastCommand = {" "};
    public ConsoleMenu() {
        this.data = new CollectionOfCommands();
    }

    public void execute(String fullCommand) {
        String[] commands = fullCommand.split(" ");
        if (commands[0].equals("help")) {
            data.helpCommand(commands);
        }else if (commands[0].equals("undo")){
            data.undoCommand(commands, lastCommand);
        } else if(data.getLevelsList().get(0).get(commands[0]) != null){
            if (data.getLevelsList().get(0).get(commands[0]).execute(commands))
                lastCommand = commands;
        } else if(data.getLevelsList().get(1).get(commands[0]) != null && data.getMainDisk().connectionStatus()){
            if (data.getLevelsList().get(1).get(commands[0]).execute(commands))
                lastCommand = commands;
        } else System.out.println("This command doesn't exist, or disk not connected");
    }
    public String diskLocation(){
        if (data.getMainDisk().connectionStatus()){
            return data.getMainDisk().diskLocation() + "\\ ";
        }
        return " ";
    }
}
