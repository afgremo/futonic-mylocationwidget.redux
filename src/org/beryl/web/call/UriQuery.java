package org.beryl.web.call;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

public class UriQuery implements Parcelable {

	private String url = "";
	private Bundle parameters = null;
	
	public UriQuery() {
		
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public void setParameters(Bundle params) {
		this.parameters = params;
	}
	
	public String toUrl() {
		return "";
	}
	
	@Override
	public String toString() {
		return toUrl();
	}
	
	public UriQuery(Parcel in) {
		readFromParcel(in);
	}
	
	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel dest, int flags) {
    	dest.writeString(url);
    	dest.writeParcelable(parameters, flags);
    }

    public void readFromParcel(Parcel in) {
    	url = in.readString();
    	parameters = in.readParcelable(null);
    }

    public static final Parcelable.Creator<UriQuery> CREATOR = new Parcelable.Creator<UriQuery>() {
        public UriQuery createFromParcel(Parcel in) {
            return new UriQuery(in);
        }

        public UriQuery[] newArray(int size) {
            return new UriQuery[size];
        }
    };
}
