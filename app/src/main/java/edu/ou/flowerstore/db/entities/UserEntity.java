package edu.ou.flowerstore.db.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

@Entity(tableName = "users", indices = {
        @Index(value = {"email"}, unique = true),
        @Index(value = {"phone"}, unique = true)
})
public class UserEntity extends BaseEntity {
    // Role enum
    public static enum UserRole {
        ADMIN,
        CUSTOMER
    }

    public static class RoleConverter {
        @TypeConverter
        public static UserRole fromString(String value) {
            return value == null ? null : UserRole.valueOf(value);
        }

        @TypeConverter
        public static String toString(UserRole value) {
            return value == null ? null : value.toString();
        }
    }

    // Status enum
    public static enum UserStatus {
        ACTIVE,
        INACTIVE,
        BANNED
    }

    public static class StatusConverter {
        @TypeConverter
        public static UserStatus fromString(String value) {
            return value == null ? null : UserStatus.valueOf(value);
        }

        @TypeConverter
        public static String toString(UserRole value) {
            return value == null ? null : value.toString();
        }
    }

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "email")
    public String email;

    @ColumnInfo(name = "password")
    public String password;

    @ColumnInfo(name = "salt")
    public String salt;

    @ColumnInfo(name = "phone")
    public String phone;

    @ColumnInfo(name = "role")
    @TypeConverters(RoleConverter.class)
    public UserRole role = UserRole.CUSTOMER;

    @ColumnInfo(name = "status")
    @TypeConverters(StatusConverter.class)
    public UserStatus status = UserStatus.ACTIVE;
}
