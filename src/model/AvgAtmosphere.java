package model;

/**
 * Created by XJW on 2017/5/23.
 */
public class AvgAtmosphere {
    /*污染物*/
    private String contaminants;
    private float pm;
    /*湿度*/
    private float humidity;
    /*风向*/
    private String windDirection;
    /*风力*/
    private int windPower;

    public String getContaminants() {
        return contaminants;
    }

    public void setContaminants(String contaminants) {
        this.contaminants = contaminants;
    }

    public float getPm() {
        return pm;
    }

    public void setPm(float pm) {
        this.pm = pm;
    }

    public float getHumidity() {
        return humidity;
    }

    public void setHumidity(float humidity) {
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
