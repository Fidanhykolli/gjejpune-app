package fidanhykolli.gjejpuneapp.payloads;


import fidanhykolli.gjejpuneapp.entities.User;

public record UserLoginRespDTO(String accessToken, User user) {
}