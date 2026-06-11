package co.edu.uniremington.portal.repository;

import co.edu.uniremington.portal.entity.ForumReply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ForumReplyRepository extends JpaRepository<ForumReply, Long> {
}
