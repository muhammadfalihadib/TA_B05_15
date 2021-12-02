package apap.tugasakhir.siretail.repository;

import apap.tugasakhir.siretail.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDb extends JpaRepository<UserModel, Integer> {
    Optional<UserModel> findById(Integer id);
    UserModel findByUsername(String username);
}
