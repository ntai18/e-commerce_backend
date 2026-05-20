package e_commerce_backend.features.profile.model.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import e_commerce_backend.common.type.GenderType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
public class ProfileResponse {
    private String profileName;
    private String avatar;
    @Enumerated(EnumType.STRING)
    private GenderType gender;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dob;
}
