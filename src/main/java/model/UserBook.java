package model;

import lombok.Data;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Data
public class UserBook {

    private Integer id;

    private String title;

    private String author;

    private String description;

    private String tookOn;

    private String dueDate;

    private String returnedOn;

    private Integer daysOverDueDate;

    public void setDaysOverDueDate() {
        this.daysOverDueDate = findDaysOverDueDate();
    }

    private Integer findDaysOverDueDate() {
        LocalDate now = LocalDate.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
        LocalDate date = LocalDate.parse(this.dueDate, formatter);

        Period period = Period.between(date, now);
        int years = period.getYears();
        int months = period.getMonths();
        int days = period.getDays();
        int totalDaysOverDeadline = (years * 365) + (months * 30) + days;

        if(totalDaysOverDeadline <= 0) {
            return 0;
        } else {
            return totalDaysOverDeadline;
        }
    }
}
