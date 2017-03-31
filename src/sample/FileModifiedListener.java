package sample;
import java.io.File;

public interface FileModifiedListener {
    void onFileModified(File modifiedFile, boolean contentsModified);
}