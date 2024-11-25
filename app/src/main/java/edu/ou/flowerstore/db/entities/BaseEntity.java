package edu.ou.flowerstore.db.entities;

import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

public class BaseEntity {
    @PrimaryKey(autoGenerate = true)
    protected long id;

    @ColumnInfo(name = "created_at", defaultValue = "CURRENT_TIMESTAMP")
    public long createdAt;

    @ColumnInfo(name = "updated_at", defaultValue = "CURRENT_TIMESTAMP")
    public long updatedAt;

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
