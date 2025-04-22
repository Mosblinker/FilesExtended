/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package files;

import java.awt.Component;
import java.awt.Toolkit;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * This includes various functions for altering files and file paths.
 * @author Milo Steier
 */
public class FilesExtended {
    /**
     * This contains the maximum loops to attempt to alter a file, 
     * 2<sup>10</sup>.
     */
    public static final int MAX_LOOP_AMOUNT = (int) Math.pow(2, 10);
    /**
     * The regular expression pattern to use to split file paths up when 
     * converting a String into a list of file paths.
     */
    protected static final String PATH_SEPARATOR_REGEX="\"[,"+File.pathSeparator+"\\s]\\s*\"?";
    /**
     * A Pattern used for splitting file paths when converting a String into a 
     * list of file paths.
     * @see #PATH_SEPARATOR_REGEX
     */
    protected static final Pattern PATH_SEPARATOR_PATTERN = 
            Pattern.compile(PATH_SEPARATOR_REGEX);
    /**
     * The template for the regular expression pattern to use to retrieve a 
     * file number. This requires two string arguments, which are the left 
     * bracket and the right bracket.
     */
    protected static final String FILE_NUMBER_REGEX = "(\\Q%s\\E\\d+\\Q%s\\E)";
    
    /**
     * This removes quotation marks around a String.
     * @param str The String to alter.
     * @return The String with the surrounding quotations removed.
     */
    public static String removeQuotations(String str) {
        if (str.isBlank())      // If the path is blank
            return "";
        if (str.contains("\"")) // If the string contains a quotation mark
            str = str.substring(str.indexOf("\"")+1);
        if (str.contains("\"")) // If the string still contains a quotation mark
            str = str.substring(0,str.lastIndexOf("\""));
        return str.trim();
    }
    /**
     * This removes the quotation marks around a file.
     * @param file The file to alter.
     * @return The file with the surrounding quotations removed.
     */
    public static File removeQuotations(File file) {
        return new File(removeQuotations(file.toString()));
    }
    /**
     * This removes the file extension from the given file path.
     * @param file The path of the file to remove the extension from.
     * @return The file path with no extension.
     */
    public static String removeFileExtension(String file) {
        return(file.isBlank())?"":FilesExtended.removeFileExtension(new File(file));
    }
    /**
     * This removes the file extension from the given file.
     * @param file The file to remove the extension from.
     * @return The path of the file, with no extension.
     */
    public static String removeFileExtension(File file) {
        if (file == null)                   // If the file is null
            return "";
        String ext = getFileExtension(file);// Gets the file extension
        String name = file.toString();      // Gets the file, as a String
        return(ext.isBlank())?name:name.substring(0,name.length()-ext.length()-1);
    }
    /**
     * This gets the file extension from the given file path.
     * @param file The file path to retrieve the extension from.
     * @return The extension of the given file path.
     */
    public static String getFileExtension(String file) {
        return(file.isBlank())?"":FilesExtended.getFileExtension(new File(file));
    }
    /**
     * This gets the file extension from the given file.
     * @param file The file path to retrieve the extension from.
     * @return The extension of the given file.
     */
    public static String getFileExtension(File file) {
            // If the file is null or not a normal file
        if (file==null || !file.isFile())
            return "";
        String name = file.getName().trim();    // Gets the file name
        if (name.isEmpty())                     // If there is no file name
            return "";
        return (name.contains("."))?name.substring(name.lastIndexOf(".")+1):"";
    }
    /**
     * This returns whether the given file path ends with the given file 
     * extension.
     * @param file The file path to check for the given extension.
     * @param extension The file extension to check for.
     * @return Whether the given file ends with the given extension.
     */
    public static boolean endsWithFileExtension(String file, String extension){
            // If either the given file or extension were null
        if (file == null || extension == null)
            return false;
        return file.trim().toLowerCase().endsWith(extension.trim().toLowerCase());
    }
    /**
     * This returns whether the given file path ends with the given file 
     * extension.
     * @param file The file path to check for the given extension.
     * @param extension The file extension to check for.
     * @return Whether the given file ends with the given extension.
     */
    public static boolean endsWithFileExtension(File file, String extension){
        return file != null && endsWithFileExtension(file.toString(),extension);
    }
    /**
     * This returns a Matcher that can be used to find the file number in the 
     * name of the given file.
     * @param file The file to go through.
     * @param leftBracket The left bracket for the file number.
     * @param rightBracket The right bracket for the file number.
     * @return A Matcher used for finding the file number in the file name.
     */
    protected static Matcher getFileNumberMatcher(File file,String leftBracket,
            String rightBracket){
        return Pattern.compile(String.format(FILE_NUMBER_REGEX, leftBracket,
                rightBracket)).matcher(file.toString()).region(
                        (file.getParent()!=null)?file.getParent().length()+1:0, 
                        removeFileExtension(file).length());
    }
    /**
     * This splits the given file path around the file number previously found 
     * by the given Matcher. If the previous attempt to find a file number had 
     * failed, then the file name will be split at the file extension. <p>
     * Precondition: This assumes that an attempt has been made with the given 
     * Matcher to find a file number in the given file.
     * @param file The file path to split.
     * @param matcher The Matcher used to find the file number in the given 
     * file.
     * @return An array containing the file path split around the file number.
     */
    protected static String[] splitAtFileNumber(File file, Matcher matcher){
        String fileName = file.toString();  // Gets the file as a String
        try{
            return new String[]{fileName.substring(0, matcher.start()),
                fileName.substring(matcher.end())};
        } // Occurs if the previous match opperation failed, i.e. no file number
        catch (IllegalStateException ex){   
                // Get the file extension
            String ext = getFileExtension(file);
            if (!ext.isBlank()) // If there is a file extension
                ext = "." +ext;
            fileName = fileName.substring(0,fileName.length()-ext.length());
                // If the start of the file name is not just the parent folder
            if (!fileName.isBlank()&&!fileName.equals(file.getParent()+
                    File.separator))
                fileName += " ";
            return new String[]{fileName,ext};
        }
    }
    /**
     * This returns the value of the number in the next group that the given
     * Matcher finds. <p>
     * Precondition: The next group in the given Matcher must have a number in 
     * between the two given Strings.
     * Postcondition: This progresses the given Matcher.
     * @param matcher The Matcher to use to find the file number.
     * @param leftBracket The left bracket for the file number.
     * @param rightBracket The right bracket for the file number.
     * @return The value of the number in the next group found by the given 
     * matcher, or null if there is no file number.
     */
    private static Integer getCurrentFileNumber(Matcher matcher, 
            String leftBracket, String rightBracket){
        if (!matcher.find())            // If there is no matching file number
            return null;
        String value = matcher.group(); // Gets the value from the matcher
        try{
            return Integer.parseInt(value.substring(leftBracket.length(), 
                value.length()-rightBracket.length()));
        }
        catch(NumberFormatException ex){// Likely not to occur, but just in case
            return null;
        }
    }
    /**
     * This returns the value of the first number that is in between the given 
     * Strings in the name of the given file.
     * @param file The file to get the number from.
     * @param leftBracket The left bracket for the file number.
     * @param rightBracket The right bracket for the file number.
     * @return The value of the first number in the given brackets in the name,
     * or null if there is no file number.
     */
    protected static Integer getCurrentFileNumber(File file, String leftBracket,
            String rightBracket){
        return getCurrentFileNumber(getFileNumberMatcher(file,leftBracket,
                rightBracket),leftBracket,rightBracket);
    }
    /**
     * This returns the value of the first number in parenthesis in the name of 
     * the given file.
     * @param file The file to get the number from.
     * @return The value of the first number in parenthesis in the name, or null
     * if there is no file number.
     */
    public static Integer getCurrentFileNumber(File file){
        return getCurrentFileNumber(file,"(",")");
    }
    /**
     * This concatenates the first two Strings in the given array, with the 
     * given number in between the two brackets situated in between the Strings 
     * from the given array. 0 will remove the number if it is at the end of the 
     * file path.
     * @param file The original version of the file path.
     * @param sections The array containing the two sections of the file path 
     * (Must have at least two elements).
     * @param leftBracket The left bracket for the file number.
     * @param rightBracket The right bracket for the file number.
     * @param num The number to put in brackets.
     * @return The finished path.
     */
    protected static String changeFileNumber(File file,String[] sections,
            String leftBracket, String rightBracket, int num){
            // If the new number is 0 and the file number is not at the start 
            // of the file name
        if (num == 0 && !sections[0].isBlank() && !sections[0].equals(
                file.getParent()+File.separator)){
                // If the number was at the end and came after a space
            if (sections[0].charAt(sections[0].length()-1)==' '&&
                    sections[1].equals("."+getFileExtension(file)))
                return sections[0].trim()+sections[1];
        }
        return sections[0]+leftBracket+num+rightBracket+sections[1];
    }
    /**
     * This changes the first number that is in between the given brackets in 
     * the given file path, or it will add one if there is no number. 0 will 
     * remove the number if it is at the end of the file path. <p>
     * Precondition: The number must not be negative.
     * @param file The file to alter.
     * @param leftBracket The left bracket for the file number.
     * @param rightBracket The right bracket for the file number.
     * @param num The number to put in the brackets.
     * @return The altered file name.
     * @throws IllegalArgumentException If the number is negative.
     */
    protected static File changeFileNumber(File file, String leftBracket,
            String rightBracket, int num) {
        if (num < 0)    // If the number is negative
            throw new IllegalArgumentException("Number cannot be negative (" + 
                    num + " < 0)");
        if (file.getName().isBlank())    // If the file name is blank
            return file;
            // The matcher used to find the file number
        Matcher matcher = getFileNumberMatcher(file,leftBracket,rightBracket);
            // Get the old file number
        Integer oldNum = getCurrentFileNumber(matcher,leftBracket,rightBracket);
            // If the new number matches the old number
        if ((oldNum == null && num == 0) || Objects.equals(oldNum, num))
            return file;
        return new File(changeFileNumber(file,splitAtFileNumber(file,matcher),
                leftBracket,rightBracket,num));
    }
    /**
     * This changes the first number in parenthesis in the given file path, or 
     * it will add one if there is no number. 0 will remove the number if it is 
     * at the end of the file path. <p>
     * Precondition: The number must not be negative.
     * @param file The file to alter.
     * @param num The number to put in parenthesis.
     * @return The altered file name.
     * @throws IllegalArgumentException If the number is negative.
     */
    public static File changeFileNumber(File file, int num) {
        return changeFileNumber(file,"(",")",num);
    }
    /**
     * This gets the next available file path, based on the first number in 
     * parenthesis.
     * @param file The file to alter.
     * @param leftBracket The left bracket for the file number.
     * @param rightBracket The right bracket for the file number.
     * @return The next available file path.
     */
    protected static File getNextAvailableFilePath(File file,String leftBracket,
            String rightBracket) {
        if (file.getName().isBlank())    // If the file name is blank
            return file;
            // The matcher used to find the file number
        Matcher matcher = getFileNumberMatcher(file,leftBracket,rightBracket);
            // Gets the current file number, or 0 of there is no file number 
        int num = Objects.requireNonNullElse(getCurrentFileNumber(matcher,
                leftBracket,rightBracket), 0);
            // This gets the sections of the file name.
        String[] sections = splitAtFileNumber(file,matcher);
        File temp = null;   // This gets the next available file path
            // A for loop to find the next available file path
        for (int pos = num + 1; temp == null || temp.exists(); pos++){
            temp = new File(changeFileNumber(file,sections,leftBracket,
                    rightBracket,pos));
        }
        return temp;
    }
    
