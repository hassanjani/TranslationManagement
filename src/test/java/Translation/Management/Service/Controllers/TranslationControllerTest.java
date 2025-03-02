package Translation.Management.Service.Controllers;

import Translation.Management.Service.Models.Translation;
import Translation.Management.Service.Services.TranslationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TranslationControllerTest {

    @Mock
    private TranslationService service;

    @Mock
    private PlatformTransactionManager transactionManager;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private TranslationController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateTranslation() {
        Translation translation = new Translation();
        when(service.createTranslation(any())).thenReturn(translation);

        ResponseEntity<Translation> response = controller.createTranslation(translation);

        assertNotNull(response.getBody());
        verify(service, times(1)).createTranslation(any());
    }

    @Test
    void shouldGetTranslationById() {
        Translation translation = new Translation();
        when(service.getTranslation(1L)).thenReturn(Optional.of(translation));

        ResponseEntity<Optional<Translation>> response = controller.getTranslation(1L);

        assertTrue(response.getBody().isPresent());
        verify(service, times(1)).getTranslation(1L);
    }

    @Test
    void shouldSearchTranslationsByLocale() {
        when(service.searchByLocale("en")).thenReturn(List.of(new Translation()));

        ResponseEntity<List<Translation>> response = controller.searchTranslations("en", null, null);

        assertEquals(1, response.getBody().size());
        verify(service, times(1)).searchByLocale("en");
    }

    @Test
    void shouldDeleteTranslation() {
        doNothing().when(service).deleteTranslation(1L);

        ResponseEntity<String> response = controller.deleteTranslation(1L);

        assertEquals("Translation deleted successfully.", response.getBody());
        verify(service, times(1)).deleteTranslation(1L);
    }

    @Test
    void shouldStreamTranslationsAsJson() throws IOException {
        Stream<Translation> mockStream = Stream.of(new Translation());
        when(service.streamAllTranslations()).thenReturn(mockStream);

        StreamingResponseBody responseBody = controller.exportTranslationsAsStream();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        responseBody.writeTo(outputStream);

        assertTrue(outputStream.size() > 0);
        verify(service, times(1)).streamAllTranslations();
    }
}
