package me.jussilemmetyinen.socialnetwork.bookmarks;

import com.google.common.flogger.FluentLogger;
import me.jussilemmetyinen.socialnetwork.bookmarks.domain.Account;
import me.jussilemmetyinen.socialnetwork.bookmarks.domain.Bookmark;
import me.jussilemmetyinen.socialnetwork.bookmarks.repository.AccountRepository;
import me.jussilemmetyinen.socialnetwork.bookmarks.repository.BookmarkRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;


import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class TestBookmarkRestController {

    private static final FluentLogger logger = FluentLogger.forEnclosingClass();

    private MediaType contentType = new MediaType(
            MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    private MockMvc mockMvc;

    private String userName = "bdussault";

    private HttpMessageConverter<Object> mappingJackson2HttpMessageConverter;

    private Account account;

    private List<Bookmark> bookmarkList = new ArrayList<>();

    @Autowired
    private BookmarkRepository bookmarkRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    void setConverters(HttpMessageConverter<?> converters) {

        this.mappingJackson2HttpMessageConverter =
                (HttpMessageConverter<Object>) Stream.of(converters)
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
                .findAny().get();
        Assert.assertNotNull("This JSON converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }

    @Before
    public void setup() {
        Assert.assertNotNull(webApplicationContext);
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext).build();

        this.bookmarkRepository.deleteAllInBatch();
        this.accountRepository.deleteAllInBatch();

        this.account = accountRepository.save(new Account(userName, "password"));
        this.bookmarkList.add(bookmarkRepository.save(new Bookmark(account,
                "http://localhost:8080/1/" + userName, "A description")));
        this.bookmarkList.add(bookmarkRepository.save(new Bookmark(account,
                "http://localhost:8080/2/" + userName, "A Description")));
        logger.atInfo().log(bookmarkList.toString());
    }

    @Test
    public void userNotFound() throws Exception {
        mockMvc.perform(
                post("/george/bookmarks/").content(
                        this.json(new Bookmark(
                                new Account(userName, "password"),
                                "http://localhost:8080/1/" + userName,
                                "A description"))).contentType(contentType))
                .andExpect(status().isNotFound());
    }
    @Test
    public void readSingleBookmark() throws Exception {
        logger.atInfo().log("URL : " + "/" + userName + "/bookmarks/" +
                this.bookmarkList.get(0).getId());
        mockMvc.perform(
                get(
                        "/" + userName + "/bookmarks/"
                                + this.bookmarkList.get(0).getId())
                        .contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(
                        (ResultMatcher) jsonPath("$.id", is(this.bookmarkList.get(0).getId()
                                .intValue())))
                .andExpect(
                        (ResultMatcher) jsonPath("$.uri", is("http://bookmark.com/1/" + userName)))
                .andExpect((ResultMatcher) jsonPath("$.description", is("A description")))
                .andDo(print());
    }

    protected String json(Object olio) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(olio,
                MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        logger.atInfo().log(mockHttpOutputMessage.getBodyAsString());
        return mockHttpOutputMessage.getBodyAsString();
    }
}
