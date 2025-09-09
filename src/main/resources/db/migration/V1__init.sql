-- 1. Таблица study_types (ENUM-справочник)
create table if not exists study_types
(
    code VARCHAR(20) PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);
create table if not exists education_forms
(
    code VARCHAR(20) PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

INSERT INTO study_types (code, name)
VALUES ('budget', 'Бюджет'),
       ('contract', 'Контракт');

INSERT INTO education_forms (code, name)
VALUES ('full-time', 'очная'),
       ('postal', 'заочная');
create table if not exists passport
(
    id                     BIGSERIAL primary key,
    passport_serial        VARCHAR(20),
    passport_number        VARCHAR(20),
    passport_issue_date    DATE,
    passport_issue_by_whom VARCHAR(256),
    address_registration   VARCHAR(256)

);
-- 4. Таблица student_info
create table if not exists student_info
(
    id               bigserial PRIMARY KEY,
    institute        varchar(255),
    speciality       varchar(255),
    training_profile varchar(255),
    course           INTEGER,
    group_number     VARCHAR(30),
    study_type       VARCHAR(20),
    education_form   varchar(20),
    photo_url        varchar(255),
    CONSTRAINT fk_study_type FOREIGN KEY (study_type)
        REFERENCES study_types (code),
    CONSTRAINT fk_education_form FOREIGN KEY (education_form)
        REFERENCES education_forms (code)
);
-- 2. Таблица personal_data
create table if not exists personal_data
(
    id                 bigserial PRIMARY KEY,
    first_name         VARCHAR(100),
    last_name          VARCHAR(100),
    middle_name        VARCHAR(100),
    birth_date         DATE,
    address_living     VARCHAR(256),
    passport_id        bigint,
    inn                VARCHAR(20),
    snils              BIGINT,
    disability_status  VARCHAR(256),
    full_state_support VARCHAR(10),
    contact_number     VARCHAR(30),
    created_at         TIMESTAMP DEFAULT now(),
    updated_at         TIMESTAMP DEFAULT now(),
    constraint fk_personal_data_passport foreign key (passport_id) references passport (id)

);

-- 3. Таблица users
create table if not exists users
(
    id               bigserial PRIMARY KEY,
    email            VARCHAR(256),
    id_kfu           INTEGER,
    last_login       TIMESTAMP,
    is_staff         BOOLEAN   DEFAULT FALSE,
    personal_data_id bigint,
    student_info_id  bigint,
    created_at       TIMESTAMP DEFAULT now(),
    updated_at       TIMESTAMP DEFAULT now(),
    CONSTRAINT fk_users_personal_data
        FOREIGN KEY (personal_data_id)
            REFERENCES personal_data (id),
    constraint fk_personal_data_student_info foreign key (student_info_id) references student_info (id)
);


--
-- -- 5. Таблица application
-- create table if not exists application
-- (
--     id   bigserial PRIMARY KEY,
--     date DATE
-- );
--
-- -- 6. Таблица assistance_types (Вид помощи)
-- create table if not exists assistance_types
-- (
--     id         bigserial PRIMARY KEY,
--     name       VARCHAR(100),
--     source     VARCHAR(1), -- ENUM-like: 'P', 'U'
--     identifier VARCHAR(10)
-- );
--
-- -- 7. Таблица documents
-- create table if not exists documents
-- (
--     id               bigserial PRIMARY KEY,
--     name             VARCHAR(256),
--     written_document VARCHAR(256),
--     is_choice        BOOLEAN
-- );
--
-- -- 8. Таблица categories_need
-- create table if not exists categories_need
-- (
--     id         bigserial PRIMARY KEY,
--     role       VARCHAR(50),
--     name       VARCHAR(256),
--     short_name VARCHAR(100),
--     documents  VARCHAR(256),
--     written    VARCHAR(256)
-- );
--
-- -- 9. Таблица categories_documents (многие-ко-многим)
-- create table if not exists categories_documents
-- (
--     id          bigserial PRIMARY KEY,
--     category_id INTEGER,
--     document_id INTEGER, -- Документ, доказывающий категорию
--     CONSTRAINT fk_categories_documents_category
--         FOREIGN KEY (category_id)
--             REFERENCES categories_need (id),
--     CONSTRAINT fk_categories_documents_document
--         FOREIGN KEY (document_id)
--             REFERENCES documents (id)
-- );
--
-- -- 10. Таблица assistance_documents (многие-ко-многим)
-- create table if not exists assistance_documents
-- (
--     id                 bigserial PRIMARY KEY,
--     assistance_type_id INTEGER,
--     document_id        INTEGER,
--     CONSTRAINT fk_assistance_documents_assistance
--         FOREIGN KEY (assistance_type_id)
--             REFERENCES assistance_types (id),
--     CONSTRAINT fk_assistance_documents_document
--         FOREIGN KEY (document_id)
--             REFERENCES documents (id)
-- );
--
-- -- 11. Таблица user_categories (многие-ко-многим)
-- create table if not exists user_categories
-- (
--     id                 bigserial PRIMARY KEY,
--     user_id            INTEGER,
--     assistance_type_id INTEGER,
--     category_id        INTEGER,
--     CONSTRAINT fk_user_categories_user
--         FOREIGN KEY (user_id)
--             REFERENCES users (id),
--     CONSTRAINT fk_user_categories_assistance
--         FOREIGN KEY (assistance_type_id)
--             REFERENCES assistance_types (id),
--     CONSTRAINT fk_user_categories_category
--         FOREIGN KEY (category_id)
--             REFERENCES categories_need (id)
-- );
--
-- -- Создаем индексы для ускорения поиска
-- CREATE INDEX idx_users_email ON users (email);
-- CREATE INDEX idx_users_username ON users (username);
-- CREATE INDEX idx_user_categories_user ON user_categories (user_id);
-- CREATE INDEX idx_user_categories_type ON user_categories (assistance_type_id);
-- CREATE INDEX idx_user_categories_category ON user_categories (category_id);