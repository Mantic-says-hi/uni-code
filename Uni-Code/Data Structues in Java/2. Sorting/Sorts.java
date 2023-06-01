/** 
** Software Technology 152
** Class to hold various static sort methods.
*/
class Sorts
{

    public static void bubbleSort(int[] A)
    {
        int i, ii, temp;

        for(i = 0; i < (A.length - 1); i++){
            for(ii = 0; ii < (A.length - 1 - i); ii++){
                if(A[ii] > A[ii+1]){

                    temp = A[ii];
                    A[ii] = A[ii+1];
                    A[ii+1] = temp;
                }
            }
        }

    }

    // bubble sort
    public static void bubbleSort2(int[] A)
    {


        int i = 0, temp;
        boolean sorted;        

        do{
            sorted = true;
            for(int ii = 0; ii < (A.length-1 - i); ii++){
                    if(A[ii] > A[ii+1]){

                        temp = A[ii];
                        A[ii] = A[ii+1];
                        A[ii+1] = temp;
                        sorted = false;
                    }          
            } 
            i = i + 1;

        }while(!sorted);


    }//bubbleSort()

    // selection sort
    public static void selectionSort(int[] A)
    {
        int minIdx, temp;

        for(int i = 0; i < (A.length - 1); i++){
            minIdx = i;
            for(int ii = i + 1; ii < A.length; ii++){
                if(A[ii] < A[minIdx]){
                    minIdx = ii;
                }
            }

            temp = A[minIdx];
            A[minIdx] = A[i];
            A[i] = temp;
        }

    }// selectionSort()

    // insertion sort
    public static void insertionSort(int[] A)
    {

        for(int i = 1; i < A.length; i++){
            int ii = i;

            while((ii > 0) && (A[ii-1] > A[ii])){

                int temp = A[ii];
                A[ii] = A[ii-1];
                A[ii-1] = temp;

                ii = ii - 1;

            } 
        }

    }// insertionSort()

    public static void insertionSort2(int[] A)
    {
        int i, ii , temp;

        for(i = 1; i < A.length; i++){
            ii = i;

            temp = A[ii];
            while((ii > 0) && (A[ii-1] > temp)){
                A[ii] = A[ii-1];
                ii = ii - 1;
            }

            A[ii] = temp;
        }
    }

    // mergeSort - front-end for kick-starting the recursive algorithm
    public static void mergeSort(int[] A)
    {

        mergeSortRecurse(A, 0, A.length - 1);

    }//mergeSort()
    private static void mergeSortRecurse(int[] A, int leftIdx, int rightIdx)
    {
        int midIdx;

        if(leftIdx < rightIdx){
            midIdx = (leftIdx + rightIdx) / 2;

            mergeSortRecurse(A, leftIdx, midIdx);
            mergeSortRecurse(A, midIdx + 1, rightIdx);

            merge(A, leftIdx, midIdx, rightIdx);
        }else{

        }


    }//mergeSortRecurse()
    private static void merge(int[] A, int leftIdx, int midIdx, int rightIdx)
    {
        int ii, jj, kk = 0;

        int[] tempArr = new int[(rightIdx - leftIdx + 1)];
        ii = leftIdx;
        jj = midIdx + 1;

        while((ii < midIdx) && (jj < rightIdx)){
            if(A[ii] <= A[jj]){

                tempArr[kk] = A[ii];
                ii = ii + 1;
            }else{

                tempArr[kk] = A[jj];
                jj = jj + 1;
            }

            kk = kk + 1;
        }

        for(ii = ii; ii < midIdx; ii++){

            tempArr[kk] = A[ii];
            kk = kk + 1;
        }

        for (jj = jj; jj < rightIdx; jj++){

            tempArr[kk] = A[jj];
            kk = kk + 1;
        }

        for(kk = leftIdx; kk < rightIdx; kk++){

            A[kk] = tempArr[kk - leftIdx];
        }
     

    }//merge()


    // quickSort - front-end for kick-starting the recursive algorithm
    public static void quickSort(int[] A)
    {

        quickSortRecurse(A, 0, A.length - 1);

    }//quickSort()
    private static void quickSortRecurse(int[] A, int leftIdx, int rightIdx)
    {
        int pivotIdx, newPivotIdx;

        if(leftIdx < rightIdx){
            
            pivotIdx = (leftIdx + rightIdx) / 2;
            newPivotIdx = doPartitioning(A, leftIdx, rightIdx, pivotIdx);

            quickSortRecurse(A, leftIdx, newPivotIdx - 1);
            quickSortRecurse(A, newPivotIdx + 1, rightIdx);
        }


    }//quickSortRecurse()
    private static int doPartitioning(int[] A, int leftIdx, int rightIdx, int pivotIdx)
    {
        int pivotVal, currIdx, temp, newPivotIdx, ii;

        pivotVal = A[pivotIdx];
        A[pivotIdx] = A[rightIdx];
        A[rightIdx] = pivotVal;

        currIdx = leftIdx;

        for(ii = leftIdx; ii <= (rightIdx -1); ii++){
            if(A[ii] < pivotVal){

                temp = A[ii];
                A[ii] = A[currIdx];
                A[currIdx] = temp;
                currIdx = currIdx + 1;
            }
        }

        newPivotIdx = currIdx;
        A[rightIdx] = A[newPivotIdx];
        A[newPivotIdx] = pivotVal;
        
        return newPivotIdx; 
    }//doPartitioning


}//end Sorts calss
