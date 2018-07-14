CREATE TABLE int_prefs (
 id INTEGER PRIMARY KEY AUTOINCREMENT,
 name VARCHAR(40) NOT NULL,
 val INTEGER NOT NULL);
CREATE UNIQUE INDEX idx_int_prefs_name ON int_prefs (name);

CREATE TABLE string_prefs (
 id INTEGER PRIMARY KEY AUTOINCREMENT,
 name VARCHAR(40) NOT NULL,
 val VARCHAR(200) NOT NULL);
CREATE UNIQUE INDEX idx_string_prefs_name ON string_prefs (name);

CREATE TABLE bool_prefs (
 id INTEGER PRIMARY KEY AUTOINCREMENT,
 name VARCHAR(40) NOT NULL,
 val TINYINT NOT NULL);
CREATE UNIQUE INDEX idx_bool_prefs_name ON bool_prefs (name);