#include <stdio.h>
#include <time.h>
#include <stdlib.h>

//Written for CSCE 1040 by Joseph Penrod

void currencyprinter(double, int, double[]);


int main(void)
{

   double dollars;//stores USD value for conversion
   int x;
   int flag = 1;//ensures input isn't negative 

   srand ( (unsigned)time (NULL));//needed for random exchangerate
   double exchangerates[5] = {.7354, .9947, 82.61, .6250, (rand()%10000)/100};
   exchangerates[4] = rand()%10000;
   exchangerates[4] /= 100;
   printf("The Josephian dollar rate is %.2f\n", exchangerates[4]);
   printf("How many dollars do you have?\n");
   scanf("%lf", &dollars);
   for(x = 0; x<=4; x++)
   {
      currencyprinter(dollars, x, exchangerates);  
   
   }
   

}

void currencyprinter(double amount, int counter, double rates[])
{//it makes it simpler to output the currency names
   switch(counter)
   {
   case 0:
   printf("You have %.2f Euro\n", amount*rates[counter]);
   break;
   case 1:
   printf("You have %.2f Canadian Dollars\n", amount*rates[counter]);
   break;
   case 2:
   printf("You have %.2f Yen\n", amount*rates[counter]);
   break;
   case 3:
   printf("You have %.2f Pounds\n", amount*rates[counter]);
   break;
   case 4:
   printf("You have %.2f Josephian dollars\n", amount*rates[counter]);
   break;
   default:
   printf("something is horribly wrong here");                                 
   break;
   }
     
}
