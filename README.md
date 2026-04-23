# Assignment: Kotlin Greeting Annotation Processor
Course : Mobile Computing
Student (s): Tomás Santos
Date : 2026-04-22
Repository URL : https://github.com/tomassantos2025/GreetingProcessorProject

---

## 1. Introduction
This assignment explores compile-time annotation processing in Kotlin through a custom `@Greeting` annotation. The main goal is to reduce repetitive wrapper code by generating methods automatically during compilation. When a method is annotated with `@Greeting("message")`, the processor creates a wrapper class that prints the configured greeting before delegating the call to the original method.

The project follows the multi-module structure requested in the assignment: one module defines the annotation, one module implements the annotation processor, and one module demonstrates the generated code in practice. The result is a small but complete example of declarative development with Kotlin, KAPT, and KotlinPoet.

## 2. System Overview
The system is composed of three functional modules:

- `annotations`: declares the `@Greeting` annotation used by application code.
- `processor`: scans annotated methods at compile time and generates wrapper classes.
- `app`: contains a demonstration class and the entry point that uses the generated wrapper.

Main features:

- Function-level custom annotation with a configurable message.
- Compile-time code generation using KAPT.
- Automatic creation of a wrapper class per annotated class.
- Demonstration of the generated wrapper in a runnable application module.

In the current implementation, `MyClass` contains two annotated methods, `sayHello()` and `compute()`. During compilation, the processor generates `MyClassWrapper`, which prints the configured greeting before calling the original methods.

## 3. Architecture and Design
The architecture is centered on separation of concerns between annotation definition, processing, and consumption:

```text
GreetingProcessorProject/
|-- annotations/
|   |-- src/main/kotlin/annotations/Greeting.kt
|-- processor/
|   |-- src/main/kotlin/processor/GreetingProcessor.kt
|-- app/
|   |-- src/main/kotlin/
|       |-- Main.kt
|       |-- com/example/app/MyClass.kt
|-- build.gradle.kts
```

Key design decisions:

- `@Greeting` uses `AnnotationTarget.FUNCTION` because the behavior is method-based.
- `AnnotationRetention.SOURCE` is sufficient because the annotation is only needed during compilation.
- KAPT is used to integrate annotation processing into the Kotlin build.
- `AutoService` registers the processor automatically, avoiding manual service metadata creation.
- KotlinPoet is used to generate Kotlin source code in a structured and maintainable way.

The processor groups annotated methods by enclosing class and generates one wrapper class per original class. This design keeps the generated API easy to understand: `MyClass` becomes `MyClassWrapper`, which holds an `original` instance and forwards calls after printing the greeting. The repository also contains some default IntelliJ starter `Main.kt` files outside the main demonstration flow; they are not essential to the annotation-processing solution.

## 4. Implementation
The implementation starts with a simple custom annotation:

```kotlin
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.SOURCE)
annotation class Greeting(val message: String)
```

The processor, implemented in `processor/src/main/kotlin/processor/GreetingProcessor.kt`, performs these steps:

1. Find all elements annotated with `@Greeting`.
2. Keep only executable elements (methods).
3. Group methods by enclosing class.
4. Generate a wrapper class named `${OriginalClassName}Wrapper`.
5. Replicate the method parameters in the generated method.
6. Emit a `println(...)` with the annotation message.
7. Delegate to the original instance.

In the `app` module, `MyClass` demonstrates the feature:

```kotlin
@Greeting("Hello from MyClass!")
open fun sayHello() {
    println("Executing sayHello method")
}

@Greeting("Welcome to compute!")
open fun compute() {
    println("Computing something...")
}
```

This produces the generated wrapper:

```kotlin
public class MyClassWrapper(
  public val original: MyClass,
) {
  public fun sayHello() {
    println("Hello from MyClass!")
    original.sayHello()
  }
}
```

The current implementation correctly handles the assignment scenario and forwards method parameters when present. However, it is currently best suited to `Unit`-returning methods like the ones used in the demo.

## 5. Testing and Validation
No dedicated automated test suite is currently included in the repository, so validation was performed through build verification, generated-source inspection, and manual execution.

Validation performed:

- Successful full project build with `.\gradlew.bat build`.
- Confirmation that the generated file `app/build/generated/source/kaptKotlin/main/com/example/app/MyClassWrapper.kt` is created during compilation.
- Manual execution of the demo application logic.

Observed runtime output:

```text
Hello from MyClass!
Executing sayHello method
Welcome to compute!
Computing something...
```

This output confirms the expected behavior: the generated wrapper prints the greeting first and then calls the original method.

Known limitations:

- No JUnit or integration tests are present.
- Return values are not propagated by the generated wrapper in the current implementation.
- Advanced cases such as overloaded methods, generics, visibility variations, or suspend functions were not explicitly tested.

## 6. Usage Instructions
### Requirements
- JDK 17
- IntelliJ IDEA or another environment capable of building Gradle Kotlin projects
- Internet access on the first build if dependencies are not already cached

### Setup
1. Clone the repository:

```powershell
git clone https://github.com/tomassantos2025/GreetingProcessorProject.git
cd GreetingProcessorProject
```

