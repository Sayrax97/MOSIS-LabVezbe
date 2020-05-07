package team4infinty.com;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.preference.Preference;
import androidx.preference.PreferenceManager;

import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.tileprovider.tilesource.MapBoxTileSource;
import org.osmdroid.tileprovider.tilesource.MapQuestTileSource;
import org.osmdroid.tileprovider.tilesource.bing.BingMapTileSource;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.MapEventsOverlay;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.util.ArrayList;

public class MyPlacesMapsActivity extends AppCompatActivity {
    MapView map = null;
    static int NEW_PLACE = 1;
    IMapController mapController = null;
    private static final int PERMISSION_ACCESS_FINE_LOCATION = 1;
    public static final int SHOW_MAP = 0;
    public static final int CENTER_PLACE_ON_MAP = 1;
    public static final int SELECT_COORDINATES = 2;

    private int state = 0;
    private boolean setCoorsEnabled = false;
    private GeoPoint placeLoc;
    private ItemizedIconOverlay myPlacesOverlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            Intent mapIntent = getIntent();
            Bundle mapBundle = mapIntent.getExtras();
            if (mapBundle != null) {
                state = mapBundle.getInt("state");
                if (state == CENTER_PLACE_ON_MAP) {
                    String placeLat = mapBundle.getString("lat");
                    String placeLon = mapBundle.getString("lon");
                    placeLoc = new GeoPoint(Double.parseDouble(placeLat), Double.parseDouble(placeLon));
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        setContentView(R.layout.activity_my_places_maps);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = findViewById(R.id.fab);
        if (state == SELECT_COORDINATES) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivityForResult(new Intent(MyPlacesMapsActivity.this, EditMyPlaceActivity.class), NEW_PLACE);
                }
            });
        } else {
            ViewGroup layout = (ViewGroup) fab.getParent();
            if (layout != null)
                layout.removeView(fab);
        }
        Configuration.getInstance().load(getApplicationContext(), PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));
        map = findViewById(R.id.map);
        map.setMultiTouchControls(true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ACCESS_FINE_LOCATION);
        } else {
            setupMap();
        }
        mapController = map.getController();
        if (mapController != null) {
            mapController.setZoom(15.0);
            GeoPoint geoPoint = new GeoPoint(43.3264, 21.9361);
            mapController.setCenter(geoPoint);
        }
    }

    private void setMyLocationOverlay() {
        MyLocationNewOverlay myLocationOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(this), map);
        myLocationOverlay.enableMyLocation();
        map.getOverlays().add(myLocationOverlay);
        mapController = map.getController();
        if (mapController != null) {
            mapController.setZoom(15.0);
            myLocationOverlay.enableFollowLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_ACCESS_FINE_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    setupMap();
                }
                return;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (state == SELECT_COORDINATES && !setCoorsEnabled) {
            menu.add(0, 1, 1, "Select Coordinates");
            menu.add(0, 2, 2, "Cancvel");
            return super.onCreateOptionsMenu(menu);
        }
        getMenuInflater().inflate(R.menu.menu_my_places_maps, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (state == SELECT_COORDINATES && !setCoorsEnabled) {
            if (id == 1) {
                setCoorsEnabled = true;
                Toast.makeText(this, "Select Coordinates", Toast.LENGTH_SHORT).show();
            } else if (id == 2) {
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        }
        switch (id) {
            case R.id.new_place_item:
                startActivityForResult(new Intent(this, EditMyPlaceActivity.class), 1);
                break;
            case R.id.about_item:
                startActivity(new Intent(this, About.class));
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

    private void setOnMapClickOverlay() {
        MapEventsReceiver mReceive = new MapEventsReceiver() {
            @Override
            public boolean singleTapConfirmedHelper(GeoPoint p) {
                if (state == SELECT_COORDINATES && !setCoorsEnabled) {
                    String lon = Double.toString(p.getLongitude());
                    String lat = Double.toString(p.getLatitude());
                    Intent locationIntent = new Intent();
                    locationIntent.putExtra("lon", lon);
                    locationIntent.putExtra("lat", lat);
                    setResult(Activity.RESULT_OK, locationIntent);
                    finish();
                }
                return false;
            }

            @Override
            public boolean longPressHelper(GeoPoint p) {
                return false;
            }
        };

        MapEventsOverlay OverlayEvents = new MapEventsOverlay(mReceive);
        map.getOverlays().add(OverlayEvents);
    }

    private void setCenterPlaceOnMap() {
        mapController = map.getController();
        if (mapController != null) {
            mapController.setZoom(15.0);
            mapController.animateTo(placeLoc);
        }
    }

    private void setupMap() {
        switch (state) {
            case SHOW_MAP:
                setMyLocationOverlay();
                break;
            case SELECT_COORDINATES:
                mapController = map.getController();
                if (mapController != null) {
                    mapController.setZoom(15.0);
                    mapController.animateTo(new GeoPoint(43.3209, 21.8958));
                }
                setOnMapClickOverlay();
                break;
            case CENTER_PLACE_ON_MAP:
            default:
                setCenterPlaceOnMap();
                break;
        }
        showMyPlaces();
    }

    private void showMyPlaces() {
        if (myPlacesOverlay != null) {
            this.map.getOverlays().remove(myPlacesOverlay);
        }
        final ArrayList<OverlayItem> items = new ArrayList<>();
        for (int i = 0; i < MyPlacesData.getInstance().getMyPlaces().size(); i++) {
            MyPlace myPlace = MyPlacesData.getInstance().getMyPlaces().get(i);
            OverlayItem item = new OverlayItem(myPlace.getName(), myPlace.getDescription(), new GeoPoint(Double.parseDouble(myPlace.getLatitude()), Double.parseDouble(myPlace.getLongitude())));
            item.setMarker(getResources().getDrawable(R.drawable.baseline_beenhere_black_24));
            items.add(item);
        }
        myPlacesOverlay = new ItemizedIconOverlay<>(items, new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
            @Override
            public boolean onItemSingleTapUp(int index, OverlayItem item) {
                Intent i = new Intent(MyPlacesMapsActivity.this, ViewMyPlaceActivity.class);
                i.putExtra("position", index);
                startActivity(i);
                return true;
            }

            @Override
            public boolean onItemLongPress(int index, OverlayItem item) {
                Intent i = new Intent(MyPlacesMapsActivity.this, ViewMyPlaceActivity.class);
                i.putExtra("position", index);
                startActivityForResult(i, 5);
                return true;
            }
        }, getApplicationContext());
        map.getOverlays().add(myPlacesOverlay);
    }
}