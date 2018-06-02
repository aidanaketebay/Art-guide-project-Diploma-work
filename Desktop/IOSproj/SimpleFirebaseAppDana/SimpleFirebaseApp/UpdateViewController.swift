//
//  UpdateViewController.swift
//  SimpleFirebaseApp
//
//  Created by Aidana Ketebay on 11.04.18.
//  Copyright © 2018 SDU. All rights reserved.
//

import UIKit
import FirebaseAuth
import Firebase
import FirebaseDatabase
class UpdateViewController: UIViewController {

    @IBOutlet weak var name: UITextField!
    @IBOutlet weak var surname: UITextField!
    @IBOutlet weak var email: UITextField!
    @IBOutlet weak var date: UIDatePicker!
    private var dbRef: DatabaseReference?
    var current_user_email = {
        return Auth.auth().currentUser?.email
    }
    var snapp : DataSnapshot? = nil
    override func viewDidLoad() {
        super.viewDidLoad()
        dbRef = Database.database().reference()
        dbRef = dbRef?.child("User")
        let otherVC = ProfileORIGINALViewController().currentUser
        let db = Database.database().reference().child("User")
        db.childByAutoId().setValue(User.init("Danagul", "Zholdykhairova", "17.03.1999", "danagul.kz1@gmail.com"))
        db.observe(DataEventType.value, with: { (snapshot) in
            print(snapshot.childrenCount)
            for snap in snapshot.children{
                if(User.init(snapshot: snap as! DataSnapshot).Email != nil){
                    let user = User.init(snapshot: snap as! DataSnapshot)
                    print(user.Email!)
                    if otherVC == user.Email as String!{
                        self.email.text = user.Email
                        self.name.text = user.Name
                        self.surname.text = user.Surname
                        self.snapp = snap as? DataSnapshot
                        //dat
                    }
                }
            }
        })
        // Do any additional setup after loading the view.
    }

    @IBAction func Done(_ sender: Any) {
        
        let db = Database.database().reference().child("User")
        var user = User.init(snapshot: (snapp )!)
        let formatter = DateFormatter()
        formatter.dateFormat = "dd.MM.yyyy"
        let date = self.date.date
        let result = formatter.string(from: date)
        
        user.DateOfRegist = result
        user.Email = email.text
        user.Name = name.text
        user.Surname = surname.text
        
        db.updateChildValues(user.toJSONFormat() as! [AnyHashable : Any])
    }
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
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
