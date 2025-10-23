package ma.emsi.benatik.tp1_benatik.llm;

import jakarta.enterprise.context.Dependent;
import jakarta.ws.rs.client.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.Serializable;

/**
 * Gère l'interface avec l'API de Gemini.
 * Son rôle est essentiellement de lancer une requête à chaque nouvelle
 * question qu'on veut envoyer à l'API.
 *
 * De portée dependent pour réinitialiser la conversation à chaque fois que
 * l'instance qui l'utilise est renouvelée.
 * Par exemple, si l'instance qui l'utilise est de portée View, la conversation est
 * réunitialisée à chaque fois que l'utilisateur quitte la page en cours.
 */
@Dependent
public class LlmClientPourGemini implements Serializable {

    private final String key;
    private final Client clientRest;
    private final WebTarget target;

    public LlmClientPourGemini() {
        this.key = System.getenv("GEMINI_KEY");
        if (this.key == null || this.key.isBlank()) {
            throw new IllegalStateException("ERREUR : La variable d'environnement GEMINI_KEY est introuvable.");
        }

        this.clientRest = ClientBuilder.newClient();

        this.target = clientRest.target(
                "https://generativelanguage.googleapis.com/v1/models/gemini-2.5-flash:generateContent?key=" + key
        );
    }

    public Response envoyerRequete(Entity<String> requestEntity) {
        return target
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(requestEntity);
    }

    public void closeClient() {
        this.clientRest.close();
    }
}