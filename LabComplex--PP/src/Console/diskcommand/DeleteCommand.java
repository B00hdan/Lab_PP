package console.diskcommand;

import console.ConsoleCommand;
import info.Disk;

public class DeleteCommand extends ConsoleCommand {

    public DeleteCommand(Disk receiver) {
        super(receiver);
    }

    @Override
    public boolean execute(String[] params) {
        if(!receiver.connectionStatus())
            return false;

        boolean result = false;
        if(params.length <= 3 && params.length > 1){
            switch (params[1]) {
                case "track" -> result = receiver.deleteTrackFromDisk(params[2]);
                case "album" -> result = receiver.deleteAlbumFromDisk(params[2]);
            }
        }

        if(!result){
            System.out.println(wrongCommandMessage);
            return false;
        } else return true;
    }

    @Override
    public void undo(String[] params) {
        receiver.restoreTrackLastOnDisk();
    }

    @Override
    protected void getInfo() {
        System.out.println("delete track/album trackName/albumName - deletes the music track/album from the disc");
    }
}
