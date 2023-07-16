package com.example.weijinghusandoridlabs;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.weijinghusandoridlabs.databinding.MessageDetailsLayoutBinding;

public class MessageDetailsFragment extends Fragment {

        ChatMessage thisMessage;

        public MessageDetailsFragment(ChatMessage toShow){

            thisMessage = toShow;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle instance){

            MessageDetailsLayoutBinding binding = MessageDetailsLayoutBinding.inflate(inflater);

            binding.messageText.setText(thisMessage.getMessage());
            binding.timeText.setText(thisMessage.getTimeSent());
            binding.idText.setText(Long.toString(thisMessage.id));

            return binding.getRoot();
        }


}
