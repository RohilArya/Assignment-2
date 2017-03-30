package sample;

import java.io.File;

/**
 * Created by 100585195 on 3/29/2017.
 */
public class DataType {
    private String serverFilename;
    private String ClientFilename ;
    public void MyDataType(String serverFilename, String ClientFilename) {
        serverFilename = this.serverFilename;
        this.ClientFilename = ClientFilename ;
    }
    public String getServerFilename() {
        return serverFilename ;
    }
    public String getStringValue() {
        return ClientFilename ;
    }
}
