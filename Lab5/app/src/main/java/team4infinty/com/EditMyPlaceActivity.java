package team4infinty.com;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditMyPlaceActivity extends AppCompatActivity implements View.OnClickListener {
    boolean editMode=true;
    int pos=-1;
    private static final String TAG = "team4infinty.com";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            Intent listenIntent=getIntent();
            Bundle bundle=listenIntent.getExtras();
            if(bundle!=null)
                pos=bundle.getInt("id");
            else{
                editMode=false;
            }
        } catch (Exception e) {
            editMode=false;
        }
        setContentView(R.layout.activity_edit_my_place);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        final Button finishButton=findViewById(R.id.finished);
        finishButton.setOnClickListener(this);
        Button cancvelButton=findViewById(R.id.cancvel);
        cancvelButton.setOnClickListener(this);
        finishButton.setEnabled(false);



        EditText nameET=findViewById(R.id.editName);
        if(!editMode){
            finishButton.setEnabled(false);

        }
        else if(pos>=0){
            finishButton.setText("Save");
            MyPlace place=MyPlacesData.getInstance().getPlace(pos);
            nameET.setText(place.getName());
            EditText descriptionET=findViewById(R.id.editDescription);
            descriptionET.setText(place.getDescription());
        }
        nameET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                finishButton.setEnabled(true);
            }
        });

        Button locationBtn=findViewById(R.id.buttonLocation);
        locationBtn.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.finished:{
                EditText latEdit=findViewById(R.id.editLatitude);
                String lat=latEdit.getText().toString();
                EditText lonEdit=findViewById(R.id.editLongitude);
                String lon=lonEdit.getText().toString();
                EditText etName=findViewById(R.id.editName);
                String name=etName.getText().toString();
                EditText etDescription=findViewById(R.id.editDescription);
                String description=etDescription.getText().toString();
                if(!editMode) {
                    MyPlace place = new MyPlace(name, description);
                    place.setLatitude(lat);
                    place.setLongitude(lon);
                    MyPlacesData.getInstance().addNewPlace(place);
                }else{
                    MyPlacesData.getInstance().updatePlace(pos,name,description,lon,lat);
                }
                setResult(Activity.RESULT_OK);
                finish();
                break;
            }
            case R.id.cancvel:{
                setResult(Activity.RESULT_CANCELED);
                finish();
                break;
            }
            case R.id.buttonLocation:{
                startActivityForResult(new Intent(EditMyPlaceActivity.this,MyPlacesMapsActivity.class)
                        .putExtra("state",MyPlacesMapsActivity.SELECT_COORDINATES),1);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if(resultCode==Activity.RESULT_OK){
                String lon=data.getExtras().getString("lon");
                EditText lonText=findViewById(R.id.editLongitude);
                lonText.setText(lon);
                String lat=data.getExtras().getString("lat");
                EditText latText=findViewById(R.id.editLatitude);
                latText.setText(lat);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}


