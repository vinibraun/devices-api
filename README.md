# 📱 Devices API

A RESTful API for device management. Ideal for internal systems that need to monitor, register, or change the status of equipment used within an organization.

## 📖 Development Roadmap
For organization purposes, a checklist containing the development stages was created: [Checklist](https://ticktick.com/pub/project/collaboration/invite/8725aa2447ed45948bc907d2c05fa7ab?u=412388932e1641b6b7b7329e8b7bc394)

## 🔧 Features

- ✅ Registration of new devices
- ✏️ Update of existing devices
- 🔍 Search by ID, brand, and device status
- 📋 Listing of all registered devices
- 🗑️ Deletion of devices, with restrictions based on state
- 🚫 Does not allow modifications to devices currently in use

## 🧰 Technologies Used

- **Java 21**
- **Spring Boot**
- **Maven**
- **H2 Database** (test environment)
- **PostgreSQL** (production, via Docker)
- **JUnit 5 / MockMvc** (integration tests)
- **Lombok** (boilerplate reduction)
- **MapStruct** (mapping between DTOs and entities)

<div align="left">
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original.svg" height="40" alt="java logo"  />
  <img width="12" />
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/spring/spring-original.svg" height="40" alt="spring logo"  />
  <img width="12" />
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/postgresql/postgresql-original.svg" height="40" alt="postgresql logo"  />
  <img width="12" />
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/docker/docker-original.svg" height="40" alt="docker logo"  />
</div>

###

## Notes
- The **main** branch serves as an overview of the features and technologies. For a more technical view, refer to the **master** branch, which contains the version with test coverage, advanced configurations, and detailed documentation.
