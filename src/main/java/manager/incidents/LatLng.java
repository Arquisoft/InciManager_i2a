package manager.incidents;

public class LatLng{
	private double lat;
	private double lng;
	
	public LatLng(double lat, double lng) {
		super();
		this.lat = lat;
		this.lng = lng;
	}

	public double getLat() {
		return lat;
	}

	public double getLng() {
		return lng;
	}

	@Override
	public String toString() {
		return "GeoCords [lat=" + lat + ", lng=" + lng + "]";
	}
	
	
}
