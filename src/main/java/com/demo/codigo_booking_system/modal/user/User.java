package com.demo.codigo_booking_system.modal.user;
import com.demo.codigo_booking_system.modal.userpackage.Packages;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;


import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user_tb")
@Data
@RequiredArgsConstructor
@ToString
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;
    private String country;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private Boolean enabled;
    private Boolean locked;
    private String[] authorities;
    private String role;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Packages> packages = new ArrayList<>();

    public int getTotalCredits() {
        return packages.stream()
                .filter(pkg -> !pkg.getExpiryDate().isBefore(LocalDate.now()))  // Only valid packages
                .mapToInt(Packages::getCredits)  // Sum up the credits from valid packages
                .sum();
    }
}
