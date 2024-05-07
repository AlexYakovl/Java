import javax.swing.*;
import java.awt.*;

public class MP3PlayerGUI extends JFrame {
    private final MP3Player player;

    public MP3PlayerGUI() {
        player = new MP3Player();

        setTitle("MP3 Player");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));

        JButton showAllPlaylistsButton = new JButton("Show All Playlists");
        showAllPlaylistsButton.addActionListener(_ -> player.showAllPlaylists());

        JButton createPlaylistButton = new JButton("Create Playlist");
        createPlaylistButton.addActionListener(_ -> {
            String playlistName = JOptionPane.showInputDialog("Enter playlist name:");
            player.createPlaylist(playlistName);
        });

        // Добавьте кнопки для других функций

        panel.add(showAllPlaylistsButton);
        panel.add(createPlaylistButton);

        // Добавьте другие кнопки в панель

        add(panel);
        setVisible(true);
    }
}