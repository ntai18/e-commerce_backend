package e_commerce_backend.features.profile.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import e_commerce_backend.common.type.GenderType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "profile")
@Getter
@Setter
public class Profile {
    @Id
    @Column(name = "account_id")
    private Long accountId;
    private String profileName;
    private String avatar;
    @Enumerated(EnumType.STRING)
    private GenderType gender;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dob;

    @OneToMany(mappedBy = "profile")
    private List<UserAddress> userAddress;
}
