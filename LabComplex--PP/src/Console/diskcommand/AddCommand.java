package console.diskcommand;

import console.ConsoleCommand;
import info.Disk;

import java.util.Arrays;
import java.util.stream.Collectors;

public class AddCommand extends ConsoleCommand {
    public AddCommand(Disk receiver) {
        super(receiver);
    }

    @Override
    public boolean execute(String[] params){
        if(!connectionCheck())
            return false;

        if(params.length < 4) {
            System.out.println(wrongCommandMessage);
            return false;
        }

        boolean result = false;
        switch (params[1]) {
            case "track" -> result = receiver.addNewTrackOnDisk(params);
            case "album" -> result = receiver.addAlbumOnDisk(params);
        }

        if (result) {
            return true;
        } else {
            System.out.println(wrongCommandMessage);
            return false;
        }
    }

    @Override
    public void undo(String[] params) {
            String name = Arrays.stream(params).filter(string -> string.contains("name")).collect(Collectors.joining());
            String[] onlyName = name.split("=");
            String[] newParams = {"delete", params[1], onlyName[1]};
        new DeleteCommand(receiver).execute(newParams);
    }

    @Override
    public void getInfo() {
        System.out.println("add track/album (name=... duration=... genre=... [author=... dateOfWriting=...])/" +
                "(AlbumName albumData.txt) -\n- add new music composition/album on the disk");
    }
}
