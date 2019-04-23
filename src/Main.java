import executors.Md5Executor;
import fileWorker.FileWorker;
import interfaceExecutable.IExecutable;

public class Main {
    public static void main(String[] args) {
        FileWorker fw = new FileWorker("C:\\Users\\Данил\\Documents\\URFUshechka\\flp\\lection02-master\\measures", true);
        IExecutable ie = new Md5Executor();
        fw.execute(ie);
        fw.flush();
    }
}
