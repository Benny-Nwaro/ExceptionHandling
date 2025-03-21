-- Step 1: Rename the 'id' column to 'user_id' in the 'users' table
ALTER TABLE users RENAME COLUMN id TO user_id;

-- Step 2: Ensure 'user_id' is a primary key (if not already)
ALTER TABLE users ADD CONSTRAINT users_pkey PRIMARY KEY (user_id);

-- Step 3: Update foreign key references in dependent tables
ALTER TABLE enrollments RENAME COLUMN student_id TO user_id;
ALTER TABLE submissions RENAME COLUMN student_id TO user_id;

-- Step 4: Update foreign key constraints
ALTER TABLE enrollments DROP CONSTRAINT IF EXISTS fk_student;
ALTER TABLE enrollments ADD CONSTRAINT fk_student FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE;

ALTER TABLE submissions DROP CONSTRAINT IF EXISTS fk_submission_student;
ALTER TABLE submissions ADD CONSTRAINT fk_submission_student FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE;
