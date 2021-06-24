package net.pingfang.utils;

//import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

//import net.pingfang.service.MessageService;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableWebMvc
@EnableSwagger2
@ComponentScan(basePackages = "net.pingfang.controller")
public class SwaggerConfig {

	//@Resource
	//private MessageService messageService;
	
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(this.apiInfo())
				.select()
				.apis(RequestHandlerSelectors
						.basePackage("net.pingfang.controller"))
				.paths(PathSelectors.any()).build();
	}

	private ApiInfo apiInfo() {
		@SuppressWarnings("deprecation")
		ApiInfo info = new ApiInfo("System equipment management and operation and maintenance subsystem", "for lane", "1.0", "www.pingfang.net", "caow",
				"pingfang", "");
		return info;
	}
	
	/**
     * 
     * Description: <br> 
     *  
     * @author luoluocaihong<br>
     * @taskId <br>
     * @return <br>
     */
   /* @Bean
    public Docket customImplementation() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("XXXX"))
                .build()
                .directModelSubstitute(org.joda.time.LocalDate.class, java.sql.Date.class)
                .directModelSubstitute(org.joda.time.DateTime.class, java.util.Date.class)
                .apiInfo(apiInfo());
    }*/
}