package xyz.dailitation.linesofcodes.sparkwebapi;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class MSPTInfoSerializer extends StdSerializer<MSPTInfo> {
    public MSPTInfoSerializer() {
        this(null);
    }

    public MSPTInfoSerializer(Class<MSPTInfo> t) {
        super(t);
    }

    @Override
    public void serialize(MSPTInfo msptInfo, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeObjectField("tenSeconds", msptInfo.tenSeconds);
        jsonGenerator.writeObjectField("oneMinute", msptInfo.oneMinute);
        jsonGenerator.writeEndObject();
    }
}
