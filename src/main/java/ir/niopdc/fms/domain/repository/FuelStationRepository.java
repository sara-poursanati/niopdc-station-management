package ir.niopdc.fms.domain.repository;

import ir.niopdc.fms.domain.entity.FuelStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FuelStationRepository extends JpaRepository<FuelStation, String> {

    List<FuelStation> findAllByOrderByOpenDate();

    @Query("SELECT f FROM FuelStation f WHERE " +
            "LOWER(f.name) LIKE LOWER(CONCAT('%', :searchText, '%'))")
    List<FuelStation> findBySearchText(@Param("searchText") String searchText);
}
