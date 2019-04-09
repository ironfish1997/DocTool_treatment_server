package top.liuliyong.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.MongoTransactionManager;

/**
 * @Author liyong.liu
 * @Date 2019/4/2
 **/
@Configuration
public class TransactionManagerConfig{
    //向spring注册MongoDB提供的事务管理器
    @Bean
    MongoTransactionManager transactionManager(MongoDbFactory dbFactory) {
        return new MongoTransactionManager(dbFactory);
    }
}
