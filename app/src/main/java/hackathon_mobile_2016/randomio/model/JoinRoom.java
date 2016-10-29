package hackathon_mobile_2016.randomio.model;

import com.google.firebase.database.DatabaseReference;

import hackathon_mobile_2016.randomio.Utils;

/**
 * Created by kuro on 29/10/2016.
 */
public class JoinRoom {

    public static void joinRoom(String roomId){
        RoomMember newMember=new RoomMember("xxx","noob",0,1,0);
        DatabaseReference roomMemberManager = Utils.firebaseDatabase.getReference("roomMember/"+roomId);
        roomMemberManager.push().setValue(newMember);
    }
}
