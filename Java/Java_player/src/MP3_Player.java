import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class MP3_Player {
	private Map<String, Playlist> playlists;
    private Playlist currentPlaylist;
    private int currentTrackIndex;
    private int playlistCounter;

    public MP3_Player() {
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
        playlists.put(name, new Playlist(name, playlistCounter++));
        System.out.println("Playlist '" + name + "' created.");
    }

    public void removePlaylistByName(String name) {
        if (playlists.containsKey(name)) {
            playlists.remove(name);
            System.out.println("Playlist '" + name + "' removed.");
        } else {
            System.out.println("Playlist '" + name + "' not found.");
        }
    }

    public void removePlaylistByNumber(int playlistNumber) {
        boolean found = false;
        for (Playlist playlist : playlists.values()) {
            if (playlist.getNumber() == playlistNumber) {
            	String playlistname = playlist.getName();
                playlists.remove(playlist.getName());
                System.out.println("Playlist with number " + playlistNumber + " (" + playlistname + ")" +" removed.");
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Playlist with number " + playlistNumber + " not found.");
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
            playlists.get(playlistName).addTrack(track);
            System.out.println("Track added to playlist '" + playlistName + "'.");
        } else {
            System.out.println("Playlist '" + playlistName + "' not found.");
        }
    }

    public void addTrackToPlaylistByNumber(int playlistNumber, String playlistName, Track track) {
        if (playlists.containsKey(playlistName) && playlists.get(playlistName).getNumber() == playlistNumber) {
            playlists.get(playlistName).addTrack(track);
            System.out.println("Track added to playlist '" + playlistName + "'.");
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
            Collections.sort(playlistList, Comparator.comparingInt(Playlist::getNumber));
            System.out.println("Available playlists:");
            for (Playlist playlist : playlistList) {
                System.out.println(playlist.getNumber() + ". " + playlist.getName());
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
                System.out.println("Now playing: " + track);
            } else {
                System.out.println("No more tracks to play.");
            }
        }
    }

    public void playNextTrack() {
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
                    writer.write(track.getTitle() + "," + track.getAuthor() + "," + track.getDuration() + "\n");
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
                        writer.write(track.getTitle() + "," + track.getAuthor() + "," + track.getDuration() + "\n");
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
        Collections.sort(playlistList, Comparator.comparingInt(Playlist::getNumber));
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

    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MP3_Player player = new MP3_Player();

        int choice = -1;
        while (choice != 0) {
            System.out.println("\nMenu:");
            System.out.println("0 - Exit");
            System.out.println("1 - Show all playlists");
            System.out.println("2 - Create playlist");
            System.out.println("3 - Play playlist");
            System.out.println("4 - Save playlist to file");
            System.out.println("5 - Remove playlist");
            System.out.println("6 - Add track to playlist");
            System.out.println("7 - Show tracks in playlist");
            System.out.println("8 - Remove track from playlist");
            System.out.println("9 - Play current track");
            System.out.println("10 - Play next track");
            System.out.println("11 - Play previous track");
            System.out.println("12 - Repeat current track");
            System.out.println("13 - Show all tracks");
            System.out.println("Enter your choice:");

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 0:
                    System.out.println("Exiting...");
                    break;
                case 1:
                    player.showAllPlaylists();
                    break;
                case 2:
                    System.out.println("Enter playlist name:");
                    String playlistName = scanner.nextLine();
                    player.createPlaylist(playlistName);
                    break;
                case 3:
                    System.out.println("How do you want to play the playlist?");
                    System.out.println("1 - By name");
                    System.out.println("2 - By number");
                    int playChoice = scanner.nextInt();
                    scanner.nextLine();
                    switch (playChoice) {
                        case 1:
                            System.out.println("Enter playlist name:");
                            playlistName = scanner.nextLine();
                            player.playPlaylistByName(playlistName);
                            break;
                        case 2:
                            System.out.println("Enter playlist number:");
                            int playlistNumber = scanner.nextInt();
                            scanner.nextLine();
                            player.playPlaylistByNumber(playlistNumber);
                            break;
                        default:
                            System.out.println("Invalid choice.");
                    }
                    break;
                case 4:
                    System.out.println("How do you want to save the playlist to file?");
                    System.out.println("1 - By name");
                    System.out.println("2 - By number");
                    int saveChoice = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    switch (saveChoice) {
                        case 1:
                            System.out.println("Enter playlist name:");
                            String playlistNameSave = scanner.nextLine();
                            player.saveToFileByName(playlistNameSave);
                            break;
                        case 2:
                            System.out.println("Enter playlist number:");
                            int playlistNumberSave = scanner.nextInt();
                            scanner.nextLine(); // Consume newline
                            player.saveToFileByNumber(playlistNumberSave);
                            break;
                        default:
                            System.out.println("Invalid choice.");
                    }
                    break;
                case 5:
                    System.out.println("How do you want to remove the playlist?");
                    System.out.println("1 - By name");
                    System.out.println("2 - By number");
                    int removeChoice = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    switch (removeChoice) {
                        case 1:
                            System.out.println("Enter playlist name:");
                            String playlistNameRemove = scanner.nextLine();
                            player.removePlaylistByName(playlistNameRemove);
                            break;
                        case 2:
                            System.out.println("Enter playlist number:");
                            int playlistNumberRemove = scanner.nextInt();
                            scanner.nextLine(); // Consume newline                          
                            player.removePlaylistByNumber(playlistNumberRemove);
                            break;
                        default:
                            System.out.println("Invalid choice.");
                    }
                    break;
                case 6:
                    System.out.println("How do you want to add track to the playlist?");
                    System.out.println("1 - By playlist name");
                    System.out.println("2 - By playlist number");
                    int addChoice = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    switch (addChoice) {
                        case 1:
                            System.out.println("Enter playlist name:");
                            String playlistNameAdd = scanner.nextLine();
                            if (player.getPlaylists().containsKey(playlistNameAdd)) {
                                System.out.println("Enter track author:");
                                String author = scanner.nextLine();
                                System.out.println("Enter track title:");
                                String title = scanner.nextLine();
                                System.out.println("Enter track duration (seconds):");
                                int duration = scanner.nextInt();
                                scanner.nextLine(); // Consume newline
                                Track track = new Track(author, title, duration);
                                player.addTrackToPlaylistByName(playlistNameAdd, track);
                            } else {
                                System.out.println("Playlist '" + playlistNameAdd + "' not found.");
                            }
                            break;
                        case 2:
                            System.out.println("Enter playlist number:");
                            int playlistNumberAdd = scanner.nextInt();
                            scanner.nextLine(); // Consume newline                 
                            String playlistNameAddByNumber = player.getPlaylistNameByNumber(playlistNumberAdd);
                            if (player.getPlaylists().containsKey(playlistNameAddByNumber)) {
                                System.out.println("Enter track author:");
                                String author = scanner.nextLine();
                                System.out.println("Enter track title:");
                                String title = scanner.nextLine();
                                System.out.println("Enter track duration (seconds):");
                                int duration = scanner.nextInt();
                                scanner.nextLine(); // Consume newline
                                Track track = new Track(author, title, duration);
                                player.addTrackToPlaylistByNumber(playlistNumberAdd, playlistNameAddByNumber, track);
                            } else {
                                System.out.println("Playlist '" + playlistNameAddByNumber + "' not found.");
                            }
                            break;
                        default:
                            System.out.println("Invalid choice.");
                    }
                    break;
                case 7:
                    System.out.println("Enter playlist name:");
                    playlistName = scanner.nextLine();
                    if (player.getPlaylists().containsKey(playlistName)) {
                        player.getPlaylist(playlistName).showTracks();
                    } else {
                        System.out.println("Playlist '" + playlistName + "' not found.");
                    }
                    break;
                case 8:
                    System.out.println("How do you want to remove track from the playlist?");
                    System.out.println("1 - By playlist name");
                    System.out.println("2 - By playlist number");
                    int removeTrackChoice = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    switch (removeTrackChoice) {
                        case 1:
                            System.out.println("Enter playlist name:");
                            String playlistNameRemoveTrack = scanner.nextLine();
                            if (player.getPlaylists().containsKey(playlistNameRemoveTrack)) {
                                System.out.println("Enter track number to remove:");
                                int trackIndexRemove = scanner.nextInt();
                                scanner.nextLine(); // Consume newline
                                player.removeTrackFromPlaylistByName(playlistNameRemoveTrack, trackIndexRemove);
                            } else {
                                System.out.println("Playlist '" + playlistNameRemoveTrack + "' not found.");
                            }
                            break;
                        case 2:
                            System.out.println("Enter playlist number:");
                            int playlistNumberRemoveTrack = scanner.nextInt();
                            scanner.nextLine(); // Consume newline
                            String playlistNameRemoveTrackByNumber = player.getPlaylistNameByNumber(playlistNumberRemoveTrack);
                            if (player.getPlaylists().containsKey(playlistNameRemoveTrackByNumber)) {
                                System.out.println("Enter track number to remove:");
                                int trackIndexRemoveByNumber = scanner.nextInt();
                                scanner.nextLine(); // Consume newline
                                player.removeTrackFromPlaylistByNumber(playlistNumberRemoveTrack, playlistNameRemoveTrackByNumber, trackIndexRemoveByNumber);
                            } else {
                                System.out.println("Playlist '" + playlistNameRemoveTrackByNumber + "' not found.");
                            }
                            break;
                        default:
                            System.out.println("Invalid choice.");
                    }
                    break;
                case 9:
                    player.playCurrentTrack();
                    break;
                case 10:
                    player.playNextTrack();
                    break;
                case 11:
                    player.playPreviousTrack();
                    break;
                case 12:
                    player.repeatCurrentTrack();
                    break;
                case 13:
                    player.displayAllSongs();
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }
}
