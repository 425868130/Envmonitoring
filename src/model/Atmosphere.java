package model;

/**
 * Created by XJW on 2017/5/20.
 * 空气类
 */
public class Atmosphere {
    /*污染物*/
    private String contaminants;
    private int pm;
    /*湿度*/
    private int humidity;
    /*风向*/
    private String windDirection;
    /*风力*/
    private int windPower;


    public Atmosphere(String contaminants, int pm, int humidity, String windDirection, int windPower) {
        this.contaminants = contaminants;
        this.pm = pm;
        this.humidity = humidity;
        this.windDirection = windDirection;
        this.windPower = windPower;
    }

    public Atmosphere() {
    }

    public String getContaminants() {
        return contaminants;
    }

    public void setContaminants(String contaminants) {
        this.contaminants = contaminants;
    }

    public int getPm() {
        return pm;
    }

    public void setPm(int pm) {
        this.pm = pm;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public int getWindPower() {
        return windPower;
    }

    public void setWindPower(int windPower) {
        this.windPower = windPower;
    }
}
