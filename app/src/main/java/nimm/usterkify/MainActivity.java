package nimm.usterkify;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String PREF_NAME = "my_pref";
    private static final String ITEM_LIST_KEY = "item_list";

    private static final int REQUEST_IMAGE_CAPTURE = 1;

    private List<Item> itemList;
    private ListView listView;
    private CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        itemList = new ArrayList<>();
        adapter = new CustomAdapter();
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
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
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
                intent.putExtra("text", item.getText());
                startActivity(intent);
            }
        });
    }

    // Method to start the camera intent
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
            String text = "Your Text Here"; // You can prompt the user for text input here
            itemList.add(new Item(imageBitmap, text));
            saveItemListToPreferences(itemList);
            adapter.notifyDataSetChanged();
        }
    }

    // Custom adapter for the ListView
    private class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return itemList.size();
        }

        @Override
        public Object getItem(int position) {
            return itemList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.list_item, parent, false);
            }

            Item item = itemList.get(position);

            ImageView imageView = convertView.findViewById(R.id.imageView);
            TextView textView = convertView.findViewById(R.id.textView);

            imageView.setImageBitmap(item.getImage());
            textView.setText(item.getText());

            return convertView;
        }
    }

    // Data model for list items
    private static class Item {
        private Bitmap image;
        private String text;

        public Item(Bitmap image, String text) {
            this.image = image;
            this.text = text;
        }

        public Bitmap getImage() {
            return image;
        }

        public String getText() {
            return text;
        }
    }

    // Method to save item list to SharedPreferences
    private void saveItemListToPreferences(List<Item> itemList) {
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(itemList);
        editor.putString(ITEM_LIST_KEY, json);
        editor.apply();
    }

    // Method to load item list from SharedPreferences
    private List<Item> loadItemListFromPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(ITEM_LIST_KEY, "");
        Type type = new TypeToken<List<Item>>() {}.getType();
        return gson.fromJson(json, type);
    }
}
