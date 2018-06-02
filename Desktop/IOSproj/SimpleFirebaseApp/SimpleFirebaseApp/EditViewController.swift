//
//  EditViewController.swift
//  SimpleFirebaseApp
//
//  Created by Aidana Ketebay on 16.04.18.
//  Copyright © 2018 SDU. All rights reserved.
//

import UIKit
import Firebase

class EditViewController: UIViewController {

   
    @IBOutlet weak var nameTF: UITextField!
    @IBOutlet weak var bthDateTF: UIDatePicker!
    @IBOutlet weak var emailTF: UITextField!
    @IBOutlet weak var surnameTF: UITextField!
    private var dbRef: FIRDatabaseReference?
    
    var current_user_email = {
        return FIRAuth.auth()?.currentUser?.email
    }
    var snapp : FIRDataSnapshot? = nil
    override func viewDidLoad() {
        super.viewDidLoad()
        dbRef = FIRDatabase.database().reference()
        dbRef = dbRef?.child("User")
        let otherVC = myProfile().currentUser
        let db = FIRDatabase.database().reference().child("User")
        
        db.observe(FIRDataEventType.value, with: { (snapshot) in
            print(snapshot.childrenCount)
            for snap in snapshot.children{
                if(User.init(snapshot: snap as! FIRDataSnapshot).Email != nil){
                    let user = User.init(snapshot: snap as! FIRDataSnapshot)
                    print(user.Email!)
                    if otherVC == user.Email as String!{
                        self.emailTF.text = user.Email
                        self.nameTF.text = user.Name
                        self.surnameTF.text = user.Surname
                        self.snapp = snap as? FIRDataSnapshot
                       
                    }
                }
            }
        })
        // Do any additional setup after loading the view.
    }
    
    @IBAction func Done(_ sender: Any) {
        
        let db = FIRDatabase.database().reference().child("User")
        var user = User.init(snapshot: (snapp as! FIRDataSnapshot?)!)
        
        let formatter = DateFormatter()
        formatter.dateFormat = "dd.MM.yyyy"
        let date = self.bthDateTF.date
        let result = formatter.string(from: date)
        
        user.DateOfRegist = result
        user.Email = emailTF.text
        user.Name = nameTF.text
        user.Surname = surnameTF.text
        
        db.updateChildValues(user.toJSONFormat() as! [AnyHashable : Any])
    }
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    
}
