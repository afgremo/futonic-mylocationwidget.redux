package com.futonredemption.mylocation;

import android.os.Parcel;
import android.os.Parcelable;

public class StaticMap implements Parcelable {

	private String url = null;
	private String filePath = null;
	
	public StaticMap() {
		
	}
	
	public StaticMap(StaticMap map) {
		this.url = map.url;
		this.filePath = map.filePath;
	}

	public StaticMap(Parcel in) {
		readFromParcel(in);
	}
	
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getFilePath() { 
		return filePath;
	}
	
	public String getUrl() {
		return url;
	}
	
	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(url);
		dest.writeString(filePath);
    }

    public void readFromParcel(Parcel in) {
    	url = in.readString();
    	filePath = in.readString();
    }

    public static final Parcelable.Creator<StaticMap> CREATOR = new Parcelable.Creator<StaticMap>() {
        public StaticMap createFromParcel(Parcel in) {
            return new StaticMap(in);
        }

        public StaticMap[] newArray(int size) {
            return new StaticMap[size];
        }
    };
}
