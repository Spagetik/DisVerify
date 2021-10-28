package spagetik.testplugin.sql;

import java.util.UUID;

public class VerifyDataBase extends DataBase {

    public VerifyDataBase(String host, String port, String name, String user, String pass) {
        super(host, port, name, user, pass);
        this.SendSqlRequest("CREATE TABLE IF NOT EXISTS verify_codes (uuid varchar(36) NOT NULL, code varchar(6) NOT NULL)", null);
    }

    public boolean addCodeToDb (UUID uuid, int code) {
        String[] data = new String[2];
        data[0] = String.valueOf(uuid);
        data[1] = String.valueOf(code);
        this.SendSqlRequest("INSERT INTO verify_codes (uuid, code) VALUES (?, ?)", data);
        return true;
    }

    public boolean removeCodeFromDb (int code) {
        String[] del_data = new String[1];
        del_data[0] = String.valueOf(code);
        this.SendSqlRequest("DELETE FROM verify_codes WHERE code = ?", del_data);
        return true;
    }
}
