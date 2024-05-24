package fidanhykolli.gjejpuneapp.email;

import fidanhykolli.gjejpuneapp.entities.Company;
import fidanhykolli.gjejpuneapp.entities.User;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class EmailSender {

    private String apiKey;
    private String domainName;

    public EmailSender(@Value("${mailgun.apiKey}") String apiKey, @Value("${mailgun.domainName}") String domainName){
        this.apiKey=apiKey;
        this.domainName=domainName;
    }

    public void sendRegistrationEmail(User recipient){
        HttpResponse<JsonNode> response =  Unirest.post("https://api.mailgun.net/v3/" + this.domainName + "/messages")
                .basicAuth("api", this.apiKey)
                .queryString("from","fidan-323@live.com")
                .queryString("to",recipient.getEmail())
                .queryString("subject", "Successfully registered!")
                .queryString("text","Thanks" + recipient.getEmail() + "for your registration! ")
                .asJson();
        System.out.println(response);
    }
}