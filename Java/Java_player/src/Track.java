public class Track {
    private String author;
    private String title;
    private int duration; // Duration in seconds

    public Track(String author, String title, int duration) {
        this.author = author;
        this.title = title;
        this.duration = duration;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        return title + " by " + author + " (" + duration + " seconds)";
    }
}
