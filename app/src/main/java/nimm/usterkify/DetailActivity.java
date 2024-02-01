package nimm.usterkify;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView imageView = findViewById(R.id.detailImageView);
        TextView textView = findViewById(R.id.detailTextView);

        // Pobierz dane przekazane z poprzedniego widoku
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Bitmap imageBitmap = extras.getParcelable("image");
            String text = extras.getString("text");

            // Ustaw obrazek i tekst w odpowiednich widokach
            imageView.setImageBitmap(imageBitmap);
            textView.setText(text);
        }
    }
}
