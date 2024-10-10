package ir.niopdc.fms.entity.repository;

import ir.niopdc.fms.entity.entity.FuelTerminal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FuelTerminalRepository extends JpaRepository<FuelTerminal, String> {
}
