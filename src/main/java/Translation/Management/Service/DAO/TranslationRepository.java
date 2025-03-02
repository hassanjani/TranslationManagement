package Translation.Management.Service.DAO;


import Translation.Management.Service.Models.Translation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.QueryHint;
import java.util.List;
import java.util.stream.Stream;

@Repository
public interface TranslationRepository extends CrudRepository<Translation, Long> {
    List<Translation> findByLocale(String locale);

    List<Translation> findByTag(String tag);

    List<Translation> findByKey(String key);

    @Query("SELECT t FROM Translation t")
    @QueryHints({@QueryHint(name = "org.hibernate.fetchSize", value = "1000")})
    Stream<Translation> streamAll();

}
