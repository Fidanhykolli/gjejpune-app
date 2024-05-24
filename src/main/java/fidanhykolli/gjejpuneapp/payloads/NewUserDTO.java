package fidanhykolli.gjejpuneapp.payloads;

import fidanhykolli.gjejpuneapp.enums.UserType;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

public record NewUserDTO(


        @NotEmpty(message = "È obbligatorio avere un nome")
        @Size(min = 3, max = 30, message = "Il nome deve essere compreso tra 3 e 30 caratteri")
        String name,

        @NotEmpty(message = "Il cognome è obbligatorio")
        @Size(min = 3, max = 30, message = "Il cognome deve essere compreso tra 3 e 30 caratteri")
        String surname,

        @NotEmpty(message = "L'email è obbligatoria")
        @Email(message = "L'email inserita non è valida")
        String email,

        @NotEmpty(message = "La password è obbligatoria")
        String password,

        boolean hasWorkVisa,

        Date dateOfBirth,

        @NotEmpty(message = "L'indirizzo è obbligatorio")
        String address,

        @NotEmpty(message = "Il numero di telefono è obbligatorio")
        String phoneNumber,

        String workExperience,

        String spokenLanguage,

        UserType role
) {
        @AssertTrue(message = "Devi avere almeno 16 anni")
        public boolean isAgeValid() {
                if (dateOfBirth == null) {
                        return false;
                }
                LocalDate dob = dateOfBirth.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate now = LocalDate.now();
                return Period.between(dob, now).getYears() >= 16;
        }
}
