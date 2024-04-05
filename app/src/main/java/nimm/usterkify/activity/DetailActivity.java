package nimm.usterkify.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import nimm.usterkify.R;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView imageView = findViewById(R.id.detailImageView);
        TextView titleView = findViewById(R.id.titleTextView);
        TextView descriptionView = findViewById(R.id.descriptionTextView);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Bitmap imageBitmap = extras.getParcelable("image");
            String text = extras.getString("title");
            String description = extras.getString("description");

            imageView.setImageBitmap(imageBitmap);
            titleView.setText(text);
            descriptionView.setText(description);
        }
    }
}
