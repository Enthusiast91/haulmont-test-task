DROP TABLE RECIPES IF EXISTS;
DROP TABLE DOCTORS IF EXISTS;
DROP TABLE PATIENTS IF EXISTS;
DROP TABLE RECIPEPRIORITY IF EXISTS;

CREATE TABLE Patients (
    id BIGINT IDENTITY PRIMARY KEY,
    name VARCHAR(30) NOT NULL,
    lastName VARCHAR(30) NOT NULL,
    patronymic VARCHAR(30) NOT NULL,
    phone VARCHAR(15) UNIQUE
);

CREATE TABLE Doctors (
    id BIGINT IDENTITY PRIMARY KEY,
    name VARCHAR(30) NOT NULL,
    lastName VARCHAR(30) NOT NULL,
    patronymic VARCHAR(30) NOT NULL,
    specialization VARCHAR(60) NOT NULL
);

CREATE  TABLE RecipePriority (
    id BIGINT IDENTITY PRIMARY KEY,
    priority VARCHAR(30) UNIQUE NOT NULL
);

INSERT INTO RECIPEPRIORITY
    (PRIORITY)
VALUES
    ('NORMAL'),
    ('CITO'),
    ('STATIM');

CREATE TABLE Recipes (
    id BIGINT IDENTITY PRIMARY KEY,
    doctorId BIGINT FOREIGN KEY REFERENCES Doctors,
    patientId BIGINT FOREIGN KEY REFERENCES Patients,
    description VARCHAR(1000) NOT NULL,
    creationDate DATE DEFAULT current_date NOT NULL,
    validity INTEGER NOT NULL,
    priorityId BIGINT FOREIGN KEY REFERENCES RecipePriority
);

INSERT INTO PATIENTS
    (NAME, LASTNAME, PATRONYMIC, PHONE)
VALUES
    ('Вячеслав', 'Гусев', 'Юрьевич', '89297031370'),
    ('Дмитрий', 'Новиков', 'Алексеевич', '89298882244'),
    ('Алина', 'Воробьева', 'Александровна', '89297771133'),
    ('Олеся', 'Воробьева', 'Александровна', '89271114466'),
    ('Алексей', 'Третьяков', 'Юрьевич', '89272225577'),
    ('Семён', 'Копылов', 'Юрьевич', '89174447799'),
    ('Олег', 'Гусев', 'Иванович', '89174447700'),
    ('Иван', 'Петров', 'Иванович', '89174447711'),
    ('Keanu', 'Reeves', '', '89273336688');

INSERT INTO DOCTORS
    (NAME, LASTNAME, PATRONYMIC, SPECIALIZATION)
VALUES
    ('Александр', 'Кашланов', 'Николаевич', 'Хирург'),
    ('Эльвира', 'Бекмузаева', 'Алмазовна', 'Терапевт'),
    ('Ольга', 'Волкова', 'Дмитриевна', 'Терапевт'),
    ('Александр', 'Чирков', 'Анатольевич', 'Офтальмолог');

INSERT INTO RECIPES
(DOCTORID, PATIENTID, DESCRIPTION, CREATIONDATE, VALIDITY, PRIORITYID)
VALUES
(0, 0, 'Не бегать в холод без шапки', DEFAULT, 30, 0),
(0, 3, 'Мазь антибаговая втирать в запястья', '2018-11-19', 30, 1),
(0, 2, 'Обязательный субботний отдых с друзьями', '2019-10-19', 60, 1),
(0, 1, 'Десять кубиков памяти внутричерепно', '2019-09-19', 7, 2),
(0, 4, 'Вставать утром с правильной ноги', '2019-11-11', 0, 0),

(1, 5, 'Полоскать рот скороговорками', '2019-11-18', 10, 1),
(1, 6, 'Таблетки с крутыми идеями, ежедневно', DEFAULT, 10, 2),
(1, 1, 'Витамины повышающие чистоту кода', '2019-10-10', 90, 1),
(1, 0, 'Навещать бабушку и кушать фрукты', '2018-09-13', 0, 0),
(1, 3, 'Пересадка бинарного дерева', '2019-11-01', 60, 0),

(2, 4, 'Исправить несколько маленьких багов на 2357-й строке', '2019-11-15', 1, 2),
(2, 2, 'Не выходить за границу массива без разрешения', DEFAULT, 30, 1),
(2, 5, 'Увеличить дозировку радости', '2019-11-18', 14, 2),

(3, 6, 'Ношение монокля на интеллектуальных вечерах', DEFAULT, 90, 0),
(3, 0, 'Очки для сидения за ПеКа', '2017-03-23', 60, 1),
(3, 1, 'Капать в глаз альбуцид', '2018-11-22', 14, 2),
(3, 2, 'Пересадка кибернетического глаза', '2019-11-15', 0, 0);