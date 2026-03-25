package dk.kinoxp.controller;

import dk.kinoxp.model.Theater;
import dk.kinoxp.repository.TheaterRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/theaters")
public class TheaterController {

    private final TheaterRepository theaterRepository;

    public TheaterController(TheaterRepository theaterRepository) {
        this.theaterRepository = theaterRepository;
    }

    @GetMapping
    public List<Theater> getAll() {
        return theaterRepository.findAll();
    }

    @PostMapping
    public Theater create(@RequestBody Theater theater) {
        return theaterRepository.save(theater);
    }
}
