package com.futonredemption.mylocation;

import android.os.Parcel;
import android.os.Parcelable;

public class ShortMapUrls implements Parcelable {

	private String basicShortUrl = null;
	private String addressShortUrl = null;
	
	public ShortMapUrls() {
	}
	
	public ShortMapUrls(ShortMapUrls links) {
		this.addressShortUrl = links.addressShortUrl;
		this.basicShortUrl = links.basicShortUrl;
	}

	public ShortMapUrls(Parcel in) {
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
	
	public boolean hasAddressShortUrl() {
		return addressShortUrl != null;
	}
	
	public String getAddressShortUrl() {
		return addressShortUrl;
	}

	public void setAddressShortUrl(String addressShortUrl) {
		this.addressShortUrl = addressShortUrl;
	}

	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(basicShortUrl);
		dest.writeString(addressShortUrl);
    }

    public void readFromParcel(Parcel in) {
    	basicShortUrl = in.readString();
    	addressShortUrl = in.readString();
    }

    public static final Parcelable.Creator<StaticMap> CREATOR = new Parcelable.Creator<StaticMap>() {
        public StaticMap createFromParcel(Parcel in) {
            return new StaticMap(in);
        }

        public StaticMap[] newArray(int size) {
            return new StaticMap[size];
        }
    };

    public boolean hasAllShortUrls() {
		return hasAddressShortUrl() && hasBasicShortUrl();
	}
    
	public boolean hasAnyShortUrl() {
		return hasAddressShortUrl() || hasBasicShortUrl();
	}

	public void fillFrom(ShortMapUrls other) {
		if(! hasBasicShortUrl()) {
			this.setBasicShortUrl(other.getBasicShortUrl());
		}
		
		if(! hasAddressShortUrl()) {
			this.setAddressShortUrl(other.getAddressShortUrl());
		}
	}
}