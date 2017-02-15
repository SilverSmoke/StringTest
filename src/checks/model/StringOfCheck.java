package checks.model;

import java.time.LocalDate;

/**
 * Created by belikov.a on 13.02.2017.
 */
public class StringOfCheck {

    private String id;
    private String market;
    private String section;
    private String product;
    private double price;
    private double profit;
    private LocalDate date;

    public StringOfCheck(String id, String market,String section, String product, double price, double profit, LocalDate date){

        this.id = id;
        this.market = market;
        this.section = section;
        this.product = product;
        this.price = price;
        this.profit = profit;
        this.date = date;

    }

    public StringOfCheck(String id){

    }

    public StringOfCheck(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String market) {
        this.product = product;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) { this.date = date; }

}
