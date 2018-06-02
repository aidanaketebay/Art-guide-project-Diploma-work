//
//  ViewController.swift
//  SimpleFirebaseApp
//
//  Created by Darkhan on 02.04.18.
//  Copyright © 2018 SDU. All rights reserved.
//

import UIKit
import Firebase
import FirebaseAuth
class ViewController: UIViewController {
    var gradient : CAGradientLayer!
    
    
    /* @IBOutlet weak var email_field: UITextField!
    
    @IBOutlet weak var password_field: UITextField!
    */
    @IBOutlet weak var signUpPressed: UIButton!
    @IBOutlet weak var name_field: UITextField!
    @IBOutlet weak var surname_field: UITextField!
    @IBOutlet weak var email_field: UITextField!
    @IBOutlet weak var password_field: UITextField!
    @IBOutlet weak var messageLabel: UILabel!
    @IBOutlet weak var indicator: UIActivityIndicatorView!
    @IBOutlet weak var DateOfBirth: UIDatePicker!
    
    private var dbRef: DatabaseReference?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
        dbRef = Database.database().reference()
        dbRef = dbRef?.child("User")
        gradient = CAGradientLayer()
        gradient.frame = self.view.bounds
        gradient.colors = [UIColor.blue.cgColor, UIColor.cyan.cgColor]
        
        
    }

    @IBAction func signUpPressed(_ sender: UIButton) {
        indicator.startAnimating()
        signUpPressed.layer.cornerRadius = 10
        signUpPressed.layer.borderWidth = 1.8
        signUpPressed.layer.borderColor = UIColor.white.cgColor
        signUpPressed.isOpaque = true
        Auth.auth().createUser(withEmail: email_field.text!, password: password_field.text!, completion: { (user, error) in
            self.indicator.stopAnimating()
            if error == nil{
                user?.sendEmailVerification(completion: { (error) in
                    if error == nil{
                        print(user?.isEmailVerified as Any)
                        self.messageLabel.text = "Check your email.We sent you a verification link"
                        self.messageLabel.textColor = UIColor.green
                    }
                })
                print("DSADsdasdasdasdasdasdasdasd")
              
                let formatter = DateFormatter()
                formatter.dateFormat = "dd.MM.yyyy"
                
                
                let date = self.DateOfBirth.date
                let result = formatter.string(from: date)
                let user = User.init(self.name_field.text!, self.surname_field.text!, result,self.email_field.text!)
                 self.dbRef?.childByAutoId().setValue(user.toJSONFormat())
                self.performSegue(withIdentifier: "signInSegue", sender: self)
            }else{
                self.messageLabel.text = "Something is wrong!"
                self.messageLabel.textColor = UIColor.red
            }
        })
    }
    
    
   
    

}

