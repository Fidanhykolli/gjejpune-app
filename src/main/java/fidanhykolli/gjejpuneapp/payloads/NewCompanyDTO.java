package fidanhykolli.gjejpuneapp.payloads;

import fidanhykolli.gjejpuneapp.enums.CompanyType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record NewCompanyDTO (
        @NotEmpty(message = "È obbligatorio avere un nome")
        @Size(min = 3, max = 30, message = "Il nome deve essere compreso tra 3 e 30 caratteri")
        String companyName,

        @NotEmpty(message = "L'email è obbligatoria")
        @Email(message = "L'email inserita non è valida")
        String email,

        @NotEmpty(message = "La password è obbligatoria")
        String password,

        @NotEmpty(message = "La password è obbligatoria")
        String vatNumber,

        @NotEmpty(message = "L'indirizzo è obbligatorio")
        String address,

        @NotEmpty(message = "Il numero di telefono è obbligatorio")
        String phoneNumber,

        @NotEmpty(message = "Il business sector è obbligatorio")
        String businessSector,

        CompanyType role
) {}
