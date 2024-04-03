package nimm.usterkify;

import android.graphics.Bitmap;

public class Item {
    private final Bitmap image;
    private final Usterka usterka;

    public Item(Bitmap image, Usterka usterka) {
        this.image = image;
        this.usterka = usterka;
    }

    public Bitmap getImage() {
        return image;
    }

    public Usterka getUsterka() {
        return usterka;
    }
}
