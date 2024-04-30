package com.richstone.cargo.model;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.richstone.cargo.model.types.Gender;
import com.richstone.cargo.model.types.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String firstname;
    private String lastname;
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    @NotBlank(message = "phone number is required")
    @NotNull
    private String phone;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToMany(mappedBy = "user")
    private List<Token> tokens;

    private boolean isEnabled = false;

    public void setEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }
    public boolean getIsEnabled() {
        return isEnabled;
    }
    public boolean isEnabled() {
        return isEnabled;
    }
    @Column(name = "is_deleted")
    private boolean isDeleted = false;
    public void setDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
    private LocalDate birthDate;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", role=" + role +
                ", isEnabled=" + isEnabled +
                '}';
    }
}
