package team4infinty.com;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    static int NEW_PLACE=1;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id)
        {
            case R.id.show_map_item:
                Toast.makeText(this, "Show Map!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this,MyPlacesMapsActivity.class));
                break;
            case R.id.new_place_item:
                startActivityForResult(new Intent(MainActivity.this,EditMyPlaceActivity.class),NEW_PLACE);
                Toast.makeText(this, "New Place!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.my_places_list:
                startActivity(new Intent(MainActivity.this,MyPlacesList.class));
                Toast.makeText(this, "My Places", Toast.LENGTH_SHORT).show();
                break;
            case R.id.about_item:
                startActivity(new Intent(MainActivity.this,About.class));
                Toast.makeText(this, "About", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode== Activity.RESULT_OK){
            Toast.makeText(this, "New Place added", Toast.LENGTH_SHORT).show();
        }
    }
}
