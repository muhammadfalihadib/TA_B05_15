package apap.tugasakhir.siretail.repository;

import apap.tugasakhir.siretail.model.ItemCabangModel;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemCabangDb extends JpaRepository<ItemCabangModel, Integer> {
    Optional<ItemCabangModel> findByUuidItem(String uuidItem);
}
