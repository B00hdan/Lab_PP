package Track;

import Info.File;
import Info.SetOfCompositions;

public class Album {
    protected File folder;
    protected SetOfCompositions set;

    public Album(String location){
        if (location != null){
            folder = new File(location);
        }
    }

}
