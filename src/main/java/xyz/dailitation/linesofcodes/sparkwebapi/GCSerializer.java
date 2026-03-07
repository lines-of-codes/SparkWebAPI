package xyz.dailitation.linesofcodes.sparkwebapi;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import me.lucko.spark.api.gc.GarbageCollector;

import java.io.IOException;

public class GCSerializer extends StdSerializer<GarbageCollector> {
    public GCSerializer() {
        this(null);
    }

    public GCSerializer(Class<GarbageCollector> t) {
        super(t);
    }

    @Override
    public void serialize(GarbageCollector garbageCollector, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeObjectField("name", garbageCollector.name());
        jsonGenerator.writeObjectField("frequency", garbageCollector.avgFrequency());
        jsonGenerator.writeObjectField("avgTime", garbageCollector.avgTime());
        jsonGenerator.writeObjectField("totalCollections", garbageCollector.totalCollections());
        jsonGenerator.writeObjectField("totalTime", garbageCollector.totalTime());
        jsonGenerator.writeEndObject();
    }
}
