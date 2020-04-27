package team4infinty.com;

import java.util.ArrayList;

public class MyPlacesData {
    ArrayList<MyPlace> myPlaces;

    public MyPlacesData() {
        this.myPlaces =new ArrayList<MyPlace>();
        addNewPlace(new MyPlace("Place A"));
        addNewPlace(new MyPlace("Place B"));
        addNewPlace(new MyPlace("Place C"));
        addNewPlace(new MyPlace("Place D"));
        addNewPlace(new MyPlace("Place E"));
        addNewPlace(new MyPlace("Place G"));
    }

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
        myPlaces.add(place);
    }

    public MyPlace getPlace(int index){
        return myPlaces.get(index);
    }

    public MyPlace deletePlace(int index){
        return myPlaces.remove(index);
    }

}
