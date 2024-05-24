package fidanhykolli.gjejpuneapp.entities;


import fidanhykolli.gjejpuneapp.entities.Company;
import jakarta.persistence.*;


@Entity
@Table(name = "jobs")
public class Job {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private boolean experienceRequired;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    public Job() {
    }

    public Job(String title, String description, boolean experienceRequired) {
        this.title = title;
        this.description = description;
        this.experienceRequired = experienceRequired;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isExperienceRequired() {
        return experienceRequired;
    }

    public void setExperienceRequired(boolean experienceRequired) {
        this.experienceRequired = experienceRequired;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}