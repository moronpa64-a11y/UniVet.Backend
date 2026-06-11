package co.edu.uniremington.portal.repository;

import co.edu.uniremington.portal.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News, Long> {
}
