package com.agroniks.marketplace.configuration;

import com.agroniks.marketplace.item.ItemService;
import com.agroniks.marketplace.item.jpa.ItemCommand;
import com.agroniks.marketplace.user.jpa.UserEntityService;
import groovy.util.logging.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/* The only reason for existence of this class is to override configuration for test suite  */

@Configuration
@Slf4j
public class LoadDatabase {

}
