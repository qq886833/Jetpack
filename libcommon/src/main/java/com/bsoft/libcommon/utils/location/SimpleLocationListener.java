package com.bsoft.libcommon.utils.location;

import android.location.Location;
import android.location.LocationListener;

/**
 * Created by Administrator on 2018/4/1.
 */

public interface SimpleLocationListener extends LocationListener {

    void onLocationChanged(Location location);

}
