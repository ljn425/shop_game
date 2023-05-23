package shop.game.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.game.enums.Approval;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Partner {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "partner_id")
    private Long id;
    private String loginEmail;  //로그인 아이디(이메일)
    private String password;    //비밀번호

    @Enumerated(EnumType.STRING)
    private Approval approval;  //승인 여부

    private String bankName;    //은행
    private String bankAccount; //계좌

    private LocalDateTime createdDate;  //가입 날짜

    public Partner(String loginEmail, String password) {
        this.loginEmail = loginEmail;
        this.password = password;
    }

    public Partner(Long id) {
        this.id = id;
    }

    @Builder
    public Partner(Long id, String loginEmail, String password, String bankName, String bankAccount, LocalDateTime createdDate) {
        this.id = id;
        this.loginEmail = loginEmail;
        this.password = password;
        this.bankName = bankName;
        this.bankAccount = bankAccount;
        this.createdDate = createdDate;
    }

    @PrePersist
    public void prePersist() {
        if (getApproval() == null) this.approval = Approval.READY;
    }
}
