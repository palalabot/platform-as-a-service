# Platform-as-a-Service (PaaS)

A simple low-code platform designed to help users build, manage, and visualize **Lambda-based microservice architectures** without requiring deep technical knowledge.

## Overview

This platform provides an intuitive interface for creating and organizing serverless microservices based on the Lambda pattern. Instead of manually configuring and tracking each service, users can visually manage their entire architecture from a single dashboard.

## Features

- 🚀 Build Lambda-based microservices with a low-code approach.
- 📊 Visualize how all microservices are connected.
- ❤️ Monitor the health and status of each microservice in real time.
- 🔍 Easily identify service dependencies and operational issues.
- 👥 Designed for both technical and non-technical users.

## Goal

The goal of this platform is to simplify the development and management of serverless microservices by providing an easy-to-use visual interface. This allows teams to focus on building applications instead of managing infrastructure complexity.

## Current Status

> **Note:** This project is still a work in progress. Features and functionality are actively being developed and may change over time.

---

## Specs: Kafka Pub/Sub Architecture

### Low-Code Platform as a Service (LC-PaaS)

High-level conceptual specification Proof of Concept

The idea is to make Apache Kafka the central event-driven communication backbone of your Low-Code Platform as a Service.

Rather than having Authentication, Workflow, Email Notification, and other microservices directly communicate with each other, they publish and consume events through Kafka.

This makes Kafka the central messaging layer that coordinates the movement of information across the platform.

The key architectural principle:

Microservices own their business logic. Kafka handles the communication and event distribution between them.

## 1. System overview

### Low-Code Platform

User Interface / Application Builder / API Gateway

Kafka Event Backbone

Event publishing · Topic management · Event distribution · Consumer groups

Asynchronous event communication

Authentication

Identity and access

Workflow Engine

Business process logic

Notification

Email and alerts

Data Service

Application data

Audit Service

Activity tracking

Integration Service

External systems

Individual Microservice Databases

Each service maintains ownership of its own data

The diagram represents logical communication, not a strict one-way network. Services can publish events and independently consume events from other services.

## 2. Purpose of the Kafka subsystem

The Kafka subsystem should serve as the central event infrastructure responsible for:

| Capability | Responsibility |
| --- | --- |
| Event publishing | Accept events from microservices |
| Event distribution | Deliver events to interested consumers |
| Topic management | Organize events by business domain |
| Consumer coordination | Distribute processing across service instances |
| Event persistence | Retain events for configured periods |
| Fault tolerance | Allow consumers to recover and replay events |
| Service decoupling | Prevent direct dependencies between microservices |

Kafka should not execute authentication rules, approve workflows, or send emails itself.

It transports events that trigger those operations.

## 3. Proposed Kafka topic architecture

Topics should be organized according to business domains rather than individual application screens.

Suggested initial topic structure:

| Topic | Purpose | Producer | Consumer |
| --- | --- | --- | --- |
| `auth.events` | Authentication events | Auth Service | Audit, Workflow |
| `user.events` | User lifecycle events | Auth Service | Notification, Audit |
| `workflow.events` | Workflow state changes | Workflow Engine | Notification, Audit |
| `notification.commands` | Requests to send notifications | Workflow Engine | Notification Service |
| `notification.events` | Delivery results | Notification Service | Audit, Workflow |
| `data.events` | Application data changes | Data Service | Workflow, Audit |
| `audit.events` | Platform activity records | All services | Audit Service |
| `integration.events` | External integration events | Integration Service | Workflow Engine |

For the POC, these eight topics are sufficient to demonstrate the architecture.

One important distinction: `*.events` represents something that has happened, while `*.commands` represents something a service is being asked to do.

## 4. Example event lifecycle

Consider an employee submitting a leave request through a low-code application.

1. User submits a request
   The application sends the request to the Workflow Service.
2. Workflow Service publishes an event
   Kafka receives `workflow.events` with an event such as `LeaveRequestSubmitted`.
3. Notification Service consumes the event
   It identifies that an approval notification is required.
4. Notification Service publishes a command
   It publishes a message to `notification.commands`.
5. Email Notification Service processes the command
   It sends an email to the assigned approver.
6. Notification result is published
   The service publishes `EmailNotificationSent` to `notification.events`.
7. Other services react independently
   Audit Service records the event, while Workflow Service can update its notification status.

The important part is that the Workflow Service doesn't need to know how the email is delivered.

It only needs to publish the appropriate event or command.

## 5. Standard event message specification

Every microservice should follow a common event envelope.

For the POC, define a standard JSON message:

json

```json
{
  "eventId": "evt-000001",
  "eventType": "LeaveRequestSubmitted",
  "eventVersion": "1.0",
  "source": "workflow-service",
  "timestamp": "2026-11-02T10:00:00Z",
  "tenantId": "tenant-001",
  "correlationId": "corr-000001",
  "payload": {
    "requestId": "leave-001",
    "employeeId": "user-001",
    "approvalRequired": true
  }
}
```

### Required fields

