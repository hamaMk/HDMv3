#include <iostream>
#include <string>
#include <stdlib.h>

using namespace std;

string getCmdWIN(string parts);

int main(int argc, char* argv[ ])
{

    string head = "cd ";

    if(argc >= 3){
        string cmd = " && copy /b part1 + part2 + part3 + part4 + part5 + part6 ";
        string pathIN = argv[1];
        string fileName = argv[2];

        string exec = head + pathIN + cmd + fileName;

         // cout << "test :: -> " << exec << endl;
        // string t = "java -version";
        const char *tmp = exec.c_str(); //boom
        system(tmp);

         cout << "merge completed";
    }
    else
    cout << "test :: -> failed" << argc << endl;

      //cout << "merge completed";

    return 0;
}


