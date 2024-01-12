package goorm.wherebnb.domain.dto.response;

import goorm.wherebnb.domain.dao.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.Period;

@Getter
public class HostResponse {

    private String hostPhoto;

    private String hostName;

    private String hostExplanation;

    private String hostCareer;

    @Builder
    public HostResponse(User host) {
        this.hostPhoto = host.getPicture();
        this.hostName = host.getName();
        this.hostExplanation = host.getExplanation();

        LocalDate startDate = host.getCreateDate().toLocalDate();
        LocalDate now = LocalDate.now();
        Period period = Period.between(startDate, now);

        this.hostCareer = period.getYears() + " years, " +
                period.getMonths() + " months, " +
                period.getDays() + " days";
    }
}
