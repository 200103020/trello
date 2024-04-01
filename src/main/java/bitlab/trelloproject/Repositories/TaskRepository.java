package bitlab.trelloproject.Repositories;

import bitlab.trelloproject.Entities.Folders;
import bitlab.trelloproject.Entities.Tasks;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface TaskRepository extends JpaRepository<Tasks,Long> {
    List<Tasks> findByFolder(Folders folders);
}
