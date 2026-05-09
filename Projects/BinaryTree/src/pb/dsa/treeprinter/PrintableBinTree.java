package pb.dsa.treeprinter;

public interface PrintableBinTree<T>
{
    T getData() ;
    PrintableBinTree<T> getLeft() ;
    PrintableBinTree<T> getRight() ;
}
