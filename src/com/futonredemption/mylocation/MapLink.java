package com.futonredemption.mylocation;

import android.os.Parcel;
import android.os.Parcelable;

public class MapLink implements Parcelable {

	private String basicLongUrl = null;
	private String basicShortUrl = null;
	
	public MapLink() {
		
	}
	
	public MapLink(MapLink links) {
		this.basicLongUrl = links.basicLongUrl;
		this.basicShortUrl = links.basicShortUrl;
	}

	public MapLink(Parcel in) {
		readFromParcel(in);
	}
	
	public boolean hasBasicShortUrl() {
		return basicShortUrl != null;
	}
	
	public String getBasicShortUrl() {
		return basicShortUrl;
	}

	public void setBasicShortUrl(String basicShortUrl) {
		this.basicShortUrl = basicShortUrl;
	}

	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(basicShortUrl);
    }

    public void readFromParcel(Parcel in) {
    	basicShortUrl = in.readString();
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