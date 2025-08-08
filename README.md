ChibchaWeb - Backend
ChibchaWeb es un backend desarrollado en Spring Boot que implementa múltiples servicios como autenticación JWT con Redis, envío de correos electrónicos mediante SMTP, verificación de reCAPTCHA y lógica de negocio personalizada para una empresa que ofrece servicios de hosting. Su diseño sigue principios de arquitectura limpia y patrones de diseño para garantizar escalabilidad, mantenibilidad y seguridad.

📂 Estructura del proyecto
La organización del código sigue una arquitectura en capas, separando claramente responsabilidades:

config → Configuraciones globales.

controller → Controladores REST que gestionan las peticiones HTTP.

dto → Objetos de transferencia de datos para comunicación entre capas.

entity → Entidades JPA que representan las tablas en la base de datos.

repository → Interfaces de acceso a datos usando Spring Data JPA.

security → Configuración de seguridad y lógica de autenticación/autorización.

service → Implementación de la lógica de negocio.

util → Funciones y utilidades reutilizables.

🛠️ Arquitectura y patrones de diseño
El proyecto sigue una arquitectura en capas e implementa los siguientes patrones:

DAO y Repository: Acceso a datos desacoplado con Spring Data JPA.

DTO: Transferencia segura y optimizada de datos.

Singleton: Beans gestionados por Spring con scope único por contexto.

Builder: Creación de objetos complejos con Lombok (@Builder).

Inyección de Dependencias: A través de anotaciones de Spring como @Autowired, @Service, @Component.

Strategy: Elección dinámica de implementaciones según la lógica requerida.

Factory Method: Creación encapsulada de objetos usando métodos @Bean en clases de configuración.

🚀 Servicios implementados
Autenticación y autorización JWT con gestión de sesiones en Redis (Upstash).

Envío de correos electrónicos vía SMTP de Gmail.

Validación de usuarios con Google reCAPTCHA.

Lógica de negocio personalizada para la aplicación.

📋 Requisitos
Java 17+

Maven 3.8+

PostgreSQL

Redis (Upstash o local)

Cuenta de Gmail con credenciales SMTP

Clave de Google reCAPTCHA

⚙️ Configuración
Configurar variables en application.properties o application.yml

properties
Copy
Edit
spring.datasource.url=jdbc:postgresql://localhost:5432/mi_bd
spring.datasource.username=usuario
spring.datasource.password=contraseña

spring.redis.host=...
spring.redis.port=...
spring.redis.password=...

jwt.secret=clave_secreta
gmail.username=tu_correo@gmail.com
gmail.password=tu_contraseña
recaptcha.secret=clave_recaptcha

Compilar y ejecutar

mvn spring-boot:run

📡 Endpoints principales
La documentación de la API está disponible con Springdoc OpenAPI (Swagger) en:

http://localhost:8080/swagger-ui.html

👥 Autores
Debloopers Team
