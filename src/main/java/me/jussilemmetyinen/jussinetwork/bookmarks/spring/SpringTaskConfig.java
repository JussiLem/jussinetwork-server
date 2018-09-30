package me.jussilemmetyinen.jussinetwork.bookmarks.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@ComponentScan({"me.jussilemmetyinen.jussinetwork.bookmarks.task"})
public class SpringTaskConfig {}
