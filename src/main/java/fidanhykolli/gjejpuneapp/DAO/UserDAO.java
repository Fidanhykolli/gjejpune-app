package fidanhykolli.gjejpuneapp.DAO;

import fidanhykolli.gjejpuneapp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDAO extends JpaRepository <User, Integer> {
    boolean existsByEmail(String email);


    Optional<User> findByEmail(String email);



}
