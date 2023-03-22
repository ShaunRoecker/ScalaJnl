

object Kata extends App {

         val list1 = 
            List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
         println(list1.sum)

         val sumList = 
            list1.foldLeft(0)(_ + _)

        println(sumList)

        def sequenceSum(start: Int, stop: Int, step: Int): Int = {
            if (start > stop) 0
            else if (start == stop) start
            else start + sequenceSum(start + step, stop, step)
        }

        println(sequenceSum(2, 6, 2)) // 12
        println(sequenceSum(1, 5, 1)) // 15
        println(sequenceSum(2, 2, 2)) // 2

        def sequenceSumUponRealization(start: Int, stop: Int, step: Int): Int = 
            (start to stop by step).sum

        val list2 = List("a", "b", "c", "a", "d", "c", "b", "a", "c")
        val list2ToString = list2.mkString

        val mappedAndCounted1 = list2.groupMapReduce(identity)(_ => 1)(_ + _)
        println(mappedAndCounted1) //Map(a -> 3, b -> 2, c -> 3, d -> 1)

        val mappedAndCounted2 = list2ToString.groupMapReduce(identity)(_ => 1)(_ + _)
        println(mappedAndCounted2) //Map(a -> 3, b -> 2, c -> 3, d -> 1)

        // For a given string s find the character c (or C) with longest 
        // consecutive repetition and return: Some(c, l)
        def longestRepetition(s: String): Option[(Char, Int)] = {
            val ls = s.toList
            if (ls.isEmpty) None
            else {
                val res = ls.tail.scanLeft(ls.head -> 1){ case ((prev, c), a) =>
                    val nextAcc = 
                        if (a == prev) c + 1
                        else 1
                    a -> nextAcc    
                }
                Some(res.maxBy(_._2))
            } 
        }

        println(longestRepetition("aabbsbbbfbddbssaabuuussuhhhhhssssddda")) //Some((h,5))

        val list3 = List("a", "b", "c", "d", "e", "f")
        val scanning = list3.scanLeft("")(_ + _)
        println(scanning) //List(, a, ab, abc, abcd, abcde, abcdef)

        val list4 = "aabbcbbbbaaa".toList
        println(
            list4.tail.scanLeft(list4.head -> 1){case ((prev, c), a) =>
                val nextAcc = 
                    if (a == prev) c + 1
                    else 1
                a -> nextAcc
            }
        )
        //List((a,1), (a,2), (b,1), (b,2), (c,1), (b,1), (b,2), (b,3), (b,4), (a,1), (a,2), (a,3))

        
        








}
