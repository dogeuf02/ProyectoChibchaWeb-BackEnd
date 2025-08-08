<p align="justify">
ChibchaWeb - Backend

ChibchaWeb es un backend desarrollado en Spring Boot que implementa múltiples servicios como autenticación JWT con Redis, envío de correos electrónicos mediante SMTP, verificación de reCAPTCHA y lógica de negocio personalizada para una empresa que ofrece servicios de hosting.

Su diseño sigue principios de arquitectura limpia y patrones de diseño que garantizan escalabilidad, mantenibilidad y seguridad.

📂 Estructura del proyecto
La organización del código sigue una arquitectura en capas, separando claramente responsabilidades:

<ul>
  <li><strong>config</strong> → Configuraciones globales.</li>
  <li><strong>controller</strong> → Controladores REST que gestionan las peticiones HTTP.</li>
  <li><strong>dto</strong> → Objetos de transferencia de datos para comunicación entre capas.</li>
  <li><strong>entity</strong> → Entidades JPA que representan las tablas en la base de datos.</li>
  <li><strong>repository</strong> → Interfaces de acceso a datos usando Spring Data JPA.</li>
</ul>

🛠️ Arquitectura y patrones de diseño
El proyecto sigue una arquitectura en capas e implementa los siguientes patrones:

<ul>
  <li><strong>DAO y Repository</strong> → Acceso a datos desacoplado con Spring Data JPA.</li>
  <li><strong>DTO</strong> → Transferencia segura y optimizada de datos.</li>
  <li><strong>Singleton</strong> → Beans gestionados por Spring con scope único por contexto.</li>
  <li><strong>Builder</strong> → Creación de objetos complejos con Lombok (<code>@Builder</code>).</li>
  <li><strong>Inyección de Dependencias</strong> → A través de anotaciones de Spring como <code>@Autowired</code>, <code>@Service</code>, <code>@Component</code>.</li>
  <li><strong>Strategy</strong> → Elección dinámica de implementaciones según la lógica requerida.</li>
  <li><strong>Factory Method</strong> → Creación encapsulada de objetos usando métodos <code>@Bean</code> en clases de configuración.</li>
</ul>

🚀 Servicios implementados
Autenticación y autorización JWT con gestión de sesiones en Redis (Upstash).

<ul>
  <li><strong>Envío de correos electrónicos</strong> vía SMTP de Gmail.</li>
  <li><strong>Validación de usuarios</strong> con Google reCAPTCHA.</li>
  <li><strong>Lógica de negocio personalizada</strong> para la aplicación.</li>
</ul>

📋 Requisitos
<ul>
  <li><strong>Java</strong> 17+</li>
  <li><strong>Maven</strong> 3.8+</li>
  <li><strong>PostgreSQL</strong></li>
  <li><strong>Redis</strong> (Upstash o local)</li>
  <li><strong>Cuenta de Gmail</strong> con credenciales SMTP</li>
  <li><strong>Clave</strong> de Google reCAPTCHA</li>
</ul>

⚙️ Configuración
Configurar variables en application.properties o application.yml:

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

Compilar y ejecutar:

mvn spring-boot:run

📡 Endpoints principales
La documentación de la API está disponible con Springdoc OpenAPI (Swagger) en:

http://localhost:8080/swagger-ui.html

👥 Autores
Debloopers Team
</p>
