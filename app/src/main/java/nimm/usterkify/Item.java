package nimm.usterkify;

import android.graphics.Bitmap;

public class Item {
    private Bitmap image;
    private Usterka usterka;

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
