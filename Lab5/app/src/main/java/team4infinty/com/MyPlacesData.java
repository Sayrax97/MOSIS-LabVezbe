package team4infinty.com;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class MyPlacesData {
    ArrayList<MyPlace> myPlaces;
    private HashMap<String,Integer> myPlacesKeyIndexMapping;
    private DatabaseReference database;
    public static final String FIREBASE_CHILD="my-places";
    private static final String TAG = "team4infinty.com";

    public MyPlacesData() {
        this.myPlaces =new ArrayList<MyPlace>();
        myPlacesKeyIndexMapping= new HashMap<String, Integer>();
        database= FirebaseDatabase.getInstance().getReference();
        database.child(FIREBASE_CHILD).addChildEventListener(childEventListener);
    }
    ChildEventListener childEventListener=new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };
    ValueEventListener parentEventListener=new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    public ArrayList<MyPlace> getMyPlaces() {
        return myPlaces;
    }

    public void setMyPlaces(ArrayList<MyPlace> myPlaces) {
        this.myPlaces = myPlaces;
    }

    private static class ChamberOfSecrets{
        public static final MyPlacesData instance= new MyPlacesData();
    }
    public static MyPlacesData getInstance() {
        return ChamberOfSecrets.instance;
    }

    public void addNewPlace(MyPlace place){
        Log.d(TAG, "addNewPlace: 1");
        String key=database.getKey();
        Log.d(TAG, "addNewPlace: 2");
        myPlaces.add(place);
        Log.d(TAG, "addNewPlace: 3");
        myPlacesKeyIndexMapping.put(key,myPlaces.size()-1);
        Log.d(TAG, "addNewPlace: 4");
        database.child(FIREBASE_CHILD).child(key).setValue(place);
        Log.d(TAG, "addNewPlace: 5");
        place.key=key;
        Log.d(TAG, "addNewPlace: 6");
    }

    public MyPlace getPlace(int index){
        return myPlaces.get(index);
    }

    public void deletePlace(int index){
        database.child(FIREBASE_CHILD).child(myPlaces.get(index).key).removeValue();
        myPlaces.remove(index);
        recreateIndexMapping();
    }

    private void recreateIndexMapping(){
        myPlacesKeyIndexMapping.clear();
        for (int i = 0; i < myPlaces.size(); i++) {
            myPlacesKeyIndexMapping.put(myPlaces.get(i).key,i);
        }
    }

    public  void updatePlace(int i,String name,String desc,String lon,String lat) {
        MyPlace myPlace=myPlaces.get(i);
        myPlace.name=name;
        myPlace.description=desc;
        myPlace.latitude=lat;
        myPlace.longitude=lon;
        database.child(FIREBASE_CHILD).child(myPlace.key).setValue(myPlace);
    }
}
