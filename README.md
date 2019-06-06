# Muzic-servce-demo
1. Completed all the endpoints for CRUD operations on Muzix
2.  mysql is udsed to view in-memory data
3. Added an endpoint to search trackByName using @query annotation.
4. Generated API documentation using Swagger 2.
5. Create custom exceptions TrackNotFoundException, TrackAlreadyExistsException in a exception package.
6. Running Logic on Startup in Spring. Created a seed data to pre-fill the database with track
information whenever the application starts. Using both  the approaches:
Approach 1: ApplicationListener<ContextRefreshedEvent>(using @value and @propertysource)
Approach 2: CommandLineRunner (using Environment)
7.Global exception using @Controlleradvice
8.Removed all hard coded data from the application code to application.properties,config.properties
a)by using @Value.
b)by using @PropertySource
c)by using Environment
9) Added @Lombok
10)added @primary to resolve bean creation error when service intrface is implemented by 2 classes.
