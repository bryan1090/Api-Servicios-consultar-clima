package com.pfcti.weather;

import com.pfcti.weather.controller.ClimaController;
import com.pfcti.weather.controller.EstadoController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@SpringBootTest
@AutoConfigureMockMvc
public class ClimaTest {
    @MockBean
    private ClimaController climaController;

    @MockBean
    private EstadoController estadoController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void devuelve_weather() throws Exception {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/api/v1/clima");
        request.content("{\"lat\": 2.1,\"lon\": 2.1}");
        request.contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void status() {

        try {
            mockMvc.perform(MockMvcRequestBuilders.get("/sys/status"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
            ;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
