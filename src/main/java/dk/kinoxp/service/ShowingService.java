package dk.kinoxp.service;

import dk.kinoxp.model.Showing;
import dk.kinoxp.repository.ShowingRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ShowingService {

    private final ShowingRepository showingRepository;

    public ShowingService(ShowingRepository showingRepository) {
        this.showingRepository = showingRepository;
    }

    public List<Showing> getActiveShowings() {
        return showingRepository.findByActiveTrue();
    }

    public List<Showing> getAll() {
        return showingRepository.findAll();
    }

    public Showing save(Showing showing) {
        return showingRepository.save(showing);
    }

    public void deactivate(Long id) {
        showingRepository.findById(id).ifPresent(s -> {
            s.setActive(false);
            showingRepository.save(s);
        });
    }

    public void delete(Long id) {
        showingRepository.deleteById(id);
    }

    public Showing findById(Long id) {
        return showingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Showing not found"));
    }
}
