package com.example.drugie_podejscie

import android.provider.BaseColumns

// object - singleton - tylko jedna instancja
// definiowanie nazw tabeli, kolumn, stałych ulatwiajacych odwolywanie do SQL
object StudentTable : BaseColumns {
    // Nazwa tabeli
    const val TABLE_NAME = "students"

    // Kolumna ID (Primary Key)
    const val COLUMN_ID = "id"

    // Kolumna na imię
    const val COLUMN_NAME = "name"

    // Kolumna na nazwisko
    const val COLUMN_SURNAME = "surname"

    // Kolumna na uniwersytet
    const val COLUMN_UNIVERSITY = "university"

    // Kolumna na wiek
    const val COLUMN_AGE = "age"

    // Komenda SQL do stworzenia tabeli
    const val SQL_CREATE_TABLE = """
        CREATE TABLE $TABLE_NAME (
            $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $COLUMN_NAME TEXT NOT NULL,
            $COLUMN_SURNAME TEXT NOT NULL,
            $COLUMN_UNIVERSITY TEXT NOT NULL,
            $COLUMN_AGE INTEGER NOT NULL
        )
    """

    // Komenda SQL do usunięcia tabeli
    const val SQL_DELETE_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
}
