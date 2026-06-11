package co.edu.uniremington.portal.repository;

import co.edu.uniremington.portal.entity.VeterinaryJourney;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VeterinaryJourneyRepository extends JpaRepository<VeterinaryJourney, Long> {
}
