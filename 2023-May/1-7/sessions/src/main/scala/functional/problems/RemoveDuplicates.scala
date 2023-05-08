package functional.problems


object Solution1:
    def removeDuplicates(nums: Array[Int]): Int =
        if (nums.length < 2) nums.length
        else
            nums.indices.tail.foldLeft(1) {
                case (acc, e) =>
                    if (nums(e) != nums(e - 1)) 
                        nums(acc) = nums(e)
                        acc + 1
                    else 
                        acc
            }

    def removeDuplicatesRL(nums: Array[Int]): Int =
        nums.distinct.length

    def removeDuplicatesRL2(nums: Array[Int]): Int =
        nums.toSet.size

    def removeDuplicatesRL3(nums: Array[Int]): Int =
        if (nums.length < 2) nums.length
        else
            val res = nums.tail.scanLeft(nums.head -> 1) { case ((prev, count), curr) =>
                val nextAcc =
                    if (prev == curr) count + 1
                    else 1
                curr -> nextAcc
            }
            res.filter(x => x._2 == 1).length

    
    
