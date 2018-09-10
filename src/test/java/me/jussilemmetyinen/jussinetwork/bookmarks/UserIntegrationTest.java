package me.jussilemmetyinen.jussinetwork.bookmarks;

import me.jussilemmetyinen.jussinetwork.bookmarks.spring.ServiceConfig;
import me.jussilemmetyinen.jussinetwork.bookmarks.spring.TestDbConfig;
import me.jussilemmetyinen.jussinetwork.bookmarks.spring.TestIntegrationConfig;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestDbConfig.class, ServiceConfig.class, TestIntegrationConfig.class })
@Transactional
public class UserIntegrationTest {

   // @Autowired
   // private VerificationTokenRepository tokenRepository;
}
