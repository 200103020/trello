package bitlab.trelloproject.Repositories;

import bitlab.trelloproject.Entities.Comments;
import bitlab.trelloproject.Entities.Tasks;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface CommentRepository extends JpaRepository<Comments, Long> {
    List<Comments> findByTask(Tasks tasks);
}
