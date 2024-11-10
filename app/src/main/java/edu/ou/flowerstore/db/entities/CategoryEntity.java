package edu.ou.flowerstore.db.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

@Entity(tableName = "categories")
public class CategoryEntity extends BaseEntity {
    public static enum Status {
        ACTIVE,
        INACTIVE
    }

    public static class StatusConverter {
        @TypeConverter
        public static Status fromString(String value) {
            return value == null ? null : Status.valueOf(value);
        }

        @TypeConverter
        public static String toString(Status status) {
            return status == null ? null : status.toString();
        }
    }

    @ColumnInfo()
    public String name;

    @ColumnInfo()
    public String description;

    @ColumnInfo()
    public String thumbnail;

    @ColumnInfo()
    @TypeConverters(StatusConverter.class)
    public Status status;
}
