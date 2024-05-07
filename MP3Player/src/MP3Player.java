import java.io.*;
import java.util.*;

import javazoom.jl.player.Player;

class MP3Player {
    private final Map<String, Playlist> playlists;
    private Playlist currentPlaylist;
    private int currentTrackIndex;
    private int playlistCounter;
    private Player player;
    private Thread playThread;


    public MP3Player() {
        this.playlists = new HashMap<>();
        this.currentPlaylist = null;
        this.currentTrackIndex = 0;
        this.playlistCounter = 1;
    }

    public Map<String, Playlist> getPlaylists() {
        return playlists;
    }

    public Playlist getPlaylist(String name) {
        return playlists.get(name);
    }

    public void createPlaylist(String name) {
        if (playlists.containsKey(name)) {
            System.out.println("Playlist '" + name + "' already exists.");
        } else {
            playlists.put(name, new Playlist(name, playlistCounter++));
            System.out.println("Playlist '" + name + "' created.");
        }
    }

    public void addPlaylist(Playlist playlist) {
        playlists.put(playlist.getName(), playlist);
    }

    public void removePlaylistByName(String name) {
        Playlist removedPlaylist = playlists.get(name);
        if (removedPlaylist != null) {
            playlists.remove(name);
            playlistCounter--;
            System.out.println("Playlist '" + name + "' removed.");

            for (Playlist playlist : playlists.values()) {
                if (playlist.getNumber() > removedPlaylist.getNumber()) {
                    playlist.setNumber(playlist.getNumber() - 1);
                }
            }
        } else {
            System.out.println("Playlist '" + name + "' not found.");
        }
    }

