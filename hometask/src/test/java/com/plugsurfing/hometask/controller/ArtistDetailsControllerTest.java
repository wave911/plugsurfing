package com.plugsurfing.hometask.controller;

import com.plugsurfing.hometask.config.RedisProperties;
import com.plugsurfing.hometask.service.artistdetails.ArtistDetailsService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableConfigurationProperties(RedisProperties.class)
@ActiveProfiles("test")
public class ArtistDetailsControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    WebApplicationContext webApplicationContext;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @MockBean
    ArtistDetailsService artistDetailsService;

    @Test
    public void testArtistDetailsController() throws Exception {
        when(artistDetailsService.getArtistDetails(anyString())).thenReturn("2022/08/02 22:57:10");
        mockMvc.perform(get("/musify/music-artist/details/f27ec8db-af05-4f36-916e-3d57f91ecf5e")).andExpect(content().string("2022/08/02 22:57:10"));
    }
}
