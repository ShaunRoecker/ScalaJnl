

import zio._


object Example extends ZIOAppDefault {
    trait DatabaseError 
    trait UserProfile
    
    def lookupProfile( userId: String): ZIO[Any, DatabaseError, Option[UserProfile]] =
       ???

    // def lookupProfile2( userId: String): ZIO[Any, Option[DatabaseError], UserProfile] =
    //     lookupProfile.foldZIO(
    //         error => ZIO.fail(Some(error)), success => success match {
    //             case None => ZIO.fail(None)
    //             case Some(profile) => ZIO.succeed(profile) }
    //     )

    def run = Console.printLine("Welcome to ZIO")


    



}