    public void removePlaylistByNumber(int playlistNumber) {
        boolean found = false;
        Playlist removedPlaylist = null;
        for (Playlist playlist : playlists.values()) {
            if (playlist.getNumber() == playlistNumber) {
                String playlistName = playlist.getName();
                removedPlaylist = playlist;
                playlists.remove(playlist.getName());
                System.out.println("Playlist with number " + playlistNumber + " (" + playlistName + ")" +" removed.");
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Playlist with number " + playlistNumber + " not found.");
        } else {
            for (Playlist playlist : playlists.values()) {
                if (playlist.getNumber() > removedPlaylist.getNumber()) {
                    playlist.setNumber(playlist.getNumber() - 1);
                }
            }
            playlistCounter--;
        }
    }

    public void playPlaylistByName(String name) {
        boolean found = false;
        for (Playlist playlist : playlists.values()) {
            if (playlist.getName().equals(name)) {
                currentPlaylist = playlist;
                currentTrackIndex = 0;
                System.out.println("Now playing playlist '" + name + "'.");
                playCurrentTrack();
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Playlist '" + name + "' not found.");
        }
    }

    public void playPlaylistByNumber(int playlistNumber) {
        boolean found = false;
        for (Playlist playlist : playlists.values()) {
            if (playlist.getNumber() == playlistNumber) {
                currentPlaylist = playlist;
                currentTrackIndex = 0;
                System.out.println("Now playing playlist '" + playlist.getName() + "'.");
                playCurrentTrack();
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Playlist with number " + playlistNumber + " not found.");
        }
    }


    public void addTrackToPlaylistByName(String playlistName, Track track) {
        if (playlists.containsKey(playlistName)) {
            File file = new File(track.getFilePath());
            if (file.exists()) {
                playlists.get(playlistName).addTrack(track);
                System.out.println("Track added to playlist '" + playlistName + "'.");
            } else {
                System.out.println("Error: File '" + track.getFilePath() + "' not found.");
            }
        } else {
            System.out.println("Playlist '" + playlistName + "' not found.");
        }
    }

    public void addTrackToPlaylistByNumber(int playlistNumber, String playlistName, Track track) {
        if (playlists.containsKey(playlistName) && playlists.get(playlistName).getNumber() == playlistNumber) {
            File file = new File(track.getFilePath());
            if (file.exists()) {
                playlists.get(playlistName).addTrack(track);
                System.out.println("Track added to playlist '" + playlistName + "'.");
            } else {
                System.out.println("Error: File '" + track.getFilePath() + "' not found.");
            }
        } else {
            System.out.println("Playlist '" + playlistName + "' not found.");
        }
    }

    public void removeTrackFromPlaylistByName(String playlistName, int index) {
        if (playlists.containsKey(playlistName)) {
            playlists.get(playlistName).removeTrack(index - 1);
        } else {
            System.out.println("Playlist '" + playlistName + "' not found.");
        }
    }

    public void removeTrackFromPlaylistByNumber(int playlistNumber, String playlistName, int index) {
        if (playlists.containsKey(playlistName) && playlists.get(playlistName).getNumber() == playlistNumber) {
            playlists.get(playlistName).removeTrack(index - 1);
        } else {
            System.out.println("Playlist '" + playlistName + "' not found.");
        }
    }

    public void showAllPlaylists() {
        if (playlists.isEmpty()) {
            System.out.println("No playlists available.");
        } else {
            List<Playlist> playlistList = new ArrayList<>(playlists.values());
            playlistList.sort(Comparator.comparingInt(Playlist::getNumber));
            System.out.println("Available playlists:");
            for (Playlist playlist : playlistList) {
                System.out.println(playlist.getNumber() + "." + playlist.getName());
            }
        }
    }

    public void playTrack(String filePath) {
        try {
            FileInputStream fileInputStream = new FileInputStream(filePath);
            player = new Player(fileInputStream);
            playThread = new Thread(() -> {
                try {
                    player.play();
                } catch (Exception e) {
                    System.out.println("Error playing track: " + e.getMessage());
                }
            });
            playThread.start();
        } catch (Exception e) {
            System.out.println("Error playing track: " + e.getMessage());
        }
    }

    public void stopTrack() {
        if (player != null) {
            player.close();
            System.out.println("Track stopped.");
            playThread.interrupt();
        } else {
            System.out.println("No track is currently playing.");
        }
    }

    public String getCurrentTrackInfo() {
        if (currentPlaylist == null || currentPlaylist.getTracks().isEmpty() || currentTrackIndex < 0) {
            return "No track is currently playing.";
        } else {
            List<Track> tracks = currentPlaylist.getTracks();
            if (currentTrackIndex < tracks.size()) {
                Track track = tracks.get(currentTrackIndex);
                return "Now playing: " + track.getTitle() + " - " + track.getAuthor() + " (" + track.getDuration() + " seconds)";
            } else {
                return "No more tracks to play.";
            }
        }
    }

    public void playCurrentTrack() {
        if (currentPlaylist == null || currentPlaylist.getTracks().isEmpty()) {
            System.out.println("No playlist is currently playing.");
        } else {
            List<Track> tracks = currentPlaylist.getTracks();
            if (currentTrackIndex >= 0 && currentTrackIndex < tracks.size()) {
                Track track = tracks.get(currentTrackIndex);
                String currentTrackFilePath = track.getFilePath();
                playTrack(currentTrackFilePath);
                System.out.println("Now playing: " + track);
            } else {
                System.out.println("No more tracks to play.");
            }
        }
    }

    public void playNextTrack() {
        stopTrack();

        if (currentPlaylist != null) {
            List<Track> tracks = currentPlaylist.getTracks();
            if (!tracks.isEmpty()) {
                currentTrackIndex = (currentTrackIndex + 1) % tracks.size();
                playCurrentTrack();
            } else {
                System.out.println("No tracks in current playlist.");
            }
        } else {
            System.out.println("No playlist is currently playing.");
        }
    }

    public void playPreviousTrack() {
        stopTrack();

        if (currentPlaylist != null) {
            List<Track> tracks = currentPlaylist.getTracks();
            if (!tracks.isEmpty()) {

                currentTrackIndex = (currentTrackIndex - 1 + tracks.size()) % tracks.size();
                playCurrentTrack();
            } else {
                System.out.println("No tracks in current playlist.");
            }
        } else {
            System.out.println("No playlist is currently playing.");
        }
    }


    public void repeatCurrentTrack() {
        stopTrack();

        if (currentPlaylist != null) {
            List<Track> tracks = currentPlaylist.getTracks();
            if (!tracks.isEmpty()) {
                playCurrentTrack();
            } else {
                System.out.println("No tracks in current playlist.");
            }
        } else {
            System.out.println("No playlist is currently playing.");
        }
    }


    public void saveToFileByName(String name) {
        if (playlists.containsKey(name)) {
            try {
                FileWriter writer = new FileWriter(name + ".txt");
                for (Track track : playlists.get(name).getTracks()) {
                    writer.write(track.getFilePath() + "," + track.getTitle() + "," + track.getAuthor() + "," + track.getDuration() + "\n");
                }
                writer.close();
                System.out.println("Playlist '" + name + "' saved to file.");
            } catch (IOException e) {
                System.out.println("Error occurred while saving playlist '" + name + "' to file.");
                e.printStackTrace();
            }
        } else {
            System.out.println("Playlist '" + name + "' not found.");
        }
    }

    public void saveToFileByNumber(int playlistNumber) {
        boolean found = false;
        for (Playlist playlist : playlists.values()) {
            if (playlist.getNumber() == playlistNumber) {
                try {
                    FileWriter writer = new FileWriter(playlist.getName() + ".txt");
                    for (Track track : playlist.getTracks()) {
                        writer.write(track.getFilePath()+ "," + track.getTitle() + "," + track.getAuthor() + "," + track.getDuration() + "\n");
                    }
                    writer.close();
                    System.out.println("Playlist '" + playlist.getName() + "' saved to file.");
                } catch (IOException e) {
                    System.out.println("Error occurred while saving playlist '" + playlist.getName() + "' to file.");
                    e.printStackTrace();
                }
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Playlist with number " + playlistNumber + " not found.");
        }
    }

    public void addPlaylistFromFile(String fileName) {
        int count = playlists.size();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String playlistName = fileName.substring(0, fileName.lastIndexOf('.')).replace('_', ' ');
            Playlist playlist = new Playlist(playlistName, count + 1);
            String line;
            while ((line = reader.readLine()) != null) {
                String[] trackInfo = line.split(",");
                if (trackInfo.length == 4) {
                    String filePath = trackInfo[0].trim();
                    Track track = new Track(filePath);
                    playlist.addTrack(track);
                } else {
                    System.out.println("Error: Invalid track information in file.");
                }
            }
            addPlaylist(playlist);
            System.out.println("Playlist '" + playlist.getName() + "' added from file.");
        } catch (IOException e) {
            System.out.println("Error occurred while reading from file.");
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid track duration in file.");
            e.printStackTrace();
        }
    }


    public String getPlaylistNameByNumber(int playlistNumber) {
        for (Playlist playlist : playlists.values()) {
            if (playlist.getNumber() == playlistNumber) {
                return playlist.getName();
            }
        }
        return null;
    }

    public void displayAllSongs() {
        if (playlists.isEmpty()) {
            System.out.println("No playlists found.");
            return;
        }
        List<Playlist> playlistList = new ArrayList<>(playlists.values());
        playlistList.sort(Comparator.comparingInt(Playlist::getNumber));
        System.out.println("All playlists and their songs:");
        for (Playlist playlist : playlistList) {
            System.out.println("Playlist: " + playlist.getName());
            System.out.println("Tracks:");
            for (Track track : playlist.getTracks()) {
                System.out.println("    " + track.getTitle() + " - " + track.getAuthor() + " (" + track.getDuration() + " seconds)");
            }
            System.out.println();
        }
    }
}
