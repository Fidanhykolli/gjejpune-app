package fidanhykolli.gjejpuneapp.DAO;

import fidanhykolli.gjejpuneapp.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CompanyDAO extends JpaRepository<Company, Integer> {
    List<Company> findByCompanyName(String companyName);

    boolean existsByEmail(String email);
    Optional<Company> findByEmail(String email);
}
