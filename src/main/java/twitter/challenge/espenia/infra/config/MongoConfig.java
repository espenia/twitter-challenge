package twitter.challenge.espenia.infra.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableMongoRepositories(basePackages = "twitter.challenge.espenia.infra.mongodb.repository")
@Slf4j
public class MongoConfig extends AbstractMongoClientConfiguration {
    
    @Value("${spring.data.mongodb.uri}")
    private String mongoUri;

    @Value("${spring.data.mongodb.database}")
    private String databaseName;

    @Value("${spring.data.mongodb.port}")
    private int port;

    @Value("${spring.data.mongodb.username}")
    private String username;

    @Value("${spring.data.mongodb.password}")
    private String password;
      
    
    @Override
    @Bean
    @Primary
    public MongoClient mongoClient() {
        log.info("Connecting to MongoDB using URI: {}", mongoUri);
        ConnectionString connectionString = new ConnectionString(String.format("%s:%d/%s", mongoUri, port, databaseName));
        
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .credential(
                    MongoCredential.createCredential(username, databaseName, password.toCharArray())
                )
                .build();

        return MongoClients.create(mongoClientSettings);
    }
    
    @Override
    protected String getDatabaseName() {
        return databaseName;
    }
    
}

