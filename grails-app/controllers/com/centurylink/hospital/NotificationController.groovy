package com.centurylink.hospital

class NotificationController {

    def index() { }
    def sendJMSMessageCall(){
        def c = Prescription.createCriteria()
        def expiredPrescription = c.list{
            le ("expirationDate",new Date() )
        }
        //If Prescription is expired send a JMS Message out to the Doctor and the Nurse..
        try {

            if (expiredPrescription){
                def message = "You have expired Prescriptions"
                sendJMSMessage("queue.notification", message)

            }
        }catch (Exception e)
        {
            println e
        }

    }

}
