

object Kata { 
   import scala.annotation.tailrec 
   // isSubsequence
   def isSubsequence(s: String, t: String): Boolean = 
      if (s.isEmpty) true
      else if (t.isEmpty) false
      else if (s.head == t.head) isSubsequence(s.tail, t.tail)
      else isSubsequence(s, t.tail)

   // isIsomorphic
   def isIsomorphic(s: String, t: String): Boolean =
      val mapped = s.zip(t).toMap.map(_.swap)
      t.flatMap(mapped.get(_)).mkString == s

   // merge two sorted Lists
   class ListNode(var _x: Int = 0) {
      var next: ListNode = null
      var x: Int = _x
   }
   def mergeTwoLists(l1: ListNode, l2: ListNode): ListNode = (l1, l2) match 
        case (null, l2) => l2
        case (l1, null) => l1
        case (l1, l2) if (l1.x <= l2.x) => {
            l1.next = mergeTwoLists(l1.next, l2)
            l1
        }
        case (l1, l2) if (l1.x > l2.x) => {
            l2.next = mergeTwoLists(l1, l2.next)
            l2
        }

   // reverse the listNodes
   def reverseList(head: ListNode): ListNode = {
        @tailrec
        def loop(head: ListNode, result: ListNode) : ListNode = {
            head match {
                case null => result
                case h => {
                    val current = h.next
                    h.next = result
                    loop(current, h)
                }
            }
        }
                
        loop(head, null)
    }

   def twoSum(list: List[Int], target: Int): List[Int] =
      val fm3 = for {
         i <- (0 until list.length)
         j <- ((i + 1) until list.length)
         if (list(i) + list(j) == target)
      } yield List(i, j)

      fm3.headOption match {
         case Some(x) => x
         case None => List(-1, -1)
      }

   println(twoSum(List(1, 2, 3, 4, 5, 6), 10)) // List(3, 5)

   // Pivot Index
   def pivotIndex(nums: List[Int]): Int = 
      @tailrec
      def pivotAcc(i: Int, lSum: Int, rSum: Int): Int =
         if (nums.isEmpty) -1
         else if (lSum == rSum - nums(i)) i
         else pivotAcc(i + 1, lSum + nums(i), rSum - nums(i))
      pivotAcc(0, 0, nums.sum)

   println(pivotIndex(List(1,7,3,6,5,6))) // 3

   // reverse a linked list
   def reverseList2(head: ListNode): ListNode = 
        @tailrec
        def loop(head: ListNode, result: ListNode): ListNode =
            head match
               case null => result
               case h => {
                  val current = h.next
                  h.next = result
                  loop(current, h)
               }
        loop(head, null)

   // Valid Parentheses
   def validParentheses(str: String): Boolean = {
      str.startsWith("(") && (str.length % 2 == 0)
   }

   println(validParentheses("()"))
   println(validParentheses(")(()))"))
   println(validParentheses("("))
   println(validParentheses("(())((()())())"))



   
   

   
   
}
