# Blood Bank Management System (Java Full Stack)

A starter academic project based on the supplied abstract.

## Stack
- Backend: Java 17, Spring Boot, Spring Web, Spring Data JPA, Spring Security, Bean Validation
- Database: MySQL
- Frontend: HTML, CSS, JavaScript (served separately or from Spring Boot static resources)
- Authentication: HTTP Basic with seeded demo users (replace with production authentication before deployment)

## Features
- Donor registration and listing
- Blood inventory by blood group
- Blood request creation and request listing
- Admin-only stock updates
- Role-protected endpoints
- Simple browser dashboard

> Medical safety: this is an academic inventory/request tracker, not a transfusion decision system. Blood compatibility, eligibility, screening, crossmatching, and release must be confirmed by qualified blood-bank staff under applicable clinical procedures. The demo's blood-group filter is not a clinical compatibility engine.

## Prerequisites
- JDK 17+
- Maven 3.9+
- MySQL 8+

## Database setup
Create a database and user in MySQL:

```sql
CREATE DATABASE blood_bank_db;
CREATE USER 'bloodapp'@'localhost' IDENTIFIED BY 'ChangeThisPassword!';
GRANT ALL PRIVILEGES ON blood_bank_db.* TO 'bloodapp'@'localhost';
FLUSH PRIVILEGES;
```

Set environment variables before running (Windows PowerShell):
```powershell
$env:DB_URL="jdbc:mysql://localhost:3306/blood_bank_db"
$env:DB_USERNAME="bloodapp"
$env:DB_PASSWORD="ChangeThisPassword!"
$env:APP_ADMIN_USER="admin"
$env:APP_ADMIN_PASSWORD="ChangeAdminPassword!"
$env:APP_STAFF_USER="staff"
$env:APP_STAFF_PASSWORD="ChangeStaffPassword!"
mvn spring-boot:run
```

Backend starts at http://localhost:8080 and serves the frontend at http://localhost:8080/.

## Demo roles
- `admin` / environment-provided admin password: can read data and update stock.
- `staff` / environment-provided staff password: can read data, register donors, and create requests.

Do not use default or shared passwords in a real deployment. This starter uses HTTP Basic for easy local testing; use HTTPS and a stronger login/session or token flow for any real deployment.

## API
- `GET /api/donors`
- `POST /api/donors`
- `GET /api/inventory`
- `PUT /api/inventory/{bloodGroup}` (admin)
- `GET /api/requests`
- `POST /api/requests`

Use browser Basic Auth prompt or an API client. Example POST donor:
```json
{
  "fullName": "Sample Donor",
  "phone": "9876543210",
  "email": "sample@example.com",
  "bloodGroup": "O+",
  "city": "Hyderabad"
}
```
Example POST blood request:
```json
{
  "hospitalName": "City Hospital",
  "contactName": "Duty Officer",
  "phone": "9876543210",
  "bloodGroup": "O+",
  "units": 2,
  "notes": "Urgent request; subject to blood-bank verification"
}
```

## Notes
- The schema is created automatically for local development with `spring.jpa.hibernate.ddl-auto=update`.
- Add audit logs, rate limiting, password hashing through a proper user store, CSRF/CORS policy as applicable, backups, consent/retention policies, and clinical validation before any real use.
