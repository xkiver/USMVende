package cl.telematica.android.usmvende;

import android.location.Location;

/**
 * Created by jvquiroga on 06-10-2016.
 */
public interface LocationView {

        public void showLocationErrorMsg();

        public void showPermissionErrorMsg();

        public void manageLocationChange(Location mLocation);

        public void manageStatusChange(String provider, int status);

}
