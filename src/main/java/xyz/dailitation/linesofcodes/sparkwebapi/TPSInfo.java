package xyz.dailitation.linesofcodes.sparkwebapi;

public class TPSInfo {
    public double tenSeconds;
    public double oneMinute;
    public double fiveMinutes;
    public double fifteenMinutes;

    public TPSInfo(double tenS, double oneM, double fiveM, double fifteenM) {
        tenSeconds = tenS;
        oneMinute = oneM;
        fiveMinutes = fiveM;
        fifteenMinutes = fifteenM;
    }
}
