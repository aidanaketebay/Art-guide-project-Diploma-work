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
import AVFoundation
class ProfileViewController: UIViewController,UITableViewDelegate,UITableViewDataSource,AVAudioRecorderDelegate {
    @IBOutlet weak var tableView: UITableView!
  // @IBOutlet weak var PlayButton: UIButton!
    var recordButton: UIButton!
    var recordingSession: AVAudioSession!
    var audioRecorder: AVAudioRecorder!
    var tweets: [Tweet] = []
    var record: [String] = []
    var audioPlayer: AVAudioPlayer!
    var numberOfRecords = 0
    private var dbRef: DatabaseReference?
    var current_user_email = {
        return Auth.auth().currentUser?.email
    }
    override func viewDidLoad() {
        numberOfRecords = 0
        super.viewDidLoad()
        navigationItem.title = current_user_email()
        dbRef = Database.database().reference()
                                /////
                                /////
                                /////
                                /////
                                /////
                                //////
        dbRef = dbRef?.child("tweets")
        dbRef?.observe(DataEventType.value, with: { (snapshot) in
            print(snapshot.childrenCount)
            self.tweets.removeAll()
            for snap in snapshot.children{
                let tweet = Tweet.init(snapshot: snap as! DataSnapshot)
                self.tweets.append(tweet)
//                if tweet.ISPath!{
//                    self.record.append(tweet.Path!)
//                }
            }
            self.tweets.reverse()
            self.tableView.reloadData()
        })
        recordingSession = AVAudioSession.sharedInstance()
        AVAudioSession.sharedInstance().requestRecordPermission { (isPermitted) in
            if isPermitted{
                print("Success!")
            }
        }
        if let number: Int = UserDefaults.standard.object(forKey: "myNumber") as? Int{
            numberOfRecords = number
        }
    }

    func getDirectory() -> URL{
        let paths = FileManager.default.urls(for: .documentDirectory, in: .userDomainMask)
        return paths[0]
    }
    
    @IBAction func PlayAudio(_ sender: UIButton) {
        var superview = sender.superview
        while let view = superview, !(view is UITableViewCell) {
            superview = view.superview
        }
        guard let cell = superview as? UITableViewCell else {
            print("button is not contained in a table view cell")
            return
        }
        guard let indexPath = tableView.indexPath(for: cell) else {
            print("failed to get index path for cell containing button")
            return
        }
       // let ttt = tweets[indexPath.row].Path
       // let url = NSURL(string: ttt!)
//        do{
//            audioPlayer = try AVAudioPlayer.init(contentsOf: url! as URL)
//            audioPlayer.play()
//        }catch{
//
//        }
        
    }
    @IBAction func composeButtonPressed(_ sender: UIBarButtonItem) {
        let alertController = UIAlertController(title: "Add new Tweet", message: "What's up?", preferredStyle: UIAlertControllerStyle.alert)
        alertController.addTextField { (textField : UITextField!) -> Void in
            textField.placeholder = "Enter here"
        }
       
        ///                                     // post action na alertAction
        let postAction = UIAlertAction(title: "Post", style: .default) { (_ ) in
            let tweet = Tweet.init(alertController.textFields![0].text!, self.current_user_email()!)
                self.dbRef?.childByAutoId().setValue(tweet.toJSONFormat())  /// SAVE
        }
        let cancelAction = UIAlertAction(title: "Cancel", style: UIAlertActionStyle.default, handler: {
            (action : UIAlertAction!) -> Void in })
        
        
        alertController.addAction(postAction)
        alertController.addAction(cancelAction)
        
        self.present(alertController, animated: true, completion: nil)
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
        print(tweets.count)
        return tweets.count
    }
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "myCell")
        cell?.textLabel?.text = tweets[indexPath.row].Content
        cell?.detailTextLabel?.text = tweets[indexPath.row].User_email//text with descrip
//        if tweets[indexPath.row].Path == nil {
//            tweets[indexPath.row].ISPath = false
//        }
//        if tweets[indexPath.row].ISPath! {
//         //   TableViewCell().ff()
//        }
      //  let element = cell?.viewWithTag(1)
        // UILabel.self; labs = (UILabel)[cell, viewWithTag:1];
        if current_user_email() == tweets[indexPath.row].User_email{
            cell?.backgroundColor = UIColor.yellow
        }
        return cell!
    }
}
