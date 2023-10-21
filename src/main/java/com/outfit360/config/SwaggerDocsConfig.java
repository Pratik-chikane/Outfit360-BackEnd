//package com.outfit360.config;
//
//
//import java.util.Collections;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.service.Contact;
//
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//
//@Configuration
//@EnableSwagger2
//public class SwaggerDocsConfig {
//	@Bean
//	public Docket createDocket() {
//		return new Docket(DocumentationType.SWAGGER_2).// UI Screen type
//				select(). 
//				apis(RequestHandlerSelectors.basePackage("com.outfit360.controller"))
//																						
//				.paths(PathSelectors.regex("/api.*")).// To specify the request paths
//				build()// build the docket object
//				.useDefaultResponseMessages(true).apiInfo(getApiInfo());
//
//	}
//
//	private ApiInfo getApiInfo() {
//		Contact contact = new Contact("outfit360", "http://www.google.com/course", "enquire@outfit360.com");
//		return new ApiInfo("outfit360", "API INFORMATION OF OUTFIT360", "3.4.RELEASE", "http:", contact,
//				"GNU PUBLIC", "http://apache.org/license/guru", Collections.emptyList());
//	}
//
//}
//
