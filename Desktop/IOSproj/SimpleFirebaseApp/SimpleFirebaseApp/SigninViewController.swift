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
        
        // Do any additional setup after loading the view.
    }

    @IBAction func signinPressed(_ sender: UIButton) {
        FIRAuth.auth()?.signIn(withEmail: email_field.text!, password: password_field.text!, completion: { (user, error) in
            if error == nil{
               
                    self.performSegue(withIdentifier: "mySegue2", sender: self)
                
                    self.message.textColor = UIColor.green
                    self.message.text = "Successfully loged in!"
                
            }else{
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
