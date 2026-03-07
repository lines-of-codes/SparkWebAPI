package xyz.dailitation.linesofcodes.sparkwebapi;

import com.fasterxml.jackson.databind.module.SimpleModule;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import io.javalin.json.JavalinJackson;
import me.lucko.spark.api.Spark;
import me.lucko.spark.api.SparkProvider;
import me.lucko.spark.api.gc.GarbageCollector;
import me.lucko.spark.api.statistic.StatisticWindow;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import static io.javalin.apibuilder.ApiBuilder.get;

public final class SparkWebAPI extends JavaPlugin {
    public Logger logger = getLogger();
    Javalin app;
    Spark spark;
    FileConfiguration pluginConfig;
    Map<String, Object> headers;

    @Override
    public void onEnable() {
        // Plugin startup logic
        logger.info("Hello, world!");

        saveDefaultConfig();

        pluginConfig = getConfig();
        headers = Objects.requireNonNull(pluginConfig.getConfigurationSection("headers")).getValues(false);
        Map<String, Object> routes = Objects.requireNonNull(pluginConfig.getConfigurationSection("routes")).getValues(false);

        if ((boolean)headers.get("enabled")) {
            headers.remove("enabled");
        } else {
            headers = new HashMap<>();
        }

        spark = SparkProvider.get();

        app = Javalin.create(config -> {
            config.jsonMapper(
                new JavalinJackson().updateMapper(mapper -> {
                    SimpleModule module = new SimpleModule("Serializers");
                    module.addSerializer(MSPTInfo.class, new MSPTInfoSerializer());
                    module.addSerializer(GarbageCollector.class, new GCSerializer());
                    mapper.registerModule(module);
                })
            );
            config.routes.apiBuilder(() -> {
                if ((boolean)routes.get("tps")) {
                    get("/api/tps", this::getTps);
                }
                if ((boolean)routes.get("mspt")) {
                    get("/api/mspt", this::getMspt);
                }
                if ((boolean)routes.get("sys_cpu")) {
                    get("/api/cpu/sys", this::getSysCpuUsage);
                }
                if ((boolean)routes.get("proc_cpu")) {
                    get("/api/cpu/proc", this::getProcCpuUsage);
                }
                if ((boolean)routes.get("gc")) {
                    get("/api/gc", this::getGCData);
                }
            });
        }).start(pluginConfig.getInt("port"));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        logger.info("Shutting down Spark Web API...");
        if (app != null) {
            app.stop();
        }
    }

    void addHeaders(Context ctx) {
        for (Map.Entry<String, Object> header : headers.entrySet()) {
            Object val = header.getValue();
            if (val == null) {
                ctx.header(header.getKey());
                continue;
            }
            ctx.header(header.getKey(), (String)val);
        }
    }

    void getTps(Context ctx) {
        var tps = spark.tps();

        addHeaders(ctx);

        if (tps == null) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR);
            return;
        }

        ctx.json(new TPSInfo(
                tps.poll(StatisticWindow.TicksPerSecond.SECONDS_10),
                tps.poll(StatisticWindow.TicksPerSecond.MINUTES_1),
                tps.poll(StatisticWindow.TicksPerSecond.MINUTES_5),
                tps.poll(StatisticWindow.TicksPerSecond.MINUTES_15)
            )
        );
    }

    void getMspt(Context ctx) {
        var mspt = spark.mspt();

        addHeaders(ctx);

        if (mspt == null) {
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR);
            return;
        }

        ctx.json(
                new MSPTInfo(
                        new MSPTInfo.MSPTStat(mspt.poll(StatisticWindow.MillisPerTick.SECONDS_10)),
                        new MSPTInfo.MSPTStat(mspt.poll(StatisticWindow.MillisPerTick.MINUTES_1))
                )
        );
    }

    void getSysCpuUsage(Context ctx) {
        ctx.json(new CPUUsageInfo(spark.cpuSystem()));
    }

    void getProcCpuUsage(Context ctx) {
        ctx.json(new CPUUsageInfo(spark.cpuProcess()));
    }

    void getGCData(Context ctx) {
        ctx.json(spark.gc());
    }
}
