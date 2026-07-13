package hct.huaripaucar.jesus.alquiler.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient vehiculoWebClient(@Value("${services.vehiculo-url}") String url) {
        return WebClient.builder().baseUrl(url).build();
    }

    @Bean
    public WebClient clienteWebClient(@Value("${services.cliente-url}") String url) {
        return WebClient.builder().baseUrl(url).build();
    }
}
