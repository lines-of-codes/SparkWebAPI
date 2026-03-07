package xyz.dailitation.linesofcodes.sparkwebapi;

import me.lucko.spark.api.statistic.misc.DoubleAverageInfo;

public class MSPTInfo {
    public static class MSPTStat {
        public double min;
        public double max;
        public double mean;
        public double median;

        public MSPTStat(DoubleAverageInfo info) {
            min = info.min();
            max = info.max();
            mean = info.mean();
            median = info.median();
        }
    }

    MSPTStat tenSeconds;
    MSPTStat oneMinute;

    public MSPTInfo(MSPTStat tenS, MSPTStat oneM) {
        tenSeconds = tenS;
        oneMinute = oneM;
    }
}
