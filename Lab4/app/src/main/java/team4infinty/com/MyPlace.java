package team4infinty.com;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MyPlace {
    private String name;
    private String description;
    private String longitude;
    private String latitude;
    private long ID;

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MyPlace(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public MyPlace(String name) {
        this.name = name;

    }

    @Override
    public String toString() {
        return name;
    }
}
