package dk.kinoxp.model;

import jakarta.persistence.*;

@Entity
@Table(name = "price_config")
public class PriceConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double basePrice;
    private double longMovieSurcharge;   // > 170 min
    private double threeDSurcharge;
    private double cowboyDiscount;       // subtracted from base
    private double sofaSurcharge;        // added to base
    private double smallGroupFee;        // <= 5 tickets
    private double groupDiscountPercent; // > 10 tickets (e.g. 7.0 = 7%)

    public PriceConfig() {}

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public double getBasePrice() { return basePrice; }
    public void setBasePrice(double basePrice) { this.basePrice = basePrice; }

    public double getLongMovieSurcharge() { return longMovieSurcharge; }
    public void setLongMovieSurcharge(double longMovieSurcharge) { this.longMovieSurcharge = longMovieSurcharge; }

    public double getThreeDSurcharge() { return threeDSurcharge; }
    public void setThreeDSurcharge(double threeDSurcharge) { this.threeDSurcharge = threeDSurcharge; }

    public double getCowboyDiscount() { return cowboyDiscount; }
    public void setCowboyDiscount(double cowboyDiscount) { this.cowboyDiscount = cowboyDiscount; }

    public double getSofaSurcharge() { return sofaSurcharge; }
    public void setSofaSurcharge(double sofaSurcharge) { this.sofaSurcharge = sofaSurcharge; }

    public double getSmallGroupFee() { return smallGroupFee; }
    public void setSmallGroupFee(double smallGroupFee) { this.smallGroupFee = smallGroupFee; }

    public double getGroupDiscountPercent() { return groupDiscountPercent; }
    public void setGroupDiscountPercent(double groupDiscountPercent) { this.groupDiscountPercent = groupDiscountPercent; }
}
