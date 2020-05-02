package team4infinty.com;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MyPlace {
    private String name;
    private String description;

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
