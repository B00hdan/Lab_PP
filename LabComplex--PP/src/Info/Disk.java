package Info;

import java.util.Objects;

public class Disk {
        protected SetOfCompositions set = null;
        protected File folder;

        int size = 1000;
        public void connectDisk(String location, int size){
                this.size = size;
                set = new SetOfCompositions();
                folder = new File(Objects.requireNonNullElse(location, "D:\\temp\\"));
        }
        public void disconnectDisk(){
                set = null;
                folder = null;
        }
        public boolean connectionStatus(){
                return set != null;
        }
        public String diskLocation(){
                return folder.getLocationOnPC();
        }
}
