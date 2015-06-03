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
	//https://stackoverflow.com/questions/837872/calculate-distance-in-meters-when-you-know-longitude-and-latitude-in-java
	if(!isCartesian) {
		return (float) distVincenty(lat1, lng1, lat2, lng2);
//		double earthRadius = 3963.1676; //miles
//		double dLat = Math.toRadians(lat2 - lat1);
//		double dLng = Math.toRadians(lng2 - lng1);
//		double a =
//				Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
//						Math.sin(dLng / 2) * Math.sin(dLng / 2);
//		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
//		float dist = (float) (earthRadius * c);
//
//		return dist;
	}
	return (float) -1;
}


//private float geographicDistanceCalculateAccurate(double lat1, double lon1, double lat2, double lon 2){
	/**
	 * Calculates geodetic distance between two points specified by latitude/longitude using Vincenty inverse formula
	 * for ellipsoids
	 *
	 * @param lat1
	 *            first point latitude in decimal degrees
	 * @param lon1
	 *            first point longitude in decimal degrees
	 * @param lat2
	 *            second point latitude in decimal degrees
	 * @param lon2
	 *            second point longitude in decimal degrees
	 * @returns distance in meters between points with 5.10<sup>-4</sup> precision
	 * @see <a href="http://www.movable-type.co.uk/scripts/latlong-vincenty.html">Originally posted here</a>
	 */
//	private double distVincenty(double lat1, double lon1, double lat2, double lon2) {
	private double distVincenty(double lat1, double lon1, double lat2, double lon2) {
		//http://www.movable-type.co.uk/scripts/latlong-vincenty.html
		//http://openmap.bbn.com/doc/api/com/bbn/openmap/proj/coords/LatLonPoint.html
		double a = 6378137, b = 6356752.314245, f = 1 / 298.257223563; // WGS-84 ellipsoid params
		double L = Math.toRadians(lon2 - lon1);
		double U1 = Math.atan((1 - f) * Math.tan(Math.toRadians(lat1)));
		double U2 = Math.atan((1 - f) * Math.tan(Math.toRadians(lat2)));
		double sinU1 = Math.sin(U1), cosU1 = Math.cos(U1);
		double sinU2 = Math.sin(U2), cosU2 = Math.cos(U2);

		double sinLambda, cosLambda, sinSigma, cosSigma, sigma, sinAlpha, cosSqAlpha, cos2SigmaM;
		double lambda = L, lambdaP, iterLimit = 100;
		do {
			sinLambda = Math.sin(lambda);
			cosLambda = Math.cos(lambda);
			sinSigma = Math.sqrt((cosU2 * sinLambda) * (cosU2 * sinLambda)
					+ (cosU1 * sinU2 - sinU1 * cosU2 * cosLambda) * (cosU1 * sinU2 - sinU1 * cosU2 * cosLambda));
			if (sinSigma == 0)
				return 0; // co-incident points
			cosSigma = sinU1 * sinU2 + cosU1 * cosU2 * cosLambda;
			sigma = Math.atan2(sinSigma, cosSigma);
			sinAlpha = cosU1 * cosU2 * sinLambda / sinSigma;
			cosSqAlpha = 1 - sinAlpha * sinAlpha;
			cos2SigmaM = cosSigma - 2 * sinU1 * sinU2 / cosSqAlpha;
			if (Double.isNaN(cos2SigmaM))
				cos2SigmaM = 0; // equatorial line: cosSqAlpha=0 (§6)
			double C = f / 16 * cosSqAlpha * (4 + f * (4 - 3 * cosSqAlpha));
			lambdaP = lambda;
			lambda = L + (1 - C) * f * sinAlpha
					* (sigma + C * sinSigma * (cos2SigmaM + C * cosSigma * (-1 + 2 * cos2SigmaM * cos2SigmaM)));
		} while (Math.abs(lambda - lambdaP) > 1e-12 && --iterLimit > 0);

		if (iterLimit == 0)
			return Double.NaN; // formula failed to converge

		double uSq = cosSqAlpha * (a * a - b * b) / (b * b);
		double A = 1 + uSq / 16384 * (4096 + uSq * (-768 + uSq * (320 - 175 * uSq)));
		double B = uSq / 1024 * (256 + uSq * (-128 + uSq * (74 - 47 * uSq)));
		double deltaSigma = B
				* sinSigma
				* (cos2SigmaM + B
				/ 4
				* (cosSigma * (-1 + 2 * cos2SigmaM * cos2SigmaM) - B / 6 * cos2SigmaM
				* (-3 + 4 * sinSigma * sinSigma) * (-3 + 4 * cos2SigmaM * cos2SigmaM)));
		double dist = b * A * (sigma - deltaSigma);

		return (dist * 0.000621371);
	}
//}

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
