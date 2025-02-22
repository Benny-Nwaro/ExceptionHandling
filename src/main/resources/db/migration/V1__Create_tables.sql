-- Users Table
CREATE TABLE users (
  user_id SERIAL PRIMARY KEY,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  date_of_birth DATE,
  email VARCHAR(255) UNIQUE NOT NULL,
  first_name VARCHAR(255) NOT NULL,
  gender VARCHAR(10),
  last_name VARCHAR(255) NOT NULL,
  password VARCHAR(50) NOT NULL,
  phone_number VARCHAR(50),
  profile_bio TEXT,
  role VARCHAR(50) CHECK (role IN ('STUDENT', 'INSTRUCTOR', 'ADMIN'))
);


-- Courses Table
--CREATE TABLE courses (
--  course_id SERIAL PRIMARY KEY,
--  title VARCHAR(255) NOT NULL,
--  description TEXT,
--  teacher_id INTEGER NOT NULL,
--  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
--  CONSTRAINT fk_teacher FOREIGN KEY (teacher_id) REFERENCES users(user_id) ON DELETE CASCADE
--);

-- Enrolments Table (Students Enrolling in Courses)
--CREATE TABLE enrollments (
--  enrollment_id SERIAL PRIMARY KEY,
--  student_id INTEGER NOT NULL,
--  course_id INTEGER NOT NULL,
--  enrollment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
--  CONSTRAINT fk_student FOREIGN KEY (student_id) REFERENCES users(user_id) ON DELETE CASCADE,
--  CONSTRAINT fk_course FOREIGN KEY (course_id) REFERENCES courses(course_id) ON DELETE CASCADE,
--  UNIQUE (student_id, course_id)
--);

-- Lessons Table (Each Course has multiple Lessons)
--CREATE TABLE lessons (
--  lesson_id SERIAL PRIMARY KEY,
--  course_id INTEGER NOT NULL,
--  title VARCHAR(255) NOT NULL,
--  content TEXT,
--  video_url VARCHAR(512),
--  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
--  CONSTRAINT fk_lesson_course FOREIGN KEY (course_id) REFERENCES courses(course_id) ON DELETE CASCADE
--);

-- Assignments Table (Each Course can have multiple Assignments)
--CREATE TABLE assignments (
--  assignment_id SERIAL PRIMARY KEY,
--  course_id INTEGER NOT NULL,
--  title VARCHAR(255) NOT NULL,
--  description TEXT,
--  due_date TIMESTAMP NOT NULL,
--  CONSTRAINT fk_assignment_course FOREIGN KEY (course_id) REFERENCES courses(course_id) ON DELETE CASCADE
--);

-- Submissions Table (Students submit assignments)
--CREATE TABLE submissions (
--  submission_id SERIAL PRIMARY KEY,
--  student_id INTEGER NOT NULL,
--  assignment_id INTEGER NOT NULL,
--  submitted_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
--  CONSTRAINT fk_submission_student FOREIGN KEY (student_id) REFERENCES users(user_id) ON DELETE CASCADE,
--  CONSTRAINT fk_submission_assignment FOREIGN KEY (assignment_id) REFERENCES assignments(assignment_id) ON DELETE CASCADE,
--  UNIQUE (student_id, assignment_id)
--);
