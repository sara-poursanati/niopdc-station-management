package ir.niopdc.fms.domain.repository;

import ir.niopdc.fms.domain.entity.FuelStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FuelStationRepository extends JpaRepository<FuelStation, String> {

    public List<FuelStation> findAllByOrderByOpenDate();

//    @Query("SELECT fs FROM FuelStation fs JOIN FETCH fs.fuelTerminals WHERE fs.id = :id")
//    Optional<FuelStation> findFuelStationById(@Param("id") String id);
}
