#include "battleship.h"

void showgrid(Status [][]);
void search1(int *nextRow, int *nextColumn);
void search2(int *nextRow, int *nextColumn);
void search3(int *nextRow, int *nextColumn);
void search4(int *nextRow, int *nextColumn);
void firerandom(int *nextRow, int *nextColumn);
void makeNextGuess(int *nextRow, int *nextColumn, Status lastresult);
int h = 5;
int v = 4;
Status opener = NONE;
int impact = 0;
int *Phorz;//Point of first impact horz
int *Pvert;//point of first impact vert
int hcopy = 0;
int vcopy = 0;
int hcopy2 = 0;//these are to prevent the search methods in miss from changing
int vcopy2 = 0;//the copies
int orientation = 0; //should be 1 to 4
int guesses = 0;
int successh = 0;
Status ocean2[10][10];//enforces sunk methodology
int Popener = 0; //If there is a hit, this increments for AI targeting
int main(void)
{
  int score = 0;
  int x, y;
  int *hptr = &h;
  int *vptr = &v;
  Status ocean[10][10];
  for(x = 0; x<10; x++)
  {
    for(y = 0; y<10; y++)
    {
      ocean[x][y] = MISS;
    }

  }
  ocean[0][0] = HIT;
  ocean[0][1] = HIT;


  ocean[4][4] = HIT;
  ocean[5][4] = HIT;
  ocean[6][4] = HIT;


  ocean[0][3] = HIT;
  ocean[0][4] = HIT;
  ocean[0][5] = HIT;

  ocean[9][9] = HIT;
  ocean[8][9] = HIT;
  ocean[7][9] = HIT;
  ocean[6][9] = HIT;

  ocean[9][0] = HIT;
  ocean[9][1] = HIT;
  ocean[9][2] = HIT;
  ocean[9][3] = HIT;
  ocean[9][4] = HIT;




  while(score != 17)
  {
    printf("Guesses = %d\n", guesses);
    printf("Calling MNG with these %d, %d, %d\n", *hptr, *vptr, opener);
    makeNextGuess(hptr, vptr, opener);
    switch(ocean[*hptr][*vptr])
    {
      case 1:
      opener = MISS;
      printf("It missed\n");
      ocean[*hptr][*vptr] = SUNK;
           showgrid(ocean);
      break;

      case 2:
	score = score + 1;
      printf("Score is %d\n", score);
      opener = HIT;
      printf("It hit\n");
      ocean[*hptr][*vptr] = SUNK;
         showgrid(ocean);
      break;

      case 3:
        printf("Previous = %d, %d\n", *hptr, *vptr);
       showgrid(ocean);
      opener = SUNK;
       makeNextGuess(hptr, vptr, opener);
      printf("Targeting %d, %d\n", *hptr, *vptr);
      if(ocean[*hptr][*vptr] == HIT)
      {
        printf("It hit\n");
        score = score + 1;
	printf("Score is %d\n", score);
        ocean[*hptr][*vptr] = SUNK;
        ocean2[*hptr][*vptr] = SUNK;
        showgrid(ocean);
        opener = HIT;
      }
      else if(ocean[*hptr][*vptr] == MISS)
      {
        printf("It missed\n");
        ocean[*hptr][*vptr] = SUNK;
        showgrid(ocean);
        opener = MISS;

      }
      break;
      default:
	printf("Something has gone horribly wrong\n");
        break;
    }

  }
  printf("Done in %d guesses\n", guesses);

}

void showgrid(Status ocean[][10])
{
  int x, y;
  for(y = 0; y<10; y++)
  {
    for(x = 0; x<10; x++)
    {
      printf("%d", ocean[x][y]);
    }
    printf("\n");
  }
  printf("\n");

}

