DROP TABLE IF EXISTS weekly_payout CASCADE;
DROP TABLE IF EXISTS schedule CASCADE;
DROP TABLE IF EXISTS shift_assignment CASCADE;
DROP TABLE IF EXISTS employee CASCADE;
DROP TABLE IF EXISTS post CASCADE;
DROP TABLE IF EXISTS shift CASCADE;

-- Employee table with additional security-specific fields
CREATE TABLE employee (
    employee_id SERIAL PRIMARY KEY,
    employee_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE,
    phone VARCHAR(20),
    hire_date DATE NOT NULL DEFAULT CURRENT_DATE,
    employment_status VARCHAR(20) DEFAULT 'ACTIVE', -- ACTIVE, INACTIVE, ON_LEAVE
    security_license_number VARCHAR(50),
    license_expiry DATE,
    hourly_rate DECIMAL(10,2) NOT NULL,
    overtime_rate DECIMAL(10,2) DEFAULT 0, -- Can be calculated as 1.5*hourly_rate
    overtime_eligible BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Shift definitions
CREATE TABLE shift (
    shift_id SERIAL PRIMARY KEY,
    shift_name VARCHAR(20) NOT NULL, -- 'Morning', 'Afternoon', 'Night', 'Graveyard'
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    duration_hours DECIMAL(4,2) GENERATED ALWAYS AS (
        EXTRACT(EPOCH FROM (end_time - start_time)) / 3600
    ) STORED,
    is_overnight BOOLEAN DEFAULT FALSE,
    shift_premium DECIMAL(4,2) DEFAULT 0.00 -- Additional pay for undesirable shifts
);

-- Post/location table
CREATE TABLE post (
    post_id SERIAL PRIMARY KEY,
    post_name VARCHAR(50) NOT NULL,
    post_location VARCHAR(100) NOT NULL,
    post_type VARCHAR(30), -- 'Static', 'Patrol', 'Control Room', 'Gatehouse'
    required_license_type VARCHAR(50), -- Specific license required for this post
    minimum_experience_months INTEGER DEFAULT 0,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Shift assignments (many-to-many between employee and post with shift details)
CREATE TABLE shift_assignment (
    assignment_id SERIAL PRIMARY KEY,
    employee_id INTEGER NOT NULL REFERENCES employee(employee_id) ON DELETE CASCADE,
    post_id INTEGER NOT NULL REFERENCES post(post_id) ON DELETE CASCADE,
    shift_id INTEGER NOT NULL REFERENCES shift(shift_id) ON DELETE CASCADE,
    assignment_date DATE NOT NULL,
    actual_start_time TIMESTAMP,
    actual_end_time TIMESTAMP,
    status VARCHAR(20) DEFAULT 'SCHEDULED', -- SCHEDULED, CONFIRMED, COMPLETED, CANCELLED, NO_SHOW
    overtime_hours DECIMAL(4,2) DEFAULT 0,
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(employee_id, assignment_date, shift_id) -- Prevent double-booking
);

-- Schedule table (weekly/daily schedules)
CREATE TABLE schedule (
    schedule_id SERIAL PRIMARY KEY,
    schedule_name VARCHAR(100),
    schedule_date DATE NOT NULL,
    shift_period VARCHAR(20), -- 'Weekly', 'Bi-weekly', 'Monthly'
    created_by INTEGER REFERENCES employee(employee_id),
    is_published BOOLEAN DEFAULT FALSE,
    published_at TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Schedule assignments (links assignments to a schedule)
CREATE TABLE schedule_shift (
    schedule_shift_id SERIAL PRIMARY KEY,
    schedule_id INTEGER NOT NULL REFERENCES schedule(schedule_id) ON DELETE CASCADE,
    assignment_id INTEGER NOT NULL REFERENCES shift_assignment(assignment_id) ON DELETE CASCADE,
    UNIQUE(schedule_id, assignment_id)
);

-- Weekly payout/earnings
CREATE TABLE weekly_payout (
    payout_id SERIAL PRIMARY KEY,
    employee_id INTEGER NOT NULL REFERENCES employee(employee_id) ON DELETE CASCADE,
    week_start_date DATE NOT NULL,
    week_end_date DATE NOT NULL,
    regular_hours DECIMAL(10,2) DEFAULT 0,
    overtime_hours DECIMAL(10,2) DEFAULT 0,
    holiday_hours DECIMAL(10,2) DEFAULT 0,
    regular_pay DECIMAL(10,2) DEFAULT 0,
    overtime_pay DECIMAL(10,2) DEFAULT 0,
    holiday_pay DECIMAL(10,2) DEFAULT 0,
    shift_premium_pay DECIMAL(10,2) DEFAULT 0,
    total_pay DECIMAL(10,2) DEFAULT 0,
    is_paid BOOLEAN DEFAULT FALSE,
    paid_date DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(employee_id, week_start_date)
);

-- Time tracking (optional but recommended for clock-in/out)
CREATE TABLE time_tracking (
    tracking_id SERIAL PRIMARY KEY,
    employee_id INTEGER NOT NULL REFERENCES employee(employee_id) ON DELETE CASCADE,
    assignment_id INTEGER REFERENCES shift_assignment(assignment_id) ON DELETE SET NULL,
    clock_in TIMESTAMP NOT NULL,
    clock_out TIMESTAMP,
    location VARCHAR(100), -- GPS coordinates or location name
    hours_worked DECIMAL(4,2) GENERATED ALWAYS AS (
        CASE 
            WHEN clock_out IS NOT NULL THEN 
                EXTRACT(EPOCH FROM (clock_out - clock_in)) / 3600
            ELSE 0
        END
    ) STORED,
    is_verified BOOLEAN DEFAULT FALSE,
    verified_by INTEGER REFERENCES employee(employee_id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Employee availability for scheduling
CREATE TABLE employee_availability (
    availability_id SERIAL PRIMARY KEY,
    employee_id INTEGER NOT NULL REFERENCES employee(employee_id) ON DELETE CASCADE,
    day_of_week INTEGER NOT NULL CHECK (day_of_week BETWEEN 1 AND 7), -- 1=Monday, 7=Sunday
    shift_id INTEGER REFERENCES shift(shift_id) ON DELETE CASCADE,
    is_available BOOLEAN DEFAULT TRUE,
    preference_level INTEGER DEFAULT 0, -- 0=No preference, 1=Preferred, -1=Avoid
    valid_from DATE DEFAULT CURRENT_DATE,
    valid_to DATE,
    UNIQUE(employee_id, day_of_week, shift_id)
);

-- Insert default shifts
INSERT INTO shift (shift_name, start_time, end_time, is_overnight, shift_premium) VALUES
('Morning', '08:00:00', '16:00:00', FALSE, 0.00),
('Afternoon', '16:00:00', '00:00:00', TRUE, 1.50), -- Overnight premium
('Night', '00:00:00', '08:00:00', TRUE, 2.00), -- Higher premium for night
('Graveyard', '22:00:00', '06:00:00', TRUE, 2.50);

-- Create indexes for performance
CREATE INDEX idx_employee_status ON employee(employment_status);
CREATE INDEX idx_shift_assignment_date ON shift_assignment(assignment_date);
CREATE INDEX idx_shift_assignment_employee ON shift_assignment(employee_id, assignment_date);
CREATE INDEX idx_shift_assignment_status ON shift_assignment(status);
CREATE INDEX idx_payout_employee_week ON weekly_payout(employee_id, week_start_date);
CREATE INDEX idx_time_tracking_employee_date ON time_tracking(employee_id, DATE(clock_in));
