package com.example.weijinghusandoridlabs;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class ChatRoomViewModel extends ViewModel {
    public MutableLiveData<ArrayList<ChatMessage>> messages = new MutableLiveData<>();

    //public MutableLiveData<ArrayList<String>> getMessages() {
    //    if (messages.getValue() == null) {
    //        messages.setValue(new ArrayList<>());
    //    }
    //    return messages;
    //}
}