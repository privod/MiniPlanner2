package ru.home.miniplanner2.model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;

/**
 * Created by privod on 19.10.2015.
 */
public abstract class Domain implements Serializable {

    @DatabaseField(generatedId = true)
    private Long id;
//    @DatabaseField(dataType = DataType.BOOLEAN)
//    private boolean isChecked;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

//    public boolean isChecked() {
//        return isChecked;
//    }
//    public void setChecked(boolean checked) {
//        isChecked = checked;
//    }
}
