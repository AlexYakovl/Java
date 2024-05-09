import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;



public class MP3PlayerGUI extends JFrame {
    private final MP3Player player;
    private final JTextArea playlistTextArea;

    public MP3PlayerGUI() {
        player = new MP3Player();
        final String[] trackinfo = new String[1];
        setTitle("MP3 Player");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));
        Font font = new Font("Montserrat Light", Font.PLAIN, 16);
        JScrollPane scrollPane = new JScrollPane();
        playlistTextArea = new JTextArea();
        scrollPane.setViewportView(playlistTextArea);
        playlistTextArea.setEditable(false);
        playlistTextArea.setFont(font);

        final Color blackColor = new Color(16, 16, 16, 255);
        final Color grayColor = new Color(35, 35, 35, 255);
        final Color orangeColor = new Color(255, 255, 255);
        //-------------------------------- кнопки -----------------------------------
        ImageIcon icon = new ImageIcon("allplaylist.png");
        JButton showAllPlaylistsButton = new JButton(icon);
        showAllPlaylistsButton.addActionListener(_ -> showAllPlaylistsGUI());

        icon = new ImageIcon("alltracks.png");
        JButton showAllTracksButton= new JButton(icon);
        showAllTracksButton.addActionListener(_ -> showAllTracksButtonGUI());

        icon = new ImageIcon("tracks.png");
        JButton showPlaylistTracksButton = new JButton(icon);
        showPlaylistTracksButton.addActionListener(_ -> {
            String playlistName = JOptionPane.showInputDialog("Enter playlist name:");
            showPlaylistTracksButtonGUI(playlistName);
        });
        icon = new ImageIcon("create.png");
        JButton createPlaylistButton = new JButton(icon);
        createPlaylistButton.addActionListener(_ -> CreatePlaylistsGUI());

        JButton deletePlaylistButton = getDeletePlaylistButton();

        JButton playPlaylistButton = getPlayPlaylistButton(trackinfo);

        JButton addTrack = getAddTrack();

        JButton deleteTrack = getDeleteTrack();

        JButton SavePlaylistButton = getSavePlaylistButton();
        icon = new ImageIcon("load.png");
        JButton LoadPlaylistButton = new JButton(icon);
        LoadPlaylistButton.addActionListener(_ -> {
            String playlistName = JOptionPane.showInputDialog("Enter file playlist filename:");
            player.addPlaylistFromFile(playlistName);
            updatePlaylistTextArea("Playlist '" + playlistName + "' was loaded");
        });
        icon = new ImageIcon("next.png");
        JButton nextButton = new JButton(icon);
        nextButton.addActionListener(_ -> {
            player.playNextTrack();
            trackinfo[0] = player.getCurrentTrackInfo();
            updatePlaylistTextArea(Arrays.toString(trackinfo));
        });
        icon = new ImageIcon("exit.png");
        JButton exitButton = new JButton(icon);
        exitButton.addActionListener(_ -> {
            int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Exit", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
        icon = new ImageIcon("prev.png");
        JButton previousButton = new JButton(icon);
        previousButton.addActionListener(_ -> {
            player.playPreviousTrack();
            trackinfo[0] = player.getCurrentTrackInfo();
            updatePlaylistTextArea(Arrays.toString(trackinfo));
        });
        icon = new ImageIcon("pause.png");
        JButton stopButton = new JButton(icon);
        stopButton.addActionListener(_ -> {
            player.stopTrack();
            trackinfo[0] = player.getCurrentTrackInfo();
            updatePlaylistTextArea(Arrays.toString(trackinfo));
        });
        icon = new ImageIcon("repeat.png");
        JButton repeatButton = new JButton(icon);
        repeatButton.addActionListener(_ -> {
            player.repeatCurrentTrack();
            trackinfo[0] = player.getCurrentTrackInfo();
            updatePlaylistTextArea(Arrays.toString(trackinfo));
        });

        repeatButton.setFocusPainted(false);
        repeatButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                repeatButton.setBackground(blackColor); // Установка цвета текста кнопки на черный при нажатии
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                repeatButton.setBackground(grayColor); // Возвращение цвета текста кнопки на исходный цвет после отпускания
            }
        });


        //----------------------------------дизайн---------------------------------

        showAllPlaylistsButton.setForeground(orangeColor);
        showAllPlaylistsButton.setBackground(grayColor);
        playlistTextArea.setBackground(grayColor);
        playlistTextArea.setForeground(orangeColor);
        createPlaylistButton.setBackground(grayColor);
        createPlaylistButton.setForeground(orangeColor);
        deletePlaylistButton.setBackground(grayColor);
        deletePlaylistButton.setForeground(orangeColor);
        SavePlaylistButton.setBackground(grayColor);
        SavePlaylistButton.setForeground(orangeColor);
        LoadPlaylistButton.setBackground(grayColor);
        LoadPlaylistButton.setForeground(orangeColor);
        addTrack.setBackground(grayColor);
        addTrack.setForeground(orangeColor);
        deleteTrack.setBackground(grayColor);
        deleteTrack.setForeground(orangeColor);
        showPlaylistTracksButton.setBackground(grayColor);
        showPlaylistTracksButton.setForeground(orangeColor);
        showAllTracksButton.setBackground(grayColor);
        showAllTracksButton.setForeground(orangeColor);
        playPlaylistButton.setBackground(grayColor);
        playPlaylistButton.setForeground(orangeColor);
        nextButton.setBackground(grayColor);
        nextButton.setForeground(orangeColor);
        previousButton.setBackground(grayColor);
        previousButton.setForeground(orangeColor);
        stopButton.setBackground(grayColor);
        stopButton.setForeground(orangeColor);
        repeatButton.setBackground(grayColor);
        repeatButton.setForeground(orangeColor);
        exitButton.setBackground(grayColor);
        exitButton.setForeground(orangeColor);






























        JPanel instrumentPanel = new JPanel(new GridLayout(3, 3));
        instrumentPanel.add(SavePlaylistButton);
        instrumentPanel.add(showAllPlaylistsButton);
        instrumentPanel.add(createPlaylistButton);

        instrumentPanel.add(addTrack);
        instrumentPanel.add(showAllTracksButton);
        instrumentPanel.add(LoadPlaylistButton);

        instrumentPanel.add(deleteTrack);
        instrumentPanel.add(showPlaylistTracksButton);
        instrumentPanel.add(deletePlaylistButton);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 1));


        JPanel buttonSwitchs = new JPanel(new GridLayout(1, 4));
        buttonSwitchs.add(playPlaylistButton);
        buttonSwitchs.add(previousButton);
        buttonSwitchs.add(stopButton);
        buttonSwitchs.add(nextButton);
        buttonSwitchs.add(repeatButton);


        buttonPanel.add(buttonSwitchs);
        buttonPanel.add(buttonSwitchs);
        buttonPanel.add(buttonSwitchs);
        buttonPanel.add(buttonSwitchs);

        buttonPanel.add(exitButton);

        panel.add(instrumentPanel);
        panel.add(scrollPane);
        panel.add(buttonPanel);

        add(panel);
        setVisible(true);
    }

    private JButton getSavePlaylistButton() {
        ImageIcon icon = new ImageIcon("save.png");
        JButton SavePlaylistButton = new JButton(icon);
        SavePlaylistButton.addActionListener(_ -> {
            String saveChoiceString = JOptionPane.showInputDialog("How do you want to save the playlist into a file?\n1 - By name\n2 - By number\nEnter your choice:");
            int saveChoice = Integer.parseInt(saveChoiceString);

            switch (saveChoice) {
                case 1:
                    String playlistName = JOptionPane.showInputDialog("Enter playlist name:");
                    player.saveToFileByName(playlistName);
                    updatePlaylistTextArea("Playlist '" + playlistName  + "' successfully saved into a file");
                    break;
                case 2:
                    String playlistNumberstr = JOptionPane.showInputDialog("Enter playlist number:");
                    int playlistNumber = Integer.parseInt(playlistNumberstr);
                    String plName = player.getPlaylistNameByNumber(playlistNumber);
                    player.saveToFileByNumber(playlistNumber);
                    updatePlaylistTextArea("Playlist '" + plName  + "' successfully saved into a file");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Invalid choice.");
            }
        });
        return SavePlaylistButton;
    }

    private JButton getDeleteTrack() {
        ImageIcon icon = new ImageIcon("deletetr.png");
        JButton deleteTrack = new JButton(icon);
        deleteTrack.addActionListener(_ -> {
            String deleteChoiceString = JOptionPane.showInputDialog("How do you want to delete track from the playlist?\n1 - By name\n2 - By number\nEnter your choice:");
            int deleteChoice = Integer.parseInt(deleteChoiceString);

            switch (deleteChoice) {
                case 1:
                    String playlistName = JOptionPane.showInputDialog("Enter playlist name:");
                    String trackNumber = JOptionPane.showInputDialog("Enter track number to remove:");
                    int trackindex= Integer.parseInt(trackNumber);
                    player.removeTrackFromPlaylistByName(playlistName, trackindex);
                    updatePlaylistTextArea("Track with number '" + trackindex  + "' successfully deleted");
                    break;
                case 2:
                    String playlistNumberstr = JOptionPane.showInputDialog("Enter playlist number:");
                    int playlistNumber = Integer.parseInt(playlistNumberstr);
                    playlistName = player.getPlaylistNameByNumber(playlistNumber);
                    trackNumber = JOptionPane.showInputDialog("Enter track number to remove:");
                    trackindex= Integer.parseInt(trackNumber);
                    player.removeTrackFromPlaylistByName(playlistName, trackindex);
                    updatePlaylistTextArea("Track with number '" + trackindex  + "' successfully deleted");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Invalid choice.");
            }
        });
        return deleteTrack;
    }

    private JButton getAddTrack() {
        ImageIcon icon = new ImageIcon("add.png");
        JButton addTrack = new JButton(icon);
        addTrack.addActionListener(_ -> {
            String addChoiceString = JOptionPane.showInputDialog("How do you want to add track to the playlist?\n1 - By name\n2 - By number\nEnter your choice:");
            int addChoice = Integer.parseInt(addChoiceString);

            switch (addChoice) {
                case 1:
                    String playlistName = JOptionPane.showInputDialog("Enter playlist name:");
                    String trackfileName = JOptionPane.showInputDialog("Enter track file name:");
                    Track track = new Track(trackfileName);
                    player.addTrackToPlaylistByName(playlistName, track);
                    updatePlaylistTextArea("Track: '" + trackfileName + "' successfully added");
                    break;
                case 2:
                    String playlistNumberstr = JOptionPane.showInputDialog("Enter playlist number:");
                    int playlistNumber = Integer.parseInt(playlistNumberstr);
                    playlistName = player.getPlaylistNameByNumber(playlistNumber);
                    trackfileName = JOptionPane.showInputDialog("Enter track file name:");
                    track = new Track(trackfileName);
                    player.addTrackToPlaylistByNumber(playlistNumber,playlistName, track);
                    updatePlaylistTextArea("Track: '" + trackfileName + "' successfully added");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Invalid choice.");
            }
        });
        return addTrack;
    }

    private JButton getPlayPlaylistButton(String[] trackinfo) {
        ImageIcon icon = new ImageIcon("play.png");
        JButton playPlaylistButton = new JButton(icon);
        playPlaylistButton.addActionListener(_ -> {
            String playChoiceString = JOptionPane.showInputDialog("How do you want to play the playlist?\n1 - By name\n2 - By number\nEnter your choice:");
            int playChoice = Integer.parseInt(playChoiceString);

            switch (playChoice) {
                case 1:
                    String playlistName = JOptionPane.showInputDialog("Enter playlist name:");
                    player.playPlaylistByName(playlistName);
                    updatePlaylistTextArea("Playlist '" + playlistName + "' is playing");
                    trackinfo[0] = player.getCurrentTrackInfo();
                    updatePlaylistTextArea(Arrays.toString(trackinfo));
                    break;
                case 2:
                    String playlistNumberString = JOptionPane.showInputDialog("Enter playlist number:");
                    int playlistNumber = Integer.parseInt(playlistNumberString);
                    player.playPlaylistByNumber(playlistNumber);
                    updatePlaylistTextArea("Playlist number '" + playlistNumber + "' is playing");
                    trackinfo[0] = player.getCurrentTrackInfo();
                    updatePlaylistTextArea(Arrays.toString(trackinfo));
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Invalid choice.");
            }
        });
        return playPlaylistButton;
    }

    private JButton getDeletePlaylistButton() {
        ImageIcon icon = new ImageIcon("delete.png");
        JButton deletePlaylistButton = new JButton(icon);
        deletePlaylistButton.addActionListener(_ -> {
            String deleteChoiceString = JOptionPane.showInputDialog("How do you want to delete the playlist?\n1 - By name\n2 - By number\nEnter your choice:");
            int deleteChoice = Integer.parseInt(deleteChoiceString);

            switch (deleteChoice) {
                case 1:
                    String playlistName = JOptionPane.showInputDialog("Enter playlist name:");
                    player.removePlaylistByName(playlistName);
                    updatePlaylistTextArea("Playlist '" + playlistName + "' deleted");
                    break;
                case 2:
                    String playlistNumberstr = JOptionPane.showInputDialog("Enter playlist number:");
                    int playlistNumber = Integer.parseInt(playlistNumberstr);
                    String plName = player.getPlaylistNameByNumber(playlistNumber);
                    player.removePlaylistByNumber(playlistNumber);
                    updatePlaylistTextArea("Playlist '" + plName + "' deleted");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Invalid choice.");
            }
        });
        return deletePlaylistButton;
    }

    private void showAllPlaylistsGUI() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        PrintStream originalStream = System.out;
        System.setOut(printStream); // Перенаправляем стандартный вывод в поток вывода
        player.showAllPlaylists(); // Вызываем вашу функцию для вывода плейлистов
        System.out.flush(); // Принудительно сбрасываем буфер
        System.setOut(originalStream); // Восстанавливаем стандартный вывод
        String playlistsOutput = outputStream.toString(); // Получаем вывод из потока
        updatePlaylistTextArea(playlistsOutput); // Выводим информацию о плейлистах в текстовое поле
    }

    private void CreatePlaylistsGUI() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        PrintStream originalStream = System.out;
        System.setOut(printStream); // Перенаправляем стандартный вывод в поток вывода
        String playlistName = JOptionPane.showInputDialog("Enter playlist name:");
        player.createPlaylist(playlistName);
        System.out.flush(); // Принудительно сбрасываем буфер
        System.setOut(originalStream); // Восстанавливаем стандартный вывод
        String playlistsOutput = outputStream.toString(); // Получаем вывод из потока
        updatePlaylistTextArea(playlistsOutput); // Выводим информацию о плейлистах в текстовое поле
    }

    private void showPlaylistTracksButtonGUI(String playlistName) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        PrintStream originalStream = System.out;
        System.setOut(printStream); // Перенаправляем стандартный вывод в поток вывода
        player.getPlaylist(playlistName).showTracks(); // Вызываем вашу функцию для вывода плейлистов
        System.out.flush(); // Принудительно сбрасываем буфер
        System.setOut(originalStream); // Восстанавливаем стандартный вывод
        String playlistsOutput = outputStream.toString(); // Получаем вывод из потока
        updatePlaylistTextArea(playlistsOutput); // Выводим информацию о плейлистах в текстовое поле
    }


    private void showAllTracksButtonGUI() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        PrintStream originalStream = System.out;
        System.setOut(printStream); // Перенаправляем стандартный вывод в поток вывода
        player.displayAllSongs(); // Вызываем вашу функцию для вывода плейлистов
        System.out.flush(); // Принудительно сбрасываем буфер
        System.setOut(originalStream); // Восстанавливаем стандартный вывод
        String playlistsOutput = outputStream.toString(); // Получаем вывод из потока
        updatePlaylistTextArea(playlistsOutput); // Выводим информацию о плейлистах в текстовое поле
    }

    private void updatePlaylistTextArea(String message) {
        playlistTextArea.append(message + "\n");
        playlistTextArea.setCaretPosition(playlistTextArea.getDocument().getLength()); // Прокручиваем текст вниз
    }

}