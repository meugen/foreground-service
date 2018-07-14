package meugeninua.foregroundservice.app.content;

public interface SharedPrefs {

    String getString(String name, String def);

    int getInt(String name, int def);

    boolean getBool(String name, boolean def);

    void putString(String name, String val);

    void putInt(String name, int val);

    void putBool(String name, boolean val);
}
