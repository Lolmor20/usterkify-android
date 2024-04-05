package nimm.usterkify.data;

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
    public long userId;

    public static long NO_USER_ID = 0;

    public Usterka(long id, String title, String description, String imageFileName, @NonNull Date creationTime, long userId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imageFileName = imageFileName;
        this.creationTime = creationTime;
        this.userId = userId;
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

    public void setId(long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
