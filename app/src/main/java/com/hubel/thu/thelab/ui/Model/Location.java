package com.hubel.thu.thelab.ui.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by thu on 14.04.16.
 */
public class Location implements Parcelable {

    private String title;
    private Long longitude;
    private Long latitude;
    private String address;

    private String adressAdditional;
    private int addressNumber;
    private int addressCode;
    private String city;

    public Location(String title, Long longitude, Long latitude, String address, String adressAdditional, int addressNumber, int addressCode, String city) {
        this.title = title;
        this.longitude = longitude;
        this.latitude = latitude;
        this.address = address;
        this.adressAdditional = adressAdditional;
        this.addressNumber = addressNumber;
        this.addressCode = addressCode;
        this.city = city;
    }

    public String getAdressAdditional() {

        return adressAdditional;
    }

    public void setAdressAdditional(String adressAdditional) {
        this.adressAdditional = adressAdditional;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getLongitude() {
        return longitude;
    }

    public void setLongitude(Long longitude) {
        this.longitude = longitude;
    }

    public Long getLatitude() {
        return latitude;
    }

    public void setLatitude(Long latitude) {
        this.latitude = latitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAddressNumber() {
        return addressNumber;
    }

    public void setAddressNumber(int addressNumber) {
        this.addressNumber = addressNumber;
    }

    public int getAddressCode() {
        return addressCode;
    }

    public void setAddressCode(int addressCode) {
        this.addressCode = addressCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeValue(this.longitude);
        dest.writeValue(this.latitude);
        dest.writeString(this.address);
        dest.writeString(this.adressAdditional);
        dest.writeInt(this.addressNumber);
        dest.writeInt(this.addressCode);
        dest.writeString(this.city);
    }

    protected Location(Parcel in) {
        this.title = in.readString();
        this.longitude = (Long) in.readValue(Long.class.getClassLoader());
        this.latitude = (Long) in.readValue(Long.class.getClassLoader());
        this.address = in.readString();
        this.adressAdditional = in.readString();
        this.addressNumber = in.readInt();
        this.addressCode = in.readInt();
        this.city = in.readString();
    }

    public static final Creator<Location> CREATOR = new Creator<Location>() {
        @Override
        public Location createFromParcel(Parcel source) {
            return new Location(source);
        }

        @Override
        public Location[] newArray(int size) {
            return new Location[size];
        }
    };
}
