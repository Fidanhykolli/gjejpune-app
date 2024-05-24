package fidanhykolli.gjejpuneapp.services;

import fidanhykolli.gjejpuneapp.DAO.CompanyDAO;
import fidanhykolli.gjejpuneapp.entities.Company;
import fidanhykolli.gjejpuneapp.entities.User;
import fidanhykolli.gjejpuneapp.enums.CompanyType;
import fidanhykolli.gjejpuneapp.exceptions.BadRequestException;
import fidanhykolli.gjejpuneapp.payloads.NewCompanyDTO;
import fidanhykolli.gjejpuneapp.payloads.NewUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyServices {
    @Autowired
    CompanyDAO companyDAO;
    @Autowired
    public PasswordEncoder passwordEncoder;

    public List<Company> getAllCompanies(){
        return companyDAO.findAll();
    }

    public Company getCompanyById(Integer id){
        return companyDAO.findById(id).get();
    }

    public Company getCompanyByName(String companyName) {
        List<Company> companies = companyDAO.findByCompanyName(companyName);
        if (!companies.isEmpty()) {
            return companies.get(0);
        } else {
            return null;
        }
    }


    public Company updateCompany(Integer id, Company updateCompany) {
        Optional<Company> existingAndUpdate = companyDAO.findById(id);
        if (existingAndUpdate.isPresent()) {
            Company existingCompany = existingAndUpdate.get();

            if (updateCompany.getCompanyName() != null) {
                existingCompany.setCompanyName(updateCompany.getCompanyName());
            }

            if(updateCompany.getAddress() != null){
                existingCompany.setAddress(updateCompany.getAddress());
            }

            if(updateCompany.getBusinessSector() != null) {
                existingCompany.setBusinessSector(updateCompany.getBusinessSector());
            }

            if(updateCompany.getPhoneNumber() != null){
                existingCompany.setPhoneNumber(updateCompany.getPhoneNumber());
            }

            if(updateCompany.getEmail() != null){
                existingCompany.setEmail(updateCompany.getEmail());
            }

            if(updateCompany.getVatNumber() != null){
                existingCompany.setVatNumber(updateCompany.getVatNumber());
            }

            if(updateCompany.getPassword() != null){
                existingCompany.setPassword(updateCompany.getPassword());
            }
            return companyDAO.save(existingCompany);
        } else {

            return null;
        }
    }

    public Company createCompany(NewCompanyDTO newCompanyDTO) {
        String email = newCompanyDTO.email();
        if (companyDAO.existsByEmail(email)) {
            throw new BadRequestException("Email " + email + " is already taken");
        }
        String encodedPassword = passwordEncoder.encode(newCompanyDTO.password());
        Company company = new Company(newCompanyDTO.companyName(),
                newCompanyDTO.address(), newCompanyDTO.vatNumber(),
                newCompanyDTO.businessSector(), newCompanyDTO.email(),
                newCompanyDTO.phoneNumber(), encodedPassword, newCompanyDTO.role());

        company = companyDAO.save(company);
        return company;
    }


    public Optional<Company> findByEmail(String email){
        return companyDAO.findByEmail(email);
    }

}
