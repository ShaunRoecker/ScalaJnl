@main def mainScript(args: String*) = 
    println(thing.name)
    println(thing.capThing)
      
    def continue =
        var i = 0
        while i < args.length do
            if i != 0 then 
                print(" ")
            print(args(i))
            i += 1
        println()
    end continue

    continue

    // use foreach instead of while loops
    args.foreach(arg => println(arg))
    args.foreach(arg => println(arg+" "))

    def printArgs(args: List[String]): Unit =
        var i = 0
        while i < args.length do
            println(args(i))
            i += 1
    
    def printArgsFunctional(args: List[String]): Unit =
        for arg <- args do
            println(arg)
    
    printArgs(List("A", "B", "C", "D")) 
    printArgsFunctional(List("A", "B", "C", "D")) 
    


object thing:
    val name: String = "thing"
    def capThing = name.capitalize



