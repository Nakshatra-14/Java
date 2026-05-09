package pb.dsa.treeprinter;

import java.util.Arrays;

public class AnyWherePrinter 
{
    private char board[][] ;
    private int rows ;
    private int cols ;
    private int lastRow ;

    public AnyWherePrinter(int rows, int cols)
    {
        this.rows = rows ;
        this.cols = cols ;
        this.lastRow = -1 ;

        board = new char[rows][] ;
    }

    public AnyWherePrinter()
    {
        this(20, 80) ;
    }

    private void allocateLines(int curRow)
    {
        while(lastRow < curRow)
        {
            ++lastRow ;
            board[lastRow] = new char[cols] ;
            Arrays.fill(board[lastRow], ' ');
        }
    }

    public void keepAt(Object item, int r, int c)
    {
        String str = item.toString() ;
        if(r < rows && c < cols)
        {
            allocateLines(r);
            int j = 0 ;
            while(j < str.length() && c < cols)
                board[r][c++] = str.charAt(j++) ;
        }
    }

    void print()
    {
        for (int i = 0; i <= lastRow ; i++) 
            System.out.println(new String(board[i]).stripTrailing());
    }

    public static void main(String[] args)
    {
        AnyWherePrinter anyp = new AnyWherePrinter(10, 40) ;
        
        anyp.keepAt("first", 3, 0) ;
        anyp.keepAt("second", 7, 8) ;
        anyp.keepAt("third", 10, 28) ;
        anyp.keepAt("fourth", 5, 38) ;
        anyp.keepAt("fifth", 2, 16) ;

        anyp.print() ;
    }
}
