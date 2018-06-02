//
//  Tweet.swift
//  SimpleFirebaseApp
//
//  Created by Darkhan on 06.04.18.
//  Copyright © 2018 SDU. All rights reserved.
//

import Foundation
import FirebaseDatabase
class Tweet{
    private var content: String?
    private var user_email: String?
    private var hashtags: String?
    
    init(_ content: String, _ user_email: String, _ hashtags: String) {
        self.content = content
        self.user_email = user_email
        self.hashtags = hashtags
    }
    
    init(_ snapshot: FIRDataSnapshot) {
        let tweet = snapshot.value as! NSDictionary
        content = tweet.value(forKey: "content") as? String
        user_email = tweet.value(forKey: "user_email") as? String
        hashtags = tweet.value(forKey: "hashtags") as? String
    }
    var Content: String?{
        get{
            return content
        }
    }
    var User_email: String?{
        get{
            return user_email
        }
    }
    var Hashtags: String?{
        get{
            return hashtags
        }
    }
    func toJSONFormat()-> Any{
        return ["content": content,
                "user_email": user_email,
                "hashtags": hashtags]
    }
}
