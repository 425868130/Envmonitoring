package model;

/**
 * Created by XJW on 2017/5/23.
 */
public class AvgWaterQuality {
    /*溶解氧*/
    private float dissolvedOxygen;
    private float ph;
    /*浊度*/
    private float turbidity;
    /*导电性*/
    private float conductivity;

    public float getDissolvedOxygen() {
        return dissolvedOxygen;
    }

    public void setDissolvedOxygen(float dissolvedOxygen) {
        this.dissolvedOxygen = dissolvedOxygen;
    }

    public float getPh() {
        return ph;
    }

    public void setPh(float ph) {
        this.ph = ph;
    }

    public float getTurbidity() {
        return turbidity;
    }

    public void setTurbidity(float turbidity) {
        this.turbidity = turbidity;
    }

    public float getConductivity() {
        return conductivity;
    }

    public void setConductivity(float conductivity) {
        this.conductivity = conductivity;
    }
}
