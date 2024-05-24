package fidanhykolli.gjejpuneapp.services;

import fidanhykolli.gjejpuneapp.entities.Company;
import fidanhykolli.gjejpuneapp.entities.User;
import fidanhykolli.gjejpuneapp.exceptions.UnauthorizedException;
import fidanhykolli.gjejpuneapp.payloads.CompanyLoginRespDTO;
import fidanhykolli.gjejpuneapp.payloads.NewCompanyLoginDTO;
import fidanhykolli.gjejpuneapp.payloads.NewUserLoginDTO;
import fidanhykolli.gjejpuneapp.payloads.UserLoginRespDTO;
import fidanhykolli.gjejpuneapp.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    public UserServices userServices;

    @Autowired
    public CompanyServices companyServices;

    @Autowired
    public JWTTools jwtTools;

    @Autowired
    public PasswordEncoder bcrypt;


    public UserLoginRespDTO authenticateUserAndGenerateToken(NewUserLoginDTO payload){
        // Ottenere l'Optional<User> dall'email
        Optional<User> optionalUser = this.userServices.findByEmail(payload.email());

        // Verificare se l'utente è presente nell'Optional
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            // Verificare la corrispondenza della password
            if (bcrypt.matches(payload.password(), user.getPassword())) {
                // Creare e restituire il token JWT se le credenziali sono valide
                return new UserLoginRespDTO(jwtTools.createToken(user), user);

            } else {
                System.out.println(payload.password());
                System.out.println(user.getPassword());

                // Lanciare un'eccezione UnauthorizedException se le credenziali non sono valide
                throw new UnauthorizedException("Credenziali non valide! Impossibile accedere");
            }
        } else {
            // Gestire il caso in cui non ci sia alcun utente con l'email specificato
            throw new UnauthorizedException("Utente non trovato con l'email specificato");
        }
    }

    public CompanyLoginRespDTO authenticateCompanyAndGenerateToken(NewCompanyLoginDTO payload){
        Optional<Company> optionalCompany = this.companyServices.findByEmail(payload.email());

        if (optionalCompany.isPresent()){
          Company company = optionalCompany.get();
          if (bcrypt.matches(payload.password(), company.getPassword())){
             return new CompanyLoginRespDTO(jwtTools.createToken(company), company);
            } else {
              throw new UnauthorizedException("Credenziali non valide! Impossibile accedere");
          }
        } else {
            throw new UnauthorizedException("Company non trovata con l'email specificata");
        }
    }


}