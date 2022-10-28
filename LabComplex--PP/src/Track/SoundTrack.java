package track;

abstract class SoundTrack {
    protected String name = "";
    protected String duration = "";
    protected boolean deleteStatus = false;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDuration() {
        return duration;
    }
    public void setDuration(String duration) {
        this.duration = duration;
    }
    public boolean isDeleteStatus() {
        return deleteStatus;
    }
    public void setDeleteStatus(boolean deleteStatus) {
        this.deleteStatus = deleteStatus;
    }
}
