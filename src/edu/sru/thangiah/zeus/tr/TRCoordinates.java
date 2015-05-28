package edu.sru.thangiah.zeus.tr;


/**
 * Created by library-tlc on 1/2/15.
 */
public class TRCoordinates {

private final double MAXIMUM_LONGITUDE = 180;
private final double MAXIMUM_LATITUDE  = 90;
private final double MINIMUM_LONGITUDE = -180;
private final double MINIMUM_LATITUDE  = -90;
private double longitude;
private double latitude;
private boolean isCartesian = false;
private double x;
private double y;


public TRCoordinates(final TRCoordinates copyMe) {
	this.isCartesian = copyMe.isCartesian;
//	this.a = copyMe.a;
//	this.b = copyMe.b;
	if(this.isCartesian){
		this.x = copyMe.getA();
		this.y = copyMe.getB();
	}
	else{
		this.longitude = copyMe.getA();
		this.latitude = copyMe.getB();
	}
//	this.longitude = copyMe.getA();
//	this.latitude = copyMe.getB();

}

public TRCoordinates(double lat, double lon){

	this.latitude = lat;
	this.longitude = lon;
}

	public TRCoordinates(double lat, double lon, final boolean isCartesian){
		this.isCartesian = isCartesian;
		if(isCartesian){
			this.x = lon;
			this.y = lat;
		}
		else{
			this.latitude = lat;
			this.longitude = lon;
		}


	}

public void setIsCartesian(final boolean isCartesian){
	this.isCartesian = isCartesian;
//	if(long)
}

public boolean getIsCartesian(){
	return isCartesian;
}
//s


public double getLongitude() {
	if(!isCartesian){ return this.longitude; }
	return (double) -1;
}




public double getLatitude() {
	if(!isCartesian){ return this.latitude; }
	return (double) -1;
}

public double[] getCoordinateSet(){
	if(!isCartesian){ return new double[]{this.longitude, this.latitude}; }
	else{ return new double[]{this.x, this.y};}
}



public TRCoordinates() {
}

public double getA(){
	if(isCartesian){ return x; }
	return longitude;
}

public double getB(){
	if(isCartesian){ return y; }
	return latitude;
}



public boolean setCoordinates(final double a, final double b) {
	//	if(isLongitudeLatitude && !isCartesian){
	if(isValidLongitudeLatitude(a, b) && !isCartesian) {
		this.longitude = a;
		this.latitude = b;
		return true;
	}
	else if (isCartesian){
		this.x = a;
		this.y = b;
		return true;
	}
	return false;
}




public boolean isValidLongitudeLatitude(final double longitude, final double latitude) {
	if(longitude <= MAXIMUM_LONGITUDE && longitude >= MINIMUM_LONGITUDE && latitude <= MAXIMUM_LATITUDE &&
	   latitude >= MINIMUM_LATITUDE) {
		return true;
	}
	return false;
}





public float calculateDistanceThisMiles(final TRCoordinates point) {
	if(this.isCartesian == point.isCartesian) {
		if (!isCartesian) {
			final double milesUnitMultiplier = 3963.1676;
			return distFrom(this.getLatitude(), this.getLongitude(), point.getLatitude(), point.getLongitude());
		} else {
			double x1 = this.x;
			double y1 = this.y;
			double x2 = point.x;
			double y2 = point.y;
			return (float) Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
		}
	}
	System.out.println("POINT TYPES DO NOT MATCH -- TRYING TO PAIR A GEOGRAPHIC COORDINATE (LONG, LAT) WITH A CARTESIAN POINT (X,Y)");
	return (float) -1;
}




private float distFrom(double lat1, double lng1, double lat2, double lng2) {
	if(!isCartesian) {
		double earthRadius = 6371; //kilometers
		double dLat = Math.toRadians(lat2 - lat1);
		double dLng = Math.toRadians(lng2 - lng1);
		double a =
				Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
						Math.sin(dLng / 2) * Math.sin(dLng / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		float dist = (float) (earthRadius * c);

		return dist;
	}
	return (float) -1;
}




public float calculateDistanceThisKilometers(final TRCoordinates point) {
	if(!isCartesian) {
		final double kilometersUnitMultiplier = 6378.10;
		return calculateDistance(point, kilometersUnitMultiplier);
	}
	return (float) -1;
}




private float calculateDistance(final TRCoordinates point, final double radiusOfEarthInUnits) {
	//		https://stackoverflow.com/questions/27928/how-do-i-calculate-distance-between-two-latitude-longitude
	// -points?rq=1

	if(!isCartesian) {
		double latitudeA = this.getLatitude();
		double latitudeB = point.getLatitude();

		double longitudeA = this.getLongitude();
		double longitudeB = point.getLongitude();

		double latDistance = Math.toRadians(latitudeA - latitudeB);
		double lngDistance = Math.toRadians(longitudeA - longitudeB);

		double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
				Math.cos(Math.toRadians(latitudeA)) * Math.cos(Math.toRadians(latitudeB)) * Math.sin(lngDistance / 2) *
						Math.sin(lngDistance / 2);

		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

		return Math.round(radiusOfEarthInUnits * c);
	}
	return (float) -1;
}





public double calculateAngleBearing(final TRCoordinates point) {
	//http://stackoverflow.com/questions/3932502/calcute-angle-between-two-latitude-longitude-points

	double long2 = this.getA();
	double long1 = point.getB();
	double lat1 = this.getA();
	double lat2 = point.getB();

	double dLon = (long2 - long1);

	double y = Math.sin(dLon) * Math.cos(lat2);
	double x = Math.cos(lat1) * Math.sin(lat2) - Math.sin(lat1) * Math.cos(lat2) * Math.cos(dLon);

	double brng = Math.atan2(y, x);

	brng = Math.toDegrees(brng);
	brng = (brng + 360) % 360;
	brng = 360 - brng;

	return brng;
}




public boolean isValidLongitudeLatitude(final TRCoordinates coordinates) {
	return isValidLongitudeLatitude(coordinates.getLongitude(), coordinates.getLatitude());
}

}
