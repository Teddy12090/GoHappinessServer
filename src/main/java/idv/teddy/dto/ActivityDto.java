package idv.teddy.dto;

import idv.teddy.validation.OnCreate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivityDto {
    @NotBlank(groups = OnCreate.class)
    private String title;

    private String startDate;
    private String endDate;
    private String startTime;
    private String endTime;
}
