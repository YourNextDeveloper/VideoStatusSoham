package com.example.videostatus.base

sealed class Permissions(val value: String) {

    object Camera : Permissions("android.permission.CAMERA")
    object Microphone : Permissions("android.permission.RECORD_AUDIO")
    object Sensors : Permissions("android.permission.BODY_SENSORS")

    sealed class Storage(value: String) : Permissions(value) {
        object ReadExternal : Storage("android.permission.READ_EXTERNAL_STORAGE")
        object WriteExternal : Storage("android.permission.WRITE_EXTERNAL_STORAGE")
    }
    sealed class Calendar(value: String) : Permissions(value) {
        object Read : Calendar("android.permission.READ_CALENDAR")
        object Write : Calendar("android.permission.WRITE_CALENDAR")
    }
    sealed class Location(value: String) : Permissions(value) {
        object Fine : Location("android.permission.ACCESS_FINE_LOCATION")
        object Coarse : Location("android.permission.ACCESS_COARSE_LOCATION")
    }

    // TODO below permissions are very critical, If you are not using it please remove from here
    sealed class CallLog(value: String) : Permissions(value) {
        object Read : CallLog("android.permission.READ_CALL_LOG")
        object Write : CallLog("android.permission.WRITE_CALL_LOG")
        object ProcessOutgoing : CallLog("android.permission.PROCESS_OUTGOING_CALLS")
    }
    sealed class Sms(value: String) : Permissions(value) {
        object Send : Sms("android.permission.SEND_SMS")
        object Receive : Sms("android.permission.RECEIVE_SMS")
        object Read : Sms("android.permission.READ_SMS")
        object ReceiveWapPush : Sms("android.permission.RECEIVE_WAP_PUSH")
        object ReceiveMms : Sms("android.permission.RECEIVE_MMS")
    }
    sealed class Contacts(value: String) : Permissions(value) {
        object Read : Contacts("android.permission.READ_CONTACTS")
        object Write : Contacts("android.permission.WRITE_CONTACTS")
        object GetAccounts : Contacts("android.permission.GET_ACCOUNTS")
    }
    sealed class Phone(value: String) : Permissions(value) {
        object ReadState : Phone("android.permission.READ_PHONE_STATE")
        object ReadNumbers : Phone("android.permission.READ_PHONE_NUMBERS")
        object Call : Phone("android.permission.CALL_PHONE")
        object Answer : Phone("android.permission.ANSWER_PHONE_CALLS")
        object AddVoiceMail : Phone("com.android.voicemail.permission.ADD_VOICEMAIL")
        object UseSip : Phone("android.permission.USE_SIP")
        object AcceptHandover : Phone("android.permission.ACCEPT_HANDOVER")
    }

}
