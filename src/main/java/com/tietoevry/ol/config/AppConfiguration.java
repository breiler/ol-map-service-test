package com.tietoevry.ol.config;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.PrecisionModel;
import org.n52.jackson.datatype.jts.JtsModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;


@Configuration
public class AppConfiguration {
  private static final int DEFAULT_SRID = 3006; // Sweref99;

  @Bean
  @Primary
  public ObjectMapper jacksonObjectMapper() {
    ObjectMapper mapper = JsonMapper.builder()
        .enable(MapperFeature.DEFAULT_VIEW_INCLUSION)
        .enable(SerializationFeature.INDENT_OUTPUT)
        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        .disable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING)
        .build();

    mapper.registerModule(new JtsModule(new GeometryFactory(new PrecisionModel(PrecisionModel.FLOATING), DEFAULT_SRID)));
    return mapper;
  }
}