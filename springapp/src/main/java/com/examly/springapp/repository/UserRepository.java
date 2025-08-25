// package com.examly.springapp.repository;

// import com.examly.springapp.model.User;
// import com.examly.springapp.model.enums.Role;

// import java.util.List;
// import java.util.Optional;

// import org.springframework.data.jpa.repository.JpaRepository;

// public interface UserRepository extends JpaRepository<User, Long> {
//     boolean existsByRole(Role role);
//     Optional<User> findByEmail(String email);

//     List<User> findByTrainer(User trainer);
//     List<User> findByRole(Role role);
// }

package com.examly.springapp.repository;

import com.examly.springapp.model.User;
import com.examly.springapp.model.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByRole(Role role);
    Optional<User> findByEmail(String email);
    Optional<User> findByPhoneNumber(String phoneNumber); // ✅ added
    List<User> findByTrainer(User trainer);
    List<User> findByRole(Role role);
}
