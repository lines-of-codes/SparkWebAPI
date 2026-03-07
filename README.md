# Spark Web API

A small plugin which will host a Web API for Spark.

This plugin is made for Paper. You are free to fork and 
port this to other mod loaders.

This plugin uses Javalin library for hosting the web API.

## Installation
First, Install Spark to your Paper server.
Even though Paper servers for Minecraft 1.21 and up
has Spark built-in, a standalone plugin version is 
required for this plugin.

Then, Install Spark Web API.
There are two files you could download, which are:
- spark-web-api-1.0.2.jar
- spark-web-api-1.0.2-all.jar

The `spark-web-api-1.0.2-SNAPSHOT.jar` is a JAR file without 
Javalin. You'll have to somehow add Javalin to classpath at 
runtime.

The `spark-web-api-1.0.2-SNAPSHOT-all.jar` is a JAR file with 
Javalin built-in. It is recommended that you download this 
file as it is easier to set up.

When starting your server, Please remember to set the 
`paper.preferSparkPlugin` system property to `true`

Here's an example on how to do so:
```
java -Dpaper.preferSparkPlugin=true -jar paper-1.21-129.jar
```

## Building
To build the Spark Web API, You'll have to run the shadowJar 
task to build the JAR with Javalin built-in. The JAR output 
can be found at `/build/libs/spark-web-api-version-all.jar`.

This plugin will eventually use the custom plugin loader 
feature of Paper once it has become more usable and stable.

## Configuration
When starting the server first time with the plugin, 
The plugin will generate a config file.
The config file allows you to change the port of the 
server, which routes are enabled, and add additional 
headers to responses.

```yaml
port: 3000
routes:
  tps: true
  mspt: true
  sys_cpu: false
  proc_cpu: false
  gc: false
headers:
  enabled: false
```

An example of adding additional headers:

```yaml
headers:
  enabled: true
  Access-Control-Allow-Origin: "*"
```

## API paths
### /api/tps
Return the TPS of the past 10 seconds, 1 minute, 
5 minutes, and 15 minutes.

Example Response:
```json
{
  "tenSeconds": 19.999926688268733,
  "oneMinute": 20.000035730063832,
  "fiveMinutes": 20.00000458740105,
  "fifteenMinutes": 19.94021043987079
}
```

This route will return 500 Internal Server Error if Spark 
cannot get the TPS of the server.

### /api/mspt
Returns the MSPT value for the past 10 seconds and 
1 minute.

Example Response:
```json
{
  "tenSeconds": {
    "min": 6.523605,
    "max": 2692.747083,
    "mean": 66.92731750515465,
    "median": 23.108289
  },
  "oneMinute": {
    "min": 6.523605,
    "max": 2692.747083,
    "mean": 66.92731750515465,
    "median": 23.108289
  }
}
```

This route will return 500 Internal Server Error if Spark 
cannot get the MSPT of the server.

### /api/gc

Returns the information about the Garbage Collector.

Example Response:
```json
{
  "G1 Young Generation": {
    "name": "G1 Young Generation",
    "frequency": 22328,
    "avgTime": 81,
    "totalCollections": 3,
    "totalTime": 243
  },
  "G1 Old Generation": {
    "name": "G1 Old Generation",
    "frequency": 0,
    "avgTime": 0,
    "totalCollections": 0,
    "totalTime": 0
  }
}
```

### /api/cpu/sys

Returns the system CPU usage in percentage.

Example Response:
```json
{
  "tenSeconds": 0.20461582280862525,
  "oneMinute": 0.31800215079510524,
  "fifteenMinutes": 0.39118254849183964
}
```

### /api/cpu/proc

Returns the Minecraft process CPU usage in percentage.

Example Response:
```json
{
  "tenSeconds": 0.025064927938294894,
  "oneMinute": 0.030417615615548597,
  "fifteenMinutes": 0.06684861042724896
}
```

## License
This plugin is licensed under the MIT License.
