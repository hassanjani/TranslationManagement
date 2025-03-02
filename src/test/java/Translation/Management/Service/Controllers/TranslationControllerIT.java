package Translation.Management.Service.Controllers;

import Translation.Management.Service.DAO.TranslationRepository;
import Translation.Management.Service.Models.Translation;
import Translation.Management.Service.Services.TranslationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TranslationControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TranslationRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TranslationService service;

    @BeforeEach
    void setUp() {
        repository.deleteAll(); // Ensure a clean database
    }

    @Test
    void shouldCreateAndRetrieveTranslation() throws Exception {
        Translation translation = new Translation();
        translation.setLocale("en");
        translation.setKey("hello");
        translation.setTag("mobile");
        translation.setContent("Hello world");

        mockMvc.perform(post("/translations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(translation)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/translations?locale=en"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void shouldDeleteTranslation() throws Exception {
        Translation translation = new Translation();
        translation.setLocale("fr");
        translation.setKey("bonjour");
        translation.setTag("web");
        translation.setContent("Bonjour tout le monde");

        Translation savedTranslation = repository.save(translation);

        mockMvc.perform(delete("/translations/" + savedTranslation.getId()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/translations/" + savedTranslation.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string("null"));
    }
}
