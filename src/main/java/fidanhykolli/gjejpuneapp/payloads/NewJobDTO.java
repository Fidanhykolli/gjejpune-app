// NewJobDTO.java
package fidanhykolli.gjejpuneapp.payloads;

import jakarta.validation.constraints.NotEmpty;

public record NewJobDTO(
        @NotEmpty(message = "Il titolo del lavoro è obbligatorio")
        String title,

        @NotEmpty(message = "La descrizione del lavoro è obbligatoria")
        String description,

        boolean experienceRequired
) {}
