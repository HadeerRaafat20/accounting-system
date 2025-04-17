package repository;

import org.springframework.data.jpa.repository.JpaRepository;

import entity.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
}