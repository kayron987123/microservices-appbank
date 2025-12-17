package pe.gad.appbank.userservice.domain.model;

import lombok.Getter;
import pe.gad.appbank.userservice.domain.enums.UserStatus;
import pe.gad.appbank.userservice.domain.event.UserCreatedEvent;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class User extends AggregateRoot{
    private final UserId id;
    private Email email;
    private Password password;
    private PersonalInfo personalInfo;
    private UserStatus status;
    private Boolean isEmailVerified;
    private final List<UserDocument> documents;

    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private User(UserId id, Email email, Password password, PersonalInfo personalInfo,
                 UserStatus status, Boolean isEmailVerified, List<UserDocument> documents,
                 LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.personalInfo = personalInfo;
        this.status = status;
        this.isEmailVerified = isEmailVerified;
        this.documents = documents != null ? documents : new ArrayList<>();
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static User create(Email email, Password password, PersonalInfo personalInfo) {
        UserId userId = UserId.generate();
        LocalDateTime now = LocalDateTime.now();

        User user = new User(
                userId,
                email,
                password,
                personalInfo,
                UserStatus.ACTIVE,
                false,
                new ArrayList<>(),
                now,
                now
        );
        user.registerEvent(new UserCreatedEvent(
                userId.toString(),
                email.value(),
                personalInfo.firstName(),
                personalInfo.lastName(),
                personalInfo.documentNumber(),
                now
        ));

        return user;
    }

    public static User withId(UserId id, Email email, Password password, PersonalInfo info,
                              UserStatus status, boolean verified, List<UserDocument> docs,
                              LocalDateTime created, LocalDateTime updated) {
        return new User(id, email, password, info, status, verified, docs, created, updated);
    }

    public void addDocument(UserDocument document) {
        if (document.isExpired()) {
            throw new IllegalArgumentException("Cannot add expired document");
        }
        this.documents.add(document);
        this.updatedAt = LocalDateTime.now();
    }

    public void changePassword(Password newPassword) {
        if (this.password.equals(newPassword)) {
            throw new IllegalArgumentException("New password cannot be the same as old password");
        }
        this.password = newPassword;
        this.updatedAt = LocalDateTime.now();
    }

    public void verifyEmail() {
        this.isEmailVerified = true;
        this.updatedAt = LocalDateTime.now();
    }
}
