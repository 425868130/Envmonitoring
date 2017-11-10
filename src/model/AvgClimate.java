package model;

import java.awt.*;
import java.util.List;

/**
 * Created by XJW on 2017/5/23.
 */
public class AvgClimate {
    /*温度*/
    private List<Point> temperature;
    /*天气*/
    private String weather;
    /*降水量*/
    private float precipitation;
    /*紫外线*/
    private float ultravioletRays;

    public List<Point> getTemperature() {
        return temperature;
    }

    public void setTemperature(List<Point> temperature) {
        this.temperature = temperature;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public float getPrecipitation() {
        return precipitation;
    }

    public void setPrecipitation(float precipitation) {
        this.precipitation = precipitation;
    }

    public float getUltravioletRays() {
        return ultravioletRays;
    }

    public void setUltravioletRays(float ultravioletRays) {
        this.ultravioletRays = ultravioletRays;
    }
}
