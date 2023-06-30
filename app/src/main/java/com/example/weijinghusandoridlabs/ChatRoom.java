package com.example.weijinghusandoridlabs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.weijinghusandoridlabs.databinding.ActivityChatRoomBinding;
import com.example.weijinghusandoridlabs.databinding.SentMessageBinding;
import com.example.weijinghusandoridlabs.databinding.ReceiveMessageBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ChatRoom extends AppCompatActivity {

    ActivityChatRoomBinding binding;
    ArrayList<ChatMessage> messages = new ArrayList<>();
    private RecyclerView.Adapter myAdapter;
    private ChatRoomViewModel chatModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        chatModel = new ViewModelProvider(this).get(ChatRoomViewModel.class);
        messages = chatModel.messages.getValue();
        if (messages == null) {
            chatModel.messages.postValue( messages = new ArrayList<>());
        }

        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        binding.sendButton.setOnClickListener(click ->{
//            messages.add(binding.textInput.getText().toString());
//            myAdapter.notifyItemChanged(messages.size()-1);
//            //clear the previous text
//            binding.textInput.setText("");
//        });

        // Set click listener for Send button
        binding.sendButton.setOnClickListener(click -> {
                SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh:mm:ss a");
                String currentDateandTime = sdf.format(new Date());

                //Create sent message object
                ChatMessage sentMessage = new ChatMessage(binding.textInput.getText().toString(), currentDateandTime, true);
                messages.add(sentMessage);
                //notify insert
                myAdapter.notifyItemInserted(messages.size() - 1);
                // Clear the textInput field
                binding.textInput.setText("");
        });

        binding.receiveButton.setOnClickListener(click ->{
                SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh:mm:ss a");
                String currentDateandTime = sdf.format(new Date());

                //Create sent message object
                ChatMessage receiveMessage = new ChatMessage(binding.textInput.getText().toString(), currentDateandTime, false);
                messages.add(receiveMessage);
                //notify insert
                myAdapter.notifyItemInserted(messages.size() - 1);
                // Clear the textInput field
                binding.textInput.setText("");
        });

        //set up recycler view adapter
        binding.recycleView.setAdapter(myAdapter = new RecyclerView.Adapter<MyRowHolder>() {
            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                if (viewType == 1) {
                    SentMessageBinding sB = SentMessageBinding.inflate(getLayoutInflater());
                    return new MyRowHolder(sB.getRoot());
                }
                else {
                    ReceiveMessageBinding rB = ReceiveMessageBinding.inflate(getLayoutInflater());
                    return new MyRowHolder(rB.getRoot());
                }

            }

            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
//                holder.messageText.setText("");
//                holder.timeText.setText("");
//                // Retrieve the object for the specific position in the messages list
//                String obj = messages.get(position);
//
//                // Set the messageText of the holder with the retrieved object
//                holder.messageText.setText(obj);

                holder.messageText.setText("");
                holder.timeText.setText("");

                ChatMessage obj = messages.get(position);
                holder.messageText.setText(obj.getMessage());
                holder.timeText.setText(obj.getTimeSent());
            }

            @Override
            public int getItemCount(){
                return messages.size();
            }

            @Override
            public int getItemViewType(int position){
                ChatMessage obj = messages.get(position);
                if(obj.isSentButton() == true){
                    return 1;
                }
                else{
                    return 0;
                }
            }
        });

        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));
    }


    class MyRowHolder extends RecyclerView.ViewHolder {
        TextView messageText;
        TextView timeText;
        public MyRowHolder(@NonNull View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.message);
            timeText = itemView.findViewById(R.id.time);
        }
    }
}