void search1(int *nextRow, int *nextColumn)
{
    if(*nextRow<9 && ocean2[*nextRow+1][*nextColumn] != SUNK )
    {
      *nextRow = *nextRow+1;
    }
    else if(*nextColumn<9 && ocean2[*nextRow][*nextColumn+1]!= SUNK)
    {
      *nextColumn = *nextColumn+1;
    }
    else if(*nextRow>0 && ocean2[*nextRow-1][*nextColumn]!= SUNK)
    {
      *nextRow = *nextRow - 1;
    }
    else if(*nextColumn>0&& ocean2[*nextRow][*nextColumn-1] != SUNK)
    {
      *nextColumn = *nextColumn-1;
    }
    else
    {
      firerandom(nextRow, nextColumn);
    }
}
void search2(int *nextRow, int *nextColumn)
{
  if(*nextColumn<9 && ocean2[*nextRow][*nextColumn+1]!= SUNK )
  {
    *nextColumn = *nextColumn+1;
  }
  else if(*nextRow>0 && ocean2[*nextRow-1][*nextColumn]!= SUNK)
  {
    *nextRow = *nextRow-1;
  }
  else if(*nextColumn>0 && ocean2[*nextRow][*nextColumn-1]!= SUNK)
  {
    *nextColumn = *nextColumn-1;
  }
  else if(*nextRow<9 && ocean2[*nextRow+1][*nextColumn]!= SUNK)
  {
    *nextRow = *nextRow+1;
  }
  else
  {
      firerandom(nextRow, nextColumn);
  }
}
void search3(int *nextRow, int *nextColumn)
{
  if(*nextRow>0 && ocean2[*nextRow-1][*nextColumn]!= SUNK)
  {
    printf("Executing *nextRow>0\n");
    *nextRow = *nextRow-1;
  }
  else if(*nextColumn>0 && ocean2[*nextRow][*nextColumn-1]!= SUNK)
  {
    printf("Executing *nextColumn>0\n");
    *nextColumn = *nextColumn-1;
  }
  else if(*nextRow<9 &&  ocean2[*nextRow+1][*nextColumn]!= SUNK)
  {
    printf("executing *nextRow<9\n");
    *nextRow = *nextRow+1;
  }
  else if(*nextColumn<9 && ocean2[*nextRow][*nextColumn+1]!= SUNK)
  {
    printf("executing *nextColumn<9\n");
    *nextColumn = *nextColumn +1;
  }
  else
  {
     firerandom(nextRow, nextColumn);
  }
}
void search4(int *nextRow, int *nextColumn)
{
  if(*nextColumn>0 && ocean2[*nextRow][*nextColumn-1]!= SUNK)
  {
    *nextColumn = *nextColumn-1;
  }
  else if(*nextRow<9 && ocean2[*nextRow+1][*nextColumn]!= SUNK)
  {
    *nextRow = *nextRow + 1;
  }
  else if(*nextColumn<9 && ocean2[*nextRow][*nextColumn+1]!= SUNK)
  {
    *nextColumn = *nextColumn + 1;
  }
  else if(*nextRow>0 && ocean2[*nextRow-1][*nextColumn]!= SUNK)
  {
    *nextRow = *nextRow-1;
  }
  else
  {
     firerandom(nextRow, nextColumn);
  }
}
void firerandom(int *nextRow, int *nextColumn)
{
  if(guesses<=50)
  {
   *nextColumn = random()%10;
   if(*nextColumn%2 == 0)//odd due to array index conventions
   {
     *nextRow = ((((random()%10)*2)+1)%10);//SHOULD BE ODD
   }
   else if(*nextColumn%2 != 0)//even due to array index conventions
   {
     *nextRow = (((random()%10)*2)%10); // SHOULD BE EVEN
   }
   printf("This is rfiring at %d, %d\n", *nextRow, *nextColumn);
  }
  else if(guesses>50)
  {
   *nextColumn = random()%10;
   if(*nextColumn%2 == 0)//odd due to array index conventions
   {
     *nextRow = (((random()%10)*2)%10);//SHOULD BE EVEN
   }
   else if(*nextColumn%2 != 0)//even due to array index conventions
   {
     *nextRow = ((((random()%10)*2)+1)%10); // SHOULD BE ODD
   }
   printf("This is rfiring at %d, %d\n", *nextRow, *nextColumn);
  }
}
void makeNextGuess(int *nextRow, int *nextColumn, Status lastresult)
{
  printf("Popener = %d\n", Popener);
  printf("Opener = %d\n", lastresult);
  if(lastresult == NONE)
  {
    guesses = guesses+ 1;
    printf("Nfiring random\n");
    firerandom(nextRow, nextColumn);
    ocean2[*nextRow][*nextColumn] == SUNK;
  }
  else if(lastresult == HIT)
  {
    guesses = guesses + 1;
    if(Popener == 0)
    {
      Popener = 1;
      hcopy = *nextRow;
      vcopy = *nextColumn;
      Phorz = &hcopy;
      printf("What's this\n");
      Pvert = &vcopy;
      printf("I'm also before a segfault\n");
      printf("Phorz/Pvert are %d, %d\n", *Phorz, *Pvert);

    }
    if(Popener == 1)
    {
      printf("HCalling search1\n");
      search1(nextRow, nextColumn);
    }
    else if(Popener == 2)
    {
      printf("HCalling search2\n");
      search2(nextRow, nextColumn);
    }
    else if(Popener == 3)
    {
      printf("HCalling search3\n");
      search3(nextRow, nextColumn);
    }
    else if(Popener == 4)
    {
      printf("HCalling search4\n");
      search4(nextRow, nextColumn);
    }
    else if(Popener == 5)
    {
      Popener = 0;
      printf("Hfiring Random Pop5\n");
      firerandom(nextRow, nextColumn);
    }//end of popener == 5
    ocean2[*nextRow][*nextColumn] = SUNK;
    printf("HFiring at %d, %d\n", *nextRow, *nextColumn);
  }
  else if(lastresult == MISS)
  {
    guesses = guesses + 1;
    if(Popener != 0)
    {
     Popener = Popener + 1;
     printf("Popener is now %d\n", Popener);
    }
    if(Popener == 1)
    {
      printf("MCalling search1\n");
      printf("Phorz and Pvert were %d, %d\n", *Phorz, *Pvert);
      hcopy2 = *Phorz;
      vcopy2 = *Pvert;
      search1(Phorz, Pvert);
      printf("Phorz and Pvert are %d, %d\n", *Phorz, *Pvert);
      *nextRow = hcopy;
      *nextColumn = vcopy;
      ocean2[*nextRow][*nextColumn] = SUNK;
      hcopy = hcopy2;
      vcopy = vcopy2;
      orientation = 1;
    }
    else if(Popener == 2)
    {
      printf("Phorz/Pvert were %d, %d\n", *Phorz, *Pvert);
      printf("MCalling search2\n");
      hcopy2 = hcopy;
      vcopy2 = vcopy;
      search2(Phorz, Pvert);
      printf("At this point, %d, %d\n", *Phorz, *Pvert);
      *nextRow = hcopy;
      *nextColumn = vcopy;
      ocean2[*nextRow][*nextColumn] = SUNK;
      hcopy = hcopy2;
      vcopy = vcopy2;
      printf("Phorz/Pvert are %d, %d\n", *Phorz, *Pvert);
      orientation = 2;
    }
    else if(Popener == 3)
    {
      hcopy2 = hcopy;
      vcopy2 = vcopy;
      printf("MCalling search3 with these arguments, %d, %d\n", *Phorz, *Pvert);
      search3(Phorz, Pvert);
      *nextRow = hcopy;
      *nextColumn = vcopy;
      ocean2[*nextRow][*nextColumn] = SUNK;
      hcopy = hcopy2;
      vcopy = vcopy2;
      orientation = 3;
    }
    else if(Popener == 4)
    {
      hcopy2 = hcopy;
      vcopy2 = vcopy;
      printf("Mcalling search4\n");
      search4(Phorz, Pvert);
      *nextRow = hcopy;
      *nextColumn = vcopy;
      ocean2[*nextRow][*nextColumn] = SUNK;
      hcopy = hcopy2;
      vcopy = vcopy2;
      orientation = 4;
    }
    else if(Popener == 5)
    {
      Popener = 0;
      printf("This is before the orientation switch\n");
      switch(orientation)
      {
      case1:
      if(*Phorz-1>0)
      {
       *nextRow = *Phorz - 1;
       *nextColumn = *Pvert;
      }
      else
      {
        printf("MFiring RC1\n");
	firerandom(nextRow, nextColumn);
      }
      break;
      case2:
      if(*Pvert-1>0)
      {
	*nextColumn = *Pvert-1;
        *nextRow = *Phorz;
      }
      else
      {
        printf("MFiring RC2\n");
        firerandom(nextRow, nextColumn);
      }
      break;
      case3:
      if(*Phorz+1<9)
      {
        *nextRow = *Phorz+1;
        *nextColumn = *Pvert;
      }
      else
      {
        ("MFiring RC3\n");
        firerandom(nextRow, nextColumn);
      }
      break;
      case4:
      if(*Pvert+1<9)
      {
	*nextColumn = *Pvert+1;
        *nextRow = *Phorz;
      }
      else
      {
        printf("MFiring RC4\n");
        firerandom(nextRow, nextColumn);
      }
      break;
      default:
        printf("Firing random by default\n");
	firerandom(nextRow, nextColumn);
        break;
      }//end of switch statement
    }//end of popener == 5
    if(Popener == 0)
    {
    firerandom(nextRow, nextColumn);
    ocean2[*nextRow][*nextColumn] = SUNK;
    }
    printf("MFiring at %d, %d\n", *nextRow, *nextColumn);
  }//end of if (lastresult == MISS)
  else if(lastresult >= SUNK || ocean2[*nextRow][*nextColumn] == SUNK)
  {
    Popener = 0;
    printf("SFIRE\n");
    while(ocean2[*nextRow][*nextColumn] == SUNK)
    {
      printf("Am i the While loop\n");
    firerandom(nextRow, nextColumn);
    }
    ocean2[*nextRow][*nextColumn] = SUNK;
  }
}//end of makenextguess