| Field | Description |
| --- | --- |
| `eventId` | Unique event identifier |
| `eventType` | Type of event being published |
| `eventVersion` | Event schema version |
| `source` | Producing microservice |
| `timestamp` | Event creation time |
| `tenantId` | Platform tenant identifier |
| `correlationId` | Links related operations |
| `payload` | Business-specific event data |

Avoid placing passwords, authentication tokens, or unnecessary personal information inside Kafka messages.

## 6. Kafka subsystem logical components

The Kafka infrastructure itself should have the following responsibilities.

| Component | Responsibility |
| --- | --- |
| Kafka Brokers | Store and distribute events |
| Topics | Categorize events |
| Producers | Publish messages |
| Consumers | Retrieve and process messages |
| Consumer Groups | Coordinate parallel processing |
| Schema Registry | Manage event schema versions |
| Dead Letter Topics | Hold messages that cannot be processed |
| Monitoring | Track throughput, latency, and failures |

For the POC, Schema Registry and dead-letter handling can be introduced as simplified components, with full implementation later.

## 7. Microservice communication contract

Define clear ownership so that the Kafka backbone doesn't become a shared business-logic layer.

| Microservice | Publishes | Subscribes to |
| --- | --- | --- |
| Authentication | `auth.events`, `user.events` | Optional user commands |
| Workflow | `workflow.events` | `auth.events`, `data.events`, `notification.events` |
| Notification | `notification.events` | `notification.commands`, `user.events` |
| Data | `data.events` | Application data commands |
| Audit | `audit.events` | All relevant business events |
| Integration | `integration.events` | Integration commands |

This is a proposed initial contract. Each service should subscribe only to events relevant to its responsibilities.

## 8. Non-functional requirements

These are architectural requirements, not implementation details.

| Area | High-level requirement |
| --- | --- |
| Scalability | Support multiple service instances and consumer groups |
| Reliability | Replicate Kafka data and configure appropriate acknowledgments |
| Delivery | Design consumers for at-least-once delivery |
| Idempotency | Prevent duplicate processing from creating duplicate business actions |
| Security | Authenticate and authorize service access |
| Multi-tenancy | Preserve tenant context across events |
| Observability | Track event IDs, correlation IDs, failures, and processing latency |
| Recovery | Support retries and dead-letter handling |
| Versioning | Maintain backward-compatible event schemas |
| Isolation | Prevent unrelated tenants from accessing each other's events |

One particularly important consideration: Kafka does not automatically guarantee that an email or workflow action will happen exactly once. Consumers must handle retries and duplicate messages safely.

## 9. Proposed POC technology stack

| Layer | Suggested technology |
| --- | --- |
| Programming language | Java |
| Microservice framework | Spring Boot |
| Messaging backbone | Apache Kafka |
| Kafka integration | Spring for Apache Kafka |
| Serialization | JSON |
| Event schema management | Schema Registry (future phase) |
| API layer | REST APIs |
| Containerization | Docker |
| Local orchestration | Docker Compose |
| Monitoring | Prometheus + Grafana (future phase) |

## 10. Suggested implementation phases

1. Phase 1 – Kafka Foundation
   - Run a local Kafka instance.
   - Create the initial topics.
   - Build a Java event producer.
   - Build a Java event consumer.
   - Demonstrate successful message delivery.
2. Phase 2 – Microservice Integration
   - Connect Authentication Service.
   - Connect Workflow Service.
   - Connect Notification Service.
   - Demonstrate service-to-service event communication without direct calls.
3. Phase 3 – Workflow Orchestration
   - Implement event-triggered workflow execution.
   - Support multiple workflow steps.
   - Publish workflow status changes.
   - Trigger email notifications through Kafka.
4. Phase 4 – Reliability and Observability
   - Implement retry policies.
   - Add dead-letter topics.
   - Add event correlation.
   - Introduce monitoring and logging.
5. Phase 5 – Multi-Tenant Platform
   - Introduce tenant-aware event handling.
   - Enforce service-level access controls.
   - Add schema versioning.
   - Demonstrate multiple independent low-code applications using the same platform infrastructure.

## 11. Definition of success for the Kafka POC

The POC should demonstrate the following scenario:

### End-to-end demonstration

Target outcomeA user submits a request through the low-code platform.

The system should then:

- Authenticate the user.
- Publish an authentication or user-context event where appropriate.
- Trigger a workflow through Kafka.
- Execute the workflow business logic.
- Publish a notification command.
- Send a simulated email notification.
- Publish the notification result.
- Record the entire activity in the audit service.

## Success criteria

All three primary services communicate through Kafka without requiring direct synchronous service-to-service calls for the demonstrated event flow.

### 12. Architectural recommendation

There is one design decision I would strongly recommend making early:

Treat Kafka as the event backbone, not the workflow engine.

Your platform should have three distinct layers:

1. Platform Layer: Authentication, application builder, tenant management, APIs.
2. Business Logic Layer: Workflow engine, notification service, data service, integration service.
3. Event Infrastructure Layer: Kafka brokers, topics, producers, consumers, schema management.

This separation means you can eventually add services such as payment processing, document generation, SMS notifications, analytics, and third-party integrations without redesigning the entire platform.

The long-term objective is to make your platform capable of supporting multiple low-code applications, each with its own business workflows, while sharing a common event-driven infrastructure.
