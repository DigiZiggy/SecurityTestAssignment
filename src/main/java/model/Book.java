package model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@Data
public class Book {

    private Integer id;

    private String title;

    private String author;

    private String description;

    private Integer amount;

    private String addedToStock;

    private boolean available;

    private Integer lendingTimeInWeeks;

    @JsonIgnore
    private List<String> users;
    @JsonIgnore
    private Integer newBookLendingTimeInWeeks = 1;
    @JsonIgnore
    private Integer fewCopiesLeftLendingTimeInWeeks = 1;
    @JsonIgnore
    private Integer fewCopiesAmount = 5;
    @JsonIgnore
    private Integer normalLendingTimeInWeeks = 4;
    @JsonIgnore
    private Integer bookConsideredNewInMonths = 3;

    public void setAmount(Integer amount) {
        if (amount == 0) {
            this.available = false;
        }
        this.amount = amount;
    }

    public void setLendingTimeInWeeks() {
        int books_age_in_months = findBooksAgeInMonths();
        if (this.available && books_age_in_months < bookConsideredNewInMonths) {
            this.lendingTimeInWeeks = newBookLendingTimeInWeeks;
        } else if (this.available && this.amount < fewCopiesAmount){
            this.lendingTimeInWeeks = fewCopiesLeftLendingTimeInWeeks;
        } else if (this.available){
            this.lendingTimeInWeeks = normalLendingTimeInWeeks;
        } else {
            this.lendingTimeInWeeks = 0;
        }
    }

    private Integer findBooksAgeInMonths() {
        LocalDate now = LocalDate.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
        LocalDate date = LocalDate.parse(this.addedToStock, formatter);

        Period period = Period.between(date, now);
        int years = period.getYears();
        int months = period.getMonths();
        return (years * 12) + months;
    }
}
