package dk.kinoxp.controller;

import dk.kinoxp.model.Reservation;
import dk.kinoxp.service.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    // Public: get reserved seats for a showing
    @GetMapping("/showing/{showingId}/seats")
    public Set<String> getReservedSeats(@PathVariable Long showingId) {
        return reservationService.getReservedSeats(showingId);
    }

    // Public: create a reservation
    @PostMapping
    public Reservation create(@RequestBody Map<String, Object> body) {
        Long showingId = Long.valueOf(body.get("showingId").toString());
        String customerName = body.get("customerName").toString();
        String customerPhone = body.get("customerPhone").toString();
        @SuppressWarnings("unchecked")
        List<String> seats = (List<String>) body.get("seats");
        return reservationService.createReservation(showingId, customerName, customerPhone, seats);
    }

    // Admin: get all reservations
    @GetMapping
    public List<Reservation> getAll() {
        return reservationService.getAll();
    }

    // Admin: get reservations for a showing
    @GetMapping("/showing/{showingId}")
    public List<Reservation> getForShowing(@PathVariable Long showingId) {
        return reservationService.getReservationsForShowing(showingId);
    }

    // Admin: delete reservation
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }
}
