BEGIN;
CREATE TABLE IF NOT EXISTS Users(
    id            SERIAL PRIMARY KEY,
    username      VARCHAR(64) NOT NULL UNIQUE,
    password_hash CHAR(128)   NOT NULL,
    avatar        TEXT            NULL DEFAULT 'avatar1.svg'
);
CREATE TABLE IF NOT EXISTS UserTokens(
    user_id     INTEGER  NOT NULL,
    token       CHAR(32) NOT NULL PRIMARY KEY,
    -- TODO: Replace integer with proper timestamp
    expiry_time BIGINT   NOT NULL
);
CREATE TABLE IF NOT EXISTS Games(
    id          SERIAL PRIMARY KEY,
    name        TEXT NOT NULL UNIQUE,
    description TEXT NOT NULL,
    rules       TEXT NOT NULL
);
CREATE TABLE IF NOT EXISTS GameResults(
    id           SERIAL PRIMARY KEY,
    user_id      INTEGER NOT NULL REFERENCES Users(id),
    game_id      INTEGER NOT NULL REFERENCES Games(id),
    result       BOOLEAN     NULL,
    score        INTEGER NOT NULL,
    time_elapsed FLOAT   NOT NULL
);
CREATE TABLE IF NOT EXISTS Achievements(
    id          SERIAL PRIMARY KEY,
    name        TEXT NOT NULL,
    description TEXT NOT NULL
);
CREATE TABLE IF NOT EXISTS AcquiredAchievements(
    user_id        INTEGER NOT NULL REFERENCES Users(id),
    achievement_id INTEGER NOT NULL REFERENCES Achievements(id),
    acquire_time   BIGINT  NOT NULL,
    PRIMARY KEY (user_id, achievement_id)
);
COMMIT;
