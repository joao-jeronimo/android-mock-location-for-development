package amotz.example.com.mocklocationfordeveloper;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.location.LocationManager;
import android.util.Log;

public class AdbBroadcastReceiver extends BroadcastReceiver
{
    String logTag="MockGpsAdbBroadcastReceiver";

    @Override
    public void onReceive(Context context, Intent intent)
    {
        mMockSetter = MockLocationSetter.getInstance(context);

        // Interpret intent.
        if (intent.getAction().equals("stop.mock")) {
            mMockSetter.stopSetting();
        }
        else {
            double lat, lon, alt;
            float accurate;
            lat = Double.parseDouble(intent.getStringExtra("lat") != null ? intent.getStringExtra("lat") : "0");
            lon = Double.parseDouble(intent.getStringExtra("lon") != null ? intent.getStringExtra("lon") : "0");
            alt = Double.parseDouble(intent.getStringExtra("alt") != null ? intent.getStringExtra("alt") : "0");
            accurate = Float.parseFloat(intent.getStringExtra("accurate") != null ? intent.getStringExtra("accurate") : "0");
            Log.i(logTag, String.format("setting mock to Latitude=%f, Longitude=%f Altitude=%f Accuracy=%f", lat, lon, alt, accurate));

            setMockLocTo(lat, lon, alt, accurate);
        }
    }




    public MockLocationSetter mMockSetter;

    private void setMockLocTo(double lat, double lon, double alt, float accuracy)
    {
        mMockSetter.startSetting();
        mMockSetter.setCornades(lat, lon, alt, accuracy);
    }
}
