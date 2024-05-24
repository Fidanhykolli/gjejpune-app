package fidanhykolli.gjejpuneapp.controllers;

import fidanhykolli.gjejpuneapp.entities.Company;
import fidanhykolli.gjejpuneapp.payloads.NewCompanyDTO;
import fidanhykolli.gjejpuneapp.payloads.NewUserDTO;
import fidanhykolli.gjejpuneapp.services.CompanyServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/company")
public class CompanyController {
    @Autowired
    CompanyServices companyServices;

    @GetMapping
    public List<Company> getAllCompanies(){
        return companyServices.getAllCompanies();
    }

    @GetMapping("/{companyName}")
    public Company getCompanyByName(@PathVariable String name){
        return companyServices.getCompanyByName(name);
    }

    @PostMapping
    public Company createCompany(@RequestBody NewCompanyDTO body){
        return companyServices.createCompany(body);
    }

    @PutMapping("/{id}")
    public Company updateCompany(@PathVariable Integer id, @RequestBody Company body){
      return companyServices.updateCompany(id, body);
    }
}