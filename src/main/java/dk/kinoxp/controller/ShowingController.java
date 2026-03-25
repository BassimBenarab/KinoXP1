package dk.kinoxp.controller;

import dk.kinoxp.model.Showing;
import dk.kinoxp.service.ShowingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/showings")
public class ShowingController {

    private final ShowingService showingService;

    public ShowingController(ShowingService showingService) {
        this.showingService = showingService;
    }

    // Public: get active showings for the front page
    @GetMapping("/active")
    public List<Showing> getActiveShowings() {
        return showingService.getActiveShowings();
    }

    // Admin: get all showings
    @GetMapping
    public List<Showing> getAll() {
        return showingService.getAll();
    }

    // Admin: create showing
    @PostMapping
    public Showing create(@RequestBody Showing showing) {
        return showingService.save(showing);
    }

    // Admin: update showing
    @PutMapping("/{id}")
    public Showing update(@PathVariable Long id, @RequestBody Showing showing) {
        showing.setId(id);
        return showingService.save(showing);
    }

    // Admin: deactivate (remove from schedule without deleting)
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivate(@PathVariable Long id) {
        showingService.deactivate(id);
        return ResponseEntity.noContent().build();
    }

    // Admin: delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        showingService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
