//
//  ProfileORIGINALViewController.swift
//  SimpleFirebaseApp
//
//  Created by Aidana Ketebay on 10.04.18.
//  Copyright © 2018 SDU. All rights reserved.
//

import UIKit
import FirebaseAuth
import Firebase
import FirebaseDatabase
class ProfileORIGINALViewController: UIViewController,UITableViewDelegate,UITableViewDataSource{
    
   
    @IBOutlet weak var Email: UILabel!
    @IBOutlet weak var Name: UILabel!
    @IBOutlet weak var Surname: UILabel!
    @IBOutlet weak var tableVieww: UITableView!
    private var dbRef: DatabaseReference?
    private var dbRef1: DatabaseReference?
    private var MyTweets: [Tweet] = []
//    var current_user_email = {
//        return Auth.auth().currentUser?.email
//    }
    public var currentUser : String = ""
    override func viewDidLoad() {
        super.viewDidLoad()
        dbRef = Database.database().reference()
                                ///
                                ///
                                ///
                                ///
                                ///
                                ///
                                ///
                                ///
                                ///
        dbRef = dbRef?.child("tweets")
//        dbRef1 = Database.database().reference()
//        dbRef1 = dbRef1?.child("User")
//        dbRef1?.childByAutoId().setValue(User.init("Danagul", "Zholdykhairova", "17.03.1999", "danagul.kz1@gmail.com").toJSONFormat())
        let otherVC = ProfileViewController()
        currentUser = otherVC.current_user_email()!
        dbRef?.observe(DataEventType.value, with: { (snapshot) in
            print(snapshot.childrenCount)
            self.MyTweets.removeAll()
            for snap in snapshot.children{
                let tweet = Tweet.init(snapshot: snap as! DataSnapshot)
                if self.currentUser == tweet.User_email {
                    self.MyTweets.append(tweet)
                    
                }
            }
            self.MyTweets.reverse()
            
            self.Email.text = self.currentUser
            
           
            self.tableVieww.reloadData()
            })
                                                            //////
                                                            //////
                                                            //////
                                                            //////
                                                            //////
            let db = Database.database().reference().child("User")
        db.observe(DataEventType.value, with: { (snapshot) in
            print(snapshot.childrenCount)
            for snap in snapshot.children{
                if(User.init(snapshot: snap as! DataSnapshot).Email != nil){
                    let user = User.init(snapshot: snap as! DataSnapshot)
                    print(user.Email!)
                    if self.currentUser == user.Email {
                        self.Email.text = user.Email
                        self.Name.text = user.Name
                        self.Surname.text = user.Surname
                        print(user.Name!)
                        //mydat
                        }
                }
            }
        })
    
    }
    @IBAction func signoutPressed(_ sender: UIButton) {
        do{
            try Auth.auth().signOut()
        }catch{
            
        }
        presentingViewController?.dismiss(animated: true, completion: nil)
    }
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        print(MyTweets.count)
        return MyTweets.count
    }

    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "cell")
        cell?.textLabel?.text = MyTweets[indexPath.row].Content
        cell?.detailTextLabel?.text = MyTweets[indexPath.row].User_email
        return cell!
    }
    

}
