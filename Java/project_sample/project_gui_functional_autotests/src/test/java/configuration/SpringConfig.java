package configuration;

import com.project.configuration.DriverConfig;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * Created by User on 15.02.2017.
 */
@Configuration
@Import({DriverConfig.class})
@ComponentScan(basePackages = "com.project")
@PropertySource("classpath:${PROJ_ENV:prod}.properties")
public class SpringConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfig() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
