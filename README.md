# todolist

## Tech Stack
- **Android SDK**
- **Kotlin**
- **Clean Architecture**
- **Coroutines**
- **Kotlin Flow**
- **DI (Hilt Dagger)**
- **Compose**
- **Room Database**

## Clean Architecture
The app follows the Clean Architecture principles to ensure a modular and scalable design:

1. **Presentation Layer**:
    - The Presentation module represents the user interface and contains multiple composable and ViewModels. It depends on the Data and Domain modules.

2. **Domain Layer**:
    - The Domain module contains the core business logic, including repositories, entities, and use cases.

3. **Data Layer**:
    - The Data module is responsible for data management, including database interactions, repository implementations, and mapping extensions.

## Version Control
The project uses a trunk-based Git branching strategy with the following branches:
- **Main**: The production-ready branch.
- **Dev**: The development branch where ongoing work is committed.
