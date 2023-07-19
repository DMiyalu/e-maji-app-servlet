package com.example.apptestapicals


import android.annotation.TargetApi
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.telephony.SmsManager
import android.telephony.SmsMessage
import android.util.Log
import android.widget.Toast
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.util.Base64


class MySmsReceiver : BroadcastReceiver() {
    private val TAG = MySmsReceiver::class.java.simpleName
    val pdu_type = "pdus"
    val activity = MainActivity()

    @TargetApi(Build.VERSION_CODES.M)
    override fun onReceive(context: Context, intent: Intent) {
        // GlobalScope.launch {
            Log.d("on receive incoming sms", "incoming sms")

            if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
                Toast.makeText(context,"message received",Toast.LENGTH_SHORT).show();
                try {
                    // Get the SMS message.
                    // Get the SMS message.
                    val bundle = intent.extras
                    var msgs: Array<SmsMessage?>
                    var strMessage = ""
                    val format = bundle!!.getString("format")

                    // Retrieve the SMS message received.
                    val pdus = bundle[pdu_type] as Array<Any>?

                    if (pdus != null) {
                        // Check the Android version.
                        // Check the Android version.
                        val isVersionM = Build.VERSION.SDK_INT >=
                                Build.VERSION_CODES.M

                        // Fill the msgs array.
                        msgs = arrayOfNulls(pdus!!.size)
                        for (i in msgs.indices) {
                            // Check Android version and use appropriate createFromPdu.
                            if (isVersionM) {
                                // If Android version M or newer:
                                msgs[i] = SmsMessage.createFromPdu(pdus[i] as ByteArray, format)

                                // Build the message to show.
                                strMessage += "SMS from " + msgs[i]!!.getOriginatingAddress();
                                strMessage += " :" + msgs[i]!!.getMessageBody() + "\n";

                                // Log and display the SMS message.
                                Log.d(TAG, "onReceive: " + strMessage);
                                Toast.makeText(context, strMessage, Toast.LENGTH_LONG).show();

                                // Post Http Request
                                val phoneNumber = msgs[i]!!.getOriginatingAddress().toString();
                                val smsContent = msgs[i]!!.getMessageBody().toString();
                                val encodedString = Base64.getEncoder().encodeToString(smsContent.toByteArray());
                                Log.d("encodedString", encodedString);



                                    // val listResult = UserApi.retrofitService.sendUser();
                                    // Log.d("result:", listResult.toString());

                                    var response = "";
                                    val baseUrl = "https://eppt.graciasgroup.com/api/sms/send/base64?phone_number=${phoneNumber}&message=${encodedString}"
                                    val url = URL(baseUrl)
                                    val connection = url.openConnection()

                                GlobalScope.launch {
                                    BufferedReader(InputStreamReader(connection.getInputStream())).use { inp ->
                                        var line: String?
                                        while (inp.readLine().also { line = it } != null) {
                                            // println(line)
                                            response = line.toString();

                                            val userObject = JSONObject(response);
                                            Log.d("Response: ", response);
                                            Log.d("userObject: ", userObject.toString());
                                            Log.d("userObject: ", userObject["message"].toString());



                                            // Send SMS
                                            if (response.contains("SMSSendDto")) {
                                                if (Build.VERSION.SDK_INT >=
                                                    Build.VERSION_CODES.M) {
                                                    val smsManager = SmsManager.getDefault()

                                                    val smsContent = response.substring(1, 10);
                                                    Log.d("sms content", smsContent);
                                                    smsManager.sendTextMessage(phoneNumber, null, userObject["message"].toString(), null, null)
                                                    Log.d("send sms status", "sent: $smsContent");
                                                } else {
                                                    val smsManager = SmsManager.getDefault()
                                                    Log.d("sms content", response);
                                                    smsManager.sendTextMessage(phoneNumber, null, userObject["message"].toString(), null, null)
                                                    Log.d("send sms status", "sent: $response");
                                                }
                                            } else {
                                                val smsManager = SmsManager.getDefault()
                                                smsManager.sendTextMessage(phoneNumber, null, "close", null, null)
                                                Log.d("send sms status", "sent: open");
                                            }
                                        }
                                        }
                                    }






                                // val result = onSendHttpRequest(postData);
                                // Log.d("result:", result.toString());
                                // val result = GetMyIP().execute("https://eppt.graciasgroup.com/api/sms/send/")

                                //val result: Boolean = onSendHttpRequest(postData);
                                //Log.d("incoming result: ", result.toString());


                                // val smsManager = SmsManager.getDefault()
                                // smsManager.sendTextMessage(msgs[i]!!.getOriginatingAddress().toString(), null, "open", null, null)
                                // Log.d("send sms status", "sent")

//                            this.activity = context.startActivity(intent, null)
                                // (activity as MainActivity).sendMessage(phoneNumber = msgs[i]!!.getOriginatingAddress().toString(), messageContent = msgs[i]!!.getMessageBody().toString())
                            } else {
                                // If Android version L or older:
                                msgs[i] = SmsMessage.createFromPdu(pdus[i] as ByteArray)
                                Log.d(TAG, "onReceive: " + strMessage);
                                Toast.makeText(context, strMessage, Toast.LENGTH_LONG).show();

                                // Http Request
                                val phoneNumber = msgs[i]!!.getOriginatingAddress().toString();
                                val smsContent = msgs[i]!!.getMessageBody().toString();
                                val encodedString = Base64.getEncoder().encodeToString(smsContent.toByteArray());

                                var response = "";
                                val baseUrl = "https://eppt.graciasgroup.com/api/sms/send/base64?phone_number=${phoneNumber}&message=${encodedString}"
                                val url = URL(baseUrl)
                                val connection = url.openConnection()

                                GlobalScope.launch {
                                    BufferedReader(InputStreamReader(connection.getInputStream())).use { inp ->
                                        var line: String?
                                        while (inp.readLine().also { line = it } != null) {
                                            // println(line)
                                            response = line.toString();
                                            Log.d("Response: ", response);
                                        }
                                    }

                                    // Send SMS
                                    if (response.contains("SMSSendDto")) {
                                        val smsManager = SmsManager.getDefault()
                                        smsManager.sendTextMessage("+243810994698", null, response, null, null)
                                        Log.d("send sms status", "sent: open");
                                    } else {
                                        val smsManager = SmsManager.getDefault()
                                        smsManager.sendTextMessage("+243810994698", null, "close", null, null)
                                        Log.d("send sms status", "sent: $response ");
                                    }
                                }
                            }
                        }
                    }
                } catch (e: Error) {
                    Log.d("Error", e.toString())
                }
            } else {
                Toast.makeText(context,"can't receive message",Toast.LENGTH_SHORT).show();
            }
        //}
    }

}