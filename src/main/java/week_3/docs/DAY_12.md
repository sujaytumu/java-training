# Day 12 — Spring Boot Architecture and Configuration

- **Architecture:** Spring Boot layers conventions, embedded server, dependency management and operational integrations on Spring Framework.
- **Auto-configuration:** conditional defaults based on classpath/properties/available beans; user beans can make defaults back off.
- **Starters:** web, data-jpa, validation, security, aop, actuator and test bring compatible dependency sets.
- **Lifecycle:** SpringApplication prepares environment, creates/refreshes context, starts embedded server and invokes runners.
- **Properties vs YAML:** dotted key/value vs indented hierarchy; avoid duplicate/conflicting configuration.
- **Profiles:** application-local/dev/prod properties separate environment settings.
- **Externalized configuration:** environment variables keep credentials outside source control.
- **@ConfigurationProperties:** binds related settings into a typed, validated object.
- **Conditional beans:** @ConditionalOnProperty and @ConditionalOnMissingBean show feature toggles and override behavior.
- **Custom auto-configuration:** reusable libraries package conditional @AutoConfiguration classes and imports metadata.
- **Logging:** package-specific levels; parameterized logs; never log passwords/tokens.
- **Actuator:** health/info support operations; expose and secure only necessary endpoints.

Practical: run local profile; activate dev and inspect resolved config. PostgreSQL must be running for dev/prod.