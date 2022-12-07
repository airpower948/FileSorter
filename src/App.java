import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class App {
    private static List<String> pathList = new ArrayList<>();
    public static void main(String[] args) throws Exception {
        Files.createDirectories(Paths.get("newDirPath"));
        getPathList("dirPath","newDirPath");
        for(String pathName : pathList){
            System.out.println(pathName);
        }

    }


    public static void getPathList(String dirPath,String newDirPath){
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(dirPath))){
            for(Path path : stream){
                if(!Files.isDirectory(path)){
                    pathList.add(path.getFileName().toString());
                    try{
                        File src = new File(path.toString());
                        File dest = new File(newDirPath+"/"+path.getFileName().toString());
                        Files.copy(src.toPath(),dest.toPath(),StandardCopyOption.REPLACE_EXISTING);
                    }catch (FileAlreadyExistsException e) {
                        e.printStackTrace();
                    }

                }else{
                    try(DirectoryStream<Path> subStream = Files.newDirectoryStream(path)){
                        for(Path path2 : subStream){
                            pathList.add(path2.getFileName().toString());
                            try{
                                File src = new File(path2.toString());
                                File dest = new File(newDirPath+"/"+path2.getFileName().toString());
                                Files.copy(src.toPath(),dest.toPath(),StandardCopyOption.REPLACE_EXISTING);
                            }catch (FileAlreadyExistsException e) {
                                e.printStackTrace();
                            }
                        }
                    }catch(IOException e){
                        e.printStackTrace();
                    }
                    
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
