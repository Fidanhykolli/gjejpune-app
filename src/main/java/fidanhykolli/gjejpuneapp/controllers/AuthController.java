package fidanhykolli.gjejpuneapp.controllers;

import fidanhykolli.gjejpuneapp.entities.Company;
import fidanhykolli.gjejpuneapp.entities.User;
import fidanhykolli.gjejpuneapp.exceptions.BadRequestException;
import fidanhykolli.gjejpuneapp.payloads.*;
import fidanhykolli.gjejpuneapp.services.AuthService;
import fidanhykolli.gjejpuneapp.services.CompanyServices;
import fidanhykolli.gjejpuneapp.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private UserServices userServices;

    @Autowired
    private CompanyServices companyServices;



    // POST http://localhost:3001/auth/user/login
    @PostMapping("/user/login")
    public UserLoginRespDTO userLogin(@RequestBody NewUserLoginDTO payload) {
        return this.authService.authenticateUserAndGenerateToken(payload);
    }

    // POST http://localhost:3001/auth/company/login
    @PostMapping("/company/login")
    public CompanyLoginRespDTO companyLogin(@RequestBody NewCompanyLoginDTO payload) {
        return this.authService.authenticateCompanyAndGenerateToken(payload);
    }

    // POST http://localhost:3001/auth/user/register
    @PostMapping("/user/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserLoginRespDTO registerUser(@RequestBody @Validated NewUserDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        User createdUser = this.userServices.createUser(body);
        return new UserLoginRespDTO("USER_ACCESS_TOKEN_HERE", createdUser);
    }

    // POST http://localhost:3001/auth/company/register
    @PostMapping("/company/register")
    @ResponseStatus(HttpStatus.CREATED)
    public CompanyLoginRespDTO registerCompany(@RequestBody @Validated NewCompanyDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        Company createdCompany = this.companyServices.createCompany(body);
        return new CompanyLoginRespDTO("COMPANY_ACCESS_TOKEN_HERE", createdCompany);
    }
}