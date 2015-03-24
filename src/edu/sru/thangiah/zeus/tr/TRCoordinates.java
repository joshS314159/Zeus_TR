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



public TRCoordinates(final TRCoordinates copyMe) {
	this.longitude = copyMe.getLongitude();
	this.latitude = copyMe.getLatitude();
}

	public TRCoordinates(double lat, double lon){
		this.latitude = lat;
		this.longitude = lon;
	}



public double getLongitude() {
	return this.longitude;
}




public double getLatitude() {
	return this.latitude;
}




public TRCoordinates() {
}



public boolean setCoordinates(final double longitude, final double latitude) {
	//	if(isLongitudeLatitude && !isCartesian){
	if(isValidLongitudeLatitude(longitude, latitude)) {
		this.longitude = longitude;
		this.latitude = latitude;
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
//	else if(this.isCartesian && point.isCartesian) {
//put formula in here
//	}
//	return -1.0;
//}




public float calculateDistanceThisMiles(final TRCoordinates point) {
	final double milesUnitMultiplier = 3963.1676;
	return distFrom(this.getLatitude(), this.getLongitude(), point.getLatitude(), point.getLongitude());
	//	return calculateDistance(point, milesUnitMultiplier);
}




private float distFrom(double lat1, double lng1, double lat2, double lng2) {
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




public float calculateDistanceThisKilometers(final TRCoordinates point) {
	final double kilometersUnitMultiplier = 6378.10;
	return calculateDistance(point, kilometersUnitMultiplier);
}




private float calculateDistance(final TRCoordinates point, final double radiusOfEarthInUnits) {
	//		https://stackoverflow.com/questions/27928/how-do-i-calculate-distance-between-two-latitude-longitude
	// -points?rq=1
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

//	if(this.isLongitudeLatitude && point.isLongitudeLatitude) {
//	final double R = 6371; // In kilometers
//	double latitudeOne = this.latitude;
//	double longitudeOne = this.longitude;
//	double latitudeTwo = point.latitude;
//	double longitudeTwo = point.longitude;
//
//	double latitudeDistance = Math.toRadians(latitudeTwo - latitudeOne);
//	double longitudeDistance = Math.toRadians(longitudeTwo - longitudeOne);
//	latitudeOne = Math.toRadians(latitudeOne);
//	latitudeTwo = Math.toRadians(latitudeTwo);
//
//	double a = Math.sin(latitudeDistance / 2) * Math.sin(latitudeDistance / 2) + Math.sin(longitudeDistance / 2) *
// Math.sin(longitudeDistance / 2) * Math.cos(latitudeOne) * Math.cos(latitudeTwo);
//	double c = 2 * Math.asin(Math.sqrt(a));
//	return R * c;
//}




public double calculateAngleBearing(final TRCoordinates point) {
	//http://stackoverflow.com/questions/3932502/calcute-angle-between-two-latitude-longitude-points
	double long2 = this.getLongitude();
	double long1 = point.getLongitude();
	double lat1 = this.getLatitude();
	double lat2 = point.getLatitude();

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
	//	if(longitude <= MAXIMUM_LONGITUDE && longitude >= MINIMUM_LONGITUDE && latitude <= MAXIMUM_LATITUDE &&
	// latitude >= MINIMUM_LATITUDE){
	//		return true;
	//	}
	//	return false;
}

}
