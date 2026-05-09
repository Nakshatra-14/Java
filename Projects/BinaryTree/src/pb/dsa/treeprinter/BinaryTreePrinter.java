package pb.dsa.treeprinter;

public class BinaryTreePrinter 
{
    private static record WidX (int wid, int x) {} ;

    private static final String TAB = "   " ;

    // ==== Use these for graphical characters ==== //
    // private static final String EDGE = "\u00C4\u00C4\u00C4" ;
    // private static final char ELBOW =  '\u00C0', BAR = '\u00B3', TEE = '\u00C3' ;
    // private static final char TOP = '\u00C4', LTOP = '\u00DA', RTOP = '\u00BF', REVTEE = '\u00C1', LELBOW = '\u00D9', RELBOW = '\u00C0'; 


    // ==== Use these for textual characters ==== //
    // private static final String EDGE = "---" ;
    // private static final char ELBOW = '+', BAR = '|', TEE = '+' ;
    // private static final char TOP = '-', LTOP = '+', RTOP = '+', REVTEE = '+', LELBOW = '+', RELBOW = '+' ;


    // don't touch! //
    private static final String EDGE = "───" ;
    private static final char ELBOW = '└', BAR = '│', TEE = '├' ;
    private static final char TOP = '─', LTOP = '┌', RTOP = '┐', REVTEE = '┴', LELBOW = '┘', RELBOW = '└' ;


    public static <T> void printOneSided(PrintableBinTree<T> tree)
    {
        boolean choices[] = new boolean[100] ;
        printOneSided(tree, choices, 0);
    }

    private static <T> void printOneSided(PrintableBinTree<T> tree, boolean choices[], int level)
    {
        if(tree != null)
        {
            int i = 0 ;
            for (; i < level-1; i++)
                System.out.print((choices[i] ? BAR : ' ') + TAB);
            if(level > 0)
                System.out.print((choices[level-1] ? TEE : ELBOW) + EDGE);

            System.out.println(tree.getData());

            if(tree.getLeft() != null)
            {
                if(tree.getRight() != null)
                    choices[level] = true ;
                printOneSided(tree.getLeft(), choices, level+1) ;
            }

            printOneSided(tree.getRight(), choices, level+1) ;
            choices[i] = false ;
        }
    }

    private static <T> WidX printBothSided(AnyWherePrinter prn, PrintableBinTree<T> tree, int row, int col)
    {
        char ch = '\0' ;

        if(tree == null)
            return new WidX(0, 0) ;

        boolean hasLeft = tree.getLeft() != null ;
        boolean hasRight = tree.getRight() != null ;

        String strData = tree.getData().toString() ;

        WidX ltWidX = printBothSided(prn, tree.getLeft(), row+2, col) ;

        col += ltWidX.wid ;
        
        int rootX = col ; // column where the root data should be printed
        int x = rootX + strData.length() / 2 ;

        col += strData.length() ;

        // Print the right subtree
        WidX rtWidX = printBothSided(prn, tree.getRight(), row+2, col) ;

        // set the symbol under the root
        if(hasLeft && hasRight)  // has both the children
        {
            ch = REVTEE ;
            rootX = (ltWidX.x() + rtWidX.x() - strData.length()) / 2 ; 
            x = rootX + strData.length() / 2 ;

            // Print the left elbow
            prn.keepAt(LTOP, row+1, ltWidX.x());

            // Print the right elbow
            prn.keepAt(RTOP, row+1, rtWidX.x());

            // Print the whole hand
            for(int j = ltWidX.x()+1 ; j < rtWidX.x() ; j++)
                prn.keepAt(TOP, row+1, j);

            // Print the middle joint
            prn.keepAt(ch, row+1, x);
        }
        else if(hasLeft) // has only the left child
        {
            ch = LELBOW ;
            x = rootX + strData.length() / 2 ;

            // Print the left elbow
            prn.keepAt(LTOP, row+1, ltWidX.x());

            // Print the left hand
            for(int j = ltWidX.x()+1 ; j < x ; j++)
                prn.keepAt(TOP, row+1, j);

            // Print the middle joint
            prn.keepAt(ch, row+1, x);
        }
        else if(hasRight) // has only the right child
        {
            ch = RELBOW ;
            x = rootX + strData.length() / 2 ;

            // Print the right elbow
            prn.keepAt(RTOP, row+1, rtWidX.x());

            // Print the right hand
            for(int j = x+1; j < rtWidX.x() ; j++)
                prn.keepAt(TOP, row+1, j);

            // Print the middle joint
            prn.keepAt(ch, row+1, x);
        }

        prn.keepAt(strData, row, rootX);


        return new WidX(ltWidX.wid() + strData.length() + rtWidX.wid(), x) ;
    }

