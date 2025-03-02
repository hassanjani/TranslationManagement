package Translation.Management.Service.Controllers;

import Translation.Management.Service.Models.Translation;
import Translation.Management.Service.Services.TranslationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@RestController
@RequestMapping("/translations")
public class TranslationController {

    @Autowired
    private TranslationService service;

    @Autowired
    private org.springframework.transaction.PlatformTransactionManager transactionManager;
    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping
    public ResponseEntity<Translation> createTranslation(@RequestBody Translation translation) {
        return ResponseEntity.ok(service.createTranslation(translation));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Translation>> getTranslation(@PathVariable Long id) {
        return ResponseEntity.ok(service.getTranslation(id));
    }

    @GetMapping
    public ResponseEntity<List<Translation>> searchTranslations(
            @RequestParam(required = false) String locale,
            @RequestParam(required = false) String tag,
            @RequestParam(required = false) String key) {
        if (locale != null) return ResponseEntity.ok(service.searchByLocale(locale));
        if (tag != null) return ResponseEntity.ok(service.searchByTag(tag));
        if (key != null) return ResponseEntity.ok(service.searchByKey(key));
        return ResponseEntity.ok(List.of());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTranslation(@PathVariable Long id) {
        service.deleteTranslation(id);
        return ResponseEntity.ok("Translation deleted successfully.");
    }

    @GetMapping("/export/stream")
    public StreamingResponseBody exportTranslationsAsStream() {
        return outputStream -> {
            TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);
            transactionTemplate.execute(status -> {
                try (Stream<Translation> stream = service.streamAllTranslations()) {
                    // JSON array start
                    outputStream.write("[".getBytes());

                    Iterator<Translation> iterator = stream.iterator();
                    boolean hasNext = iterator.hasNext();

                    while (hasNext) {
                        Translation translation = iterator.next();
                        // Use the Spring-configured ObjectMapper with JavaTimeModule
                        objectMapper.writeValue(outputStream, translation);

                        hasNext = iterator.hasNext();
                        if (hasNext) {
                            // Add comma between JSON objects
                            outputStream.write(",".getBytes());
                        }
                        outputStream.flush();
                    }

                    // JSON array end
                    outputStream.write("]".getBytes());

                } catch (IOException e) {
                    throw new RuntimeException("Error streaming JSON", e);
                }
                return null;
            });
        };
    }

}
