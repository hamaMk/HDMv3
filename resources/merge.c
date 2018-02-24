#include <stdlib.h>
#include <string.h>
#include <stdio.h>

 void main(int argc, char * argv[ ]){

		
    char  tmp[] = "cd ";
    if(argc >1){

        strcat(tmp,argv[1]);
        strcat(tmp," && copy /b part1 + part2 + part3 + part4 + part5 + part6 fullHDMfile");
       // strcat(tmp,argv[2]);
    		//char j[] = "sim";
        system(tmp);
    }
    else system("cd C:/Users/HAMANDISHE/Videos/HDM Downloads/HDMv3/ && copy /b part1 + part2 + part3 + part4 + part5 + part6 fullfile");

    printf("merge completed");

 }