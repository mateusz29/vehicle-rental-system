# Vehicle Rental System

This project implements a vehicle rental system using Jakarta EE. Each branch represents different stages of development with various Jakarta EE features.

## Project Structure

The project uses a three-tier model:
- Vehicle (1)
- Rental (N)
- User (1)

## Branches Overview

### jakarta-servlet
- Basic user management with UUID identifiers
- JSON responses for user data
- Avatar management system
- File system storage for user avatars

### jakarta-cdi
- CDI bean implementation for user management
- Memory-based storage for vehicles and rentals
- Dependency injection patterns

### jakarta-jsf
- Template-based UI with navigation
- Rental and vehicle management views
- CRUD operations for vehicles and rentals
- Form validation and converters

### jakarta-jax-rs
- REST API for rentals and vehicles
- Hierarchical API structure
- JSON format for data exchange
- CRUD operations via REST endpoints

### jakarta-jpa
- H2 database integration
- JPA entity mapping
- Bidirectional relationships
- EntityManager implementation

### jakarta-ejb-security
- EJB implementation for business logic
- User authentication with Basic Auth
- Role-based authorization
- User-vehicle ownership management

### jakarta-jsf-cdi
- Advanced authorization in views
- Multilingual support
- AJAX operations
- CDI events

### jakarta-jsf-jpa-bv
- Optimistic locking
- Creation and modification timestamps
- Criteria API queries
- Bean Validation

## Security Features
- Role-based access control (admin and user roles)
- User authentication
- Resource ownership validation

## Getting Started
1. Clone the repository
2. Switch to desired branch
3. Build using Maven
4. Deploy to Open Liberty server
5. Access application at http://localhost:9081/vehicle-rental

## Features by Role

### Administrator
- Manage all vehicles
- View all rentals
- Edit and delete any vehicle
- Access all system functions

### Regular User
- View vehicles
- Manage owned rentals
- Create new rental listings
- View own rentals

## Development Notes
- Each branch builds upon previous functionality
- Test data is loaded on application startup
- Security features are implemented from jakarta-ejb-security branch onwards
- Uses H2 in-memory database