    /**
     * This gets the next available file path, based on the first number in 
     * parenthesis.
     * @param file The file to alter.
     * @return The next available file path.
     */
    public static File getNextAvailableFilePath(File file) {
        return getNextAvailableFilePath(file,"(",")");
    }
    /**
     * This attempts to rename the given source file to the given target file.
     * <p> Precondition: The source file must exist. <p>
     * Precondition: The target file must not exist.
     * @param source The file to rename.
     * @param target The file path to rename to.
     * @return If the file was successfully renamed.
     * @throws FileNotFoundException If the source file does not exist.
     * @throws FileAlreadyExistsException If the target file exists.
     */
    public synchronized static boolean rename(File source, File target) throws 
            FileNotFoundException, FileAlreadyExistsException {
        if (source.equals(target))  // If the source and the target are the same
            return true;
        if (!source.exists())   // If the source does not exist
            throw new FileNotFoundException("Source file, \""+ source + 
                    "\", does not exist");
        if (target.exists())    // If the target file exists
            throw new FileAlreadyExistsException(target.toString(),
                    source.toString(),
                    "Target file, \"" + target + "\", already exists");
            // While the file has not been moved
        for (int tr = 0; tr < MAX_LOOP_AMOUNT && source.exists(); tr++) {
            try {
                try{
                    Files.move(source.toPath(), target.toPath(), 
                            StandardCopyOption.REPLACE_EXISTING,
                            StandardCopyOption.ATOMIC_MOVE);
                }
                catch (AtomicMoveNotSupportedException exc){
                    Files.move(source.toPath(), target.toPath(), 
                            StandardCopyOption.REPLACE_EXISTING);
                }
                return true;
            }
            catch (IOException exc) { }
            catch (SecurityException exc) {
                return false;
            }
        }
        return false;
    }
    /**
     * This attempts to delete the given file.
     * @param source The file to delete.
     * @return Whether the file was successfully deleted.
     */
    public synchronized static boolean delete(File source) {
        if (!source.exists())   // If the file does not exist
            return true;
            // While the file has not been deleted
        for (int tr = 0; tr < MAX_LOOP_AMOUNT && source.exists(); tr++) {
            try {   // If the file has been deleted
                if (Files.deleteIfExists(source.toPath()))
                    return true;
            }
            catch (IOException exc) { }
            catch (SecurityException exc){
                return false;
            }
        }
        return false;
    }
    /**
     * This attempts to set whether the given file is hidden. <p>
     * On UNIX systems, files are considered hidden when the name begins with a 
     * period. On Microsoft Windows systems, a file is considered hidden when it
     * is marked as hidden in the file system.
     * @param file The file to set the attribute of.
     * @param hide Whether the file should be hidden.
     * @return The abstract pathname directing to the hidden file, or null if 
     * unsuccessful in setting the attribute of the file.
     * @throws java.io.FileNotFoundException If the file does not exist.
     * @see File#isHidden() 
     */
    public synchronized static File setHiddenAttribute(File file,boolean hide)
            throws FileNotFoundException{
        if (!file.exists()) // If the file does not exist
            throw new FileNotFoundException("File, \""+file+"\", does not exist");
        try{
            try{    // A for loop to attempt to apply the dos:hidden attribute
                for (int t = 0;t< MAX_LOOP_AMOUNT&&file.isHidden() != hide;t++){
                    try{
                        file = Files.setAttribute(file.toPath(), "dos:hidden", hide).toFile();
                    }
                    catch (IOException ex){}
                }
            }   // Catches it if the dos attribute is not supported
            catch (UnsupportedOperationException ex){
                String name = file.getName();       // The name of the file
                    // If whether it starts with a period does not match up with
                if(name.startsWith(".")!=hide){//whether the file is to be hidden
                    String parent = file.getParent();   // Gets the parent
                        // The new name for the file
                    File temp=new File(((parent!=null)?parent+File.separator:"")
                            +((hide)?"."+name:name.substring(1)));
                        // A for loop to try and rename the file. If successfully
                        // renamed, but the file is still hidden, this causes 
                        // the file to be reverted back to the original name
                    for (int l = 0; l < 2 && file.isHidden() != hide;l++){
                        try {   // If the file was successfully renamed
                            if (rename(file,temp)){ 
                                File t = file;//Temporarilly stores the old name
                                file = temp;
                                temp = t;
                            }
                            else
                                return null;
                        } catch (IOException ex1) {
                            return null;
                        }
                    }
                }
            }
        }
        catch (SecurityException exc){
            return null;
        }
        return (file.isHidden() == hide) ? file : null;
    }
    /**
     * This adds files from the given folder, inclusive, to the given ArrayList.
     * @param file The file or folder to add.
     * @param files The ArrayList of files to add to.
     * @return The ArrayList of files after the found files have been added.
     */
    public static ArrayList<File>getFilesFromFolder(File file,
            ArrayList<File>files) {
        return getFilesFromFolder(file,files,-1);
    }
    /**
     * This returns the files in the given folder, inclusive.
     * @param file The file or folder to add.
     * @return An ArrayList of files that have been found.
     */
    public static ArrayList <File> getFilesFromFolder(File file) {
        return getFilesFromFolder(file,new ArrayList<File>());
    }
    /**
     * This adds files from the given folder, inclusive, to the given ArrayList,
     * using the given FileFilter.
     * @param file The file or folder to add.
     * @param files The ArrayList of files to add to.
     * @param filter The filter for the files, or null if no filter.
     * @return The ArrayList of files after the found files have been added.
     */
    public static ArrayList <File> getFilesFromFolder(File file, 
            ArrayList <File> files, java.io.FileFilter filter) {
        return getFilesFromFolder(file,files,filter,-1);
    }
    /**
     * This returns the files in the given folder, inclusive, using the given 
     * FileFilter.
     * @param file The file or folder to add.
     * @param filter The filter for the files, or null if no filter.
     * @return An ArrayList of files that have been found.
     */
    public static ArrayList <File> getFilesFromFolder(File file, 
            java.io.FileFilter filter) {
        return getFilesFromFolder(file,new ArrayList<File>(),filter);
    }
    /**
     * This adds files from the given folder, inclusive, to the given ArrayList,
     * up until the given subfolder depth.
     * @param file The file or folder to add.
     * @param files The ArrayList of files to add to.
     * @param depth The maximum subfolder depth, or negative if unlimited.
     * @return The ArrayList of files after the found files have been added.
     */
    public static ArrayList <File> getFilesFromFolder(File file, 
            ArrayList <File> files, int depth) {
        return getFilesFromFolder(file,files,null,depth);
    }
    /**
     * This adds files from the given folder, inclusive, up until the given 
     * subfolder depth.
     * @param file The file or folder to add.
     * @param depth The maximum subfolder depth, or negative if unlimited.
     * @return An ArrayList of files that have been found.
     */
    public static ArrayList <File> getFilesFromFolder(File file, int depth) {
        return getFilesFromFolder(file,new ArrayList<File>(),depth);
    }
    /**
     * This adds files from the given folder, inclusive, to the given ArrayList,
     * using the given FileFilter and up until the given subfolder depth.
     * @param file The file or folder to add.
     * @param files The ArrayList of files to add to.
     * @param filter The filter for the files, or null if no filter.
     * @param depth The maximum subfolder depth, or negative if unlimited.
     * @return The ArrayList of files after the found files have been added.
     */
    public static ArrayList <File> getFilesFromFolder(File file, 
            ArrayList <File> files, java.io.FileFilter filter, int depth) {
        if (filter == null)     // If the filter is null
            filter = new java.io.FileFilter() {
                @Override
                public boolean accept(File pathname) {
                    return true;
                }
            };
        return getFilesFromFolder(file,files,filter,depth,0);
    }
    /**
     * This adds files from the given folder, inclusive, using the given 
     * FileFilter and up until the given subfolder depth.
     * @param file The file or folder to add.
     * @param filter The filter for the files, or null if no filter.
     * @param depth The maximum subfolder depth, or negative if unlimited.
     * @return An ArrayList of files that have been found.
     */
    public static ArrayList <File> getFilesFromFolder(File file, 
            java.io.FileFilter filter, int depth) {
        return getFilesFromFolder(file,new ArrayList<File>(), filter,depth);
    }
    /**
     * This adds files from the given folder, inclusive, to the given ArrayList,
     * using the given FileFilter, up until the given maximum subfolder depth.
     * @param file The file or folder to add.
     * @param files The ArrayList of files to add to.
     * @param filter The filter for the files.
     * @param depth The maximum subfolder depth, or negative if unlimited.
     * @param curDepth The current subfolder depth.
     * @return The ArrayList of files after the found files have been added.
     */
    protected static ArrayList <File> getFilesFromFolder(File file, 
            ArrayList <File> files, java.io.FileFilter filter, int depth, 
            int curDepth) {
            // If the current depth is greater than the maximum depth and the 
        if (depth >= 0 && curDepth > depth) // maximum depth is not negative
            return files;
        if (filter.accept(file)){       // If the file itself is accepted
            if (!files.contains(file))  // If the file has not been added yet
                files.add(file);
            if (file.isDirectory()) {   // If the file is a directory
                    // Gets all the files in the folder
                File[] arr=file.listFiles(filter);
                if (arr != null)        // If the array is not null
                    for (File temp :arr)// For all the files in the folder
                        getFilesFromFolder(temp,files,filter,depth,curDepth+1);
            }
        }
        return files;
    }
    /**
     * This adds files from a String listing the files to an ArrayList of Files.
     * <p>
     * Postcondition: This will alter the given ArrayList.
     * @param fileList The String holding the file paths.
     * @param files The ArrayList of files to add to.
     * @return The given ArrayList after the files have been added.
     */
    public static ArrayList<File> getFilesFromString(String fileList, 
            ArrayList<File> files) {
        fileList = removeQuotations(fileList);
            // Matcher used to split the file paths
        Matcher matcher = PATH_SEPARATOR_PATTERN.matcher(fileList);
        int start = 0;          // The start of the next file path
        while(matcher.find()){  // If the matcher finds something
                // Get the next file path, with the quotations removed
            String temp=fileList.substring(start,matcher.start());
            if (!temp.isBlank())    // If the file path is blank
                files.add(new File(temp));
            start = matcher.end();
        }
        fileList = fileList.substring(start);
        if (!fileList.isBlank())    // If the file path is blank
            files.add(new File(fileList));
        return files;
    }
    /**
     * This gets files from a String listing the files to an ArrayList of Files.
     * @param fileList The String holding the file paths.
     * @return The ArrayList of files.
     */
    public static ArrayList<File> getFilesFromString(String fileList) {
        return getFilesFromString(fileList,new ArrayList<File>());
    }
    /**
     * This converts a <code>javax.swing.filechooser.FileFilter</code> into a
     * <code>java.io.FileFilter</code>.
     * @param filter The <code>javax.swing.filechooser.FileFilter</code> to 
     * convert.
     * @return The <code>java.io.FileFilter</code> version of the given filter.
     * @see java.io.FileFilter
     * @see javax.swing.filechooser.FileFilter
     */
    public static java.io.FileFilter convertFileFilter(
            javax.swing.filechooser.FileFilter filter) {
        return new java.io.FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return filter.accept(pathname);
            }
        };
    }
    /**
     * This converts a <code>java.io.FileFilter</code> into a
     * <code>javax.swing.filechooser.FileFilter</code>.
     * @param filter The <code>java.io.FileFilter</code> to convert.
     * @param description The description for the 
     * <code>javax.swing.filechooser.FileFilter</code>.
     * @return The <code>javax.swing.filechooser.FileFilter</code> version of 
     * the given filter.
     * @see java.io.FileFilter
     * @see javax.swing.filechooser.FileFilter
     */
    public static javax.swing.filechooser.FileFilter convertFileFilter(
            java.io.FileFilter filter, String description){
        return new javax.swing.filechooser.FileFilter(){
            @Override
            public boolean accept(File f) {
                return filter.accept(f);
            }
            @Override
            public String getDescription() {
                return description;
            }
        };
    }
    /**
     * This removes duplicate files from the given List of Files. <p>
     * Postcondition: This will alter the given List.
     * @param files The List of files.
     * @return The List of files with duplicates removed.
     */
    public static List <File> removeDuplicateFiles(List <File> files){
            // A for loop to find duplicates
        for (int pos = 0; pos < files.size(); pos++) {
            File temp = files.get(pos);     // The element to check for
                // While the last index of the element is not the current one
            while (files.lastIndexOf(temp) != pos){
                files.remove(files.lastIndexOf(temp));
            }
        }
        return files;
    }
    /**
     * This returns a String stating all non-duplicated files, separating them 
     * with the native system's path separator. <p>
     * Postcondition: This will alter the given List.
     * @param files The List of files to list.
     * @return A String stating all non-duplicate files.
     */
    public static String getFileList(List<File> files){
        removeDuplicateFiles(files);
        if (files.isEmpty())     // If there are no files.
            return "";
        String paths = "";       // This gets the string containing the files
        for (File temp : files)  // A for loop to go though the files
            paths += "\"" + temp + "\"" + File.pathSeparator + " ";
        return paths.substring(0,paths.length()-(File.pathSeparator.length()+1));
    }
    /**
     * This generates a FileNameExtensionFilter with the given description and 
     * file extensions, and lists the valid extensions in the description.
     * @param description The description to use.
     * @param extensions The file extensions to use.
     * @return The FileNameExtensionFilter with the given description and 
     * file extensions.
     */
    public static FileNameExtensionFilter generateExtensionFilter(
            String description, String... extensions){
        description += " (";
        for (String temp : extensions)  // A for loop to add the extensions.
            description += "*." + temp + File.pathSeparator;
        description = description.substring(0, description.length()-1) + ")";
        return new FileNameExtensionFilter(description,extensions);
    }
    /**
     * This is a helper function that attempts to create the given directories, 
     * opening an error JOptionPane if failed, and returns whether it was 
     * successful.
     * @param parent The parent component for the JOptionPane showed if the 
     * directory fails to be created.
     * @param dir The directory to create.
     * @param multiple If there will be multiple directories created.
     * @return Whether this was successful at creating the directories.
     * @see #createDirectories(java.awt.Component, java.io.File) 
     */
    private static boolean createDirectories(Component parent, File dir, 
            boolean multiple) {
        String message;     // The message to display if something goes wrong
        try {
            Files.createDirectories(dir.toPath());
            return true;
        } catch(FileAlreadyExistsException exc) {
                // The directory already exists as a file
            if (multiple)   // If multiple directories were suppose to be created
                message = "One of the directories";
            else
                message = "The specified directory";
            message += " already exists as a file.";
        } catch (IOException | SecurityException exc) {
                // Some error occurred while creating the directory
            message = String.format("An error occurred while creating the %s.",
                    (multiple)?"directories":"specified directory");
        }
        Toolkit.getDefaultToolkit().beep();
        JOptionPane.showMessageDialog(parent,message,
                "ERROR - Error Creating Director"+((multiple)?"ies":"y"),
                JOptionPane.ERROR_MESSAGE);
        return false;
    }
    /**
     * This attempts to create the given directory, opening an error 
     * JOptionPane if failed, and returns whether it was successful.
     * @param parent The parent component for the JOptionPane showed if the 
     * directory fails to be created.
     * @param dir The directory to create.
     * @return Whether the directory was successfully created or previously 
     * existed as a directory.
     */
    public static boolean createDirectories(Component parent, File dir){
            // If the directory file does not exist
        if (!dir.exists() || !dir.isDirectory()) {
                // Get the directory file's parent
            File dirParent = dir.getParentFile();
                // Try to create the directory and return whether the directory 
                // was created successfully. We determine if multiple 
                // directories will be created by checking to see if the 
                // directory has a parent that exists and is also a directory. 
                // If the directory's parent exists and is a directory, then 
                // only the given directory will be created
            return createDirectories(parent,dir,dirParent!=null&&
                    !(dirParent.exists()&&dirParent.isDirectory()));
        }
        return true;
    }
    /**
     * 
     * @param relFile
     * @param file The file to resolve (cannot be null).
     * @param isDirectory Whether to treat the {@code relFile} file as a 
     * directory. If this is false, then the {@code relFile} file will be 
     * treated as a sibling of the {@code file}.
     * @return 
     * @throws InvalidPathException
     * @throws SecurityException
     * @throws IOError
     */
    public static File resolve(File relFile, File file, boolean isDirectory){
            // Ensure that the file is not null
        Objects.requireNonNull(file);
            // Ensure that the relative file is not null
        Objects.requireNonNull(relFile);
        return PathsExtended.resolve(relFile.toPath(), file.toPath(),
                isDirectory).toFile();
    }
    /**
     * 
     * @param relFile
     * @param file The file to resolve (cannot be null).
     * @return 
     * @throws InvalidPathException
     * @throws SecurityException
     * @throws IOError
     */
    public static File resolve(File relFile, File file){
            // Ensure that the file is not null
        Objects.requireNonNull(file);
            // Ensure that the relative file is not null
        Objects.requireNonNull(relFile);
        return resolve(relFile,file,relFile.isDirectory());
    }
    /**
     * 
     * @param relFile The file to make the file relative to.
     * @param file The file to make relative.
     * @param isDirectory Whether to treat the {@code relFile} file as a 
     * directory.
     * @param divergence
     * @param options
     * @return 
     * @throws InvalidPathException
     * @throws SecurityException
     * @throws IOError
     * @throws IllegalArgumentException If the divergence is negative
     */
    public static File relativize(File relFile, File file, 
            boolean isDirectory, int divergence, LinkOption... options){
            // Ensure that the file is not null
        Objects.requireNonNull(file);
            // Ensure that the relative file is not null
        Objects.requireNonNull(relFile);
        return PathsExtended.relativize(relFile.toPath(), file.toPath(), 
                isDirectory, divergence, options).toFile();
    }
    /**
     * 
     * @param relFile The file to make the file relative to.
     * @param file The file to make relative.
     * @param divergence
     * @param options
     * @return 
     * @throws InvalidPathException
     * @throws SecurityException
     * @throws IOError
     * @throws IllegalArgumentException If the divergence is negative
     */
    public static File relativize(File relFile, File file, int divergence, 
            LinkOption... options){
            // Ensure that the file is not null
        Objects.requireNonNull(file);
            // Ensure that the relative file is not null
        Objects.requireNonNull(relFile);
        return relativize(relFile,file,relFile.isDirectory(),divergence,
                options);
    }
    /**
     * 
     * @param relFile The file to make the file relative to.
     * @param file The file to make relative.
     * @param isDirectory Whether to treat the {@code relFile} file as a 
     * directory.
     * @param options
     * @return 
     * @throws InvalidPathException
     * @throws SecurityException
     * @throws IOError
     * @see PathsExtended#DEFAULT_RELATIVIZE_DIVERGENCE
     */
    public static File relativize(File relFile, File file, 
            boolean isDirectory, LinkOption... options){
        return relativize(relFile,file,isDirectory,
                PathsExtended.DEFAULT_RELATIVIZE_DIVERGENCE,options);
    }
    /**
     * 
     * @param relFile The file to make the file relative to.
     * @param file The file to make relative.
     * @param options
     * @return 
     * @throws InvalidPathException
     * @throws SecurityException
     * @throws IOError
     * @see PathsExtended#DEFAULT_RELATIVIZE_DIVERGENCE
     */
    public static File relativize(File relFile, File file, 
            LinkOption... options){
        return relativize(relFile,file,
                PathsExtended.DEFAULT_RELATIVIZE_DIVERGENCE,options);
    }
    /**
     * This class cannot be constructed.
     */
    private FilesExtended(){}
}
