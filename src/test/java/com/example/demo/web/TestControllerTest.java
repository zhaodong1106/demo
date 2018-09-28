package com.example.demo.web;

import com.example.demo.dao.GoodsDao;
import com.example.demo.entity.Goods;
import com.example.demo.utils.PictureService;
import org.junit.Test;
import org.junit.experimental.results.ResultMatchers;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.UUID;

import static org.junit.Assert.*;

/**
 * Created by T011689 on 2018/9/26.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TestControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Test
    public void testGoods() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/testGoods")).andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void goodsList() throws Exception {

    }

    @Test
    public void add() throws Exception {

    }

    @Test
    public void testPoi() throws Exception {

    }

    @Test
    public void testMultipartFile() throws Exception {

    }

}