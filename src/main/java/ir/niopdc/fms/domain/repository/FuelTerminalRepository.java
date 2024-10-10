package ir.niopdc.fms.domain.repository;

import ir.niopdc.fms.domain.entity.FuelTerminal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FuelTerminalRepository extends JpaRepository<FuelTerminal, String> {
}
