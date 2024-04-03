package nimm.usterkify;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import io.objectbox.BoxStore;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private static final int REQUEST_IMAGE_CAPTURE = 1;

    private ListView listView;
    private CustomAdapter adapter;
    private BoxStore boxStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boxStore = MyObjectBox.builder().androidContext(this).build();

        listView = findViewById(R.id.listView);
        List<Item> itemList = boxStore.boxFor(Usterka.class).getAll().stream()
                .map(usterka -> new Item(getBitmapFromFile(usterka.getImageFileName()), usterka))
                .collect(Collectors.toList());
        adapter = new CustomAdapter(itemList);
        listView.setAdapter(adapter);

        // Floating action button
        findViewById(R.id.fab_add).setOnClickListener(new View.OnClickListener() {
            private final ActivityResultLauncher<String> requestPermissionLauncher =
                    registerForActivityResult(new RequestPermission(), isGranted -> {
                        if (isGranted) {
                            dispatchTakePictureIntent();
                        } else {
                            Log.i(TAG, "Camera permission not granted");
                        }
                    });

            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    dispatchTakePictureIntent();
                } else {
                    requestPermissionLauncher.launch(Manifest.permission.CAMERA);
                }
            }
        });

        // Click listener for list items
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Item item = itemList.get(position);
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("image", item.getImage());
                intent.putExtra("title", item.getUsterka().getTitle());
                intent.putExtra("description", item.getUsterka().getDescription());
                startActivity(intent);
            }
        });
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } catch (ActivityNotFoundException e) {
            Log.e(TAG, e.getMessage());
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            UsterkaDetailsDialogFragment dialog = new UsterkaDetailsDialogFragment();

            dialog.setOnSubmitClickListener(new UsterkaDetailsDialogFragment.OnSubmitClickListener() {
                @Override
                public void onSubmit(String title, String description) {
                    Date now = Calendar.getInstance().getTime();
                    String fileName = now + ".png";
                    Usterka usterka = new Usterka(0, title, description, fileName, now);
                    try {
                        saveUsterka(usterka, imageBitmap);
                        adapter.itemList.add(new Item(imageBitmap, usterka));
                        adapter.notifyDataSetChanged();
                    } catch (IOException e) {
                        Log.e(TAG, e.getMessage());
                    }
                }
            });
            dialog.show(getFragmentManager(), "UsterkaDetailsDialogFragment");
        }
    }

    private void saveUsterka(Usterka usterka, Bitmap imageBitmap) throws IOException {
        FileOutputStream out = new FileOutputStream(getFilesDir() + usterka.getImageFileName());
        imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
        boxStore.boxFor(Usterka.class).put(usterka);
    }

    private Bitmap getBitmapFromFile(String fileName) {
        File mSaveBit = new File(getFilesDir() + fileName);
        String filePath = mSaveBit.getPath();
        return BitmapFactory.decodeFile(filePath);
    }
}
