//
//  ProfileViewController.swift
//  SimpleFirebaseApp
//
//  Created by Darkhan on 02.04.18.
//  Copyright © 2018 SDU. All rights reserved.
//

import UIKit
import FirebaseAuth
import Firebase
import FirebaseDatabase

class ProfileViewController: UIViewController, UITableViewDelegate,UITableViewDataSource, UISearchResultsUpdating{
    var current_user_email = {
        return FIRAuth.auth()?.currentUser?.email
        
    }
    var filteredTweets = [Tweet]()
    private var dbReference: FIRDatabaseReference?
    private var tweets: [Tweet] = []
    private var id: [String] = []
    @IBOutlet weak var tableView: UITableView!
    
    
    
    let searchCont = UISearchController(searchResultsController: nil)
    
    override func viewDidLoad() {
        super.viewDidLoad()
        dbReference = FIRDatabase.database().reference()
        
        dbReference?.child("tweets").observe(FIRDataEventType.value, with: { (snapshot) in
            self.tweets.removeAll()
            for snap in snapshot.children {
                let tweet = Tweet.init(snap as! FIRDataSnapshot)
                self.tweets.append(tweet)
            }
            self.tweets.reverse()
            self.filteredTweets = self.tweets
            self.searchCont.searchResultsUpdater = self
            self.searchCont.dimsBackgroundDuringPresentation = false
            self.definesPresentationContext = true
            self.tableView.tableHeaderView = self.searchCont.searchBar
            self.tableView.register(UITableViewCell.self, forCellReuseIdentifier: "myCell")
            self.tableView.reloadData()
        })
        navigationItem.title = current_user_email()
        // Do any additional setup after loading the view.
       
    }

    @IBAction func signoutPressed(_ sender: UIButton) {
        do{
        try FIRAuth.auth()?.signOut()
        }catch{
            
        }
        //presentingViewController?.dismiss(animated: true, completion: nil)
       
        
    }
    
    @IBAction func composeButtonPressed(_ sender: UIBarButtonItem) {
        let alertController = UIAlertController(title: "Add new Tweet", message: "What's up?", preferredStyle: UIAlertControllerStyle.alert)
        alertController.addTextField { (textField : UITextField!) -> Void in
            textField.placeholder = "Enter tweet here"
        }
        alertController.addTextField { (textField : UITextField!) -> Void in
            textField.placeholder = "Enter hashtags"
        }
        let postAction = UIAlertAction(title: "Post", style: .default) { (_ ) in
            if(alertController.textFields![0].text == ""){
            
            }
            else{
                let user_email = self.current_user_email()
                let content = alertController.textFields![0].text!
                let hashtag = alertController.textFields![1].text!
                let tweet = Tweet.init(content, user_email!, hashtag)
                let ref = self.dbReference?.child("tweets").childByAutoId()
                ref?.setValue(tweet.toJSONFormat())
                self.id.append((ref?.key)!)
                print(self.id)
            }
        }
        let cancelAction = UIAlertAction(title: "Cancel", style: UIAlertActionStyle.default, handler: {
            (action : UIAlertAction!) -> Void in })
        
        
        alertController.addAction(postAction)
        alertController.addAction(cancelAction)
        
        self.present(alertController, animated: true, completion: nil)
    }
    static let ref = FIRDatabase.database().reference()
    static func remove(parentA: String) {
        
        self.ref.child("tweets").child(parentA).removeValue()
        
        ref.removeValue { error, _ in
            
            print(error)
        }
    }
    
     func tableView(_ tableView: UITableView, commit editingStyle: UITableViewCellEditingStyle, forRowAt indexPath: IndexPath) {
        if editingStyle == .delete {
            if(current_user_email() == FIRAuth.auth()?.currentUser?.email){
                ProfileViewController.remove(parentA: id[indexPath.row])
                tweets.remove(at: indexPath.row)
                tableView.reloadData()
            }
        }
    
        
            
    
    }
    
    
  
    
    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return filteredTweets .count
    }
    func updateSearchResults(for searchController: UISearchController) {
        if searchController.searchBar.text! == ""{
            filteredTweets = tweets
        } else{
            filteredTweets = tweets.filter({($0.Hashtags?.lowercased().contains(searchController.searchBar.text!.lowercased()))!})
        }
        self.tableView.reloadData()
    }
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "myCell")
        let user = filteredTweets[indexPath.row].User_email
        cell?.textLabel?.text = filteredTweets[indexPath.row].Content
        cell?.detailTextLabel?.text = user
        if current_user_email() == user{
            cell?.backgroundColor = UIColor.yellow
        }
        return cell!
    }
    
}
