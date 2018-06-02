//
//  ViewController.swift
//  SimpleFirebaseApp
//
//  Created by Darkhan on 02.04.18.
//  Copyright © 2018 SDU. All rights reserved.
//

import UIKit
import Firebase
import FirebaseDatabase
import FirebaseAuth
class ViewController: UIViewController {
    @IBOutlet weak var email_field: UITextField!
    
    @IBOutlet weak var password_field: UITextField!
    
    @IBOutlet weak var DateOfBirth: UIDatePicker!
    
    @IBOutlet weak var name_field: UITextField!
    
    @IBOutlet weak var surname_field: UITextField!
    
    private var dbRef: FIRDatabaseReference?
    
    @IBOutlet weak var messageLabel: UILabel!
    @IBOutlet weak var indicator: UIActivityIndicatorView!
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
        let current_user = FIRAuth.auth()?.currentUser
        dbRef = FIRDatabase.database().reference()
        dbRef = dbRef?.child("User")
        if current_user != nil{
            performSegue(withIdentifier: "mySegue1", sender: self)
        }
    }

    @IBAction func signUpPressed(_ sender: UIButton) {
        indicator.startAnimating()
        
        
        FIRAuth.auth()?.createUser(withEmail: email_field.text!, password: password_field.text!, completion: { (user, error) in
           
            self.indicator.stopAnimating()
            if error == nil{
                self.messageLabel.text = "User successfully signed up"
                self.messageLabel.textColor = UIColor.green
                let formatter = DateFormatter()
                formatter.dateFormat = "dd.MM.yyyy"
                
                
                let date = self.DateOfBirth.date
                let result = formatter.string(from: date)
                print(result)
                let user = User.init(self.name_field.text!, self.surname_field.text!, result,self.email_field.text!)
                self.dbRef?.childByAutoId().setValue(user.toJSONFormat())

                //self.performSegue(withIdentifier: "signInSegue", sender: self)
            }else{
                print(error)
                self.messageLabel.text = "Something is wrong!"
                self.messageLabel.textColor = UIColor.red
            }
        })
    }
    
    
    @IBAction func signInPressed(_ sender: UITapGestureRecognizer) {
        
    }
    

}

