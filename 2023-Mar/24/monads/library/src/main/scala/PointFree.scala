package fplibrary


object PointFree {
    // val ac = compose(ab, bc)
    def compose[A, B, C](ab: A => B, bc: B => C): A => C = 
        a => {
            // produce a c here
            val b = ab(a)
            val c = bc(b)
            c
            // or:
            // bc(ab(a))
        }
    
    def composeKleisli[A, B, C](
        aDb: A => Description[B], 
        bDc: B => Description[C]
    ): A => Description[C] = 
        a => {
         val db = aDb(a)
         val b = db.apply()

         val dc = bDc(b)
         dc
        }

    // So we can compose with Kleisli arrows with
    // any type, rather than just Description
    def composeKleisli2[A, B, C, D[_]: Monad](
        aDb: A => D[B], 
        bDc: B => D[C]
    ): A => D[C] = 
        a => {
            val db = aDb(a)
            val dc = Monad[D].flatMap(db)(bDc)
            dc
        }
    


}


