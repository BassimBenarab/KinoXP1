package dk.kinoxp.service;

import dk.kinoxp.model.Reservation;
import dk.kinoxp.model.Showing;
import dk.kinoxp.repository.ReservationRepository;
import dk.kinoxp.repository.ShowingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ShowingRepository showingRepository;
    private final PricingService pricingService;

    public ReservationService(ReservationRepository reservationRepository,
                               ShowingRepository showingRepository,
                               PricingService pricingService) {
        this.reservationRepository = reservationRepository;
        this.showingRepository = showingRepository;
        this.pricingService = pricingService;
    }

    public List<Reservation> getReservationsForShowing(Long showingId) {
        return reservationRepository.findByShowingId(showingId);
    }

    public Set<String> getReservedSeats(Long showingId) {
        return reservationRepository.findByShowingId(showingId).stream()
                .flatMap(r -> r.getSeats().stream())
                .collect(Collectors.toSet());
    }

    public Reservation createReservation(Long showingId, String customerName, String customerPhone, List<String> seats) {
        Showing showing = showingRepository.findById(showingId)
                .orElseThrow(() -> new RuntimeException("Showing not found"));

        // Check for seat conflicts
        Set<String> alreadyReserved = getReservedSeats(showingId);
        for (String seat : seats) {
            if (alreadyReserved.contains(seat)) {
                throw new RuntimeException("Seat " + seat + " is already reserved");
            }
        }

        Reservation reservation = new Reservation();
        reservation.setShowing(showing);
        reservation.setCustomerName(customerName);
        reservation.setCustomerPhone(customerPhone);
        reservation.setSeats(seats);
        reservation.setTotalPrice(pricingService.calculateTotalPrice(showing, seats));

        return reservationRepository.save(reservation);
    }

    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }

    public List<Reservation> getAll() {
        return reservationRepository.findAll();
    }
}
