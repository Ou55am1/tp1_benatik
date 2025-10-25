# TP1 - Accès direct à l'API d'un LLM avec Jakarta EE

**Étudiant :** BENATIK Oussama  
**Numéro :** 6  
**Date :** Octobre 2025  
**Module :** Agents conversationnels en Java avec LangChain4j **[EMSI - UCA - I2A]**

---

## 📋 Table des matières

- [📖 Description du projet](#-description-du-projet)
- [🛠️ Technologies utilisées](#️-technologies-utilisées)
- [📁 Architecture](#-architecture)
- [🚀 Installation et déploiement](#-installation-et-déploiement)
- [✨ Fonctionnalités](#-fonctionnalités)
- [🔧 Explications techniques](#-explications-techniques)
- [📡 Intégration de l'API Gemini](#-intégration-de-lapi-gemini)
- [🐛 Mode Debug](#-mode-debug)
- [🎯 Exercice bonus](#-exercice-bonus)
- [📸 Captures d'écran](#-captures-décran)
- [🚧 Difficultés rencontrées](#-difficultés-rencontrées)
- [📄 Licence](#-licence)
- [✉️ Contact](#️-contact)

---

## 📖 Description du projet

Ce TP transforme l'application du TP0 en une véritable interface conversationnelle connectée à l'API Gemini de Google. L'utilisateur pose des questions et reçoit des réponses générées par un vrai LLM (Large Language Model). Un mode "debug" permet de visualiser les échanges JSON avec l'API.

**Évolution par rapport au TP0 :**
- ✅ Intégration de l'API Gemini via requêtes REST
- ✅ Manipulation de JSON pour les requêtes et réponses
- ✅ Mode debug pour visualiser les échanges
- ✅ Gestion des exceptions réseau
- ✅ Architecture en couches (UI → Backing Bean → JsonUtil → LlmClient)

**Objectifs pédagogiques :**
- Comprendre le fonctionnement des APIs REST
- Maîtriser la manipulation de JSON avec Jakarta JSON Processing
- Implémenter un client HTTP en Java
- Gérer la sécurité des clés API
- Appliquer les bonnes pratiques d'architecture logicielle

---

## 🛠️ Technologies utilisées

| Technologie | Version | Usage |
|-------------|---------|-------|
| **Java** | 21 | Langage de programmation |
| **Jakarta EE** | 10 | Framework d'entreprise |
| **JSF (Jakarta Faces)** | 4.0.1 | Interface utilisateur web |
| **CDI** | 4.0.1 | Injection de dépendances |
| **Jakarta JSON Processing** | 2.1.0 | Manipulation de JSON |
| **Jakarta REST Client** | 3.1.0 | Client HTTP pour API REST |
| **PrimeFaces** | 15.0.7 | Composants UI avancés |
| **Maven** | 3.9.9 | Gestion de projet |
| **Payara Server** | 6.2025.9 | Serveur d'application |
| **Gemini API** | 2.5 | LLM de Google |
| **Git/GitHub** | - | Versionnement |

---

## 📁 Architecture

```
tp1_jakartaee/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── ma/emsi/benatik/tp1_jakartaee/
│   │   │       ├── jsf/
│   │   │       │   ├── Bb.java                    # Backing bean principal
│   │   │       │   └── CharsetFilter.java         # Filtre UTF-8
│   │   │       ├── llm/
│   │   │       │   ├── JsonUtilPourGemini.java    # Gestion JSON Gemini
│   │   │       │   ├── LlmClientPourGemini.java   # Client API Gemini
│   │   │       │   ├── LlmInteraction.java        # Record de données
│   │   │       │   └── RequeteException.java      # Exception personnalisée
│   │   ├── resources/
│   │   │   └── META-INF/
│   │   │       ├── beans.xml
│   │   │       └── persistence.xml
│   │   └── webapp/
│   │       ├── WEB-INF/
│   │       │   └── web.xml
│   │       ├── resources/
│   │       │   ├── css/
│   │       │   │   └── mycsslayout.css
│   │       │   └── js/
│   │       │       └── script.js
│   │       └── index.xhtml                        # Page JSF avec mode debug
│   └── test/
├── pom.xml
└── README.md
```

---

## 🚀 Installation et déploiement

### **Prérequis**

- JDK 17+
- Maven 3.6+
- Payara Server 6.2025.9
- Git
- Compte Google AI Studio (pour la clé API Gemini)

### **Étapes d'installation**

#### **1. Obtenir une clé API Gemini**

1. Visitez [Google AI Studio](https://makersuite.google.com/app/apikey)
2. Créez une nouvelle clé API
3. Copiez la clé générée

#### **2. Configurer la variable d'environnement**

**Windows :**
```cmd
setx GEMINI_KEY "votre_clé_api_ici"
```

**Linux/Mac :**
```bash
export GEMINI_KEY="votre_clé_api_ici"
# Ajoutez cette ligne dans ~/.bashrc ou ~/.zshrc pour la rendre permanente
```

**Vérification :**
```bash
echo %GEMINI_KEY%    # Windows
echo $GEMINI_KEY     # Linux/Mac
```

#### **3. Cloner et compiler le projet**

```bash
git clone https://github.com/Ou55am1/tp1_benatik.git
cd tp1_jakartaee
mvn clean package
```

#### **4. Déployer sur Payara**

```bash
cd [PAYARA_HOME]/glassfish/bin
asadmin start-domain domain1
asadmin --port 4849 deploy --force=true "[CHEMIN_COMPLET]/tp1_jakartaee/target/tp1_jakartaee.war"
```

#### **5. Accéder à l'application**

```
http://localhost:9090/tp1_benatik/
```

---

## ✨ Fonctionnalités

### **Fonctionnalités principales**

- ✅ **Sélection du rôle système** (Assistant, Traducteur, Guide touristique)
- ✅ **Conversation avec Gemini AI** en temps réel
- ✅ **Historique de conversation** envoyé à chaque requête
- ✅ **Mode Debug** pour visualiser les échanges JSON
- ✅ **Gestion des erreurs** avec messages utilisateur clairs
- ✅ **Encodage UTF-8** pour tous les caractères
- ✅ **Boutons de copie rapide** pour toutes les zones
- ✅ **Nouveau chat** pour réinitialiser la conversation

### **Rôles système disponibles**

| Rôle | Description | Exemple d'usage |
|------|-------------|-----------------|
| **Assistant** | Aide générale et polyvalente | "Explique-moi la photosynthèse" |
| **Traducteur EN-FR** | Traduction bidirectionnelle | "Translate: Hello World" |
| **Guide touristique** | Conseils de voyage | "Que visiter à Casablanca ?" |
| **Assistant comique** 🎭 | Réponses humoristiques et divertissantes | "C'est quoi un ordinateur ?" |

---

## 🔧 Explications techniques

### **1. Architecture en couches**

L'application suit une architecture séparée en responsabilités :

```
Interface JSF (index.xhtml)
        ↓
Backing Bean (Bb.java)
        ↓
JsonUtil (JsonUtilPourGemini.java)
        ↓
LlmClient (LlmClientPourGemini.java)
        ↓
API Gemini (REST)
```

**Avantages :**
- Séparation des préoccupations
- Facilite le changement d'API (OpenAI, Claude, etc.)
- Code testable et maintenable

---

### **2. Le Record LlmInteraction**

Introduit en Java 16, le record simplifie la création de classes de données immuables.

**Définition :**
```java
public record LlmInteraction(
    String questionJson,      // JSON de la requête
    String reponseJson,       // JSON de la réponse
    String reponseExtraite    // Texte extrait pour l'utilisateur
) {}
```

**Utilisation dans le backing bean :**
```java
LlmInteraction interaction = jsonUtil.envoyerRequete(question);
this.reponse = interaction.reponseExtraite();
this.texteRequeteJson = interaction.questionJson();
this.texteReponseJson = interaction.reponseJson();
```


---

### **3. Gestion du JSON avec Jakarta JSON Processing**

**Création d'une requête JSON :**
```java
private String creerRequeteJson(String systemRole, String question) {
    JsonArray contents = Json.createArrayBuilder()
            .add(Json.createObjectBuilder()
                    .add("role", "user")
                    .add("parts", Json.createArrayBuilder()
                            .add(Json.createObjectBuilder()
                                    .add("text", systemRole + "\n" + question))))
            .build();

    this.requeteJson = Json.createObjectBuilder()
            .add("contents", contents)
            .build();

    return this.requeteJson.toString();
}

```

**Extraction de la réponse :**
```java
private String extractReponse(String json) {
    try (JsonReader jsonReader = Json.createReader(new StringReader(json))) {
        JsonObject jsonObject = jsonReader.readObject();
        JsonObject content = jsonObject
                .getJsonArray("candidates")
                .getJsonObject(0)
                .getJsonObject("content");

        // Ajouter la réponse dans l'historique JSON (conversation)
        this.requeteJson = this.pointer.add(this.requeteJson, content);

        // Extraire le texte de la réponse
        return content.getJsonArray("parts").getJsonObject(0).getString("text");
    }
}

```

---

### **4. Sécurité de la clé API**

**❌ Mauvaise pratique :**
```java
String apiKey = "AIzaSyD...XYZ123"; // Hardcodé dans le code
```

**✅ Bonne pratique :**
```java
String apiKey = System.getenv("GEMINI_KEY"); // Variable d'environnement
if (apiKey == null || apiKey.isEmpty()) {
    throw new IllegalStateException("GEMINI_KEY non définie");
}
```

**Pourquoi ?**
- Évite l'exposition de la clé dans le code source
- Permet différentes clés par environnement (dev, prod)
- Empêche la publication accidentelle sur GitHub

---

## 📡 Intégration de l'API Gemini

### **Endpoint utilisé**

```
POST https://generativelanguage.googleapis.com/v1/models/gemini-2.5-flash:generateContent?key={API_KEY}
```

### **Format de la requête**

```json
{
  "contents": [
    {
      "role": "user",
      "parts": [{"text": "Votre système est: Assistant"}]
    },
    {
      "role": "user",
      "parts": [{"text": "Question de l'utilisateur"}]
    }
  ]
}
```

### **Format de la réponse**

```json
{
  "candidates": [
    {
      "content": {
        "parts": [
          {
            "text": "Réponse de Gemini..."
          }
        ]
      }
    }
  ]
}
```

### **Gestion de l'historique**

À chaque nouvelle question, **tout l'historique** est renvoyé à l'API pour maintenir le contexte :

```java
// 1ère question
contents: [
  {role: "user", parts: [{"text": "Rôle système"}]},
  {role: "user", parts: [{"text": "Question 1"}]}
]

// 2ème question
contents: [
  {role: "user", parts: [{"text": "Rôle système"}]},
  {role: "user", parts: [{"text": "Question 1"}]},
  {role: "model", parts: [{"text": "Réponse 1"}]},
  {role: "user", parts: [{"text": "Question 2"}]}
]
```

---

## 🐛 Mode Debug

Le mode debug affiche deux zones supplémentaires :

### **1. JSON de la requête**
Montre exactement ce qui est envoyé à Gemini :
- Structure du JSON
- Historique complet de la conversation
- Rôle système

### **2. JSON de la réponse**
Affiche la réponse brute de l'API :
- Métadonnées de la génération
- Tokens utilisés
- Texte généré

**Code du bouton debug :**
```xhtml
<h:commandButton id="debugbutton"
                 value="#{bb.debug ? 'Mode Normal' : 'Mode Debug'}"
                 action="#{bb.toggleDebug()}"
                 style="margin-top:10px; background-color:#1976d2; color:white; border:none; padding:8px 16px; border-radius:6px;" />

```

**Méthode dans le backing bean :**
```java
public void toggleDebug() {
    this.setDebug(!isDebug());
}
```

---

## 🎯 Exercice bonus

### **Rôle système personnalisé : Assistant Comique 🎭**

**Description du rôle :**
> "You are a humorous assistant. You always answer with a funny tone, making jokes or puns, while still giving correct and informative answers. Even for serious topics, keep your responses light, witty, and entertaining."

**Objectif :** Rendre l'apprentissage plus amusant en combinant informations précises et humour. Même les sujets sérieux sont abordés avec légèreté et esprit.

**Conversation exemple :**

```
👤 Utilisateur : C'est quoi un ordinateur ?

🤖 Assistant Comique : 
Ah, un ordinateur ! C'est cette boîte magique qui transforme votre café en code... 
enfin presque ! ☕💻

Plus sérieusement (mais pas trop), un ordinateur c'est comme un chef cuisinier 
électronique : il prend des ingrédients (données), suit des recettes (programmes), 
et vous sert des plats (résultats). Sauf qu'au lieu de rissolé, tout est en binaire ! 

Les composants principaux :
🧠 Le processeur (CPU) : le cerveau qui réfléchit à 3 milliards de fois par seconde 
   (impressionnant, mais il ne sait toujours pas où sont vos clés)
💾 La mémoire RAM : la mémoire à court terme (comme la vôtre avant le café du matin)
💿 Le disque dur : la bibliothèque où dorment vos photos de vacances de 2015
🎨 La carte graphique : l'artiste qui dessine vos mèmes préférés

Fun fact : Les premiers ordinateurs pesaient plusieurs tonnes et avaient moins de 
puissance que votre grille-pain connecté ! 🍞 

Donc voilà, un ordinateur = calculatrice sous stéroïdes qui peut aussi regarder 
des vidéos de chats ! 🐱✨
```

```
👤 Utilisateur : Comment fonctionne Internet ?

🤖 Assistant Comique :
Internet, c'est comme un gigantesque jeu de téléphone arabe, mais avec moins 
d'erreurs... enfin en théorie ! 📞🌐

Imaginez des milliards d'ordinateurs qui se passent des petits mots (des paquets 
de données) à la vitesse de la lumière. C'est la plus grande chaîne de potins 
du monde, et elle ne dort jamais ! 

Le fonctionnement en 3 étapes rigolotes :
1️⃣ Vous tapez "vidéos de chats qui dansent" 🐱💃
2️⃣ Votre requête voyage à travers des câbles sous-marins (oui, sous l'océan, 
   même les poissons ont le wifi maintenant ! 🐠)
3️⃣ Des serveurs loin loin très loin vous envoient les précieuses vidéos

Techniquement :
• Protocole TCP/IP : le langage universel (plus utilisé que l'espéranto !)
• Routeurs : les facteurs qui ne se trompent (presque) jamais d'adresse
• DNS : l'annuaire téléphonique qui traduit "google.com" en "172.217...."
• HTTP/HTTPS : le protocole qui fait transiter vos mèmes en toute sécurité 🔒

Anecdote : Le premier message envoyé sur Internet était "LO" au lieu de "LOGIN" 
car le système a planté. Même en 1969, les bugs existaient déjà ! 🐛😂

Bref, Internet = bibliothèque universelle + réseau social + boutique en ligne + 
distributeur de mèmes, le tout dans une boîte magique ! ✨📦
```

**JSON de la dernière requête (2ème question) :**

```json
{
  "contents": [
    {
      "role": "user",
      "parts": [
        {
          "text": "You are a humorous assistant. You always answer with a funny tone, making jokes or puns, while still giving correct and informative answers. Even for serious topics, keep your responses light, witty, and entertaining."
        }
      ]
    },
    {
      "role": "user",
      "parts": [
        {
          "text": "C'est quoi un ordinateur ?"
        }
      ]
    },
    {
      "role": "model",
      "parts": [
        {
          "text": "Ah, un ordinateur ! C'est cette boîte magique qui transforme votre café en code... enfin presque ! ☕💻\n\nPlus sérieusement (mais pas trop)..."
        }
      ]
    },
    {
      "role": "user",
      "parts": [
        {
          "text": "Comment fonctionne Internet ?"
        }
      ]
    }
  ]
}
```

**Explication détaillée du JSON :**

| Élément | Rôle | Description |
|---------|------|-------------|
| **1er objet** | `"role": "user"` | 🎯 **Rôle système** : Définit le comportement de l'assistant (ton humoristique) |
| **2ème objet** | `"role": "user"` | ❓ **Question 1** : "C'est quoi un ordinateur ?" |
| **3ème objet** | `"role": "model"` | 💬 **Réponse 1** : Réponse humoristique de Gemini à la 1ère question |
| **4ème objet** | `"role": "user"` | ❓ **Question 2** : "Comment fonctionne Internet ?" |

**Points clés :**
1. **Rôle système (1er objet)** : Envoyé une seule fois au début, il conditionne **toutes** les réponses suivantes
2. **Historique complet** : L'API reçoit les 2 questions + 1 réponse pour maintenir le contexte
3. **Structure "role" + "parts"** : 
   - `"role"` peut être `"user"` (humain) ou `"model"` (IA)
   - `"parts"` contient un tableau avec le texte effectif
4. **Conservation du ton** : Grâce à l'historique, Gemini garde le ton comique établi par le rôle système

**Avantage pédagogique :**
L'assistant comique rend l'apprentissage plus engageant tout en délivrant des informations techniques précises. Il démontre qu'on peut vulgariser des concepts complexes avec humour sans perdre en exactitude ! 🎓😄

---

## 📸 Captures d'écran

### **Interface principale avec conversation**

![Conversation avec Gemini](https://github.com/user-attachments/assets/5a318fe5-cd56-405c-b215-2efb7b869640)
*Exemple de conversation avec le rôle "Assistant"*

### **Mode Debug activé**

![Mode Debug](https://github.com/user-attachments/assets/8cd97477-89d4-457e-9ffb-7c76fe0eb18f)
*Visualisation des échanges JSON avec l'API*

### **Sélection des rôles système disponibles**

![Rôles système disponibles](https://github.com/user-attachments/assets/2c989ac8-7b1d-405d-addb-234e1e0c730b)
*Liste déroulante montrant les 4 rôles : Assistant, Traducteur, Guide touristique et Assistant comique*

### **Rôle personnalisé - Assistant Comique 🎭**

![Assistant Comique en action](https://github.com/user-attachments/assets/b8bfda54-23e3-413d-bb6f-6faf2c5c64f6)
*Exemple de réponse humoristique : "C'est quoi un ordinateur ?"*

---

## 🚧 Difficultés rencontrées

### **1. Manipulation du JSON**

**Problème :** Difficulté à naviguer dans la structure JSON imbriquée de la réponse Gemini.

**Solution :** 
- Utilisation du mode debug pour visualiser la structure
- Test avec Postman pour comprendre l'API
- Documentation de l'API Gemini très utile

---

### **2. Variables d'environnement**

**Problème :** La clé API n'était pas reconnue au démarrage de Payara.

**Solution :** 
- Redémarrage complet de l'IDE après définition de la variable
- Vérification avec `System.getenv()` dans le constructeur
- Ajout d'un message d'erreur explicite si la clé est absente

---

## 🎓 Comparaison avec le TP0

| Aspect | TP0 | TP1 |
|--------|-----|-----|
| **Réponse** | Texte inversé (miroir) | Réponse d'un vrai LLM |
| **Traitement** | Local (backing bean) | API externe (Gemini) |
| **Format** | Java natif | JSON (REST) |
| **Complexité** | Simple | Moyenne |
| **Architecture** | 2 couches | 4 couches |
| **Réseau** | Non | Oui (HTTP) |
| **Gestion erreurs** | Validation basique | Exceptions réseau + API |

---

## 📚 Ressources utiles

- [Documentation API Gemini](https://ai.google.dev/gemini-api/docs)
- [Jakarta JSON Processing](https://jakarta.ee/specifications/jsonp/)
- [Records Java](https://docs.oracle.com/en/java/javase/17/language/records.html)
- [REST Client Jakarta](https://jakarta.ee/specifications/restful-ws/)
- [Bonnes pratiques sécurité API](https://owasp.org/www-project-api-security/)
- [Supports de cours (M. Richard Grin)](http://richard.grin.free.fr/emsi/casablanca-ia/supports/index.html)

---

## 📄 Licence

Ce projet est réalisé dans un cadre pédagogique à l'EMSI.

---

## ✉️ Contact

**Étudiant :** BENATIK Oussama  
**Email :** Oussama.Benatik@emsi-edu.ma / benatik.oussama@gmail.com  
**GitHub :** [https://github.com/Ou55am1](https://github.com/Ou55am1)  
**LinkedIn :** [https://www.linkedin.com/in/oussama-benatik](https://www.linkedin.com/in/oussama-benatik-2464aa337)

---

**Date de remise :** Octobre 2025  
**Status :** ✅ Terminé

---

## 🔗 Liens vers les TPs

- **TP0 (précédent) :** [https://github.com/Ou55am1/tp0_benatik](https://github.com/Ou55am1/tp0_benatik)
- **TP1 (actuel) :** [https://github.com/Ou55am1/tp1_benatik](https://github.com/Ou55am1/tp1_benatik)
