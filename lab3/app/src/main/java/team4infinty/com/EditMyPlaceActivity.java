package team4infinty.com;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
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
                EditText etName=findViewById(R.id.editName);
                String name=etName.getText().toString();
                EditText etDescription=findViewById(R.id.editDescription);
                String description=etDescription.getText().toString();
                if(!editMode) {
                    MyPlace place = new MyPlace(name, description);
                    MyPlacesData.getInstance().addNewPlace(place);
                }else{
                    MyPlace place=MyPlacesData.getInstance().getPlace(pos);
                    place.setName(name);
                    place.setDescription(description);
                }
                setResult(Activity.RESULT_OK);
                finish();
                break;
            }
            case R.id.cancvel:{
                setResult(Activity.RESULT_CANCELED);
                finish();

            }
        }
    }
}


