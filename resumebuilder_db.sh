#!/bin/bash

# Variables
DB_NAME="resumebuilder"
DB_USER="resumebuilder"
DB_PASSWORD="resumebuilder@123"

echo "Creating database and user..."

psql -U postgres <<EOF

-- Create database
CREATE DATABASE ${DB_NAME};

-- Create user
CREATE USER ${DB_USER} WITH PASSWORD '${DB_PASSWORD}';

-- Grant database privileges
GRANT ALL PRIVILEGES ON DATABASE ${DB_NAME} TO ${DB_USER};

EOF

echo "Creating schemas..."

psql -U postgres -d ${DB_NAME} <<EOF

-- Create schemas
CREATE SCHEMA uam AUTHORIZATION ${DB_USER};
CREATE SCHEMA jasper_cv AUTHORIZATION ${DB_USER};
CREATE SCHEMA email AUTHORIZATION ${DB_USER};
CREATE SCHEMA email_test AUTHORIZATION ${DB_USER};
CREATE SCHEMA crm_kwa AUTHORIZATION ${DB_USER};

-- Grant schema privileges
GRANT ALL PRIVILEGES ON SCHEMA uam TO ${DB_USER};
GRANT ALL PRIVILEGES ON SCHEMA jasper_cv TO ${DB_USER};
GRANT ALL PRIVILEGES ON SCHEMA email TO ${DB_USER};
GRANT ALL PRIVILEGES ON SCHEMA email_test TO ${DB_USER};
GRANT ALL PRIVILEGES ON SCHEMA crm_kwa TO ${DB_USER};

EOF

echo "Database setup completed successfully."