package net.pingfang.config.restTemplate;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
public class RestTemplateConfig {

    private static final Logger logger= LoggerFactory.getLogger(RestTemplateConfig.class);

    @Bean(name = "restTemplate")
    public RestTemplate restTemplate(ClientHttpRequestFactory factory) {
        // 添加内容转换器,使用默认的内容转换器
        RestTemplate restTemplate = new RestTemplate(factory);
        // 设置编码格式为UTF-8
        List<HttpMessageConverter<?>> converterList = restTemplate.getMessageConverters();
        HttpMessageConverter<?> converterTarget = null;
        for (HttpMessageConverter<?> item : converterList) {
            if (item.getClass() == StringHttpMessageConverter.class) {
                converterTarget = item;
                break;
            }
        }
        if (converterTarget != null) {
            converterList.remove(converterTarget);
        }
        HttpMessageConverter<?> converter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
        converterList.add(1,converter);

        logger.info("-----restTemplate-----初始化完成");
        return restTemplate;
    }

    @Bean
    public ClientHttpRequestFactory simpleClientHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(5000);//ms
        factory.setConnectTimeout(15000);//ms
        return factory;
    }
    
    @Bean(name = "headers")
    public HttpHeaders headers() {
    	HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.valueOf("application/json;charset=UTF-8"));
		return headers;
    }

    
}