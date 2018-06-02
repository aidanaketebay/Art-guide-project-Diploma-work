//
//  SigninViewController.swift
//  SimpleFirebaseApp
//
//  Created by Darkhan on 02.04.18.
//  Copyright © 2018 SDU. All rights reserved.
//

import UIKit
import Firebase
import FirebaseAuth
class SigninViewController: UIViewController {

    @IBOutlet weak var email_field: UITextField!
    
    @IBOutlet weak var message: UILabel!
    @IBOutlet weak var password_field: UITextField!
    override func viewDidLoad() {
        super.viewDidLoad()
        
        let current_user = Auth.auth().currentUser
        if current_user != nil{
            performSegue(withIdentifier: "mySegue2", sender: self)
        }// Do any additional setup after loading the view.
    }

    @IBAction func signinPressed(_ sender: UIButton) {
        Auth.auth().signIn(withEmail: email_field.text!, password: password_field.text!, completion: { (user, error) in
            if error == nil{
                if (user?.isEmailVerified)!{
                    self.performSegue(withIdentifier: "mySegue2", sender: self)
                }else{
                    self.message.textColor = UIColor.red
                    self.message.text = "Your email is not verified!"
                }
            }else{
                print(error as Any)
                self.message.text = "Error! User is not signed up."
            }
        })
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
