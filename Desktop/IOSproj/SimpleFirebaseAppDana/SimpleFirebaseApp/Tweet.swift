//
//  Tweet.swift
//  SimpleFirebaseApp
//
//  Created by Darkhan on 03.04.18.
//  Copyright © 2018 SDU. All rights reserved.
//

import Foundation
import FirebaseDatabase
struct Tweet{
    private var content: String?
    private var user_email: String?
    //private var dateOfTweet: Date
   // private var path: String?
    //private var isPath: Bool
    init(_ content: String, _ user_email: String){//}, _ dateOfTweet: Date){/*, isPath: Bool, path: String) {*/
        self.content = content
        self.user_email = user_email
      //  self.dateOfTweet = dateOfTweet
       // self.isPath = isPath
      //  self.path = path
    }
    init(snapshot: DataSnapshot) {
        let tweet = snapshot.value as! NSDictionary
        content = tweet.value(forKey: "content") as? String
        user_email = tweet.value(forKey: "user_email") as? String
//        if dateOfTweet = tweet.value(forKey: "dateOfTweet") != nil{
//            dateOfTweet = (tweet.value(forKey: "dateOfTweet") as? Date)!
//        }
       // isPath = (tweet.value(forKey: "isPPath") as? Bool)!
       // path = tweet.value(forKey: "Path") as? String
    }
    var Content: String?{
        get{
            return content
        }
        set(content){
            self.content = content
        }
    }
    var User_email: String?{
        get{
            return user_email
        }set(user_email){
            self.user_email = user_email
        }
    }
//    var DateOfTweet: Date?{
//        get{
//            return dateOfTweet
//        }set(dateOfTweet){
//            self.dateOfTweet = dateOfTweet!
//        }
//    }
//    var ISPath: Bool?{
//        get{
//            return isPath
//        }set(isPath){
//            self.isPath = isPath!
//        }
//    }
//    var Path: String?{
//        get{
//            return path
//        }set(path){
//            self.path = path
//        }
//    }
    func toJSONFormat()-> Any{
        return ["content": content,
                "user_email": user_email// twit to database
               // ,"dateOfTweet": dateOfTweet
        ]
               // "isPPath": isPath,
               // "Path": path]
    }
    
}
