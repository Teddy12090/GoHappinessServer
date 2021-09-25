package idv.teddy;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

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
        return new ObjectMapper();
    }
}
