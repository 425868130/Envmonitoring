package model;

/**
 * Created by XJW on 2017/5/20.
 * 气候
 */
public class Climate {
    /*温度*/
    private float temperature;
    /*天气*/
    private String weather;
    /*降水量*/
    private int precipitation;
    /*紫外线*/
    private int ultravioletRays;

    public Climate(float temperature, String weather, int precipitation, int ultravioletRays) {
        this.temperature = temperature;
        this.weather = weather;
        this.precipitation = precipitation;
        this.ultravioletRays = ultravioletRays;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public int getPrecipitation() {
        return precipitation;
    }

    public void setPrecipitation(int precipitation) {
        this.precipitation = precipitation;
    }

    public int getUltravioletRays() {
        return ultravioletRays;
    }

    public void setUltravioletRays(int ultravioletRays) {
        this.ultravioletRays = ultravioletRays;
    }
}
