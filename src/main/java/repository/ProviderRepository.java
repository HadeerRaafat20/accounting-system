package repository;

import org.springframework.data.jpa.repository.JpaRepository;

import entity.Provider;

public interface ProviderRepository extends JpaRepository<Provider, Long> {
}
