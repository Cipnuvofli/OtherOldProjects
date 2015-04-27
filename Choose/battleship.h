#include <stdio.h>

typedef enum {NONE, 	// no status, just starting
	      MISS, 	// last shot was a "miss"
	      HIT, 	// last shot was a "hit"
	      SUNK, 	// last shot sunk a ship
              SUNKCarrier,
              SUNKBattleship,
	      SUNKFrigate,
	      SUNKSubmarine,
              SUNKMineSweeper,
	      OVER	// game over
             } Status;
