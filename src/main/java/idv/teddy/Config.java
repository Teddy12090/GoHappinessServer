package idv.teddy;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;

@Configuration
public class Config {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Converter<Date, String> dateToStringConverter = ctx -> ctx.getSource() != null ? sdf.format(ctx.getSource()) : null;
        modelMapper.typeMap(Date.class, String.class).setConverter(dateToStringConverter);
        return modelMapper;
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();

        SimpleModule m = new SimpleModule();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        m.addSerializer(Date.class, new JsonSerializer<>() {
            @Override
            public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
                String format = dateFormat.format(date);
                jsonGenerator.writeString(format);
            }
        });
        m.addSerializer(Time.class, new JsonSerializer<>() {
            @Override
            public void serialize(Time time, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                String format = timeFormat.format(time);
                jsonGenerator.writeString(format);
            }
        });
        objectMapper.registerModule(m);

        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        return objectMapper;
    }
}
