package idv.teddy;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
}
