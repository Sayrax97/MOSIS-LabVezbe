package team4infinty.com;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.Preference;
import androidx.preference.PreferenceManager;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.MapBoxTileSource;
import org.osmdroid.tileprovider.tilesource.MapQuestTileSource;
import org.osmdroid.tileprovider.tilesource.bing.BingMapTileSource;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

public class MyPlacesMapsActivity extends AppCompatActivity {
    MapView map=null;
    static int NEW_PLACE=1;
    IMapController mapController=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_places_maps);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(MyPlacesMapsActivity.this,EditMyPlaceActivity.class),NEW_PLACE);
            }
        });
        Configuration.getInstance().load(getApplicationContext(), PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));
        map=findViewById(R.id.map);
        map.setMultiTouchControls(true);
        mapController=map.getController();
        if (mapController!=null){
            mapController.setZoom(15.0);
            GeoPoint geoPoint=new GeoPoint(43.3264, 21.9361);
            mapController.setCenter(geoPoint);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_my_places_maps,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();

        switch (id)
        {
            case R.id.new_place_item:
                startActivityForResult(new Intent(this,EditMyPlaceActivity.class),1);
                break;
            case R.id.about_item:
                startActivity(new Intent(this,About.class));
                Toast.makeText(this, "About", Toast.LENGTH_SHORT).show();
                break;
            case android.R.id.home:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        map.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        map.onPause();
    }
}
