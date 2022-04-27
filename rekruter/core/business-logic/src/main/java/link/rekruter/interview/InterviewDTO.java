package link.rekruter.interview;

import java.time.LocalDateTime;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class InterviewDTO {

    private Long id;

    @Size(max = 255)
    private String title;

    @Size(max = 255)
    private String description;

    private LocalDateTime appointment;

    private Long durationOfInterview;

    @Size(max = 255)
    private String location;

    @Size(max = 255)
    private String createdBy;

    private LocalDateTime createdOn;

    private Boolean isPrivate;

    private Boolean cancelled;

    private Long candidate;

}
