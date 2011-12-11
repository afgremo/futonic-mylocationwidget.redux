package com.futonredemption.mylocation;

import android.os.Parcel;
import android.os.Parcelable;

public class StaticMap implements Parcelable {

	private String smallMapUrl = null;
	private String smallMapFilePath = null;
	private String mediumMapUrl = null;
	private String mediumMapFilePath = null;
	private String largeMapUrl = null;
	private String largeMapFilePath = null;
	
	public StaticMap() {
		
	}
	
	public StaticMap(StaticMap map) {
		this.smallMapUrl = map.smallMapUrl;
		this.smallMapFilePath = map.smallMapFilePath;
		this.mediumMapUrl = map.mediumMapUrl;
		this.mediumMapFilePath = map.mediumMapFilePath;
		this.largeMapUrl = map.largeMapUrl;
		this.largeMapFilePath = map.largeMapFilePath;
	}

	public StaticMap(Parcel in) {
		readFromParcel(in);
	}
	
	public boolean hasSmallMap() {
		return smallMapFilePath != null;
	}
	
	public boolean hasMediumMap() {
		return mediumMapFilePath != null;
	}
	
	public boolean hasLargeMap() {
		return largeMapFilePath != null;
	}
	
	public String getSmallMapUrl() {
		return smallMapUrl;
	}

	public void setSmallMapUrl(String smallMapUrl) {
		this.smallMapUrl = smallMapUrl;
	}

	public String getSmallMapFilePath() {
		return smallMapFilePath;
	}

	public void setSmallMapFilePath(String smallMapFilePath) {
		this.smallMapFilePath = smallMapFilePath;
	}

	public String getMediumMapUrl() {
		return mediumMapUrl;
	}

	public void setMediumMapUrl(String mediumMapUrl) {
		this.mediumMapUrl = mediumMapUrl;
	}

	public String getMediumMapFilePath() {
		return mediumMapFilePath;
	}

	public void setMediumMapFilePath(String mediumMapFilePath) {
		this.mediumMapFilePath = mediumMapFilePath;
	}

	public String getLargeMapUrl() {
		return largeMapUrl;
	}

	public void setLargeMapUrl(String largeMapUrl) {
		this.largeMapUrl = largeMapUrl;
	}

	public String getLargeMapFilePath() {
		return largeMapFilePath;
	}

	public void setLargeMapFilePath(String largeMapFilePath) {
		this.largeMapFilePath = largeMapFilePath;
	}

	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(smallMapUrl);
		dest.writeString(smallMapFilePath);
		dest.writeString(mediumMapUrl);
		dest.writeString(mediumMapFilePath);
		dest.writeString(largeMapUrl);
		dest.writeString(largeMapFilePath);
    }

    public void readFromParcel(Parcel in) {
    	smallMapUrl = in.readString();
    	smallMapFilePath = in.readString();
    	mediumMapUrl = in.readString();
    	mediumMapFilePath = in.readString();
    	largeMapUrl = in.readString();
    	largeMapFilePath = in.readString();
    }

    public static final Parcelable.Creator<StaticMap> CREATOR = new Parcelable.Creator<StaticMap>() {
        public StaticMap createFromParcel(Parcel in) {
            return new StaticMap(in);
        }

        public StaticMap[] newArray(int size) {
            return new StaticMap[size];
        }
    };

	public boolean hasAllMaps() {
		return hasSmallMap() && hasMediumMap() && hasLargeMap();
	}

	public boolean hasAnyStaticMap() {
		return hasSmallMap() || hasMediumMap() || hasLargeMap();
	}
	
	public void fillFrom(final StaticMap other) {
		if(! hasSmallMap()) {
			this.setSmallMapUrl(other.getSmallMapUrl());
			this.setSmallMapFilePath(other.getSmallMapFilePath());
		}
		
		if(! hasMediumMap()) {
			this.setMediumMapUrl(other.getMediumMapUrl());
			this.setMediumMapFilePath(other.getMediumMapFilePath());
		}
		
		if(! hasLargeMap()) {
			this.setLargeMapUrl(other.getLargeMapUrl());
			this.setLargeMapFilePath(other.getLargeMapFilePath());
		}
	}
}