    public static <T> void printBothSided(PrintableBinTree<T> tree)
    {
        AnyWherePrinter anyp = new AnyWherePrinter(50, 200) ;
        printBothSided(anyp, tree, 0, 0) ;
        anyp.print();
    }


    public static void main(String[] args) 
    {
        // Character pre[]  = {'A', 'B', 'C'} ;
        // Character in[]   = {'B', 'A', 'C'} ;
        // Character post[] = {'B', 'C', 'A'} ;

        // Character pre[]  = {'A', 'B', 'D', 'G', 'E', 'H', 'I', 'C', 'F'} ;
        // Character in[]   = {'D', 'G', 'B', 'H', 'E', 'I', 'A', 'F', 'C'} ;
        // Character post[] = {'G', 'D', 'H', 'I', 'E', 'B', 'F', 'C', 'A'} ;

        Integer pre[]  = {250, 120, 530, 650, 830, 160, 260, 190, 370} ;
        Integer in[]   = {530, 650, 120, 250, 260, 160, 190, 830, 370} ;
        Integer post[] = {650, 530, 120, 260, 190, 160, 370, 830, 250} ;

        // may be wrong
        // Integer pre[]  = {40, 4, 3, 9, 7, 5, 15, 18, 16, 17, 20} ;
        // Integer in[]   = {3, 4, 5, 7, 9, 14, 15, 16, 17, 18, 20} ;
        // Integer post[] = {3, 5, 7, 9, 4, 17, 16, 20, 18, 15, 14} ;

        // Indices to the array ar[]
        // Integer preIndices[]  = {0, 1, 3, 4, 7, 11, 13, 14, 8, 2, 5, 6, 9, 12, 10} ;
        // Integer inIndices[]   = {3, 1, 13, 11, 14, 7, 4, 8, 0, 5, 2, 9, 12, 6, 10} ;
        // Integer postIndices[] = {3, 13, 14, 11, 7, 8, 4, 1, 5, 12, 9, 10, 6, 2, 0} ;

        // String ar[] = {"Communication", "Tree", "Sun", "Extraordinary", "Pencil", "Butterfly", "Knowledge", "Incomprehensible", "Garden", "Smile", "Development", "Book", "Unbelievable", "Water", "Responsibility"} ; 

        // String pre[] = new String[preIndices.length] ;
        // String in[] = new String[inIndices.length] ;
        // String post[] = new String[postIndices.length] ;

        // for(int i = 0 ; i < preIndices.length ; i ++)
        // {
        //     pre[i] = ar[preIndices[i]] ;
        //     in[i] = ar[inIndices[i]] ;
        //     post[i] = ar[postIndices[i]] ;
        // }

                               
        // BinTree<Character> bt = BinTree.reconstructBinTreePreIn(pre, in) ;
        // BinTree<Integer> bt = BinTree.reconstructBinTreePreIn(pre, in) ;
        // BinTree<String> bt = BinTree.reconstructBinTreePreIn(pre, in) ;


        // System.out.println("pre : " + bt.preorderToString());
        // System.out.println("in  : " + bt.inorderToString());
        // System.out.println("post: " + bt.postorderToString());

        // BinaryTreePrinter.printOneSided(bt.getRoot()) ;
        // System.out.println("====================================================");
        // BinaryTreePrinter.printBothSided(bt.getRoot());
        System.out.println("BinaryTreePrinter");
    }
}


