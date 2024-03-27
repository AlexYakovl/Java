import java.util.ArrayList;
import java.util.List;

public class Playlist {
    private String name;
    private List<Track> tracks;
    private int playlistNumber;

    public Playlist(String name, int playlistNumber) {
        this.name = name;
        this.tracks = new ArrayList<>();
        this.playlistNumber = playlistNumber;
    }

    public String getName() {
        return name;
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public void addTrack(Track track) {
        tracks.add(track);
    }

    public void removeTrack(int index) {
        if (index >= 0 && index < tracks.size()) {
            tracks.remove(index);
        } else {
            System.out.println("Invalid track index.");
        }
    }

    public void showTracks() {
        if (tracks.isEmpty()) {
            System.out.println("Playlist is empty.");
        } else {
            System.out.println("Tracks in playlist '" + name + "':");
            for (int i = 0; i < tracks.size(); i++) {
                System.out.println((i + 1) + ". " + tracks.get(i));
            }
        }
    }
    
    public int getNumber() {
        return playlistNumber;
    }
}

