package uy.edu.ucu.si.grupo8.obligatorio3.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uy.edu.ucu.si.grupo8.obligatorio3.models.User;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(final String username);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("UPDATE User SET active = ?1 WHERE id = ?2")
    Optional<User> setUserActive(final Long id, final Boolean active);
}
