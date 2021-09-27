package idv.teddy.dto;

import idv.teddy.validation.OnCreate;
import idv.teddy.validation.StringDateTimeFormat;
import idv.teddy.validation.UniqueActivity;
import lombok.*;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@With
@UniqueActivity(groups = OnCreate.class)
public class ActivityDto {
    private Long id;

    @NotBlank(groups = OnCreate.class)
    private String title;

    @NotBlank(groups = OnCreate.class)
    @StringDateTimeFormat(value = "yyyy-MM-dd")
    private String startDate;

    @StringDateTimeFormat(value = "yyyy-MM-dd")
    private String endDate;

    @StringDateTimeFormat(value = "HH:mm:ss")
    private String startTime;

    @StringDateTimeFormat(value = "HH:mm:ss")
    private String endTime;

    @NotBlank(groups = OnCreate.class)
    @URL
    private String url;

    @NotBlank(groups = OnCreate.class)
    private String source;
}
