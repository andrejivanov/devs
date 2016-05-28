package de.andrejivanov;

import feign.Feign;
import feign.Logger;
import feign.RequestInterceptor;
import feign.auth.BasicAuthRequestInterceptor;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class APIConfig {
    @Bean(name = "githubAPI")
    public GithubAPI githubAPI(
            @Value("${github.endpoint}") final String endpoint,
            @Value("${github.basicAuth.user}") final String user,
            @Value("${github.basicAuth.password}") final String password
    ) {

        final RequestInterceptor basicAuth = new BasicAuthRequestInterceptor(user, password);

        return Feign.builder()
                .decoder(new GsonDecoder())
                .encoder(new GsonEncoder())
                .requestInterceptor(basicAuth)
                .logLevel(Logger.Level.BASIC)
                .target(GithubAPI.class, endpoint);
    }
}
