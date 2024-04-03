package nimm.usterkify;

import androidx.annotation.NonNull;

import java.util.Date;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class Usterka {
    @Id
    public long id;
    public String title;
    public String description;
    public String imageFileName;
    public Date creationTime;

    public Usterka(long id, String title, String description, String imageFileName, @NonNull Date creationTime) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imageFileName = imageFileName;
        this.creationTime = creationTime;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImageFileName() {
        return imageFileName;
    }
}
