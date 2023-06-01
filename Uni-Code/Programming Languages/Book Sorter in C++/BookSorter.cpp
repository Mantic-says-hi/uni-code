#include <iostream>
#include "Book.h"
#include <list>

using namespace std;

//Generic setup of books
void setupBook(Book& book,int id, std::string name, std::string isbn)
{
    book.SetBookID(id);
    book.SetBookName(name);
    book.SetBookISBN(isbn);

}

//Partitioning that happens during recursing
int partition(list <Book>& L, int leftIdx, int rightIdx, int pivotIdx)
{
    int currIdx,  newPivot, ii;
    Book pivotVal,temp;

    auto book = std::next(L.begin(),pivotIdx);
    auto right = std::next(L.begin(),rightIdx);
    pivotVal = *book;
    *book = *right;
    *right = pivotVal;

    currIdx = leftIdx;

    for(ii = leftIdx; ii <= (rightIdx - 1); ii++)
    {
        auto check = std::next(L.begin(),ii);
        Book checker = *check;
        if(checker.GetBookID() < pivotVal.GetBookID())
        {
            auto curr = std::next(L.begin(),currIdx);
            temp = *check;
            *check = *curr;
            *curr = temp;
            currIdx += 1;
        }
    }
    newPivot = currIdx;
    auto piv = std::next(L.begin(),newPivot);
    *right = *piv;
    *piv = pivotVal;

    return newPivot;
}

//Recursive loop of sorting
void quicksortRecurse(list <Book>& L, int leftIdx, int rightIdx)
{
    int pivotIdx, newPivotIdx;

    if(leftIdx < rightIdx)
    {
        pivotIdx = (leftIdx + rightIdx) / 2;
        newPivotIdx = partition(L, leftIdx, rightIdx, pivotIdx);
        quicksortRecurse(L, leftIdx, newPivotIdx - 1);
        quicksortRecurse(L, newPivotIdx + 1, rightIdx);
    }
}

//Entry, makes it easier just to dump a list in
void quickSort(list <Book>& L)
{
    quicksortRecurse(L,0,L.size() - 1);
}

//Hardcode 7 books to demonstrate how sorting works
list <Book> makeList()
{
    Book bOne, bTwo, bThree, bFour, bFive, bSix, bSeven;
    setupBook(bOne, 7498,"Book 1","0-123-123123-4");
    setupBook(bTwo, 1247,"Book 2","1-213-213213-5");
    setupBook(bThree, 6394,"Book 3","2-321-321321-8");
    setupBook(bFour, 8351,"Book 4","3-890-098098-6");
    setupBook(bFive, 3548,"Book 5","4-089-890890-1");
    setupBook(bSix, 9043,"Book 6","5-654-654321-2");
    setupBook(bSeven, 6365,"Book 7","6-456-123456-0");
    list <Book> list = {bOne,bTwo,bThree,bFour,bFive,bSix,bSeven};
    return list;
}

int main()
{
    //Make hardcoded list
    list <Book> bookList = makeList();

    //Print initial list
    std::cout << "\n\nLIST OF CURRENT BOOKS:\n" << std::endl;
    std::cout << "   ID     NAME         ISBN" << std::endl;
    std::cout << " --------------------------------" << std::endl;
    for (auto &book : bookList)
    {
        std::cout << "| "
                  << book.GetBookID()   << " | " 
                  << book.GetBookName() << " | "
                  << book.GetISBN()     << " | " << std::endl;
    }
    std::cout << " --------------------------------\n\n" << std::endl;

    //Sort List
    quickSort(bookList);

    //Print sorted list
    std::cout << "LIST AFTER SORTING BOOKS:\n" << std::endl;
    std::cout << "   ID     NAME         ISBN" << std::endl;
    std::cout << " --------------------------------" << std::endl;
    for (auto &book : bookList)
    {
        std::cout << "| "
                  << book.GetBookID()   << " | " 
                  << book.GetBookName() << " | "
                  << book.GetISBN()     << " | " << std::endl;
    }
    std::cout << " --------------------------------" << std::endl;
}

