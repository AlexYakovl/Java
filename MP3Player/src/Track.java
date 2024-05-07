import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import com.mpatric.mp3agic.Mp3File;
import java.io.File;

public class Track {
    private String title;
    private String Author;
    private Long Duration;
    private final String filePath;
    private Double framerate;

    public Track(String filePath) {
        this.filePath = filePath;

        try {

            Mp3File mp3File = new Mp3File(filePath);
            this.Duration = mp3File.getLengthInSeconds();
            framerate = (double)mp3File.getFrameCount() / mp3File.getLengthInMilliseconds();

            AudioFile audioFile = AudioFileIO.read(new File(filePath));

            Tag tag = audioFile.getTag();
            if (tag != null) {
                this.title = tag.getFirst(FieldKey.TITLE);
                this.Author = tag.getFirst(FieldKey.ARTIST);
            } else {
                this.title = mp3File.getFilename();
                this.Author = "Unknown Artist";
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return Author;
    }

    public Long getDuration() {
        return Duration;
    }

    public String getFormatedLength() {
        Long minutes = Duration / 60;
        Long seconds = Duration % 60;

        return String.format("%02d:%02d", minutes, seconds);
    }

    public String getFilePath() {
        return filePath;
    }

    public double getFramerate() {
        return framerate;
    }

    @Override
    public String toString() {
        return "Track{" +
                "title='" + title + '\'' +
                ", artist='" + Author + '\'' +
                ", length=" + Duration +
                ", filePath='" + filePath + '\'' +
                '}';
    }
}
