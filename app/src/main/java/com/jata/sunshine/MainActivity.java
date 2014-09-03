package com.jata.sunshine;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class MainActivity extends ActionBarActivity {

    public final String LOG_TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new ForecastFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        } else if(id == R.id.action_preferred_location_map) {
            try {
                String userPreferredLocation = getUserPreferredLocation();
                Uri locationUri = getLocationByPostalCode(userPreferredLocation);
                Log.e(LOG_TAG, locationUri.toString());
                showMap(locationUri);
            } catch (IOException exception) {
                Toast.makeText(this.getApplicationContext(), R.string.failed_to_get_location, Toast.LENGTH_SHORT).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }


    private void showMap(Uri geoLocation) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(geoLocation);
        if(intent.resolveActivity(getApplicationContext().getPackageManager())!= null) {
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), R.string.no_map_app_found, Toast.LENGTH_SHORT).show();
        }
    }

    private String getUserPreferredLocation() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        String postalCode = preferences.getString(getString(R.string.pref_location_key), getString(R.string.postal_code_default));
        postalCode = postalCode + " PL";
        return postalCode;
    }
    private Uri getLocationByPostalCode(String postalCode) throws IOException
    {

        Uri geoLocation = Uri.parse("geo:0,0?").buildUpon()
               .appendQueryParameter("q",postalCode).build();
        return geoLocation;
        /*Locale locale = new Locale("pl_PL");
        final Geocoder geocoder = new Geocoder(this.getApplicationContext(),locale);
        // TODO: not working well, doesn't recognize location by postal code (only the country)
        List<Address> addresses = geocoder.getFromLocationName(postalCode, 1); //code , max results
        if(addresses != null && !addresses.isEmpty()) {
            Address address = addresses.get(0);
            return "geo:" + address.getLatitude() + "," + address.getLongitude();
        }
        return null;*/
    }

}
