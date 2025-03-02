package Translation.Management.Service.Services;


import Translation.Management.Service.DAO.TranslationRepository;
import Translation.Management.Service.Models.Translation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class TranslationService {

    @Autowired
    private TranslationRepository repository;

    public Translation createTranslation(Translation translation) {
        return repository.save(translation);
    }

    public Optional<Translation> getTranslation(Long id) {
        return repository.findById(id);
    }

    public List<Translation> searchByLocale(String locale) {
        return repository.findByLocale(locale);
    }

    public List<Translation> searchByTag(String tag) {
        return repository.findByTag(tag);
    }

    public List<Translation> searchByKey(String key) {
        return repository.findByKey(key);
    }

    public void deleteTranslation(Long id) {
        repository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Stream<Translation> streamAllTranslations() {
        return repository.streamAll();  // ✅ Fetches large datasets efficiently
    }


    public List<Translation> getTranslationsByLocale(String locale) {
        return repository.findByLocale(locale);
    }

}
