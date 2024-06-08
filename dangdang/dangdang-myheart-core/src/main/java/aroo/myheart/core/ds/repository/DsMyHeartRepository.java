package aroo.myheart.core.ds.repository;

import aroo.myheart.core.ds.domain.DsMyHeart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DsMyHeartRepository extends JpaRepository<DsMyHeart, Long> {
}
