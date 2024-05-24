package fidanhykolli.gjejpuneapp.controllers;

import fidanhykolli.gjejpuneapp.entities.Company;
import fidanhykolli.gjejpuneapp.entities.Job;
import fidanhykolli.gjejpuneapp.payloads.NewJobDTO;
import fidanhykolli.gjejpuneapp.services.CompanyServices;
import fidanhykolli.gjejpuneapp.services.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/jobs")
public class JobController {
    @Autowired
    CompanyServices companyServices;

    @Autowired
    private JobService jobService;

    @GetMapping
    public List<Job> getAllJobs() {
        return jobService.getAllJobs();
    }

    @GetMapping("/{title}")
    public List<Job> findJobsByTitle(@PathVariable String title) {
        return jobService.findJobsByTitle(title);
    }

        @Secured("Company")
    @PostMapping
    public Job createJob(@RequestBody NewJobDTO newJobDTO, Principal principal) {
        String email = principal.getName();
        Optional<Company> optionalCompany = companyServices.findByEmail(email);
        if (optionalCompany.isPresent()) {
            Company company = optionalCompany.get();
            Job job = jobService.createJob(newJobDTO, company.getId());
            return job;
        } else {
            throw new RuntimeException("Company not found");
        }
    }

    @Secured("Company")
    @PutMapping("/{id}")
    public Job updateJob(@PathVariable Long id, @RequestBody NewJobDTO newJobDTO, Principal principal) {
        String email = principal.getName();
        Optional<Company> optionalCompany = companyServices.findByEmail(email);
        if (optionalCompany.isPresent()) {
            Company company = optionalCompany.get();
            Long companyId = company.getId();
            Job job = jobService.updateJob(id, newJobDTO, companyId);
            return job;
        } else {
            throw new RuntimeException("Company not found");
        }
    }


    @Secured("Company")
    @DeleteMapping("/{id}")
    public void deleteJob(@PathVariable Long id) {
        // Otteniamo l'oggetto Company dall'Authentication
        Company company = (Company) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long companyId = company.getId();

        // Chiamiamo il servizio per eliminare il lavoro
        jobService.deleteJob(id, companyId);
    }
}