/*
 * Class: CISC 3130
 * Section: TY2
 * EmplId: 23809622
 * Name: Liyu Lin
 */
package moviebst;
import java.io.*;
/**
 *
 * @author linliyu
 */
public class MovieBST {

    //A node represents a movie
    class Movie{
        private String title;
        //private int releaseYear;
        private Movie left, right;
        
        public Movie(String title){
            this.title = title;
           // this.releaseYear = releaseYear;
            this.left = this.right = null;
        }   
    }//end class Movie
    
    //Root of MovieBST
    private Movie first;
    
    //no-arg constructor
    public MovieBST(){
        first = null;
    }
    //Constructor
    public MovieBST(Movie first){
        this.first = first;
    }
    
    //this method mainly calls insertRec()
    public void insert(String title){
        first = insertRec(first, title);
    }
    //A recursive function to insert a new movie in MovieBST
    public Movie insertRec(Movie first, String title){
        //if the tree is empty, return a new node
        if (first == null){
            first = new Movie(title);
            return first;
        }
        //otherwise, resur down the tree
        if(title.compareTo(first.title) < 0)
            first.left = insertRec(first.left, title);
        else if(title.compareTo(first.title) > 0)
            first.right = insertRec(first.right, title);
        
        //return the unchanged node pointer
        return first;
    }
    
    //This method mainly calls inorderRec()
    public void inorder(){
        inorderRec(first);
    }
    //A recursive function to do inorder traversal of MovieBST
    public void inorderRec(Movie first){
        if(first != null){
            inorderRec(first.left);
            System.out.println(first.title);
            inorderRec(first.right);
        }
    }
    
    //A recursive function selects movie titles fall alphabetically between start and end.
    public void subSet(MovieBST subTree, Movie first, String start, String end){
        if(first == null){
            return;
        }
        if(start.compareTo(first.title) < 0){
            subSet(subTree, first.left, start, end);
        }
        if(start.compareTo(first.title) <= 0 && end.compareTo(first.title) >= 0){
            subTree.insert(first.title);
        }
        if(end.compareTo(first.title) > 0){
            subSet(subTree, first.right, start, end);
        }
    }
        
//    public void subSet(Movie first, String start, String end){
//        //base case
//        if(first == null){
//            return;
//        }
//        //Since the desired tree is sorted, recurse 
//        //If first.title is greater than start, then only in left subtree
//        if(start.compareTo(first.title) < 0){
//            subSet(first.left, start, end);
//        }
//        //If first.title lies in range, then prints first.title
//        if(start.compareTo(first.title) <= 0 && end.compareTo(first.title) >= 0){
//            System.out.println(first.title);
//        }
//        //If first.title is smaller than end, then only in right subtree
//        if(end.compareTo(first.title) > 0){
//            subSet(first.left, start, end);
//        }
//        
//    }
    
    public static void main(String[] args) {
        MovieBST tree = new MovieBST();
        String line = "";
        String cvsSplitBy = ",";
        
        try (BufferedReader br = new BufferedReader(new FileReader("/ml-latest-small/movies.csv"))) {

            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] movieInfo = line.split(cvsSplitBy);
                // Some movie titles with special character ","
                if(movieInfo.length <= 3){
                // regular case
                    tree.insert(movieInfo[1].replaceAll("['...*^\\\"|\\\"$]", ""));
                } else{
                   //movie titles with special character ","
                     String title = movieInfo[1].replaceAll("['...*^\\\"|\\\"$]", "") + ","
                                  + movieInfo[2].replaceAll("['...*^\\\"|\\\"$]", "");
                     tree.insert(title);
                }
            }
   

        } catch (IOException e) {
            e.printStackTrace();
        }
        
        //print inorder traversal of the MovieBST
        tree.inorder();
        
        //Example of retrieving subsets
        MovieBST subTree = new MovieBST();
        System.out.println("The movie titles fall alphabetically between \"Bug's Life\" and \"Harry Potter\": ");
        tree.subSet(subTree, tree.first, "Bug's Life", "Harry Potter");
        subTree.inorder();
        System.out.println("\nThe movie titles fall alphabetically between \"Back to the Future\" and \"Hulk\": ");
        tree.subSet(subTree, tree.first, "Back to the Future", "Hulk");
        subTree.inorder();
        System.out.println("\nThe movie titles fall alphabetically between \"Toy Story\" and \"WALL-E\": ");
        tree.subSet(subTree, tree.first, "Toy Story", "WALL-E");
        subTree.inorder();
       
    }
    
}//end class
