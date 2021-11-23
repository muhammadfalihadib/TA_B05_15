package apap.tugasakhir.siretail.repository;

import apap.tugasakhir.siretail.model.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDb extends JpaRepository<RoleModel, Long> {
}
