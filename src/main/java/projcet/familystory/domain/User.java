package projcet.familystory.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import projcet.familystory.social.Role1;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class User {

    @Id
    @GeneratedValue
    private Long uID;

    @NotNull
    private String userID;

    @NotNull
    private String password;

    @NotNull
    private String name;

    @NotNull
    private String email;

    @NotNull
    private String nickName;

    @NotNull
    private String phoneNumber;

    @NotNull
    private LocalDate birthday;

    private String userImage;
    private String mainTeamID;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserTeam> teams = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Content> contents = new ArrayList<>();





}
