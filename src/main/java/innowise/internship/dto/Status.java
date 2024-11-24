package innowise.internship.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@ToString
public class Status {
    private String version;
    private String description;
    private String login;
    private Date date;
}
