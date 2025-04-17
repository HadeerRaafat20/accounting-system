package Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import entity.Provider;
import service.ProviderService;

import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/api/providers")
@PreAuthorize("hasRole('ROLE_CHEF_ACCOUNTANT')")
public class ProviderController {

	@Autowired
	private ProviderService providerService;

	@PostMapping
	public ResponseEntity<Provider> createProvider(@RequestBody Provider provider) {
		return ResponseEntity.ok(providerService.createProvider(provider));
	}

	@GetMapping
	public ResponseEntity<List<Provider>> getAllProviders() {
		return ResponseEntity.ok(providerService.getAllProviders());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Provider> getProviderById(@PathVariable Long id) {
		return providerService.getProviderById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@PutMapping("/{id}")
	public ResponseEntity<Provider> updateProvider(@PathVariable Long id, @RequestBody Provider provider) {
		return ResponseEntity.ok(providerService.updateProvider(id, provider));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteProvider(@PathVariable Long id) {
		providerService.deleteProvider(id);
		return ResponseEntity.noContent().build();
	}
}