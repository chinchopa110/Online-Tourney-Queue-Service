package itmo.polyakov.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import javax.sql.DataSource
import org.springframework.boot.jdbc.DataSourceBuilder

@Configuration
@EnableJpaRepositories(basePackages = ["itmo.polyakov.repositories"])
class DatabaseConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    fun dataSource(): DataSource {
        return DataSourceBuilder.create().build()
    }
}