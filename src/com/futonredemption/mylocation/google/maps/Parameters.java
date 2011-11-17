package com.futonredemption.mylocation.google.maps;

import android.location.Location;

/** http://code.google.com/apis/maps/documentation/staticmaps/#URL_Parameters */
public class Parameters {
	
	/** Location Parameter: center (required if markers not present) defines the center of the map, equidistant from all edges of the map. This parameter takes a location as either a comma-separated {latitude,longitude} pair (e.g. "40.714728,-73.998672") or a string address (e.g. "city hall, new york, ny") identifying a unique location on the face of the earth. For more information, see Locations below. */
	public Center center = new Center();
	
	/** Location Parameter: zoom (required if markers not present) defines the zoom level of the map, which determines the magnification level of the map. This parameter takes a numerical value corresponding to the zoom level of the region desired. For more information, see zoom levels below. */
	public int zoom = 0;
	
	/** Map Parameter: size (required) defines the rectangular dimensions of the map image. This parameter takes a string of the form valuexvalue where horizontal pixels are denoted first while vertical pixels are denoted second. For example, 500x400 defines a map 500 pixels wide by 400 pixels high. If you create a static map that is 100 pixels wide or smaller, the "Powered by Google" logo is automatically reduced in size. */
	public Dimension size = new Dimension();
	
	/** scale (optional) affects the number of pixels that are returned. scale=2 returns twice as many pixels as scale=1 while retaining the same coverage area and level of detail (i.e. the contents of the map don't change). This is useful when developing for high-resolution displays, or when generating a map for printing. The default value is 1. Accepted values are 2 and 4 (4 is only available to Maps API Premier customers.) See Scale Values for more information. */
	public int scale = 1;
	
	/** Map Parameter: format (optional) defines the format of the resulting image. By default, the Static Maps API creates PNG images. There are several possible formats including GIF, JPEG and PNG types. Which format you use depends on how you intend to present the image. JPEG typically provides greater compression, while GIF and PNG provide greater detail. For more information, see Image Formats. */
	public String format = "png";
	
	/** Map Parameter: maptype (optional) defines the type of map to construct. There are several possible maptype values, including roadmap, satellite, hybrid, and terrain. For more information, see Static Maps API Maptypes below. */
	public String maptype = "hybrid";
	
	/** Map Parameter: language (optional) defines the language to use for display of labels on map tiles. Note that this parameter is only supported for some country tiles; if the specific language requested is not supported for the tile set, then the default language for that tileset will be used. */
	public String language = "en";
	
	/** Feature Parameter: markers (optional) define one or more markers to attach to the image at specified locations. This parameter takes a single marker definition with parameters separated by the pipe character (|). Multiple markers may be placed within the same markers parameter as long as they exhibit the same style; you may add additional markers of differing styles by adding additional markers parameters. Note that if you supply markers for a map, you do not need to specify the (normally required) center and zoom parameters. For more information, see Static Map Markers below. */
	public String markers = null;
	
	/** Feature Parameter: path (optional) defines a single path of two or more connected points to overlay on the image at specified locations. This parameter takes a string of point definitions separated by the pipe character (|). You may supply additional paths by adding additional path parameters. Note that if you supply a path for a map, you do not need to specify the (normally required) center and zoom parameters. For more information, see Static Map Paths below.
visible (optional) specifies one or more locations that should remain visible on the map, though no markers or other indicators will be displayed. Use this parameter to ensure that certain features or map locations are shown on the static map. */
	public String path = null;
	
	/** Feature Parameter: visible (optional) specifies one or more locations that should remain visible on the map, though no markers or other indicators will be displayed. Use this parameter to ensure that certain features or map locations are shown on the static map. */
	public boolean visible = true;
	
	/** Feature Parameter: style (optional) defines a custom style to alter the presentation of a specific feature (road, park, etc.) of the map. This parameter takes feature and element arguments identifying the features to select and a set of style operations to apply to that selection. You may supply multiple styles by adding additional style parameters. For more information, see Styled Maps below. */
	public String style = null;
	
	/** Reporting Parameter: sensor (required) specifies whether the application requesting the static map is using a sensor to determine the user's location. This parameter is required for all static map requests. For more information, see Denoting Sensor Usage below. */
	public boolean sensor = true;
	
	public static class Dimension {
		public int width = 512;
		public int height = 512;
		
		public Dimension() {
			
		}
		
		public Dimension(int width, int height) {
			this.width = width;
			this.height = height;
		}

		@Override
		public String toString() {
			// FIXME: the x might break this.
			return String.format("%dx%d", width, height);
		}
	}
	
	public static class Center {
		public double latitude = 0.0;
		public double longitude = 0.0;
		public String address = null;
		
		
		public Center() {
			
		}
		
		public Center(Location location) {
			this.latitude = location.getLatitude();
			this.longitude = location.getLongitude();
		}
		
		@Override
		public String toString() {
			if(address == null || address.length() == 0)
				return String.format("%.6f,%.6f", latitude, longitude);
			else
				return address;
		}
	}
}
