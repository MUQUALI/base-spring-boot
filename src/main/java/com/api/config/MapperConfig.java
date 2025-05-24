package com.api.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@Configuration
public class MapperConfig {
	/** Config bean name for object mapper */
	public static final String OBJECT_MAPPER_BEAN = "objectMapper";

	/** Config bean name for json converter */
	public static final String JSON_CONVERTER_BEAN = "jsonConverter";

	@Bean(OBJECT_MAPPER_BEAN)
	public ObjectMapper objectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setPropertyNamingStrategy(PropertyNamingStrategies.LOWER_CAMEL_CASE);
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
		mapper.enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL);
		return mapper;
	}

	@Bean(JSON_CONVERTER_BEAN)
	public MappingJackson2HttpMessageConverter jsonConverter(@Qualifier(OBJECT_MAPPER_BEAN) ObjectMapper mapper) {
		MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
		jsonConverter.setObjectMapper(mapper);
		return jsonConverter;
	}
}
