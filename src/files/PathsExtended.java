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
     * This class cannot be constructed.
     */
    private PathsExtended(){}
}
