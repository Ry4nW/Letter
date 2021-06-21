package sample;

public class File {

    String url = "";

    public File(String str) {
        url = str;
    }

    public Object toURI() {
        return url;
    }
}
