package com.ylluberes.kenectlabs.contact_challenge.config;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.function.client.WebClient;



@Configuration
public class AppConfig {

    private static final Logger logger = LoggerFactory.getLogger(AppConfig.class);

    @Value("${kenect.contact.api.baseUrl}")
    private String kenectLabBaseUrl;

    @Value("${kenect.contact.api.token}")
    private String kenectLabBearerToken;

    @Value("${kenect.restclient.default.connectTimeoutInMills}")
    private Integer kenectLabConnectTimeoutInMills;

    @Value("${kenect.restclient.default.readTimeoutInMills}")
    private Integer getKenectLabReadTimeoutInMills;

    @Bean(name = "kenectLabClient")
    public WebClient webClient() {
        return WebClient.builder().baseUrl(kenectLabBaseUrl).build();
    }

    @Bean(name = "kenectLabsRestClient")
    public RestClient kenectRestClient () {
        return RestClient.builder()
                .baseUrl(kenectLabBaseUrl)
                .defaultHeader("Authorization",kenectLabBearerToken)
                .requestFactory(clientHttpRequestFactory())
                .build();
    }

    @Bean(name = "modelMapper")
    public ModelMapper modelMapper () {
        return new ModelMapper();
    }

    private ClientHttpRequestFactory clientHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        logger.info("kenectLab client read timeout in mills {}", getKenectLabReadTimeoutInMills);
        logger.info("kenectLab client connect timeout in mills {}", kenectLabConnectTimeoutInMills);
        factory.setConnectTimeout(kenectLabConnectTimeoutInMills);
        factory.setReadTimeout(getKenectLabReadTimeoutInMills);
        return factory;
    }


}
