package Translation.Management.Service;

import Translation.Management.Service.DAO.TranslationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import Translation.Management.Service.Models.Translation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    private static final String[] LOCALES = {"en", "fr", "es"};
    private static final String[] TAGS = {"mobile", "web", "desktop"};
    private final TranslationRepository repository;

    public DatabaseSeeder(TranslationRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) {
        if (repository.count() == 0) {
            List<Translation> translations = new ArrayList<>();
            Random random = new Random();

            for (int i = 0; i < 100000; i++) {
                Translation translation = new Translation();
                translation.setLocale(LOCALES[random.nextInt(LOCALES.length)]); // Random locale
                translation.setKey("key" + i);
                translation.setTag(TAGS[random.nextInt(TAGS.length)]); // Random tag
                translation.setContent("Content for key" + i);
                translations.add(translation);
            }
            repository.saveAll(translations);
            System.out.println("Inserted 100K translations with mixed locales and tags.");
        }
    }
}
