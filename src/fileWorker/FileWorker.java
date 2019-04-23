package fileWorker;

import Pair.Tuple;
import interfaceExecutable.IExecutable;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FileWorker {
    private boolean isRecursive = true;
    private final File initFile;
    private final String path;
    private ArrayList<Tuple> result = new ArrayList<>();


    public FileWorker(String path){
        this.path = path;
        this.initFile = new File(path);
    }

    public FileWorker(String path, boolean isRecursive){
        this.path = path;
        this.initFile = new File(path);
        this.isRecursive = isRecursive;
    }

    public void execute(IExecutable command){
        recursiveExecute(command, this.initFile);
    }

    private void recursiveExecute(IExecutable command, File file) {
        if (file.isDirectory()) {
            File[] attachments = file.listFiles();

            for (File attachment : attachments) {
                if (attachment.isDirectory() & isRecursive)
                    recursiveExecute(command, attachment);

                result.add(command.process(attachment));
            }
        }
        result.add(command.process(file));
    }

    public void flush(){
        try(FileWriter fw = new FileWriter("result.txt")){
            for(Tuple pair: result)
                fw.write(String.format("%s : %s\n", pair.getFile().getPath().substring(path.length()),pair.getResult()));

            this.result.clear();
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
    }

    public Object getResult(File file) {
        for(Tuple pair: result){
            if(file == pair.getFile())
                return pair.getResult();
        }

        return "No such file exists";
    }

    public void setRecursivity(boolean bool){
        this.isRecursive = bool;
    }

    public boolean getRecursivity(){
        return this.isRecursive;
    }

    public String getPath() {
        return path;
    }

    public File getInitFile(){
        return initFile;
    }
}
