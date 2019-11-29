package amotz.example.com.mocklocationfordeveloper;

import android.app.Application;
import android.content.Context;
import android.location.LocationManager;

public class MockLocationSetter extends Thread
{
    private static MockLocationSetter _singleton;
    public static MockLocationSetter getInstance(Context context) {
        if (_singleton==null)
            _singleton = new MockLocationSetter(context);
        return _singleton;
    }

    private MockLocationProvider mockGPS;
    private MockLocationProvider mockWifi;

    private MockLocationSetter(Context context)
    {
        if (mockGPS == null)
            mockGPS = new MockLocationProvider(LocationManager.GPS_PROVIDER, context);
        if (mockWifi == null)
            mockWifi = new MockLocationProvider(LocationManager.NETWORK_PROVIDER, context);
        this.start();
    }

    double _lat = 0;
    double _lon = 0;
    double _alt = 0;
    float _accuracy = 0;

    boolean _doSet = false;

    public synchronized void startSetting() {
        _doSet=true;
    }

    public synchronized void stopSetting() {
        _doSet=false;
        /*
        if (mockGPS != null) {
            mockGPS.shutdown();
        }
        if (mockWifi != null) {
            mockWifi.shutdown();
        }*/
    }

    public synchronized void setCornades(double lat, double lon,double alt,float accuracy) {
        _lat = lat;
        _lon = lon;
        _alt = alt;
        _accuracy = accuracy;
    }

    @Override
    public void run()
    {/*
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                newLocation.setElapsedRealtimeNanos(SystemClock.elapsedRealtimeNanos());
            }
            newLocation.setTime(System.currentTimeMillis());
            lm.setTestProviderLocation(LocationManager.GPS_PROVIDER, newLocation);

            mHandler.postDelayed(mMockRunnable, 1000); // At each 1 second
            */

        for (;;) {
            synchronized(this) {
                if (_doSet) {
                    mockGPS.pushLocation(_lat, _lon, _alt, _accuracy);
                    mockWifi.pushLocation(_lat, _lon, _alt, _accuracy);
                }
            }

            try {
                sleep(100);
            } catch (Exception toign) {  }
        }
    }
}
