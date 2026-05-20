package e_commerce_backend.features.authentication.repository;

import e_commerce_backend.features.authentication.model.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;



@Repository
public interface AccountRepository extends JpaRepository<Account, Long>{
    @Query(value = "SELECT a FROM Account a WHERE a.username = :username")
    Account findByUsername(String username);

    @Query(value = "SELECT a FROM Account a WHERE a.email = :email")
    Account findByEmail(String email);
}
