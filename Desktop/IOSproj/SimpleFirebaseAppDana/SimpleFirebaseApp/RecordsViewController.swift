//
//  RecordsViewController.swift
//  SimpleFirebaseApp
//
//  Created by Aidana Ketebay on 11.04.18.
//  Copyright © 2018 SDU. All rights reserved.
//

import UIKit
import AVFoundation
import FirebaseAuth
import Firebase
import FirebaseDatabase
class RecordsViewController: UIViewController {

    @IBOutlet weak var TextFi: UITextField!
    public var isPlay: Bool = false
    private var dbRef: DatabaseReference?
    var recordingSession: AVAudioSession!
    var audioRecorder: AVAudioRecorder!
    var audioPlayer: AVAudioPlayer!
    var numberOfRecords = 50
    
   
    let otherVC = ProfileViewController()
    override func viewDidLoad() {
        super.viewDidLoad()
        recordingSession = AVAudioSession.sharedInstance()
        AVAudioSession.sharedInstance().requestRecordPermission { (isPermitted) in
            if isPermitted{
                print("Success!")
            }
        }
        if let number: Int = UserDefaults.standard.object(forKey: "myNumber") as? Int{
            numberOfRecords = number
        }
        // Do any additional setup after loading the view.
    }
    func getDirectory() -> URL{
        let paths = FileManager.default.urls(for: .documentDirectory, in: .userDomainMask)
        return paths[0]
    }
    func displayAlert(_ title: String, _ message: String){
        let alert = UIAlertController(title: title, message: message, preferredStyle: .alert)
        alert.addAction(UIAlertAction(title: "dissmiss", style: .default, handler: nil))
        present(alert, animated: true, completion: nil)
    }
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    var url : String = ""
    @IBAction func Play(_ sender: Any) {
        isPlay = true
        if audioRecorder == nil{
            numberOfRecords += 1
            let fileName = getDirectory().appendingPathComponent("\(numberOfRecords).m4a")
            let settings = [AVFormatIDKey: Int(kAudioFormatMPEG4AAC), AVSampleRateKey: 12000, AVNumberOfChannelsKey: 1, AVEncoderAudioQualityKey: AVAudioQuality.high.rawValue]
            url = fileName.absoluteString
            do
            {
                audioRecorder = try AVAudioRecorder(url: fileName, settings: settings)
                audioRecorder.delegate = self as! AVAudioRecorderDelegate
                audioRecorder.record()
                
              //  buttonLabel.setTitle("Stop", for: .normal)
            }catch{
                audioRecorder = nil
                displayAlert("Ups!", "Recording failed")
            }
        }else{
            audioRecorder.stop()
            audioRecorder = nil
            UserDefaults.standard.setValue(numberOfRecords, forKey: "myNumber")
          //  buttonLabel.setTitle("Record", for: .normal)
           // tableView.reloadData()
        }
    }
    
    @IBAction func Stop(_ sender: Any) {
        audioRecorder.stop()
        audioRecorder = nil
        UserDefaults.standard.setValue(numberOfRecords, forKey: "myNumber")
    }
    @IBAction func Tweeet(_ sender: Any) {
        let tt = TextFi.text
       // audioRecorder.stop()
        audioRecorder = nil
        UserDefaults.standard.setValue(numberOfRecords, forKey: "myNumber")
        //let path = getDirectory().appendingPathComponent("\(indexPath.row+1).m4a")
       // let tweet = Tweet.init(tt!, otherVC.current_user_email()!,)//,isPath: isPlay,path: url)
       // dbRef?.childByAutoId().setValue(tweet.toJSONFormat())
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
