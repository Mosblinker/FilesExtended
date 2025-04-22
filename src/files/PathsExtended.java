/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package files;

import java.io.*;
import java.nio.file.*;
import java.util.*;

/**
 * This includes various functions for altering Paths.
 * @author Mosblinker
 */
public class PathsExtended {
    /**
     * This is the default amount by which to go up in the directory tree to 
     * check to see if a path should be made relative to another path.
     */
    public static final int DEFAULT_RELATIVIZE_DIVERGENCE = 3;
    /**
     * This finds and returns the index of the first mismatch in the names 
     * between two {@code Path}s, starting at the specified index and ignoring 
     * the {@link Path#getRoot() roots} of the paths. If no mismatch is found, 
     * then -1 will be returned. The index will be in the range of {@code 
     * Math.max(fromIndex, 0)}, inclusive, up to the name count of the shorter 
     * path, inclusive. <p>
     * 
     * If the two paths share a common prefix starting at {@code fromIndex}, 
     * ignoring their root, then the returned index is the length of the common 
     * prefix plus {@code fromIndex} and it follows that there is a mismatch 
     * between the two names at that given index within the respective paths. If 
     * one prefix is a proper prefix of the other, then the returned index is 
     * the name count of the sorter path and it follows that the index is only 
     * valid for the longer path. Otherwise, there is no mismatch. <p>
     * 
     * The two non-null paths share a common prefix starting at {@code 
     * fromIndex} of length {@code k} if the following expression is true:
     * <pre><code>
     *      k >= fromIndex &&
     *      k < Math.min(path1.getNameCount(), path2.getNameCount()) &&
     *      path1.subpath(0,k).equals(path2.subpath(0,k)) &&
     *      !path1.getName(k).equals(path2.getName(k))
     * </code></pre>
     * 
     * Note that a returned value of {@code fromIndex} indicates that the names 
     * at the index {@code fromIndex} from each path mismatch.
     * 
     * @param path1 The first path to be tested for a mismatch.
     * @param path2 The second path to be tested for a mismatch.
     * @param fromIndex The index from which to start the search for the 
     * mismatch at.
     * @return The index of the first mismatched name between the two paths 
     * starting at {@code Math.max(fromIndex, 0)}, or -1 if there is no 
     * mismatch.
     * @throws NullPointerException If either path is null.
     * @see Path#startsWith(Path) 
     * @see Path#getName(int) 
     * @see Path#getNameCount() 
     * @see Path#getRoot() 
     * @see Path#subpath(int, int) 
     * @see #mismatch(Path, Path) 
     */
    public static int mismatch(Path path1, Path path2, int fromIndex){
            // Make sure the first path is not null
        Objects.requireNonNull(path1);
            // Make sure the second path is not null
        Objects.requireNonNull(path2);
            // If the two paths are the same path
        if (path1.equals(path2))
            return -1;
            // Get the name count of the smaller of the two paths
        int length = Math.min(path1.getNameCount(), path2.getNameCount());
            // If the offset exceeds the name count of the smaller path
        if (fromIndex >= length)
            return length;
            // If a non-zero, positive starting index was included
        if (fromIndex > 0){
                // Get the paths starting at the starting index
            path1 = path1.subpath(fromIndex, path1.getNameCount());
            path2 = path2.subpath(fromIndex, path2.getNameCount());
        }   // An iterator to go through the first path
        Iterator<Path> itr1 = path1.iterator();
            // An interator to go through the second path
        Iterator<Path> itr2 = path2.iterator();
            // This gets the current index in the two paths
        int index = Math.max(fromIndex, 0);
            // While both paths still have elements left in them
        while (itr1.hasNext() && itr2.hasNext()){
                // If the current two elements don't match
            if (!itr1.next().equals(itr2.next()))
                return index;
            index++;
        }   // If one of the paths ran out of elements before the other one
        if (itr1.hasNext() || itr2.hasNext())
            return index;
        return -1;
    }
    /**
     * This finds and returns the index of the first mismatch in the names 
     * between two {@code Path}s, ignoring the {@link Path#getRoot() roots} of 
     * the paths. If no mismatch is found, then -1 will be returned. The index 
     * will be in the range of 0, inclusive, up to the name count of the shorter 
     * path, inclusive. <p>
     * 
     * If the two paths share a common prefix, ignoring their root, then the 
     * returned index is the length of the common prefix and it follows that 
     * there is a mismatch between the two names at that given index within the 
     * respective paths. If one prefix is a proper prefix of the other, then the 
     * returned index is the name count of the sorter path and it follows that 
     * the index is only valid for the longer path. Otherwise, there is no 
     * mismatch. <p>
     * 
     * The two non-null paths share a common prefix of length {@code k} if the 
     * following expression is true:
     * <pre><code>
     *      k >= fromIndex &&
     *      k < Math.min(path1.getNameCount(), path2.getNameCount()) &&
     *      path1.subpath(0,k).equals(path2.subpath(0,k)) &&
     *      !path1.getName(k).equals(path2.getName(k))
     * </code></pre>
     * 
     * Note that a returned value of 0 indicates that the first names of each 
     * path mismatch.
     * 
     * @param path1 The first path to be tested for a mismatch.
     * @param path2 The second path to be tested for a mismatch.
     * @return The index of the first mismatched name between the two paths, or 
     * -1 if there is no mismatch.
     * @throws NullPointerException If either path is null.
     * @see Path#startsWith(Path) 
     * @see Path#getName(int) 
     * @see Path#getNameCount() 
     * @see Path#getRoot() 
     * @see Path#subpath(int, int) 
     * @see #mismatch(Path, Path, int) 
     */
    public static int mismatch(Path path1, Path path2){
        return mismatch(path1,path2,0);
    }
    /**
     * This returns a version of the given path that is <em>real</em> up to the 
     * given index, exclusive. For more information on what it means to make a 
     * path <em>real</em>, please refer to the {@link Path#toRealPath 
     * Path.toRealPath} method. <p>
     * 
     * This method is equivalent to getting the subpath of the given path, 
     * starting at the start of the path and ending at the given index, 
     * exclusive, and making that subpath <em>real</em> before appending the 
     * rest of the given path to that subpath.
     * 
     * @param path The path to make partially <em>real</em>.
     * @param toIndex The index up to which to make the path <em>real</em>, 
     * exclusive.
     * @param options The options indicating how symbolic links are to be 
     * handled.
     * @return A version of the given path where the path up to the given index, 
     * exclusive, is an absolute path representing the <em>real</em> path of the 
     * file located by the given path.
     * @throws IOException If the file does not exist or an I/O error occurs.
     * @throws SecurityException If a security exception occurs while creating 
     * the <em>real</em> path.
     * @see Path#toRealPath(LinkOption...) 
     * @see Path#subpath(int, int) 
     * @see Path#getNameCount() 
     * @see Path#getRoot() 
     */
    public static Path toRealPath(Path path, int toIndex, LinkOption... options) 
            throws IOException{
            // If none of the path is to be resolved.
        if (toIndex == 0)
            return path;
            // Get the length of the path as the name count
        int length = Objects.requireNonNull(path).getNameCount();
            // If the entire path is to be resolved
        if (toIndex == length)
                // Return the real path of the given path, following symbolic 
            return path.toRealPath(options);    // links if there are any
            // Check the given index
        Objects.checkIndex(toIndex, length);
            // Get a subpath starting from the beginning and ending at the given 
            // index
        Path start = path.subpath(0, toIndex);
            // If the path has a root
        if (path.getRoot() != null)
                // Make sure the subpath has the root of the path
            start = path.getRoot().resolve(start);
            // Get the real path of the subpath, following symbolic links if 
            // there are any, and append the rest of the path to the result.
        return start.toRealPath(options).resolve(path.subpath(toIndex, length));
    }
    /**
     * 
     * @param relPath
     * @param path The path to resolve (cannot be null).
     * @param isDirectory Whether to treat the {@code relPath} path as a 
     * directory. If this is false, then the {@code relPath} path will be 
     * treated as a sibling of the {@code path}.
     * @return 
     * @throws SecurityException
     * @throws IOError
     */
    public static Path resolve(Path relPath, Path path, boolean isDirectory){
            // If the given path is null
        Objects.requireNonNull(path);
            // If the given relative path is null
        Objects.requireNonNull(relPath);
            // If the path is already absolute
        if (path.isAbsolute())
                // Normalize the path
            return path.normalize();
            // Make the relative path absolute and normalized
        relPath = relPath.toAbsolutePath().normalize();
            // If the relative path has no names and no root (the relative path 
            // is empty)
        if (relPath.getNameCount() == 0 && relPath.getRoot() == null)
            return path.normalize();
            // If the relative file is a directory
        if (isDirectory)
                // Resolve the file's path
            path = relPath.resolve(path);
        else    // Resolve the file's path as a sibling of the relative file
            path = relPath.resolveSibling(path);
        return path.normalize();
    }
    /**
     * 
     * @param relPath
     * @param path The path to resolve (cannot be null).
     * @return 
     * @throws SecurityException
     * @throws IOError
     */
    public static Path resolve(Path relPath, Path path){
            // If the given path is null
        Objects.requireNonNull(path);
            // If the given relative path is null
        Objects.requireNonNull(relPath);
        return resolve(relPath,path,Files.isDirectory(relPath));
    }
    /**
     * 
     * @param relPath The path to make the {@code path} relative to.
     * @param path The path to make relative.
     * @param isDirectory Whether to treat the {@code relPath} file as a 
     * directory.
     * @param divergence
     * @param options
     * @return 
     * @throws InvalidPathException
     * @throws SecurityException
     * @throws IOError
     * @throws IllegalArgumentException If the divergence is negative
     */
    public static Path relativize(Path relPath, Path path, boolean isDirectory, 
            int divergence, LinkOption... options){
            // If the given path is null
        Objects.requireNonNull(path);
            // If the given relative path is null
        Objects.requireNonNull(relPath);
            // If the file divergence is negative
        if (divergence < 0)
            throw new IllegalArgumentException();
            // If the given path is not absolute
        if (!path.isAbsolute())
            return path;
            // Make the relative path absolute and normalized
        relPath = relPath.toAbsolutePath().normalize();
            // If the relative path is not a directory
        if (!isDirectory)
                // Get the path of the parent of the path
            relPath = relPath.getParent();
            // If the relative path is null or the relative path's root is null
        if (relPath == null || relPath.getRoot() == null)
            return path;
            // Get the normalized version of the path
        path = path.normalize();
            // If the two paths do not share a root
        if (!relPath.getRoot().equals(path.getRoot()))
            return path;
            // This is the path to check for to see if the should be made 
        Path dirPath = relPath;     // relative
            // Go up the directory tree up until we've reached the desired 
            // location or we've reached the root.
        for (int i = 0; i < divergence && dirPath.getNameCount() > 0;i++){
                // Get the parent of the path
            Path temp = dirPath.getParent();
                // If the parent of the path to check is not null
            if (temp != null)
                dirPath = temp;
            else    // End early, we cannot go any higher
                break;
        }   // If the path starts with the relative path
        if (path.startsWith(dirPath)) 
                // Make the path relative to the relative path
            return relPath.relativize(path);
            // If the two paths should not diverge
        else if (divergence == 0)
            return path;
            // Get the index at which the two paths differ
        int index = mismatch(path,relPath);
            // If the two paths differ, the index is within the range of the two 
            // paths, and the difference on the relative path is greater than 
            // the amount by which to make the relative
        while(index >= 0 && index < path.getNameCount() && 
                index < relPath.getNameCount() && 
                relPath.getNameCount() - index > divergence){
                // Two path variables to store temporary forms of the path and 
                // relative paths when the symlinks are read
            Path tempPath, tempRel;
            try{    // Try to read the symlink at the given index in the path
                tempPath = toRealPath(path,index+1,options);
            } catch (IOException | SecurityException ex){
                tempPath = path;
            }
            try{    // Try to read the symlink at the given index in the 
                    // relative path
                tempRel = toRealPath(relPath,index+1,options);
            } catch (IOException | SecurityException ex){
                tempRel = relPath;
            }    // Get the mismatch between these two new paths
            int index2 = mismatch(tempPath,tempRel,index);
                // If reading the symlinks managed to increase the amount by 
            if (index < index2){    // which the two paths match
                    // Store the new paths and the index of mismatch
                path = tempPath;
                relPath = tempRel;
                index = index2;
            } else 
                // We've either reached a point where the the two paths 
                // completely diverge or we've reached a symlink with a path 
                // that is not found in both paths
                break;
        }   // If enough of the relative path matches the path that they can be 
            // made relative.
        if (relPath.getNameCount() - index <= divergence)
            return relPath.relativize(path);
        return path;
    }
    /**
     * 
     * @param relPath The path to make the {@code path} relative to.
     * @param path The path to make relative.
     * @param divergence
     * @param options
     * @return 
     * @throws InvalidPathException
     * @throws SecurityException
     * @throws IOError
     * @throws IllegalArgumentException If the divergence is negative
     */
    public static Path relativize(Path relPath, Path path, int divergence, 
            LinkOption... options){
            // If the given path is null
        Objects.requireNonNull(path);
            // If the given relative path is null
        Objects.requireNonNull(relPath);
        return relativize(relPath,path,Files.isDirectory(relPath),divergence,
                options);
    }
    /**
     * 
     * @param relPath The path to make the {@code path} relative to.
     * @param path The path to make relative.
     * @param isDirectory Whether to treat the {@code relPath} file as a 
     * directory.
     * @param options
     * @return 
     * @throws InvalidPathException
     * @throws SecurityException
     * @throws IOError
     * @see #DEFAULT_RELATIVE_FILE_DIVERGENCE
     */
    public static Path relativize(Path relPath, Path path, boolean isDirectory, 
            LinkOption... options){
        return relativize(relPath,path,isDirectory,
                DEFAULT_RELATIVIZE_DIVERGENCE,options);
    }
    /**
     * 
     * @param relPath The path to make the {@code path} relative to.
     * @param path The path to make relative.
     * @param options
     * @return 
     * @throws InvalidPathException
     * @throws SecurityException
     * @throws IOError
     * @see #DEFAULT_RELATIVE_FILE_DIVERGENCE
     */
    public static Path relativize(Path relPath,Path path,LinkOption... options){
        return relativize(relPath,path,DEFAULT_RELATIVIZE_DIVERGENCE,options);
    }
    /**
     * This class cannot be constructed.
     */
    private PathsExtended(){}
}
