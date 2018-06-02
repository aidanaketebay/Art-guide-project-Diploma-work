//
//  myProfile.swift
//  SimpleFirebaseApp
//
//  Created by Aidana Ketebay on 14.04.18.
//  Copyright © 2018 SDU. All rights reserved.
//

import UIKit
import Firebase
import FirebaseAuth
import FirebaseDatabase

class myProfile: UIViewController, UITableViewDelegate, UITableViewDataSource{

    @IBOutlet weak var Email: UILabel!
    @IBOutlet weak var Name: UILabel!
    @IBOutlet weak var Surname: UILabel!
    @IBOutlet weak var tableVieww: UITableView!
    private var dbRef: FIRDatabaseReference?
    private var dbRef1: FIRDatabaseReference?
    private var MyTweets: [Tweet] = []
    @IBOutlet weak var dateOfBirth: UILabel!
    
    public var currentUser : String = ""
    
    
    override func viewDidLoad() {
        
        super.viewDidLoad()
        dbRef = FIRDatabase.database().reference()
        dbRef = dbRef?.child("tweets")
        
        let otherVC = ProfileViewController()
        currentUser = otherVC.current_user_email()!
        dbRef?.observe(FIRDataEventType.value, with: { (snapshot) in
            print(snapshot.childrenCount)
            self.MyTweets.removeAll()
            for snap in snapshot.children{
                let snap = snap as! FIRDataSnapshot
                let tweet = Tweet.init(snap)
                if self.currentUser == tweet.User_email {
                    self.MyTweets.append(tweet)
                }
            }
            self.MyTweets.reverse()
            
            self.Email.text = self.currentUser
           
            self.tableVieww.reloadData()
        })
       
        let db = FIRDatabase.database().reference().child("User")
        db.observe(FIRDataEventType.value, with: { (snapshot) in
            print(snapshot.childrenCount)
            for snap in snapshot.children {
                let user = User.init(snapshot: snap as! FIRDataSnapshot)
                if(user != nil && user.Email != nil){
                    let user = User.init(snapshot: snap as! FIRDataSnapshot)
                    print(user.Email!)
                    if self.currentUser == user.Email {
                        self.dateOfBirth.text = user.DateOfRegist
                        self.Email.text = user.Email
                        self.Name.text = user.Name
                        self.Surname.text = user.Surname
                        print(user.Name!)
                        print("No")
                        
                    }
                }
            }
        })
        
    }
    @IBAction func signoutPressed(_ sender: UIButton) {
        do{
            try FIRAuth.auth()?.signOut()
        }catch{
            
        }
        presentingViewController?.dismiss(animated: true, completion: nil)
    }
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        //print(MyTweets.count)
        return MyTweets.count
    }
    
    func tableView(_ tableView: UITableView, commit editingStyle: UITableViewCellEditingStyle, forRowAt indexPath: IndexPath) {
        if editingStyle == .delete{
            //self.myMap.removeAnnotation(annotations[indexPath.row])
            
            let deletingrow = self.MyTweets[indexPath.row]
            MyTweets.remove(at: indexPath.row)
            FIRDatabase.database().reference().child("tweets").child(deletingrow.Content!).removeValue()
            
            
            tableVieww.reloadData()
            
        }
        
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        let cell = tableView.dequeueReusableCell(withIdentifier: "myCell")
        cell?.textLabel?.text = MyTweets[indexPath.row].Content
        cell?.detailTextLabel?.text = MyTweets[indexPath.row].User_email
        return cell!
    }
    
    
    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */

}
