package com.richstone.cargo.model;

import com.richstone.cargo.model.types.OtpStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.Calendar;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Otp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private Date expirationTime;
    @Enumerated(EnumType.STRING)
    private OtpStatus otpStatus;
    private static final int EXPIRATION_TIME = 15;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    public Otp(String code, User user, OtpStatus status) {
        super();
        this.code = code;
        this.user = user;
        this.expirationTime = this.getOtpExpirationTime();
        this.otpStatus = status;
    }

    public Date getOtpExpirationTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE, EXPIRATION_TIME);
        return new Date(calendar.getTime().getTime());
    }
}
