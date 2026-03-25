package dk.kinoxp.service;

import dk.kinoxp.model.*;
import dk.kinoxp.repository.PriceConfigRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PricingService {

    private final PriceConfigRepository priceConfigRepository;

    public PricingService(PriceConfigRepository priceConfigRepository) {
        this.priceConfigRepository = priceConfigRepository;
    }

    public double calculateTotalPrice(Showing showing, List<String> seats) {
        PriceConfig config = priceConfigRepository.findAll().stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No price configuration found"));

        Movie movie = showing.getMovie();
        Theater theater = showing.getTheater();
        int ticketCount = seats.size();
        double total = 0.0;

        for (String seat : seats) {
            int row = Integer.parseInt(seat.split("-")[0]);
            double price = config.getBasePrice();

            // Long movie surcharge (> 170 min)
            if (movie.isLongMovie()) {
                price += config.getLongMovieSurcharge();
            }

            // 3D surcharge
            if (movie.isIs3D()) {
                price += config.getThreeDSurcharge();
            }

            // Cowboy rows (first N rows) – cheaper
            if (row <= theater.getCowboyRowCount()) {
                price -= config.getCowboyDiscount();
            }

            // Sofa rows (last N rows in large theater) – more expensive
            int sofaStartRow = theater.getRows() - theater.getSofaRowCount() + 1;
            if (theater.getSofaRowCount() > 0 && row >= sofaStartRow) {
                price += config.getSofaSurcharge();
            }

            total += price;
        }

        // Small group fee (5 or fewer tickets)
        if (ticketCount <= 5) {
            total += config.getSmallGroupFee();
        }

        // Group discount (more than 10 tickets – applies to base price only)
        if (ticketCount > 10) {
            double baseDiscount = config.getBasePrice() * (config.getGroupDiscountPercent() / 100.0) * ticketCount;
            total -= baseDiscount;
        }

        return Math.round(total * 100.0) / 100.0;
    }
}
