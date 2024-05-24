package fidanhykolli.gjejpuneapp.DAO;

import fidanhykolli.gjejpuneapp.entities.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JobDAO extends JpaRepository<Job, Long> {
    Optional<Job> findByTitle(String title);
    List<Job> findJobsByTitle(String title);
    void deleteById(Long id);
    Optional<Job> findById(Long id);

}
