package com.tiamaes.bike.common.utils;

/**
 * WGS-84：是国际标准,GPS坐标(Google Earth使用、或者GPS模块) 
 * GCJ-02：中国坐标偏移标准,Google Map、高德、腾讯使用
 * BD-09：百度坐标偏移标准，Baidu Map使用
 * 
 * {@link http://www.oschina.net/code/snippet_260395_39205}
 * 
 * @author Chen
 * @since 1.0.0
 * @date 2016-11-28
 */
public class GPSUtils {
	private static final double PI = 3.14159265358979324;
	private static final double X_PI = 3.14159265358979324 * 3000.0 / 180.0;
	private static final double A = 6378245.0;// 卫星椭球坐标投影到平面地图坐标系的投影因子
	private static final double EE = 0.00669342162296594323;// 椭球的偏心率

	/**
	 * WGS-84 to GCJ-02
	 * 
	 * @param lat
	 * @param lng
	 * @return [lat, lng]
	 */
	public static double[] fromWGS84ToGCJ02(double lat, double lng) {
		if (outOfChina(lat, lng)) {
			return new double[] { lat, lng };
		}
		double[] d = delta(lat, lng);
		return new double[] { lat + d[0], lng + d[1] };
	}

	/**
	 * WGS-84 to BD-09
	 * @param lat
	 * @param lng
	 * @return [lat, lng]
	 */
	public static double[] fromWGS84ToBD09(double lat, double lng){
		double[] gcj02 = fromWGS84ToGCJ02(lat, lng);
		return fromGCJ02ToBD09(gcj02[0], gcj02[1]);
	}

	/**
	 * WGS-84 to Web mercator
	 * 
	 * @param lat
	 * @param lng
	 * @return [lat, lng]
	 */
	public static double[] fromWGS84ToMercator(double lat, double lng) {
		double x = lng * 20037508.34 / 180.;
		double y = Math.log(Math.tan((90 + lat) * PI / 360)) / (PI / 180);
		y = y * 20037508.34 / 180;
		return new double[] { y, x };
	}

	/**
	 * GCJ-02 to WGS-84
	 * 
	 * @param lat
	 * @param lng
	 * @return [lat, lng]
	 */
	public static double[] fromGCJ02ToWGS84(double lat, double lng) {
		if (outOfChina(lat, lng)) {
			return new double[] { lat, lng };
		}
		double[] d = delta(lat, lng);
		return new double[] { lat - d[0], lng - d[1] };
	}

	/**
	 * GCJ-02 to WGS-84 exactly
	 * 
	 * @param lat
	 * @param lng
	 * @return [lat, lng]
	 */
	public static double[] fromGCJ02ToWGS84ForExactly(double lat, double lng) {
		double initDelta = 0.01;
		double threshold = 0.000000001;
		double dLat = initDelta, dLon = initDelta;
		double mLat = lat - dLat, mLon = lng - dLon;
		double pLat = lat + dLat, pLon = lng + dLon;
		double wgsLat, wgsLon, i = 0;
		while (true) {
			wgsLat = (mLat + pLat) / 2;
			wgsLon = (mLon + pLon) / 2;
			double[] tmp = fromWGS84ToGCJ02(wgsLat, wgsLon);
			dLat = tmp[0] - lat;
			dLon = tmp[1] - lng;
			if ((Math.abs(dLat) < threshold) && (Math.abs(dLon) < threshold)) {
				break;
			}
			if (dLat > 0) {
				pLat = wgsLat;
			} else {
				mLat = wgsLat;
			}
			if (dLon > 0) {
				pLon = wgsLon;
			} else {
				mLon = wgsLon;
			}

			if (++i > 10000) {
				break;
			}
		}
		return new double[] { wgsLat, wgsLon };
	}

