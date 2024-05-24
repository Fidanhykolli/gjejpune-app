package fidanhykolli.gjejpuneapp.services;

import fidanhykolli.gjejpuneapp.DAO.JobDAO;
import fidanhykolli.gjejpuneapp.entities.Company;
import fidanhykolli.gjejpuneapp.entities.Job;
import fidanhykolli.gjejpuneapp.payloads.NewJobDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobService {

    @Autowired
    private JobDAO jobdao;

    public List<Job> getAllJobs() {
        return jobdao.findAll();
    }

    public List<Job> findJobsByTitle(String title) {
        return jobdao.findJobsByTitle(title);
    }

    public Job createJob(NewJobDTO newJobDTO, Long companyId) {
        Company currentCompany = (Company) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!currentCompany.getId().equals(companyId)) {
            throw new IllegalArgumentException("Unauthorized: Company ID does not match authenticated company.");
        }
        Job job = new Job(newJobDTO.title(), newJobDTO.description(), newJobDTO.experienceRequired());
        job.setCompany(currentCompany);
        return jobdao.save(job);
    }

    public Job updateJob(Long id, NewJobDTO newJobDTO, Long companyId) {
        // Otteniamo l'oggetto Company dall'Authentication
        Company currentCompany = (Company) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Verifichiamo se l'ID della compagnia fornito corrisponde all'ID della compagnia autenticata
        if (!currentCompany.getId().equals(companyId)) {
            throw new IllegalArgumentException("Unauthorized: Company ID does not match authenticated company.");
        }

        // Cerchiamo il lavoro dal repository
        Optional<Job> optionalJob = jobdao.findById(id);

        if (optionalJob.isPresent()) {
            Job existingJob = optionalJob.get();
            // Verifichiamo che il lavoro appartenga alla compania autenticata
            if (existingJob.getCompany().getId().equals(currentCompany.getId())) {
                // Aggiorniamo i dettagli del lavoro con i nuovi valori
                existingJob.setTitle(newJobDTO.title());
                existingJob.setDescription(newJobDTO.description());
                existingJob.setExperienceRequired(newJobDTO.experienceRequired());
                // Salviamo e restituiamo il lavoro aggiornato
                return jobdao.save(existingJob);
            }
        }
        return null;
    }


    public void deleteJob(Long id, Long companyId) {
        Company currentCompany = (Company) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!currentCompany.getId().equals(companyId)) {
            throw new IllegalArgumentException("Unauthorized: Company ID does not match authenticated company.");
        }
        Optional<Job> optionalJob = jobdao.findById(id);
        if (optionalJob.isPresent()) {
            Job existingJob = optionalJob.get();
            if (existingJob.getCompany().getId().equals(currentCompany.getId())) {
                jobdao.deleteById(id);
            }
        }
    }
}
