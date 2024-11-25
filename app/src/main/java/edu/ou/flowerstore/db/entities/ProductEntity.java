package edu.ou.flowerstore.db.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

@Entity(tableName = "products")
public class ProductEntity extends BaseEntity {
    public static enum StatusEnum {
        ACTIVE,
        INACTIVE,
        SOLD_OUT
    }

    public ProductEntity () {

    }

    public ProductEntity(String name, int price) {
        this.name = name;
        this.price = price;
    }

    static public class StatusConverter {
        @TypeConverter
        public static StatusEnum fromString(String value) {
            return value == null ? null : StatusEnum.valueOf(value);
        }

        @TypeConverter
        public static String toString(StatusEnum value) {
            return value == null ? null : value.toString();
        }
    }

    @ColumnInfo()
    public String name;

    @ColumnInfo()
    public String description;

    @ColumnInfo(name = "origin_price")
    public long originPrice;

    @ColumnInfo()
    public long price;

    @ColumnInfo()
    @TypeConverters(StatusConverter.class)
    public StatusEnum status;

    @ColumnInfo()
    public int thumbnail;

    @ColumnInfo(name = "category_id")
    public long categoryId;
}
