# Notification Service

## Overview

The **Notification Service** is responsible for handling all notifications within the CUE Event Management System. It manages the delivery of emails, in-app alerts, and system notifications triggered by other microservices. This service ensures reliable and asynchronous communication through AWS SNS and SQS.

---

## Purpose

The Notification Service centralizes the management of all system-generated messages to guarantee consistency and scalability. It operates asynchronously using message queues to prevent blocking operations in other services. Its main responsibilities include:

* Receiving notification events via **AWS SNS** and **SQS**.
* Sending transactional and system emails via **Amazon SES**.
* Handling notification templates for dynamic content.
* Logging delivery results and errors for traceability.

---

## Versions

| Component                                   | Version |
| ------------------------------------------- | ------- |
| **Java**                                    | 21      |
| **Spring Boot**                             | 3.5.4   |
| **Gradle**                                  | 8.14.3  |
| **Bancolombia Clean Architecture Scaffold** | 3.26.1  |

---

## Architecture

The Notification Service follows the **Bancolombia Clean Architecture Scaffold**, ensuring scalability and decoupled communication between layers.

```
notification-service/
├── applications/             # Application entry points and configurations
├── domain/                   # Core entities, use cases, and models
├── infrastructure/            # Adapters for AWS SNS/SQS, SES, and persistence
├── build.gradle               # Gradle configuration
└── settings.gradle            # Project settings
```

### Layers

* **Domain:** Contains models for `Notification`, `Template`, and `Recipient`.
* **Use Cases:** Implements logic for event consumption, email dispatch, and status tracking.
* **Infrastructure:** Integrates with AWS SNS, SQS, and SES clients.
* **Entry Points:** Exposes health and monitoring endpoints.

---

## Environment Variables

Below are the required environment variables for the Notification Service:

```bash
# -----------------------------------
# Server Configuration
# -----------------------------------
SERVER_PORT=8080
SPRING_PROFILES_ACTIVE=dev

# -----------------------------------
# AWS Configuration
# -----------------------------------
AWS_REGION=us-east-1
AWS_ACCESS_KEY_ID=your-aws-access-key
AWS_SECRET_ACCESS_KEY=your-aws-secret-key

# -----------------------------------
# SNS & SQS Configuration
# -----------------------------------
NOTIFICATION_QUEUE_URL=https://sqs.us-east-1.amazonaws.com/123456789012/notification-queue

# -----------------------------------
# SES (Email Sending)
# -----------------------------------
AWS_SES_SOURCE_EMAIL=notifications@cue.edu.co
AWS_SES_TEMPLATE_BUCKET=cue-email-templates
EMAIL_SENDER_NAME=CUE Event Manager

# -----------------------------------
# Internal Communication
# -----------------------------------
INTERNAL_SECRET=your-internal-service-secret
EUREKA_URL=http://discovery-service:8761/eureka/

# -----------------------------------
# Logging Configuration
# -----------------------------------
LOGGING_LEVEL_ROOT=INFO
LOGGING_LEVEL_CO.EDU.CUE=DEBUG
```

---

## Key Features

* **Asynchronous Processing:** Handles notifications through AWS SQS message queues.
* **Email Delivery:** Uses **Amazon SES** for transactional and templated emails.
* **Multi-channel Support:** Designed to support future channels such as SMS or push notifications.
* **Retry Mechanism:** Failed messages are retried automatically using AWS DLQ (Dead Letter Queue).
* **Template Engine:** Dynamic email templates stored in S3 for flexible content management.

---

## Security

* All inter-service communication is secured via the `INTERNAL_SECRET`.
* AWS credentials are stored securely using environment variables or AWS Secrets Manager
