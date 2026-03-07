package xyz.dailitation.linesofcodes.sparkwebapi;

import me.lucko.spark.api.statistic.StatisticWindow;
import me.lucko.spark.api.statistic.types.DoubleStatistic;

public class CPUUsageInfo {
    public double tenSeconds;
    public double oneMinute;
    public double fifteenMinutes;

    public CPUUsageInfo(DoubleStatistic<StatisticWindow.CpuUsage> usage) {
        tenSeconds = usage.poll(StatisticWindow.CpuUsage.SECONDS_10);
        oneMinute = usage.poll(StatisticWindow.CpuUsage.MINUTES_1);
        fifteenMinutes = usage.poll(StatisticWindow.CpuUsage.MINUTES_15);
    }
}
