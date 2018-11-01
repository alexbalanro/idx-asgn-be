package com.idexx.asgn.search;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import static org.springframework.test.web.client.ExpectedCount.manyTimes;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withServerError;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SearchTests {
    @Autowired
    private MockMvc mockMvc;

    private MockRestServiceServer server;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * Check that some results are still returned if one upstream service fails
     */
    @Test
    public void testOneUpstreamServiceFail() throws Exception {
        setupMockFailOnAlbum();

        //can check here only books were returned or the number
        this.mockMvc.perform(get("/api/search?searchTerm={something}", "something"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    /**
     * Check that 5xx is returned if all upstream services fail
     */
    @Test
    public void testAllUpstreamServiceFail() throws Exception {
        setupMockFailOnAllSearches();

        //can check here only books were returned or the number
        this.mockMvc.perform(get("/api/search?searchTerm={something}", "something"))
                .andDo(print())
                .andExpect(status().is5xxServerError());
    }

    @Before
    public void beforeTest() {
        if (server == null)
            server = MockRestServiceServer.bindTo(restTemplate).build();
    }

    private void setupMockFailOnAlbum() {
        server.reset();
        server.expect(manyTimes(), requestTo("https://www.googleapis.com/books/v1/volumes?q=something"))
                .andRespond(withSuccess(new ClassPathResource("google_books_response.json"), MediaType.APPLICATION_JSON));

        server.expect(manyTimes(), requestTo("https://itunes.apple.com/search?entity=album&term=something"))
                .andRespond(withServerError());
    }

    private void setupMockFailOnAllSearches() {
        server.reset();
        server.expect(manyTimes(), requestTo("https://www.googleapis.com/books/v1/volumes?q=something"))
                .andRespond(withServerError());

        server.expect(manyTimes(), requestTo("https://itunes.apple.com/search?entity=album&term=something"))
                .andRespond(withServerError());
    }
}
