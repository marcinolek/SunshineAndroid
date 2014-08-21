package com.jata.sunshine;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Marcin on 2014-08-21.
 */
public class WeatherDataParser {

    public static double getMaxTemperatureForDay(String weatherJsonString, int dayIndex) throws JSONException {
        JSONObject obj = new JSONObject(weatherJsonString);
        JSONArray dayList = obj.getJSONArray("list");
        JSONObject dayNotation = dayList.getJSONObject(dayIndex);
        JSONObject tempNotation = dayNotation.getJSONObject("temp");
        double maxTemp = tempNotation.getDouble("max");
        return maxTemp;
    }

}
