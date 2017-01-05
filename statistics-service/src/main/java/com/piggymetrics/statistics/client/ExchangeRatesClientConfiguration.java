package com.piggymetrics.statistics.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.codec.Decoder;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.cloud.netflix.feign.support.ResponseEntityDecoder;
import org.springframework.cloud.netflix.feign.support.SpringDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

/**
 * Created by yungoo on 2017/1/5.
 */
@Configuration
public class ExchangeRatesClientConfiguration {

    HttpMessageConverter mappingJackson2HttpMessageConverter = new ContentTypeFixedJackson2HttpMessageConverter();

    private static class ContentTypeFixedJackson2HttpMessageConverter extends AbstractJackson2HttpMessageConverter {
        public ContentTypeFixedJackson2HttpMessageConverter() {
            super(Jackson2ObjectMapperBuilder.json().build(),
                    MediaType.TEXT_HTML,
                    MediaType.APPLICATION_JSON,
                    new MediaType("application", "*+json"));
        }
    };

    HttpMessageConverters messageConverters = new HttpMessageConverters(mappingJackson2HttpMessageConverter);

    ObjectFactory<HttpMessageConverters> messageConvertersFactory = new ObjectFactory<HttpMessageConverters>() {
        @Override
        public HttpMessageConverters getObject() throws BeansException {
            return messageConverters;
        }
    };

    @Bean
    public Decoder feignDecoder() {
        return new ResponseEntityDecoder(new SpringDecoder(this.messageConvertersFactory));
    }
}
