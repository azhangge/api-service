package com.huajie.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.sql.DataSource;

/**
 * Created by fangxing on 17-7-3.
 */

@Configuration
@EnableJpaRepositories("com.huajie")
public class JpaConfig {
}
