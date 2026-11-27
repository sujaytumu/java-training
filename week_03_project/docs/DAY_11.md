# Day 11 — Spring Framework Fundamentals

- **Why Spring:** manages object creation, dependency wiring, transactions, web infrastructure and cross-cutting concerns instead of manually constructing every dependency.
- **IoC:** the container controls object lifecycle and application wiring.
- **DI:** dependencies are passed into constructors; this makes requirements explicit, supports final fields and simplifies unit tests.
- **ApplicationContext:** Boot creates and refreshes the IoC container, discovers beans, resolves dependencies and publishes lifecycle events.
- **Bean lifecycle:** instantiate → inject dependencies → aware callbacks → BeanPostProcessors → initialization callbacks → use → destruction callbacks.
- **Scopes:** singleton (default, one per context), prototype (new object per request from container), request/session (web context). Prototype destruction is not managed like singleton.
- **Component scan:** @SpringBootApplication scans its package and descendants.
- **Stereotypes:** @Component generic, @Service business, @Repository persistence/exception translation, @Controller MVC, @RestController response-body controller.
- **@Configuration/@Bean:** explicit factory methods for objects not discovered as components.
- **@Primary/@Qualifier:** resolve multiple candidates; Primary is default, Qualifier selects a named candidate.
- **Constructor vs field injection:** constructor injection preferred; field injection hides required dependencies and complicates tests.
- **Proxies:** Spring AOP, @Transactional and method security often use proxies. Self-invocation via this bypasses proxy interception; private/final methods have proxy limitations.
- **AOP:** advice + pointcut + join point separates logging/metrics/auditing from business code.

Practical: inspect the component/service/repository layers, configuration beans, prototype TraceToken, lifecycle callback and logging aspect. Trigger a service call and inspect logs.