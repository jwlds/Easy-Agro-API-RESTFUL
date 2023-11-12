package jw.com.br.EasyAgro.domain.user.my;


import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Task {
    private String title;
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private boolean isPermission;

}
