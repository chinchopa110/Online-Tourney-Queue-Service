package itmo.polyakov.configs

import com.zaxxer.hikari.HikariDataSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import javax.sql.DataSource

@Configuration
@EnableJpaRepositories(basePackages = ["itmo.polyakov.repositories"])
class DatabaseConfig {

    @Bean
    fun dataSource(): DataSource {
        return HikariDataSource().apply {
            jdbcUrl = "jdbc:postgresql://localhost:5432/card_db"
            username = "postgres"
            password = "123"
            driverClassName = "org.postgresql.Driver"
        }
    }
}