package team4infinty.com;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ViewMyPlaceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_my_place);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        int pos=-1;
        try {
            Intent listenIntent=getIntent();
            Bundle bundle=listenIntent.getExtras();
            pos=bundle.getInt("id");
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            finish();
        }
        if(pos>=0){
            MyPlace place=MyPlacesData.getInstance().getPlace(pos);
            TextView txtName=findViewById(R.id.view_my_place_name);
            txtName.setText(place.getName());
            TextView txtDescription=findViewById(R.id.view_my_place_description);
            txtDescription.setText(place.getDescription());
            TextView txtLatitude=findViewById(R.id.editLatitudeView);
            txtLatitude.setText(place.getLatitude());
            TextView txtLongitude=findViewById(R.id.editLongitudeView);
            txtLongitude.setText(place.getLongitude());
        }
        final Button finishedButton=findViewById(R.id.view_my_place_button);
        finishedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_my_place,menu);
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
            case R.id.my_places_list:
                startActivityForResult(new Intent(this,MyPlacesList.class),1);
                Toast.makeText(this, "New Place!", Toast.LENGTH_SHORT).show();
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
}
