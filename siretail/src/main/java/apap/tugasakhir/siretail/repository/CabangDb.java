package apap.tugasakhir.siretail.repository;

import apap.tugasakhir.siretail.model.CabangModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CabangDb extends JpaRepository<CabangModel, Integer> {
}
