package api_emresab.repository;

import api_emresab.model.ProduccionPoste;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProduccionPosteRepository extends JpaRepository<ProduccionPoste, Long> {
}
