package service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import entity.Provider;
import repository.ProviderRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProviderService {

	@Autowired
	private ProviderRepository providerRepository;

	public Provider createProvider(Provider provider) {
		return providerRepository.save(provider);
	}

	public List<Provider> getAllProviders() {
		return providerRepository.findAll();
	}

	public Optional<Provider> getProviderById(Long id) {
		return providerRepository.findById(id);
	}

	public Provider updateProvider(Long id, Provider updatedProvider) {
		return providerRepository.findById(id).map(provider -> {
			provider.setName(updatedProvider.getName());
			provider.setAddress(updatedProvider.getAddress());
			provider.setPhone(updatedProvider.getPhone());
			provider.setService(updatedProvider.getService());
			provider.setNote(updatedProvider.getNote());
			return providerRepository.save(provider);
		}).orElseThrow(() -> new RuntimeException("Provider not found with ID: " + id));
	}

	public void deleteProvider(Long id) {
		providerRepository.deleteById(id);
	}
}