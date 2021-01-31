package com.tmorris.reunitedtrav.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tmorris.reunitedtrav.serializers.LocalDateTimeDeserializer;
import com.tmorris.reunitedtrav.serializers.LocalDateTimeSerializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.LocalDateTime;
import java.util.List;

@EnableWebMvc
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(
            ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/static/**")
                .addResourceLocations("/WEB-INF/view/react/build/static/");
        registry.addResourceHandler("/*.js")
                .addResourceLocations("/WEB-INF/view/react/build/");
        registry.addResourceHandler("/*.json")
                .addResourceLocations("/WEB-INF/view/react/build/");
        registry.addResourceHandler("/*.ico")
                .addResourceLocations("/WEB-INF/view/react/build/");
        registry.addResourceHandler("/index.html")
                .addResourceLocations("/WEB-INF/view/react/build/index.html");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedOrigins("*")
                .allowedHeaders("*");
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(customGsonHttpMessageConverter());
    }

    private GsonHttpMessageConverter customGsonHttpMessageConverter() {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .serializeNulls()
                .serializeSpecialFloatingPointValues()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer())
                .create();

        GsonHttpMessageConverter gsonMessageConverter = new GsonHttpMessageConverter();
        gsonMessageConverter.setGson(gson);

        return gsonMessageConverter;
    }
}
