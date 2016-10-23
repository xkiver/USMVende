package cl.telematica.android.usmvende;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

/**
 * Created by jvquiroga on 06-10-2016.
 */
public class LocationPresenterImpl implements LocationPresenter, LocationListener {

    private LocationManager mLocationManager;
    private Activity mContext;
    private LocationView mLocationView;

    private final String GPS = LocationManager.GPS_PROVIDER;
    private final String NET = LocationManager.NETWORK_PROVIDER;
    private static final long minTime = 10000;
    private static final float minDistance = 200;

    public LocationPresenterImpl(Activity mContext, LocationManager mLocationManager, LocationView mLocationView) {
        this.mLocationManager = mLocationManager;
        this.mContext = mContext;
        this.mLocationView = mLocationView;
    }

    @Override
    public void startUpdates() {
        if (!mLocationManager.isProviderEnabled(NET) && !mLocationManager.isProviderEnabled(GPS)) {
            mLocationView.showLocationErrorMsg();
        } else {
            if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                mLocationView.showPermissionErrorMsg();
                return;
            }
            if (mLocationManager.isProviderEnabled(NET)) {
                mLocationManager.requestLocationUpdates(NET, minTime, minDistance, this);
            }
            if(mLocationManager.isProviderEnabled(GPS)){
                mLocationManager.requestLocationUpdates(GPS, minTime, minDistance, this);
            }
        }
    }

    @Override
    public void stopUpdates() {
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            mLocationView.showPermissionErrorMsg();
            return;
        }
        mLocationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        mLocationView.manageLocationChange(location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        mLocationView.manageStatusChange(provider, status);
    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

}