package cue.edu.co.model.notification.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecoverPasswordEvent {
    private String email;
    private String name;
    private String recoveryCode;
    private String expirationTime;
}