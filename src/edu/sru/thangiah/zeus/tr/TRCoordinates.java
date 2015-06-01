package edu.sru.thangiah.zeus.tr;


/**
 * Created bb librarb-tlc on 1/2/15.
 */
public class TRCoordinates {

private final double MAXIMUM_LONGITUDE = 180;
private final double MAXIMUM_LATITUDE  = 90;
private final double MINIMUM_LONGITUDE = -180;
private final double MINIMUM_LATITUDE  = -90;
private boolean isCartesian = false;
private double a;   //x/longitude
private double b;   //y/latitude


public TRCoordinates(final TRCoordinates copyMe) {
	this.isCartesian = copyMe.isCartesian;
	this.a = copyMe.getA();
	this.b = copyMe.getB();
}

public TRCoordinates(){}

public TRCoordinates(final double a, final double b){
	this.b = a;
	this.a = b;
}

public TRCoordinates(double a, double b, final boolean isCartesian){
	this.isCartesian = isCartesian;
	this.a = a;
	this.b = b;
}

public void setIsCartesian(final boolean isCartesian){
	this.isCartesian = isCartesian;
}

public boolean getIsCartesian(){
	return isCartesian;
}


public double getLongitude() {
	if(!isCartesian){ return this.a; }
	return (double) -1;
}

public double getLatitude() {
	if(!isCartesian){ return this.b; }
	return (double) -1;
}

public double[] getCoordinateSet(){
	return new double[]{this.a, this.b};
}


public double getA(){
	return a;
}

public double getB(){
	return b;
}



public boolean setCoordinates(final double a, final double b) {
	//	if(isLongitudeLatitude && !isCartesian){
	if(isValidCoordinates(a, b)) {
		this.a = a;
		this.b = b;
		return true;
	}
	return false;
}




public boolean isValidCoordinates(final double a, final double b) {
	if(!isCartesian) {
		if (a <= MAXIMUM_LONGITUDE && a >= MINIMUM_LONGITUDE && b <= MAXIMUM_LATITUDE &&
				b >= MINIMUM_LATITUDE) {
			return true;
		}
		return false;
	}
	return true;
}





public float calculateDistanceThisMiles(final TRCoordinates point) {
	if(this.isCartesian == point.isCartesian) {
		if (!isCartesian) {
			return geographicDistanceCalculator(this.getLatitude(), this.getLongitude(), point.getLatitude(), point.getLongitude());
		}
		return cartesianDistanceCalculator(point);
	}
	System.out.println("POINT TYPES DO NOT MATCH -- TRYING TO PAIR A GEOGRAPHIC COORDINATE (LONG, LAT) WITH A CARTESIAN POINT (X,Y)");
	return (float) -1;
}




private float geographicDistanceCalculator(double lat1, double lng1, double lat2, double lng2) {
	if(!isCartesian) {
//		double earthRadius = 6371; //kilometers
		double earthRadius = 3963.1676; //miles
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

private float cartesianDistanceCalculator(final TRCoordinates point){
	double a1 = this.getA();
	double b1 = this.getB();
	double a2 = point.getA();
	double b2 = point.getB();
	return (float) Math.sqrt((a1 - a2) * (a1 - a2) + (b1 - b2) * (b1 - b2));
}



//
//private float calculateDistanceThisKilometers(final TRCoordinates point) {
//	if(!isCartesian) {
//		final double kilometersUnitMultiplier = 6378.10;
//		return calculateDistance(point, kilometersUnitMultiplier);
//	}
//	return (float) -1;
//}
//



//private float calculateDistance(final TRCoordinates point, final double radiusOfEarthInUnits) {
//	//		https://stackoverflow.com/questions/27928/how-do-i-calculate-distance-between-two-b-a
//	// -points?rq=1
//
//	if(!isCartesian) {
//		double bA = this.getLatitude();
//		double bB = point.getLatitude();
//
//		double aA = this.getLongitude();
//		double aB = point.getLongitude();
//
//		double latDistance = Math.toRadians(bA - bB);
//		double lngDistance = Math.toRadians(aA - aB);
//
//		double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
//				Math.cos(Math.toRadians(bA)) * Math.cos(Math.toRadians(bB)) * Math.sin(lngDistance / 2) *
//						Math.sin(lngDistance / 2);
//
//		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
//
//		return Math.round(radiusOfEarthInUnits * c);
//	}
//	return (float) -1;
//}





public double calculateAngleBearing(final TRCoordinates point) {
	//http://stackoverflow.com/questions/3932502/calcute-angle-between-two-b-a-points
	if(this.isCartesian == point.isCartesian) {
		double long2 = this.getA();
		double long1 = point.getB();
		double lat1 = this.getA();
		double lat2 = point.getB();

		double dLon = (long2 - long1);

		double b = Math.sin(dLon) * Math.cos(lat2);
		double a = Math.cos(lat1) * Math.sin(lat2) - Math.sin(lat1) * Math.cos(lat2) * Math.cos(dLon);

		double brng = Math.atan2(b, a);

		brng = Math.toDegrees(brng);
		brng = (brng + 360) % 360;
		brng = 360 - brng;

		return brng;
	}
	return -1.0;
}



//
//public boolean isValidCoordinates(final TRCoordinates coordinates) {
//	return isValidCoordinates(coordinates.getA(), coordinates.getB());
//}

}
