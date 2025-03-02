package Translation.Management.Service.Controllers;

import Translation.Management.Service.DAO.TranslationRepository;
import Translation.Management.Service.Models.Translation;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TranslationControllerPerformanceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TranslationRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
        for (int i = 0; i < 5000; i++) {
            Translation translation = new Translation();
            translation.setLocale("en");
            translation.setKey("key" + i);
            translation.setTag("mobile");
            translation.setContent("Content for key" + i);
            repository.save(translation);
        }
    }

    @Test
    void shouldRetrieveTranslationsWithinLimit() throws Exception {
        long startTime = System.currentTimeMillis();

        mockMvc.perform(get("/translations?locale=en")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        assertTrue(duration < 3000, "Response took too long!"); // Ensure under 3 seconds
    }
}
