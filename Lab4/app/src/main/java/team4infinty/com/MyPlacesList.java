package team4infinty.com;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MyPlacesList extends AppCompatActivity {

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
                startActivityForResult(new Intent(this,EditMyPlaceActivity.class),1);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_places_list);

//        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar1);
//        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ListView myPlacesList=findViewById(R.id.list);
        myPlacesList.setAdapter(new ArrayAdapter<MyPlace>(this,android.R.layout.simple_spinner_item,MyPlacesData.getInstance().getMyPlaces()));

        myPlacesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MyPlace place=(MyPlace) parent.getAdapter().getItem(position);
                Toast.makeText(MyPlacesList.this, place.toString()+" "+"selected", Toast.LENGTH_SHORT).show();
                Bundle bundle=new Bundle();
                bundle.putInt("id",position);
                Intent intent=new Intent(MyPlacesList.this,ViewMyPlaceActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
        }
        });
        myPlacesList.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                AdapterView.AdapterContextMenuInfo info=(AdapterView.AdapterContextMenuInfo) menuInfo;
                MyPlace place=MyPlacesData.getInstance().getPlace(info.position);
                menu.setHeaderTitle(place.getName());
                menu.add(0,1,1,"View place");
                menu.add(0,2,2,"Edite place");
                menu.add(0,3,3,"Delete place");
                menu.add(0,4,4,"Show place");
            }
        });
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info=(AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Bundle bundle=new Bundle();
        bundle.putInt("id",info.position);
        Intent i=null;
        if(item.getItemId()==1){
            i=new Intent(MyPlacesList.this,ViewMyPlaceActivity.class);
            i.putExtras(bundle);
            startActivity(i);
        }
        else if(item.getItemId()==2){
            i=new Intent(this,EditMyPlaceActivity.class);
            i.putExtras(bundle);
            startActivity(i);
        }
        else if(item.getItemId()==3){
            MyPlacesData.getInstance().deletePlace(info.position);
            setList();
        }
        else if(item.getItemId()==4){
            i= new Intent(this,MyPlacesMapsActivity.class);
            i.putExtra("state",MyPlacesMapsActivity.CENTER_PLACE_ON_MAP);
            MyPlace place=MyPlacesData.getInstance().getPlace(info.position);
            i.putExtra("lat",place.getLatitude());
            i.putExtra("lon",place.getLongitude());
            startActivityForResult(i,2);
        }

        return super.onContextItemSelected(item);
    }

    private void setList(){
        ListView myPlacesList=findViewById(R.id.list);
        myPlacesList.setAdapter(new ArrayAdapter<MyPlace>(this,android.R.layout.simple_spinner_item,MyPlacesData.getInstance().getMyPlaces()));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode== Activity.RESULT_OK){
            ListView myPlacesList=findViewById(R.id.list);
            myPlacesList.setAdapter(new ArrayAdapter<MyPlace>(this,android.R.layout.simple_spinner_item,MyPlacesData.getInstance().getMyPlaces()));

        }
    }
}