2. Build the project:

```powershell
.\gradlew.bat build
```

### Execution
The simplest way to run the demonstration is through IntelliJ IDEA:

1. Open the project as a Gradle project.
2. Let Gradle sync all modules.
3. Run `app/src/main/kotlin/Main.kt`.

During compilation, KAPT generates `MyClassWrapper`, and the application then instantiates and uses that generated class.

From the command line, the project currently provides a build flow but not a dedicated `run` task, since the `application` plugin is not configured in `app`.

---

# Autonomous Software Engineering Sections
These sections apply only if this assignment instance was marked `AC OK` and `AI OK`.

## 7. Prompting Strategy
In the documented AI-assisted work for this repository, prompts were used to transform a generic README template into a project-specific report. The prompting process evolved in two stages:

- first, a prompt was used to create the section structure requested by the assignment template;
- second, a refinement prompt required the README to be written from the actual context of the implemented project rather than from placeholders.

Representative prompt examples from this documentation workflow:

- "Create the markdown file, README.md following this example:"
- "Complete it based on the context of this project"

The refined prompt produced a better result because it forced repository inspection, build verification, and alignment with the actual implementation.

## 8. Autonomous Agent Workflow
For the AI-assisted documentation work, the agent workflow was:

1. Inspect the repository structure and Gradle module configuration.
2. Read the annotation, processor, and demo application code.
3. Inspect the generated wrapper source.
4. Validate the project with a successful `.\gradlew.bat build`.
5. Execute the compiled demo to confirm observed runtime behavior.
6. Synthesize the findings into this README.

In this documented interaction, AI support was used for analysis, validation, and documentation generation. The repository code itself appears to have been implemented already before this README was written.

## 9. Verification of AI-Generated Artifacts
The AI-generated artifact in this context is primarily the README content. Its correctness was verified by comparing every major statement against the repository and against actual command results.

Verification steps:

- checked `settings.gradle.kts` and module `build.gradle.kts` files;
- checked `Greeting.kt`, `GreetingProcessor.kt`, `MyClass.kt`, and `Main.kt`;
- confirmed generated code under `app/build/generated/source/kaptKotlin/main/`;
- ran `.\gradlew.bat build` successfully;
- ran the demo application and confirmed the greeting output order.

This verification reduces the risk of inaccurate documentation or unsupported claims.

## 10. Human vs AI Contribution
Based on the evidence available in the repository, the project implementation was already present before this documentation pass. In the current documented workflow:

- the human provided the assignment brief, repository context, and final intent for the README;
- the AI inspected the repository, validated the build and output, and drafted the documentation.

Final responsibility for correctness, submission, and any edits remains with the human author. If AI tools were also used earlier during implementation, that contribution should be added explicitly here.

## 11. Ethical and Responsible Use
Responsible use of AI in this task required keeping the generated documentation tied to verifiable facts. Unsupported assumptions were avoided where possible. For example, course and student information were not invented because the repository does not provide authoritative values for those fields.

The main risks in AI-assisted documentation are fabrication, overstatement, and omission of limitations. These were handled by:

- grounding claims in actual source files and build outputs;
- explicitly stating current limitations such as the lack of automated tests;
- distinguishing verified facts from information that still depends on the author's confirmation.

---

# Development Process

## 12. Version Control and Commit History
The project is versioned with Git and hosted on GitHub. At the time this README was prepared, the visible history showed a single commit:

- `9f47d0e` - `first commit`

This confirms the repository is under version control, but it does not yet demonstrate the continuous, incremental commit history requested by the assignment statement. A stronger history for academic reporting would separate milestones such as annotation creation, processor implementation, app integration, debugging, and documentation into distinct commits.

## 13. Difficulties and Lessons Learned
The most important technical challenge in this assignment is wiring together the full annotation-processing pipeline correctly. Defining an annotation is simple, but making the processor discoverable, configuring KAPT, and generating Kotlin code into the correct output directory requires a good understanding of the build process.

Main lessons learned:

- compile-time processing is a practical way to remove repetitive boilerplate;
- multi-module organization makes the responsibilities clearer and easier to maintain;
- KotlinPoet simplifies source generation compared with manual string concatenation;
- verification of generated code is essential because build success alone does not guarantee correct runtime behavior.

## 14. Future Improvements
Possible future improvements include:

- support return values in generated wrapper methods;
- add automated unit and integration tests;
- support more complex method signatures and edge cases;
- add an `application` plugin or fat-jar setup for simpler command-line execution;
- remove unused template starter files that are not part of the final assignment architecture;
- evaluate KSP as an alternative to KAPT for newer Kotlin projects.

---

## 15. AI Usage Disclosure ( Mandatory )
AI tools used in the documented workflow for this repository:

- OpenAI Codex/ChatGPT: used to inspect the repository, validate build behavior, summarize the implementation, and draft this README.

How AI was used:

- documentation drafting;
- repository analysis;
- build and runtime verification support;
- wording and structuring of the report.

Responsibility statement:

The author remains fully responsible for all submitted content, including the correctness of the code, documentation, conclusions, and any AI-assisted material.
