package ma.emsi.benatik.tp1_benatik.llm;

public record LlmInteraction(
        String questionJson,
        String reponseJson,
        String reponseExtraite
) {}