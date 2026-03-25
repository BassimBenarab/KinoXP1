package dk.kinoxp;

import dk.kinoxp.model.*;
import dk.kinoxp.repository.PriceConfigRepository;
import dk.kinoxp.service.PricingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PricingServiceTest {

    private PricingService pricingService;
    private PriceConfigRepository priceConfigRepository;
    private PriceConfig config;
    private Showing showing;
    private Movie movie;
    private Theater theater;

    @BeforeEach
    void setUp() {
        priceConfigRepository = Mockito.mock(PriceConfigRepository.class);
        pricingService = new PricingService(priceConfigRepository);

        config = new PriceConfig();
        config.setBasePrice(100.0);
        config.setLongMovieSurcharge(20.0);
        config.setThreeDSurcharge(15.0);
        config.setCowboyDiscount(20.0);
        config.setSofaSurcharge(30.0);
        config.setSmallGroupFee(25.0);
        config.setGroupDiscountPercent(7.0);

        when(priceConfigRepository.findAll()).thenReturn(List.of(config));

        movie = new Movie();
        movie.setDurationMinutes(120);
        movie.setIs3D(false);

        theater = new Theater();
        theater.setRows(20);
        theater.setSeatsPerRow(12);
        theater.setCowboyRowCount(2);
        theater.setSofaRowCount(0);

        showing = new Showing();
        showing.setMovie(movie);
        showing.setTheater(theater);
    }

    @Test
    void normalSeat_basePrice() {
        double price = pricingService.calculateTotalPrice(showing, List.of("5-6"));
        // 1 ticket <= 5 → small group fee
        assertEquals(100.0 + 25.0, price);
    }

    @Test
    void cowboyRow_discount() {
        double price = pricingService.calculateTotalPrice(showing, List.of("1-3"));
        // cowboy discount + small group fee
        assertEquals(100.0 - 20.0 + 25.0, price);
    }

    @Test
    void longMovie_surcharge() {
        movie.setDurationMinutes(180);
        double price = pricingService.calculateTotalPrice(showing, List.of("5-5"));
        assertEquals(100.0 + 20.0 + 25.0, price);
    }

    @Test
    void threeD_surcharge() {
        movie.setIs3D(true);
        double price = pricingService.calculateTotalPrice(showing, List.of("5-5"));
        assertEquals(100.0 + 15.0 + 25.0, price);
    }

    @Test
    void sofaRow_surcharge() {
        theater.setRows(25);
        theater.setSofaRowCount(2); // rows 24-25 are sofa
        double price = pricingService.calculateTotalPrice(showing, List.of("25-1"));
        assertEquals(100.0 + 30.0 + 25.0, price);
    }

    @Test
    void groupDiscount_moreThan10() {
        List<String> seats = List.of("5-1","5-2","5-3","5-4","5-5","5-6","5-7","5-8","5-9","5-10","5-11");
        double price = pricingService.calculateTotalPrice(showing, seats);
        // 11 tickets × 100 base = 1100, minus 7% group discount on base = 1100 - 77 = 1023
        assertEquals(1100.0 - (100.0 * 0.07 * 11), price, 0.01);
    }

    @Test
    void smallGroupFee_exactly5() {
        List<String> seats = List.of("5-1","5-2","5-3","5-4","5-5");
        double price = pricingService.calculateTotalPrice(showing, seats);
        assertEquals(5 * 100.0 + 25.0, price);
    }

    @Test
    void noFeeNoDiscount_6tickets() {
        List<String> seats = List.of("5-1","5-2","5-3","5-4","5-5","5-6");
        double price = pricingService.calculateTotalPrice(showing, seats);
        assertEquals(6 * 100.0, price);
    }
}
