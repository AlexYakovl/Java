import javax.swing.*;
import java.util.*;

public class App {
    public static void MP3PlayerCLI() {
        Scanner scanner = new Scanner(System.in);
        MP3Player player = new MP3Player();

        int choice = -1;
        while (choice != 0) {
            System.out.println("\nMenu:");
            System.out.println("0 - Exit");
            System.out.println("1 - Show all playlists");
            System.out.println("2 - Create playlist");
            System.out.println("3 - Play playlist");
            System.out.println("4 - Save playlist to file");
            System.out.println("5 - Load playlist from file");
            System.out.println("6 - Remove playlist");
            System.out.println("7 - Add track to playlist");
            System.out.println("8 - Show tracks in playlist");//
            System.out.println("9 - Remove track from playlist");
            System.out.println("10 - Play next track");
            System.out.println("11 - Play previous track");
            System.out.println("12 - Repeat current track");
            System.out.println("13 - Stop current track");
            System.out.println("14 - Show all tracks"); //
            System.out.println("Enter your choice:");

            choice = scanner.nextInt();
            scanner.nextLine();
            String playlistName;

            switch (choice) {
                case 0:
                    player.stopTrack();
                    System.out.println("Exiting...");
                    break;
                case 1:
                    player.showAllPlaylists();
                    break;
                case 2:
                    System.out.println("Enter playlist name:");
                    playlistName = scanner.nextLine();
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
                    System.out.println("Enter the file name to add playlist:");
                    String fileName = scanner.nextLine();
                    player.addPlaylistFromFile(fileName);
                    break;
                case 6:
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
                case 7:
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
                                System.out.println("Enter track file path:");
                                String filePath = scanner.nextLine();
                                Track track = new Track(filePath);
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
                                System.out.println("Enter track file path:");
                                String filePath = scanner.nextLine();
                                Track track = new Track(filePath);
                                player.addTrackToPlaylistByNumber(playlistNumberAdd, playlistNameAddByNumber, track);
                            } else {
                                System.out.println("Playlist '" + playlistNameAddByNumber + "' not found.");
                            }
                            break;
                        default:
                            System.out.println("Invalid choice.");
                    }
                    break;

                case 8:
                    System.out.println("Enter playlist name:");
                    playlistName = scanner.nextLine();
                    if (player.getPlaylists().containsKey(playlistName)) {
                        player.getPlaylist(playlistName).showTracks();
                    } else {
                        System.out.println("Playlist '" + playlistName + "' not found.");
                    }
                    break;
                case 9:
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
                    player.stopTrack();
                    break;
                case 14:
                    player.displayAllSongs();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MP3PlayerGUI();
            }
        });
    }
}

