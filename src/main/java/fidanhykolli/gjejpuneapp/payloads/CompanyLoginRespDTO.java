package fidanhykolli.gjejpuneapp.payloads;

import fidanhykolli.gjejpuneapp.entities.Company;

public record CompanyLoginRespDTO(String accessToken, Company company) {
}
