package cn.tedu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
@MapperScan("cn.tedu.cart.mapper")
public class StarterCart {
    public static void main(String[] args) {
        SpringApplication.run(StarterCart.class,args);
    }
    //ribbon+restTemplate
    @Bean
    @LoadBalanced
    public RestTemplate initRestemplate(){
        return new RestTemplate();
    }
}