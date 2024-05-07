import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

class MP3PlayerTest {
    private static final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void testAddPlaylist() {
        MP3Player player = new MP3Player();
        Playlist playlist = new Playlist("Test Playlist", 1);
        player.addPlaylist(playlist);
        assertTrue(player.getPlaylists().containsKey("Test Playlist"));
    }

    @Test
    public void testAddTrackToPlaylistByName() {
        MP3Player player = new MP3Player();
        Playlist playlist = new Playlist("Test Playlist", 1);
        player.addPlaylist(playlist);
        Track track = new Track("MORG.mp3");
        player.addTrackToPlaylistByName("Test Playlist", track);
        assertEquals(1, player.getPlaylist("Test Playlist").getTracks().size());
        assertEquals("", player.getPlaylist("Test Playlist").getTracks().getFirst().getTitle());
    }

    @Test
    public void testAddTrackToPlaylistByNumber() {
        MP3Player player = new MP3Player();
        Playlist playlist = new Playlist("Test Playlist", 1);
        player.addPlaylist(playlist);
        Track track = new Track("MORG.mp3");
        player.addTrackToPlaylistByNumber(1,"Test Playlist", track);

        assertEquals(1, player.getPlaylists().get("Test Playlist").getTracks().size());
        assertEquals(163, player.getPlaylists().get("Test Playlist").getTracks().getFirst().getDuration());
    }

    @Test
    public void testRemovePlaylistByName() {
        MP3Player player = new MP3Player();
        Playlist playlist = new Playlist("Test Playlist", 1);
        player.addPlaylist(playlist);
        player.removePlaylistByName("Test Playlist");
        assertFalse(player.getPlaylists().containsKey("Test Playlist"));
    }

    @Test
    public void testRemovePlaylistByNumber() {
        MP3Player player = new MP3Player();
        Playlist playlist = new Playlist("Test Playlist", 1);
        player.addPlaylist(playlist);
        player.removePlaylistByNumber(1);
        assertFalse(player.getPlaylists().containsKey("Test Playlist"));
    }

    @Test
    public void testPlayNextTrack() {
        MP3Player player = new MP3Player();
        Track track1 = new Track("MORG.mp3");
        Track track2 = new Track("DVN.mp3");
        player.createPlaylist("Test Playlist");
        player.addTrackToPlaylistByName("Test Playlist",track1);
        player.addTrackToPlaylistByName("Test Playlist",track2);
        player.playPlaylistByName("Test Playlist");
        player.playNextTrack();
        assertEquals("Now playing:  -  (132 seconds)", player.getCurrentTrackInfo());
    }

    @Test
    public void testPlayPreviousTrack() {
        MP3Player player = new MP3Player();
        Track track1 = new Track("MORG.mp3");
        Track track2 = new Track("DVN.mp3");
        player.createPlaylist("Test Playlist");
        player.addTrackToPlaylistByName("Test Playlist",track1);
        player.addTrackToPlaylistByName("Test Playlist",track2);
        player.playPlaylistByName("Test Playlist");
        player.playNextTrack();
        assertEquals("Now playing:  -  (132 seconds)", player.getCurrentTrackInfo());
    }

    @Test
    public void testRepeatCurrentTrack() {
        MP3Player player = new MP3Player();
        player.createPlaylist("Test Playlist");
        Track track = new Track("MORG.mp3");
        player.addTrackToPlaylistByName("Test Playlist",track);
        player.playPlaylistByName("Test Playlist");
        player.repeatCurrentTrack();
        assertEquals("Now playing:  -  (163 seconds)", player.getCurrentTrackInfo());
    }

    @Test
    public void testAddPlaylistFromFile() {
        MP3Player player = new MP3Player();
        player.addPlaylistFromFile("test_playlist.txt");
        assertTrue(player.getPlaylists().containsKey("test playlist"));
    }

    @Test
    public void testSavePlaylistToFileByName() {
        MP3Player player = new MP3Player();
        Playlist playlist = new Playlist("Test Playlist", 1);
        Track track = new Track("MORG.mp3");
        playlist.addTrack(track);
        player.addPlaylist(playlist);
        player.saveToFileByName("Test Playlist");
        assertTrue(new File("Test Playlist.txt").exists());
    }

    @Test
    public void testLoadPlaylistFromFileByName() {
        MP3Player player = new MP3Player();
        player.addPlaylistFromFile("test_playlist.txt");
        assertTrue(player.getPlaylists().containsKey("test playlist"));
    }

    @Test
    public void testShowAllPlaylists() {
        MP3Player player = new MP3Player();
        player.createPlaylist("TestPlaylist1");
        player.createPlaylist("TestPlaylist2");
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        player.showAllPlaylists();
        assertEquals("""
                Available playlists:\r
                1.TestPlaylist1\r
                2.TestPlaylist2\r
                """, outContent.toString());
    }

    @Test
    public void testDisplayAllSongs() {
        MP3Player player = new MP3Player();
        Playlist playlist1 = new Playlist("Playlist 1", 1);
        Playlist playlist2 = new Playlist("Playlist 2", 2);

        Track track1 = new Track("MORG.mp3");
        Track track2 = new Track("DVN.mp3");

        playlist1.addTrack(track1);
        playlist2.addTrack(track2);

        player.addPlaylist(playlist1);
        player.addPlaylist(playlist2);

        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        player.displayAllSongs();

        String expectedOutput = """
                All playlists and their songs:\r
                Playlist: Playlist 1\r
                Tracks:\r
                     -  (163 seconds)\r
                \r
                Playlist: Playlist 2\r
                Tracks:\r
                     -  (132 seconds)\r
                \r
                """;

        assertEquals(expectedOutput, outContent.toString());
    }
}
