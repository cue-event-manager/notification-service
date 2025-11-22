package cue.edu.co.model.notification.events;

import cue.edu.co.model.role.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCreatedEvent{
        private Long id;
        private String firstName;
        private String lastName;
        private String email;
        private Role role;
        private LocalDate birthDate;

}
