# LinguaTune

## Project Description

LinguaTune is a language learning platform that helps users study and practice languages through music. Users can explore songs in different languages, access translations, create flashcards, and more.

## User Stories

- [User Story 1]([user-story-link-1](https://onedrive.live.com/redir?resid=18AC64F45B3A313A%211034&page=Edit&wd=target%28Untitled%20Section.one%7Cafd66f8e-0fdb-4ef0-a139-d94c86c14c0a%2FLinguaTune%20Concept%20and%20User%20Stories%7C6abc0c83-c308-4bf6-9efb-be04058843b0%2F%29&wdorigin=703)): As a language enthusiast, I want to search for songs in various languages and learn their lyrics and translations.
- [User Story 2]([user-story-link-2](https://onedrive.live.com/redir?resid=18AC64F45B3A313A%211034&page=Edit&wd=target%28Untitled%20Section.one%7Cafd66f8e-0fdb-4ef0-a139-d94c86c14c0a%2FLinguaTune%20Concept%20and%20User%20Stories%7C6abc0c83-c308-4bf6-9efb-be04058843b0%2F%29&wdorigin=703)): As a registered user, I want to create and manage my flashcards to memorize vocabulary from my favorite songs.

## ERD Diagram
![LinguaTune ERD(crow's foot)](https://github.com/GabrielleHandy/LinguaTune/assets/91081738/3ac670b9-8917-4336-af5a-19166ad4bcc8)



## Project Management

[Link to Project Management Tool](https://trello.com/invite/b/0kvNtG3d/ATTI0a35670936bb1c0ce2ac6e2a0a81947994F26865/linguatune-backend)

## Tools and Technologies Used

## Dependencies

### Spring Boot Dependencies
- `org.springframework.boot:spring-boot-starter-web`
- `org.springframework.boot:spring-boot-starter-security`
- `org.springframework.boot:spring-boot-starter-test` (scope: test)
- `org.springframework.security:spring-security-test` (scope: test)
- `org.springframework.boot:spring-boot-devtools` (scope: runtime, optional)

### Database Dependencies
- `org.springframework.boot:spring-boot-starter-data-jpa`
- `com.h2database:h2` (scope: runtime)
- `com.vaadin.external.google:android-json:0.0.20131108.vaadin1`

### Testing Dependencies
- `org.mockito:mockito-core:4.10.0` (scope: test)
- `junit:junit` (scope: test)
- `org.mockito:mockito-core:5.4.0` (scope: test)

### JSON Web Token (JWT) Dependencies
- `io.jsonwebtoken:jjwt-api:0.11.5`
- `io.jsonwebtoken:jjwt-impl:0.11.5` (scope: runtime)
- `io.jsonwebtoken:jjwt-jackson:0.11.5` (scope: runtime)


## Installation Guide

To run LinguaTune on your local machine, follow these steps:

1. Clone the repository: `git clone https://github.com/your/repo.git`
2. Navigate to the project directory: `cd linguatune`
3. Install dependencies: `npm install`
4. Start the Angular application [link to frontend](https://github.com/GabrielleHandy/LinguaTune-Front): `ng serve`
5. Start the Spring Boot server: `mvn spring-boot:run`
6. Access the application in your browser at `http://localhost:4200`

## API Endpoints

<details>
<summary><strong>Authentication Endpoints</strong></summary>

| Endpoint                        | Description                      |
|---------------------------------|----------------------------------|
| GET /auth/users/email/{email}   | Get user by email                |
| POST /auth/users/login          | Log in a user                    |
| POST /auth/users/register       | Register a new user              |
| PUT /auth/users/{id}            | Update user information by ID    |
| DELETE /auth/users/{id}         | Delete user by ID                |

</details>

<details>
<summary><strong>Translation Endpoints</strong></summary>

| Endpoint                        | Description                      |
|---------------------------------|----------------------------------|
| GET /api/translations/song/{id} | Get translation by song ID       |
| GET /api/translations/{id}      | Get translation by ID            |

</details>

<details>
<summary><strong>Study Pages Endpoints</strong></summary>

| Endpoint                        | Description                      |
|---------------------------------|----------------------------------|
| GET /api/studypages/            | Get user's study pages           |
| POST /api/studypages/create/French | Create a French study page     |
| POST /api/studypages/create/Spanish | Create a Spanish study page   |
| GET /api/studypages/{id}        | Get study page by ID             |
| DELETE /api/studypages/{id}     | Delete study page by ID          |

</details>

<details>
<summary><strong>Song Endpoints</strong></summary>

| Endpoint                        | Description                      |
|---------------------------------|----------------------------------|
| GET /api/songs/French           | Get French songs                  |
| GET /api/songs/Spanish          | Get Spanish songs                 |
| GET /api/songs/artist/{artist}  | Get songs by artist               |
| GET /api/songs/{id}             | Get song by ID                    |

</details>

<details>
<summary><strong>Language Endpoints</strong></summary>

| Endpoint                        | Description                      |
|---------------------------------|----------------------------------|
| GET /api/languages/             | Get all languages                 |
| GET /api/languages/{id}         | Get language by ID                |

</details>

<details>
<summary><strong>Flash Card Stack Endpoints</strong></summary>

| Endpoint                        | Description                      |
|---------------------------------|----------------------------------|
| POST /api/stacks/create/{studyPageId} | Create a flashcard stack      |
| DELETE /api/stacks/delete/{stackId} | Delete stack by ID              |
| GET /api/stacks/studypage/{studyPageId} | Get flashcard stacks for a study page |
| GET /api/stacks/{id}            | Get stack by ID                   |

</details>

<details>
<summary><strong>Flash Card Endpoints</strong></summary>

| Endpoint                        | Description                      |
|---------------------------------|----------------------------------|
| DELETE /api/flashcards/delete/{cardId} | Delete flashcard by ID      |
| POST /api/flashcards/song/{songId}/create/{stackId} | Create flashcards for a song and a stack |
| GET /api/flashcards/stack/{id}   | Get flashcards by stack ID        |

</details>

## Video Demo



[LinguaTune Demo](https://github.com/GabrielleHandy/LinguaTune/assets/91081738/3dbcb1d7-9720-4cc7-9291-f71bf0549efd)




## Challenges Faced

During the development of LinguaTune, I faced several challenges, including:

- Using MockMvc for the controllers
- Managing translations for songs from various sources.
- Time Management for an overly ambitious project.

## Key Achievements

Some of the key achievements of the LinguaTune project include:

- Building a user-friendly language learning platform.
- Enabling users to create and manage their flashcards.
- Integrating music and language learning in an innovative way.

## Acknowledgments & 3rd Party Resources Used

I'd like to acknowledge the following resources and libraries that helped us in building LinguaTune:
My amazing teachers whose knowledge made this possible!
[Suresh Sigera](https://git.generalassemb.ly/sureshmelvinsigera)
[Leo Rodriguez](https://git.generalassemb.ly/Leonardo)
[Dhruhbo Chowdhury](https://git.generalassemb.ly/dhrubo-chowdhury)

- [Bootstrap](https://getbootstrap.com): Used for styling the user interface.
- [Angular](https://angular.io): Framework for building the frontend.
- [Spring Boot](https://spring.io/projects/spring-boot): Framework for building the backend.




### Gabrielle Handy


[<img src="https://github.com/GabrielleHandy/LinguaTune/assets/91081738/bc9dbf65-eebf-46c1-bcbb-39533196ec28" height= "100" style="margin-bottom:-19px">](https://github.com/GabrielleHandy)


