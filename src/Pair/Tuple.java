package Pair;

import java.io.File;

public class Tuple<P> {
    private File file;
    private P result;

    public Tuple(File file, P result){
        this.file = file;
        this.result = result;
    }

    public File getFile() {
        return file;
    }

    public P getResult() {
        return result;
    }
}