	/**
	 * GCJ-02 to BD-09
	 * 
	 * @param lat
	 * @param lng
	 * @return [lat, lng]
	 */
	public static double[] fromGCJ02ToBD09(double lat, double lng) {
		double x = lng, y = lat;
		double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * X_PI);
		double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * X_PI);
		double bdLon = z * Math.cos(theta) + 0.0065;
		double bdLat = z * Math.sin(theta) + 0.006;
		return new double[] { bdLat, bdLon };
	}

	/**
	 * BD-09 to GCJ-02
	 * 
	 * @param lat
	 * @param lng
	 * @return [lat, lng]
	 */
	public static double[] fromBD09ToGCJ02(double lat, double lng) {
		double x = lng - 0.0065, y = lat - 0.006;
		double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * X_PI);
		double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * X_PI);
		double gcjLon = z * Math.cos(theta);
		double gcjLat = z * Math.sin(theta);
		return new double[] { gcjLat, gcjLon };
	}
	
	/**
	 * BD-09 to WGS-84
	 * @param lat
	 * @param lng
	 * @return [lat, lng]
	 */
	public static double[] fromBD09ToWGS84(double lat, double lng) {
		double[] gcj02 = fromBD09ToGCJ02(lat, lng);
		return fromGCJ02ToWGS84(gcj02[0], gcj02[1]);
	}
	/**
	 * Web mercator to WGS-84
	 * 
	 * @param lat
	 * @param lng
	 * @return [lat, lng]
	 */
	public static double[] fromMercatorToWGS84(double lat, double lng) {
		double x = lng / 20037508.34 * 180;
		double y = lng / 20037508.34 * 180;
		y = 180 / PI * (2 * Math.atan(Math.exp(y * PI / 180)) - PI / 2);
		return new double[] { y, x };
	}
	/**
	 * 计算两个坐标点的距离
	 * @param lat1
	 * @param lng1
	 * @param lat2
	 * @param lng2
	 * @return
	 */
	public static double distance(double lat1, double lng1, double lat2, double lng2) {
		final double earthRadius = 6371000;
		double x = Math.cos(lat1 * PI / 180) * Math.cos(lat2 * PI / 180) * Math.cos((lng1 - lng2) * PI / 180);
		double y = Math.sin(lat1 * PI / 180) * Math.sin(lat2 * PI / 180);
		double s = x + y;
		if (s > 1) { s = 1; }
		if (s < -1) { s = -1; }
		double alpha = Math.acos(s);
		return alpha * earthRadius;
	}

	private static boolean outOfChina(double lat, double lng) {
		if (lng < 72.004 || lng > 137.8347) {
			return true;
		}
		if (lat < 0.8293 || lat > 55.8271) {
			return true;
		}
		return false;
	}
	
	private static double[] delta(double lat, double lon) {
		double y = transformLat(lon - 105.0, lat - 35.0);
		double x = transformLng(lon - 105.0, lat - 35.0);
		double radLat = lat / 180.0 * PI;
		double magic = Math.sin(radLat);
		magic = 1 - EE * magic * magic;
		double sqrtMagic = Math.sqrt(magic);
		y = (y * 180.0) / ((A * (1 - EE)) / (magic * sqrtMagic) * PI);
		x = (x * 180.0) / (A / sqrtMagic * Math.cos(radLat) * PI);
		return new double[] { y, x };
	}

	private static double transformLat(double x, double y) {
		double ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y + 0.2 * Math.sqrt(Math.abs(x));
		ret += (20.0 * Math.sin(6.0 * x * PI) + 20.0 * Math.sin(2.0 * x * PI)) * 2.0 / 3.0;
		ret += (20.0 * Math.sin(y * PI) + 40.0 * Math.sin(y / 3.0 * PI)) * 2.0 / 3.0;
		ret += (160.0 * Math.sin(y / 12.0 * PI) + 320 * Math.sin(y * PI / 30.0)) * 2.0 / 3.0;
		return ret;
	}

	private static double transformLng(double x, double y) {
		double ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1 * Math.sqrt(Math.abs(x));
		ret += (20.0 * Math.sin(6.0 * x * PI) + 20.0 * Math.sin(2.0 * x * PI)) * 2.0 / 3.0;
		ret += (20.0 * Math.sin(x * PI) + 40.0 * Math.sin(x / 3.0 * PI)) * 2.0 / 3.0;
		ret += (150.0 * Math.sin(x / 12.0 * PI) + 300.0 * Math.sin(x / 30.0 * PI)) * 2.0 / 3.0;
		return ret;
	}
}
