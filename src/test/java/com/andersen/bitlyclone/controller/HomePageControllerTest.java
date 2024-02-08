package com.andersen.bitlyclone.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@WebMvcTest(HomePageController.class)
class HomePageControllerTest {

  @Test
  void testHomePage_ReturnsIndexView() throws Exception {

    HomePageController homePageController = new HomePageController();

    MockMvc mockMvc = MockMvcBuilders.standaloneSetup(homePageController).build();

    mockMvc.perform(MockMvcRequestBuilders.get("/"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.view().name("index"));
  }
}