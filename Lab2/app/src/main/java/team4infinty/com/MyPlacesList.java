package team4infinty.com;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MyPlacesList extends AppCompatActivity {
    ArrayList<String> places;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_my_places_list,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();

        switch (id)
        {
            case R.id.show_map_item:
                Toast.makeText(this, "Show Map!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.new_place_item:
                Toast.makeText(this, "New Place!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.about_item:
                startActivity(new Intent(this,About.class));
                Toast.makeText(this, "About", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_places_list);

        places=new ArrayList<String>();
        places.add("Park Svetog Save");
        places.add("Tvrdjava");
        places.add("Cair");
        places.add("Trg Kralja Milana");

        ListView myPlacesList=findViewById(R.id.list);
        myPlacesList.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,places));
    }
}
