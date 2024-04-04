package NS.pgmg.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(title = "PogleMogle API 명세서", version = "v2")
)
@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi board() {
        return GroupedOpenApi.builder()
                .group("Board")
                .pathsToMatch("/board/**")
                .build();
    }

    @Bean
    public GroupedOpenApi User() {
        return GroupedOpenApi.builder()
                .group("User")
                .pathsToMatch("/user/**", "/favorite/**", "/login", "/social")
                .build();
    }
}
