package com.practice.microservices.apigateway;

import java.util.function.Function;

import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/* 
 * adding custom filters 
 */

@Configuration
public class ApiGatewayConfiguration {

	@Bean
	public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
		/*
		 * using default routes return builder.routes().build();
		 */
		/*creating seperate function as routefunction
		 * 
		 * Function<PredicateSpec, Buildable<Route>> routeFunction = p->p.path("/get")
		 * match path .filters(f->f.addRequestHeader("myHeader", "my uri")
		 * .addRequestParameter("MyParam", "value")) to add header or path parameter,
		 * used to add authentication parameters .uri("http://httpbin.org:80")
		 * redirecting to the new uri ;
		 * 
		 * return builder.routes() .route(routeFunction) .build();
		 */
		
		
		return builder
				.routes()
					/*match path 1,predicate*/ 
					.route(p->p.path("/get") 
						/* to add header or path parameter, used to add authentication parameters(what needs to be done, once the it match the predicate)*/
						.filters(f->f.addRequestHeader("myHeader", "my uri")
								.addRequestParameter("MyParam", "value"))
				 		/*redirecting to the new uri */
						.uri("http://httpbin.org:80")
						)
					
					/*match path 2*/ 
					.route(p->p.path("/currency-exchange/**")

							/*
							 * talk to eureka find the location of the service and load balance btw the
							 * instances of the service, using the specified name of the service on the Eureka service
							 */
							.uri("lb://currency-exchange")
						)
					
					/*match path 3*/ 
					.route(p->p.path("/currency-conversion/**")

							/*
							 * talk to eureka find the location of the service and load balance btw the
							 * instances of the service, using the specified name of the service on the Eureka service
							 */
							.uri("lb://currency-conversion")
						)
					
					/*match path 3*/ 
					.route(p->p.path("/currency-conversion-feign/**")

							/*
							 * talk to eureka find the location of the service and load balance btw the
							 * instances of the service, using the specified name of the service on the Eureka service
							 */
							.uri("lb://currency-conversion")
						)
					
					/*match path 3*/ 
					.route(p->p.path("/currency-conversion-new/**")
						/* rewtire the uri to feign uri using filters
						 * (?<segment>.*) used to match the string following the specified uri 
						 * and add it in the place of ${segment} in replacing uri */
							.filters(
									f->f.rewritePath(
											"/currency-conversion-new/(?<segment>.*)",
											"/currency-conversion-feign/${segment}"
											)
									)
							/*
							 * talk to eureka find the location of the service and load balance btw the
							 * instances of the service, using the specified name of the service on the Eureka service
							 */
							.uri("lb://currency-conversion")
						)
					
		.build();
		
	}
}
