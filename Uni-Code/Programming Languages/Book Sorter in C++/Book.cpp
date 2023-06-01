#include <iostream>
#include "Book.h"

using namespace std;

Book::Book()
{
    bookID = 0;
    bookName = "N/A";
    ISBN = "N/A";
    //std::cout << "Empty book entry created." << std::endl;
}

Book::~Book()
{
    //std::cout << "Book entry : " << bookID << " has been removed." << std::endl;
}

int Book::GetBookID()
{
    return bookID;
}

std::string Book::GetBookName()
{
    return bookName;
}

std::string Book::GetISBN()
{
    return ISBN;
}

void Book::SetBookID(int inBookID)
{
    bookID = inBookID;
}

void Book::SetBookName(std::string inBookName)
{
    bookName = inBookName;
}

void Book::SetBookISBN(std::string inISBN)
{
    ISBN = inISBN;
}